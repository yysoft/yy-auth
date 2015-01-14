package net.caiban.auth.dashboard.service.hr.impl;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.caiban.auth.dashboard.dao.hr.AttendanceAnalysisDao;
import net.caiban.auth.dashboard.dao.hr.AttendanceDao;
import net.caiban.auth.dashboard.dao.hr.AttendanceScheduleDao;
import net.caiban.auth.dashboard.dao.hr.AttendanceScheduleDetailDao;
import net.caiban.auth.dashboard.dao.staff.StaffDao;
import net.caiban.auth.dashboard.domain.hr.Attendance;
import net.caiban.auth.dashboard.domain.hr.AttendanceAnalysis;
import net.caiban.auth.dashboard.domain.hr.AttendanceSchedule;
import net.caiban.auth.dashboard.domain.hr.AttendanceScheduleDetail;
import net.caiban.auth.dashboard.domain.hr.AttendanceScheduleDetailSearch;
import net.caiban.auth.dashboard.dto.PageDto;
import net.caiban.auth.dashboard.dto.hr.AttendanceDto;
import net.caiban.auth.dashboard.dto.hr.WorkDay;
import net.caiban.auth.dashboard.service.hr.AttendanceService;
import net.caiban.auth.dashboard.util.DesktopConst;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;

import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

@Component("attendanceService")
public class AttendanceServiceImpl implements AttendanceService {

	@Resource
	private AttendanceDao attendanceDao;
	@Resource
	private StaffDao staffDao;
	@Resource
	private AttendanceAnalysisDao attendanceAnalysisDao;
	@Resource
	private AttendanceScheduleDao attendanceScheduleDao;
	@Resource
	private AttendanceScheduleDetailDao attendanceScheduleDetailDao;

