/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-19
 */
package net.caiban.auth.dashboard.service.staff;


import net.caiban.auth.dashboard.domain.staff.Feedback;
import net.caiban.auth.dashboard.dto.PageDto;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-19
 */
public interface FeedbackService {

	public Integer feedback(Feedback feedback);
	
	public PageDto<Feedback> pageFeedback(Integer status, PageDto<Feedback> page);
	
	public Integer dealSuccess(Integer id);
	
	public Integer dealNothing(Integer id);
	
	public Integer dealImpossible(Integer id);
	
	public Integer deleteFeedback(Integer id);
	
}
