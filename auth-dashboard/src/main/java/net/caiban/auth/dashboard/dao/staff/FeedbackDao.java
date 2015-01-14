/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-19
 */
package net.caiban.auth.dashboard.dao.staff;

import java.util.List;

import net.caiban.auth.dashboard.domain.staff.Feedback;
import net.caiban.auth.dashboard.dto.PageDto;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-19
 */
public interface FeedbackDao {
	
	public final static int STATUS_UN = 0;
	public final static int STATUS_SUCCESS = 1;
	public final static int STATUS_NOTHING = 2;
	public final static int STATUS_IMPOSSIBLE = 3;
	
	public List<Feedback> queryFeedback(Integer status, PageDto<Feedback> page);
	
	public Integer queryFeedbackCount(Integer status);
	
	public Integer updateStatus(Integer id, Integer status);
	
	public Integer insertFeedback(Feedback feedback);
	
	public Integer deleteFeedback(Integer id);
}
