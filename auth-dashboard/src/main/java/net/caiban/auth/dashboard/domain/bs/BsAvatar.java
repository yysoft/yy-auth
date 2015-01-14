/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-10
 */
package net.caiban.auth.dashboard.domain.bs;

import java.io.Serializable;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-6-10
 */
public class BsAvatar implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String url;
	private String avatar;
	
	/**
	 * 
	 */
	public BsAvatar() {
		super();
	}
	
	/**
	 * @param url
	 * @param avatar
	 */
	public BsAvatar(String url, String avatar) {
		super();
		this.url = url;
		this.avatar = avatar;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the avatar
	 */
	public String getAvatar() {
		return avatar;
	}
	/**
	 * @param avatar the avatar to set
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	
}
