package com.ulewo.mapper;

import org.apache.ibatis.annotations.Param;

import com.ulewo.model.Like;

/**
 * 吐槽
 * 
 * @author luo.hl
 * @date 2013-12-8 下午4:15:54
 * @version 3.0
 * @copyright www.ulewo.com
 */
public interface LikeMapper<T> extends BaseMapper<T> {
	public int getLikeCount(@Param("param") Like like);

	public void save(Like like);
}
