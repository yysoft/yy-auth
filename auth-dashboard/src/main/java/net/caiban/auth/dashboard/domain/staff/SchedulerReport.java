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
 * created on 2011-7-5
 */
public class SchedulerReport implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String DeptCode;
	private String account;
	private String text;
	private String details;
	private Date composeDate;
	private String year;
	private Integer week;
	private Date gmtCreated;
	private Date gmtModified;
	
	
	
	public SchedulerReport(String deptCode, String account,
			String text, String details, Date composeDate, String year,
			Integer week, Date gmtCreated, Date gmtModified) {
		super();
		DeptCode = deptCode;
		this.account = account;
		this.text = text;
		this.details = details;
		this.composeDate = composeDate;
		this.year = year;
		this.week = week;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}
	
	
	public SchedulerReport() {
		super();
	}


	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the deptCode
	 */
	public String getDeptCode() {
		return DeptCode;
	}
	/**
	 * @param deptCode the deptCode to set
	 */
	public void setDeptCode(String deptCode) {
		DeptCode = deptCode;
	}
	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
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
	 * @param details the details to set
	 */
	public void setDetails(String details) {
		this.details = details;
	}
	/**
	 * @return the composeDate
	 */
	public Date getComposeDate() {
		return composeDate;
	}
	/**
	 * @param composeDate the composeDate to set
	 */
	public void setComposeDate(Date composeDate) {
		this.composeDate = composeDate;
	}
	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}
	/**
	 * @return the week
	 */
	public Integer getWeek() {
		return week;
	}
	/**
	 * @param week the week to set
	 */
	public void setWeek(Integer week) {
		this.week = week;
	}
	/**
	 * @return the gmtCreated
	 */
	public Date getGmtCreated() {
		return gmtCreated;
	}
	/**
	 * @param gmtCreated the gmtCreated to set
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
	 * @param gmtModified the gmtModified to set
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	
	
}
