package com.ulewo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ulewo.model.TopicCategory;

/**
 * 主题分类
 * 
 * @author luo.hl
 * @date 2013-12-8 下午4:38:20
 * @version 3.0
 * @copyright www.ulewo.com
 */
public interface TopicCategoryMapper<T> extends BaseMapper<T> {
	/**
	 * 查询分类和分类下的文章数
	 * 
	 * @param category
	 * @return
	 */
	public TopicCategory selectCategoryAndTopicCount(
			@Param("param") TopicCategory category);

	public List<TopicCategory> selectCategory4ListAndTopicCount(
			@Param("param") TopicCategory category);
}
