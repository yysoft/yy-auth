/**
 * Project name: zz91-utils
 * File name: MailInfoDomain.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.zz91.util.mail;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author kongsj
 * @email kongsj@zz91.net
 * @date 2011-11-3
 */
public class MailInfoDomain implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -6236426916325992172L;
    private String sender;// 发送者
    private String receiver;// 接收者
    private String emailTitle;// email标题
    private String templateId;// 模板编号
    private Map<String, Object> emailParameter;// 正文内容参数
    private Date gmtPost;// 发送时间
    @Deprecated
    private Boolean isImmediate;// 是否立即发送
    private String username; // smtp 帐号
    private String password; // smtp 密码
    private String accountCode; // 账户code
    
    private Integer priority;

    /**
     * @param string
     * @param string2
     * @param string3
     * @param string4
     * @param map
     * @param date
     * @param b
     */
    public MailInfoDomain() {

    }

    public MailInfoDomain(String sender, String receiver, String emailTitle, String templateId, Map<String, Object> emailParameter,
            Date gmtPost, boolean isImmediate) {
        this.sender = sender;
        this.receiver = receiver;
        this.emailTitle = emailTitle;
        this.templateId = templateId;
        this.emailParameter = emailParameter;
        this.gmtPost = gmtPost;
        this.isImmediate = isImmediate;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getEmailTitle() {
        return emailTitle;
    }

    public void setEmailTitle(String emailTitle) {
        this.emailTitle = emailTitle;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public Map<String, Object> getEmailParameter() {
        return emailParameter;
    }

    public void setEmailParameter(Map<String, Object> emailParameter) {
        this.emailParameter = emailParameter;
    }

    public Date getGmtPost() {
        return gmtPost;
    }

    public void setGmtPost(Date gmtPost) {
        this.gmtPost = gmtPost;
    }

    public Boolean getIsImmediate() {
        return isImmediate;
    }

    public void setIsImmediate(Boolean isImmediate) {
        this.isImmediate = isImmediate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

	/**
	 * @return the priority
	 */
	public Integer getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

}
