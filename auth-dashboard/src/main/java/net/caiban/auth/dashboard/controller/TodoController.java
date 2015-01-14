/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-21
 */
package net.caiban.auth.dashboard.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-6-21
 */
@Controller
public class TodoController extends BaseController {

	@RequestMapping
	public ModelAndView mini(HttpServletRequest request, Map<String, Object> out){
		return null;
	}
}
