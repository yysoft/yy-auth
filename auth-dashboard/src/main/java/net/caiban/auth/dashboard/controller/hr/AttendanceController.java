package net.caiban.auth.dashboard.controller.hr;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.caiban.auth.dashboard.controller.BaseController;
import net.caiban.auth.dashboard.domain.hr.Attendance;
import net.caiban.auth.dashboard.domain.hr.AttendanceSchedule;
import net.caiban.auth.dashboard.domain.hr.AttendanceScheduleDetailSearch;
import net.caiban.auth.dashboard.dto.PageDto;
import net.caiban.auth.dashboard.dto.hr.AttendanceScheduleDetailDto;
import net.caiban.auth.dashboard.service.hr.AttendanceScheduleDetailService;
import net.caiban.auth.dashboard.service.hr.AttendanceScheduleService;
import net.caiban.auth.dashboard.service.hr.AttendanceService;
import net.caiban.auth.dashboard.util.DesktopConst;
import net.caiban.utils.DateUtil;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Strings;

@Controller
public class AttendanceController extends BaseController {

	@Resource
	private AttendanceService attendanceService;
	@Resource
	private AttendanceScheduleService attendanceScheduleService;
	@Resource
	private AttendanceScheduleDetailService attendanceScheduleDetailService;

	@RequestMapping
	public void index(HttpServletRequest request,Map<String, Object> out, String code, Date from) {
		out.put("code", code);
		if(from==null){
			from=DateUtil.getNowMonthFirstDay();
		}
		out.put("from", DateUtil.toString(from,"yyyy-MM-dd HH:mm:ss"));
		out.put("to", DateUtil.toString(DateUtil.getDateAfterMonths(from, 1),"yyyy-MM-dd HH:mm:ss"));
		
		List<AttendanceSchedule> list=attendanceScheduleService.queryScheduleOnly(DesktopConst.ISUSE_TRUE);
		Map<String, String> m=new HashMap<String, String>();
		for(AttendanceSchedule schedule:list){
			m.put("s"+schedule.getId(), schedule.getName());
		}
		out.put("schedule", JSONObject.fromObject(m).toString());
	}

	@RequestMapping
	public ModelAndView query(String name, String code, Date from, Date to,
			PageDto<Attendance> page ,Map<String, Object> out) {
		page = attendanceService.pageAttendance(name, code, from, to, page);
		return printJson(page, out);
	}

	@RequestMapping
	public ModelAndView impt(HttpServletRequest request, Map<String, Object> out, Integer error, Date from, Date to) {
		out.put("error", error);
		if(from==null && to==null){
			from=DateUtil.getNowMonthFirstDay();
			to=DateUtil.getDateAfterMonths(from, 1);
			to=DateUtil.getDateAfterDays(to, -1);
		}
		
		out.put("from", DateUtil.toString(from, DATE_FORMAT));
		out.put("to", DateUtil.toString(to, DATE_FORMAT));
		
		out.put("schedule", attendanceScheduleService.queryScheduleOnly(DesktopConst.ISUSE_TRUE));
		
		return null;
	}

	final static String DATE_FORMAT="yyyy-MM-dd";
	@RequestMapping
	public ModelAndView doImpt(HttpServletRequest request, Map<String, Object> out,
			String from, String to, String dateFormat, Integer scheduleId) {
		
		int error=0;
		do {
			if(Strings.isNullOrEmpty(from)){
				error=1;
				break;
			}
			if(Strings.isNullOrEmpty(to)){
				error=1;
				break;
			}
			Date fromDate=null;
			Date toDate=null;
			
			try {
				fromDate=DateUtil.getDate(from, DATE_FORMAT);
				toDate=DateUtil.getDate(to, DATE_FORMAT);
			} catch (ParseException e) {
				error=1;
				break;
			}
			
			MultipartRequest multipartRequest = (MultipartRequest)request;
			MultipartFile file = multipartRequest.getFile("uploadfile");
			if(file.getOriginalFilename()!=null &&
					!file.getOriginalFilename().equals("")){
				try {
					attendanceService.impt(fromDate, toDate,
							file.getInputStream(), dateFormat, scheduleId);
				} catch (IOException e) {
				}
			}
			
			out.put("from", DateUtil.toString(fromDate, DesktopConst.DATE_TIME_FORMAT));
			out.put("to", DateUtil.toString(toDate, DesktopConst.DATE_TIME_FORMAT));
		} while (false);
		
		out.put("error", error);
		
		return new ModelAndView("redirect:impt.htm");
	}
	
