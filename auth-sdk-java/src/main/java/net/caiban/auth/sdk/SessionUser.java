/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-5
 */
package net.caiban.auth.sdk;

import java.io.Serializable;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-5
 */
public class SessionUser implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private String account;
	private String name;
	private String deptCode;
	private String[] rightArr;
	
	private String vticket;
	private String ticket;
	private String key;
	
	private String staffNo;
	
	public final static String SESSION_KEY="zz91authorizesession";
	
	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the deptCode
	 */
	public String getDeptCode() {
		return deptCode;
	}
	/**
	 * @param deptCode the deptCode to set
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	/**
	 * @return the ticket
	 */
	public String getTicket() {
		return ticket;
	}
	/**
	 * @param ticket the ticket to set
	 */
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * @return the vticket
	 */
	public String getVticket() {
		return vticket;
	}
	/**
	 * @param vticket the vticket to set
	 */
	public void setVticket(String vticket) {
		this.vticket = vticket;
	}
	/**
	 * @return the rightArr
	 */
	public String[] getRightArr() {
		return rightArr;
	}
	/**
	 * @param rightArr the rightArr to set
	 */
	public void setRightArr(String[] rightArr) {
		this.rightArr = rightArr;
	}
	public String getStaffNo() {
		return staffNo;
	}
	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}
	
	
}
