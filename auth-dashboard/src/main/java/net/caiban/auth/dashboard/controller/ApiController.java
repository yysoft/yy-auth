/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-4
 */
package net.caiban.auth.dashboard.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.caiban.auth.dashboard.dto.ExtResult;
import net.caiban.auth.dashboard.exception.ServiceException;
import net.caiban.auth.dashboard.service.bs.BsService;
import net.caiban.auth.dashboard.service.staff.StaffService;
import net.caiban.auth.sdk.AuthMenu;
import net.caiban.auth.sdk.SessionUser;
import net.caiban.utils.MD5;
import net.caiban.utils.cache.JedisUtil;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import redis.clients.jedis.Jedis;

import com.google.common.base.Strings;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-4
 */
@Controller
public class ApiController extends BaseController {
	
	@Resource
	private StaffService staffService;
	@Resource
	private BsService bsService;
	
	@RequestMapping
	public ModelAndView ssoUser(HttpServletRequest request, HttpServletResponse response, 
			Map<String, Object> out, String a, String pc, String pd){
		ExtResult result = new ExtResult();
		
		do {
			//验证用户登录信息是否正确
			String account=null;
			try {
				account = staffService.validateUser(a, pd, pc);
			} catch (ServiceException e1) {
				result.setData(e1.getMessage());
				break;
			}
			
			SessionUser sessionUser = staffService.initSessionUser(account, pc);
			
			//生成ticket
			String key=UUID.randomUUID().toString();
			String ppassword=bsService.queryPasswordOfBs(pc);
			String ticket=null;
			try {
				ticket = MD5.encode(a+pd+pc+ppassword+key);
			} catch (NoSuchAlgorithmException e) {
				result.setData("SERVER_EXCEPTION");
				break;
			} catch (UnsupportedEncodingException e) {
				result.setData("SERVER_EXCEPTION");
				break;
			}
			sessionUser.setKey(key);
			sessionUser.setTicket(ticket);
			
			//保存6小时
//			MemcachedUtils.getInstance().getClient().set(ticket, 6*60*60, sessionUser);
			
			Jedis jedis = null;
			try {
				jedis = JedisUtil.getPool().getResource();
				jedis.setex(ticket, 6*60*60, JSONObject.fromObject(sessionUser).toString());
			} catch (Exception e) {
				result.setData("SERVER_EXCEPTION");
				break;
			}finally{
				if(jedis!=null){
					JedisUtil.getPool().returnResource(jedis);
				}
			}
			result.setSuccess(true);
			result.setData(sessionUser);
			
			return printJson(result, out);
		} while (false);
		
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView ssoTicket(HttpServletRequest request, Map<String, Object> out, String t, String pc){
//		MemcachedUtils.getInstance().getClient(DesktopConst.CACHE_ZZWORK);
		//TODO 可以换成数据库方式实现
//		SessionUser sessionUser = (SessionUser) MemcachedUtils.getInstance().getClient().get(t);
		
		ExtResult result = new ExtResult();
		
		do {
			if(Strings.isNullOrEmpty(t)){
				result.setData("UNEXCEPT_TICKET");
				break;
			}
			
			Jedis jedis = null;
			String userStr = null;
			try {
				jedis = JedisUtil.getPool().getResource();
				userStr = jedis.get(t);
			} catch (Exception e) {
				result.setData("SERVER_EXCEPTION");
				break;
			}finally{
				if(jedis!=null){
					JedisUtil.getPool().returnResource(jedis);
				}
			}
			
			if(Strings.isNullOrEmpty(userStr)){
				result.setData("SESSION_TIMEOUT");
				break;
			}
			
			SessionUser sessionUser = (SessionUser) JSONObject.toBean(JSONObject.fromObject(userStr), SessionUser.class);
			
			if(sessionUser==null){
				result.setData("SESSION_TIMEOUT");
				break;
			}
			
			String key = UUID.randomUUID().toString();
			String ppassword=bsService.queryPasswordOfBs(pc);
			String vticket = null;
			try {
				vticket = MD5.encode(pc+ppassword+key);
			} catch (NoSuchAlgorithmException e) {
				result.setData("SERVER_EXCEPTION");
				break;
			} catch (UnsupportedEncodingException e) {
				result.setData("SERVER_EXCEPTION");
				break;
			}
			sessionUser.setVticket(vticket);
			sessionUser.setKey(key);
			
			sessionUser.setRightArr(staffService.queryRightOfStaff(sessionUser.getAccount(), pc));
			
			result.setSuccess(true);
			result.setData(sessionUser);
			return printJson(result, out);
		} while (false);
		
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView menu(HttpServletRequest request, Map<String, Object> out, String parentCode, String projectCode, String account){
		if(Strings.isNullOrEmpty(parentCode)){
			parentCode = bsService.queryRightCodeOfProject(projectCode);
		}
		List<AuthMenu> menu = staffService.queryMenuOfStaff(account, parentCode);
		
		return printJson(menu, out);
	}
	
	@RequestMapping
	public ModelAndView ssoLogout(HttpServletRequest request, Map<String, Object> out, String t){
//		MemcachedUtils.getInstance().getClient().delete(t);
		return printJson("{result:true}", out);
	}
	
	@RequestMapping
	public ModelAndView nameOfAccount(HttpServletRequest request, Map<String, Object> out, String a){
		return printJson("{'"+a+"':'"+staffService.queryNameOfAccount(a)+"'}", out);
	}
	
	@RequestMapping
	public ModelAndView staffOfDept(HttpServletRequest request, Map<String, Object> out, String dc){
		return printJson(staffService.queryStaffNameByDeptCode(dc), out);
	}
	@RequestMapping
	public ModelAndView queryStaffOfDept(HttpServletRequest request, Map<String, Object> out, String dc){
		return printJson(staffService.queryStaffByDeptCode(dc), out);
	}
}
