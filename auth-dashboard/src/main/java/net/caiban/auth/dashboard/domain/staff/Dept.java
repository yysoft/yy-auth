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
public class Dept implements Serializable {

	/**
	 *  公司部门
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String code;
	private String note;
	private Date gmtCreated;
	private Date gmtModified;

	/**
	 * 
	 */
	public Dept() {
		super();
	}
	public Dept(Integer id, String name, String code, String note,
			Date gmtCreated, Date gmtModified) {

		this.id = id;
		this.name = name;
		this.code = code;
		this.note = note;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}
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
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
