package com.ulewo.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ulewo.dao.UserDao;
import com.ulewo.entity.User;
import com.ulewo.enums.QueryUserType;
import com.ulewo.service.UserService;
import com.ulewo.util.PaginationResult;
import com.ulewo.util.StringUtils;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Override
	public User findUser(String value, QueryUserType type) {

		if (null == value) {
			return null;
		}
		return userDao.findUser(value, type);
	}

	@Override
	public synchronized void addUser(User user) {

		String userId = userDao.findMaxUserId();
		user.setUserId(userId);
		String date = StringUtils.dateFormater.get().format(new Date());
		user.setRegisterTime(date);
		user.setPrevisitTime(date);
		userDao.addUser(user);
	}

	@Override
	public void updateUser(User user) {
		userDao.update(user);
	}

	@Override
	public void deleteUser(String userId) {

		// TODO Auto-generated method stub

	}

	@Override
	public PaginationResult findUser(String value, QueryUserType type, int offset, int total) {

		//int total = userDao.findUser(value, type)
		return null;
	}

	public User login(String value, String password) {

		User user = null;
		if (value.contains("@")) {
			user = userDao.findUser(value, QueryUserType.EMAIL);
		}
		else {
			user = userDao.findUser(value, QueryUserType.USERNAME);
		}
		if (user != null && user.getPassword().equals(password)) {
			return user;
		}
		else {
			return null;
		}
	}
	
	public User queryBaseInfo(String userId){
		return userDao.findBaseInfo(userId);
	}
}
