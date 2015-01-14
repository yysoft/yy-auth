package net.caiban.auth.dashboard.service.staff;

import java.util.Date;
import java.util.List;

import net.caiban.auth.dashboard.domain.staff.SchedulerEvent;

public interface schedulerEventService {
 
	/**
	 * 安排日程计划
	 * 其中：asign_account,owner_account,text,start_date,end_date必需有值
	 */
	public Integer assignEvent(SchedulerEvent event);
	/**
	 * 在日历上更新日程
	 * 只允许更新:owner_account,title,details,start_date,end_date
	 * 其中id,title,start_date,end_date必需有值
	 * 更新条件：id
	 */
	public Integer reassignEvent(SchedulerEvent event);
	/**
	 * 根据ID删除日程，ID不能为空
	 */
	public Integer deleteEvent(Integer id);
	/**
	 * 在日历上加载日程
	 * owner:仅加载自己的日程
	 * deptCode:仅加载指定部门日程
	 * 两个条件如果都没有则加载全公司的日程
	 */
	public List<SchedulerEvent> queryEvent(Date from, Date to, String owner, String deptCode);
	/**
	 * 更新日程的进度和重要程度
	 * 当persent为null时默认设置成0
	 * 当importance为null时默认设置为0
	 *
	 *  
	 */
	public Integer updateEvent(Integer id, Integer persent, Integer importance);
	/**
	 * 查找日报关联的日程
	 *
	 *  
	 */
	public List<SchedulerEvent> queryAssociatedEvent(Integer reportId);
	
	public List<SchedulerEvent> query();

}
 
