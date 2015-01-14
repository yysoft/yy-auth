package com.zz91.util.domain;

import java.io.Serializable;

public class UploadResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean success = false;
	private String error;
	private String uploadedFilename;
	private String submitedFIlename;
	private String prefix;
	private String path;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getUploadedFilename() {
		return uploadedFilename;
	}
	public void setUploadedFilename(String uploadedFilename) {
		this.uploadedFilename = uploadedFilename;
	}
	public String getSubmitedFIlename() {
		return submitedFIlename;
	}
	public void setSubmitedFIlename(String submitedFIlename) {
		this.submitedFIlename = submitedFIlename;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	
}
