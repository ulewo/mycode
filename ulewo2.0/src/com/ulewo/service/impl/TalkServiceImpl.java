package com.ulewo.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ulewo.dao.TalkDao;
import com.ulewo.dao.UserDao;
import com.ulewo.entity.NoticeParam;
import com.ulewo.entity.Talk;
import com.ulewo.enums.NoticeType;
import com.ulewo.service.TalkService;
import com.ulewo.util.Constant;
import com.ulewo.util.FormatAt;
import com.ulewo.util.StringUtils;

@Service("talkService")
public class TalkServiceImpl implements TalkService {

	private TalkDao talkDao;

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
}
