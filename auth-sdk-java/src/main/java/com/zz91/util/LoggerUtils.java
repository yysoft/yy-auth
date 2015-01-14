package com.zz91.util;

import java.util.HashMap;

import org.apache.log4j.Logger;

@Deprecated
public class LoggerUtils {
	private static HashMap<String,Logger> loggerMap=new HashMap<String,Logger>();
	private static final String DEFAULT_LOGGER="[out info]";
	private static Logger getLogger(String name){
		if(!loggerMap.containsKey(name)){
			addLogger(name);
		}
		return loggerMap.get(name);
	}
	private static void addLogger(String name){
		Logger logger= Logger.getLogger(name);
		loggerMap.put(name, logger);		
	}
	//debug
	public static void debug(String message){
		debug(DEFAULT_LOGGER,message);
	}
	public static void debug(Class<?> clazz,String message){
		debug(clazz.getName(),message);
	}
	public static void debug(String loggerName,String message){
		getLogger(loggerName).debug(getMessage(loggerName,message));
	}
	//info
	public static void info(String message){
		debug(DEFAULT_LOGGER,message);
	}
	public static void info(Class<?> clazz,String message){
		debug(clazz.getName(),message);
	}
	public static void info(String loggerName,String message){
		getLogger(loggerName).debug(getMessage(loggerName,message));
	}
	//warning
	public static void warn(String message){
		debug(DEFAULT_LOGGER,message);
	}
	public static void warn(Class<?> clazz,String message){
		debug(clazz.getName(),message);
	}
	public static void warn(String loggerName,String message){
		getLogger(loggerName).warn(getMessage(loggerName,message));
	}
	//error
	public static void error(String message){
		debug(DEFAULT_LOGGER,message);
	}
	public static void error(Class<?> clazz,String message){
		debug(clazz.getName(),message);
	}
	public static void error(String loggerName,String message){
		getLogger(loggerName).warn(getMessage(loggerName,message));
	}
	
	private static String getMessage(String loggerName,String message){
		return loggerName+":"+message;
	}
	public static void main(String[] args){
		LoggerUtils.info(LoggerUtils.class, "info");
	}
}
