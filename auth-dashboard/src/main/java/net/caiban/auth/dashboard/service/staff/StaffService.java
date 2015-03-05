/**
 * 
 */
package net.caiban.auth.dashboard.service.staff;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.caiban.auth.dashboard.domain.bs.Bs;
import net.caiban.auth.dashboard.domain.staff.Staff;
import net.caiban.auth.dashboard.dto.PageDto;
import net.caiban.auth.dashboard.dto.staff.StaffDto;
import net.caiban.auth.dashboard.exception.ServiceException;
import net.caiban.auth.sdk.AuthMenu;
import net.caiban.auth.sdk.SessionUser;

/**
 * @author yuyh
 * 
 */
public interface StaffService {

	/**
	 * <br />默认显示所有未离职员工（status<2）
	 * <br />允许的查询条件：staff
	 * <br />staff.name：模糊查询
	 * <br />staff.staffNo：完全匹配
	 * <br />staff.deptCode：完全匹配
	 * <br />返回pageDto，totalRecord必需有
	 */
	public PageDto<StaffDto> pageStaff(Staff staff, PageDto<StaffDto> page);

	/**
	 * 创建员工信息，创建顺序如下： 1.创建账号信息 2.关联账号与角色（如果有） 3.创建员工信息
	 */
	public Integer createStaff(Staff staff, String account, String password,
			Integer[] roleArray);

	/**
	 * 员工离职，更改状态（status=2），同时更新note
	 */
	public Integer staffTurnover(Integer staffId, String note, Date gmtLeft);
	
	/**
	 * 员工转正，更改状态（status=1），同时更新note
	 */
	public Integer regular(Integer staffId, String note, Date gmtLeft);
	
	public Integer updateStaff(Staff staff, String password, String roleArr);
	
    public List<Bs> queryBsByStaffId(Integer staffId);
	
	public List<Bs> queryDeptBsByDeptCode(String deptCode);
	
	/**
	 * 查找员工的所有权限，权限包括两部分 （一是部门权限，二是角色权限）的并集 项目号必需提供，先通过项目号查出根权限
	 */
	public String[] queryRightOfStaff(String account, String projectCode);

	/**
	 * 验证用户登录 1.验证员工状态是否正常，status<2 2.验证账户密码是否正确 3.验证项目是否允许访问
	 */
	public String validateUser(String account, String password,
			String projectCode) throws ServiceException;

	/**
	 * 初始化二项信息： 1.员工基本信息(account, staffname, deptCode) 2.初始化对应项目的权限
	 */
	public SessionUser initSessionUser(String account, String projectCode);

	public Integer queryStaffIdByAccount(String account);

	public List<AuthMenu> queryMenuOfStaff(String account, String parentCode);
	
	public StaffDto queryStaffByAccount(String account);
	
	public Integer changePassword(String account, String op, String np, String vp);
	
	public String queryNameOfAccount(String account);
	
	public Map<String, String> queryStaffNameByDeptCode(String deptCode);

	public List<Staff> queryStaffByDeptCode(String dc);
	
}
