/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-6
 */
package net.caiban.auth.dashboard.service.auth.impl;

import java.util.List;

import javax.annotation.Resource;

import net.caiban.auth.dashboard.dao.auth.ParamDao;
import net.caiban.auth.dashboard.service.auth.ParamService;

import org.springframework.stereotype.Component;

import com.zz91.util.domain.Param;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-6
 */
@Component("paramService")
public class ParamServiceImpl implements ParamService {

	@Resource
	private ParamDao paramDao;
	
	@Override
	public List<Param> queryParam() {
		
		return paramDao.queryParam();
	}

}
