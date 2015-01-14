/**
 * 
 */
package net.caiban.auth.dashboard.service.bs.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.caiban.auth.dashboard.dao.auth.AuthRightDao;
import net.caiban.auth.dashboard.dao.bs.BsDao;
import net.caiban.auth.dashboard.dao.staff.DeptDao;
import net.caiban.auth.dashboard.dao.staff.StaffDao;
import net.caiban.auth.dashboard.domain.bs.Bs;
import net.caiban.auth.dashboard.domain.staff.Dept;
import net.caiban.auth.dashboard.domain.staff.Staff;
import net.caiban.auth.dashboard.dto.ExtTreeDto;
import net.caiban.auth.dashboard.dto.PageDto;
import net.caiban.auth.dashboard.dto.bs.BsDto;
import net.caiban.auth.dashboard.dto.staff.StaffDto;
import net.caiban.auth.dashboard.service.bs.BsService;

import org.springframework.stereotype.Component;

import com.zz91.util.lang.StringUtils;

/**
 * @author root
 * 
 */
@Component("bsService")
public class BsServiceImpl implements BsService {
	@Resource
	private BsDao bsDao;
	@Resource
	private DeptDao deptDao;
	@Resource
	private StaffDao staffDao;
	@Resource
	private AuthRightDao authRightDao;
	
	@Override
	public List<Bs> queryBsOfDept(Integer deptId, String types) {
		return bsDao.queryBsOfDept(deptId, types);
	}

	@Override
	public List<Bs> queryBsOfStaff(Integer staffId, String types) {
		return bsDao.queryBsOfStaff(staffId, types);
	}
	
	
	@Override
	public List<Bs> queryMyBs(String account, String types){
		Integer deptId=deptDao.queryDeptIdByAccount(account);
		Integer staffId = staffDao.queryStaffIdByAccount(account);
		
		List<Bs> deptBs=bsDao.queryBsOfDept(deptId, types);
		List<Bs> staffBs=bsDao.queryBsOfStaff(staffId, types);
		
		Set<Integer> bsidSet = new HashSet<Integer>();
		for(Bs bs: deptBs){
			bsidSet.add(bs.getId());
		}
		
		for(Bs bs:staffBs){
			if(!bsidSet.contains(bs.getId())){
				deptBs.add(bs);
			}
		}
		return deptBs;
	}
	
	@Override
	public String queryPasswordOfBs(String pcode){
		
		return bsDao.queryPasswordOfBs(pcode);
	}
	
	
	@Override
	public String queryRightCodeOfProject(String projectCode){
		return bsDao.queryRightCodeOfBs(projectCode);
	}

	@Override
	public Integer createBs(Bs bs) {
		if(StringUtils.isEmpty(bs.getAvatar())){
			bs.setAvatar("/themes/images/no_image.gif");
		}
		return bsDao.insertBs(bs);
	}

	@Override
	public Integer deleteBs(Integer id) {
		bsDao.deleteBsDept(id);
		bsDao.deleteBsStaff(id);
		return bsDao.deleteBs(id);
	}

	@Override
	public PageDto<Bs> pageBs(Bs bs, PageDto<Bs> page) {
		page.setRecords(bsDao.queryBs(bs, page));
		page.setTotalRecords(bsDao.queryBsCount(bs));
		return page;
	}

	@Override
	public Integer updateBs(Bs bs) {
		return bsDao.updateBs(bs);
	}

	@Override
	public BsDto queryOneBs(Integer id) {
		Bs bs=bsDao.queryOneBs(id);
		BsDto bsdto = new BsDto();
		bsdto.setBs(bs);
		bsdto.setRightName(authRightDao.queryNameByCode(bs.getRightCode()));
		return bsdto;
	}

	@Override
	public List<Staff> queryStaffByBs(Integer id, String types) {
		return bsDao.queryStaffByBs(id, types);
	}

	@Override
	public List<Dept> queryDeptByBs(Integer id, String types) {
		return bsDao.queryDeptByBs(id, types);
	}

