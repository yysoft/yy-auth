/**
 * 
 */
package net.caiban.auth.dashboard.service.bs;

import java.util.List;

import net.caiban.auth.dashboard.domain.bs.Bs;
import net.caiban.auth.dashboard.domain.staff.Dept;
import net.caiban.auth.dashboard.domain.staff.Staff;
import net.caiban.auth.dashboard.dto.ExtTreeDto;
import net.caiban.auth.dashboard.dto.PageDto;
import net.caiban.auth.dashboard.dto.bs.BsDto;
import net.caiban.auth.dashboard.dto.staff.StaffDto;

/**
 * @author root
 * 
 */
public interface BsService {
	
	public List<Bs> queryBsOfStaff(Integer staffId, String types);
	public List<Bs> queryBsOfDept(Integer deptId, String types);
	
	public PageDto<Bs> pageBs(Bs bs, PageDto<Bs> page);
	
	public Integer createBs(Bs bs);
	
	public Integer updateBs(Bs bs);
	
	public Integer deleteBs(Integer id);
	
	public BsDto queryOneBs(Integer id);
	
	public List<Staff> queryStaffByBs(Integer id,String types);
	
	public List<Dept> queryDeptByBs(Integer id,String types);
	
	
	public String queryPasswordOfBs(String pcode);
	
	public String queryRightCodeOfProject(String projectCode);
	
	/**
	 * 查找登录用户可以访问的业务系统
	 */
	public List<Bs> queryMyBs(String account, String types);
	
	public List<ExtTreeDto> queryDeptTreeNode(String parentCode, Integer bsId);
	
	public List<StaffDto> queryBsStaff(Integer id);
	
	public Integer deleteBsStaff(Integer bsId, Integer staffId);
	
	public Integer createBsStaff(Integer bsId, String account);
	
	public Integer updateBsDept(Integer bsId, Integer deptId, Boolean checked);
	
	public String queryUrl(String key);
	
	/*******************以下老代码***********************/
//	/**
//	 * 根据登录用戶staffId 查訊所有能查看的系统 
//	 * 
//	 * @param staffId
//	 * @return List<Bs>
//	 */
//	public List<Bs> queryMyBsByStaffId(Integer staffId, String types);
//
//	/**
//	 * 根据部门Id 查訊所有能查看的系统
//	 * 
//	 * @param deptId
//	 * @return List<Bs>
//	 */
//	public List<Bs> queryMyBsByDeptId(Integer deptId, String types);
//
//	/**
//	 * 查詢该员工能查看的所有系统
//	 * 
//	 * @return
//	 */
//	public List<Bs> queryAllBsByStaffAndDeptId(Integer staffId, Integer deptId,
//			String type);
//	/**
//	 *  分页查詢所有系统
//	 * @param bs
//	 * @param page 分页參数 
//	 * @return List<BsDto>
//	 */
//	public PageDto pageBsByCondition(Bs bs,PageDto page);
}
