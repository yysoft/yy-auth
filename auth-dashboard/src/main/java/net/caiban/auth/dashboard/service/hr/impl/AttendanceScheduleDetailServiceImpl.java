/**
 * 
 */
package net.caiban.auth.dashboard.service.hr.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.caiban.auth.dashboard.dao.hr.AttendanceScheduleDetailDao;
import net.caiban.auth.dashboard.domain.hr.AttendanceScheduleDetail;
import net.caiban.auth.dashboard.domain.hr.AttendanceScheduleDetailSearch;
import net.caiban.auth.dashboard.dto.hr.AttendanceScheduleDetailDto;
import net.caiban.auth.dashboard.service.hr.AttendanceScheduleDetailService;

import org.springframework.stereotype.Component;

import com.zz91.util.datetime.DateUtil;

/**
 * @author mays
 *
 */
@Component("attendanceScheduleDetailService")
public class AttendanceScheduleDetailServiceImpl implements AttendanceScheduleDetailService {

	@Resource
	private AttendanceScheduleDetailDao attendanceScheduleDetailDao;
	
	@Override
	public void buildSchedule(Integer scheduleId, Date month, Integer[] day, 
			String[] workf, String[] workt, String createdBy) {

		List<AttendanceScheduleDetail> list=new ArrayList<AttendanceScheduleDetail>();
		int i=0;
		for(Integer dayofmonth:day){
			AttendanceScheduleDetail wd=new AttendanceScheduleDetail();
			Date d=DateUtil.getDateAfterDays(month, dayofmonth-1);
			wd.setDay(d);
			wd.setWorkf(parseWorkTime(workf[i])*1000l);
			wd.setWorkt(parseWorkTime(workt[i])*1000l);
			
			wd.setScheduleId(scheduleId);
//			wd.setUnixtime(d.getTime());
			wd.setGmtMonth(month);
			wd.setCreatedBy(createdBy);
			wd.setModifiedBy(createdBy);
			
			list.add(wd);
			i++;
		}
		
		attendanceScheduleDetailDao.deleteSchedule(scheduleId, month);
		for(AttendanceScheduleDetail detail:list){
			attendanceScheduleDetailDao.insert(detail);
		}
		
	}
	
	private Long parseWorkTime(String wt){
		String[] wtArr=wt.split(":");
		return Long.valueOf(wtArr[0])*3600 + Long.valueOf(wtArr[1])*60;
	}

	@Override
	public List<AttendanceScheduleDetailDto> buildInitSchedule(AttendanceScheduleDetailSearch search) {
		//查找当月数据
		//若当月已排班，按照月份，列出所有天时间，并按照排班设置工作时间
		//若未排班，列出默认排班时间
		List<AttendanceScheduleDetail> list=attendanceScheduleDetailDao.queryDefault(search);
		
		Map<Long, AttendanceScheduleDetail> map=new HashMap<Long, AttendanceScheduleDetail>();
		for(AttendanceScheduleDetail detail: list){
			map.put(detail.getDay().getTime(), detail);
		}
		
		Date nextMonth=DateUtil.getDateAfterMonths(search.getGmtMonth(), 1);
		Date monthLastDay=DateUtil.getDateAfterDays(nextMonth, -1);
		
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(search.getGmtMonth());
		int firstDay=calendar.get(Calendar.DAY_OF_MONTH);
		calendar.setTime(monthLastDay);
		int lastDay=calendar.get(Calendar.DAY_OF_MONTH);
		
		List<AttendanceScheduleDetailDto> result=new ArrayList<AttendanceScheduleDetailDto>();
		
		for(int i=firstDay;i<=lastDay;i++){
			Date d=DateUtil.getDateAfterDays(search.getGmtMonth(), i-1);
			try {
				d= DateUtil.getDate(d, "yyyy-MM-dd 00:00:00");
			} catch (ParseException e) {
			}
//			AttendanceScheduleDetail wd=new AttendanceScheduleDetail();
			
			AttendanceScheduleDetailDto dto=new AttendanceScheduleDetailDto();
			
			AttendanceScheduleDetail detail=map.get(d.getTime());
			
			calendar.setTime(d);
			
			if(detail!=null){
				dto.setWorkf(parseWorkTimeFromLong(detail.getWorkf()));
				dto.setWorkt(parseWorkTimeFromLong(detail.getWorkt()));
				dto.setWorkDay(1);
			}else{
				detail=new AttendanceScheduleDetail();
				detail.setWorkf(parseWorkTime("8:30")*1000l);
				dto.setWorkf("8:30");
				
				int m=calendar.get(Calendar.MONTH);
				if(m<9 && m>3){
					detail.setWorkt(parseWorkTime("18:00")*1000l);
					dto.setWorkt("18:00");
				}else {
					detail.setWorkt(parseWorkTime("17:30")*1000l);
					dto.setWorkt("17:30");
				}
				dto.setWorkDay(0);
			}
			
			
			detail.setDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));
			detail.setDayOfMonth(i);
			detail.setUnixtime(d.getTime());
			
			dto.setDetail(detail);
			
			result.add(dto);
		}
		
		return result;
	}

	private String parseWorkTimeFromLong(Long time){
		if(time==null){
			return "";
		}
		
		time=time/1000l;
		long hour=time/3600;
		long min=(time%3600)/60;
		
		String hs=String.valueOf(hour);
		if(hour<10){
			hs="0"+hour;
		}
		
		String ms=String.valueOf(min);
		if(min<10){
			ms="0"+min;
		}
		
		return hs+":"+ms;
	}
	
//	public static void main(String[] args) {
//		AttendanceScheduleDetailServiceImpl service=new AttendanceScheduleDetailServiceImpl();
//		Long a=service.parseWorkTime("8:01")*1000l;
//		System.out.println(service.parseWorkTimeFromLong(a));
//	}
}
