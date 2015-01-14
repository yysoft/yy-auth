package net.caiban.auth.dashboard.domain.staff;

import java.util.Date;

public class SchedulerReportEvent {
	private Integer id;
	private Integer eventId;
	private Integer reportId;
	private Date startDate;
	private Date endDate;
	
	
	public SchedulerReportEvent(Integer eventId, Integer reportId) {
		super();
		
		this.eventId = eventId;
		this.reportId = reportId;
	}
	
	
	public SchedulerReportEvent() {
		super();
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getEventId() {
		return eventId;
	}
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	public Integer getReportId() {
		return reportId;
	}
	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
