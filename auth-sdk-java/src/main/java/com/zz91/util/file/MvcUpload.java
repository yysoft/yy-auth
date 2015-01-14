/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-17
 */
package com.zz91.util.file;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.zz91.util.lang.StringUtils;

/**
 * SpringMVC上传工具
 * 
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-3-17
 */
public class MvcUpload {
	
	final static String DEFAULT_FIELD="uploadfile";
	final static String DEFAULT_ROOT="/usr/data/resources";
	
	public static final String[] WHITE_IMG={".jpg", ".jpeg", ".gif", ".bmp", ".png"};
	public static final String[] WHITE_DOC={".doc", ".docx", ".txt",".pdf"};
	public static final String[] BLOCK_ANY={".bat", ".sh", ".exe"};
	public static final long DEFAULT_SIZE=2*1024; //单位k

	public static Map<String, String> CNERROR=new HashMap<String, String>();
	
	static{
		CNERROR.put("001", "没有文件上传！");
		CNERROR.put("002", "没有扩展名，不允许上传！");
		CNERROR.put("003", "黑名单文件，不允许上传！");
		CNERROR.put("004", "不在白名单里，不允许上传！");
		CNERROR.put("005", "文件大小超过最大限制，不允许上传！");
	}

	public static String localUpload(HttpServletRequest request, String destPath,
			String destFilename) throws Exception{
		return localUpload(request, destPath, destFilename, 
				DEFAULT_FIELD, WHITE_IMG, BLOCK_ANY, DEFAULT_SIZE);
	}
	
	public static String localDocUpload(HttpServletRequest request, String destPath,
			String destFilename) throws Exception{
		return localUpload(request, destPath, destFilename, 
				DEFAULT_FIELD, WHITE_DOC, BLOCK_ANY, DEFAULT_SIZE);
	}
	
	public static String localUpload(HttpServletRequest request, String destPath,
			String destFilename, String fieldname, String[] whitelist, 
			String[] blocklist, long size) throws Exception {
		
		MultipartRequest multipartRequest = (MultipartRequest) request;
		
		MultipartFile file = multipartRequest.getFile(fieldname);
		
		String name = file.getOriginalFilename();

		if (StringUtils.isEmpty(name)) {
			throw new Exception("001");
		}
		
		String suffix=name.substring(name.lastIndexOf("."), name.length());
		suffix=suffix.toLowerCase();
		
		if(StringUtils.isEmpty(suffix)){
			throw new Exception("002"); //没有扩展名
		}
		
		//判断黑名单
		if(blocklist!=null){
			for(String s:blocklist){
				if(s.equals(suffix)){
					throw new Exception("003"); //黑名单禁止上传
				}
			}
		}
		
		//判断白名单
		boolean allow=false;
		if(whitelist!=null){
			for(String s:whitelist){
				if(s.equals(suffix)){
					allow=true;
					break;
				}
			}
			if(!allow){
				throw new Exception("004"); //不在白名单里
			}
		}
		
		//判断文件大小
		long filesize=size*1024;
		if(file.getSize()!=0 && file.getSize()>filesize){
			throw new Exception("005"); //文件太大了
		}
		
		if (StringUtils.isNotEmpty(destFilename)) {
			name = destFilename + suffix;
		}

		if (!destPath.endsWith("/")) {
			destPath = destPath + "/";
		}
		
		FileUtils.makedir(destPath);

		File upfile = new File(destPath + name);

		file.transferTo(upfile);
		
		return name;
	}
	
	public static String getDestRoot(){
		return DEFAULT_ROOT;
	}
	
	public static String getDateFolder() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.YEAR) + "/" + (now.get(Calendar.MONTH) + 1)
				+ "/" + now.get(Calendar.DAY_OF_MONTH);
	}
	
	public static String getErrorMessage(String msg){
		if(CNERROR.get(msg)!=null){
			return CNERROR.get(msg);
		}
		return msg;
	}
	
	public static String getModalPath(String modal){
		return getDestRoot()+"/"+modal+"/"+getDateFolder();
	}
}
