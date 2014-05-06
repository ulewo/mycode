package com.ulewo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 博客分类
 * 
 * @author luo.hl
 * @date 2013-12-8 下午4:19:31
 * @version 3.0
 * @copyright www.ulewo.com
 */
public interface BlogCommentMapper<T> extends BaseMapper<T> {
    public void deleteBatch(@Param("blogId") Integer blogId,
	    @Param("list") List<String> list);
}
