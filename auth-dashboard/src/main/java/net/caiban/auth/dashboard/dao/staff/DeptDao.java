/**
 * 
 */
package net.caiban.auth.dashboard.dao.staff;

import java.util.List;

import net.caiban.auth.dashboard.domain.auth.AuthRight;
import net.caiban.auth.dashboard.domain.staff.Dept;

/**
 * @author root
 *
 */
public interface DeptDao {
	
	public Integer queryDeptIdByAccount(String account);
	public List<AuthRight> queryRightOfDept(String parentCode, Integer deptId);
	public List<AuthRight> queryChildRightOfDept(String parentCode, Integer deptId);
	
	public List<Dept> queryChild(String parentCode);
	
	public Integer countChild(String parentCode);
	
	public List<Integer> queryRightIdOfDept(Integer deptId);
	
	public Integer insertDeptRight(Integer deptId, Integer rightId);
	
	public Integer deleteDeptRight(Integer deptId, Integer rightId);
	
	public Integer insertDept(Dept dept);
	
	public Integer deleteRightByDept(String deptCode);
	
	public Integer deleteDeptBs(String deptCode);
	
	public Integer deleteDept(String code);
	
	public Dept queryOneDept(String code);
	
	public Integer updateDept(Dept dept);
	
	public String queryMaxCodeOfChild(String parentCode);
	
}
