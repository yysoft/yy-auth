package net.caiban.auth.dashboard.domain.hr;

import java.io.Serializable;
import java.util.Date;

public class AttendanceScheduleDetail implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer scheduleId;
	private Date gmtMonth;
	private Integer dayOfYear;
	private Integer dayOfMonth;
	private Integer dayOfWeek;
	private Date day;
	private Long workf;
	private Long workt;
	private Long unixtime;
	private String createdBy;
	private String ModifiedBy;
	private Date gmtCreated;
	private Date gmtModified;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getGmtMonth() {
		return gmtMonth;
	}
	public void setGmtMonth(Date gmtMonth) {
		this.gmtMonth = gmtMonth;
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
	public Long getUnixtime() {
		return unixtime;
	}
	public void setUnixtime(Long unixtime) {
		this.unixtime = unixtime;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return ModifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		ModifiedBy = modifiedBy;
	}
	public Date getGmtCreated() {
		return gmtCreated;
	}
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	public Integer getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}
	public Date getDay() {
		return day;
	}
	public void setDay(Date day) {
		this.day = day;
	}
	
}
