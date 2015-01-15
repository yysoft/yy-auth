/**
 * 
 */
package net.caiban.auth.dashboard.dao.bs.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.caiban.auth.dashboard.dao.BaseDao;
import net.caiban.auth.dashboard.dao.bs.BsDao;
import net.caiban.auth.dashboard.domain.bs.Bs;
import net.caiban.auth.dashboard.domain.staff.Dept;
import net.caiban.auth.dashboard.domain.staff.Staff;
import net.caiban.auth.dashboard.dto.PageDto;
import net.caiban.auth.dashboard.dto.staff.StaffDto;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * @author root
 * 
 */
@Component("bsDao")
public class BsDaoImpl extends BaseDao implements BsDao {
	
	final static String SQL_PREFIX="bs";
	
	@Resource
	private MessageSource messageSource;
	
	@Override
	public Integer countBsOfDept(Integer deptId, String projectCode) {
//		Assert.notNull(deptId,  messageSource.getMessage("assert.notnull", new String[] { "deptId" }, null));
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("deptId", deptId);
		root.put("code", projectCode);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "countBsOfDept"), root);
	}

	@Override
	public Integer countBsOfStaff(Integer staffId, String projectCode) {
//		Assert.notNull(staffId,  messageSource.getMessage("assert.notnull", new String[] { "staffId" }, null));
		Map<String,Object> root =new HashMap<String,Object>();
		root.put("staffId", staffId);
		root.put("code", projectCode);
		return (Integer)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "countBsOfStaff"),root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Bs> queryBsOfDept(Integer deptId, String types) {
//		Assert.notNull(deptId,  messageSource.getMessage("assert.notnull", new String[] { "deptId" }, null));
		Map<String,Object> root=new HashMap<String,Object>();
		root.put("deptId", deptId);
		root.put("types",types);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX,"queryBsOfDept"),root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Bs> queryBsOfStaff(Integer staffId, String types) {
//		Assert.notNull(staffId,  messageSource.getMessage("assert.notnull", new String[] { "staffId" }, null));
		Map<String,Object> root=new HashMap<String,Object>();
		root.put("staffId", staffId);
		root.put("types", types);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX,"queryBsOfStaff"),root);
	}

	@Override
	public String queryRightCodeOfBs(String projectCode) {
//		Assert.notNull(projectCode,  messageSource.getMessage("assert.notnull", new String[] { "projectCode" }, null));
		return (String)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX,"queryRightCodeOfBs"),projectCode);
	}

	@Override
	public String queryPasswordOfBs(String pcode) {
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryPasswordOfBs"), pcode);
	}

	@Override
	public Integer insertBs(Bs bs) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertBs"), bs);
	}

	@Override
	public Integer deleteBs(Integer id) {
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteBs"), id);
	}


	@Override
	public Integer updateBs(Bs bs) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateBs"),bs);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Bs> queryBs(Bs bs, PageDto<Bs> page) {
		Map<String, Object> root=new HashMap<String,Object>();
		root.put("bs", bs);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX,"queryBs"),root);
	}

	@Override
	public Integer queryBsCount(Bs bs) {
		Map<String,Object> root=new HashMap<String,Object>();
		root.put("bs", bs);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryBsCount"),root);
	}

	@Override
	public Bs queryOneBs(Integer id) {
		return (Bs) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryOneBs"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Staff> queryStaffByBs(Integer id, String types) {
		Map<String,Object> root=new HashMap<String,Object>();
		root.put("id", id);
		root.put("types", types);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryStaffByBs"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dept> queryDeptByBs(Integer id, String types) {
		Map<String,Object> root=new HashMap<String,Object>();
		root.put("id", id);
		root.put("types", types);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryDeptByBs"),root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> queryDeptIdByBsId(Integer bsId) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryDeptIdByBsId"), bsId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StaffDto> queryBsStaff(Integer bsId) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryBsStaff"), bsId);
	}

	@Override
	public Integer insertBsStaff(Integer bsId, Integer staffId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("bsId", bsId);
		root.put("staffId", staffId);
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertBsStaff"),root);
	}

	@Override
	public Integer deleteBsStaff(Integer bsId, Integer staffId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("bsId", bsId);
		root.put("staffId", staffId);
		return (Integer) getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteBsStaff"),root);
	}

	@Override
	public Integer deleteBsDept(Integer bsId, Integer deptId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("bsId", bsId);
		root.put("deptId", deptId);
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteBsDept"), root);
	}

	@Override
	public Integer insertBsDept(Integer bsId, Integer deptId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("bsId", bsId);
		root.put("deptId", deptId);
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertBsDept"), root);
	}

	@Override
	public Integer deleteBsDept(Integer bsId) {
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteBsDeptByBsId"), bsId);
	}

	@Override
	public Integer deleteBsStaff(Integer bsId) {
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteBsStaffByBsId"), bsId);
	}

	@Override
	public String queryUrl(String key) {
		return (String) getSqlMapClientTemplate().queryForObject(SQL_PREFIX, "queryUrl");
	}
	
	/***************************/

	/*
	public Integer countPageBs(Bs bs) {
		Assert.notNull(bs, "bs is not null");
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"bs.countPageBs", bs);
	}

	@SuppressWarnings("unchecked")
	public List<Bs> queryMyBsByStaffId(Integer staffId, String types) {
		Assert.notNull(staffId, "staffId is not null");
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("staffId", staffId);
		root.put("types", types);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryMyBsByStaffId"),
				root);
	}

	@SuppressWarnings("unchecked")
	public List<Bs> queryMyBsByDeptId(Integer deptId, String types) {
		Assert.notNull(deptId, "deptId is not null");
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("deptId", deptId);
		root.put("types", types);
		return getSqlMapClientTemplate().queryForList("bs.queryMyBsByDeptId",
				root);
	}

	public Integer insertBs(Bs bs) {
		Assert.notNull(bs, "bs is not null");
		return (Integer) getSqlMapClientTemplate().insert("bs.insertBs", bs);
	}

	public Integer updateBs(Bs bs) {
		Assert.notNull(bs, "bs is not null");
		return getSqlMapClientTemplate().update("bs.updateBs", bs);
	}

	@SuppressWarnings("unchecked")
	public List<BsDto> pageBsByBsDo(Bs bs, PageDto page) {
		Assert.notNull(bs, "bs is not null");
		Assert.notNull(page, "page is not null");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bs", bs);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList("bs.pageBsByBsDo", map);
	}
	*/

}
