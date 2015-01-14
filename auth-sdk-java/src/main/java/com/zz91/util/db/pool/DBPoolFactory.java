package com.zz91.util.db.pool;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import com.mysql.jdbc.StringUtils;
import com.zz91.util.LoggerUtils;
import com.zz91.util.db.exception.DBException;
import com.zz91.util.file.FileUtils;
import com.zz91.util.lang.ConvertUtil;

public class DBPoolFactory {

	private static final String DEFAULT_DATASOURCE_CONFIG_FILE = "dbpools.properties";
	private static DBPoolFactory _instance = null;
	
	
	/**
	 * 连接池列表
	 */
	private static Map<String, DataSource> poollist ;

	private DBPoolFactory() {

	}

	public synchronized static DBPoolFactory getInstance() {
		if(_instance==null){
			_instance=new DBPoolFactory();
		}
		return _instance;
	}
	
	public void init(){
		init(DEFAULT_DATASOURCE_CONFIG_FILE);
	}

	/**
	 * 依据配置文件向连接池列表添加新的池化（Pooled）数据源
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void init(String configFile) {
		poollist = new HashMap<String, DataSource>();
		Map<String, String> configMap;
		try {
			configMap = FileUtils.readPropertyFile(configFile, "UTF-8");
			add(configMap);
		} catch (IOException e) {
			LoggerUtils.error(DBPoolFactory.class, "加载配置文件失败,配置文件："+configFile);
		} catch (PropertyVetoException e) {
			LoggerUtils.error(DBPoolFactory.class, "加载配置文件失败,配置文件："+configFile);
		} catch (SQLException e) {
			LoggerUtils.error(DBPoolFactory.class, "初始化数据库配置发生错误："+e.toString());
		}
	}

	/**
	 * 向连接池列表添加一个新的池化（Pooled）数据源
	 * 
	 * @param key
	 * @param driver
	 *            数据连接驱动，如com.mysql.jdbc.Driver
	 * @param url
	 *            连接串，如jdbc:mysql://localhost/samland
	 * @param username
	 *            数据库连接用户名，如samland
	 * @param password
	 *            数据库连接用户密码
	 * @throws PropertyVetoException
	 * @throws SQLException
	 * @throws Exception
	 */
	public void add(String key, String driver, String url, String username, String password)
			throws PropertyVetoException, SQLException {
		if (key == null)
			throw new DBException("DBPool'key' CANNOT be null");
		Map<String, String> configMap = new HashMap<String, String>();
		configMap.put("dataSourceList", key);
		configMap.put(key + ".jdbc.driver", driver);
		configMap.put(key + ".jdbc.url", url);
		configMap.put(key + ".jdbc.username", username);
		configMap.put(key + ".jdbc.password", password);
		add(configMap);
	}

