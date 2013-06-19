package com.ulewo.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ulewo.dao.TalkDao;
import com.ulewo.dao.UserDao;
import com.ulewo.entity.NoticeParam;
import com.ulewo.entity.Talk;
import com.ulewo.enums.NoticeType;
import com.ulewo.service.TalkService;
import com.ulewo.util.Constant;
import com.ulewo.util.FormatAt;
import com.ulewo.util.Pagination;
import com.ulewo.util.PaginationResult;
import com.ulewo.util.StringUtils;

@Service("talkService")
public class TalkServiceImpl implements TalkService {
	@Autowired
	private TalkDao talkDao;

	@Autowired
	private UserDao userDao;

	public void setTalkDao(TalkDao talkDao) {

		this.talkDao = talkDao;
	}

	public void setUserDao(UserDao userDao) {

		this.userDao = userDao;
	}

	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public void addTalk(Talk talk) {

		String cotent = StringUtils.clearHtml(talk.getContent());
		List<String> referers = new ArrayList<String>();
		String formatContent = FormatAt.getInstance(Constant.TYPE_TALK).GenerateRefererLinks(userDao, cotent, referers);
		talk.setContent(formatContent);
		talk.setCreateTime(format.format(new Date()));
		talkDao.addTalk(talk);

		NoticeParam noticeParm = new NoticeParam();
		noticeParm.setArticleId(talk.getId());
		noticeParm.setNoticeType(NoticeType.ATINTALK);
		noticeParm.setAtUserIds(referers);
		noticeParm.setSendUserId(talk.getUserId());
		NoticeThread noticeThread = new NoticeThread(noticeParm);
		Thread thread = new Thread(noticeThread);
		thread.start();
	}

	@Override
	public List<Talk> queryLatestTalk(int offset, int total) {

		List<Talk> list = talkDao.queryLatestTalk(offset, total);
		for (Talk talk : list) {
			talk.setCreateTime(StringUtils.friendly_time(talk.getCreateTime()));
		}
		return list;
	}

	@Override
	public int queryTalkCount() {

		return talkDao.queryTalkCount();
	}

	@Override
	public List<Talk> queryLatestTalkByUserId(int offset, int total, String userId) {

		List<Talk> list = talkDao.queryLatestTalkByUserId(offset, total, userId);
		for (Talk talk : list) {
			talk.setCreateTime(StringUtils.friendly_time(talk.getCreateTime()));
		}
		return list;
	}

	@Override
	public int queryTalkCountByUserId(String userId) {

		return talkDao.queryTalkCountByUserId(userId);
	}

	public Talk queryDetail(int talkId) {

		Talk talk = talkDao.queryDetail(talkId);
		talk.setCreateTime(StringUtils.friendly_time(talk.getCreateTime()));
		return talk;
	}

	@Override
	public PaginationResult queryLatestTalkByPag(int page, int pageSize) {

		int count = queryTalkCount();
		Pagination pagination = new Pagination(page, count, pageSize);
		pagination.action();
		List<Talk> list = queryLatestTalk(pagination.getOffSet(), pageSize);
		PaginationResult result = new PaginationResult(pagination.getPage(), pagination.getPageTotal(), count, list);
		return result;
	}

	@Override
	public PaginationResult queryLatestTalkByUserIdByPag(int page, int pageSize, String userId) {

		int count = queryTalkCountByUserId(userId);
		Pagination pagination = new Pagination(page, count, pageSize);
		pagination.action();
		List<Talk> list = queryLatestTalkByUserId(pagination.getOffSet(), pageSize, userId);
		PaginationResult result = new PaginationResult(pagination.getPage(), pagination.getPageTotal(), count, list);
		return result;
	}

}
