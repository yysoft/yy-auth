/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-13
 */
package com.zz91.util.auth.frontsso;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-6-13
 */
public class SsoConst {
	public static String API_HOST="http://passport.zz91.com/api";
	public static String SSO_DOMAIN="zz91.com";
	public final static String TICKET_KEY="zz91ssotoken";
	public final static String SESSION_KEY="ZZSID";
	
	private String api;
	private String domain;
	
	public void startup(){
		API_HOST = api;
		SSO_DOMAIN = domain;
	}

	/**
	 * @return the api
	 */
	public String getApi() {
		return api;
	}

	/**
	 * @param api the api to set
	 */
	public void setApi(String api) {
		this.api = api;
	}

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * @param domain the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	
}
