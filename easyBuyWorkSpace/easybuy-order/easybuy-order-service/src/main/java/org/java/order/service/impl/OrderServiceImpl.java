package org.java.order.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.java.common.pojo.EasyBuyResult;
import org.java.common.utils.JsonUtils;
import org.java.jedis.JedisClient;
import org.java.jedis.JedisClientCluster;
import org.java.mapper.EasybuyOrderDetailMapper;
import org.java.mapper.EasybuyOrderMapper;
import org.java.mapper.EasybuyUserAddressMapper;
import org.java.order.service.OrderService;
import org.java.pojo.EasybuyOrder;
import org.java.pojo.EasybuyOrderDetail;
import org.java.pojo.EasybuyProduct;
import org.java.pojo.EasybuyUserAddress;
import org.java.pojo.EasybuyUserAddressExample;
import org.java.pojo.EasybuyUserAddressExample.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service
public class OrderServiceImpl implements OrderService{
	@Autowired
	private EasybuyOrderMapper easybuyOrderMapper;
	@Autowired
	private EasybuyOrderDetailMapper easybuyOrderDetailMapper;
	@Autowired
	private JedisClientCluster jedisClient;
	@Value("${ORDER_ID_REDIS}")
	private String ORDER_ID_REDIS;
	@Value("${REDIS_CART}")
	private String REDIS_CART;
	
	@Autowired
	private EasybuyUserAddressMapper easybuyUserAddressMapper;
	@Override
	public List<EasybuyUserAddress> getAddressbyUserId(Integer userid) {
		EasybuyUserAddressExample example=new EasybuyUserAddressExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andUseridEqualTo(userid);
		List<EasybuyUserAddress> list = easybuyUserAddressMapper.selectByExample(example); 
		return list;
	}

	@Override
	public EasyBuyResult createOrder(Integer addressId, String newAddress, String newRemark,Integer userid,String loginname,Float sum) {
		//-----------------3.添加用户收货地址-----------------
		EasybuyUserAddress address = new EasybuyUserAddress();
		//判断用户是否用的新地址
		if(addressId == -1){
			//新地址
			address.setAddress(newAddress);
			address.setRemark(newRemark);
			address.setUserid(userid);
			//添加新的收货地址
			easybuyUserAddressMapper.insertSelective(address);
		} else {
			//老地址
			address = easybuyUserAddressMapper.selectByPrimaryKey(addressId);
		}
		//-----------------1.生成订单-----------------
		EasybuyOrder order = new EasybuyOrder();
		//先从reids中取出订单编号
		String order_id = jedisClient.get(ORDER_ID_REDIS);
		if(StringUtils.isBlank(order_id)){
			//生成订单编号
			long time = System.currentTimeMillis();//获取到系统当前时间--毫秒
			//将时间存入到redis中
			jedisClient.set(ORDER_ID_REDIS, time + "");
		}
		String orderId = jedisClient.incr(ORDER_ID_REDIS).toString();//加1后取出
		order.setId(orderId);//订单编号
		order.setUserid(userid);
		order.setLoginname(loginname);
		order.setUseraddress(address.getAddress());
		order.setCost(sum);
		order.setState(0);//订单状态(0:未支付 1:已支付)新订单默认都是未支付
		easybuyOrderMapper.insert(order);
		//-----------------2.生成订单详情-----------------
		String json = jedisClient.get(REDIS_CART + userid);
		//转换成集合
		List<EasybuyProduct> list = JsonUtils.jsonToList(json, EasybuyProduct.class);
		for (EasybuyProduct product : list) {
			EasybuyOrderDetail detail = new EasybuyOrderDetail();
			detail.setOrderid(orderId);
			detail.setProductid(product.getId());
			detail.setQuantity(product.getStock());
			detail.setCost(product.getPrice() * product.getStock());
			//插入到数据库中
			easybuyOrderDetailMapper.insertSelective(detail);
		}
		
		return EasyBuyResult.ok(order);
	}

	@Override
	public void updateOrder(String orderId) {
		EasybuyOrder order=new EasybuyOrder();
		order.setId(orderId);
		order.setState(1);
		easybuyOrderMapper.updateByPrimaryKeySelective(order);
		
	}

}
