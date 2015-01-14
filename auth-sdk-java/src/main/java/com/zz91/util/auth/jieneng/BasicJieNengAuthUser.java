/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-13
 */
package com.zz91.util.auth.jieneng;

import java.io.Serializable;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-6-13
 */
public class BasicJieNengAuthUser implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer uid;	//会员ID 
	private String memberCode;	//会员等级编号
	private Integer cid;	//公司信息
	


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


	
}
