/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-13
 */
package com.zz91.util.auth.frontsso;

import java.io.Serializable;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-6-13
 */
public class SsoUser implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer accountId;
	private String account;
	private String email;
	private Integer companyId;
	private String membershipCode;
	private String zstFlag;
	private String areaCode;
	private String serviceCode;
	
	@Deprecated
	private String vticket;
	private String ticket;
	@Deprecated
	private String key;
	
	
	public final static String SESSION_KEY="zz91authorizesession";
	
	/**
	 * @return the accountId
	 */
	public Integer getAccountId() {
		return accountId;
	}
	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the companyId
	 */
	public Integer getCompanyId() {
		return companyId;
	}
	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	/**
	 * @return the membershipCode
	 */
	public String getMembershipCode() {
		return membershipCode;
	}
	/**
	 * @param membershipCode the membershipCode to set
	 */
	public void setMembershipCode(String membershipCode) {
		this.membershipCode = membershipCode;
	}
	/**
	 * @return the zstFlag
	 */
	public String getZstFlag() {
		return zstFlag;
	}
	/**
	 * @param zstFlag the zstFlag to set
	 */
	public void setZstFlag(String zstFlag) {
		this.zstFlag = zstFlag;
	}
	/**
	 * @return the areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}
	/**
	 * @param areaCode the areaCode to set
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	/**
	 * @return the serviceCode
	 */
	public String getServiceCode() {
		return serviceCode;
	}
	/**
	 * @param serviceCode the serviceCode to set
	 */
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	/**
	 * @return the vticket
	 */
	@Deprecated
	public String getVticket() {
		return vticket;
	}
	/**
	 * @param vticket the vticket to set
	 */
	@Deprecated
	public void setVticket(String vticket) {
		this.vticket = vticket;
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
	@Deprecated
	public String getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	@Deprecated
	public void setKey(String key) {
		this.key = key;
	}
	
}
