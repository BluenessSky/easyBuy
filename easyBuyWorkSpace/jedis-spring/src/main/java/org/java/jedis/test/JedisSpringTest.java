package org.java.jedis.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisSpringTest {
	ApplicationContext context=null;
	@Before
	public void init(){
		//加载spring配置文件
		context=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
	}
	@Test
	public void test(){
		
		//获取连接池
		JedisPool pool=(JedisPool) context.getBean("jedisPool");
		//创建jedis对象
		Jedis jedis=pool.getResource();
		System.out.println(jedis.get("str1"));
	}
}
