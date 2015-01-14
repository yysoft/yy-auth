/**
 * 
 */
package net.caiban.auth.dashboard.dto.hr;

import java.io.Serializable;

/**
 * @author mays
 *
 */
public class AttendanceDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String code;
	private String name;
	private String account;
	private Long workf;
	private Long workt;
	
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
	
	
}
