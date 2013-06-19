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
import com.ulewo.util.Pagination;
import com.ulewo.util.PaginationResult;
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

	public PaginationResult queryReTalkByPag(int page, int pageSize, int talkId) {

		int count = queryReTalkCount(talkId);
		Pagination pagination = new Pagination(page, count, pageSize);
		pagination.action();
		List<ReTalk> list = queryReTalk(pagination.getOffSet(), pageSize, talkId);
		PaginationResult result = new PaginationResult(pagination.getPage(), pagination.getPageTotal(), count, list);
		return result;
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