	@Override
	public List<ExtTreeDto> queryDeptTreeNode(String parentCode, Integer bsId) {
		List<ExtTreeDto> nodeList = new ArrayList<ExtTreeDto>();
		if(bsId==null || bsId.intValue()<=0){
			return nodeList;
		}
		
		if(parentCode==null){
			parentCode="";
		}
		
		List<Integer> deptIdList = bsDao.queryDeptIdByBsId(bsId);
		List<Dept> deptList=deptDao.queryChild(parentCode);
		
		for(Dept dept:deptList){
			ExtTreeDto node = new ExtTreeDto();
			node.setId(String.valueOf(dept.getId()));
			node.setData(dept.getCode());
			node.setText(dept.getName());
			Integer i = deptDao.countChild(dept.getCode());
			if(i==null || i.intValue()<=0){
				node.setLeaf(true);
			}
			if(deptIdList.contains(dept.getId())){
				node.setChecked(true);
			}else{
				node.setChecked(false);
			}
			
			nodeList.add(node);
			
		}
		
		return nodeList;
	}

	@Override
	public List<StaffDto> queryBsStaff(Integer id) {
		return bsDao.queryBsStaff(id);
	}

	@Override
	public Integer createBsStaff(Integer bsId, String account) {
		Integer staffId=staffDao.queryStaffIdByAccount(account);
		if(staffId==null){
			return null;
		}
		return bsDao.insertBsStaff(bsId, staffId);
	}

	@Override
	public Integer deleteBsStaff(Integer bsId, Integer staffId) {
		return bsDao.deleteBsStaff(bsId, staffId);
	}

	@Override
	public Integer updateBsDept(Integer bsId, Integer deptId, Boolean checked) {
		if(checked==null){
			checked = false;
		}
		Integer i=null;
		if(checked){
			i=bsDao.insertBsDept(bsId, deptId);
		}else{
			i=bsDao.deleteBsDept(bsId, deptId);
		}
		return i;
	}

	@Override
	public String queryUrl(String key) {
		return bsDao.queryUrl(key);
	}
	
	/********************/

//	public List<Bs> queryMyBsByStaffId(Integer staffId, String types) {
//		Assert.notNull(staffId, messageSource.getMessage("assert.notnull",
//				new String[] { "staffId" }, null));
//		return bsDao.queryMyBsByStaffId(staffId, types);
//	}
//
//	public List<Bs> queryMyBsByDeptId(Integer deptId, String types) {
//		Assert.notNull(deptId, messageSource.getMessage("assert.notnull",
//				new String[] { "deptId" }, null));
//		return bsDao.queryMyBsByDeptId(deptId, types);
//	}
//
//	public List<Bs> queryAllBsByStaffAndDeptId(Integer staffId, Integer deptId,
//			String types) {
//		Assert.notNull(staffId, messageSource.getMessage("assert.notnull",
//				new String[] { "staffId" }, null));
//		Assert.notNull(deptId, messageSource.getMessage("assert.notnull",
//				new String[] { "deptId" }, null));
//		List<Bs> listStaff = bsDao.queryMyBsByStaffId(staffId, types);
//		List<Bs> listDept = bsDao.queryMyBsByDeptId(deptId, types);
//		
//		Map<Integer, Bs> staffMap = new HashMap<Integer, Bs>();
//		for (Bs bs : listStaff) {
//			staffMap.put(bs.getId(), bs);
//		}
//
//		for (Bs bs : listDept) {
//			if (staffMap.get(bs.getId()) == null) {
//				listStaff.add(bs);
//			}
//		}
//
//		return listStaff;
//	}
//
//	public PageDto pageBsByCondition(Bs bs,PageDto page) {
//		
//		Assert.notNull(bs, messageSource.getMessage("assert.notnull",
//				new String[] { "bs" }, null));
//		
//		Assert.notNull(page, messageSource.getMessage("assert.notnull",
//				new String[] { "page" }, null));
//		
//		List<BsDto> list=bsDao.pageBsByBsDo(bs, page);
////		Map<String, String> map=ParamFacade.getInstance().getParamByType("bs_type");
//		for (BsDto bsDto2 : list) {
//			//TODO 根据参数配置查詢typeName
////			bsDto2.setTypeName(map.get(bsDto2.getBs().getTypes()));
//		}
//		page.setRecords(list);
//		page.setTotalRecords(bsDao.countPageBs(bs));
//		return page;
//	}

	
}
