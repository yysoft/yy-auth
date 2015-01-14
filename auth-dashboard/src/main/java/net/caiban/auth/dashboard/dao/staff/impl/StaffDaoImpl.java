/**
 * 
 */
package net.caiban.auth.dashboard.dao.staff.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.caiban.auth.dashboard.dao.BaseDao;
import net.caiban.auth.dashboard.dao.staff.StaffDao;
import net.caiban.auth.dashboard.domain.bs.Bs;
import net.caiban.auth.dashboard.domain.staff.Staff;
import net.caiban.auth.dashboard.dto.PageDto;
import net.caiban.auth.dashboard.dto.staff.StaffDto;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.zz91.util.Assert;

/**
 * @author root
 *
 */
@Component("staffDao")
public class StaffDaoImpl extends BaseDao implements StaffDao {
	
	final static String SQL_PREFIX="staff";
	
	@Resource
	private MessageSource messageSource;
	
	@Override
	public Integer countStaffByStatus(String account, Integer[] status) {
		Assert.notNull(account,  messageSource.getMessage("assert.notnull", new String[] { "account" }, null));
		Assert.notNull(status,  messageSource.getMessage("assert.notnull", new String[] { "status" }, null));
		Map<String,Object> root=new HashMap<String,Object>();
		root.put("account", account);
		root.put("status", status);
		return (Integer)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "countStaffByStatus"),root);
	}

	@Override
	public Integer insertStaff(Staff staff) {
		return (Integer)getSqlMapClientTemplate().insert(buildId(SQL_PREFIX,"insertStaff"), staff);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StaffDto> queryStaff(Staff staff, PageDto<StaffDto> page) {
		
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("staff", staff);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX,"queryStaff"), root);
	}

	@Override
	public Staff queryStaffByAccount(String account) {
		Assert.notNull(account,  messageSource.getMessage("assert.notnull", new String[] { "account" }, null));
		return (Staff)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryStaffByAccount"),account);
	}

	@Override
	public Integer queryStaffCount(Staff staff) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("staff", staff);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryStaffCount"), root);
	}

	@Override
	public Integer updateStaffStatus(Integer staffId, Integer status,
			String note, Date gmtLeft) {
		Assert.notNull(staffId,  messageSource.getMessage("assert.notnull", new String[] { "staffId" }, null));
		Assert.notNull(status,  messageSource.getMessage("assert.notnull", new String[] { "status" }, null));
		Map<String,Object> root=new HashMap<String,Object>();
		root.put("id", staffId);
		root.put("status", status);
		root.put("note", note);
		root.put("gmtLeft", gmtLeft);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateStaffStatus"),root);
	}

	@Override
	public Integer queryStaffIdByAccount(String account) {
		Assert.notNull(account,  messageSource.getMessage("assert.notnull", new String[] { "account" }, null));
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryStaffIdByAccount"), account);
	}

	@Override
	public String queryDeptCodeByStaffId(Integer id) {
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryDeptCodeByStaffId"), id);
	}

	@Override
	public Integer updateStaff(Staff staff) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateStaff"), staff);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Bs> queryBsByStaffId(Integer staffId) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX,"queryBsByStaffId"), staffId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Bs> queryDeptBsByDeptCode(String deptCode) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryDeptBsByStaffId"),deptCode);
	}

	@Override
	public Integer countStaff(String staffNo) {
		
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "countStaff"), staffNo);
	}

	@Override
	public String queryNameByAccount(String account) {
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryNameByAccount"), account);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Staff> queryStaffNameByDeptCode(String deptCode) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryStaffNameByDeptCode"), deptCode);
	}

	@Override
	public String queryAccountByName(String name) {
		
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryAccountByName"), name);
	}
}
