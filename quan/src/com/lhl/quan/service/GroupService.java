package com.lhl.quan.service;

import java.util.List;

import com.lhl.entity.Group;

public interface GroupService {
	/**
	 * 创建群组
	 * @param group
	 * @return
	 * @throws Exception
	 */

	public String createGroup(Group group) throws Exception;

	/**
	 * 单笔查询群组
	 * @param gid
	 * @return
	 * @throws Exception
	 */
	public Group queryGorup(String gid) throws Exception;

	/**
	 * 
	 * description: 查询基本信息
	 * @return
	 * @author lhl
	 */
	public Group queryGroupExtInfo(String gid) throws Exception;

	/**
	 * 更新群组
	 * @param group
	 * @throws Exception
	 */
	public void updateGroup(Group group) throws Exception;

	/**
	 * 
	 * description:    查询所有可用的群组 按照群组文章数排序
	 * @param isvalid
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @author lhl
	 */
	List<Group> queryGroupsOderArticleCount(int pageNumber, int pageSize) throws Exception;

	/**
	 * 
	 * description:    查询所有可用群数量
	 * @param isvalid
	 * @return
	 * @author lhl
	 */
	int queryGroupsCount() throws Exception;

	/**
	 * 
	 * description: 查询用户创建的群组数
	 * @return
	 * @throws Exception
	 * @author lhl
	 */
	int queryCreatedGroupCount(String userId) throws Exception;

	/**
	 * 
	 * description: 查询用户创建的群组
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author lhl
	 */
	List<Group> queryCreatedGroups(String userId) throws Exception;

	/**
	 * 
	 * description: 查询参加的群组数量
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author lhl
	 */
	int queryJoinedGroupCount(String userId) throws Exception;

	/**
	 * 
	 * description: 查询参加的群组
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author lhl
	 */
	List<Group> queryJoinedGroups(String userId) throws Exception;

	/**
	 * 
	 * description: 搜索群组
	 * @return
	 * @throws Exception
	 * @author lhl
	 */
	List<Group> searchGroups(String keyWord, int offset, int total) throws Exception;

	/**
	 * 
	 * description: 搜索群组数
	 * @param keyWord
	 * @return
	 * @throws Exception
	 * @author lhl
	 */
	int searchGroupCount(String keyWord) throws Exception;
}
