/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-15
 */
package com.zz91.util.auth.jieneng;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.zz91.util.encrypt.MD5;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;

/**
 * @author Leon
 * 
 *         created on 2011-9-15
 */
public class JieNengAuthUtils {

	private static JieNengAuthUtils _instance = null;
	private static final String URLPREFIX = JieNengAuthConst.API_HOST; // URL前缀
	
	private JieNengAuthUtils() {

	}

	synchronized public static JieNengAuthUtils getInstance() {
		if (_instance == null) {
			_instance = new JieNengAuthUtils();
		}
		return _instance;
	}

	private final static int TIMEOUT = 100000; // TODO 发布后改为10秒＝10000

	/**
	 * 发送消息包含：account，password，projectCode
	 * ticket=account+password+projectCode+projectPassword+key
	 * 返回信息包含：jieNengAuthUser，ticket，key
	 */
	public JieNengAuthUser validateUser(HttpServletResponse response,
			String account, String password, Integer expired) {
		JieNengAuthUser jieNengAuthUser = null;
		try {
			String encodePwd = MD5.encode(password);
			// 提交给服务器验证
			URL url = new URL(URLPREFIX + "/jieNengAuthUser.htm?a=" + account + "&p=" + encodePwd+"&project="+JieNengAuthConst.PROJECT);
			Document doc = Jsoup.parse(url, TIMEOUT);
			jieNengAuthUser = new JieNengAuthUser();
			String jieNengAuthUserObj=doc.select("body").text();
			if(!jieNengAuthUserObj.startsWith("{")){
				
				return null;
			}
			JSONObject json = JSONObject.fromObject(jieNengAuthUserObj);
			jieNengAuthUser.setUid((Integer)(json.get("uid")));
			jieNengAuthUser.setCid((Integer)(json.get("cid")));
			jieNengAuthUser.setMemberCode(json.get("memberCode").toString());
			String rights = json.get("rightList").toString();
			rights=rights.replace("[", "").replace("]", "").replace(" ", "").replace("\"", "");
			jieNengAuthUser.setRightList(rights.split(","));
			
			jieNengAuthUser.setKey(json.get("key").toString());
			jieNengAuthUser.setTicket(json.get("ticket").toString());

			HttpUtils.getInstance().setCookie(response, JieNengAuthConst.TICKET_KEY,
					jieNengAuthUser.getTicket(), JieNengAuthConst.JIENENG_DOMAIN, null);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return jieNengAuthUser;
	}
	
	
	/**
	 * 发送信息包含：ticket，projectCode
	 * vticket=projectCode+projectPassword+key
	 * 返回信息包含：EpAuthUser，vticket，key
	 */
	public JieNengAuthUser validateTicket(HttpServletRequest request){
		URL url;
		JieNengAuthUser jieNengAuthUser = null;
		String ticket=HttpUtils.getInstance().getCookie(request, JieNengAuthConst.TICKET_KEY, JieNengAuthConst.JIENENG_DOMAIN);
		if(StringUtils.isEmpty(ticket)){
			return null;
		}
		try {
			url = new URL(URLPREFIX + "/jieNengAuthTicket.htm?t=" + ticket+"&project="+JieNengAuthConst.PROJECT);
			Document doc = Jsoup.parse(url, TIMEOUT);
			
			String jieNengAuthUserObj=doc.select("body").text();
			if(!jieNengAuthUserObj.startsWith("{")){
				return null;
			}
			
			JSONObject json = JSONObject.fromObject(jieNengAuthUserObj);
//			String key=json.get("key").toString();
//			if(StringUtils.isEmpty(key)){
//				return null;
//			}
			
			jieNengAuthUser = new JieNengAuthUser();
			
			jieNengAuthUser.setUid((Integer)(json.get("uid")));
			jieNengAuthUser.setCid((Integer)(json.get("cid")));
			jieNengAuthUser.setMemberCode(json.get("memberCode").toString());
			jieNengAuthUser.setTicket(ticket);
			String rights = json.get("rightList").toString();
			rights=rights.replace("[", "").replace("]", "").replace(" ", "").replace("\"", "");
			jieNengAuthUser.setRightList(rights.split(","));

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return jieNengAuthUser;
	}
	public void logout(HttpServletRequest request,
			HttpServletResponse response, String sessionid) {
		// 得到票据，重置cookie
		String ticket = HttpUtils.getInstance().getCookie(request,
				JieNengAuthConst.TICKET_KEY, JieNengAuthConst.JIENENG_DOMAIN);
		URL url;
		try {
			url = new URL(URLPREFIX + "/jieNengAuthLogout.htm?t=" + ticket);
			Jsoup.parse(url, TIMEOUT);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		clearnJieNengAuthUser(request, sessionid);
		HttpUtils.getInstance().setCookie(response, JieNengAuthConst.TICKET_KEY,
				null, JieNengAuthConst.JIENENG_DOMAIN, 0);
	}
	
	public boolean authorizeRight(String rightContent, HttpServletRequest request, String sessionid){
		if(StringUtils.isEmpty(rightContent)){
			return false;
		}
		
		JieNengAuthUser sessionUser = getJieNengAuthUser(request, sessionid);
		String[] rightArr=sessionUser.getRightList();
		if(rightArr==null){
			return false;
		}
		
		for(String s:rightArr){
			if(rightContent.equals(s)){
				return true;
			}
		}
		return false;
	}

	public JieNengAuthUser getJieNengAuthUser(HttpServletRequest request,
			String sessionid) {
		String tickkey = HttpUtils.getInstance().getCookie(request,
				JieNengAuthConst.TICKET_KEY, JieNengAuthConst.JIENENG_DOMAIN);
		if (StringUtils.isEmpty(tickkey)) {
			clearnJieNengAuthUser(request, sessionid);
			return null;
		}
		JieNengAuthUser jieNengAuthUser = null;
		if (sessionid == null) {
			// TODO 使用session实现
			jieNengAuthUser = (JieNengAuthUser) request.getSession().getAttribute(
					JieNengAuthUser.SESSION_KEY);
		} else {
			// TODO 使用memcached实现
		}
		return jieNengAuthUser;
	}

	public void setJieNengAuthUser(HttpServletRequest request, JieNengAuthUser ssoUser,
			String sessionid) {
		if (sessionid == null) {
			// TODO 使用session实现
			request.getSession().setAttribute(JieNengAuthUser.SESSION_KEY, ssoUser);
		} else {
			// TODO 使用memcached实现
		}
	}

	public void clearnJieNengAuthUser(HttpServletRequest request, String sessionid) {
		if (sessionid == null) {
			// TODO 使用session实现
			request.getSession().removeAttribute(JieNengAuthUser.SESSION_KEY);
		} else {
			// TODO 使用memcached实现
		}
	}
	
	public static void main(String[] args) {
		JieNengAuthConst.PROJECT="myesite";
		JieNengAuthConst.API_HOST="http://localhost:880/admin/api";
		JieNengAuthConst.JIENENG_DOMAIN="huanbao.com";
		JieNengAuthConst.PROJECT_PASSWORD="135246";
	}
	
}
