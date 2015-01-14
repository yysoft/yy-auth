package net.caiban.auth.dashboard.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class DesktopFilter
 */
public class DesktopFilter implements Filter {

    /**
     * Default constructor. 
     */
    public DesktopFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest rq, ServletResponse rp, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) rq;
		HttpServletResponse response = (HttpServletResponse) rp;
		request.setAttribute("contextPath", request.getContextPath());
		request.setAttribute("contextJs", request.getContextPath()+"/js");
		request.setAttribute("contextThemes", request.getContextPath()+"/themes");
		
		request.setAttribute("contextResources", request.getContextPath()+"/themes");
		
		request.setAttribute("systemName", "员工工作平台");
		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
