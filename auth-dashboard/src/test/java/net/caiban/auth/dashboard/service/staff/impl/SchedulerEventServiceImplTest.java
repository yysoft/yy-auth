package net.caiban.auth.dashboard.service.staff.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.caiban.auth.dashboard.domain.staff.SchedulerEvent;
import net.caiban.auth.dashboard.service.staff.schedulerEventService;
import net.caiban.service.BaseServiceTestCase;

import org.junit.Test;

import com.zz91.util.datetime.DateUtil;


public class SchedulerEventServiceImplTest extends BaseServiceTestCase{
	 
	@Resource
	private schedulerEventService schedulerEventService;
	
	@Test
	public void testAssignEvent()	{
		clean();
		Integer id=schedulerEventService.assignEvent(new SchedulerEvent("test text", "text details", new Date(), new Date(), "test assginAccount",
				"test ownerAccount", 1, 1, null, null, "test deptCode"));
		SchedulerEvent event=queryTestRecord(id);
		assertNotNull(event);
		assertEquals("test text", event.getText());
		assertEquals("test assginAccount", event.getAssignAccount());
		assertEquals("test ownerAccount", event.getOwnerAccount());
	}
	
	@Test
	public void testdeleteEvent()	{
		clean();
		Integer id=createOneTestRecord(new SchedulerEvent("test text", "text details", new Date(), new Date(), "test assginAccount",
				"test ownerAccount", 1, 1, null, null, "test deptCode"));
		Integer i=schedulerEventService.deleteEvent(id);
		assertEquals(1, i.intValue());
		SchedulerEvent event=queryTestRecord(id);
		assertNull(event);
	}
	
	@Test
	public void testQueryAssociatedEvent()	{
		clean();
		Integer id=createOneTestRecord(new SchedulerEvent("test text1", "text details1", new Date(), new Date(), "test assginAccount",
				"test ownerAccount", 1, 1, null, null, "test deptCode"));
//		createOneTestRecord(new SchedulerEvent("test text1", "text details1", new Date(), new Date(), "test assginAccount",
//				"test ownerAccount", 1, 1, null, null, "test deptCode"));
//		
//		createOneTestRecord(new SchedulerEvent("test text2", "text details2", new Date(), new Date(), "test assginAccount",
//				"test ownerAccount", 1, 2, null, null, "test deptCode"));
//		
//		createOneTestRecord(new SchedulerEvent("test text3", "text details3", new Date(), new Date(), "test assginAccount",
//				"test ownerAccount", 2, 1, null, null, "test deptCode"));
//		
//		createOneTestRecord(new SchedulerEvent("test text4", "text details4", new Date(), new Date(), "test assginAccount",
//				"test ownerAccount", 3, 2, null, null, "test deptCode"));
//		
//		createOneTestRecord(new SchedulerEvent("test text5", "text details5", new Date(), new Date(), "test assginAccount",
//				"test ownerAccount", 2, 2, null, null, "test deptCode"));
		List<SchedulerEvent> list=schedulerEventService.queryAssociatedEvent(id);
		assertNotNull(list);
		assertEquals(1, list.size());
	}
	
	@Test
	public void testReassignEvent()	{
		clean();
		SchedulerEvent event=new SchedulerEvent("old text", "old details", new Date(), new Date(), "test assginAccount",
				"old ownerAccount", 1, 1, null, null, "test deptCode");
		Integer id=createOneTestRecord(event);
		event.setId(id);
		event.setOwnerAccount("new ownerAccount");
		event.setText("new text");
		event.setDetails("new details");
		Integer i=schedulerEventService.reassignEvent(event);
		assertEquals(1, i.intValue());
		assertEquals("new ownerAccount", event.getOwnerAccount());
		assertEquals("new text", event.getText());
		assertEquals("new details", event.getDetails());
	}
	
	@Test
	public void testUpdateEvent()	{
		clean();
		SchedulerEvent event=new SchedulerEvent("test text", "test details", new Date(), new Date(), "test assginAccount",
				"test ownerAccount", 2, 2, null, null, "test deptCode");
		Integer id=createOneTestRecord(event);
		event.setId(id);
		event.setPersent(10);
		event.setImportance(10);
		Integer i=schedulerEventService.updateEvent(id, 2, 2);
		
		assertEquals(1, i.intValue());
		assertEquals(10, event.getPersent().intValue());
		assertEquals(10, event.getImportance().intValue());
	}
	
