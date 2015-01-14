/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-5
 */
package net.caiban.auth.dashboard.dao.auth.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.caiban.auth.dashboard.dao.BaseDao;
import net.caiban.auth.dashboard.dao.auth.AuthUserDao;
import net.caiban.auth.dashboard.domain.auth.AuthRight;
import net.caiban.auth.dashboard.domain.auth.AuthRole;

import org.springframework.stereotype.Component;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-5
 */
@Component("authUserDao")
public class AuthUserDaoImpl extends BaseDao implements AuthUserDao {

	final static String SQL_PREFIX="authUser";
	
	@Override
	public Integer countUser(String account, String password) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("account", account);
		root.put("password", password);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "countUser"), root);
	}

	@Override
	public Integer insertUser(String account, String email, String password,
			Integer steping) {
		Map<String,Object> root=new HashMap<String,Object>();
		root.put("account", account);
		root.put("email", email);
		root.put("password", password);
		root.put("steping", steping);
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertUser"), root);
	}

//	@Override
//	public Integer insertUserRole(Integer userId, Integer roleId) {
//		return null;
//	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AuthRight> queryRightOfUser(String parentCode, String account) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("parentCode", parentCode);
		root.put("account", account);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryRightOfUser"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AuthRight> queryChildRightOfUser(String parentCode,
			String account) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("parentCode", parentCode);
		root.put("account", account);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryChildRightOfUser"), root);
	}

	@Override
	public Integer updatePassword(String password, String account) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("account", account);
		root.put("password", password);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updatePassword"), root);
	}

	@Override
	public Integer deleteUserRole(Integer userId) {
		
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteUserRole"), userId);
	}
	
	
	@Override
	public Integer insertUserRole(Integer userId, Integer[] roleArr) {
		int impacted = 0;
		try {
			getSqlMapClient().startBatch();
			for(Integer roleId : roleArr){
				Map<String, Object> root=new HashMap<String, Object>();
				root.put("roleId", roleId);
				root.put("userId", userId);
				getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertUserRole"), root);
				impacted++;
			}
			getSqlMapClient().executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return impacted;
	}

	@Override
	public Integer queryUserIdByAccount(String account) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryUserIdByAccount"), account);
	}

	@Override
	public Integer updateSteping(Integer userId, Integer steping) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("userId", userId);
		root.put("steping", steping);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateSteping"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AuthRole> queryRoleOfUser(String account) {
		
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryRoleOfUser"), account);
	}

}
