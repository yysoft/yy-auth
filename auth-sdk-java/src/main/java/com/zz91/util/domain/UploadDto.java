package com.zz91.util.domain;

import java.io.Serializable;

public class UploadDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String tmpFolder = "/tmp/";
	private String rootFolder = "resources/";
	private String uploadFolder = "nomodel/";
	private String[] allowFileType = { ".jpg", ".jpeg", ".gif", ".bmp", ".png" }; // 白名单
	private String[] deniedFileType = {".bat", ".sh", ".exe"};
	private int sizeThreshold = 4096;
	private int sizeMax = 2 * 1024 * 1024;
	
	//remote file
	private String remoteHostIp;  //远程主机IP
    private String account;       //登陆账户
    private String password;      //登陆密码
    private String shareDocName;  //共享文件夹名称
    
	public String getTmpFolder() {
		return tmpFolder;
	}
	public void setTmpFolder(String tmpFolder) {
		this.tmpFolder = tmpFolder;
	}
	public String getUploadFolder() {
		return uploadFolder;
	}
	public void setUploadFolder(String uploadFolder) {
		this.uploadFolder = uploadFolder;
	}
	public String[] getAllowFileType() {
		return allowFileType;
	}
	public void setAllowFileType(String[] allowFileType) {
		this.allowFileType = allowFileType;
	}
	public String[] getDeniedFileType() {
		return deniedFileType;
	}
	public void setDeniedFileType(String[] deniedFileType) {
		this.deniedFileType = deniedFileType;
	}
	public int getSizeThreshold() {
		return sizeThreshold;
	}
	public void setSizeThreshold(int sizeThreshold) {
		this.sizeThreshold = sizeThreshold;
	}
	public int getSizeMax() {
		return sizeMax;
	}
	public void setSizeMax(int sizeMax) {
		this.sizeMax = sizeMax;
	}
	public String getRemoteHostIp() {
		return remoteHostIp;
	}
	public void setRemoteHostIp(String remoteHostIp) {
		this.remoteHostIp = remoteHostIp;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getShareDocName() {
		return shareDocName;
	}
	public void setShareDocName(String shareDocName) {
		this.shareDocName = shareDocName;
	}
	public String getRootFolder() {
		return rootFolder;
	}
	public void setRootFolder(String rootFolder) {
		this.rootFolder = rootFolder;
	}
}
