/**
 * Copyright 2010 YYSoft
 * All right reserved.
 * Created on 2010-5-9
 */
package net.caiban.auth.dashboard.init;

import java.util.List;

import javax.annotation.Resource;

import net.caiban.auth.dashboard.service.auth.ParamService;

import com.zz91.util.cache.JedisClientUtils;
import com.zz91.util.domain.Param;
import com.zz91.util.param.ParamUtils;

/**
 * @author Mays (x03570227@gmail.com)
 *
 */
public class InitSystem {

	@Resource
	private ParamService paramService;
	
	public void startup(){
		List<Param> paramList = paramService.queryParam();
		ParamUtils.getInstance().init(paramList, null);
		
		JedisClientUtils.getInstance().init();
//		MemcachedUtils.getInstance().init();
	}
	
	public void shutdown(){
//		MemcachedUtils.getInstance().shutdownClient();
	}

}
