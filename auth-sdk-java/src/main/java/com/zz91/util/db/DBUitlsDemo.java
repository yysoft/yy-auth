package com.zz91.util.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Deprecated
public class DBUitlsDemo implements IParamReadDataHandler {

	private List<User> userList=new ArrayList<User>();
	
	private int age;
 
	public void setAge(int age) {
		this.age = age;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setParams(PreparedStatement paramPreparedStatement) throws SQLException {
		paramPreparedStatement.setInt(1, age);
	}

	public void handleRead(ResultSet paramResultSet) throws SQLException {
		while(paramResultSet.next()){
			User user=new User();
			user.setName(paramResultSet.getString(1));
			user.setAge(paramResultSet.getInt(2));
			userList.add(user);
		}

	}

	public static void main(String [] args) throws SQLException{
		Connection con=DBUtils.getConnection("dbName");
		DBUtils.insertUpdate(con, "insert into user (name,age) values(name1,18),(name2,18),(name3,18),(name4,18)");
		DBUtils.insertUpdate(con, "update user set name='nameup' ,age=22 where id=1");
		DBUtils.commit(con);
		DBUtils.insertUpdate("dbName", "insert into user (name,age) values(name1,18),(name2,18),(name3,18),(name4,18)","插入用户信息时出错。");
		DBUtils.insertUpdate("dbName", "update user set name='nameup' ,age=22 where id=1","更新用户信息时出错。");
		
		DBUtils.insertUpdate(con, "insert into user (name,age) values(?,?)", new IInsertUpdateHandler() {
			
			public void handleInsertUpdate(PreparedStatement paramPreparedStatement) throws SQLException {
				paramPreparedStatement.setString(1, "name1");
				paramPreparedStatement.setInt(2, 11);
				paramPreparedStatement.execute();
			}
		});
		
	}
}
class User{
	private String name;
	private int age;
	private int id;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}