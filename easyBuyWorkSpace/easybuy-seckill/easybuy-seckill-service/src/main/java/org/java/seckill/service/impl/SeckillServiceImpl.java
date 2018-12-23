package org.java.seckill.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.java.common.pojo.EasyBuyResult;
import org.java.common.utils.JsonUtils;
import org.java.dto.Exposer;
import org.java.exception.RepeatSeckillException;
import org.java.exception.SeckillClosedException;
import org.java.exception.SeckillException;
import org.java.jedis.JedisClient;
import org.java.mapper.SeckillMapper;
import org.java.mapper.SuccessKilledMapper;
import org.java.pojo.Seckill;
import org.java.pojo.SeckillExample;
import org.java.pojo.SuccessKilled;
import org.java.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
@Service
public class SeckillServiceImpl implements SeckillService {
	@Autowired
	private SeckillMapper seckillMapper;
	@Autowired
	private SuccessKilledMapper successKilledMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${SECKILL_PRODUCT}")
	private String SECKILL_PRODUCT;
	@Value("${SECKILL_EXPIRE}")
	private Integer SECKILL_EXPIRE;
	@Value("${SECKILL_REDIS}")
	private String SECKILL_REDIS;
	@Value("${MD5_SALT}")
	private String MD5_SALT;
	@Override
	public List<Seckill> getSeckills() {
		//1从reids中取出
		String json = jedisClient.get("SECKILL_PRODUCT");
		json=null;
		//2.判断是否有
		if(StringUtils.isNoneBlank(json)){
			//表示Redis中已经存在
			//返回之前重新设置时间
			return JsonUtils.jsonToList(json, Seckill.class);
		}
		//3.如果Redis中没有，从数据库查询
		SeckillExample example=new SeckillExample();
		List<Seckill> list = seckillMapper.selectByExample(example);
		//4.放入Redis中
		jedisClient.set(SECKILL_PRODUCT,JsonUtils.objectToJson(list));
		//5.设置存在时间
		jedisClient.expire(SECKILL_PRODUCT,SECKILL_EXPIRE);
		return list;
	}
	
	@Override
	public Seckill getById(Long seckillId) {
		//1.从redis中去查询
		String json = jedisClient.get(SECKILL_REDIS + seckillId);
		json = null;	//为了方便测试，我会频繁更新所以希望每次更新都会查询到
		//2.判断是否取到了
		if(StringUtils.isNoneBlank(json)){
			//取到后转换成对象
			Seckill seckill = JsonUtils.jsonToPojo(json, Seckill.class);
			//每次取出后重新设置存在时间
			return seckill;
		}
		//3.reids中没有查询到，那么从数据库查询
		Seckill seckill = seckillMapper.selectByPrimaryKey(seckillId);
		//4.放入到redis中
		jedisClient.set(SECKILL_REDIS + seckillId, JsonUtils.objectToJson(seckill));
		//5.设置存在时间
		jedisClient.expire(SECKILL_REDIS + seckillId, SECKILL_EXPIRE);
		return seckill;
	}
	@Override
	public Exposer exportSeckillUrl(long seckillId) {
		//1.根据编号查询对象
		Seckill seckill = this.getById(seckillId);
		//2.根据对象获取到秒杀开始和结束的时间
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		//3.获取到当前系统时间
		Date now = new Date();
		//4.进行比较
		if(now.getTime() > endTime.getTime() || now.getTime() < startTime.getTime()){
			//表示秒杀已经结束或者是还未开始，那么不暴露秒杀接口
			return new Exposer(false, now.getTime(), startTime.getTime(), endTime.getTime());
		}
		//5.在秒杀时间内，那么先经经过md5加密后暴漏秒杀接口
		String md5 = md5(seckillId, MD5_SALT);
		return new Exposer(true, md5, seckillId);
	}
	
	public String md5(long seckillId,String salt){
		String md = seckillId + salt;
		return DigestUtils.md5DigestAsHex(md.getBytes());
	}
	
	@Override
	public EasyBuyResult executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatSeckillException, SeckillClosedException {
		//如果执行秒杀的时候没有md5字符串，那么肯定就是非法秒杀
		//传进来的md5字符串不符合暴漏秒杀接口规范，也认为是非法秒杀
		if(md5 == null || !md5.equals(md5(seckillId,MD5_SALT))){
			throw new SeckillException("非法秒杀");
		}
		//------------------------------------正常秒杀--------------------------------------
		//2.减少库存
		Date now = new Date();//获取到系统当前时间
		//3.根据id查询对象(涉及到多线程)
		Seckill seckill = seckillMapper.getByIdForUpdate(seckillId);		//添加数据库悲观锁
		//4.进行比较
		if(now.getTime() < seckill.getStartTime().getTime() || now.getTime() > seckill.getEndTime().getTime()){
			//表示秒杀已经结束或者是还未开始，那么不暴露秒杀接口
			throw new SeckillClosedException("不再秒杀时间内");
		}
		Map<String, Object> map = new HashMap<>();
		map.put("seckillId", seckillId);
		map.put("now", now);
		int reduceCount = seckillMapper.reduceCount(map);
		//判断
		if(reduceCount == 0){
			//库存不足
			throw new SeckillException("库存不足");
		}
		//************************************减少库存，并且在购买记录表中添加一行记录*****************************
		//1.添加购买记录
		SuccessKilled successKilled = new SuccessKilled();
		successKilled.setSeckillId(seckillId);
		successKilled.setUserPhone(userPhone);
		successKilled.setState((byte)0);
		int insertCount = successKilledMapper.insertSelective(successKilled);
		if(insertCount == 0){
			//重复秒杀
			throw new RepeatSeckillException("重复秒杀");
		}
		
		//表示秒杀成功
		return EasyBuyResult.build(200, "秒杀成功",seckillId);
	}

}
