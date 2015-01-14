/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-5
 */
package net.caiban.auth.dashboard.service.staff.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.caiban.auth.dashboard.dao.auth.AuthRightDao;
import net.caiban.auth.dashboard.dao.staff.DeptDao;
import net.caiban.auth.dashboard.domain.auth.AuthRight;
import net.caiban.auth.dashboard.domain.staff.Dept;
import net.caiban.auth.dashboard.dto.ExtTreeDto;
import net.caiban.auth.dashboard.service.staff.DeptService;

import org.springframework.stereotype.Component;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-5
 */
@Component("deptService")
public class DeptServiceImpl implements DeptService {

	@Resource
	private DeptDao deptDao;
	@Resource
	private AuthRightDao authRightDao;
	
	@Override
	public List<ExtTreeDto> queryDeptNode(String parentCode) {
		if(parentCode==null){
			parentCode="";
		}
		
		List<Dept> list=deptDao.queryChild(parentCode);
		List<ExtTreeDto> nodeList=new ArrayList<ExtTreeDto>();
		for(Dept dept:list){
			ExtTreeDto node=new ExtTreeDto();
			node.setId(String.valueOf(dept.getId()));
			node.setText(dept.getName());
			node.setData(dept.getCode());
			Integer i = deptDao.countChild(dept.getCode());
			if(i==null || i.intValue()<=0){
				node.setLeaf(true);
			}
			nodeList.add(node);
		}
		return nodeList;
	}

	@Override
	public List<ExtTreeDto> queryDeptRightNode(String parentCode, Integer deptId) {
		List<ExtTreeDto> nodeList = new ArrayList<ExtTreeDto>();
		if(deptId==null || deptId.intValue()<=0){
			return nodeList;
		}
		
		List<Integer> deptRight = deptDao.queryRightIdOfDept(deptId);
		List<AuthRight> rightList = authRightDao.queryChild(parentCode);
		
		if(rightList==null){
			return nodeList;
		}
		
		for(AuthRight right:rightList){
			ExtTreeDto node = new ExtTreeDto();
			node.setId(String.valueOf(right.getId()));
			node.setData(right.getCode());
			node.setText(right.getName());
			Integer i = authRightDao.countChild(right.getCode());
			if(i==null || i.intValue()<=0){
				node.setLeaf(true);
			}
			if(deptRight.contains(right.getId())){
				node.setChecked(true);
			}else{
				node.setChecked(false);
			}
			nodeList.add(node);
		}
		return nodeList;
	}

	@Override
	public Integer updateDeptRight(Integer deptId, Integer rightId,
			Boolean checked) {
		if(checked==null){
			checked=false;
		}
		
		Integer i=0;
		if(checked){
			i=deptDao.insertDeptRight(deptId, rightId);
		}else{
			i=deptDao.deleteDeptRight(deptId, rightId);
		}
		
		return i;
	}

	@Override
	public Integer createDept(Dept dept, String parentCode) {
		if(parentCode==null){
			parentCode="";
		}
		
		String code=deptDao.queryMaxCodeOfChild(parentCode);
		if(code!=null && code.length()>0){
			code = code.substring(parentCode.length());
			Integer codeInt=Integer.valueOf(code);
			codeInt++;
			dept.setCode(parentCode+String.valueOf(codeInt));
		}else{
			dept.setCode(parentCode+"1000");
		}
		
		return deptDao.insertDept(dept);
	}

	@Override
	public Integer deleteDept(String code) {
		//删除业务系统关联
		//删除权限关联
		deptDao.deleteRightByDept(code);
		deptDao.deleteDeptBs(code);
		return deptDao.deleteDept(code);
	}

	@Override
	public Dept queryOneDept(String code) {
		
		return deptDao.queryOneDept(code);
	}

	@Override
	public Integer updateDept(Dept dept) {
		return deptDao.updateDept(dept);
	}

}
