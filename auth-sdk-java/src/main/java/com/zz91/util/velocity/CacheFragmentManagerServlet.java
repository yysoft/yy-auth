/**
 * 
 */
package com.zz91.util.velocity;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zz91.util.lang.StringUtils;

/**
 * 用于管理页面缓存功能的状态
 * 
 * 配置：
 * <servlet>
 * 	<servlet-name>cacheFragmentServlet</servlet-name>
 * 	<servlet-class>com.zz91.util.velocity.CacheFragmentManagerServlet</servlet-class>
 * </servlet>
 * <servlet-mapping>
 * 	<servlet-name>cacheFragmentServlet</servlet-name>
 * 	<url-pattern>/fragment/manager.servlet</url-pattern>
 * </servlet-mapping>
 * 
 * @author root
 *
 */
public class CacheFragmentManagerServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	public CacheFragmentManagerServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");

		String v=request.getParameter("v");
		String vm="";
		if(StringUtils.isNotEmpty(v) && StringUtils.isNumber(v) && !CacheFragmentDirective.CACHE_VERSION.equals(v)){
			CacheFragmentDirective.CACHE_VERSION=v;
			vm="version is reseted";
		}
		String d=request.getParameter("d");
		String dm="";
		if(StringUtils.isNotEmpty(v)){
			if("on".equals(d) && !CacheFragmentDirective.DEBUG){
				CacheFragmentDirective.DEBUG=true;
				dm="Debug is on";
			}
			if("off".equals(d) && CacheFragmentDirective.DEBUG){
				CacheFragmentDirective.DEBUG=false;
				dm="Debug is off";
			}
		}
		
		StringBuffer sb=new StringBuffer();
		sb.append("<form action='' method='post'>");
		sb.append("Cache version: <input name='v' style='width:20px;' type='text' value='").append(CacheFragmentDirective.CACHE_VERSION).append("' />");
		sb.append("<span style='color:red' >").append(vm).append("</span><br/>");
		sb.append("debug model: ");
		if(CacheFragmentDirective.DEBUG){
			sb.append("<input type='radio' name='d' value='on' checked='true'/>on ");
			sb.append("<input type='radio' name='d' value='off' />off");
		}else{
			sb.append("<input type='radio' name='d' value='on' />on ");
			sb.append("<input type='radio' name='d' value='off' checked='true'/>off");
		}
		sb.append("<span style='color:red' >").append(dm).append("</span><br/>");
		sb.append("<input type='submit' value='保存' /></form>");
		
		sb.append("<br />");
		sb.append("README:<br />");
		sb.append("version: Change version will reset cache of all page fragment<br />");
		sb.append("debug: Ignore cache if status is on<br />");
		
		PrintWriter pw = response.getWriter();
		
		pw.write(sb.toString());
		pw.close();
	}
}
