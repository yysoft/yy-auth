/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-13
 */
package com.zz91.util.auth.frontsso;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpException;

import com.zz91.util.encrypt.MD5;
import com.zz91.util.exception.AuthorizeException;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.http.SessionUtils;
import com.zz91.util.lang.StringUtils;


/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-6-13
 */
public class SsoUtils extends SessionUtils{

	private static SsoUtils _instance=null;
	
	private SsoUtils(){
		
	}
	
	synchronized public static SsoUtils getInstance(){
		if(_instance==null){
			_instance = new SsoUtils();
		}
		return _instance;
	}
	
	/**
	 * 发送消息包含：account，password，projectCode
	 * ticket=account+password+projectCode+projectPassword+key
	 * 返回信息包含：sessionUser，ticket，key
	 * @throws IOException 
	 * @throws AuthorizeException 
	 * @throws NoSuchAlgorithmException 
	 * @throws HttpException 
	 */
	public SsoUser validateUser(HttpServletResponse response, String account, String password, Integer expired, String ip) 
			throws HttpException, NoSuchAlgorithmException, AuthorizeException, IOException{
		String encodePwd="";
		try {
			encodePwd = MD5.encode(password);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return validateUserByEncodePwd(response, account, encodePwd, expired, ip);
	}
	
	public SsoUser validateUserByEncodePwd(HttpServletResponse response, String account, String password, Integer expired, String ip) 
			throws AuthorizeException, HttpException, IOException, NoSuchAlgorithmException {
		SsoUser ssoUser = null;
		String encodeAccount = URLEncoder.encode(account, "utf-8");
		
		String result = HttpUtils.getInstance().httpGet(SsoConst.API_HOST+"/validationUser.htm?a="+encodeAccount+"&p="+password+"&ip="+ip, HttpUtils.CHARSET_UTF8);
		
		JSONObject resultJson = JSONObject.fromObject(result);
		
		//TODO error code 返回逻辑
		if(resultJson.containsKey("error") && !StringUtils.isEmpty(resultJson.getString("error"))){
			throw new AuthorizeException(resultJson.getString("error"));
		}

		if(!resultJson.containsKey("key") || !resultJson.containsKey("ticket") || !resultJson.containsKey("ssoUser")){
			throw new AuthorizeException(AuthorizeException.ERROR_SERVER);
		}
		
		String key=resultJson.getString("key");
		String ticket=resultJson.getString("ticket");
		if(StringUtils.isEmpty(key) || StringUtils.isEmpty(ticket)){
			throw new AuthorizeException(AuthorizeException.ERROR_SERVER);
		}
		
		String validateTicket=MD5.encode(account+password+key);
		if(!ticket.equals(validateTicket)){
			throw new AuthorizeException(AuthorizeException.ERROR_SERVER);
		}
		
		if(!resultJson.getString("ssoUser").equals("null")){
			ssoUser = (SsoUser) JSONObject.toBean(resultJson.getJSONObject("ssoUser"), SsoUser.class);
		}
		
		if(ssoUser==null){
			throw new AuthorizeException(AuthorizeException.ERROR_SERVER);
		}
		
		HttpUtils.getInstance().setCookie(response, SsoConst.TICKET_KEY, ticket, SsoConst.SSO_DOMAIN, null);
				
		return ssoUser;
	}
	
	/**
	 * 发送信息包含：ticket，projectCode
	 * vticket=projectCode+projectPassword+key
	 * 返回信息包含：SessionUser，vticket，key
	 */
	public SsoUser validateTicket(HttpServletRequest request){
		
		SsoUser ssoUser = null;
		String ticket=HttpUtils.getInstance().getCookie(request, SsoConst.TICKET_KEY, SsoConst.SSO_DOMAIN);
		
		String result=null;
		try {
			result = HttpUtils.getInstance().httpGet(SsoConst.API_HOST+"/validationTicket.htm?t="+ticket, HttpUtils.CHARSET_UTF8);
		} catch (HttpException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if(result==null){
			return null;
		}
		
		JSONObject resultJson = JSONObject.fromObject(result);
		
		if(!resultJson.containsKey("key") || !resultJson.containsKey("vticket")){
			return null;
		}
		
		String key = resultJson.getString("key");
		String vticket = resultJson.getString("vticket");
		
		if(StringUtils.isEmpty(key) || StringUtils.isEmpty(vticket)){
			return null;
		}
		
		String velidateTicket="";
		try {
			velidateTicket = MD5.encode(key+ticket);
		} catch (NoSuchAlgorithmException e) {
		} catch (UnsupportedEncodingException e) {
		}
		
		if(!velidateTicket.equals(vticket)){
			return null;
		}
		
		if(!resultJson.getString("ssoUser").equals("null")){
			ssoUser = (SsoUser) JSONObject.toBean(resultJson.getJSONObject("ssoUser"), SsoUser.class);
		}
		
		return ssoUser;
	}
	
	public void logout(HttpServletRequest request, HttpServletResponse response, String sessionid){
		//得到票据，重置cookie
		String ticket=HttpUtils.getInstance().getCookie(request, SsoConst.TICKET_KEY, SsoConst.SSO_DOMAIN);
		try {
			HttpUtils.getInstance().httpGet(SsoConst.API_HOST+"/ssoLogout.htm?t="+ticket, HttpUtils.CHARSET_UTF8);
		} catch (HttpException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		clearnSessionUser(request, sessionid);
		HttpUtils.getInstance().setCookie(response, SsoConst.TICKET_KEY, null, SsoConst.SSO_DOMAIN, 0);
	}
	
	public SsoUser getSessionUser(HttpServletRequest request, String sessionid){
		String tickkey=HttpUtils.getInstance().getCookie(request, SsoConst.TICKET_KEY, SsoConst.SSO_DOMAIN);
		if(StringUtils.isEmpty(tickkey)){
			clearnSessionUser(request, sessionid);
			return null;
		}
		SsoUser ssoUser = null;
		if(sessionid==null){
			// TODO 使用session实现
			ssoUser = (SsoUser) request.getSession().getAttribute(SsoUser.SESSION_KEY);
		}else{
			// TODO 使用memcached实现
		}
		return ssoUser;
	}
	
	public void setSessionUser(HttpServletRequest request, SsoUser ssoUser, String sessionid){
		if(sessionid==null){
			// TODO 使用session实现
			request.getSession().setAttribute(SsoUser.SESSION_KEY, ssoUser);
		}else{
			// TODO 使用memcached实现
		}
	}
	
	public void clearnSessionUser(HttpServletRequest request, String sessionid){
		if(sessionid==null){
			// TODO 使用session实现
			request.getSession().removeAttribute(SsoUser.SESSION_KEY);
		}else{
			// TODO 使用memcached实现
		}
	}
	
	public String getSID(){
		return SsoConst.SESSION_KEY;
	}
	
	public String getDomain(){
		return SsoConst.SSO_DOMAIN;
	}
	
	public Integer getSessionAge(){
		return 69*60;
	}
}
