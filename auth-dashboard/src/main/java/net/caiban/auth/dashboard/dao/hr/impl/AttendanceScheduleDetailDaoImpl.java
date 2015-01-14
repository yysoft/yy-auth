/**
 * 
 */
package net.caiban.auth.dashboard.dao.hr.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.caiban.auth.dashboard.dao.BaseDao;
import net.caiban.auth.dashboard.dao.hr.AttendanceScheduleDetailDao;
import net.caiban.auth.dashboard.domain.hr.AttendanceScheduleDetail;
import net.caiban.auth.dashboard.domain.hr.AttendanceScheduleDetailSearch;

import org.springframework.stereotype.Component;

/**
 * @author mays
 *
 */
@Component("attendanceScheduleDetailDao")
public class AttendanceScheduleDetailDaoImpl extends BaseDao implements
		AttendanceScheduleDetailDao {

	final static String SQL_PREFIX="attendanceScheduleDetail";

	@Override
	public Integer count(Integer scheduleId, Date gmtMonth) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("scheduleId", scheduleId);
		root.put("gmtMonth", gmtMonth);
		
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "count"), root);
	}

	@Override
	public Integer insert(AttendanceScheduleDetail detail) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insert"), detail);
	}

	@Override
	public Integer deleteSchedule(Integer scheduleId, Date gmtMonth) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("scheduleId", scheduleId);
		root.put("gmtMonth", gmtMonth);
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteSchedule"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AttendanceScheduleDetail> queryDefault(
			AttendanceScheduleDetailSearch search) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryDefault"), search);
	}

}
