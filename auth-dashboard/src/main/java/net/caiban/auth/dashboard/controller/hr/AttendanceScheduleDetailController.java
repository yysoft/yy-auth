package net.caiban.auth.dashboard.controller.hr;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.caiban.auth.dashboard.controller.BaseController;
import net.caiban.auth.dashboard.domain.hr.AttendanceScheduleDetailSearch;
import net.caiban.auth.dashboard.dto.hr.AttendanceScheduleDetailDto;
import net.caiban.auth.dashboard.service.hr.AttendanceScheduleDetailService;
import net.caiban.auth.dashboard.service.hr.AttendanceScheduleService;
import net.caiban.auth.dashboard.util.DesktopConst;
import net.caiban.utils.DateUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AttendanceScheduleDetailController extends BaseController {

	@Resource
	private AttendanceScheduleDetailService attendanceScheduleDetailService;
	@Resource
	private AttendanceScheduleService attendanceScheduleService;

	@RequestMapping
	public void index(HttpServletRequest request,Map<String, Object> out) {
	}

	@RequestMapping
	public ModelAndView show(HttpServletRequest request, Map<String, Object> out, 
			AttendanceScheduleDetailSearch search, Integer year, Integer month,
			String action) 
			throws ParseException {
		
		if(year==null || month==null){
			Calendar.getInstance().setTime(new Date());
			year=Calendar.getInstance().get(Calendar.YEAR);
			month=Calendar.getInstance().get(Calendar.MONTH);
		}
		
		out.put("year", year);
		out.put("month", month);
		
		
		Calendar c=Calendar.getInstance();
		c.set(year, month, 1, 0, 0, 0);
		
		search.setGmtMonth(c.getTime());
		
		out.put("targetMonth", DateUtil.toString(c.getTime(), DesktopConst.DATE_TIME_FORMAT));
		
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
		
		out.put("action", action);
		
		out.put("scheduleId", search.getScheduleId());
		
		return null;
	}
	

	@Deprecated
	@RequestMapping
	public ModelAndView saveDetail(HttpServletRequest request, Map<String, Object> out, 
			String dayArr, String workfArr, String worktArr,
			Date targetMonth, Integer scheduleId) {
		
//		SessionUser user=getCachedUser(request);
		
//		attendanceScheduleDetailService.buildSchedule(scheduleId, targetMonth, 
//				StringUtils.StringToIntegerArray(dayArr), workfArr.split(","), 
//				worktArr.split(","), user.getAccount());
		
		
		Calendar.getInstance().setTime(targetMonth);
		Integer year=Calendar.getInstance().get(Calendar.YEAR);
		Integer month=Calendar.getInstance().get(Calendar.MONTH);
		
		out.put("scheduleId", scheduleId);
		out.put("month", month);
		out.put("year", year);
		out.put("action", "update");
		
		return new ModelAndView("redirect:show.htm");
	}

}
