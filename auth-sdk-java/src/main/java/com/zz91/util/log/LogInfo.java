package com.zz91.util.log;


import java.io.Serializable;
import java.util.Date;

/**
 * 记录日志信息对应的实体bean
 * @author root
 *
 */
@Deprecated
public class LogInfo implements Serializable {
	/**
	 * 远程传输对象，需保证serialVersionUID与远程服务器反序列化对象保持一致
	 */
	private static final long serialVersionUID = -616379108814659853L;
	private Integer id;
	private String appCode;
	private String operator;
	private Integer operation;
	private String operatorIp;
	private Integer result;
	private Date gmtOperate;
	private String message;
	private String note;
	private Date gmtCreated;
	private Date gmtModified;
	
	
	public LogInfo(Integer id, String appCode, String operator,
			Integer operation, String operatorIp, Integer result,
			Date gmtOperate, String message, String note, Date gmtCreated,
			Date gmtModified) {
		super();
		this.id = id;
		this.appCode = appCode;
		this.operator = operator;
		this.operation = operation;
		this.operatorIp = operatorIp;
		this.result = result;
		this.gmtOperate = gmtOperate;
		this.message = message;
		this.note = note;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}
	public LogInfo() {
		// TODO Auto-generated constructor stub
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Integer getOperation() {
		return operation;
	}
	public void setOperation(Integer operation) {
		this.operation = operation;
	}
	public String getOperatorIp() {
		return operatorIp;
	}
	public void setOperatorIp(String operatorIp) {
		this.operatorIp = operatorIp;
	}
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public Date getGmtOperate() {
		return gmtOperate;
	}
	public void setGmtOperate(Date gmtOperate) {
		this.gmtOperate = gmtOperate;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
	@Override
	public String toString() {
		return "LogInfo [appCode=" + appCode + ", gmtCreated=" + gmtCreated
				+ ", gmtModified=" + gmtModified + ", gmtOperate=" + gmtOperate
				+ ", id=" + id + ", message=" + message + ", note=" + note
				+ ", operation=" + operation + ", operator=" + operator
				+ ", operatorIp=" + operatorIp + ", result=" + result + "]";
	}
	
	
}
