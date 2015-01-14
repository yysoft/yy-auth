package net.caiban.auth.dashboard.service.staff.impl;

import java.util.List;

import javax.annotation.Resource;

import net.caiban.auth.dashboard.dao.staff.SchedulerReportDao;
import net.caiban.auth.dashboard.domain.staff.SchedulerReport;
import net.caiban.auth.dashboard.service.staff.schedulerReportService;

import org.springframework.stereotype.Component;

import com.zz91.util.Assert;
@Component("schedulerReportService")
public class SchedulerReportServiceImpl implements schedulerReportService{
	
	@Resource
	private SchedulerReportDao schedulerReportDao;

	@Override
	public Integer associateEvent(Integer reportId, Integer eventId) {
		Assert.notNull(reportId, "the reportId can not be null");
		Assert.notNull(eventId, "the eventId can not be null");
		return schedulerReportDao.insertReportEvent(reportId, eventId);
	}

	@Override
	public Integer createReport(SchedulerReport report) {
//		Assert.notNull(report, "the report can not be null");
//		Assert.notNull(report.getAccount(), "the account can not be null");
//		Assert.notNull(report.getText(), "the text can not be null");
//		Assert.notNull(report.getYear(), "the year can not be null");
//		Assert.notNull(report.getWeek(), "the week can not be null");
		return schedulerReportDao.insertReport(report);
	}
	@Override
	public SchedulerReport queryOneReport(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return schedulerReportDao.queryOneReport(id);
	}

	@Override
	public List<SchedulerReport> queryReport(String year, Integer week,
			String account, String deptCode) {
		Assert.notNull(year, "the year can not be null");
		Assert.notNull(week, "the week can not be null");
		return schedulerReportDao.queryReport(year, week, account, deptCode);
	}

	@Override
	public Integer createReportEvent(Integer reportId, Integer eventId) {
		
		return schedulerReportDao.insertReportEvent(reportId, eventId);
	}

	
}
