/*
 * Copyright 2009 ASTO.
 * All right reserved.
 * Created on 2009-12-12 by Ryan.
 */
package com.zz91.util.filter;

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

import org.apache.log4j.Logger;

import com.zz91.util.lang.StringUtils;

/**
 *
 * 这个过滤器用于当用户没有指明具体页面时，转向到目录下默认的页面（index.htm）
 *
 * @author Ryan
 *
 */
@Deprecated
public class HomePageFilter implements Filter {

	private static Logger LOG =Logger.getLogger(HomePageFilter.class);
	
	private String homePageUri = "";
	private Set<String> excludePath=new HashSet<String>();

	public void destroy() {
		
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		// 1, 判断当前用户访问的地址是否是根地址(没有指明要访问的网页)
		String servletPath = request.getServletPath();

		if(excludePath.contains(servletPath)){
			chain.doFilter(req, res);
			return ;
		}
		
		if (!servletPath.contains(".")) {
			if (!servletPath.endsWith("/")&&!homePageUri.startsWith("/")) {
				homePageUri = "/" + homePageUri;
			}
			if(servletPath.endsWith("/")&&homePageUri.startsWith("/")){
				servletPath=servletPath.substring(0,servletPath.length()-1);
			}
			LOG.debug("the user accessed:" + servletPath
					+ ",now forward request to the real page:" + servletPath+homePageUri);
			request.getRequestDispatcher(servletPath + homePageUri).forward(req, res);
		}
		// 2 如果用户不是访问根路径,不做处理
		else {
			chain.doFilter(req, res);
		}
	}

	public void init(FilterConfig cfg) throws ServletException {
		homePageUri = cfg.getInitParameter("uri");
		String exclude=cfg.getInitParameter("exclude");
		if(StringUtils.isNotEmpty(exclude)){
			String[] excludePages=exclude.split(",");
			for(String s:excludePages){
				excludePath.add(s);
			}
		}
	}

}