	@Override
	public Boolean impt(Date from, Date to, InputStream inputStream, 
			String dateFormat, Integer scheduleId) {
		
		if(StringUtils.isEmpty(dateFormat)){
			dateFormat="yyyy-MM";
		}
		
		to=new Date(to.getTime()+86399000);
		
		//清理老数据
		attendanceDao.deleteAttendance(from, to, scheduleId);
		
		//导入新数据
		HSSFWorkbook work=null;
		try {
			work = new HSSFWorkbook(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(work==null){
			return false;
		}
		
		HSSFSheet sheet = work.getSheetAt(0);
		int rows = sheet.getPhysicalNumberOfRows();
		
		Map<String, String> accountMap=new HashMap<String, String>();
		
		for (int i = 1; i < rows; i++) {
			HSSFRow row = sheet.getRow(i);
			if(row!=null){
				Attendance att=new Attendance();
				att.setCode(row.getCell(0).getRichStringCellValue().toString());
				att.setName(row.getCell(1).getRichStringCellValue().toString());
				att.setScheduleId(scheduleId);
				
				String gmtwork=row.getCell(2).getRichStringCellValue().toString();
				if(!StringUtils.isEmpty(gmtwork)){
					try {
						att.setGmtWork(DateUtil.getDate(gmtwork, dateFormat));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				
				if(StringUtils.isNotEmpty(att.getName())){
					if(accountMap.get(att.getName())==null){
						String ac= staffDao.queryAccountByName(att.getName());
						if(ac==null){
							ac="";
						}
						accountMap.put(att.getName(), ac);
					}
					att.setAccount(accountMap.get(att.getName()));
				}
				
				attendanceDao.insert(att);
			}
		}
		
		return true;
	}

	@Override
	public PageDto<Attendance> pageAttendance(String name, String code,
			Date from, Date to, PageDto<Attendance> page) {
		//计算 from to
		page.setRecords(attendanceDao.queryAttendance(name, code, from, to, page));
		page.setTotalRecords(attendanceDao.queryAttendanceCount(name, code, from, to));
		return page;
	}
	
	
	/**
	 * <br />当月应出勤天数：统计选定工作日
	 * <br />出勤天数：工作日内有打卡记录
	 * <br />
	 * <br />请假：无法判断，由HR填写，不在统计范围内
	 * <br />其他天数：无法判断，由HR定义与填写，不在统计范围内
	 * <br />
	 * <br />未打卡：无上班/下班时间记录
	 * <br />旷工：无记录=应出勤天数-出勤天数?  迟到30分钟以上算旷工半天
	 * <br />迟到：上班时间>应上班时间
	 * <br />早退：下班时间<应下班时间
	 * <br />加班：下班时间>20:00
	 * @param month
	 * @param workDay
	 */
	@Override
	public void analysis(Date month) {
		
		List<AttendanceSchedule> list=attendanceScheduleDao.queryDefault(DesktopConst.ISUSE_TRUE);
		
		for(AttendanceSchedule schedule:list){
			AttendanceScheduleDetailSearch search = new AttendanceScheduleDetailSearch();
			search.setGmtMonth(month);
			search.setScheduleId(schedule.getId());
			
			List<AttendanceScheduleDetail> detailList=attendanceScheduleDetailDao.queryDefault(search);
			
			analysisBySchedule(month, schedule.getId(), detailList);
		}
		
	}
	
	
	
	private Map<String, AttendanceDto> buildDayRecord(List<Attendance> list, Long half, Long monthDay){
		Map<String, AttendanceDto> dtoMap=new HashMap<String, AttendanceDto>();
		long wt=0;
		for(Attendance attendance:list){
			AttendanceDto dto=dtoMap.get(attendance.getCode());
			if(dto==null){
				dto=new AttendanceDto();
				dto.setAccount(attendance.getAccount());
				dto.setCode(attendance.getCode());
				dto.setName(attendance.getName());
				dtoMap.put(attendance.getCode(), dto);
			}
			wt=attendance.getGmtWork().getTime() - monthDay;
			if(wt<=half){
				if(dto.getWorkf()!=null){
					if(wt<dto.getWorkf().longValue()){
						dto.setWorkf(wt);
					}
				}else{
					dto.setWorkf(wt);
				}
			}else{
				if(dto.getWorkt()!=null){
					if(wt>dto.getWorkt().longValue()){
						dto.setWorkt(wt);
					}
				}else {
					dto.setWorkt(wt);
				}
			}
		}
		list=null;
		return dtoMap;
	}

	@Override
	public List<WorkDay> buildWorkday(Date month, Integer[] day, String[] workf,
			String[] workt) {
		List<WorkDay> list=new ArrayList<WorkDay>();
		int i=0;
		for(Integer dayofmonth:day){
			WorkDay wd=new WorkDay();
			Date d=DateUtil.getDateAfterDays(month, dayofmonth-1);
			wd.setDay(d.getTime());
			wd.setWorkf(parseWorkTime(workf[i])*1000l);
			wd.setWorkt(parseWorkTime(workt[i])*1000l);
			list.add(wd);
			i++;
		}
		return list;
	}
	
	private Long parseWorkTime(String wt){
		String[] wtArr=wt.split(":");
		return Long.valueOf(wtArr[0])*3600 + Long.valueOf(wtArr[1])*60;
	}

	@Override
	public List<WorkDay> buildworkDays(Date month) {
		Date nextMonth=DateUtil.getDateAfterMonths(month, 1);
		Date monthLastDay=DateUtil.getDateAfterDays(nextMonth, -1);
		
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(month);
		int firstDay=calendar.get(Calendar.DAY_OF_MONTH);
		calendar.setTime(monthLastDay);
		int lastDay=calendar.get(Calendar.DAY_OF_MONTH);
		
		List<WorkDay> list=new ArrayList<WorkDay>();
		
		for(int i=firstDay;i<=lastDay;i++){
			calendar.setTime(DateUtil.getDateAfterDays(month, i-1));
			WorkDay wd=new WorkDay();
			wd.setDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));
			wd.setDayOfMonth(i);
			wd.setUnixtime(calendar.getTimeInMillis());
			list.add(wd);
		}
		
		return list;
	}

	@Override
	public Map<Long, List<Date>> queryAttendData(String code, Date month, Integer scheduleId) {
		
		Date nextMonth=DateUtil.getDateAfterMonths(month, 1);

		List<Attendance> list=attendanceDao.queryAttendancesByWork(month, nextMonth, code, scheduleId);
		
		Map<Long, List<Date>> result=new HashMap<Long, List<Date>>();
		Date day=null;
		for(Attendance obj:list){
			
			try {
				day = DateUtil.getDate(obj.getGmtWork(), "yyyy-MM-dd 00:00:00");
			} catch (ParseException e) {
			}
			
			if(day==null){
				continue ;
			}
			
			if(result.get(day.getTime())==null){
				result.put(day.getTime(), new ArrayList<Date>());
			}
			result.get(day.getTime()).add(obj.getGmtWork());
			
			day=null;
		}
		
		return result;
	}

	public void analysisBySchedule(Date month, Integer scheduleId, List<AttendanceScheduleDetail> detailList) {
		
		attendanceAnalysisDao.deleteByGmtTarget(month, scheduleId);
		
		Map<String, AttendanceAnalysis> result=new HashMap<String, AttendanceAnalysis>();
		List<Attendance> list=null;
		
		//按天统计每天的数据
		for(AttendanceScheduleDetail wd:detailList){
			//得到某工作日数据
			 list = attendanceDao.queryAttendancesByWork(wd.getDay(), DateUtil.getDateAfterDays(wd.getDay(), 1), null, scheduleId);
			
			
			Map<String, AttendanceDto> dayList=buildDayRecord(list, (wd.getWorkf()+wd.getWorkt())/2, wd.getDay().getTime());
			
			for(String code:dayList.keySet()){
				AttendanceDto dto=dayList.get(code);
				AttendanceAnalysis analysisResult=result.get(code);
				if(analysisResult==null){
					analysisResult = new AttendanceAnalysis();
					analysisResult.setAccount(dto.getAccount());
					analysisResult.setName(dto.getName());
					analysisResult.setCode(code);
					analysisResult.setDayFull(BigDecimal.valueOf(detailList.size()));
					analysisResult.setDayUnwork(BigDecimal.valueOf(0));
					
					analysisResult.setDayActual(BigDecimal.valueOf(0));
					analysisResult.setDayUnrecord(0);
					analysisResult.setDayLate(0);
					analysisResult.setDayEarly(0);
					analysisResult.setDayOvertime(0);
					
					result.put(code, analysisResult); //?
				}
				
				analysisResult.setDayActual(BigDecimal.valueOf(analysisResult.getDayActual().doubleValue()+1));
				
				if(dto.getWorkf()!=null){
					if(dto.getWorkf()>wd.getWorkf()){
						if(dto.getWorkf()>(wd.getWorkf()+30*60*1000)){
							//旷工半天
							analysisResult.setDayUnwork(BigDecimal.valueOf(analysisResult.getDayUnwork().doubleValue()+.5d));
							//这里很可能是请假，可以注成请假
							
						}else{
							analysisResult.setDayLate(analysisResult.getDayLate()+1);
						}
					}
				}else{
					analysisResult.setDayUnrecord(analysisResult.getDayUnrecord()+1);
				}
				
				if(dto.getWorkt()!=null){
					if(dto.getWorkt()<wd.getWorkt()){
						analysisResult.setDayEarly(analysisResult.getDayEarly()+1);
						//下午有记录的可能是请假，不是早退
					}else{
						if(dto.getWorkt()>(20*60*60*1000)){
							analysisResult.setDayOvertime(analysisResult.getDayOvertime()+1);
						}
					}
				}else{
					analysisResult.setDayUnrecord(analysisResult.getDayUnrecord()+1);
				}
				
				//早上没有记录，下午没有记录＝矿工
			}
			
		}
		
		//保存统计结果
		for(String code:result.keySet()){
			AttendanceAnalysis analysisResult=result.get(code);
			analysisResult.setGmtTarget(month);
			analysisResult.setScheduleId(scheduleId);
			attendanceAnalysisDao.insert(analysisResult);
		}
	}
	
}
