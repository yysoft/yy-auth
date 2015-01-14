package net.caiban.auth.dashboard.service.hr;

import java.util.List;

import net.caiban.auth.dashboard.domain.hr.AttendanceSchedule;
import net.caiban.auth.dashboard.dto.hr.AttendanceScheduleDto;

public interface AttendanceScheduleService {

	/**
	 * 查找全部班次，按isuse查找
	 * isuse null 则查找全部
	 * */
	public List<AttendanceScheduleDto> querySchedule(Integer isuse, Integer year);
	
	public Integer create(AttendanceSchedule schedule);
	
	public Integer update(AttendanceSchedule schedule);
	
//	public Integer useSchedule(Integer id);
//	
//	public Integer unUseSchedule(Integer id);
	public Integer updateStatus(Integer id, Integer isuse);
	
	public String queryName(Integer id);
	
	public List<AttendanceSchedule> queryScheduleOnly(Integer isuse);
	
}
