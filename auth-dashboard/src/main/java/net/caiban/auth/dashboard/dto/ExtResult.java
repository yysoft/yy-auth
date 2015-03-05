/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-11-11
 */
package net.caiban.auth.dashboard.dto;

import java.io.Serializable;

/**
 * @author root
 *
 * created on 2010-11-11
 */
public class ExtResult implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Boolean success;
    private Boolean error;
    private Object data;
    
	public ExtResult() {
	}

	/**
	 * @param success
	 * @param error
	 * @param data
	 */
	public ExtResult(Boolean success, Boolean error, Object data) {
		this.success = success;
		this.error = error;
		this.data = data;
	}

	/**
	 * @return the success
	 */
	public Boolean getSuccess() {
		if(success==null){
			success=false;
		}
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(Boolean success) {
		this.success = success;
	}

	/**
	 * @return the error
	 */
	public Boolean getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(Boolean error) {
		this.error = error;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}
    
    
}
