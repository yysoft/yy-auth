package net.caiban.auth.dashboard.service.hr;

import java.util.Date;
import java.util.List;

import net.caiban.auth.dashboard.domain.hr.AttendanceScheduleDetailSearch;
import net.caiban.auth.dashboard.dto.hr.AttendanceScheduleDetailDto;

public interface AttendanceScheduleDetailService {

	public void buildSchedule(Integer scheduleId, Date month, Integer[] day, 
			String[] workf, String[] workt, String createdBy);
	
	public List<AttendanceScheduleDetailDto> buildInitSchedule(AttendanceScheduleDetailSearch search);
	
}
