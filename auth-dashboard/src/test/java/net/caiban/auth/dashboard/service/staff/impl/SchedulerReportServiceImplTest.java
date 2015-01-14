package net.caiban.auth.dashboard.service.staff.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.caiban.auth.dashboard.domain.staff.SchedulerReport;
import net.caiban.auth.dashboard.domain.staff.SchedulerReportEvent;
import net.caiban.auth.dashboard.service.staff.schedulerReportService;
import net.caiban.service.BaseServiceTestCase;

import org.junit.Test;

import com.zz91.util.datetime.DateUtil;


public class SchedulerReportServiceImplTest extends BaseServiceTestCase{
	@Resource
	schedulerReportService schedulerReportService;
	
	@Test
	public void testAssociateEvent()	{
		clean();
		Integer id=schedulerReportService.associateEvent(1,1);
		SchedulerReportEvent re=queryOneRecord(id);
		assertNotNull(re);
		assertEquals(1, re.getEventId().intValue());
		assertEquals(1, re.getReportId().intValue());
	}
	
	@Test
	public void testCreateReport()	{
		clean();
		Integer id=schedulerReportService.createReport(new SchedulerReport("test deptCode", "test account", "test text",
				"test details", new Date(), "2011", 1, null, null));
		SchedulerReport report=queryOneTestRecord(id);
		assertNotNull(report);
		assertEquals("test deptCode", report.getDeptCode());
		assertEquals("test account", report.getAccount());
		assertEquals("test text", report.getText());
		assertEquals("2011", report.getYear());
		assertEquals(1, report.getWeek().intValue());
	}
	
	@Test
	public void testQueryOneReport()	{
		clean();
		Integer id=createOneTestRecord(new SchedulerReport("test deptCode", "test account", "test text", "test details", new Date(), 
				"2011", 1, null, null));
		SchedulerReport report=schedulerReportService.queryOneReport(id);
		assertNotNull(report);
		assertEquals("2011", report.getYear());
		assertEquals(1, report.getWeek().intValue());
	}
	
	@Test
	public void testQueryReport_1()	{
		clean();
		createOneTestRecord(new SchedulerReport(null, null, "test text", "test details", new Date(), 
				"2011", 1, null, null));
		createOneTestRecord(new SchedulerReport(null, "test account", "test text", "test details", new Date(), 
				"2011", 3, null, null));
		createOneTestRecord(new SchedulerReport("test deptCode", null, "test text", "test details", new Date(), 
				"2011", 5, null, null));
		createOneTestRecord(new SchedulerReport("test deptCode", "test account", "test text", "test details", new Date(), 
				"2011", 2, null, null));
		List<SchedulerReport> list= schedulerReportService.queryReport("2011", 2, "test account", "test deptCode");
		assertNotNull(list);
		assertEquals(1, list.size());
		assertEquals("2011", list.get(0).getYear());
		assertEquals(2, list.get(0).getWeek().intValue());
	}
	
	@Test
	public void testQueryReport_2()	{
		clean();
		createOneTestRecord(new SchedulerReport(null, null, "test text", "test details", new Date(), 
				"2011", 1, null, null));
		createOneTestRecord(new SchedulerReport(null, "test account", "test text", "test details", new Date(), 
				"2011", 3, null, null));
		createOneTestRecord(new SchedulerReport("test deptCode", null, "test text", "test details", new Date(), 
				"2011", 5, null, null));
		createOneTestRecord(new SchedulerReport("test deptCode", "test account", "test text", "test details", new Date(), 
				"2011", 2, null, null));
		List<SchedulerReport> list= schedulerReportService.queryReport("2011", 2, null, null);
		assertNotNull(list);
		assertEquals(1, list.size());
		assertEquals("2011", list.get(0).getYear());
		assertEquals(2, list.get(0).getWeek().intValue());
	}
	
	@Test
	public void testQueryReport_3()	{
		clean();
		createOneTestRecord(new SchedulerReport(null, null, "test text", "test details", new Date(), 
				"2011", 1, null, null));
		createOneTestRecord(new SchedulerReport(null, "test account", "test text", "test details", new Date(), 
				"2011", 3, null, null));
		createOneTestRecord(new SchedulerReport("test deptCode", null, "test text", "test details", new Date(), 
				"2011", 5, null, null));
		createOneTestRecord(new SchedulerReport("test deptCode", "test account", "test text", "test details", new Date(), 
				"2011", 2, null, null));
		List<SchedulerReport> list= schedulerReportService.queryReport("2011", 5, "test deptCode", null);
		assertNotNull(list);
		assertEquals(1, list.size());
		assertEquals("2011", list.get(0).getYear());
		assertEquals(5, list.get(0).getWeek().intValue());
	}
	
	@Test
	public void testQueryReport_4()	{
		clean();
		createOneTestRecord(new SchedulerReport(null, null, "test text", "test details", new Date(), 
				"2011", 1, null, null));
		createOneTestRecord(new SchedulerReport(null, "test account", "test text", "test details", new Date(), 
				"2011", 3, null, null));
		createOneTestRecord(new SchedulerReport("test deptCode", null, "test text", "test details", new Date(), 
				"2011", 5, null, null));
		createOneTestRecord(new SchedulerReport("test deptCode", "test account", "test text", "test details", new Date(), 
				"2011", 2, null, null));
		 
		List<SchedulerReport> list= schedulerReportService.queryReport("2011", 3, null, "test account");
		assertNotNull(list);
		assertEquals(1, list.size());
		assertEquals("2011", list.get(0).getYear());
		assertEquals(3, list.get(0).getWeek().intValue());
	}
	
	private Integer createOneTestRecord(SchedulerReport report)	{
		String sql="insert into `scheduler_report`(`dept_code`,`account`,`text`,`details`,`compose_date`,`year`,`week`,`gmt_created`,`gmt_modified`)" +
		"values('"
		+ report.getDeptCode()
		+ "','"
		+ report.getAccount()
		+ "','"
		+ report.getText()
		+ "','"
		+ report.getDetails()
		+ "','"
		+ DateUtil.toString(report.getComposeDate(), "yyyy-MM-dd hh:mm:ss")
		+ "','"
		+ report.getYear()
		+ "',"
		+ report.getWeek()
		+ ",now(),now())";
		try {
			connection.prepareStatement(sql).execute();
			return insertResult();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private SchedulerReport queryOneTestRecord(Integer id)	{
		String sql="select dept_code,account,text,details,compose_date,year,week,gmt_created,gmt_modified from scheduler_report where id=" + id;
		
		try {
			ResultSet rs=connection.createStatement().executeQuery(sql);
			System.out.println(sql);
			while(rs.next()){
				return new SchedulerReport(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getDate(5), rs.getString(6), rs.getInt(7), null, null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private SchedulerReportEvent queryOneRecord(Integer id)	{
		String sql="select event_id,report_id,gmt_created,gmt_modified from scheduler_report_event where id=" + id;
		try {
			ResultSet rs=connection.createStatement().executeQuery(sql);
			if(rs.next()){
				return new SchedulerReportEvent(rs.getInt(1), rs.getInt(2));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}
	
	private void clean()	{
		try {
			connection.prepareStatement("delete from scheduler_report").execute();
			connection.prepareStatement("delete from scheduler_report_event").execute();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}	
}
