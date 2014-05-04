package com.ulewo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 博客分类
 * 
 * @author luo.hl
 * @date 2013-12-8 下午4:22:00
 * @version 3.0
 * @copyright www.ulewo.com
 */
public interface BlogCategoryMapper<T> extends BaseMapper<T> {
	/**
	 * 查询所有分类和文章数量
	 * @param obj
	 * @return
	 */
	public List<T> selectCategoryWithBlogCount(@Param("param") T obj);

	/**
	 * 查询分类和文章数量
	 * @param obj
	 * @return
	 */
	public T selectCategorySingleWithBlogCount(@Param("param") T obj);
}
