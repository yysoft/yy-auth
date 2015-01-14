/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-13
 */
package com.zz91.util.velocity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.zz91.util.file.FileUtils;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-6-13
 */
public class AddressTool {
	
	private static Map<String, String> addressMap;
	private static String PROP=null;

	public void init(Object obj) {
		if(addressMap==null){
			initAddress();
		}
	}
	
	@SuppressWarnings("unchecked")
	private static void initAddress(){
		addressMap = new HashMap<String, String>();
//		addressMap.put("front", "http://china.zz91.com");
//		addressMap.put("map", "http://map.zz91.com");
//		addressMap.put("price", "http://price.zz91.com");
//		addressMap.put("exhibit", "http://exhibit.zz91.com");
//		
//		addressMap.put("huzhu", "http://huzhu.zz91.com");
//		addressMap.put("trade", "http://trade.zz91.com");
//		addressMap.put("subject", "http://subject.zz91.com");
//		addressMap.put("myrc", "http://myrc.zz91.com");
//		addressMap.put("company", "http://company.zz91.com");
//		addressMap.put("score", "http://score.zz91.com");
//		addressMap.put("admin", "http://admin1949.zz91.com");
//		addressMap.put("passport", "http://passport.zz91.com");
//		addressMap.put("esite", "http://esite.zz91.com");
//		addressMap.put("tags", "http://tags.zz91.com");
//		
//		addressMap.put("img", "http://img0.zz91.com");
//		addressMap.put("resource", "http://img1.zz91.com");
//		
//		addressMap.put("logo", "http://img0.zz91.com/logo.gif");
//		addressMap.put("favicon", "http://img0.zz91.com/favicon.ico");
//		
//		addressMap.put("server", "http://china.zz91.com");
//		
//		addressMap.put("images", "http://img0.zz91.com/front/images");
//		addressMap.put("css", "http://img0.zz91.com/front/css");
//		addressMap.put("js", "http://img0.zz91.com/front/js");
//		
//		addressMap.put("thumb", "http://image01.zz91.com/images.php?picurl=");
		
		if(PROP==null){
			Map<String, String> map=null;
			try {
				map = FileUtils.readPropertyFile("web.properties", "utf-8");
			} catch (IOException e) {
			}
			if (map!=null) {
				PROP = map.get("address.prop");
			}
		}
		
		try {
			Map<String, String> m=FileUtils.readPropertyFile(PROP, "utf-8");
			addressMap.putAll(m);
		} catch (IOException e1) {
		}
	}
	
	public String get(String key) {
		if(addressMap==null){
			initAddress();
		}
		return addressMap.get(key);
	}

	@SuppressWarnings("unchecked")
	public void configure(Map<Object, Object> params) {
//		PROP = (String) params.get("prop");
		if(PROP==null){
			Map<String, String> map=null;
			try {
				map = FileUtils.readPropertyFile("web.properties", "utf-8");
			} catch (IOException e) {
			}
			if (map!=null) {
				PROP = map.get("address.prop");
			}
		}
	}
	
	public static String getAddress(String key){
		if(addressMap==null){
			initAddress();
		}
		return addressMap.get(key);
	}
	
}
