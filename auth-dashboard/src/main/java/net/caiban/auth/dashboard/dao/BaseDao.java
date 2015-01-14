/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-5
 */
package net.caiban.auth.dashboard.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-5
 */
public class BaseDao extends SqlMapClientDaoSupport {

	public String buildId(String prefix, String sqlId){
		return prefix+"."+sqlId;
	}
}
