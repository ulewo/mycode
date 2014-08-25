/* 
 * Copyright (C), 2004-2010, 三五互联科技股份有限公司
 * File Name: com.lhl.quan.service.ReArticleService.java
 * Encoding UTF-8 
 * Version: 1.0 
 * Date: 2012-3-30
 * History:
 * 1. Date: 2012-3-30
 *    Author: luohl
 *    Modification: 新建
 * 2. ...
 */
package com.ulewo.service;

import java.util.Map;

import com.ulewo.exception.BusinessException;
import com.ulewo.model.SessionUser;
import com.ulewo.model.TopicComment;
import com.ulewo.util.UlewoPaginationResult;
import com.ulewo.util.UlewoPaginationResult4Json;

/**
 * @Title:
 * @Description: 回复文章
 * @author luohl
 * @date 2012-3-30
 * @version V1.0
 */
public interface TopicCommentService {
	/**
	 * 新增回复
	 * 
	 * @param map
	 * @param sessionUser
	 * @return
	 * @throws BusinessException
	 */
	public TopicComment addComment(Map<String, String> map,
			SessionUser sessionUser) throws BusinessException;

	/**
	 * 
	 * description: 删除回复
	 * 
	 * @param map
	 * @param sessionUser
	 *            TODO @
	 * @author luohl
	 * @throws BusinessException
	 *             TODO
	 */
	public void deleteComment(Map<String, String> map, SessionUser sessionUser)
			throws BusinessException;

	/**
	 * 
	 * description: 分页查询回复
	 * 
	 * @param map
	 * @param offset
	 * @param total
	 * @return @
	 * @author luohl
	 */
	public UlewoPaginationResult<TopicComment> queryCommentByTopicId(
			Map<String, String> map);

	public UlewoPaginationResult4Json<TopicComment> queryComment4JsonByTopicId(
			Map<String, String> map) throws BusinessException;

	public void deleteCommentByAdmin(Map<String, String> map)
			throws BusinessException;

	public Map<String, Object> queryCommentByTopicId4Api(Map<String, String> map);
}
