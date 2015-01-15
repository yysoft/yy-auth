/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-6
 */
package net.caiban.auth.dashboard.dao.auth.impl;

import java.util.List;

import net.caiban.auth.dashboard.dao.BaseDao;
import net.caiban.auth.dashboard.dao.auth.ParamDao;
import net.caiban.utils.param.Param;

import org.springframework.stereotype.Component;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-6
 */
@Component("paramDao")
public class ParamDaoImpl extends BaseDao implements ParamDao {
	
	@SuppressWarnings("unchecked")
	public List<Param> queryParam(){
		return getSqlMapClientTemplate().queryForList(buildId("param", "queryParam"));
	}
}
