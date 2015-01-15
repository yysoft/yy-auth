/**
 * Copyright 2010 YYSoft
 * All right reserved.
 * Created on 2010-5-9
 */
package net.caiban.auth.dashboard.init;

import net.caiban.utils.cache.JedisUtil;


/**
 * @author Mays (x03570227@gmail.com)
 *
 */
public class InitSystem {

	public void startup(){
		
		JedisUtil.initPool("cache.properties");
	}
	
	public void shutdown(){
		JedisUtil.getPool().destroy();
	}

}
