/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-13
 */
package com.zz91.util.auth.jieneng;

/**
 * @author Leon 
 *
 * created on 2011-6-13
 */
public class JieNengAuthConst {
	public static String API_HOST = null;	//由spring容器来初始化主机信息
	public static String JIENENG_DOMAIN = null;	//由spring容器来初始化域名信息
	public static String PROJECT=null;
	public static String PROJECT_PASSWORD=null;
	public final static String TICKET_KEY="EPssotoken";
	
	private String api;
	private String domain;
	private String project;
	private String projectPwd;
	
	public void startup(){
		API_HOST = api;
		JIENENG_DOMAIN = domain;
		PROJECT=project;
		PROJECT_PASSWORD=projectPwd;
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

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getProjectPwd() {
		return projectPwd;
	}

	public void setProjectPwd(String projectPwd) {
		this.projectPwd = projectPwd;
	}
	
	
}
