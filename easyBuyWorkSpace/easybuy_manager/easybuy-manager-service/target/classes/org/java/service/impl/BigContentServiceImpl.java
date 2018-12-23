package org.java.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.java.common.pojo.EasyBuyResult;
import org.java.common.utils.JsonUtils;
import org.java.jedis.JedisClient;
import org.java.mapper.EasybuyBigcontentMapper;
import org.java.pojo.EasybuyBigcontent;
import org.java.pojo.EasybuyBigcontentExample;
import org.java.service.BigContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service
public class BigContentServiceImpl implements BigContentService{
	@Autowired
	private EasybuyBigcontentMapper easybuyBigcontentMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${GUANGGAO_KEY}")
	private String  GUANGGAO_KEY;
	@Value("${DAGUANGGAO}")
	private String 	DAGUANGGAO;
	@Override
	public EasyBuyResult saveBigContent(EasybuyBigcontent bigcontent) {
		try {
			easybuyBigcontentMapper.insertSelective(bigcontent);
			//添加完成后需要将redis中大广告删除
			jedisClient.hdel(GUANGGAO_KEY, DAGUANGGAO);
			return EasyBuyResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return EasyBuyResult.build(500, "添加大广告失败");
		}
	}

	@Override
	public List<EasybuyBigcontent> getAllBigContents() {
		//1.从redis中尝试着取出大广告(如果取到了，那么直接返回)
				String string = jedisClient.hget(GUANGGAO_KEY,DAGUANGGAO);
				if(StringUtils.isNotBlank(string)){
					//表示在redis中查询到了结果
					//将json格式的字符串(因为在后面存入到redis中前会现将其转换恒json格式de字符串存入)
					List<EasybuyBigcontent> list = JsonUtils.jsonToList(string, EasybuyBigcontent.class);
					return list;
				}
				//2.如果redis中取不到，那么执行下面的代码(从mysql中查询)
				EasybuyBigcontentExample example = new EasybuyBigcontentExample();
				List<EasybuyBigcontent> list = easybuyBigcontentMapper.selectByExample(example);
				//3.查询到了结果后将结果写入到redis中
				jedisClient.hset(GUANGGAO_KEY, DAGUANGGAO, JsonUtils.objectToJson(list));
				return list;
	}

}
