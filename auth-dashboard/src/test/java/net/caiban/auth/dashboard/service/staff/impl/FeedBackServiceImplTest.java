package net.caiban.auth.dashboard.service.staff.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;

import net.caiban.auth.dashboard.domain.staff.Feedback;
import net.caiban.auth.dashboard.dto.PageDto;
import net.caiban.auth.dashboard.service.staff.FeedbackService;
import net.caiban.service.BaseServiceTestCase;

import org.junit.Test;


public class FeedBackServiceImplTest extends BaseServiceTestCase{
	@Resource
	FeedbackService feedbackService;
	
	@Test
	public void testDealImpossible()	{
		clean();
		Feedback fb=new Feedback(1,1,"test topic","test content","test account",1,null,new Date());
		Integer id=createOneTestRecord(fb);
		Integer i=feedbackService.dealImpossible(id);
		assertEquals(1, i.intValue());
		
		Feedback feedback=queryOneTestRecord(id);
		assertEquals(3, feedback.getStatus().intValue());
		
	}
	
	@Test
	public void testFeedBack()	{
		clean();
		Integer id=feedbackService.feedback(new Feedback(1, 1, "test topic","test content", "test account",0, null, null));
		Feedback fb=queryOneTestRecord(id);
		assertNotNull(fb);
		assertEquals(1, fb.getBsId().intValue());
		assertEquals(0, fb.getStatus().intValue());
//		assertEquals("test topic", fb.getTopic());
//		assertEquals("test content", fb.getContent());
//		assertEquals("test account", fb.getAccount());
	}
	@Test
	public void testDealNothing()	{
		clean();
		Feedback fb=new Feedback(1,1,"test topic","test content","test account",1,null,new Date());
		Integer id=createOneTestRecord(fb);
		Integer i=feedbackService.dealNothing(id);
		assertEquals(1, i.intValue());
		
		Feedback feedback=queryOneTestRecord(id);
		assertEquals(2, feedback.getStatus().intValue());
		
	}
	@Test
	public void testDealSuccess(){
		clean();
		Feedback fb=new Feedback(1,1,"test topic","test content","test account",0,null,new Date());
		Integer id=createOneTestRecord(fb);
		Integer i=feedbackService.dealSuccess(id);
		assertEquals(1, i.intValue());
		
		Feedback feedback=queryOneTestRecord(id);
		assertEquals(1, feedback.getStatus().intValue());
		
	}
	
	@Test
	public void testDelete()	{
		clean();
		Integer id=createOneTestRecord(new Feedback(1,1,"test topic","test content","test account",0,null,null));
		
		Integer i=feedbackService.deleteFeedback(id);
		assertEquals(1, i.intValue());
		
		Feedback feedback=queryOneTestRecord(id);
		assertNull(feedback);
	}
	
	@Test
	public void testPageFeedBack()	{
		clean();
		manyTestRecord(7, 1);
		PageDto<Feedback> page=new PageDto<Feedback>();
		page.setStart(5);
		page.setLimit(5);
		page = feedbackService.pageFeedback(1, page);
		assertNotNull(page.getRecords());
		assertEquals(2, page.getRecords().size());
		assertEquals(7, page.getTotalRecords().intValue());
		
		page.setStart(0);
		page.setLimit(5);
		page = feedbackService.pageFeedback(1, page);
		assertNotNull(page);
		assertEquals(5, page.getRecords().size());
		assertEquals(7, page.getTotalRecords().intValue());
	}
	
	/********************************************/
	private void clean()	{
		try {
			connection.prepareStatement("delete from feedback").execute();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	
	private Integer createOneTestRecord(Feedback fb)	{
		String sql="insert into feedback(`bs_id`,`account`,`topic`,`content`,`status`,`gmt_created`,`gmt_modified`)" +
				"values("							
				+ fb.getBsId()
				+ ",'"
				+ fb.getAccount()
				+ "','"
				+ fb.getTopic()
				+ "','"
				+ fb.getContent()
				+ "',"
				+ fb.getStatus()
				+ ",now(),now())";
		
		try {
			connection.prepareStatement(sql).execute();
			return insertResult();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	private Feedback queryOneTestRecord(Integer id)	{
		String sql = "select id, bs_id,account,topic,content,status,gmt_created,gmt_modified from feedback where id=" + id;
		try {
			ResultSet rs=connection.createStatement().executeQuery(sql);
			if(rs.next()){
				return new Feedback(rs.getInt(1), rs.getInt(2), rs.getString(3), 
						rs.getString(4), rs.getString(5), rs.getInt(6), null, null);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}
	
	public Feedback oneTestRecord(Integer status)	{
		
		return new Feedback(1,1,"old topic","old content","old account",status,null,null);
	}
	
	public void manyTestRecord(int num,Integer status) {
		for (int i = 0; i < num; i++) {
			createOneTestRecord(oneTestRecord(status));
		}
	}
}
