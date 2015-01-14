/**
 * 
 */
package net.caiban.auth.dashboard.dto.hr;

import java.io.Serializable;

/**
 * @author mays
 *
 */
public class WorkDay implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer dayOfYear;
	private Integer dayOfMonth;
	private Integer dayOfWeek;
	private Long day;
	private Long workf;
	private Long workt;
	private Long unixtime;
	
	public Long getWorkf() {
		return workf;
	}
	public void setWorkf(Long workf) {
		this.workf = workf;
	}
	public Long getWorkt() {
		return workt;
	}
	public void setWorkt(Long workt) {
		this.workt = workt;
	}
	public Integer getDayOfYear() {
		return dayOfYear;
	}
	public void setDayOfYear(Integer dayOfYear) {
		this.dayOfYear = dayOfYear;
	}
	public Integer getDayOfMonth() {
		return dayOfMonth;
	}
	public void setDayOfMonth(Integer dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}
	public Integer getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(Integer dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public Long getDay() {
		return day;
	}
	public void setDay(Long day) {
		this.day = day;
	}
	public Long getUnixtime() {
		return unixtime;
	}
	public void setUnixtime(Long unixtime) {
		this.unixtime = unixtime;
	}
	
	
	
}
