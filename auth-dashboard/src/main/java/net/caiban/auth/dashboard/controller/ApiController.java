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
		
		do {
			//验证用户登录信息是否正确
			String account = staffService.validateUser(a, pd, pc);
			if(Strings.isNullOrEmpty(account)){
				break;
			}
			SessionUser sessionUser = staffService.initSessionUser(account, pc);

//			//验证用户是否允许使用该系统
//			if(!bsService.allowAccess(account, pc)){
//				break;
//			}
			
			//生成ticket
			String key=UUID.randomUUID().toString();
			String ppassword=bsService.queryPasswordOfBs(pc);
			String ticket=null;
			try {
				ticket = MD5.encode(a+pd+pc+ppassword+key);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			sessionUser.setKey(key);
			sessionUser.setTicket(ticket);
			
			out.put("user", sessionUser);
			
			//保存6小时
//			MemcachedUtils.getInstance().getClient().set(ticket, 6*60*60, sessionUser);
			
			Jedis jedis = null;
			try {
				jedis = JedisUtil.getJedis();
				jedis.setex(ticket, 6*60*60, JSONObject.fromObject(sessionUser).toString());
			} catch (Exception e) {
			}finally{
				if(jedis!=null){
					JedisUtil.getPool().returnResource(jedis);
				}
			}
			
//			JedisClientUtils.getInstance().getClient().setex(ticket, 6*60*60, JSONObject.fromObject(sessionUser).toString());
			
		} while (false);
		
		return new ModelAndView("/api/ssoUser");
	}
	
	@RequestMapping
	public ModelAndView ssoTicket(HttpServletRequest request, Map<String, Object> out, String t, String pc){
//		MemcachedUtils.getInstance().getClient(DesktopConst.CACHE_ZZWORK);
		//TODO 可以换成数据库方式实现
//		SessionUser sessionUser = (SessionUser) MemcachedUtils.getInstance().getClient().get(t);
		
		Jedis jedis = null;
		
		String userStr = null;
				
		try {
			jedis = JedisUtil.getJedis();
			userStr = jedis.get(t);
		} catch (Exception e) {
		}finally{
			if(jedis!=null){
				JedisUtil.getPool().returnResource(jedis);
			}
		}
		
		do {
			
			if(Strings.isNullOrEmpty(userStr)){
				break;
			}
			
			SessionUser sessionUser = (SessionUser) JSONObject.toBean(JSONObject.fromObject(userStr), SessionUser.class);
			
			if(sessionUser==null){
				break;
			}
			
			String key = UUID.randomUUID().toString();
			String ppassword=bsService.queryPasswordOfBs(pc);
			String vticket = null;
			try {
				vticket = MD5.encode(pc+ppassword+key);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			sessionUser.setVticket(vticket);
			sessionUser.setKey(key);
			
			sessionUser.setRightArr(staffService.queryRightOfStaff(sessionUser.getAccount(), pc));
			
			out.put("user", sessionUser);
		} while (false);
		return null;
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
