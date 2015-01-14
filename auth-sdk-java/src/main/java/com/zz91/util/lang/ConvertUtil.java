package com.zz91.util.lang;

import org.apache.commons.beanutils.ConvertUtils;

import com.mysql.jdbc.StringUtils;

public class ConvertUtil {
	public static int convertStringToInt(String value){
		if(value==null)
			return 0;
		return (Integer) ConvertUtils.convert(value, Integer.class);
	}
 
	public static long convertStringToLong(String value){
		if(value==null)
			return 0l;
		return (Long) ConvertUtils.convert(value, Long.class);
	}
	
	public static boolean convertStringToBoolean(String value){
		if(value==null)
			return false;
		return (Boolean)ConvertUtils.convert(value, Boolean.class);
	}
	public static void main(String [] fdsa){
//		System.out.println(ConvertUtil.convertStringToInt(" "));
//		System.out.println(ConvertUtil.convertStringToBoolean(""));
		
		System.out.println(StringUtils.isEmptyOrWhitespaceOnly("12"));
	}
}
