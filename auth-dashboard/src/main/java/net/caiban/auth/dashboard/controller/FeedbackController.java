/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-7-9
 */
package net.caiban.auth.dashboard.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.caiban.auth.dashboard.domain.staff.Feedback;
import net.caiban.auth.dashboard.dto.ExtResult;
import net.caiban.auth.dashboard.dto.PageDto;
import net.caiban.auth.dashboard.service.staff.FeedbackService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-7-9
 */
@Controller
public class FeedbackController extends BaseController{

	@Resource
	private FeedbackService feedbackService;
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView queryFeedback(HttpServletRequest request, Map<String, Object> out, Integer status, PageDto<Feedback> page){
		page.setSort("id");
		page.setDir("desc");
		page = feedbackService.pageFeedback(status, page);
		
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView dealSuccess(HttpServletRequest request, Map<String, Object> out, Integer id){
		Integer i = feedbackService.dealSuccess(id);
		ExtResult result = new ExtResult();
		if(i!=null & i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView dealNothing(HttpServletRequest request, Map<String, Object> out, Integer id){
		Integer i = feedbackService.dealNothing(id);
		ExtResult result = new ExtResult();
		if(i!=null & i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView dealImpossible(HttpServletRequest request, Map<String, Object> out, Integer id){
		Integer i = feedbackService.dealImpossible(id);
		ExtResult result = new ExtResult();
		if(i!=null & i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView deleteFeedback(HttpServletRequest request, Map<String, Object> out, Integer id){
		Integer i = feedbackService.deleteFeedback(id);
		ExtResult result = new ExtResult();
		if(i!=null & i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
}
