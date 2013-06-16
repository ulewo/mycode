package com.ulewo.service;

import java.util.List;

import com.ulewo.entity.UserFriend;
import com.ulewo.util.PaginationResult;

public interface UserFriendService {

	/**
	 * 新增好友
	 * 
	 * @param userFriend
	 */
	public void addFriend(UserFriend userFriend);

	/**
	 * 删除好友
	 * 
	 * @param userId
	 * @param friendId
	 */
	public void deleteFirend(String userId, String friendId);

	/**
	 * 查询粉丝
	 * 
	 * @param userId
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public PaginationResult queryFans(String userId, int page, int pageSize);

	/**
	 * 查询关注的人
	 * 
	 * @param userId
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public PaginationResult queryFocus(String userId, int page, int pageSize);
	
	public UserFriend queryFocusUser(String userId,String friendId);
	
	public List<UserFriend> queryFocus2List(String userId,int offset,int pageSize);
	
	public List<UserFriend> queryFans2List(String userId,int offset,int pageSize);
}
