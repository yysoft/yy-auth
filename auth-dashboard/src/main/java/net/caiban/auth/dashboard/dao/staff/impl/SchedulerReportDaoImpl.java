package net.caiban.auth.dashboard.dao.staff.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.caiban.auth.dashboard.dao.BaseDao;
import net.caiban.auth.dashboard.dao.staff.SchedulerReportDao;
import net.caiban.auth.dashboard.domain.staff.SchedulerReport;

import org.springframework.stereotype.Component;
@Component("schedulerReportDao")
public class SchedulerReportDaoImpl extends BaseDao implements SchedulerReportDao{
	
	final static String SQL_PREFIX="schedulerReport";
	
	@Override
	public Integer insertReport(SchedulerReport report) {
		
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertReport"), report);
	}

	@Override
	public Integer insertReportEvent(Integer reportId, Integer eventId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("reportId", reportId);
		root.put("eventId", eventId);
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertReportEvent"), root);
	}

	@Override
	public SchedulerReport queryOneReport(Integer id) {
		
		return (SchedulerReport) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryOneReport"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SchedulerReport> queryReport(String year, Integer week,
			String account, String deptCode) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("year", year);
		root.put("week", week);
//		root.put("account", account);
//		root.put("deptCode", deptCode);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryReport"), root);
	}

}
