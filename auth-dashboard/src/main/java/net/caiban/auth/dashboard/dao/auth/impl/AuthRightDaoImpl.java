/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-5
 */
package net.caiban.auth.dashboard.dao.auth.impl;

import java.util.List;

import net.caiban.auth.dashboard.dao.BaseDao;
import net.caiban.auth.dashboard.dao.auth.AuthRightDao;
import net.caiban.auth.dashboard.domain.auth.AuthRight;

import org.springframework.stereotype.Component;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-5
 */
@Component("authRightDao")
public class AuthRightDaoImpl extends BaseDao implements AuthRightDao {

	final static String SQL_PREFIX = "authRight";
	
	@Override
	public Integer countChild(String parentCode) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "countChild"), parentCode);
	}

	@Override
	public Integer createRight(AuthRight right) {
		return null;
	}

	@Override
	public Integer deleteRightByCode(String code) {
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteRightByCode"), code);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AuthRight> queryChild(String parentCode) {
		
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryChild"), parentCode);
	}

	@Override
	public String queryMaxCodeOfChild(String parentCode) {
		
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryMaxCodeOfChild"), parentCode);
	}

	@Override
	public Integer updateRight(AuthRight right) {
		
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateRight"), right);
	}
	
	@Override
	public AuthRight queryOneRight(String code){
		
		return (AuthRight) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryOneRight"), code);
	}

	@Override
	public Integer insertRight(AuthRight right) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertRight"), right);
	}

	@Override
	public Integer deleteDeptRight(String code) {
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteDeptRight"), code);
	}

	@Override
	public Integer deleteRoleRight(String code) {
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteRoleRight"), code);
	}

	@Override
	public Integer queryIdByCode(String code) {
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "queryIdByCode"), code);
	}

	@Override
	public String queryNameByCode(String code) {
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryNameByCode"), code);
	}

}