	/**
	 * @param request
	 * @param out
	 * @param targetMonth
	 * @return
	 */
	@RequestMapping
	public ModelAndView analysis(HttpServletRequest request, Map<String, Object> out, String targetMonth){
		
		Date targetMonthDate=null;
		try {
			if(!Strings.isNullOrEmpty(targetMonth)){
				targetMonthDate =  DateUtil.getDate(targetMonth, "yyyy-MM");
			}else {
				targetMonthDate = DateUtil.getDate(new Date(), "yyyy-MM");
				targetMonth=DateUtil.toString(targetMonthDate, "yyyy-MM");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		out.put("targetMonth", targetMonth);
		
//		List<WorkDay> list=attendanceService.buildworkDays(targetMonthDate);
//		out.put("monthDay", list);
//		
//		out.put("workf", "8:30");
//		
//		Calendar d=Calendar.getInstance();
//		d.setTime(targetMonthDate);
//		int m=d.get(Calendar.MONTH);
//		
//		if(m<9 && m>3){
//			out.put("workt", "18:00");
//		}else {
//			out.put("workt", "17:30");
//		}
//		
//		out.put("weekName", new String[]{ "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" });
		
		return null;
	}
	
//	public static void main(String[] args) throws ParseException {
//		String targetMonth="2012-08";
//		Calendar d=Calendar.getInstance();
//		d.setTime(DateUtil.getDate(targetMonth, "yyyy-MM"));
//		System.out.println(d.get(Calendar.MONTH));
//		System.out.println(d.get(Calendar.DAY_OF_WEEK));
//	}
//	
	@RequestMapping
	public ModelAndView doAnalysis(HttpServletRequest request, Map<String, Object> out,
			String targetMonth){
		
		Date targetMonthDate=null;
		try {
			targetMonthDate = DateUtil.getDate(targetMonth, "yyyy-MM");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
//		List<WorkDay> list = attendanceService.buildWorkday(targetMonthDate, 
//				StringUtils.StringToIntegerArray(dayArr), workfArr.split(","), worktArr.split(","));
		
		attendanceService.analysis(targetMonthDate);
		
		return new ModelAndView("redirect:analysis.htm?targetMonth="+targetMonth);
	}
	
	@RequestMapping
	public ModelAndView viewAttendance(HttpServletRequest request, 
			Map<String, Object> out, String targetMonth, String code, 
			Integer scheduleId){
		
		Date targetMonthDate=null;
		try {
			if(!Strings.isNullOrEmpty(targetMonth)){
				targetMonthDate =  DateUtil.getDate(targetMonth, "yyyy-MM");
			}else {
				targetMonthDate = DateUtil.getDate(new Date(), "yyyy-MM");
				targetMonth=DateUtil.toString(targetMonthDate, "yyyy-MM");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		out.put("targetMonth", targetMonth);
		
		AttendanceScheduleDetailSearch search = new AttendanceScheduleDetailSearch();
		search.setGmtMonth(targetMonthDate);
		search.setScheduleId(scheduleId);
		
		List< AttendanceScheduleDetailDto> list = attendanceScheduleDetailService.buildInitSchedule(search);
		out.put("list", list);
		
		out.put("scheduleName", attendanceScheduleService.queryName(search.getScheduleId()));
		
		out.put("workf", "8:30");
		
		Calendar d=Calendar.getInstance();
		d.setTime(search.getGmtMonth());
		int m=d.get(Calendar.MONTH);
		
		if(m<9 && m>3){
			out.put("workt", "18:00");
		}else {
			out.put("workt", "17:30");
		}
		
		out.put("weekName", new String[]{ "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" });
		
		out.put("scheduleId", search.getScheduleId());
		
		//获取相关数据
		Map<Long, List<Date>> targetAttendance=attendanceService.queryAttendData(code, targetMonthDate, scheduleId);
		
		out.put("userAttendance", targetAttendance);
		
		return null;
	}
}
