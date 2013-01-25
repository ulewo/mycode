package com.lhl.quan.service.impl;

import java.util.List;

import com.lhl.entity.Notice;
import com.lhl.quan.dao.NoticeDao;
import com.lhl.quan.service.NoticeService;

public class NoticeServiceImpl implements NoticeService
{

	private NoticeDao noticeDao;

	@Override
	public void updateNotice(Notice notice)
	{

		noticeDao.updateNotice(notice);

	}

	@Override
	public List<Notice> queryNoticeByUserId(String userId, String status)
	{

		return noticeDao.queryNoticeByUserId(userId, status);
	}

	@Override
	public int queryNoticeCountByUserId(String userId, String status)
	{

		return noticeDao.queryNoticeCountByUserId(userId, status);
	}

	public void setNoticeDao(NoticeDao noticeDao)
	{

		this.noticeDao = noticeDao;
	}

}
