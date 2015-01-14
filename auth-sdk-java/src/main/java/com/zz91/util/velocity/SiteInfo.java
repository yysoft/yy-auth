/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-13
 */
package com.zz91.util.velocity;

import java.util.Map;

import com.zz91.util.lang.StringUtils;
import com.zz91.util.param.ParamUtils;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-6-13
 */
@Deprecated
public class SiteInfo {
	
	private static String PROP_KEY = "site_info_front"; 

	public void init(Object obj) {
		
	}
	
	public String get(String key) {
		return ParamUtils.getInstance().getValue(PROP_KEY, key);
	}

	public void configure(Map<Object, Object> params) {
		String key=(String) params.get("propkey");
		if(StringUtils.isNotEmpty(key)){
			PROP_KEY = key;
		}
	}
}
