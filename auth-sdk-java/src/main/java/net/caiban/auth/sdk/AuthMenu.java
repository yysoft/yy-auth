/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-6
 */
package net.caiban.auth.sdk;

import java.io.Serializable;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-6
 */
public class AuthMenu  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String text;
	private boolean leaf;
	private String url;
	private String data;
	private boolean disabled;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @return the leaf
	 */
	public boolean isLeaf() {
		return leaf;
	}
	/**
	 * @param leaf the leaf to set
	 */
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}
	/**
	 * @return the disabled
	 */
	public boolean isDisabled() {
		return disabled;
	}
	/**
	 * @param disabled the disabled to set
	 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
//	private String code;
//	private String menu;
//	private String menuUrl;
//	private String menuCss;
//	private boolean leaf;
	
}
