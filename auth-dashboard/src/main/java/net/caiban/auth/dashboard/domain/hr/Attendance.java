package net.caiban.auth.dashboard.domain.hr;

import java.io.Serializable;
import java.util.Date;

/***
 * 
 * @author root
 *
 */
public class Attendance implements Serializable {
      
	/**
	 * 员工出勤信息
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String  name;
	private String  code;
	private String  account;
	private Date    gmtWork;
	private Date    gmtCreated;
	private Date    gmtModeified;
	private Integer scheduleId;
	
	public Attendance() {
		
		
	}
	public Attendance(Integer id ,String name,String code,String account,Date gmtWork,
			Date gmtCreated,Date gmtModeified){
		this.id = id;
		this.name = name;
		this.code =code;
		this.account = account;
		this.gmtWork = gmtWork;
		this.gmtCreated =gmtCreated;
		this.gmtModeified = gmtModeified;
		
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Date getGmtWork() {
		return gmtWork;
	}
	public void setGmtWork(Date gmtWork) {
		this.gmtWork = gmtWork;
	}
	public Date getGmtCreated() {
		return gmtCreated;
	}
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	public Date getGmtModeified() {
		return gmtModeified;
	}
	public void setGmtModeified(Date gmtModeified) {
		this.gmtModeified = gmtModeified;
	}
	public Integer getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}
	
}
