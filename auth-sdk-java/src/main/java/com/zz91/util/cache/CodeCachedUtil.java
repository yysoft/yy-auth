package com.zz91.util.cache;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 通用类别缓存工具
 * @author mays (mays@zz91.net)
 *
 */
public class CodeCachedUtil {

	public final static String CACHE_TYPE_AREA="ca";
	public final static String CACHE_TYPE_TRADE="ct";
	public final static String CACHE_TYPE_NEWS="cn";
	public final static String CACHE_TYPE_EXHIBIT="ce";
	public final static String CACHE_TYPE_PARAM = "pa";
	public final static String CACHE_TYPE_SUBNET = "sb";// 环保子网类别
	
	public final static Integer EXPIRATION=0;
	
	private static Logger LOG = Logger.getLogger(CodeCachedUtil.class);
	
	@SuppressWarnings("unchecked")
	public static void init(Map<String, String> map, String cacheType, String cache){
		
		LOG.debug("["+cacheType+"]Loading category from map... ");
		if(cacheType==null){
			throw new IllegalArgumentException("Error loading category, cause by: cacheType is null");
		}
		
		for (String key:map.keySet()) {
			if(key==null){
				continue;
			}
			
			putObject(cacheType+"@"+key, map.get(key));
			
			String childKey=cacheType+"@list@";
			if(key.length()>4){
				childKey=childKey+key.substring(0, key.length()-4);
			}
			
			Map<String, String> childMap=(Map<String, String>) holdObject(childKey);
			if(childMap==null ){
				childMap = new LinkedHashMap<String, String>();
			}
			childMap.put(key, map.get(key));
			putObject(childKey, childMap);
		}
	}
	
	public static String getValue(String cacheType, String key){
		return (String) holdObject(cacheType+"@"+key);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, String> getChild(String cacheType, String code) {
		if(code==null){
			return null;
		}
		return (Map<String, String>) holdObject(cacheType+"@list@"+code);
	}
	
	public static void putObject(String key, Object value) {
		MemcachedUtils.getInstance().getClient().set(key, CodeCachedUtil.EXPIRATION,
				value);
	}

	public static Object holdObject(String key) {
		return MemcachedUtils.getInstance().getClient().get(key);
	}
	
}
