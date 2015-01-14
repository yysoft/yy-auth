/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-9
 */
package net.caiban.auth.dashboard.controller.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.caiban.auth.dashboard.controller.BaseController;
import net.caiban.auth.dashboard.domain.auth.AuthRight;
import net.caiban.auth.dashboard.dto.ExtResult;
import net.caiban.auth.dashboard.dto.ExtTreeDto;
import net.caiban.auth.dashboard.dto.PageDto;
import net.caiban.auth.dashboard.service.auth.AuthRightService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * import org.springframework.stereotype.Controller;
@author mays (mays@zz91.com)
 *
 * created on 2011-5-9
 */
@Controller
public class RightController extends BaseController{

	@Resource
	private AuthRightService authRightService;
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView child(HttpServletRequest request, Map<String, Object> out, String parentCode){
		if(parentCode==null){
			parentCode="";
		}
		List<ExtTreeDto> treenode= authRightService.queryTreeNode(parentCode);
		return printJson(treenode, out);
	}
	
	@RequestMapping
	public ModelAndView deleteRight(HttpServletRequest request, Map<String, Object> out, String code){
		ExtResult result = new ExtResult();
		Integer i = authRightService.deleteRightByCode(code);
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping
	public ModelAndView queryOneRight(HttpServletRequest request, Map<String, Object> out, String code){
		AuthRight right = authRightService.queryOneRight(code);
		List<AuthRight> list=new ArrayList<AuthRight>();
		list.add(right);
		PageDto page=new PageDto();
		page.setRecords(list);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView createRight(HttpServletRequest request, Map<String, Object> out, AuthRight right, String parentCode){
		Integer i = authRightService.createRight(right, parentCode);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateRight(HttpServletRequest request, Map<String, Object> out, AuthRight right){
		Integer i=authRightService.updateRight(right);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
}
