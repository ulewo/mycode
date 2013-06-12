package com.ulewo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ulewo.dao.UserFriendDao;
import com.ulewo.entity.UserFriend;
import com.ulewo.service.UserFriendService;
import com.ulewo.util.Pagination;
import com.ulewo.util.PaginationResult;

public class UserFriendServiceImpl implements UserFriendService {
	@Autowired
	private UserFriendDao userFriendDao;

	@Override
	public void addFriend(UserFriend userFriend) {
		userFriendDao.addFriend(userFriend);
	}

	@Override
	public void deleteFirend(String userId, String friendId) {
		userFriendDao.deleteFriend(userId, friendId);
	}

	@Override
	public PaginationResult queryFans(String userId, int page, int pageSize) {
		int count = userFriendDao.queryFansCount(userId);
		Pagination pagination = new Pagination(page, count, pageSize);
		pagination.action();
		List<UserFriend> list = userFriendDao.queryFans(userId,
				pagination.getOffSet(), pageSize);
		PaginationResult result = new PaginationResult(pagination.getPage(),
				pagination.getPageTotal(), count, list);
		return result;
	}

	@Override
	public PaginationResult queryFocus(String userId, int page, int pageSize) {
		int count = userFriendDao.queryFocusCount(userId);
		Pagination pagination = new Pagination(page, count, pageSize);
		pagination.action();
		List<UserFriend> list = userFriendDao.queryFocus(userId,
				pagination.getOffSet(), pageSize);
		PaginationResult result = new PaginationResult(pagination.getPage(),
				pagination.getPageTotal(), count, list);
		return result;
	}

}
