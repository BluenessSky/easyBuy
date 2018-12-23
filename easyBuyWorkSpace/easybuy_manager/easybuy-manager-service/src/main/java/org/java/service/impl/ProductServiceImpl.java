package org.java.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.java.common.pojo.EasyBuyResult;
import org.java.common.utils.JsonUtils;
import org.java.jedis.JedisClient;
import org.java.mapper.EasybuyProductCategoryMapper;
import org.java.mapper.EasybuyProductMapper;
import org.java.pojo.EasybuyProduct;
import org.java.pojo.EasybuyProductCategory;
import org.java.pojo.EasybuyProductCategoryExample;
import org.java.pojo.EasybuyProductCategoryExample.Criteria;
import org.java.pojo.EasybuyProductExample;
import org.java.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.container.page.PageHandler;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.tools.hat.internal.server.FinalizerObjectsQuery;
@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	private EasybuyProductMapper easybuyProductMapper;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Resource(name="addProducterTopic")
	private Destination addProducterTopic;
	
	@Resource(name="deleteProducterTopic")
	private Destination deleteProducterTopic;
	
	@Value("${ITEM_PRODUCT}")
	private String ITEM_PRODUCT;
	
	@Value("${PRODUCT_EXPIRE}")
	private Integer PRODUCT_EXPIRE;
	@Override
	public EasybuyProduct getById(Integer id) {
		//1.从Redis中试着获取到key
		String json = jedisClient.get("ITEM_PRODUCT"+":"+id);
		if(StringUtils.isNotBlank(json)){//redis中是否查询
			//将json格式的字符串转换成对象
			EasybuyProduct product=JsonUtils.jsonToPojo(json,EasybuyProduct.class);
			jedisClient.expire(ITEM_PRODUCT,PRODUCT_EXPIRE);
			return product;
		}
		//2.如果获取不到那么直接导数据中查询
		EasybuyProduct product = easybuyProductMapper.selectByPrimaryKey(id);
		//3.查询的结果放入到Redis中
		jedisClient.set(ITEM_PRODUCT+":"+product.getId(),JsonUtils.objectToJson(product));
		//设置缓存时间
		jedisClient.expire(ITEM_PRODUCT,PRODUCT_EXPIRE);
		return product;
	}
	@Override
	public PageInfo<EasybuyProduct> getProducts(Integer currentPage, Integer pageSize) {
		if(currentPage==null){
			currentPage=1;
		}
		if(pageSize==null){
			pageSize=10;
		}
		PageHelper.startPage(currentPage, pageSize);
		EasybuyProductExample example=new EasybuyProductExample();
		example.setOrderByClause("id desc");
		List<EasybuyProduct> list = easybuyProductMapper.selectByExample(example);
		PageInfo<EasybuyProduct> pageInfo=new PageInfo<>(list);
		return pageInfo;
	}
	@Override
	public EasyBuyResult updateOrSave(final EasybuyProduct product) {
		try {
			
			if(product.getId()!=null){
				easybuyProductMapper.updateByPrimaryKey(product);
			}else{
				//刚添加的商品是不会删除的，讲是否删除改为0，即为不删除
				product.setIsdelete(0);
				easybuyProductMapper.insertSelective(product);
			}
			//将修改或添加的信息同步到solr库中
			//将修改或添加的id存入到activemq
			jmsTemplate.send(addProducterTopic, new MessageCreator() {
				
				@Override
				public Message createMessage(Session session) throws JMSException {
					TextMessage textMessage=session.createTextMessage(product.getId()+"");
					return textMessage;
				}
			});
			
			//信息同步solr中
			return EasyBuyResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return EasyBuyResult.build(500,"添加或者是修改商品出错");
		}
	}
	@Override
	public EasyBuyResult deleteProduct(final Integer id) {
		try {
			EasybuyProduct product = easybuyProductMapper.selectByPrimaryKey(id);
			if(product.getIsdelete()==0){
				product.setIsdelete(1);//设置iddelete属性1代表下架0代表上架
				jmsTemplate.send(deleteProducterTopic, new MessageCreator() {
					
					@Override
					public Message createMessage(Session session) throws JMSException {
						TextMessage textMessage=session.createTextMessage(id+"");
						return textMessage;
					}
				});
			}else {
				product.setIsdelete(0);
				jmsTemplate.send(addProducterTopic, new MessageCreator() {
					
					@Override
					public Message createMessage(Session session) throws JMSException {
						TextMessage textMessage=session.createTextMessage(id+"");
						return textMessage;
					}
				});
			}
			
			//更新导数据库中
			easybuyProductMapper.updateByPrimaryKey(product);
			
			return EasyBuyResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return EasyBuyResult.build(500,"删除商品出错");
		}
	}

}
