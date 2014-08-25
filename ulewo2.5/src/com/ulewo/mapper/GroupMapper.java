package com.ulewo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ulewo.util.SimplePage;

/**
 * 群组
 * 
 * @author luo.hl
 * @date 2013-12-8 下午4:26:42
 * @version 3.0
 * @copyright www.ulewo.com
 */
public interface GroupMapper<T> extends BaseMapper<T> {

	/**
	 * 查询加入的窝窝
	 * 
	 * @param userId
	 * @param page
	 * @return
	 */
	public List<T> selectJoinedGroups(@Param("userId") Integer userId,
			@Param("page") SimplePage page);

	/**
	 * 加入窝窝的数量
	 * 
	 * @param userId
	 * @return
	 */
	public int selectJoinedGroupsCount(@Param("userId") Integer userId);

	/**
	 * 创建的窝窝
	 * 
	 * @param userId
	 * @param page
	 * @return
	 */
	public List<T> selectCreatedGroups(@Param("userId") Integer userId,
			@Param("page") SimplePage page);

	/**
	 * 创建窝窝的数量
	 * 
	 * @param userId
	 * @return
	 */
	public int selectCreatedGroupsCount(@Param("userId") Integer userId);

	/**
	 * 查询列表 包含创建者 成员数 文章数
	 * 
	 * @param param
	 * @param page
	 * @return
	 */
	public List<T> selectExtendInfoList(
			@Param("param") Map<String, String> param,
			@Param("page") SimplePage page);

	public T selectExtendInfo(@Param("param") Map<String, String> obj);

	/**
	 * 查询推荐的窝窝
	 * 
	 * @param page
	 *            TODO
	 * @return
	 */
	public List<T> selectCommendGroup(@Param("page") SimplePage page);

}
