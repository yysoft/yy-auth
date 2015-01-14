/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-7-5
 */
package net.caiban.auth.dashboard.dto.staff;

import java.io.Serializable;

import net.caiban.auth.dashboard.domain.staff.SchedulerReport;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-7-5
 */
public class SchedulerReportDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SchedulerReport report;
	private String userName;
	/**
	 * @return the report
	 */
	public SchedulerReport getReport() {
		return report;
	}
	/**
	 * @param report the report to set
	 */
	public void setReport(SchedulerReport report) {
		this.report = report;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
