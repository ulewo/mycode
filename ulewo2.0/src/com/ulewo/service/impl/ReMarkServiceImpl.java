package com.ulewo.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ulewo.dao.ReMarkDao;
import com.ulewo.dao.UserDao;
import com.ulewo.entity.ReMark;
import com.ulewo.entity.User;
import com.ulewo.enums.QueryUserType;
import com.ulewo.service.ReMarkService;
import com.ulewo.util.Constant;
import com.ulewo.util.StringUtils;

@Service("reMarkService")
public class ReMarkServiceImpl implements ReMarkService {
	private static final SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd");
	@Autowired
	private ReMarkDao reMarkDao;

	@Autowired
	private UserDao userDao;

	@Override
	public void addReMark(ReMark reMark) {
		reMarkDao.addReMark(reMark);
		User curUser = userDao.findUser(reMark.getUserId(),
				QueryUserType.USERID);
		curUser.setMark(curUser.getMark() + Constant.ARTICLE_MARK2);
		userDao.update(curUser);
	}

	@Override
	public boolean isMark(String userId, String time) {
		return reMarkDao.isMark(userId, time);
	}

	@Override
	public List<ReMark> queryReMarkByTime(String time) {
		List<ReMark> remarks = reMarkDao.queryReMarkByTime(time);
		for (ReMark mark : remarks) {
			mark.setMarkTime(StringUtils.friendly_time(mark.getMarkTime()));
		}
		return remarks;
	}

	@Override
	public int userMarkCount(String userId) {
		return reMarkDao.userMarkCount(userId);
	}

	@Override
	public List<ReMark> queryUserReMark(String userId) {
		List<ReMark> remarks = reMarkDao.userMarkInfo(userId);
		for (ReMark mark : remarks) {
			mark.setMarkTime(StringUtils.friendly_time(mark.getMarkTime()));
		}
		return remarks;
	}

	@Override
	public int allMarkCount(String markTime) {
		return reMarkDao.allMarkCount(markTime);
	}

	@Override
	public boolean isContinuationMark(String userId) {
		String endDate = format.format(new Date());
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -7);
		Date date = c.getTime();
		String startDate = format.format(date);
		int count = reMarkDao.getCountBetweenTime(userId, startDate, endDate);
		if (count >= Constant.DAYNUM) {
			rewardMark(userId);
			return true;
		}
		return false;
	}

	private void rewardMark(String userId) {
		User user = userDao.findUser(userId, QueryUserType.USERID);
		user.setMark(user.getMark() + Constant.ARTICLE_MARK8);
		userDao.update(user);
	}
}
