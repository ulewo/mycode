package com.ulewo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ulewo.util.SimplePage;

/**
 * 收藏
 * 
 * @author luo.hl
 * @date 2013-12-8 下午4:25:31
 * @version 3.0
 * @copyright www.ulewo.com
 */
public interface CollectionMapper<T> extends BaseMapper<T> {

    public int selectTopicInfoCount(@Param("param") Map<String, String> param);

    public int selectBlogInfoCount(@Param("param") Map<String, String> param);

    public List<T> selectTopicInfoList(
	    @Param("param") Map<String, String> param,
	    @Param("page") SimplePage page);

    public List<T> selectBlogInfoList(
	    @Param("param") Map<String, String> param,
	    @Param("page") SimplePage page);
}
