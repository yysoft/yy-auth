package net.caiban.auth.dashboard.domain.hr;

import java.io.Serializable;

/***
 * 
 * @author root
 *
 */
public class AttendanceScheduleSearch implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer isuse;
	
	public Integer getIsuse() {
		return isuse;
	}
	public void setIsuse(Integer isuse) {
		this.isuse = isuse;
	}
	
}
