/**
 * 
 */
package net.caiban.auth.dashboard.service.staff.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.caiban.auth.dashboard.dao.auth.AuthRightDao;
import net.caiban.auth.dashboard.dao.auth.AuthUserDao;
import net.caiban.auth.dashboard.dao.bs.BsDao;
import net.caiban.auth.dashboard.dao.staff.DeptDao;
import net.caiban.auth.dashboard.dao.staff.StaffDao;
import net.caiban.auth.dashboard.domain.auth.AuthRight;
import net.caiban.auth.dashboard.domain.auth.AuthRole;
import net.caiban.auth.dashboard.domain.bs.Bs;
import net.caiban.auth.dashboard.domain.staff.Dept;
import net.caiban.auth.dashboard.domain.staff.Staff;
import net.caiban.auth.dashboard.dto.PageDto;
import net.caiban.auth.dashboard.dto.staff.StaffDto;
import net.caiban.auth.dashboard.service.staff.StaffService;
import net.caiban.auth.sdk.AuthMenu;
import net.caiban.auth.sdk.SessionUser;
import net.caiban.utils.MD5;
import net.caiban.utils.lang.StringUtils;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

/**
 * @author root
 *
 */
@Component("staffService")
public class StaffServiceImpl implements StaffService {

	@Resource
	private StaffDao staffDao;
	@Resource
	private AuthUserDao authUserDao;
	@Resource
	private BsDao bsDao;
	@Resource
	private DeptDao deptDao;
	@Resource
	private AuthRightDao authRightDao;
	
