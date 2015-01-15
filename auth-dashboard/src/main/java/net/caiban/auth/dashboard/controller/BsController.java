/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-9
 */
package net.caiban.auth.dashboard.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.caiban.auth.dashboard.domain.bs.Bs;
import net.caiban.auth.dashboard.domain.bs.BsAvatar;
import net.caiban.auth.dashboard.dto.ExtResult;
import net.caiban.auth.dashboard.dto.ExtTreeDto;
import net.caiban.auth.dashboard.dto.PageDto;
import net.caiban.auth.dashboard.dto.bs.BsDto;
import net.caiban.auth.dashboard.dto.staff.StaffDto;
import net.caiban.auth.dashboard.service.bs.BsService;
import net.caiban.utils.lang.StringUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-9
 */
@Controller
public class BsController extends BaseController{

	@Resource
	private BsService bsService;
	
	final static int PASSWORD_LENGTH = 16;
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		out.put("initcode", UUID.randomUUID());
		out.put("initpassword", StringUtils.randomString(PASSWORD_LENGTH));
		return null;
	}
	
	@RequestMapping
	public ModelAndView deptChild(HttpServletRequest request, Map<String, Object> out, String parentCode, Integer bsId){
		List<ExtTreeDto> deptTree = bsService.queryDeptTreeNode(parentCode, bsId);
		return printJson(deptTree, out);
	}
	
	@RequestMapping
	public ModelAndView queryBs(HttpServletRequest request, Map<String, Object> out, PageDto<Bs> page, Bs bs){
		page=bsService.pageBs(bs, page);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView deleteBs(HttpServletRequest request, Map<String, Object> out, Integer id){
		Integer i= bsService.deleteBs(id);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView createBs(HttpServletRequest request, Map<String, Object> out, Bs bs){
		Integer i = bsService.createBs(bs);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		result.setData(UUID.randomUUID()+"|"+StringUtils.randomString(PASSWORD_LENGTH));
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView queryOneBs(HttpServletRequest request, Map<String, Object> out, Integer id){
		BsDto dto=bsService.queryOneBs(id);
		PageDto<BsDto> page=new PageDto<BsDto>();
		List<BsDto> list=new ArrayList<BsDto>();
		list.add(dto);
		page.setRecords(list);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView updateBs(HttpServletRequest request, Map<String, Object> out, Bs bs){
		Integer i=bsService.updateBs(bs);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView queryBsStaff(HttpServletRequest request, Map<String, Object> out, Integer bsId){
		List<StaffDto> list = bsService.queryBsStaff(bsId);
		PageDto<StaffDto> page=new PageDto<StaffDto>();
		page.setRecords(list);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView deleteBsStaff(HttpServletRequest request, Map<String, Object> out, Integer bsId, Integer staffId){
		Integer i = bsService.deleteBsStaff(bsId, staffId);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView createBsStaff(HttpServletRequest request, Map<String, Object> out, Integer bsId, String account){
		Integer i = bsService.createBsStaff(bsId, account);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateBsDept(HttpServletRequest request, Map<String, Object> out, Integer bsId, Integer deptId, Boolean checked){
		Integer i = bsService.updateBsDept(bsId, deptId, checked);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView avatar(HttpServletRequest request, Map<String, Object> out){
		List<BsAvatar> list=new ArrayList<BsAvatar>();
		list.add(new BsAvatar("/images/bsicon/user-2.png","user-2.png"));
		list.add(new BsAvatar("/images/bsicon/security.png","security.png"));
		list.add(new BsAvatar("/images/bsicon/cd.png","cd.png"));
		list.add(new BsAvatar("/images/bsicon/soccer-ball.png","soccer-ball.png"));
		list.add(new BsAvatar("/images/bsicon/clipboard.png","clipboard.png"));
		list.add(new BsAvatar("/images/bsicon/toilet-paper.png","toilet-paper.png"));
		list.add(new BsAvatar("/images/bsicon/earth.png","earth.png"));
		list.add(new BsAvatar("/images/bsicon/block.png","block.png"));
		list.add(new BsAvatar("/images/bsicon/pen.png","pen.png"));
		list.add(new BsAvatar("/images/bsicon/poo.png","poo.png"));
		list.add(new BsAvatar("/images/bsicon/apple-red.png","apple-red.png"));
		list.add(new BsAvatar("/images/bsicon/xls.png","xls.png"));
		list.add(new BsAvatar("/images/bsicon/print.png","print.png"));
		list.add(new BsAvatar("/images/bsicon/settings.png","settings.png"));
		list.add(new BsAvatar("/images/bsicon/movie.png","movie.png"));
		list.add(new BsAvatar("/images/bsicon/apple-green.png","apple-green.png"));
		list.add(new BsAvatar("/images/bsicon/flag.png","flag.png"));
		list.add(new BsAvatar("/images/bsicon/burn.png","burn.png"));
		list.add(new BsAvatar("/images/bsicon/pack-2.png","pack-2.png"));
		list.add(new BsAvatar("/images/bsicon/music-1.png","music-1.png"));
		list.add(new BsAvatar("/images/bsicon/text.png","text.png"));
		list.add(new BsAvatar("/images/bsicon/tree.png","tree.png"));
		list.add(new BsAvatar("/images/bsicon/pdf.png","pdf.png"));
		list.add(new BsAvatar("/images/bsicon/pack-1.png","pack-1.png"));
		list.add(new BsAvatar("/images/bsicon/photo.png","photo.png"));
		list.add(new BsAvatar("/images/bsicon/credit-card.png","credit-card.png"));
		list.add(new BsAvatar("/images/bsicon/lock.png","lock.png"));
		list.add(new BsAvatar("/images/bsicon/alert.png","alert.png"));
		list.add(new BsAvatar("/images/bsicon/dvd.png","dvd.png"));
		list.add(new BsAvatar("/images/bsicon/music-2.png","music-2.png"));
		list.add(new BsAvatar("/images/bsicon/download.png","download.png"));
		list.add(new BsAvatar("/images/bsicon/toy-2 copy.png","toy-2 copy.png"));
		list.add(new BsAvatar("/images/bsicon/cd-copy.png","cd-copy.png"));
		list.add(new BsAvatar("/images/bsicon/ruler.png","ruler.png"));
		list.add(new BsAvatar("/images/bsicon/coke.png","coke.png"));
		list.add(new BsAvatar("/images/bsicon/basketball.png","basketball.png"));
		list.add(new BsAvatar("/images/bsicon/clock.png","clock.png"));
		list.add(new BsAvatar("/images/bsicon/note.png","note.png"));
		list.add(new BsAvatar("/images/bsicon/skull.png","skull.png"));
		list.add(new BsAvatar("/images/bsicon/search.png","search.png"));
		list.add(new BsAvatar("/images/bsicon/wireless.png","wireless.png"));
		list.add(new BsAvatar("/images/bsicon/color-palette.png","color-palette.png"));
		list.add(new BsAvatar("/images/bsicon/dvd-copy.png","dvd-copy.png"));
		list.add(new BsAvatar("/images/bsicon/tennis-ball.png","tennis-ball.png"));
		list.add(new BsAvatar("/images/bsicon/unlock.png","unlock.png"));
		list.add(new BsAvatar("/images/bsicon/user-1.png","user-1.png"));
		list.add(new BsAvatar("/images/bsicon/8ball.png","8ball.png"));
		list.add(new BsAvatar("/images/bsicon/app.png","app.png"));
		list.add(new BsAvatar("/images/bsicon/bell.png","bell.png"));
		list.add(new BsAvatar("/images/bsicon/lamp.png","lamp.png"));
		list.add(new BsAvatar("/images/bsicon/chat.png","chat.png"));
		list.add(new BsAvatar("/images/bsicon/id-card.png","id-card.png"));
		list.add(new BsAvatar("/images/bsicon/flower.png","flower.png"));
		list.add(new BsAvatar("/images/bsicon/t-shirt.png","t-shirt.png"));
		list.add(new BsAvatar("/images/bsicon/toy.png","toy.png"));
		list.add(new BsAvatar("/images/bsicon/upload.png","upload.png"));
		list.add(new BsAvatar("/images/bsicon/recycle.png","recycle.png"));
		list.add(new BsAvatar("/images/bsicon/date.png","date.png"));
		list.add(new BsAvatar("/images/bsicon/burger.png","burger.png"));
		list.add(new BsAvatar("/images/bsicon/pencil-2.png","pencil-2.png"));
		list.add(new BsAvatar("/images/bsicon/doc.png","doc.png"));
		list.add(new BsAvatar("/images/bsicon/music-3.png","music-3.png"));
		list.add(new BsAvatar("/images/bsicon/edit.png","edit.png"));
		list.add(new BsAvatar("/images/bsicon/note-2.png","note-2.png"));
		list.add(new BsAvatar("/images/bsicon/twitter.png","twitter.png"));
		list.add(new BsAvatar("/images/bsicon/help.png","help.png"));
		return printJson(list, out);
	}
	
//	public static void main(String[] args) {
//		List<String> list=FileUtils.listFile("/root/iconpng");
//		for(String fileName:list){
//			System.out.println("list.add(new BsAvatar(\""+fileName.replace("/root/iconpng/", "/images/bsicon/")+"\",\""+fileName.replace("/root/iconpng/", "")+"\"));");
//		}
//	}
	
}
