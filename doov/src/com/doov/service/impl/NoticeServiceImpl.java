package com.doov.service.impl;

import java.util.List;

import com.doov.dao.NoticeDao;
import com.doov.entity.Notice;
import com.doov.service.NoticeService;

public class NoticeServiceImpl implements NoticeService {

	private NoticeDao noticeDao;

	@Override
	public List<Notice> selectAll(String title, int noStart, int offSet) {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCount(String title) {

		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Notice queryNotice(int id) {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int add(Notice notice) {

		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(Notice notice) {

		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int id) {

		// TODO Auto-generated method stub

	}

}
