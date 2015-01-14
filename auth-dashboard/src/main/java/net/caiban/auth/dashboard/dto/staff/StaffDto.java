/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-11-15
 */
package net.caiban.auth.dashboard.dto.staff;

import java.io.Serializable;

import net.caiban.auth.dashboard.domain.staff.Staff;

/**
 * @author root
 * 
 *         created on 2010-11-15
 */
public class StaffDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Staff staff;
	private String deptName;
	private String roleArr;
	private String role;
	private Integer bsId;

	/**
	 * @return the staff
	 */
	public Staff getStaff() {
		return staff;
	}

	/**
	 * @param staff
	 *            the staff to set
	 */
	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	/**
	 * @return the deptName
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * @param deptName
	 *            the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * @return the roleArr
	 */
	public String getRoleArr() {
		return roleArr;
	}

	/**
	 * @param roleArr
	 *            the roleArr to set
	 */
	public void setRoleArr(String roleArr) {
		this.roleArr = roleArr;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the bsId
	 */
	public Integer getBsId() {
		return bsId;
	}

	/**
	 * @param bsId
	 *            the bsId to set
	 */
	public void setBsId(Integer bsId) {
		this.bsId = bsId;
	}

}
