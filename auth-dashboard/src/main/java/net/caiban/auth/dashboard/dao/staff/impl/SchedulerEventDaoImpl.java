package net.caiban.auth.dashboard.dao.staff.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.caiban.auth.dashboard.dao.BaseDao;
import net.caiban.auth.dashboard.dao.staff.SchedulerEventDao;
import net.caiban.auth.dashboard.domain.staff.SchedulerEvent;

import org.springframework.stereotype.Component;
@Component("schedulerEventDao")
public class SchedulerEventDaoImpl extends BaseDao implements SchedulerEventDao{
	
	final static String SQL_PREFIX="schedulerEvent";
	@Override
	public Integer deleteEvent(Integer id) {
		
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteEvent") ,id);
	}

	@Override
	public Integer insertEvent(SchedulerEvent event) {
		
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertEvent"), event);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SchedulerEvent> queryAssociatedEvent(Integer reportId) {
		
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryAssociatedEvent"), reportId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SchedulerEvent> queryEvent(Date startDate, Date endDate, String ownerAccount,
			String deptCode) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("startDate", startDate);
		root.put("endDate", endDate);
		root.put("ownerAccount", ownerAccount);
		root.put("deptCode",deptCode);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryEvent"), root);
	}

	@Override
	public Integer updateEvent(SchedulerEvent event) {
		
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateEvent"), event);
	}

	@Override
	public Integer updateEventProcess(Integer id, Integer persent,
			Integer importance) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", id);
		root.put("persent", persent);
		root.put("importance", importance);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateEventProcess"), root);
	}

	@Override
	public SchedulerEvent querySchedulerEvent(Integer id) {
		
		return  (SchedulerEvent) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "querySchedulerEvent"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SchedulerEvent> query() {
		
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "query"));
	}
	

}
