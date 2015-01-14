package net.caiban.auth.dashboard.dao.staff;

import java.util.List;

import net.caiban.auth.dashboard.domain.staff.SchedulerReport;

public interface SchedulerReportDao {
 
	/**
	 * 在日/周报中关联相关日程计划
	 * reportId:日/周报ID
	 * eventId:日程ID
	 * 两个ID均不能为空
	 */
	public Integer insertReportEvent(Integer reportId, Integer eventId);
	/**
	 * 创建日/周报
	 * deptCode:创建汇报时汇报人所在部门
	 * account:汇报人账户
	 * deptCode,account,text,year,week不能为空
	 */
	public Integer insertReport(SchedulerReport report);
	/**
	 * 查询日/周报
	 * year：年份，不能为空
	 * week：周数，不能为空
	 * account：日报创建者
	 * deptCode：创建日报时创建者所在部门
	 */
	public List<SchedulerReport> queryReport(String year, Integer week, String account, String deptCode);
	/**
	 * 根据日/周报ID查找一条日/周报信息
	 */
	public SchedulerReport queryOneReport(Integer id);
}
 
