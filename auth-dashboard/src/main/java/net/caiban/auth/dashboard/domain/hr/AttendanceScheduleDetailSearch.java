package net.caiban.auth.dashboard.domain.hr;

import java.util.Date;

public class AttendanceScheduleDetailSearch {

	private Integer scheduleId;
	private Date gmtMonth;
	
	public Integer getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}
	public Date getGmtMonth() {
		return gmtMonth;
	}
	public void setGmtMonth(Date gmtMonth) {
		this.gmtMonth = gmtMonth;
	}
}
