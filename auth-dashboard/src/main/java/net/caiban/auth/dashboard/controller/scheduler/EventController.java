/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-7-5
 */
package net.caiban.auth.dashboard.controller.scheduler;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.caiban.auth.dashboard.controller.BaseController;
import net.caiban.auth.dashboard.domain.staff.SchedulerEvent;
import net.caiban.auth.dashboard.dto.ExtResult;
import net.caiban.auth.dashboard.service.staff.schedulerEventService;
import net.caiban.auth.sdk.AuthClient;
import net.caiban.auth.sdk.SessionUser;
import net.caiban.utils.DateUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Strings;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-7-5
 */
@Controller
public class EventController extends BaseController{

	@Resource
	private schedulerEventService schedulerEventService;
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out, String type){
		Map<String, String> map=AuthClient.getInstance().queryStaffOfDept(getCachedUser(request).getDeptCode());
		out.put("deptMember", map);
		out.put("user", getCachedUser(request));
		out.put("type", type);
		return null;
	}
	
	final static String DATE_FORMATE="yyyy-MM-dd";
	final static String DATE_TIME_FORMATE="yyyy-MM-dd HH:mm:ss";
	
	@RequestMapping
	public ModelAndView queryEvent(HttpServletRequest request, Map<String, Object> out, String from, String to, String ownerAccount, String deptCode){

		Date fromDate=null;
		Date toDate=null;
		try {
			if (!Strings.isNullOrEmpty(from)){
				fromDate = DateUtil.getDate(from, DATE_FORMATE);
			}
			if (!Strings.isNullOrEmpty(to)){
				toDate = DateUtil.getDate(to, DATE_FORMATE);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		List<SchedulerEvent> list=schedulerEventService.queryEvent(fromDate, toDate, ownerAccount, deptCode);
		
		Map<String, String> staffMap=new HashMap<String, String>();
		List<Map<String, Object>> resultList=new ArrayList<Map<String, Object>>();
		SessionUser sessionUser = getCachedUser(request);
		for(SchedulerEvent event:list){
			Map<String, Object> e=new HashMap<String, Object>();
			e.put("id", event.getId());
			e.put("start_date", DateUtil.toString(event.getStartDate(), DATE_TIME_FORMATE));
			e.put("end_date", DateUtil.toString(event.getEndDate(), DATE_TIME_FORMATE));
			e.put("text", event.getText());
			e.put("details", event.getDetails());
			String staffName=staffMap.get(event.getOwnerAccount());
			if(staffName==null){
				staffMap.put(event.getOwnerAccount(), AuthClient.getInstance().queryStaffNameOfAccount(event.getOwnerAccount()));
				staffName=staffMap.get(event.getOwnerAccount());
			}
			String assignStaffName=staffMap.get(event.getAssignAccount());
			if(assignStaffName==null){
				staffMap.put(event.getAssignAccount(), AuthClient.getInstance().queryStaffNameOfAccount(event.getAssignAccount()));
				assignStaffName=staffMap.get(event.getAssignAccount());
			}
			e.put("staff", event.getOwnerAccount());
			e.put("staffName", staffName);
			e.put("assignStaff", event.getAssignAccount());
			e.put("assignStaffName", assignStaffName);
			if(!sessionUser.getAccount().equals(event.getOwnerAccount())
					&& !sessionUser.getAccount().equals(event.getAssignAccount())	){
				e.put("readonly", true);
			}
			resultList.add(e);
		}
		
//		list.add("{id:1,staff:'def',start_date:'2011-07-06 14:50:00',end_date:'2011-07-06 18:50:00',text:'这是一个测试server的event',details:'here is a details text'}");
//		list.add("{id:5,staff:'abc',start_date:'2011-07-06 8:00:00',end_date:'2011-07-06 15:30:00',text:'这是一个测试 8点钟',details:'here is a details text'}");
		return printJson(resultList, out);
	}
	
	@RequestMapping
	public ModelAndView eventChanged(HttpServletRequest request, Map<String, Object> out, SchedulerEvent event, String start, String end){
		SessionUser sessionUser=getCachedUser(request);
		
		try {
			event.setStartDate(DateUtil.getDate(start, DATE_TIME_FORMATE));
			event.setEndDate(DateUtil.getDate(end, DATE_TIME_FORMATE));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if(Strings.isNullOrEmpty(event.getOwnerAccount())){
			event.setOwnerAccount(sessionUser.getAccount());
		}
		event.setDeptCode(sessionUser.getDeptCode());
		
		Integer i=schedulerEventService.reassignEvent(event);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView eventAdded(HttpServletRequest request, Map<String, Object> out, SchedulerEvent event, String start, String end){
		SessionUser sessionUser=getCachedUser(request);
		
		try {
			event.setStartDate(DateUtil.getDate(start, DATE_TIME_FORMATE));
			event.setEndDate(DateUtil.getDate(end, DATE_TIME_FORMATE));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		event.setAssignAccount(sessionUser.getAccount());
		if(Strings.isNullOrEmpty(event.getOwnerAccount())){
			event.setOwnerAccount(sessionUser.getAccount());
		}
		event.setDeptCode(sessionUser.getDeptCode());
		
		Integer i=schedulerEventService.assignEvent(event);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
			result.setData(i);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView eventDeleted(HttpServletRequest request, Map<String, Object> out, Integer id){
		Integer i=schedulerEventService.deleteEvent(id);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}		
		return printJson(result, out);
	}
	
	@RequestMapping
	private ModelAndView query(HttpServletRequest requet,Map<String, Object> out){
		
		List<SchedulerEvent> list=schedulerEventService.query();
		
		return printJson(list, out);
	}
	
}
