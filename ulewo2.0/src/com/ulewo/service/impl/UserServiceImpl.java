package com.ulewo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ulewo.dao.UserDao;
import com.ulewo.entity.PaginationResult;
import com.ulewo.entity.User;
import com.ulewo.enums.QueryUserType;
import com.ulewo.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	@Override
	public User findUser(String value, QueryUserType type) {
		if(null==value){
			return null;
		}
		return userDao.findUser(value, type);
	}

	@Override
	public void addUser(User user) {
		userDao.addUser(user);
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteUser(String userId) {
		// TODO Auto-generated method stub

	}

	@Override
	public PaginationResult findUser(String value, QueryUserType type,
			int offset, int total) {
		// TODO Auto-generated method stub
		return null;
	}

}
