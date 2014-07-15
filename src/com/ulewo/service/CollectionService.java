package com.ulewo.service;

import java.util.Map;

import com.ulewo.exception.BusinessException;
import com.ulewo.model.Collection;
import com.ulewo.model.SessionUser;
import com.ulewo.util.UlewoPaginationResult;

public interface CollectionService {

	/**
	 * 添加收藏
	 * @param map
	 * @param sessionUser
	 */
	public void addCollection(Map<String, String> map, SessionUser sessionUser) throws BusinessException;

	/**
	 * 查询文章是否已经收藏
	 * @param map
	 * @param sessionUser
	 * @return
	 */
	public Collection articleCollectionInfo(Map<String, String> map, SessionUser sessionUser);

	/**
	 * 删除收藏
	 * @param map
	 * @param sessionUser
	 */

	public void deleteCollection(Map<String, String> map, SessionUser sessionUser) throws BusinessException;

	/**
	 * 查询用户的收藏文章
	 * @param map
	 * @param sessionUser
	 * @return
	 */
	public UlewoPaginationResult<Collection> queryCollectionByUserId(Map<String, String> map, SessionUser sessionUser);

}
