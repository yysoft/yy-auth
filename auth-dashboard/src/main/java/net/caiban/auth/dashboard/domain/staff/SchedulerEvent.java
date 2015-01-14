/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-7-5
 */
package net.caiban.auth.dashboard.domain.staff;

import java.io.Serializable;
import java.util.Date;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-7-5
 */
public class SchedulerEvent implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String text;
	private String details;
	private Date startDate;
	private Date endDate;
	private String assignAccount;
	private String ownerAccount;
	private Integer persent;
	private Integer importance;
	private Date gmtCreated;
	private Date gmtModified;
	private String deptCode;
	
	
	public SchedulerEvent(String text, String details,
			Date startDate, Date endDate, String assignAccount,
			String ownerAccount, Integer persent, Integer importance,
			Date gmtCreated, Date gmtModified, String deptCode) {
		super();
		this.text = text;
		this.details = details;
		this.startDate = startDate;
		this.endDate = endDate;
		this.assignAccount = assignAccount;
		this.ownerAccount = ownerAccount;
		this.persent = persent;
		this.importance = importance;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
		this.deptCode = deptCode;
	}
	
	
	
	public SchedulerEvent() {
		super();
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
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the details
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * @param details
	 *            the details to set
	 */
	public void setDetails(String details) {
		this.details = details;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the assignAccount
	 */
	public String getAssignAccount() {
		return assignAccount;
	}

	/**
	 * @param assignAccount
	 *            the assignAccount to set
	 */
	public void setAssignAccount(String assignAccount) {
		this.assignAccount = assignAccount;
	}

	/**
	 * @return the ownerAccount
	 */
	public String getOwnerAccount() {
		return ownerAccount;
	}

	/**
	 * @param ownerAccount
	 *            the ownerAccount to set
	 */
	public void setOwnerAccount(String ownerAccount) {
		this.ownerAccount = ownerAccount;
	}

	/**
	 * @return the persent
	 */
	public Integer getPersent() {
		return persent;
	}

	/**
	 * @param persent
	 *            the persent to set
	 */
	public void setPersent(Integer persent) {
		this.persent = persent;
	}

	/**
	 * @return the importance
	 */
	public Integer getImportance() {
		return importance;
	}

	/**
	 * @param importance
	 *            the importance to set
	 */
	public void setImportance(Integer importance) {
		this.importance = importance;
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

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

}
