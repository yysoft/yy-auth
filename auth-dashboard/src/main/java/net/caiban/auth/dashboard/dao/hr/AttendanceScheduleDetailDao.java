package net.caiban.auth.dashboard.dao.hr;

import java.util.Date;
import java.util.List;

import net.caiban.auth.dashboard.domain.hr.AttendanceScheduleDetail;
import net.caiban.auth.dashboard.domain.hr.AttendanceScheduleDetailSearch;

public interface AttendanceScheduleDetailDao {

	public Integer count(Integer scheduleId, Date gmtMonth);
	
	public Integer insert(AttendanceScheduleDetail detail);
	
	public Integer deleteSchedule(Integer scheduleId, Date gmtMonth);
	
	public List<AttendanceScheduleDetail> queryDefault(AttendanceScheduleDetailSearch search);
}
