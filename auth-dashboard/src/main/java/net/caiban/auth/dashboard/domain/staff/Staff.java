/**
 * 
 */
package net.caiban.auth.dashboard.domain.staff;

import java.io.Serializable;
import java.util.Date;

/**
 * @author root
 * 
 */
public class Staff implements Serializable {

	/**
	 * 员工
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String account;
	private String staffNo;// 工作号
	private String deptCode;
	private String name;
	private String email;
	private String sex;// 性别
	private String avatar;// 图像
	private Date birthday;
	private String jobs;
	private String status;
	private Date gmtEntry;
	private Date gmtLeft;
	private String note;
	private Date gmtCreated;
	private Date gmtModified;

	/**
	 * 
	 */
	public Staff() {
		super();
	}

	/**
	 * @param id
	 * @param account
	 * @param staffNo
	 * @param deptCode
	 * @param name
	 * @param email
	 * @param sex
	 * @param avatar
	 * @param birthday
	 * @param jobs
	 * @param status
	 * @param gmtEntry
	 * @param gmtLeft
	 * @param note
	 * @param gmtCreated
	 * @param gmtModified
	 */
	public Staff(Integer id, String account, String staffNo, String deptCode,
			String name, String email, String sex, String avatar,
			Date birthday, String jobs, String status, Date gmtEntry,
			Date gmtLeft, String note, Date gmtCreated, Date gmtModified) {
		super();
		this.id = id;
		this.account = account;
		this.staffNo = staffNo;
		this.deptCode = deptCode;
		this.name = name;
		this.email = email;
		this.sex = sex;
		this.avatar = avatar;
		this.birthday = birthday;
		this.jobs = jobs;
		this.status = status;
		this.gmtEntry = gmtEntry;
		this.gmtLeft = gmtLeft;
		this.note = note;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @return the staffNo
	 */
	public String getStaffNo() {
		return staffNo;
	}

	/**
	 * @param staffNo
	 *            the staffNo to set
	 */
	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	/**
	 * @return the deptCode
	 */
	public String getDeptCode() {
		return deptCode;
	}

	/**
	 * @param deptCode
	 *            the deptCode to set
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the avatar
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * @param avatar
	 *            the avatar to set
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday
	 *            the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * @return the jobs
	 */
	public String getJobs() {
		return jobs;
	}

	/**
	 * @param jobs
	 *            the jobs to set
	 */
	public void setJobs(String jobs) {
		this.jobs = jobs;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the gmtEntry
	 */
	public Date getGmtEntry() {
		return gmtEntry;
	}

	/**
	 * @param gmtEntry
	 *            the gmtEntry to set
	 */
	public void setGmtEntry(Date gmtEntry) {
		this.gmtEntry = gmtEntry;
	}

	/**
	 * @return the gmtLeft
	 */
	public Date getGmtLeft() {
		return gmtLeft;
	}

	/**
	 * @param gmtLeft
	 *            the gmtLeft to set
	 */
	public void setGmtLeft(Date gmtLeft) {
		this.gmtLeft = gmtLeft;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the gmtCreated
	 */
	public Date getGmtCreated() {
		return gmtCreated;
	}

	/**
	 * @param gmtCreated
	 *            the gmtCreated to set
	 */
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	/**
	 * @return the gmtModified
	 */
	public Date getGmtModified() {
		return gmtModified;
	}

	/**
	 * @param gmtModified
	 *            the gmtModified to set
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

}
