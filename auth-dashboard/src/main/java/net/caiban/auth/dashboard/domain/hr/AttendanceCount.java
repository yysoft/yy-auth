package net.caiban.auth.dashboard.domain.hr;

import java.io.Serializable;
import java.util.Date;
/***
 * 
 * @author root
 *
 */
@SuppressWarnings("serial")
public class AttendanceCount  implements Serializable {

	/**
	 * 员工出勤统计信息
	 */
	private static long serialVersionUID = 1L;
	private Integer id;
	private String  name;
	private String  code;
	private String  account;//系统中的账号
	private Integer punch0;
	private Integer punch20;
	private Integer punch60;
	private Integer punch80;
	private Integer punchCount;
	private Date    gmtMonth;//统计的月份
	private Date    gmtWork;//问题数据的具体时间
	private Date    gmtCreated;
	private Date    gmtModeified;
	public AttendanceCount() {
		
	}
    public AttendanceCount(Integer id,String name,String code,String account,Integer punch0,Integer punch20,
    		Integer punch60,Integer punchCount,Date gmtMonth,Date gmtCreated,Date gmtModeified) {
    	this.id =id;
    	this.name= name;
    	this.code= code;
    	this.account =account;
    	this.punch0 = punch0;
    	this.punch20 = punch20;
    	this.punch60 = punch60;
    	this.punchCount = punchCount;
    	this.gmtCreated = gmtCreated;
    	this.gmtMonth  = gmtMonth;
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
	public Integer getPunch0() {
		return punch0;
	}
	public void setPunch0(Integer punch0) {
		this.punch0 = punch0;
	}
	public Integer getPunch20() {
		return punch20;
	}
	public void setPunch20(Integer punch20) {
		this.punch20 = punch20;
	}
	public Integer getPunch60() {
		return punch60;
	}
	public void setPunch60(Integer punch60) {
		this.punch60 = punch60;
	}
	public Integer getPunch80() {
		return punch80;
	}
	public void setPunch80(Integer punch80) {
		this.punch80 = punch80;
	}
	public static void setSerialversionuid(long serialversionuid) {
		serialVersionUID = serialversionuid;
	}
	public Integer getPunchCount() {
		return punchCount;
	}
	public void setPunchCount(Integer punchCount) {
		this.punchCount = punchCount;
	}
	public Date getGmtMonth() {
		return gmtMonth;
	}
	public void setGmtMonth(Date gmtMonth) {
		this.gmtMonth = gmtMonth;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    

}
