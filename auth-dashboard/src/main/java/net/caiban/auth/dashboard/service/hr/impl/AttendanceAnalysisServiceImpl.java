/**
 * 
 */
package net.caiban.auth.dashboard.service.hr.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.caiban.auth.dashboard.dao.hr.AttendanceAnalysisDao;
import net.caiban.auth.dashboard.domain.hr.AttendanceAnalysis;
import net.caiban.auth.dashboard.dto.PageDto;
import net.caiban.auth.dashboard.service.hr.AttendanceAnalysisService;

import org.springframework.stereotype.Component;

/**
 * @author mays
 *
 */
@Component("attendanceAnalysisService")
public class AttendanceAnalysisServiceImpl implements AttendanceAnalysisService {
	
	@Resource
	private AttendanceAnalysisDao attendanceAnalysisDao;

	@Override
	public PageDto<AttendanceAnalysis> pageAnalysis(String name, String code,
			Date month, PageDto<AttendanceAnalysis> page) {
		
		if(page.getSort()==null){
			page.setSort("id");
		}
		if(page.getDir()==null){
			page.setDir("desc");
		}
		
		page.setRecords(attendanceAnalysisDao.queryAnalysis(name, code, month, page));
		page.setTotalRecords(attendanceAnalysisDao.queryAnalysisCount(name, code, month));
		
		return page;
	}

	@Override
	public Integer updateAnalysis(AttendanceAnalysis analysis) {
		return attendanceAnalysisDao.updateAnalysis(analysis);
	}

	@Override
	public List<AttendanceAnalysis> queryAnalysisByMonth(Date month){
		return attendanceAnalysisDao.queryAnalysisByMonth(month);
	}

	

}