	private void add(Map<String, String> configMap) throws PropertyVetoException, SQLException {
		String[] dataSources = configMap.get("dataSourceList").split(",");
		for (String dataSource : dataSources) {
			dataSource = dataSource.trim();
			if (StringUtils.isEmptyOrWhitespaceOnly(dataSource))//如果数据库连接池的名称为空 跳过
				continue;
			ComboPooledDataSource cpds = new ComboPooledDataSource();
			cpds.setDriverClass(configMap.get(dataSource + ".jdbc.driver"));
			cpds.setJdbcUrl(configMap.get(dataSource + ".jdbc.url"));
			cpds.setUser(configMap.get(dataSource + ".jdbc.username"));
			cpds.setPassword(configMap.get(dataSource + ".jdbc.password"));
			//初始化时获取三个连接，取值应在minPoolSize与maxPoolSize之间。Default: 3 initialPoolSize
			int initialPoolSize = ConvertUtil.convertStringToInt(configMap.get(dataSource
					+ ".jdbc.initialPoolSize"));
			if (initialPoolSize > 0)
				cpds.setInitialPoolSize(initialPoolSize);
			//连接池中保留的最大连接数。Default: 15 maxPoolSize
			int maxPoolSize = ConvertUtil.convertStringToInt(configMap.get(dataSource
					+ ".jdbc.maxPoolSize"));
			if (maxPoolSize > 0)
				cpds.setMaxPoolSize(maxPoolSize);
			// 连接池中保留的最小连接数。
			int minPoolSize = ConvertUtil.convertStringToInt(configMap.get(dataSource
					+ ".jdbc.minPoolSize"));
			if (minPoolSize > 0)
				cpds.setMinPoolSize(minPoolSize);
			//当连接池中的连接耗尽的时候c3p0一次同时获取的连接数。Default: 3 acquireIncrement
			int acquireIncrement = ConvertUtil.convertStringToInt(configMap.get(dataSource
					+ ".jdbc.acquireIncrement"));
			if (acquireIncrement > 0)
				cpds.setAcquireIncrement(acquireIncrement);
			//定义在从数据库获取新连接失败后重复尝试的次数。Default: 30  acquireRetryAttempts
			int acquireRetryAttempts = ConvertUtil.convertStringToInt(configMap.get(dataSource
					+ ".jdbc.acquireRetryAttempts"));
			if (acquireRetryAttempts > 0)
				cpds.setAcquireRetryAttempts(acquireRetryAttempts);
			//每60秒检查所有连接池中的空闲连接。Default: 0  idleConnectionTestPeriod
			int idleConnectionTestPeriod = ConvertUtil.convertStringToInt(configMap.get(dataSource
					+ ".jdbc.idleConnectionTestPeriod"));
			if (idleConnectionTestPeriod > 0)
				cpds.setIdleConnectionTestPeriod(idleConnectionTestPeriod);
			//最大空闲时间,25000秒内未使用则连接被丢弃。若为0则永不丢弃。Default: 0  maxIdleTime
			int maxIdleTime = ConvertUtil.convertStringToInt(configMap.get(dataSource
					+ ".jdbc.idleConnectionTestPeriod"));
			if (maxIdleTime > 0)
				cpds.setMaxIdleTime(maxIdleTime);
			else
				cpds.setMaxIdleTime(60 * 20);
			//两次连接中间隔时间，单位毫秒。Default: 1000 acquireRetryDelay
			int acquireRetryDelay = ConvertUtil.convertStringToInt(configMap.get(dataSource
					+ ".jdbc.acquireRetryDelay"));
			if (acquireRetryDelay > 0)
				cpds.setAcquireRetryDelay(acquireRetryDelay);
			//连接关闭时默认将所有未提交的操作回滚。Default: false autoCommitOnClose
			boolean autoCommitOnClose = ConvertUtil.convertStringToBoolean(configMap.get(dataSource
					+ ".jdbc.autoCommitOnClose"));
			if (autoCommitOnClose)
				cpds.setAutoCommitOnClose(autoCommitOnClose);
			//定义所有连接测试都执行的测试语句。在使用连接测试的情况下这个一显著提高测试速度。注意：
			//测试的表必须在初始数据源的时候就存在。Default: null  preferredTestQuery
			//String preferredTestQuery = configMap.get(dataSource + ".jdbc.preferredTestQuery");
			//if (preferredTestQuery != null)
			//	cpds.setPreferredTestQuery(preferredTestQuery);
			// 因性能消耗大请只在需要的时候使用它。如果设为true那么在每个connection提交的
			// 时候都将校验其有效性。建议使用idleConnectionTestPeriod或automaticTestTable
			// 等方法来提升连接测试的性能。Default: false testConnectionOnCheckout
			// cpds.setTestConnectionOnCheckout(true);
			//如果设为true那么在取得连接的同时将校验连接的有效性。Default: false  testConnectionOnCheckin
			// cpds.setTestConnectionOnCheckin(true);

			//获取连接失败将会引起所有等待连接池来获取连接的线程抛出异常。
			//但是数据源仍有效保留，并在下次调用getConnection()的时候继续尝试获取连接。
			//如果设为true，那么在尝试获取连接失败后该数据源将申明已断开并永久关闭。Default: false  breakAfterAcquireFailure
			boolean breakAfterAcquireFailure = ConvertUtil.convertStringToBoolean(configMap
					.get(dataSource + ".jdbc.breakAfterAcquireFailure"));
			if (breakAfterAcquireFailure)
				cpds.setBreakAfterAcquireFailure(breakAfterAcquireFailure);
			if (poollist.containsKey(dataSource)) {
				DataSources.destroy(poollist.get(dataSource));
				poollist.remove(dataSource);
			}
			poollist.put(dataSource, cpds);
		}

	}

	/**
	 * 根据key字符串获取连接池
	 * 
	 * @param keyDataSources
	 * @return DBPool 连接池
	 * @throws Exception
	 */
	public DBPool getDBPool(String key) {
		if (key == null) {
			//			LoggerUtils.error(DBPoolFactory.class, "DBPool 'key' CANNOT be null");
			throw new DBException(DBPoolFactory.class.getName()
					+ ":getDBPool() occur error, key is null.");
		}
		DataSource ds = poollist.get(key);
		DBPool dbpool = new DBPool();
		dbpool.setDs_pooled(ds);
		return dbpool;
	}
}
