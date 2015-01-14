package net.caiban.auth.dashboard.dao.staff;

import java.util.Date;
import java.util.List;

import net.caiban.auth.dashboard.domain.staff.SchedulerEvent;

public interface SchedulerEventDao {
 
	/**
	 * 在数据库中插入一条日程信息
	 * 其中：asign_account,owner_account,title,start_date,end_date必需有值
	 */
	public Integer insertEvent(SchedulerEvent event);
	/**
	 * 在日历上更新日程
	 * 只允许更新:owner_account,text,details,start_date,end_date
	 * 其中id,text,start_date,end_date必需有值
	 * 更新条件：id
	 *
	 *  
	 */
	public Integer updateEvent(SchedulerEvent event);
	/**
	 *  
	 */
	public Integer updateEventProcess(Integer id, Integer persent, Integer importance);
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
	public List<SchedulerEvent> queryEvent(Date startDate, Date endDate, String ownerAccount, String deptCode);
	/**
	 * 查找日报关联的日程
	 *
	 *  
	 */
	public List<SchedulerEvent> queryAssociatedEvent(Integer reportId);
	
	public SchedulerEvent querySchedulerEvent(Integer id);
	
	public List<SchedulerEvent> query();

}
 
