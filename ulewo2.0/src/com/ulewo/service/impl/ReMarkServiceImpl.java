package com.ulewo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ulewo.dao.ReMarkDao;
import com.ulewo.entity.ReMark;
import com.ulewo.service.ReMarkService;
@Service("reMarkService")
public class ReMarkServiceImpl implements ReMarkService {
	@Autowired
	private ReMarkDao reMarkDao;
	@Override
	public void addReMark(ReMark reMark) {
		reMarkDao.addReMark(reMark);
	}

	@Override
	public boolean isMark(String userId, String time) {
		return reMarkDao.isMark(userId, time);
	}

	@Override
	public List<ReMark> queryReMarkByTime(String time) {
		return reMarkDao.queryReMarkByTime(time);
	}

}
