package net.caiban.auth.dashboard.dao.hr.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.caiban.auth.dashboard.dao.BaseDao;
import net.caiban.auth.dashboard.dao.hr.AttendanceDao;
import net.caiban.auth.dashboard.domain.hr.Attendance;
import net.caiban.auth.dashboard.dto.PageDto;

import org.springframework.stereotype.Component;


@Component("attendanceDao")
public class AttendanceDaoImpl extends BaseDao implements AttendanceDao {
	
	final static String SQL_PREFIX="attendance";
	
	@Override
	public Integer deleteAttendance(Date from, Date to, Integer scheduleId) {
		Map<String, Object> datetodate = new HashMap<String, Object>();
		datetodate.put("from", from);
		datetodate.put("to", to);
		datetodate.put("scheduleId", scheduleId);
		return this.getSqlMapClientTemplate().delete(buildId(SQL_PREFIX,"deleteAttendance"), datetodate);
	}

	@Override
	public Integer insert(Attendance attendance) {
		return (Integer)this.getSqlMapClientTemplate().insert(buildId(SQL_PREFIX,"insert"),attendance);
	}
	
	/****************************************/

	@SuppressWarnings("unchecked")
	@Override
	public List<Attendance> queryAttendance(String name, String code,
			Date from, Date to, PageDto<Attendance> page) {
		Map<String, Object>  root  =  new  HashMap<String, Object>();
		root.put("name", name);
		root.put("code", code);
		root.put("from", from);
		root.put("to", to);
		root.put("page", page);
		
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX,"queryAttendance"),root);
	}

	@Override
	public Integer queryAttendanceCount(String name, String code, Date from, Date to) {
		Map<String, Object>  root  =  new  HashMap<String, Object>();
		root.put("name", name);
		root.put("code", code);
		root.put("from", from);
		root.put("to", to);
//		root.put("scheduleId", scheduleId);
		
		return (Integer)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX,"queryAttendanceCount"), root);
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Attendance> queryByGmtWork(Date from, Date to) {
//		 Map<String, Date>  fromdateto = new HashMap<String, Date>();
//		 fromdateto.put("from", from);
//		 fromdateto.put("to", to);
//		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX,"getAttByDate"), fromdateto);
//	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Attendance> queryAttendancesByWork(Date from, Date to, String code, Integer scheduleId) {
		Map<String, Object>  root  =  new  HashMap<String, Object>();
		root.put("from", from);
		root.put("to", to);
		root.put("code", code);
		root.put("scheduleId", scheduleId);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryAttendancesByWork"), root);
	}

	

}
