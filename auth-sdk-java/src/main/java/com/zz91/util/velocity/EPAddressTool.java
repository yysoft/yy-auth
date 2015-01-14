/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-13
 */
package com.zz91.util.velocity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mays (mays@huanbao.com)
 *
 * created on 2011-6-13
 */
@Deprecated
public class EPAddressTool {
	
	private static Map<String, String> addressMap;

	public void init(Object obj) {
		if(addressMap==null){
			initAddress();
		}
	}
	
	private static void initAddress(){
		addressMap = new HashMap<String, String>();
		addressMap.put("www", "http://www.huanbao.com");
		addressMap.put("trade", "http://www.huanbao.com/trade");
		addressMap.put("news", "http://www.huanbao.com/news");
		addressMap.put("exhibit", "http://www.huanbao.com/exhibit");
		addressMap.put("company", "http://www.huanbao.com/company");
		addressMap.put("esite", "http://www.huanbao.com/esite");
		addressMap.put("myesite", "http://www.huanbao.com/myesite");
		
		addressMap.put("img", "http://img0.huanbao.com");
		addressMap.put("resource", "http://img1.zz91.com");
		
		addressMap.put("logo", "http://img0.huanbao.com/huanbao/logo.gif");
		addressMap.put("favicon", "http://img0.huanbao.com/huanbao/favicon.ico");
		
		addressMap.put("thumb", "http://image01.zz91.com/images.php?picurl=");
	}
	
	public String get(String key) {
		return addressMap.get(key);
	}

	public void configure(Map<Object, Object> params) {
		
	}
	
	public static String getAddress(String key){
		if(addressMap==null){
			initAddress();
		}
		return addressMap.get(key);
	}
	
}
