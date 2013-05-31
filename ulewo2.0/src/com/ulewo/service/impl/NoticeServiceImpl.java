package com.ulewo.service.impl;

import java.util.List;

import com.ulewo.dao.NoticeDao;
import com.ulewo.entity.Notice;
import com.ulewo.service.NoticeService;

public class NoticeServiceImpl implements NoticeService {

	private NoticeDao noticeDao;

	@Override
	public void updateNotice(Notice notice) {

		noticeDao.updateNotice(notice);

	}

	@Override
	public List<Notice> queryNoticeByUserId(String userId, String status) {

		return noticeDao.queryNoticeByUserId(userId, status);
	}

	@Override
	public int queryNoticeCountByUserId(String userId, String status) {

		return noticeDao.queryNoticeCountByUserId(userId, status);
	}

	public void deleteNotice(int id) {

		noticeDao.deleteNotice(id);
	}

	public Notice getNotice(int id) {

		return noticeDao.getNotice(id);
	}

	public void setNoticeDao(NoticeDao noticeDao) {

		this.noticeDao = noticeDao;
	}

}
