package com.ulewo.service;

import java.util.Map;

import com.ulewo.exception.BusinessException;
import com.ulewo.model.CommonDynamic;
import com.ulewo.util.UlewoPaginationResult;

public interface CommonDynamicService {

	/**
	 * 查询动态
	 * @param param
	 * @param userId TODO
	 * @return
	 * @throws BusinessException
	 */
	public UlewoPaginationResult<CommonDynamic> selectDynamic(Map<String, String> param, Integer userId)
			throws BusinessException;

	/**
	 * 查询关注的人所有动态的数量
	 * @param param
	 * @param page
	 * @return
	 */
	public int selectFocusAllDynamicCount(Map<String, String> param) throws BusinessException;

	/**
	 * 查询关注的人所有的动态
	 * @param param
	 * @return
	 */
	public UlewoPaginationResult<CommonDynamic> selectFocusAllDynamic(Map<String, String> param)
			throws BusinessException;

	/**
	 * 查询用户所有动态的数量
	 * @param param
	 * @param page
	 * @return
	 */
	public int selectUserAllDynamicCount(Map<String, String> param) throws BusinessException;

	/**
	 * 查询用户所有动态
	 * @param param
	 * @return
	 */
	public UlewoPaginationResult<CommonDynamic> selectUserAllDynamic(Map<String, String> param)
			throws BusinessException;

	/**
	 * 查询用户发表的文章
	 * @param param
	 * @return
	 * @throws BusinessException
	 */
	public UlewoPaginationResult<CommonDynamic> selectUserTopic(Map<String, String> param) throws BusinessException;

	/**
	 * 查询用户发表的博文
	 * @param param
	 * @return
	 * @throws BusinessException
	 */
	public UlewoPaginationResult<CommonDynamic> selectUserBlog(Map<String, String> param) throws BusinessException;

	/**
	 * 查询用户发表的吐槽
	 * @param param
	 * @return
	 * @throws BusinessException
	 */
	public UlewoPaginationResult<CommonDynamic> selectUserBlast(Map<String, String> param) throws BusinessException;
}
