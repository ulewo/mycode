package com.ulewo.service;

import java.util.Map;

import com.ulewo.exception.BusinessException;
import com.ulewo.model.BlastComment;
import com.ulewo.util.UlewoPaginationResult;

public interface BlastCommentService {

	/**
	 * 回复吐槽
	 * 
	 * @param map
	 * @param userId
	 *            TODO
	 * @return TODO
	 * @throws Exception
	 */
	public BlastComment saveBlastComment(Map<String, String> map, Integer userId)
			throws BusinessException;

	public UlewoPaginationResult<BlastComment> queryBlastCommentByPag(
			Map<String, String> map) throws BusinessException;

	public void deleteComments(Map<String, String> map)
			throws BusinessException;

	public Map<String, Object> queryBlastCommentByPag4Api(
			Map<String, String> map) throws BusinessException;
}
