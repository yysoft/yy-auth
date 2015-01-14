/**
 * 
 */
package net.caiban.auth.dashboard.domain.staff;

import java.io.Serializable;
import java.util.Date;

/**
 * @author root
 * 
 */
public class Contacts implements Serializable {

	/**
	 * 员工通迅信息
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String account;
	private String keys;
	private String values;
	private Date gmtCreated;
	private Date gmtModified;

	public Contacts(Integer id, String account, String keys, String values,
			Date gmtCreated, Date gmtModified) {
		this.id = id;
		this.account = account;
		this.keys = keys;
		this.values = values;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

}
