/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-7
 */
package net.caiban.auth.sdk;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import net.caiban.utils.file.PropertiesUtil;

import com.google.common.base.Strings;

/**
 * @author mays (mays@caiban.net)
 *
 * created on 2015-1-14
 */
public class AuthConst {
	
	final static Logger LOG = Logger.getLogger(AuthConst.class);
	
	public static String PROJECT_CODE="";
	public static String PROJECT_PASSWORD="";

	public static String API_HOST="http://auth.caiban.net/api";
	public static String SSO_DOMAIN="caiban.net";

	public final static String TICKET_KEY="_WLT";
	
	public static String CONFIG_DEFAULT="auth.properties";
	
	private String projectCode;
	private String projectPassword;
	private String api;
	private String domain;
	
	public void startup(){
		PROJECT_CODE = projectCode;
		PROJECT_PASSWORD = projectPassword;
		API_HOST = api;
		SSO_DOMAIN = domain;
	}
	
	public static void init(){
		init(CONFIG_DEFAULT);
	}
	
	public static void init(String properties){
		if(Strings.isNullOrEmpty(properties)){
			throw new IllegalArgumentException("Init auth sdk failure. properties can not be null");
		}
		
		try {
			Map<String, String> config = PropertiesUtil.read(properties, PropertiesUtil.CHARSET_UTF8);
			init(config);
		} catch (IOException e) {
			LOG.error("Can not load auth properties from path: "+properties, e);
		}
	}
	
	public static void init(Map<String, String> config){
		
		PROJECT_CODE = Strings.isNullOrEmpty(config.get("work.project.code"))?PROJECT_CODE:config.get("work.project.code");
		PROJECT_PASSWORD = Strings.isNullOrEmpty(config.get("work.project.password"))?PROJECT_CODE:config.get("work.project.password");
		API_HOST = Strings.isNullOrEmpty(config.get("work.api"))?PROJECT_CODE:config.get("work.api");
		SSO_DOMAIN = Strings.isNullOrEmpty(config.get("work.domain"))?PROJECT_CODE:config.get("work.domain");
		
	}
	
	/**
	 * @return the projectCode
	 */
	public String getProjectCode() {
		return projectCode;
	}
	/**
	 * @param projectCode the projectCode to set
	 */
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	/**
	 * @return the projectPassword
	 */
	public String getProjectPassword() {
		return projectPassword;
	}
	/**
	 * @param projectPassword the projectPassword to set
	 */
	public void setProjectPassword(String projectPassword) {
		this.projectPassword = projectPassword;
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
