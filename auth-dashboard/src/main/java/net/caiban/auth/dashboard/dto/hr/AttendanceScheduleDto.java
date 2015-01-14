/**
 * 
 */
package net.caiban.auth.dashboard.dto.hr;

import java.io.Serializable;
import java.util.Map;

import net.caiban.auth.dashboard.domain.hr.AttendanceSchedule;

/**
 * @author mays
 *
 */
public class AttendanceScheduleDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private AttendanceSchedule schedule;
	private Map<Integer, Integer> monthCount;
	
	public AttendanceSchedule getSchedule() {
		return schedule;
	}
	public void setSchedule(AttendanceSchedule schedule) {
		this.schedule = schedule;
	}
	public Map<Integer, Integer> getMonthCount() {
		return monthCount;
	}
	public void setMonthCount(Map<Integer, Integer> monthCount) {
		this.monthCount = monthCount;
	}
	
}
