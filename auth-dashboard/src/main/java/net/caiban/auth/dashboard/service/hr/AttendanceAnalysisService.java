package net.caiban.auth.dashboard.service.hr;


import java.util.Date;
import java.util.List;

import net.caiban.auth.dashboard.domain.hr.AttendanceAnalysis;
import net.caiban.auth.dashboard.dto.PageDto;

public interface AttendanceAnalysisService {
	
	public PageDto<AttendanceAnalysis> pageAnalysis(String name, String code, Date month, PageDto<AttendanceAnalysis> page );
	
	public Integer updateAnalysis(AttendanceAnalysis analysis);
	
	public List<AttendanceAnalysis> queryAnalysisByMonth(Date month);
	
}
 
