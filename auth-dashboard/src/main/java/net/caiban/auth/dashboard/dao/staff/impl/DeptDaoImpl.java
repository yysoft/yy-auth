package net.caiban.auth.dashboard.dao.staff.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.caiban.auth.dashboard.dao.BaseDao;
import net.caiban.auth.dashboard.dao.staff.DeptDao;
import net.caiban.auth.dashboard.domain.auth.AuthRight;
import net.caiban.auth.dashboard.domain.staff.Dept;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component("deptDao")
public class DeptDaoImpl extends BaseDao implements DeptDao {

   final static String SQL_PREFIX="dept";
	
	@Resource
	private MessageSource messageSource;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Dept> queryChild(String parentCode) {
//		Assert.notNull(parentCode,  messageSource.getMessage("assert.notnull", new String[] { "parentCode" }, null));
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryChild"),parentCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AuthRight> queryRightOfDept(String parentCode, Integer deptId) {
		Map<String, Object> root= new HashMap<String, Object>();
		root.put("parentCode" , parentCode);
		root.put("deptId", deptId);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryRightOfDept"), root);
	}

	@Override
	public Integer queryDeptIdByAccount(String account) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryDeptIdByAccount"), account);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AuthRight> queryChildRightOfDept(String parentCode,
			Integer deptId) {
		Map<String, Object> root= new HashMap<String, Object>();
		root.put("parentCode" , parentCode);
		root.put("deptId", deptId);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryChildRightOfDept"), root);
	}

	@Override
	public Integer countChild(String parentCode) {
		
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "countChild"), parentCode);
	}

	@Override
	public Integer deleteDeptRight(Integer deptId, Integer rightId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("deptId", deptId);
		root.put("rightId", rightId);
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteDeptRight"), root);
	}

	@Override
	public Integer insertDeptRight(Integer deptId, Integer rightId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("deptId", deptId);
		root.put("rightId", rightId);
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertDeptRight"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> queryRightIdOfDept(Integer deptId) {
		
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryRightIdOfDept"), deptId);
	}

	@Override
	public Integer deleteDept(String code) {
		
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteDept"), code);
	}

	@Override
	public Integer deleteDeptBs(String deptCode) {
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteDeptBs"), deptCode);
	}

	@Override
	public Integer deleteRightByDept(String deptCode) {
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteRightByDept"), deptCode);
	}

	@Override
	public Integer insertDept(Dept dept) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertDept"), dept);
	}

	@Override
	public Dept queryOneDept(String code) {
		return (Dept) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryOneDept"), code);
	}

	@Override
	public Integer updateDept(Dept dept) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateDept"), dept);
	}

	@Override
	public String queryMaxCodeOfChild(String parentCode) {
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryMaxCodeOfChild"), parentCode);
	}

}
