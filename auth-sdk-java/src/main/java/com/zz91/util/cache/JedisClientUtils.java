/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-12-13
 */
package com.zz91.util.cache;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

/**
 * @author mays (mays@zz91.net)
 * 
 *         created on 2010-12-13
 */
public class JedisClientUtils {

	private static Logger LOG = Logger.getLogger(JedisClientUtils.class);

	private String DEFAULT_PROP = "cache.properties";

	private static JedisClientUtils _instances = null;
	
	private Jedis client;

	public JedisClientUtils(){
		
	}

	public static synchronized JedisClientUtils getInstance() {
		if (_instances == null) {
			_instances = new JedisClientUtils();
		}
		return _instances;
	}

	/**
	 * 使用默认配置初始化memcached客户端
	 */
	public void init() {
		init(DEFAULT_PROP);
	}

	/**
	 * 初始化缓存服务客户端
	 * 
	 * @param properties
	 *            :配置文件的名字或者完整的配置文件路径 例：cache.properites 或
	 *            /usr/config/cache.properties
	 */
	public void init(String properties) {
		// 从配置文件读取缓存服务器信息
		LOG.debug("Loading cache properties:" + properties);
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(
				properties);
		Properties p = new Properties();
		try {
			p.load(is);
		} catch (IOException e) {
			LOG.error("An error occurred when load cached properties:"
					+ properties, e);
		}
		createClient(p.getProperty("redis.server"), Integer.valueOf(p.getProperty("redis.port")));
	}

	/**
	 * 创建一个memcache客户端连接
	 * 
	 * @param clientKey
	 *            ： 客户端key,在获取客户端对象时需要使用key,key不能与已经存在的key重复
	 * @param serverAddress
	 *            ： memcached服务器及端口地址，多个服务器用空格隔开
	 *            例："127.0.0.1:11211 localhost:11210"
	 */
	public void createClient(String serverAddress, Integer serverPort) {
//		try {
			client = new Jedis(serverAddress, serverPort);
			
//			client = new MemcachedClient(AddrUtil.getAddresses(serverAddress));
			LOG.debug("Jedis client initialized.");
//		} catch (IOException e) {
//			LOG.error("An error occurred when initialize memcached client", e);
//		}
	}

	/**
	 * 关闭全部memcached客户端连接
	 */
	public void shutdownClient() {
		LOG.debug("shutdown jedis client...");
		client.shutdown();
		LOG.debug("client has been closed.");
	}

	/**
	 * 通过key获取memcached客户端
	 * 
	 * @param clientKey
	 *            :要获取的客户端key 例：
	 *            MemcachedUtils.getClient().set("user",0,"user profile");
	 * @return
	 */
	public Jedis getClient() {
		return client;
	}

}
