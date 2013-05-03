package com.lhl.quan.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.lhl.entity.Talk;
import com.lhl.quan.dao.TalkDao;
import com.lhl.quan.service.TalkService;
import com.lhl.util.Tools;

public class TalkServiceImpl implements TalkService {

	private TalkDao talkDao;

	public void setTalkDao(TalkDao talkDao) {
		this.talkDao = talkDao;
	}

	private SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	@Override
	public void addTalk(Talk talk) {
		talk.setContent(Tools.clearHtml(talk.getContent()));
		talk.setCreateTime(format.format(new Date()));
		talkDao.addTalk(talk);
	}

	@Override
	public List<Talk> queryLatestTalk(int offset, int total) {
		List<Talk> list = talkDao.queryLatestTalk(offset, total);
		for (Talk talk : list) {
			talk.setCreateTime(Tools.friendly_time(talk.getCreateTime()));
		}
		return list;
	}

	@Override
	public int queryTalkCount() {
		return talkDao.queryTalkCount();
	}

	@Override
	public List<Talk> queryLatestTalkByUserId(int offset, int total,
			String userId) {
		List<Talk> list = talkDao
				.queryLatestTalkByUserId(offset, total, userId);
		for (Talk talk : list) {
			talk.setCreateTime(Tools.friendly_time(talk.getCreateTime()));
		}
		return list;
	}

	@Override
	public int queryTalkCountByUserId(String userId) {
		return talkDao.queryTalkCountByUserId(userId);
	}

}
