/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-9
 */
package net.caiban.auth.dashboard.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.caiban.auth.dashboard.domain.staff.Dept;
import net.caiban.auth.dashboard.dto.ExtResult;
import net.caiban.auth.dashboard.dto.ExtTreeDto;
import net.caiban.auth.dashboard.dto.PageDto;
import net.caiban.auth.dashboard.service.staff.DeptService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-9
 */
@Controller
public class DeptController extends BaseController {
	
	@Resource
	private DeptService deptService;

	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView deptChild(HttpServletRequest request, Map<String, Object> out, String parentCode){
		List<ExtTreeDto> deptNode = deptService.queryDeptNode(parentCode);
		return printJson(deptNode, out);
	}
	
	@RequestMapping
	public ModelAndView rigthChild(HttpServletRequest request, Map<String, Object> out, String parentCode, Integer deptId){
		List<ExtTreeDto> rightNode = deptService.queryDeptRightNode(parentCode, deptId);
		return printJson(rightNode, out);
	}
	
	@RequestMapping
	public ModelAndView updateDeptRight(HttpServletRequest request, Map<String, Object> out, Integer deptId, Integer rightId, Boolean checked){
		Integer i = deptService.updateDeptRight(deptId, rightId, checked);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView deleteDept(HttpServletRequest request, Map<String, Object> out, String code){
		Integer i=deptService.deleteDept(code);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView createDept(HttpServletRequest request, Map<String, Object> out, Dept dept, String parentCode){
		Integer i=deptService.createDept(dept, parentCode);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateDept(HttpServletRequest request, Map<String, Object> out, Dept dept){
		Integer i=deptService.updateDept(dept);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView queryOneDept(HttpServletRequest request, Map<String, Object> out, String code){
		Dept dept=deptService.queryOneDept(code);
		PageDto<Dept> page=new PageDto<Dept>();
		List<Dept> list=new ArrayList<Dept>();
		list.add(dept);
		page.setRecords(list);
		return printJson(page, out);
	}

}
