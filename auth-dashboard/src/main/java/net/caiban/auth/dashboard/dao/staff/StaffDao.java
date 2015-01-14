/**
 * 
 */
package net.caiban.auth.dashboard.dao.staff;

import java.util.Date;
import java.util.List;

import net.caiban.auth.dashboard.domain.bs.Bs;
import net.caiban.auth.dashboard.domain.staff.Staff;
import net.caiban.auth.dashboard.dto.PageDto;
import net.caiban.auth.dashboard.dto.staff.StaffDto;

/**
 * @author root
 *
 */
public interface StaffDao {
	
	public List<StaffDto> queryStaff(Staff staff, PageDto<StaffDto> page);
	public Integer queryStaffCount(Staff staff);
	public Integer insertStaff(Staff staff);
	public Integer updateStaffStatus(Integer staffId, Integer status, String note, Date gmtLeft);
	public Integer countStaffByStatus(String account, Integer[] status);
	public Staff queryStaffByAccount(String account);
	public Integer queryStaffIdByAccount(String account);
	
	public String queryDeptCodeByStaffId(Integer id);
	
	public Integer updateStaff(Staff staff);
	
	public List<Bs> queryBsByStaffId(Integer staffId);
	
	public List<Bs> queryDeptBsByDeptCode(String deptCode);
	
	public Integer countStaff(String staffNo);
	
	public String queryNameByAccount(String account);
	
	public List<Staff> queryStaffNameByDeptCode(String deptCode);
	
	public String queryAccountByName(String name);
}