	@Override
	public Integer createStaff(Staff staff, String account, String password,
			Integer[] roleArray) {
		//验证
		Integer i = authUserDao.countUser(account, password);
		if(i!=null && i.intValue()>0){
			return null;
		}
		
		Integer j = staffDao.countStaff(staff.getStaffNo());
		if(j!=null && j.intValue()>0){
			return null;
		}
		
		//step1 创建用户账户
		Integer userId=null;
		try {
			userId = authUserDao.insertUser(account, staff.getEmail(), MD5.encode(password), 1);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(userId==null){
			return null;
		}
		//step2 创建员工信息（必需）
		staff.setStatus("0");
		if(staff.getGmtEntry()==null){
			staff.setGmtEntry(new Date());
		}
		Integer staffId=staffDao.insertStaff(staff);
		//step3 创建角色关系
		authUserDao.insertUserRole(userId, roleArray);
		authUserDao.updateSteping(userId, 0);
		return staffId;
	}

	@Override
	public SessionUser initSessionUser(String account, String projectCode) {
		SessionUser sessionUser = null;
		do {
			if(Strings.isNullOrEmpty(account) || Strings.isNullOrEmpty(projectCode)){
				break;
			}
			
			Staff staff = staffDao.queryStaffByAccount(account);
			if(staff == null){
				break;
			}
			
			String[] rightArr = queryRightOfStaff(account, projectCode);
			if(rightArr==null){
				break;
			}
			
			sessionUser = new SessionUser();
			sessionUser.setAccount(staff.getAccount());
			sessionUser.setDeptCode(staff.getDeptCode());
			sessionUser.setName(staff.getName());
			sessionUser.setRightArr(rightArr);
			sessionUser.setStaffNo(staff.getStaffNo());
			
		} while (false);
		
		return sessionUser;
	}

	@Override
	public PageDto<StaffDto> pageStaff(Staff staff, PageDto<StaffDto> page) {
		if(staff.getStatus()==null){
			staff.setStatus("1");
		}
		page.setRecords(staffDao.queryStaff(staff, page));
		page.setTotalRecords(staffDao.queryStaffCount(staff));
		return page;
	}

	@Override
	public String[] queryRightOfStaff(String account, String projectCode) {
		//权限包含两部分，部门的权限和角色的权限，并集
		String parentCode = bsDao.queryRightCodeOfBs(projectCode);
		Integer deptId = deptDao.queryDeptIdByAccount(account);
		List<AuthRight> deptRight = deptDao.queryRightOfDept(parentCode, deptId);
		List<AuthRight> roleRight = authUserDao.queryRightOfUser(parentCode, account);
		//取并集->array
		Set<String> set = new HashSet<String>();
		for(AuthRight right:deptRight) {
			if(!Strings.isNullOrEmpty(right.getContent())){
				CollectionUtils.addAll(set, right.getContent().split("\\|"));
			}
		}
		for(AuthRight right:roleRight){
			if(!Strings.isNullOrEmpty(right.getContent())){
				CollectionUtils.addAll(set, right.getContent().split("\\|"));
			}
		}
		String[] rightArr = {};
		rightArr = set.toArray(rightArr);
		return rightArr;
	}
	
	@Override
	public Integer staffTurnover(Integer staffId, String note, Date gmtLeft) {
		return staffDao.updateStaffStatus(staffId, 2, note, gmtLeft);
	}
	
	@Override
	public Integer regular(Integer staffId, String note, Date gmtLeft) {
		return staffDao.updateStaffStatus(staffId, 1, note, gmtLeft);
	}

	@Override
	public String validateUser(String account, String password,
			String projectCode) {
		do {
			if(Strings.isNullOrEmpty(account) || Strings.isNullOrEmpty(password)){
				break;
			}
			Integer[] status={0,1};  //0：试用期，1：转正，2：离职
			Integer sc = staffDao.countStaffByStatus(account, status);
			if(sc==null || sc.intValue()<=0){
				break;
			}
			
			Integer uc = authUserDao.countUser(account, password);
			if(uc==null || uc.intValue()<=0){
				break;
			}
			
			Integer bsc = bsDao.countBsOfStaff(staffDao.queryStaffIdByAccount(account), projectCode);
			Integer bdc = bsDao.countBsOfDept(deptDao.queryDeptIdByAccount(account), projectCode);
			
			if(bsc==null || bdc==null || (bsc.intValue()+bdc.intValue())<=0){
				break;
			}
			
			return account;
			
		} while (false);
		return null;
	}
	
	@Override
	public Integer queryStaffIdByAccount(String account){
		return staffDao.queryStaffIdByAccount(account);
	}
	
	@Override
	public List<AuthMenu> queryMenuOfStaff(String account, String parentCode){
		Integer deptId = deptDao.queryDeptIdByAccount(account);
		List<AuthRight> deptRight = deptDao.queryChildRightOfDept(parentCode, deptId);
		List<AuthRight> roleRight = authUserDao.queryChildRightOfUser(parentCode, account);
		
		Map<Integer, AuthRight> rightMap = new LinkedHashMap<Integer, AuthRight>();
		for(AuthRight right: deptRight){
			rightMap.put(right.getId(), right);
		}
		for(AuthRight right: roleRight){
			if(rightMap.get(right.getId())==null){
				rightMap.put(right.getId(), right);
			}
		}
		
		List<AuthMenu> menuList = new ArrayList<AuthMenu>();
		for(Integer key:rightMap.keySet()){
			AuthRight right = rightMap.get(key);
			if(!Strings.isNullOrEmpty(right.getMenu())){
				AuthMenu menu = new AuthMenu();
				menu.setData(right.getCode());
				menu.setText(right.getMenu());
				menu.setUrl(right.getMenuUrl());
				Integer i = authRightDao.countChild(right.getCode());
				if(i==null || i.intValue()<=0){
					menu.setLeaf(true);
				}
				menuList.add(menu);
			}
		}
		
		return menuList;
	}

	@Override
	public Integer updateStaff(Staff staff, String password, String roleArr) {
		if(!Strings.isNullOrEmpty(password)){
			try {
				authUserDao.updatePassword(MD5.encode(password), staff.getAccount());
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		Integer userId=authUserDao.queryUserIdByAccount(staff.getAccount());
		authUserDao.deleteUserRole(userId);
		if(!Strings.isNullOrEmpty(roleArr)){
			authUserDao.insertUserRole(userId, StringUtils.StringToIntegerArray(roleArr));
		}
		
		return staffDao.updateStaff(staff);
	}

	@Override
	public List<Bs> queryBsByStaffId(Integer staffId) {
		return staffDao.queryBsByStaffId(staffId);
	}

	@Override
	public List<Bs> queryDeptBsByDeptCode(String deptCode) {
		return staffDao.queryDeptBsByDeptCode(deptCode);
	}

	@Override
	public StaffDto queryStaffByAccount(String account) {
		Staff staff = staffDao.queryStaffByAccount(account);
		Dept dept = deptDao.queryOneDept(staff.getDeptCode());
		List<AuthRole> roleList = authUserDao.queryRoleOfUser(account);
		StaffDto staffDto = new StaffDto();
		staffDto.setStaff(staff);
		staffDto.setRole("");
		staffDto.setRoleArr("");
		staffDto.setDeptName(dept.getName());
		int i=0;
		for(AuthRole role:roleList){
			if(i>0){
				staffDto.setRole(staffDto.getRole()+",");
				staffDto.setRoleArr(staffDto.getRoleArr()+",");
			}
			staffDto.setRole(staffDto.getRole()+role.getName());
			staffDto.setRoleArr(staffDto.getRoleArr()+role.getId());
			i++;
		}
		return staffDto;
	}

	@Override
	public Integer changePassword(String account, String op, String np, String vp) {
		if(Strings.isNullOrEmpty(op) || Strings.isNullOrEmpty(np)||Strings.isNullOrEmpty(vp) || !np.equals(vp)){
			return null;
		}
		try {
			String md5Op=MD5.encode(op);
			String md5Np=MD5.encode(np);
			Integer i = authUserDao.countUser(account, md5Op);
			if(i!=null && i.intValue()>0){
				return authUserDao.updatePassword(md5Np, account);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String queryNameOfAccount(String account) {
		if(Strings.isNullOrEmpty(account)){
			return "";
		}
		return staffDao.queryNameByAccount(account);
	}

	@Override
	public Map<String, String> queryStaffNameByDeptCode(String deptCode) {
		Map<String, String> map=new HashMap<String, String>();
		if(Strings.isNullOrEmpty(deptCode)){
			return map;
		}
		List<Staff> list=staffDao.queryStaffNameByDeptCode(deptCode);
		for(Staff staff:list){
			map.put(staff.getAccount(), staff.getName());
		}
		return map;
	}

	@Override
	public List<Staff> queryStaffByDeptCode(String deptCode) {
		return staffDao.queryStaffNameByDeptCode(deptCode);
	}
}
