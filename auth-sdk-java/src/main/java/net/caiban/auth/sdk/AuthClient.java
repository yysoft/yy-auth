/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-4
 */
package net.caiban.auth.sdk;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.caiban.utils.MD5;
import net.caiban.utils.http.CookiesUtil;
import net.caiban.utils.http.HttpRequestUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.log4j.Logger;

import com.google.common.base.Strings;

/**
 * @author mays (mays@caiban.net)
 *
 * created on 2015-1-14
 */
public class AuthClient {

	private static AuthClient _instance=null;
	
	private AuthClient(){
		
	}
	
	final static Logger LOG = Logger.getLogger(AuthClient.class);
	
	synchronized public static AuthClient getInstance(){
		if(_instance==null){
			_instance = new AuthClient();
		}
		return _instance;
	}
	
//	private final static int TIMEOUT=10000;
	
	/**
	 * 发送消息包含：account，password，projectCode
	 * ticket=account+password+projectCode+projectPassword+key
	 * 返回信息包含：sessionUser，ticket，key
	 * @throws YYAuthException 
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	public SessionUser validateUser(HttpServletResponse response, String account, String password, String pcode, String ppassword) throws YYAuthException{
		SessionUser sessionUser = null;
		
		String encodePwd=null;
		try {
			encodePwd = MD5.encode(password);
		} catch (NoSuchAlgorithmException e) {
			throw new YYAuthException("INVALID_PASSWORD", e);
		} catch (UnsupportedEncodingException e) {
			throw new YYAuthException("INVALID_PASSWORD", e);
		}
		
		String resp = HttpRequestUtil.httpGet(AuthConst.API_HOST+"/ssoUser.htm?a="+account+"&pc="+pcode+"&pd="+encodePwd);
		
		if(!JSONUtils.mayBeJSON(resp)){
			LOG.debug("Auth API is not avalible. response is :"+resp);
			throw new YYAuthException("NETWORK_ERROR");
		}
		
		JSONObject respJson = JSONObject.fromObject(resp);
		
		boolean success = respJson.optBoolean("success", false);
		
		if(!success){
			String error = "NETWORK_ERROR";
			if(JSONUtils.isString(respJson.get("data"))){
				error = respJson.getString("data");
			}
			throw new YYAuthException(error);
		}
		
		sessionUser = (SessionUser) JSONObject.toBean(respJson.getJSONObject("data"), SessionUser.class);
		
		if(sessionUser==null){
			throw new YYAuthException("INVALID_SESSION_USER");
		}
		
		String key = sessionUser.getKey();
		String ticket = sessionUser.getTicket();
		
		if(Strings.isNullOrEmpty(key) || Strings.isNullOrEmpty(ticket)){
			throw new YYAuthException("INVALID_TICKET");
		}
		
		String validateTicket=null;
		try {
			validateTicket = MD5.encode(account+encodePwd+pcode+ppassword+key);
		} catch (NoSuchAlgorithmException e) {
			throw new YYAuthException("INVALID_TICKET", e);
		} catch (UnsupportedEncodingException e) {
			throw new YYAuthException("INVALID_TICKET", e);
		}
		
		if(!ticket.equals(validateTicket)){
			throw new YYAuthException("INVALID_TICKET");
		}
		
		CookiesUtil.setCookie(response, AuthConst.TICKET_KEY, ticket, AuthConst.SSO_DOMAIN, null);
		
		return sessionUser;
	}
	
	/**
	 * 发送信息包含：ticket，projectCode
	 * vticket=projectCode+projectPassword+key
	 * 返回信息包含：SessionUser，vticket，key
	 * @throws YYAuthException 
	 */
	public SessionUser validateTicket(HttpServletRequest request, String pcode, String ppassword) throws YYAuthException{

		String ticket=CookiesUtil.getCookie(request, AuthConst.TICKET_KEY, AuthConst.SSO_DOMAIN);
		if(Strings.isNullOrEmpty(ticket)){
			throw new YYAuthException("INVALID_LOCAL_TICKET");
		}
		
		String resp = HttpRequestUtil.httpGet(AuthConst.API_HOST+"/ssoTicket.htm?t="+ticket+"&pc="+pcode);
		
		if(!JSONUtils.mayBeJSON(resp)){
			LOG.debug("Auth API is not avalible. response is :"+resp);
			throw new YYAuthException("NETWORK_ERROR");
		}
		
		JSONObject respJson = JSONObject.fromObject(resp);
		
		SessionUser sessionUser = (SessionUser) JSONObject.toBean(respJson.getJSONObject("data"), SessionUser.class);
		
		if(sessionUser==null){
			throw new YYAuthException("INVALID_SESSION_USER");
		}
		
		String key = sessionUser.getKey();
		String vticket = sessionUser.getVticket();
		
		if(Strings.isNullOrEmpty(key) || Strings.isNullOrEmpty(vticket)){
			throw new YYAuthException("INVALID_TICKET");
		}
		
		String velidateTicket=null;
		try {
			velidateTicket = MD5.encode(pcode+ppassword+key);
		} catch (NoSuchAlgorithmException e) {
			throw new YYAuthException("INVALID_TICKET", e);
		} catch (UnsupportedEncodingException e) {
			throw new YYAuthException("INVALID_TICKET", e);
		}
		
		if(!velidateTicket.equals(vticket)){
			throw new YYAuthException("INVALID_TICKET");
		}
		
		return sessionUser;
	}
	
