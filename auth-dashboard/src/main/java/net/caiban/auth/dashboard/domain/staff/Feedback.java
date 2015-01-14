/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-19
 */
package net.caiban.auth.dashboard.domain.staff;

import java.io.Serializable;
import java.util.Date;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-19
 */
public class Feedback implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer bsId;
	private String topic;
	private String content;
	private String account;
	private Integer status;
	private Date gmtCreated;
	private Date gmtModified;
	
	
	
	public Feedback() {
		// TODO Auto-generated constructor stub
	}
	
	public Feedback(Integer id, Integer bsId, String topic, String content,
			String account, Integer status, Date gmtCreated, Date gmtModified) {
		this.id = id;
		this.bsId = bsId;
		this.topic = topic;
		this.content = content;
		this.account = account;
		this.status = status;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}
	
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the bsId
	 */
	public Integer getBsId() {
		return bsId;
	}
	/**
	 * @param bsId the bsId to set
	 */
	public void setBsId(Integer bsId) {
		this.bsId = bsId;
	}
	/**
	 * @return the topic
	 */
	public String getTopic() {
		return topic;
	}
	/**
	 * @param topic the topic to set
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * @return the gmtCreated
	 */
	public Date getGmtCreated() {
		return gmtCreated;
	}
	/**
	 * @param gmtCreated the gmtCreated to set
	 */
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	/**
	 * @return the gmtModified
	 */
	public Date getGmtModified() {
		return gmtModified;
	}
	/**
	 * @param gmtModified the gmtModified to set
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
