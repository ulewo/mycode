package com.ulewo.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ulewo.dao.UserFriendDao;
import com.ulewo.entity.UserFriend;
import com.ulewo.service.UserFriendService;
import com.ulewo.util.Pagination;
import com.ulewo.util.PaginationResult;
@Service("userFriendService")
public class UserFriendServiceImpl implements UserFriendService {
	@Autowired
	private UserFriendDao userFriendDao;
	private final SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	public void addFriend(UserFriend userFriend) {
		userFriend.setCreateTime(formate.format(new Date()));
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
	
	
	public UserFriend queryFocusUser(String userId,String friendId){
		return userFriendDao.queryFocusUser(userId, friendId);
	}

	@Override
	public List<UserFriend> queryFocus2List(String userId, int offset,
			int pageSize) {
		List<UserFriend> list = userFriendDao.queryFocus(userId,
				offset, pageSize);
		return list;
	}

	@Override
	public List<UserFriend> queryFans2List(String userId, int offset,
			int pageSize) {
		List<UserFriend> list = userFriendDao.queryFans(userId,
				offset, pageSize);
		return list;
	}
}
