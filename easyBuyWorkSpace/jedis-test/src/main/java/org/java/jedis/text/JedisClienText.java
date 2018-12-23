package org.java.jedis.text;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisClienText {
	/**
	 * 测试机
	 */
	@Test
	public void test(){
		//创建jedis对象(参数1：表示连接的redis所在的服务器地址，参数2：表示连接的Redis的端口号)
		Jedis jedis=new Jedis("192.168.25.129",6379);
		System.out.println(jedis.ping());
		jedis.set("str1","hello jedis");
		String string = jedis.get("str1");
		System.out.println("str1取出的是"+string);
	}
	/**
	 * 使用连接池
	 */
	@Test
	public void text2(){
		//连接池配置
		JedisPoolConfig poolConfig=new JedisPoolConfig();
		//最大连接数
		poolConfig.setMaxTotal(30);
		//最大连接空闲数
		poolConfig.setMaxIdle(2);
		//创建jedis连接池
		JedisPool pool=new JedisPool(poolConfig,"192.168.25.129",6379);
		//从连接池中创建jedis对象
		Jedis jedis = null;
		try {
			jedis =	pool.getResource();
			jedis.set("str1","lian jiechi");
			String string = jedis.get("str1");
			System.out.println("str1取出的是"+string);
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			if(jedis != null){
				jedis.close();
			}
		}
		
	}
}
