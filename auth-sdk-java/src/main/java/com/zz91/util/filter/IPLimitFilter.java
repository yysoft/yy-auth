/*
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-13 上午11:05:58
 */
package com.zz91.util.filter;

import java.io.IOException;
import java.io.PrintWriter;
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

import org.apache.log4j.Logger;

import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.http.IP2Long;
import com.zz91.util.lang.StringUtils;

/**
 *
 * 日志信息
 * log4j.logger.com.zz91.util.http.iplimit=info, limitlog
 * 
 * log4j.appender.limitlog = org.apache.log4j.DailyRollingFileAppender
 * log4j.appender.limitlog.File = /usr/data/iplimit/limit
 * log4j.appender.limitlog.Append = true
 * log4j.appender.limitlog.Threshold = info
 * log4j.appender.limitlog.layout = org.apache.log4j.PatternLayout
 * log4j.appender.limitlog.layout.ConversionPattern =%m%n
 * 
 * @author mays(mays@zz91.net)
 * created by 2011-12-26
 */
public class IPLimitFilter implements Filter {
	
	final static Logger LOG=Logger.getLogger("com.zz91.util.http.iplimit");
	final static String BLOCK_PREFIX="ib.";
	final static String RECORD_PREFIX="ir.";
	static Set<Long> excludeIP=new HashSet<Long>(); 
	static int TIME_RANG=10;
	static int MAX_VISIT=0; //等于0表示关闭自动加黑功能，默认关闭
	static int DISABLE_TIME=300;
	
	
	public void init(FilterConfig config) throws ServletException {
		String whiteIP = config.getInitParameter("excludeIP");
		if(whiteIP!=null){
			String[] ipArr=whiteIP.split(",");
			for(String ip:ipArr){
				if(ip.contains("-")){
					String[] ipRang=ip.split("-");
					Long rangFrom=IP2Long.ipToLong(ipRang[0]);
					Long rangTo=IP2Long.ipToLong(ipRang[1]);
					for(long i=rangFrom;i<=rangTo;i++){
						excludeIP.add(i);
					}
				}else{
					excludeIP.add(IP2Long.ipToLong(ip));
				}
			}
		}
		
		String timerang=config.getInitParameter("timeRang");
		if(StringUtils.isNumber(timerang)){
			TIME_RANG=Integer.valueOf(timerang);
		}
		
		String maxvisit=config.getInitParameter("maxVisit");
		if(StringUtils.isNumber(maxvisit)){
			MAX_VISIT=Integer.valueOf(maxvisit);
		}
		
		String diabletime=config.getInitParameter("disableTime");
		if(StringUtils.isNumber(diabletime)){
			DISABLE_TIME=Integer.valueOf(diabletime);
		}
		
	}

	public void destroy() {
	}
	
	public void doFilter(ServletRequest rq, ServletResponse rp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request= (HttpServletRequest) rq;
		HttpServletResponse response = (HttpServletResponse) rp;
		
		String ip=HttpUtils.getInstance().getIpAddr(request);
		Long ipl=IP2Long.ipToLong(ip);
		
		//如果符合白名单条件
		if(isWhiteIP(ipl)){
			chain.doFilter(request, response);
			return ;
		}
		
		response.setCharacterEncoding("utf-8");
		if(MAX_VISIT==0){
			PrintWriter pw=response.getWriter();
			pw.print("You are not allowed!");
			pw.close();
			LOG.info("{\"ip\":"+ip+",\"time\":"+System.currentTimeMillis()+",\"\":\""+request.getRequestURI()+"\"}");
			return ;
		}
		
		do {
			//判断IP是否在黑名单内
			Integer disableFlag=(Integer) MemcachedUtils.getInstance().getClient().get(BLOCK_PREFIX+ipl);
			if(disableFlag!=null && disableFlag.intValue()==1){
				MemcachedUtils.getInstance().getClient().set(BLOCK_PREFIX+ip, DISABLE_TIME, 1);
				break;
			}
			//判断IP是否符合黑名单条件（10秒内的历史访问次数>100）
			if(isLimit(ipl)){
				break;
			}
			chain.doFilter(request, response);
			return ;
		} while (false);
		
		
		PrintWriter pw=response.getWriter();
		pw.print("对不起，您的访问过于频繁，服务器忙不过来了，喝杯咖啡休息下吧！");
		pw.close();
		LOG.info("{\"ip\":"+ip+",\"time\":"+System.currentTimeMillis()+",\"\":\""+request.getRequestURI()+"\"}");
		return ;
	}
	
	private boolean isLimit(Long ip){
		long now=System.currentTimeMillis();
		String c=(String) MemcachedUtils.getInstance().getClient().get(RECORD_PREFIX+ip);
		Integer visitNum=1;
		do {
			//第一次访问或长时间不访问，没有IP记录
			if(c==null || !c.contains(",")){
				break;
			}
			
			//距离IP记录大于10秒，将重新计数
			Long last=Long.valueOf(c.substring(0, c.indexOf(",")));
			if((now-last.longValue())>(TIME_RANG*1000)){
				break;
			}
			
			//10秒内访问次数小于100次
			visitNum=Integer.valueOf(c.substring(c.indexOf(",")+1));
			if(visitNum.intValue()<MAX_VISIT){
				visitNum++;
				break;
			}
			
			MemcachedUtils.getInstance().getClient().set(BLOCK_PREFIX+ip, DISABLE_TIME, 1);
			
			return true;
		} while (false);
		
		MemcachedUtils.getInstance().getClient().set(RECORD_PREFIX+ip, 10, now+","+visitNum);
		return false;
	}
	
	private boolean isWhiteIP(Long ip){
		return excludeIP.contains(ip);
	}
	
	public static void main(String[] args) throws InterruptedException {
//		MemcachedUtils.getInstance().createClient("127.0.0.1:11211");
//		MemcachedUtils.getInstance().getClient().set("test", 10, "test0001");
//		Thread.sleep(9000);
//		System.out.println(MemcachedUtils.getInstance().getClient().get("test"));
//		MemcachedUtils.getInstance().createClient("192.168.2.178:11211");
//		MemcachedUtils.getInstance().getClient().delete("ib."+IP2Long.ipToLong("127.0.0.1"));
//		MemcachedUtils.getInstance().getClient().delete("ir."+IP2Long.ipToLong("127.0.0.1"));
//		MemcachedUtils.getInstance().shutdownClient();
		
	}

}
