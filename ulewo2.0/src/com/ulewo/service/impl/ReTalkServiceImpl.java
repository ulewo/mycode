package com.ulewo.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ulewo.dao.ReTalkDao;
import com.ulewo.dao.UserDao;
import com.ulewo.entity.NoticeParam;
import com.ulewo.entity.ReTalk;
import com.ulewo.enums.NoticeType;
import com.ulewo.service.ReTalkService;
import com.ulewo.util.Constant;
import com.ulewo.util.FormatAt;
import com.ulewo.util.StringUtils;

@Service("reTalkService")
public class ReTalkServiceImpl implements ReTalkService {
	@Autowired
	private ReTalkDao reTalkDao;

	@Autowired
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {

		this.userDao = userDao;
	}

	public void setReTalkDao(ReTalkDao reTalkDao) {

		this.reTalkDao = reTalkDao;
	}

	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public void addReTalk(ReTalk retalk) {

		String cotent = StringUtils.clearHtml(retalk.getContent());
		List<String> referers = new ArrayList<String>();
		String formatContent = FormatAt.getInstance(Constant.TYPE_TALK).GenerateRefererLinks(userDao, cotent, referers);
		retalk.setContent(formatContent);
		retalk.setCreateTime(format.format(new Date()));
		reTalkDao.addReTalk(retalk);

		NoticeParam noticeParm = new NoticeParam();
		noticeParm.setArticleId(retalk.getTalkId());
		noticeParm.setNoticeType(NoticeType.RETALK);
		noticeParm.setAtUserIds(referers);
		noticeParm.setSendUserId(retalk.getUserId());
		noticeParm.setReceiveUserId(retalk.getAtUserId());
		NoticeThread noticeThread = new NoticeThread(noticeParm);
		Thread thread = new Thread(noticeThread);
		thread.start();
	}

	@Override
	public List<ReTalk> queryReTalk(int offset, int total, int talkId) {

		List<ReTalk> list = reTalkDao.queryReTalk(offset, total, talkId);
		for (ReTalk retalk : list) {
			retalk.setCreateTime(StringUtils.friendly_time(retalk.getCreateTime()));
		}
		return list;
	}

	@Override
	public int queryReTalkCount(int talkId) {

		return reTalkDao.queryReTalkCount(talkId);
	}

	@Override
	public void deleteReTalk(int reTalkId) {

		reTalkDao.deleteReTalk(reTalkId);
	}

}
