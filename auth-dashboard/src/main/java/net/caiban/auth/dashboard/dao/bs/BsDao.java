/**
 * 
 */
package net.caiban.auth.dashboard.dao.bs;

import java.util.List;

import net.caiban.auth.dashboard.domain.bs.Bs;
import net.caiban.auth.dashboard.domain.staff.Dept;
import net.caiban.auth.dashboard.domain.staff.Staff;
import net.caiban.auth.dashboard.dto.PageDto;
import net.caiban.auth.dashboard.dto.staff.StaffDto;

/**
 * @author root
 *
 */
public interface BsDao {
	
	public List<Bs> queryBsOfStaff(Integer staffId,String types);
	public List<Bs> queryBsOfDept(Integer deptId,String types);
	public String queryRightCodeOfBs(String projectCode);
	public Integer countBsOfDept(Integer deptId, String projectCode);
	public Integer countBsOfStaff(Integer staffId, String projectCode);
	public String queryPasswordOfBs(String pcode);
	
	public List<Bs> queryBs(Bs bs, PageDto<Bs> page);
	
	public Integer queryBsCount(Bs bs);
	
	public Bs queryOneBs(Integer id);
	
	public Integer insertBs(Bs bs);
	
	public Integer updateBs(Bs bs);
	
	public Integer deleteBs(Integer id);
	
	public List<Staff> queryStaffByBs(Integer id,String types);
	
	public List<Dept> queryDeptByBs(Integer id,String types);
	
	public List<Integer> queryDeptIdByBsId(Integer bsId);
	
	public List<StaffDto> queryBsStaff(Integer bsId);
	
	public Integer insertBsStaff(Integer bsId, Integer staffId);
	
	public Integer deleteBsStaff(Integer bsId, Integer staffId);
	
	public Integer insertBsDept(Integer bsId, Integer deptId);
	
	public Integer deleteBsDept(Integer bsId, Integer deptId);
	
	public Integer deleteBsDept(Integer bsId);
	
	public Integer deleteBsStaff(Integer bsId);
	
	public String queryUrl(String key);
	/***********************************/
	
//	/**
//	 * 根据员工Id 查訊所有能查看的系统
//	 * @param staffId 
//	 * @return List<Bs>
//	 */
//	public List<Bs> queryMyBsByStaffId(Integer staffId,String types);
//	/**
//	 * 根据部门Id 查訊所有能查看的系统
//	 * @param deptId
//	 * @return List<Bs>
//	 */
//	public List<Bs> queryMyBsByDeptId(Integer deptId, String types);
//	/**
//	 *  分页显示所有的业务系统
//	 * @param bsDo  不能为空 
//	 * 			bsDo,pageDto
//	 * @return  List<BsDto> list<Bs> 和typeName
//	 */
//	public List<BsDto> pageBsByBsDo(Bs bs,PageDto page);
//	/**
//	 * 统计记录总数
//	 * @param bs 查詢条件 不能为空
//	 *       code,name,types code 可以为空
//	 * @return 记录总数
//	 */
//	public Integer countPageBs(Bs bs);
//	/**
//	 *  添加业务系统
//	 * @param bs 不能为空
//	 * @return 如果>0 添加成功 否则矢败
//	 */
//	public Integer insertBs(Bs bs);
//	/**  
//	 * 修改业务系统
//	 * @param bs 不能为空
//	 * @return 如果>0 修改成功 否则矢败
//	 */
//	public Integer updateBs(Bs bs);
}
