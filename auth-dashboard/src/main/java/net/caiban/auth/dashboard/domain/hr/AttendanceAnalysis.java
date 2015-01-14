/**
 * 
 */
package net.caiban.auth.dashboard.domain.hr;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author mays
 *
 */
public class AttendanceAnalysis implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String code;
	private String name;
	private String account;
	private BigDecimal dayFull;
	private BigDecimal dayActual;
	private BigDecimal dayLeave;
	private BigDecimal dayOther;
	private BigDecimal dayUnwork;
	private Integer dayUnrecord;
	private Integer dayLate;
	private Integer dayEarly;
	private Integer dayOvertime;
	private Date gmtTarget;
	private Date gmtCreated;
	private Date gmtModified;
	private Integer scheduleId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public BigDecimal getDayFull() {
		return dayFull;
	}
	public void setDayFull(BigDecimal dayFull) {
		this.dayFull = dayFull;
	}
	public BigDecimal getDayActual() {
		return dayActual;
	}
	public void setDayActual(BigDecimal dayActual) {
		this.dayActual = dayActual;
	}
	public BigDecimal getDayLeave() {
		return dayLeave;
	}
	public void setDayLeave(BigDecimal dayLeave) {
		this.dayLeave = dayLeave;
	}
	public BigDecimal getDayOther() {
		return dayOther;
	}
	public void setDayOther(BigDecimal dayOther) {
		this.dayOther = dayOther;
	}
	public BigDecimal getDayUnwork() {
		return dayUnwork;
	}
	public void setDayUnwork(BigDecimal dayUnwork) {
		this.dayUnwork = dayUnwork;
	}
	public Integer getDayUnrecord() {
		return dayUnrecord;
	}
	public void setDayUnrecord(Integer dayUnrecord) {
		this.dayUnrecord = dayUnrecord;
	}
	public Integer getDayLate() {
		return dayLate;
	}
	public void setDayLate(Integer dayLate) {
		this.dayLate = dayLate;
	}
	public Integer getDayEarly() {
		return dayEarly;
	}
	public void setDayEarly(Integer dayEarly) {
		this.dayEarly = dayEarly;
	}
	public Integer getDayOvertime() {
		return dayOvertime;
	}
	public void setDayOvertime(Integer dayOvertime) {
		this.dayOvertime = dayOvertime;
	}
	public Date getGmtTarget() {
		return gmtTarget;
	}
	public void setGmtTarget(Date gmtTarget) {
		this.gmtTarget = gmtTarget;
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
	
}
