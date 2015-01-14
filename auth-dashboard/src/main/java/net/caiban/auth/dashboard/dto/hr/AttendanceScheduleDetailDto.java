/**
 * 
 */
package net.caiban.auth.dashboard.dto.hr;

import java.io.Serializable;

import net.caiban.auth.dashboard.domain.hr.AttendanceScheduleDetail;

/**
 * @author mays
 *
 */
public class AttendanceScheduleDetailDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private AttendanceScheduleDetail detail;
	private String workf;
	private String workt;
	private Integer workDay;
	
	public AttendanceScheduleDetail getDetail() {
		return detail;
	}
	public void setDetail(AttendanceScheduleDetail detail) {
		this.detail = detail;
	}
	public String getWorkf() {
		return workf;
	}
	public void setWorkf(String workf) {
		this.workf = workf;
	}
	public String getWorkt() {
		return workt;
	}
	public void setWorkt(String workt) {
		this.workt = workt;
	}
	public Integer getWorkDay() {
		return workDay;
	}
	public void setWorkDay(Integer workDay) {
		this.workDay = workDay;
	}
	
	
}
