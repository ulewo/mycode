package com.ulewo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ulewo.util.SimplePage;

/**
 * TODO: 主题
 * 
 * @author luo.hl
 * @date 2013-12-8 下午4:37:38
 * @version 3.0
 * @copyright www.ulewo.com
 */
public interface TopicMapper<T> extends BaseMapper<T> {

	public int selectTopicCount(@Param("param") Map<String, String> param);

	public List<T> selectTopicList(@Param("param") Map<String, String> param,
			@Param("page") SimplePage page);

	public T showTopic(@Param("param") Map<String, String> map);

	public T selectTopicByTopicId(@Param("topicId") Integer topicId);

	public List<T> selectTopicByTopicids(@Param("list") String list[]);

	public void insertBatch(@Param("list") List<T> list);

	public List<T> selectImageTopic4Index(
			@Param("pCategroyId") int pCategroyId,
			@Param("page") SimplePage page);

	public List<T> selectTopic4IndexNoImageByCategroyId(
			@Param("categroyId") int categroyId, @Param("list") List<T> list,
			@Param("page") SimplePage page);

	public List<T> selectTopic4IndexNoImageByPCategroyId(
			@Param("pCategroyId") int pCategroyId, @Param("list") List<T> list,
			@Param("page") SimplePage page);

	public List<T> selectTopic4Index(@Param("pCategroyId") int pCategroyId,
			@Param("page") SimplePage page);

	public List<T> selectTopicList4Search(@Param("topicId") String topicId);
}
