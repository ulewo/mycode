package com.ulewo.service;

import java.util.List;
import java.util.Map;

import com.ulewo.exception.BusinessException;
import com.ulewo.model.Group;
import com.ulewo.model.SessionUser;
import com.ulewo.util.SimplePage;
import com.ulewo.util.UlewoPaginationResult;

public interface GroupService {
	/**
	 * 创建群组
	 * 
	 * @param map
	 * @param sessionUser
	 *            TODO
	 * @param member
	 *            TODO
	 * @return @
	 * @throws BusinessException
	 *             TODO
	 */

	public Group createGroup(Map<String, String> map, SessionUser sessionUser)
			throws BusinessException;

	/**
	 * 修改群组
	 * 
	 * @param map
	 * @param userId
	 * @throws BusinessException
	 *             TODO
	 */
	public void updateGroup(Map<String, String> map, Integer userId)
			throws BusinessException;

	/**
	 * 更新头像
	 * 
	 * @param map
	 * @param userId
	 * @throws BusinessException
	 *             TODO
	 */
	public void updateGroupIcon(Map<String, String> map, Integer userId)
			throws BusinessException;

	/**
	 * 更新公告
	 * 
	 * @param map
	 * @param userId
	 * @throws BusinessException
	 */
	public void updateGroupNotice(Map<String, String> map, Integer userId)
			throws BusinessException;

	/**
	 * 删除群组
	 * 
	 * @param map
	 * @throws BusinessException
	 *             TODO
	 */
	public void deleteGroup(Map<String, String> map) throws BusinessException;

	/**
	 * 查询所有群组
	 * 
	 * @param map
	 * @return
	 */
	public UlewoPaginationResult<Group> findAllGroup(Map<String, String> map);

	/**
	 * 查询创建的群组
	 * 
	 * @param map
	 * @return
	 * @throws BusinessException
	 *             TODO
	 */
	public UlewoPaginationResult<Group> findCreatedGroups(
			Map<String, String> map) throws BusinessException;

	public List<Group> findCreatedGroups(Integer userId, SimplePage page);

	/**
	 * 查询加入的群组
	 * 
	 * @param map
	 * @return
	 * @throws BusinessException
	 *             TODO
	 */
	public UlewoPaginationResult<Group> findJoinedGroups(Map<String, String> map)
			throws BusinessException;

	public List<Group> findJoinedGroups(Integer userId, SimplePage page);

	/**
	 * 搜索群组
	 * 
	 * @param map
	 * @return
	 */
	public UlewoPaginationResult<Group> searchGroups(Map<String, String> map);

	/**
	 * 查询群组基本信息
	 * 
	 * @param map
	 * @return
	 * @throws BusinessException
	 *             TODO
	 */
	public Group findGroupBaseInfo(Map<String, String> map)
			throws BusinessException;

	/**
	 * 首页，查询推荐的窝窝
	 * 
	 * @param page
	 *            TODO
	 * 
	 * @return
	 */
	public List<Group> findCommendGroup(SimplePage page);

	public List<Group> findCommendGroupAndTopic(SimplePage page);

	/**
	 * 更新窝窝状态，所属分类
	 * 
	 * @param map
	 * @throws BusinessException
	 */
	public void updateGroup(Map<String, String> map) throws BusinessException;

	public void saveGroupGrade(Map<String, String> map)
			throws BusinessException;

	/**
	 * api
	 * 
	 * @param params
	 *            TODO
	 * 
	 * @return
	 */

	public Map<String, Object> findCommendGroups4Api(Map<String, String> params);

	public Map<String, Object> findJoinedGroups4Api(Map<String, String> params,
			Integer userId);

	public Map<String, Object> findCreatedGroups4Api(
			Map<String, String> params, Integer userId);
}
