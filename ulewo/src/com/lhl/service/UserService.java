package com.lhl.service;

import java.util.List;

import com.lhl.dao.UserDao;
import com.lhl.entity.User;
import com.lhl.util.StringUtil;

public class UserService {
	private UserService() {

	}

	private static final String ZEROID = "1";
	private static final String FIRSTUID = "10000";
	private static UserService instance;

	/**
	 * 构造实例
	 * 
	 * @return
	 */
	public synchronized static UserService getInstance() {

		if (instance == null) {
			instance = new UserService();
		}
		return instance;
	}

	public List<User> queryList() throws Exception {

		return UserDao.getInstance().queryUserList();
	}

	public User queryUserByUserName(String userName) throws Exception {

		return UserDao.getInstance().queryUserByName(userName);
	}

	public User queryUserByUid(String uid) throws Exception {

		return UserDao.getInstance().queryUserByUid(uid);
	}

	public void updateUserAvatar(String uid, String avatar) {

		UserDao.getInstance().updateAvatar(uid, avatar);
	}

	public synchronized String register(User user) {

		UserDao userDao = UserDao.getInstance();
		String uid = userDao.getMaxUid();
		if (StringUtil.isNumber(uid)) {

			uid = Integer.parseInt(uid) + 1 + "";
			if (ZEROID.equals(uid)) {
				uid = FIRSTUID;
			}
			user.setUid(uid);
			userDao.addUser(user);
			return uid;
		} else {
			return null;
		}

	}
}
