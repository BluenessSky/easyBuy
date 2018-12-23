package org.java.seckill.service;

import java.util.List;

import org.java.common.pojo.EasyBuyResult;
import org.java.dto.Exposer;
import org.java.exception.RepeatSeckillException;
import org.java.exception.SeckillClosedException;
import org.java.exception.SeckillException;
import org.java.pojo.Seckill;

public interface SeckillService {
	/**
	 * 查询上架秒杀的商品
	 * @return
	 */
	List<Seckill> getSeckills();
	/**
	 * 根据编号获取到商品对象
	 * @param seckillId
	 * @return
	 */
	Seckill getById(Long seckillId);
	/**
	 * 是否暴漏秒杀接口
	 */
	Exposer exportSeckillUrl(long seckillId);
	/**
	 * 执行秒杀
	 * @param seckillId		秒杀的商品编号
	 * @param userPhone		用户电话
	 * @param md5			接口加密字符串
	 * @return				秒杀的结果
	 * @throws SeckillException			秒杀过程中出现异常
	 * @throws RepeatSeckillException	重复秒杀异常
	 * @throws SeckillClosedException	秒杀已经关闭异常
	 */
	EasyBuyResult executeSeckill(long seckillId,long userPhone,String md5) 
			throws SeckillException,RepeatSeckillException,SeckillClosedException;
}

