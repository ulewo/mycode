package com.ulewo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ulewo.util.SimplePage;

/**
 * 附件mapper
 * 
 * @author luo.hl
 * @date 2013-12-8 下午4:12:31
 * @version 3.0
 * @copyright www.ulewo.com
 */
public interface AttachmentMapper<T> extends BaseMapper<T> {

	public int selectAttachment4TopicCount(@Param("param") Map<String, String> map);

	List<T> selectAttachment4Topic(@Param("param") Map<String, String> map, @Param("page") SimplePage page);
}
