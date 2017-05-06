package com.test;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taotao.content.utils.JedisClient;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class Test {
	/**
	 * redis 单机测试
	 */
	//@org.junit.Test
	public void testDemo1() {
		// 第一步：创建一个Jedis对象。需要指定服务端的ip及端口。
		Jedis jedis = new Jedis("192.168.25.131", 6379);
		// 第二步：使用Jedis对象操作数据库，每个redis命令对应一个方法。
		jedis.set("hello", "fdsafasdfas");
		String result = jedis.get("hello");
		// 第三步：打印结果。
		System.out.println(result);
		// 第四步：关闭Jedis
		jedis.close();

	}

	/**
	 * redis单机版连接连接池
	 */
	//@org.junit.Test
	public void testDemo2() {
		JedisPool jedisPool = new JedisPool("192.168.25.131", 6379);
		Jedis jedis = jedisPool.getResource();
		jedis.set("hello", "fdsafasdfas");
		String result = jedis.get("hello");
		// 第三步：打印结果。
		System.out.println(result);
		// 第四步：关闭Jedis
		jedis.close();
		jedisPool.close();
	}

	/**
	 * redis 连接 redis集群
	 */
	////@org.junit.Test
	public void testDemo3() {

		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.25.131", 7001));
		nodes.add(new HostAndPort("192.168.25.131", 7002));
		nodes.add(new HostAndPort("192.168.25.131", 7003));
		nodes.add(new HostAndPort("192.168.25.131", 7004));
		nodes.add(new HostAndPort("192.168.25.131", 7005));
		nodes.add(new HostAndPort("192.168.25.131", 7006));
		JedisCluster jedisCluster = new JedisCluster(nodes);

		jedisCluster.set("key007", "fdasfas");
		String value = jedisCluster.get("key007");
		System.out.println(value);

		jedisCluster.close();
	}

	/**
	 * Spring配置连接redis 可单机,集群
	 */
	//@org.junit.Test
	public void testDemo4() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-*.xml");
		JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
		jedisClient.set("demo", "集群测试成功!");
		System.out.println(jedisClient.get("demo"));
		
	}

}
