/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-19
 */
package net.caiban.auth.dashboard.service.staff.impl;


import javax.annotation.Resource;

import net.caiban.auth.dashboard.dao.staff.FeedbackDao;
import net.caiban.auth.dashboard.domain.staff.Feedback;
import net.caiban.auth.dashboard.dto.PageDto;
import net.caiban.auth.dashboard.service.staff.FeedbackService;

import org.springframework.stereotype.Component;

import com.zz91.util.Assert;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-19
 */
@Component("feedbackService")
public class FeedbackServiceImpl implements FeedbackService {

	@Resource
	private FeedbackDao feedbackDao;
	
	@Override
	public Integer dealImpossible(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return feedbackDao.updateStatus(id, FeedbackDao.STATUS_IMPOSSIBLE);
	}

	@Override
	public Integer dealNothing(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return feedbackDao.updateStatus(id, FeedbackDao.STATUS_NOTHING);
	}

	@Override
	public Integer dealSuccess(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return feedbackDao.updateStatus(id, FeedbackDao.STATUS_SUCCESS);
	}

	@Override
	public Integer deleteFeedback(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return feedbackDao.deleteFeedback(id);
	}

	@Override
	public Integer feedback(Feedback feedback) {
		if(feedback.getBsId()==null){
			feedback.setBsId(0);
		}
		if(feedback.getStatus()==null){
			feedback.setStatus(FeedbackDao.STATUS_UN);
		}
		return feedbackDao.insertFeedback(feedback);
	}

	@Override
	public PageDto<Feedback> pageFeedback(Integer status, PageDto<Feedback> page) {
		page.setRecords(feedbackDao.queryFeedback(status, page));
		page.setTotalRecords(feedbackDao.queryFeedbackCount(status));
		return page;
	}
}
