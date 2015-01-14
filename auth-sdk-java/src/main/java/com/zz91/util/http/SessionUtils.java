package com.zz91.util.http;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.lang.StringUtils;

/**
 * 通过Memcached模拟Session
 * @author mays
 *
 */
public class SessionUtils {
	
	public void setValue(HttpServletRequest request, HttpServletResponse response, String key, Object value){
		
		String sessionKey = getSessionKey(getSessionId(request, response), key);
		
		MemcachedUtils.getInstance().getClient().set(sessionKey, getSessionAge(), value);
	}
	
	public Object getValue(HttpServletRequest request, HttpServletResponse response, String key){
		String sessionKey = getSessionKey(getSessionId(request, response), key);
		
		return MemcachedUtils.getInstance().getClient().get(sessionKey);
	}
	
	public void remove(HttpServletRequest request, String key){
		String sessionId = HttpUtils.getInstance().getCookie(request, getSID(), getDomain());
		
		if(StringUtils.isEmpty(sessionId)){
			return ;
		}
		
		String sessionKey = getSessionKey(sessionId, key);
		MemcachedUtils.getInstance().getClient().delete(sessionKey);
	}
	
	public String getSessionId(HttpServletRequest request, HttpServletResponse response){
		String sessionId = HttpUtils.getInstance().getCookie(request, getSID(), getDomain());
		
		if(StringUtils.isEmpty(sessionId)){
			sessionId=UUID.randomUUID().toString();
			HttpUtils.getInstance().setCookie(response, getSID(), sessionId, getDomain(), getCookieAge());
		}
		
		return sessionId;
	}
	
	

	public String getSID(){
		return "sessionid";
	}
	
	public String getDomain(){
		return "zz91.com";
	}
	
	public Integer getCookieAge(){
		return null;
	}
	
	public Integer getSessionAge(){
		return 20*60;
	}
	
	public String getSessionKey(String sessionId, String key){
		return sessionId+"."+key;
	}
}
