package com.ulewo.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 博客
 * 
 * @author luo.hl
 * @date 2013-12-8 下午4:18:41
 * @version 3.0
 * @copyright www.ulewo.com
 */
public interface BlogMapper<T> extends BaseMapper<T> {
    public T selectDetail(@Param("param") Map<String, String> map);

    public T selectBlogByBlogId(@Param("blogId") Integer blogId);
}
