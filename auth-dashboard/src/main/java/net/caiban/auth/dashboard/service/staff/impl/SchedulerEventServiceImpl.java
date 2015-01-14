package net.caiban.auth.dashboard.service.staff.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.caiban.auth.dashboard.dao.staff.SchedulerEventDao;
import net.caiban.auth.dashboard.domain.staff.SchedulerEvent;
import net.caiban.auth.dashboard.service.staff.schedulerEventService;

import org.springframework.stereotype.Component;

import com.zz91.util.Assert;

@Component("schedulerEventService")
public class SchedulerEventServiceImpl implements schedulerEventService{
	
	@Resource
	private SchedulerEventDao schedulerEventDao;
	@Override
	public Integer assignEvent(SchedulerEvent event) {
		Assert.notNull(event.getAssignAccount(), "the asignAccount can not be null");
		Assert.notNull(event.getOwnerAccount(), "the ownerAccount can not be null");
		Assert.notNull(event.getText(), "the text can not be null");
		Assert.notNull(event.getStartDate(), "the startDate can not be null");
		Assert.notNull(event.getEndDate(), "the endDate can not be null");
		return schedulerEventDao.insertEvent(event);
	}

	@Override
	public Integer deleteEvent(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return schedulerEventDao.deleteEvent(id);
	}

	@Override
	public List<SchedulerEvent> queryAssociatedEvent(Integer reportId) {
		//Assert.notNull(reportId, "the reportId can not be null");
		return schedulerEventDao.queryAssociatedEvent(reportId);
	}

	@Override
	public List<SchedulerEvent> queryEvent(Date startDate, Date endDate, String ownerAccount,
			String deptCode) {
//		Assert.notNull(startDate, "the startDate can not be null");
//		Assert.notNull(endDate, "the to endDate not be null");
		return schedulerEventDao.queryEvent(startDate, endDate, ownerAccount, deptCode);
	}

	@Override
	public Integer reassignEvent(SchedulerEvent event) {
		//Assert.notNull(event.getId(), "the id can not be null");
		Assert.notNull(event.getText(), "the text can not be null");
		Assert.notNull(event.getStartDate(), "the startDate can not be null");
		Assert.notNull(event.getEndDate(), "the endDate can not be null");
		
		return schedulerEventDao.updateEvent(event);
	}

	@Override
	public Integer updateEvent(Integer id, Integer persent, Integer importance) {
		Assert.notNull(id, "the id can not be null");
		if(persent==null){
			persent=0;
		}
		if(importance==null){
			importance=0;
		}
		return schedulerEventDao.updateEventProcess(id, persent, importance);
	}

	@Override
	public List<SchedulerEvent> query() {
		
		return schedulerEventDao.query();
	}
}
