/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-13
 */
package com.zz91.util.auth.jieneng;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;

/**
 * @author Leon
 *
 * created on 2011-9-15	
 */
public class JieNengAuthFilter implements Filter{

	private String loginURL = "";
	
	private Set<String> noLoginPage;
	private Set<String> noAuthPage;
	
	private Set<String> mustLoginPage;

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest rq, ServletResponse rp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request= (HttpServletRequest) rq;
		HttpServletResponse response = (HttpServletResponse) rp;

		String uri = request.getRequestURI();
		String path = request.getContextPath();
		path= path==null?"":path;
		
		StringBuffer refURL=request.getRequestURL();
		String url=loginURL+"?url="+refURL;

		JieNengAuthUser jieNengAuthUser = JieNengAuthUtils.getInstance().getJieNengAuthUser(request, null);
		String tickkey = HttpUtils.getInstance().getCookie(request,
				JieNengAuthConst.TICKET_KEY, JieNengAuthConst.JIENENG_DOMAIN);
		if(jieNengAuthUser == null || !tickkey.equals(jieNengAuthUser.getTicket())){
			jieNengAuthUser = JieNengAuthUtils.getInstance().validateTicket(request);
			if(jieNengAuthUser!=null){
				JieNengAuthUtils.getInstance().setJieNengAuthUser(request, jieNengAuthUser, null);
			}
		}
		request.setAttribute("jieNengAuthUser", jieNengAuthUser);
		
		do {

			if(filterByConfig(noLoginPage, path, uri)){
				chain.doFilter(request, response);
				return ;
			}
			
//			if(!filterByConfig(mustLoginPage, path, uri)){
//				chain.doFilter(request, response);
//				return ;
//			}
			
			if(jieNengAuthUser==null){
				break;
			}
			
			if(filterByConfig(noAuthPage, path, uri)){
				chain.doFilter(request, response);
				return ;
			}
			
			if(!filterByAuth(jieNengAuthUser.getRightList(), request)){
				break;
			}
			
			chain.doFilter(request, response);
			return ;
		} while (false);
		
		//AJAX请求权限过滤
		if(request.getHeader("x-requested-with")!=null
				&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
			response.setHeader("sessionstatus","timeout");
			response.setHeader("redirectUrl",path+url);
			return ;
		}
		
		if(url.startsWith("http")){
			response.sendRedirect(url);
		}else{
			response.sendRedirect(path+url);
		}
		return ;
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
//		deniedURL = config.getInitParameter("deniedURL");
		loginURL = config.getInitParameter("loginURL");
		
//		projectCode = config.getInitParameter("projectCode");
//		projectPassword = config.getInitParameter("projectPassword");
		
		String tmp[]= null;
		noLoginPage = new HashSet<String>();
		String e1=config.getInitParameter("noLoginPage");
		if(StringUtils.isNotEmpty(e1)){
			tmp=e1.split("\\|");
			for(String ex:tmp){
				noLoginPage.add(ex);
			}
		}

		noAuthPage = new HashSet<String>();
		String e2=config.getInitParameter("noAuthPage");
		if(StringUtils.isNotEmpty(e2)){
			tmp=e2.split("\\|");
			for(String ex:tmp){
				noAuthPage.add(ex);
			}
		}
		
		mustLoginPage = new HashSet<String>();
		String e3=config.getInitParameter("mustLoginPage");
		if(StringUtils.isNotEmpty(e3)){
			tmp=e3.split("\\|");
			for(String ex:tmp){
				mustLoginPage.add(ex);
			}
		}
	}
	
	public boolean filterByConfig(Set<String> exclude, String path,String uri){
		for(String url:exclude){
			url=path+url;
			if(url.startsWith("*")){
				if(uri.endsWith(url.substring(1))){
					return true;
				}
			}else if(url.endsWith("*")){
				if(uri.startsWith(url.substring(0, url.length()-1))){
					return true;
				}
			}else{
				if(url.equals(uri)){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean filterByAuth(String[] rightArr, HttpServletRequest request){
		if(rightArr==null || rightArr.length<=0){
			return false;
		}
		
		String query = request.getQueryString();
		String contextPath = request.getContextPath();
		String uri = request.getRequestURI();
		if(uri.startsWith(contextPath)){
			uri=uri.substring(contextPath.length(),uri.length());
		}
		String url = uri+(query == null ? "" : "?"+query);

		boolean ispass=false;
		for(String s:rightArr){
			if(url.matches(s.trim())){
				ispass=true;
				break;
			}
		}
		return ispass;
	}
}
