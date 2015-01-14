/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-4
 */
package net.caiban.auth.sdk;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.zz91.util.encrypt.MD5;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;

/**
 * @author mays (mays@caiban.net)
 *
 * created on 2015-1-14
 */
public class AuthClient {

	private static AuthClient _instance=null;
	
	private AuthClient(){
		
	}
	
	synchronized public static AuthClient getInstance(){
		if(_instance==null){
			_instance = new AuthClient();
		}
		return _instance;
	}
	
	private final static int TIMEOUT=10000;
	
	/**
	 * 发送消息包含：account，password，projectCode
	 * ticket=account+password+projectCode+projectPassword+key
	 * 返回信息包含：sessionUser，ticket，key
	 */
	public SessionUser validateUser(HttpServletResponse response, String account, String password, String pcode, String ppassword){
		SessionUser sessionUser = null;
		
	
			try {
				String encodePwd=MD5.encode(password);
				//提交给服务器验证
				URL url = new URL(AuthConst.API_HOST+"/ssoUser.htm?a="+account+"&pc="+pcode+"&pd="+encodePwd);
				Document doc = Jsoup.parse(url, TIMEOUT);
				//验证返回结果是否正确
				String key=doc.select("#key").val();
				String ticket=doc.select("#ticket").val();
				if(StringUtils.isEmpty(key) || StringUtils.isEmpty(ticket)){
					return null;
				}
				
				String validateTicket=MD5.encode(account+encodePwd+pcode+ppassword+key);
				if(!ticket.equals(validateTicket)){
					return null;
				}
				
				sessionUser = new SessionUser();
				sessionUser.setAccount(doc.select("#account").val());
				sessionUser.setDeptCode(doc.select("#deptCode").val());
				sessionUser.setName(doc.select("#name").val());
				sessionUser.setStaffNo(doc.select("#staffNo").val());
				
				String right = doc.select("#rightArr").val();
				sessionUser.setRightArr(right.split("\\|"));

				HttpUtils.getInstance().setCookie(response, AuthConst.TICKET_KEY, ticket, AuthConst.SSO_DOMAIN, null);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		
		return sessionUser;
	}
	
	/**
	 * 发送信息包含：ticket，projectCode
	 * vticket=projectCode+projectPassword+key
	 * 返回信息包含：SessionUser，vticket，key
	 */
	public SessionUser validateTicket(HttpServletRequest request, String pcode, String ppassword){
		URL url;
		SessionUser sessionUser = null;
		String ticket=HttpUtils.getInstance().getCookie(request, AuthConst.TICKET_KEY, AuthConst.SSO_DOMAIN);
		if(StringUtils.isEmpty(ticket)){
			return null;
		}
		try {
			url = new URL(AuthConst.API_HOST+"/ssoTicket.htm?t="+ticket+"&pc="+pcode);
			Document doc = Jsoup.parse(url, TIMEOUT);
			String key = doc.select("#key").val();
			String vticket = doc.select("#vticket").val();
			if(StringUtils.isEmpty(key) || StringUtils.isEmpty(vticket)){
				return null;
			}
			//验证vticket
			String velidateTicket = MD5.encode(pcode+ppassword+key);
			if(!velidateTicket.equals(vticket)){
				return null;
			}
			
			sessionUser = new SessionUser();
			sessionUser.setAccount(doc.select("#account").val());
			sessionUser.setDeptCode(doc.select("#deptCode").val());
			sessionUser.setName(doc.select("#name").val());
			sessionUser.setStaffNo(doc.select("#staffNo").val());
			
			sessionUser.setTicket(ticket);
			
			String right = doc.select("#rightArr").val();
			sessionUser.setRightArr(right.split("\\|"));
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return sessionUser;
	}
	
	public void logout(HttpServletRequest request, HttpServletResponse response, String sessionid){
		//得到票据，重置cookie
		String ticket=HttpUtils.getInstance().getCookie(request, AuthConst.TICKET_KEY, AuthConst.SSO_DOMAIN);
		URL url;
		try {
			url = new URL(AuthConst.API_HOST+"/ssoLogout.htm?t="+ticket);
			Jsoup.parse(url, TIMEOUT);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		clearnSessionUser(request, sessionid);
		HttpUtils.getInstance().setCookie(response, AuthConst.TICKET_KEY, null, AuthConst.SSO_DOMAIN, 0);
	}
	
	public String queryStaffNameOfAccount(String account){
		URL url;
		String name="";
		try {
			url = new URL(AuthConst.API_HOST+"/nameOfAccount.htm?a="+account);
			Document doc=Jsoup.parse(url, TIMEOUT);
			name = JSONObject.fromObject(doc.select("body").text()).getString(account);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return name;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> queryStaffOfDept(String deptCode){
		URL url;
		Map<String, String> map=new HashMap<String, String>();
		try {
			url = new URL(AuthConst.API_HOST+"/staffOfDept.htm?dc="+deptCode);
			Document doc=Jsoup.parse(url, TIMEOUT);
			JSONObject json = JSONObject.fromObject(doc.select("body").text());
			Set<String> ks=json.keySet();
			for(String k:ks){
				map.put(k, json.getString(k));
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@SuppressWarnings("rawtypes")
	public List<JSONObject> queryStaffByDept(String deptCode){
		URL url;
		List<JSONObject> list=new ArrayList<JSONObject>();
		try {
			url = new URL(AuthConst.API_HOST+"/queryStaffOfDept.htm?dc="+deptCode);
			Document doc=Jsoup.parse(url, TIMEOUT);
			JSONArray jsonarray=JSONArray.fromObject(doc.select("body").text());
			for (Iterator iter = jsonarray.iterator(); iter.hasNext();) {
				JSONObject object = (JSONObject) iter.next();
				list.add(object);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@SuppressWarnings( { "deprecation", "unchecked" })
	public List<AuthMenu> queryMenuByParent(String parentCode, String pcode, String account){
		URL url;
		
		try {
			url = new URL(AuthConst.API_HOST+"/menu.htm?parentCode="+parentCode+"&projectCode="+pcode+"&account="+account);
			Document doc = Jsoup.parse(url, TIMEOUT);
			String menu=doc.select("body").html().replace("&quot;", "'").trim();
			List<AuthMenu> list = JSONArray.toList(JSONArray.fromObject(menu), AuthMenu.class);
			return list;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean authorizeRight(String rightContent, HttpServletRequest request, String sessionid){
		if(StringUtils.isEmpty(rightContent)){
			return false;
		}
		
		SessionUser sessionUser = getSessionUser(request, sessionid);
		String[] rightArr=sessionUser.getRightArr();
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
	
	public SessionUser getSessionUser(HttpServletRequest request, String sessionid){
		String tickkey=HttpUtils.getInstance().getCookie(request, AuthConst.TICKET_KEY, AuthConst.SSO_DOMAIN);
		if(StringUtils.isEmpty(tickkey)){
			clearnSessionUser(request, sessionid);
			return null;
		}
		SessionUser sessionUser = null;
		if(sessionid==null){
			// TODO 使用session实现
			sessionUser = (SessionUser) request.getSession().getAttribute(SessionUser.SESSION_KEY);
		}else{
			// TODO 使用memcached实现
		}
		return sessionUser;
	}
	
	public void setSessionUser(HttpServletRequest request, SessionUser sessionUser, String sessionid){
		if(sessionid==null){
			// TODO 使用session实现
			request.getSession().setAttribute(SessionUser.SESSION_KEY, sessionUser);
		}else{
			// TODO 使用memcached实现
		}
	}
	
	public void clearnSessionUser(HttpServletRequest request, String sessionid){
		if(sessionid==null){
			// TODO 使用session实现
			request.getSession().removeAttribute(SessionUser.SESSION_KEY);
		}else{
			// TODO 使用memcached实现
		}
	}
	
	public static void main(String[] args) {
		URL url;
		try {
			url = new URL("http://work.zz9l.com:880/work/api/nameOfAccount.htm?a=mays");
			//url = new URL("http://work.zz9l.com:880/work/api/staffOfDept.htm?deptCode=1000");
			Document doc=Jsoup.parse(url, TIMEOUT);
			System.out.println(doc.select("body").text());
			String o=doc.select("body").text();
			JSONObject json = JSONObject.fromObject(o);
			System.out.println(json.get("mays"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
