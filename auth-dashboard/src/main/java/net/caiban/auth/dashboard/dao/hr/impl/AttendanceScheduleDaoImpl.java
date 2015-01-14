/**
 * 
 */
package net.caiban.auth.dashboard.dao.hr.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.caiban.auth.dashboard.dao.BaseDao;
import net.caiban.auth.dashboard.dao.hr.AttendanceScheduleDao;
import net.caiban.auth.dashboard.domain.hr.AttendanceSchedule;

import org.springframework.stereotype.Component;

/**
 * @author mays
 *
 */
@Component("attendanceScheduleDao")
public class AttendanceScheduleDaoImpl extends BaseDao implements AttendanceScheduleDao {

	final static String SQL_PREFIX="attendanceSchedule";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AttendanceSchedule> queryDefault(Integer isuse) {
		
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryDefault"), isuse);
		
	}

	@Override
	public Integer insert(AttendanceSchedule schedule) {
		
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insert"), schedule);
	}

	@Override
	public Integer update(AttendanceSchedule schedule) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "update"), schedule);
	}

	@Override
	public Integer updateStatus(Integer id, Integer isuse) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", id);
		root.put("isuse", isuse);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateStatus"), root);
	}

	@Override
	public String queryName(Integer id) {
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryName"), id);
	}

}
