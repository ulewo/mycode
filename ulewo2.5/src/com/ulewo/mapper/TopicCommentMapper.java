package com.ulewo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ulewo.util.SimplePage;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2013-12-8 下午4:38:04
 * @version 3.0
 * @copyright www.ulewo.com
 */
public interface TopicCommentMapper<T> extends BaseMapper<T> {
    /**
     * 查询一级回复数量
     * 
     * @param param
     * @return
     */
    public int pComentCount(@Param("param") Map<String, String> param);

    /**
     * 查询一级回复
     * 
     * @param param
     * @param page
     *            TODO
     * @return
     */
    public List<T> selectPComentList(@Param("param") Map<String, String> param,
	    @Param("page") SimplePage page);

    /**
     * 查询二级回复
     * 
     * @param param
     * @return
     */
    public List<T> selectSubComment(@Param("param") Map<String, String> param);
}
