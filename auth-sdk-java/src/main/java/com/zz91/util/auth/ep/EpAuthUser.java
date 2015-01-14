/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-13
 */
package com.zz91.util.auth.ep;

import java.io.Serializable;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-6-13
 */
public class EpAuthUser implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer uid;	//会员ID 
	private String memberCode;	//会员等级编号
	private Integer cid;	//公司信息
	private String account;	//帐号
	private String loginName; //姓名
	private String[] rightList;	//该公司所拥有的会员权利列表
	
	private String vticket;
	private String ticket;
	private String key;
	
	public final static String SESSION_KEY="EPauthorizesession";


	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}


	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String[] getRightList() {
		return rightList;
	}

	public void setRightList(String[] rightList) {
		this.rightList = rightList;
	}

	public static String getSessionKey() {
		return SESSION_KEY;
	}

	public String getVticket() {
		return vticket;
	}

	public void setVticket(String vticket) {
		this.vticket = vticket;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

}