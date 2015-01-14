package net.caiban.auth.dashboard.service.hr.impl;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;

import net.caiban.auth.dashboard.domain.hr.Attendance;
import net.caiban.auth.dashboard.dto.PageDto;
import net.caiban.auth.dashboard.service.hr.impl.AttendanceServiceImpl;
import net.caiban.service.BaseServiceTestCase;

import org.junit.Test;

public class AttendanceServiceImplTest extends BaseServiceTestCase{
	@Resource
    private AttendanceServiceImpl  attendanceServiceImpl;
	
	
	@Test
	public void test_Impt() {
		 clean();
		 String filepath ="";
		 FileInputStream inputStream =null;
		try {
			inputStream = new FileInputStream(new File(filepath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 boolean b  = attendanceServiceImpl.impt(new Date(), new Date(), inputStream, null, 	1);
		 assertNotNull(b);
		 assertTrue(b);


	}

	@Test
	public void test_PageAttendance() {
		clean();
		for(int i=1;i<=37;i++){
			insert(null, null, null, null, null, null);
			
		}
		
		PageDto<Attendance>  page  = new PageDto<Attendance>();
		page = attendanceServiceImpl.pageAttendance(null,null, null, null, page);
		assertNotNull(page);
		assertNotNull(page.getTotalRecords());
		assertEquals(20, page.getTotalRecords().intValue());
		
		//最后一页的测试
		
		page  = new PageDto<Attendance>();
		page  =attendanceServiceImpl.pageAttendance(null,null, null, null, page);
		assertNotNull(page);
		assertNotNull(page.getTotalRecords());
		assertEquals(7, page.getTotalRecords().intValue());
	}
	
	
/***********************************/
	
	private void clean(){
		try {
			connection.prepareStatement("delete from attendancecount").execute();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}	
	
	
	public int insert(String name,String code,String account,Date gmtWork,
			Date gmtCreated,Date modeified) {
		
		
		StringBuffer  sb = new StringBuffer();
		sb.append("insert into attendance");
		sb.append("(name,code,account,gmtWork,gmtCreated,modeified)");
		sb.append("values");
		sb.append("("+name+","+code+","+account+","+gmtWork+","+gmtCreated+","+modeified+")");
		
		try {
			connection.prepareStatement(sb.toString()).execute();
			return   insertResult();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
			
	}
	
	

}
