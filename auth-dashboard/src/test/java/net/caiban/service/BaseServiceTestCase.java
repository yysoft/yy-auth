package net.caiban.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientFactoryBean;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import com.ibatis.sqlmap.client.SqlMapClient;

public class BaseServiceTestCase extends
		AbstractDependencyInjectionSpringContextTests {

	protected Connection connection;

	@Autowired
	private SqlMapClientFactoryBean sqlMapClient;

	protected String[] getConfigLocations() {
		return new String[] { "spring-desktop.xml" };
	}

	public void test_demo() {

	}

	public void onSetUp() {
		try {
			connection = ((SqlMapClient) sqlMapClient.getObject())
					.getDataSource().getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onTearDown() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
			}
		}
	}

	public int insertResult() {
		try {
			ResultSet rs = connection.createStatement().executeQuery(
					"select last_insert_id()");
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
