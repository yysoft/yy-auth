/**
 * 
 */
package net.caiban.auth.dashboard.dao.hr.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.caiban.auth.dashboard.dao.BaseDao;
import net.caiban.auth.dashboard.dao.hr.AttendanceAnalysisDao;
import net.caiban.auth.dashboard.domain.hr.AttendanceAnalysis;
import net.caiban.auth.dashboard.dto.PageDto;

import org.springframework.stereotype.Component;

/**
 * @author mays
 *
 */
@Component("attendanceAnalysis")
public class AttendanceAnalysisDaoImpl extends BaseDao implements AttendanceAnalysisDao {

	final static String SQL_PREFIX="attendanceAnalysis";
	@Override
	public Integer insert(AttendanceAnalysis attendanceAnalysis) {
		
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insert"), attendanceAnalysis);
	}
	
	@Override
	public Integer deleteByGmtTarget(Date gmtTarget, Integer scheduleId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("gmtTarget", gmtTarget);
		root.put("scheduleId", scheduleId);
		
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteByGmtTarget"), root);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AttendanceAnalysis> queryAnalysis(String name, String code,
			Date month, PageDto<AttendanceAnalysis> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("name", name);
		root.put("code", code);
		root.put("gmtTarget", month);
		root.put("page", page);
		
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryAnalysis"), root);
	}
	
	@Override
	public Integer queryAnalysisCount(String name, String code, Date month) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("name", name);
		root.put("code", code);
		root.put("gmtTarget", month);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryAnalysisCount"), root);
	}

	@Override
	public Integer updateAnalysis(AttendanceAnalysis analysis) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateAnalysis"), analysis);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AttendanceAnalysis> queryAnalysisByMonth(Date month) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryAnalysisByMonth"), month);
	}

}