/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-5
 */
package net.caiban.auth.dashboard.dao.auth.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.caiban.auth.dashboard.dao.BaseDao;
import net.caiban.auth.dashboard.dao.auth.AuthRoleDao;
import net.caiban.auth.dashboard.domain.auth.AuthRole;

import org.springframework.stereotype.Component;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-5
 */
@Component("authRoleDao")
public class AuthRoleDaoImpl extends BaseDao implements AuthRoleDao {
	
	final static String SQL_PREFIX="authRole";

	@Override
	public Integer insertRole(AuthRole role) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertRole"), role);
	}

	@Override
	public Integer insertRoleRight(Integer roleId, Integer rightId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("roleId", roleId);
		root.put("rightId", rightId);
		
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertRoleRight"), root);
	}

	@Override
	public Integer deleteRightOfRole(Integer roleId) {
		
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteRightOfRole"), roleId);
	}

	@Override
	public Integer deleteRole(Integer roleId) {
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteRole"), roleId);
	}

	@Override
	public Integer deleteRoleRight(Integer roleId, Integer rightId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("roleId", roleId);
		root.put("rightId", rightId);
		
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteRoleRight"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AuthRole> queryRole() {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryRole"));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> queryRightIdOfRole(Integer roleId) {
		
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryRightOfRole"), roleId);
	}

	@Override
	public Integer updateRole(AuthRole role) {
		
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateRole"), role);
	}

}
