package com.ulewo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 调查详情
 * 
 * @author luo.hl
 * @date 2013-12-8 下午4:36:15
 * @version 3.0
 * @copyright www.ulewo.com
 */
public interface TopicSurveyDtlMapper<T> extends BaseMapper<T> {
    public List<T> selectTopicSurveyDtls(@Param("param") Map<String, String> map);
}
