package com.lhl.quan.service;

import java.util.List;

import com.lhl.entity.FriendGroup;

/** 
 * @Title:
 * @Description: 友情圈业务层
 * @author luohl
 * @date 2012-3-29
 * @version V1.0
*/
public interface FriendGroupService
{

	/**
	 * description: 添加友情圈
	 * @param friendGroup
	 * @throws Exception
	 * @author luohl
	 */
	public void addFriendGroup(FriendGroup friendGroup) throws Exception;

	/**
	 * 
	 * description: 删除友情圈
	 * @param id
	 * @param gid
	 * @throws Exception
	 * @author luohl
	 */
	public void deleteFriendGroup(int id, String gid) throws Exception;

	/**
	 * 
	 * description: 单笔查询友情圈
	 * @param id
	 * @return
	 * @throws Exception
	 * @author luohl
	 */
	public FriendGroup getFriendGroup(int id) throws Exception;;

	/**
	 * 
	 * description: 多笔查询友情圈
	 * @param gid
	 * @return
	 * @throws Exception
	 * @author luohl
	 */
	public List<FriendGroup> queryFriendGroups(String gid) throws Exception;

	/**
	 * 
	 * description: 查询友情圈数量
	 * @param gid
	 * @return
	 * @throws Exception
	 * @author luohl
	 */
	public int queryFriendGroupCount(String gid) throws Exception;
}
