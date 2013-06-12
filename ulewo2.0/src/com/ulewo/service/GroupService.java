package com.ulewo.service;

import java.util.List;

import com.ulewo.entity.Group;
import com.ulewo.util.PaginationResult;

public interface GroupService {
	/**
	 * 创建群组
	 * 
	 * @param group
	 * @return @
	 */

	public String createGroup(Group group);

	/**
	 * 单笔查询群组
	 * 
	 * @param gid
	 * @return @
	 */
	public Group queryGorup(String gid);

	/**
	 * 更新群组
	 * 
	 * @param group
	 *            @
	 */
	public void updateGroup(Group group);

	/***
	 * 查询所有圈子根据文章数量倒序排列
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public PaginationResult queryGroupsOderArticleCount(int page, int pageSize);

	/**
	 * 
	 * description: 查询所有可用群数量
	 * 
	 * @param isvalid
	 * @return
	 * @author lhl
	 */
	int queryGroupsCount();

	/**
	 * 
	 * description: 查询用户创建的群组数
	 * 
	 * @return @
	 * @author lhl
	 */
	int queryCreatedGroupCount(String userId);

	/**
	 * 
	 * description: 查询用户创建的群组
	 * 
	 * @param userId
	 * @return @
	 * @author lhl
	 */
	List<Group> queryCreatedGroups(String userId);

	/**
	 * 
	 * description: 查询参加的群组数量
	 * 
	 * @param userId
	 * @return @
	 * @author lhl
	 */
	int queryJoinedGroupCount(String userId);

	/**
	 * 
	 * description: 查询参加的群组
	 * 
	 * @param userId
	 * @return @
	 * @author lhl
	 */
	List<Group> queryJoinedGroups(String userId);

	/**
	 * 
	 * description: 搜索群组
	 * 
	 * @return @
	 * @author lhl
	 */
	List<Group> searchGroups(String keyWord, int offset, int total);

	/**
	 * 
	 * description: 搜索群组数
	 * 
	 * @param keyWord
	 * @return @
	 * @author lhl
	 */
	int searchGroupCount(String keyWord);
}