	public void logout(HttpServletRequest request, HttpServletResponse response, String sessionid){
		//得到票据，重置cookie
		String ticket=CookiesUtil.getCookie(request, AuthConst.TICKET_KEY, AuthConst.SSO_DOMAIN);
		
		HttpRequestUtil.httpGet(AuthConst.API_HOST+"/ssoLogout.htm?t="+ticket);

		clearnSessionUser(request, sessionid);
		CookiesUtil.setCookie(response, AuthConst.TICKET_KEY, null, AuthConst.SSO_DOMAIN, 0);
		
	}
	
	public String queryStaffNameOfAccount(String account){
		
		String resp = HttpRequestUtil.httpGet(AuthConst.API_HOST+"/nameOfAccount.htm?a="+account);
		if(resp!=null && JSONUtils.mayBeJSON(resp)){
			JSONObject jobj = JSONObject.fromObject(resp);
			return jobj.optString(account, "");
		}
		
		return "";
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> queryStaffOfDept(String deptCode){
		
		String resp = HttpRequestUtil.httpGet(AuthConst.API_HOST+"/staffOfDept.htm?dc="+deptCode);
		
		Map<String, String> map=new HashMap<String, String>();

		if(resp!=null && JSONUtils.mayBeJSON(resp)){
			JSONObject json = JSONObject.fromObject(resp);
			
			Set<String> ks=json.keySet();
			for(String k:ks){
				map.put(k, json.getString(k));
			}
		}
		
		return map;
	}
	
	@SuppressWarnings("rawtypes")
	public List<JSONObject> queryStaffByDept(String deptCode){
		
		String resp = HttpRequestUtil.httpGet(AuthConst.API_HOST+"/queryStaffOfDept.htm?dc="+deptCode);
		
		List<JSONObject> list=new ArrayList<JSONObject>();

		if(resp!=null && JSONUtils.mayBeJSON(resp)){
			JSONArray jsonarray=JSONArray.fromObject(resp);
			for (Iterator iter = jsonarray.iterator(); iter.hasNext();) {
				JSONObject object = (JSONObject) iter.next();
				list.add(object);
			}
		}
		
		return list;
	}
	
	@SuppressWarnings( { "deprecation", "unchecked" })
	public List<AuthMenu> queryMenuByParent(String parentCode, String pcode, String account){
		
		String resp = HttpRequestUtil.httpGet(AuthConst.API_HOST+"/menu.htm?parentCode="+parentCode+"&projectCode="+pcode+"&account="+account);
		
		if(resp==null || !JSONUtils.mayBeJSON(resp)){
			return null;
		}
		
		JSONArray menuJson = JSONArray.fromObject(resp);
		List<AuthMenu> list = JSONArray.toList(menuJson, AuthMenu.class);
		return list;
//		JsonConfig config = new JsonConfig();
//		JSONArray.toList(menuJson, AuthMenu.class, config);
		
		
		//TODO UNCOMPLETE
		
//		URL url;
//		
//		try {
//			url = new URL(AuthConst.API_HOST+"/menu.htm?parentCode="+parentCode+"&projectCode="+pcode+"&account="+account);
//			Document doc = Jsoup.parse(url, TIMEOUT);
//			String menu=doc.select("body").html().replace("&quot;", "'").trim();
//			List<AuthMenu> list = JSONArray.toList(JSONArray.fromObject(menu), AuthMenu.class);
//			return list;
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
	}
	
	public boolean authorizeRight(String rightContent, HttpServletRequest request, String sessionid){
		if(Strings.isNullOrEmpty(rightContent)){
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
		String tickkey=CookiesUtil.getCookie(request, AuthConst.TICKET_KEY, AuthConst.SSO_DOMAIN);
		if(Strings.isNullOrEmpty(tickkey)){
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
//		URL url;
//		try {
//			url = new URL("http://work.zz9l.com:880/work/api/nameOfAccount.htm?a=mays");
//			//url = new URL("http://work.zz9l.com:880/work/api/staffOfDept.htm?deptCode=1000");
//			Document doc=Jsoup.parse(url, TIMEOUT);
//			System.out.println(doc.select("body").text());
//			String o=doc.select("body").text();
//			JSONObject json = JSONObject.fromObject(o);
//			System.out.println(json.get("mays"));
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		String resp = HttpRequestUtil.httpGet("http://test.caiban.net:8080/auth/api/ssoUser.htm?a=mays&pc=16449732-13b0-48c2-8eef-6784ecf903bf&pd=2fe54263d7742123");
		
		JSONObject respJson = JSONObject.fromObject(resp);
		SessionUser sessionUser = (SessionUser) JSONObject.toBean(respJson.getJSONObject("data"), SessionUser.class);
		
		System.out.println(sessionUser.getAccount());
		
		HttpRequestUtil.shutdown();
	}
}
