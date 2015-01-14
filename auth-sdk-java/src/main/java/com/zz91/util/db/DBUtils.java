package com.zz91.util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.log4j.Logger;


import com.mysql.jdbc.StringUtils;
//import com.zz91.util.LoggerUtils;
import com.zz91.util.db.pool.DBPoolFactory;

public class DBUtils {
	
	final static Logger LOG=Logger.getLogger(DBUtils.class);

	/**
	 * 根据sql语句获取数据条数，传递的sql语句必须是"select count(*)..."格式
	 * 
	 * @param con
	 * @param countSQL
	 * @return
	 * @throws SQLException
	 */
	public static int getRowCount(Connection con, String countSQL)
			throws SQLException {
		ResultSet rs = null;
		PreparedStatement pStatement = null;
		int count = 0;
		try {
			pStatement = con.prepareStatement(countSQL);
			pStatement.clearParameters();
			rs = pStatement.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			} else {
				count = 0;
			}
		} catch (SQLException ex) {
			throw new SQLException(ex.getMessage());

		} finally {
			closeResStat(rs, pStatement);
		}
		return count;
	}

	// ===============通过继承IReadDataHandler，IInsertUpdateHandler类来处理SQL语句的执行====================
	/**
	 * 依据传入的查询语句以及查询结果处理器查询指定的数据库中的记录
	 * 
	 * @param dbName
	 *            连接的数据库
	 * @param query
	 *            查询语句
	 * @param reader
	 *            查询结果处理器
	 * @return 是否成功处理
	 */
	public static boolean select(String dbName, String query,
			IReadDataHandler reader) {
		return select(dbName, query, reader, null);
	}

	/**
	 * 依据传入的查询语句以及查询结果处理器查询指定的数据库中的记录
	 * 
	 * @param dbName
	 *            连接的数据库
	 * @param query
	 *            查询语句
	 * @param reader
	 *            查询结果处理器
	 * @param errMsg
	 *            指定出错时的显示信息
	 * @return 是否成功处理
	 */
	public static boolean select(String dbName, String query,
			IReadDataHandler reader, String errMsg) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rset = null;
		boolean success = true;
		if (StringUtils.isEmptyOrWhitespaceOnly(query)) {
			LOG.error("DBUtil ERROR:Empty SQL!");
			return false;
		}
		try {
			con = DBUtils.getConnection(dbName);
			stmt = con.prepareStatement(query);
			LOG.debug("SQL Query:" + query);
			if (reader instanceof IParamReadDataHandler)
				((IParamReadDataHandler) reader).setParams(stmt);
			rset = stmt.executeQuery();
			reader.handleRead(rset);
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		} finally {
			closeConnResStat(con, rset, stmt);
		}
		return success;
	}

	public static boolean insertUpdate(String dbName, String query) {
		return insertUpdate(dbName, query, null, null);
	}

	public static boolean insertUpdate(String dbName, String query,
			String errMsg) {
		return insertUpdate(dbName, query, null, errMsg);
	}

	public static boolean insertUpdate(String dbName, String query,
			IInsertUpdateHandler batch) {
		return insertUpdate(dbName, query, batch, null);
	}

	public static boolean insertUpdate(String dbName, String query,
			IInsertUpdateHandler batch, String errMsg) {
		Connection con = null;
		PreparedStatement stmt = null;
		boolean success = true;
		if (StringUtils.isEmptyOrWhitespaceOnly(query)) {
			LOG.error("DBUtil ERROR:Empty SQL!");
			return false;
		}
		try {
			con = DBUtils.getConnection(dbName);
			stmt = con.prepareStatement(query);
			if (batch != null)
				batch.handleInsertUpdate(stmt);
			else
				stmt.executeUpdate();
			DBUtils.commit(con);
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		} finally {
			closeConnResStat(con, null, stmt);
		}
		return success;
	}

	/**
	 * 依据传入的查询语句以及查询结果处理器查询指定的数据库中的记录
	 * 
	 * @param reader
	 *            查询结果处理器
	 * @return 是否成功处理
	 */
	public static boolean select(Connection con, String query,
			IReadDataHandler reader) {
		return select(con, query, reader, null);
	}

	/**
	 * 依据传入的查询语句以及查询结果处理器查询指定的数据库中的记录
	 * 
	 * @param reader
	 *            查询结果处理器
	 * @param errMsg
	 *            指定出错时的显示信息
	 * @return 是否成功处理
	 */
	public static boolean select(Connection con, String query,
			IReadDataHandler reader, String errMsg) {
		PreparedStatement stmt = null;
		ResultSet rset = null;
		boolean success = true;
		if (StringUtils.isEmptyOrWhitespaceOnly(query)) {
			LOG.error("DBUtil ERROR:Empty SQL!");
			return false;
		}
		try {
			stmt = con.prepareStatement(query);
			if (reader instanceof IParamReadDataHandler)
				((IParamReadDataHandler) reader).setParams(stmt);
			rset = stmt.executeQuery();
			reader.handleRead(rset);
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		} finally {
			closeResStat(rset, stmt);
		}
		return success;
	}

	public static boolean insertUpdate(Connection con, String query) {
		return insertUpdate(con, query, null, null);
	}

	public static boolean insertUpdate(Connection con, String query,
			String errMsg) {
		return insertUpdate(con, query, null, errMsg);
	}

	public static boolean insertUpdate(Connection con, String query,
			IInsertUpdateHandler batch) {
		return insertUpdate(con, query, batch, null);
	}

	public static boolean insertUpdate(Connection con, String query,
			IInsertUpdateHandler batch, String errMsg) {
		PreparedStatement stmt = null;
		boolean success = true;
		if (StringUtils.isEmptyOrWhitespaceOnly(query)) {
			LOG.error("DBUtil ERROR:Empty SQL!");
			return false;
		}
		try {
			stmt = con.prepareStatement(query);
			if (batch != null)
				batch.handleInsertUpdate(stmt);
			else
				stmt.executeUpdate();
			commit(con);
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		} finally {
			closeStatement(stmt);
		}
		return success;
	}

	// ==================================oracle=================================================
	/**
	 * 使用传递的数据库连接，获取指定名称的序列 Oracle
	 */
	public static String getSequence(Connection conn, String sequenceName)
			throws SQLException {
		String sequenceID = null;
		StringBuffer buffer = new StringBuffer();
		buffer.append("Select ");
		buffer.append(sequenceName);
		buffer.append(".nextval from dual");
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			pstm = conn.prepareStatement(buffer.toString());
			rs = pstm.executeQuery();
			if (rs.next())
				sequenceID = rs.getString(1);
		} catch (SQLException se) {
			throw se;
		} finally {
			closeResStat(rs, pstm);
			buffer = null;
		}
		return sequenceID;
	}

	/**
	 * 取数据库服务器中系统时间，只适用于oracle数据库
	 */
	public static java.sql.Date getOracleSysDate(Connection conn)
			throws SQLException {
		java.sql.Date result = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("select sysdate from dual");
			rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getDate(1);
			}
		} finally {
			closeResStat(rs, ps);
		}
		return result;
	}

	/**
	 * 根据传入的SQL语句，获取分页SQL语句 oracle
	 * 
	 * @param sql
	 *            传入的SQL语句
	 * @param offset
	 *            第一条纪录的偏移量
	 * @param count
	 *            要显示的记录条数
	 * @return
	 */
	public static String pageSQL(String sql, int offset, int count) {
		if (offset < 0 || count <= 0) {
			return sql;
		} else {
			StringBuffer sb = new StringBuffer(512);
			sb.append("select * from (select t.* ,rownum as raa from ( ");
			sb.append(sql);
			sb.append(") t where rownum <");
			sb.append(offset + count);
			sb.append(") where raa >= ");
			sb.append(offset);
			return sb.toString();
		}
	}

	// =======================================================================================
	/**
	 * 获取指定DATASOURCE的连接
	 * 
	 * @param dsName
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection(String dsName) throws SQLException {
		return createConnection(dsName);
	}

	/**
	 * 获得一个新的数据库连接
	 * 
	 * @param key
	 * @return Connection
	 * @throws SQLException
	 * @throws Exception
	 */
	private static Connection createConnection(String dsName)
			throws SQLException {
		DBPoolFactory df = DBPoolFactory.getInstance();
		DataSource ds_pooled = df.getDBPool(dsName).getDs_pooled();
		Connection conn = ds_pooled.getConnection();
		beginTrans(conn);
		return conn;
	}

	/**
	 * 根据传递的参数创建连接
	 * 
	 * @param driverName
	 * @param url
	 * @param user
	 * @param password
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private static Connection createJDBCConnection(String driverName,
			String url, String user, String password) throws SQLException,
			ClassNotFoundException {
		Connection conn = null;
		Class.forName(driverName);
		conn = DriverManager.getConnection(url, user, password);
		beginTrans(conn);
		return conn;
	}

	/**
	 * 根据传递的参数创建连接
	 * 
	 * @param driverName
	 * @param url
	 * @param user
	 * @param password
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static Connection getConnection(String driverName, String url,
			String user, String password) throws SQLException,
			ClassNotFoundException {
		return createJDBCConnection(driverName, url, user, password);
	}

	/**
	 * 创建数据库事务
	 */
	public static void beginTrans(Connection con) throws SQLException {
		if (con != null) {
			con.setAutoCommit(false);
		}
	}

	/**
	 * 数据库连接事务回滚，无需考虑异常
	 */
	public static void rollback(Connection con) {
		if (con != null) {
			try {
				con.rollback();
			} catch (SQLException e) {
			}
		}
	}

	/**
	 * 数据库事务提交
	 */
	public static void commit(Connection con) throws SQLException {
		if (con != null) {
			con.commit();
		}
	}

	/**
	 * 释放ResultSet，自动忽略异常
	 */
	public static void closeResult(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
			}
			rs = null;
		}
	}

	/**
	 * 释放Statement，自动忽略异常
	 */
	public static void closeStatement(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (Exception e) {
			}
			statement = null;
		}
	}

	/**
	 * 释放Connection，自动忽略异常
	 */
	public static void closeConnection(Connection con) {
		if (con != null) {
			try {
				rollback(con);
				con.close();
			} catch (Exception e) {
			} finally {
			}
			con = null;
		}
	}

	/**
	 * 释放ResultSet和Statement，自动忽略异常
	 */
	public static void closeResStat(ResultSet rs, Statement ps) {
		closeResult(rs);
		closeStatement(ps);
	}

	/**
	 * 释放Connection,ResultSet和Statement，自动忽略异常
	 */
	public static void closeConnResStat(Connection con, ResultSet rs,
			Statement ps) {
		closeResStat(rs, ps);
		closeConnection(con);
	}

	@Deprecated
	public static void clearTableData(Connection con, String tableName) {
		String sql = "delete from " + tableName + " where 1=1 ";
		insertUpdate(con, sql);
	}

	public static void main(String[] args) {
	}

	public static void setConfigFile(String configFile) {
		DBPoolFactory.getInstance().init(configFile);
	}
}
