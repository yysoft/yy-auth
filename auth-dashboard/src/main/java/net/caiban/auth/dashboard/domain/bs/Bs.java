/**
 * 
 */
package net.caiban.auth.dashboard.domain.bs;

import java.io.Serializable;
import java.util.Date;

/**
 * @author root
 * 
 */
public class Bs implements Serializable {

	/**
	 * 公司业务系统
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String code;// 代号
	private String rightCode;
	private String name;
	private String url;
	private String avatar;
	private String note;
	private String types;
	private String versions;
	private String keys;
	private String password;
	private Date gmtCreated;
	private Date gmtModified;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getVersions() {
		return versions;
	}

	public void setVersions(String versions) {
		this.versions = versions;
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getRightCode() {
		return rightCode;
	}

	public void setRightCode(String rightCode) {
		this.rightCode = rightCode;
	}

	public Bs() {

	}

	public Bs(Integer id, String code, String rightCode, String name,
			String url, String avatar, String note, String types,
			String versions, String keys, String password, Date gmtCreated,
			Date gmtModified) {
		super();
		this.id = id;
		this.code = code;
		this.rightCode = rightCode;
		this.name = name;
		this.url = url;
		this.avatar = avatar;
		this.note = note;
		this.types = types;
		this.versions = versions;
		this.keys = keys;
		this.password = password;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}

}
