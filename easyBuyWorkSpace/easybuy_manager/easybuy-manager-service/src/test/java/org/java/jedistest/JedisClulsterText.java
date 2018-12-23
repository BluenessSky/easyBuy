package org.java.jedistest;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class JedisClulsterText {
	public static void main(String[] args) {
		Set<HostAndPort> nodes=new HashSet<>();
		nodes.add(new HostAndPort("192.168.25.129",7001));
		nodes.add(new HostAndPort("192.168.25.129",7002));
		nodes.add(new HostAndPort("192.168.25.129",7003));
		nodes.add(new HostAndPort("192.168.25.129",7004));
		nodes.add(new HostAndPort("192.168.25.129",7005));
		nodes.add(new HostAndPort("192.168.25.129",7006));
		JedisCluster jedisCluster=new JedisCluster(nodes);
		jedisCluster.set("key6","8899qq");
		System.out.println(jedisCluster.get("key6"));
	}
}
