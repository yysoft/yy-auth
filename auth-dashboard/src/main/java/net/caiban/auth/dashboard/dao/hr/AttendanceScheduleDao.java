package net.caiban.auth.dashboard.dao.hr;

import java.util.List;

import net.caiban.auth.dashboard.domain.hr.AttendanceSchedule;

public interface AttendanceScheduleDao {

	public List<AttendanceSchedule> queryDefault(Integer isuse);
	
	public Integer insert(AttendanceSchedule schedule);
	
	public Integer update(AttendanceSchedule schedule);
	
	public Integer updateStatus(Integer id, Integer isuse);
	
	public String queryName(Integer id);

}