	@Test
	public void testQueryEvent_1()	{
		clean();
		try {
			createOneTestRecord(new SchedulerEvent("test text", "test details", DateUtil.getDate("2011-06-30 15:21:51","yyyy-MM-dd hh:mm:ss"), 
					DateUtil.getDate("2011-07-01 16:21:51","yyyy-MM-dd hh:mm:ss"), "test assginAccount", 
					"test ownerAccount", 1, 1, null, null, "test deptCode"));
			List<SchedulerEvent> list=schedulerEventService.queryEvent(DateUtil.getDate("2011-06-30 15:21:51","yyyy-MM-dd hh:mm:ss"), DateUtil.getDate("2011-07-01 16:21:51","yyyy-MM-dd hh:mm:ss"), "test ownerAccount", "test deptCode");
			assertNotNull(list);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testQueryEvent_2()	{
		clean();
		try {
			createOneTestRecord(new SchedulerEvent("test text", "test details", DateUtil.getDate("2011-07-01 15:21:51","yyyy-MM-dd hh:mm:ss"), 
					DateUtil.getDate("2011-07-03 16:21:51","yyyy-MM-dd hh:mm:ss"), "test assginAccount", 
					"test ownerAccount", 1, 1, null, null, "test deptCode"));
			List<SchedulerEvent> list=schedulerEventService.queryEvent(DateUtil.getDate("2011-07-01 15:21:51","yyyy-MM-dd hh:mm:ss"), DateUtil.getDate("2011-07-03 16:21:51","yyyy-MM-dd hh:mm:ss"), "test ownerAccount", "test deptCode");
			assertNotNull(list);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	public void testQueryEvent_3()	{
		clean();
		try {
			createOneTestRecord(new SchedulerEvent("test text", "test details", DateUtil.getDate("2011-07-03 15:21:51","yyyy-MM-dd hh:mm:ss"), 
					DateUtil.getDate("2011-07-04 16:21:51","yyyy-MM-dd hh:mm:ss"), "test assginAccount", 
					"test ownerAccount", 1, 1, null, null, "test deptCode"));
			List<SchedulerEvent> list=schedulerEventService.queryEvent(DateUtil.getDate("2011-07-03 15:21:51","yyyy-MM-dd hh:mm:ss"), DateUtil.getDate("2011-07-04 16:21:51","yyyy-MM-dd hh:mm:ss"), "test ownerAccount", "test deptCode");
			assertNotNull(list);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	public void testQueryEvent_4()	{
		clean();
		try {
			createOneTestRecord(new SchedulerEvent("test text", "test details", DateUtil.getDate("2011-07-04 15:21:51","yyyy-MM-dd hh:mm:ss"), 
					DateUtil.getDate("2011-07-06 16:21:51","yyyy-MM-dd hh:mm:ss"), "test assginAccount", 
					"test ownerAccount", 1, 1, null, null, "test deptCode"));
			List<SchedulerEvent> list=schedulerEventService.queryEvent(DateUtil.getDate("2011-07-04 15:21:51","yyyy-MM-dd hh:mm:ss"), DateUtil.getDate("2011-07-06 16:21:51","yyyy-MM-dd hh:mm:ss"), "test ownerAccount", "test deptCode");
			assertNotNull(list);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	public void testQueryEvent_5()	{
		clean();
		try {
			createOneTestRecord(new SchedulerEvent("test text", "test details", DateUtil.getDate("2011-07-06 15:21:51","yyyy-MM-dd hh:mm:ss"), 
					DateUtil.getDate("2011-07-07 16:21:51","yyyy-MM-dd hh:mm:ss"), "test assginAccount", 
					"test ownerAccount", 1, 1, null, null, "test deptCode"));
			List<SchedulerEvent> list=schedulerEventService.queryEvent(DateUtil.getDate("2011-07-06 15:21:51","yyyy-MM-dd hh:mm:ss"), DateUtil.getDate("2011-07-07 16:21:51","yyyy-MM-dd hh:mm:ss"), "test ownerAccount", "test deptCode");
			assertNotNull(list);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testQueryEvent_6()	{
		clean();
		try {
			createOneTestRecord(new SchedulerEvent("test text", "test details", DateUtil.getDate("2011-06-30 15:21:51","yyyy-MM-dd hh:mm:ss"), 
					DateUtil.getDate("2011-07-07 16:21:51","yyyy-MM-dd hh:mm:ss"), "test assginAccount", 
					"test ownerAccount", 1, 1, null, null, "test deptCode"));
			List<SchedulerEvent> list=schedulerEventService.queryEvent(new Date(), new Date(), "test ownerAccount", "test deptCode");
			assertNotNull(list);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<SchedulerEvent> list=schedulerEventService.queryEvent(new Date(), new Date(), "test ownerAccount", "test deptCode");
		assertNotNull(list);
		
	}
	
	private Integer createOneTestRecord(SchedulerEvent event)	{
		String sql;
		sql = "insert into scheduler_event (text,details,start_date,end_date,assign_account,owner_account," +
				"persent,importance,dept_code,gmt_created,gmt_modified)" +
				"values('"							
				+ event.getText()
				+ "','"
				+ event.getDetails()
				+ "',"
				+ DateUtil.toString(event.getStartDate(), "yyyy-MM-dd hh:mm:ss")
				+ ","
				+ DateUtil.toString(event.getEndDate(), "yyyy-MM-dd hh:mm:ss")
				+ ",'"
				+ event.getAssignAccount()
				+ "','"
				+ event.getOwnerAccount()
				+ "',"
				+ event.getPersent()
				+ ","
				+ event.getImportance()
				+ ",'"
				+ event.getDeptCode()
				+ "',now(),now())";
		System.out.println(sql);
		try {
			connection.prepareStatement(sql).execute();
			
			return insertResult();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
		
	}
	
	private SchedulerEvent queryTestRecord(Integer id)	{
		String sql="select text,details,start_date,end_date,assign_account,owner_account ," +
				"persent,importance,dept_code,gmt_created,gmt_modified from scheduler_event where id=" + id;
		try {
			ResultSet rs=connection.createStatement().executeQuery(sql);
			while(rs.next()){
				return new SchedulerEvent(rs.getString(1), rs.getString(2), rs.getDate(3), rs.getDate(4), rs.getString(5),
						rs.getString(6), rs.getInt(7), rs.getInt(8), null, null, rs.getString(11));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}
	
	private void clean()	{
		try {
			connection.prepareStatement("delete from scheduler_event").execute();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
}
