package com.ulewo.service;

import java.util.Map;

import com.ulewo.exception.BusinessException;
import com.ulewo.model.Like;
import com.ulewo.model.SessionUser;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-7-5 下午5:31:18
 * @version 3.0
 * @copyright www.ulewo.com
 */
public interface LikeService {
	public void doLike(Map<String, String> map, SessionUser sessionUser)
			throws BusinessException;

	public int getLikeCount(Like like);
}
