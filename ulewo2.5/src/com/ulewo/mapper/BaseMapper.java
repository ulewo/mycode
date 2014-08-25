package com.ulewo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ulewo.util.SimplePage;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2013-12-9 下午5:52:35
 * @version 3.0
 * @copyright www.ulewo.com
 */
public interface BaseMapper<T> {
    /**
     * 新增
     * 
     * @param topic
     * @return
     */
    public int insert(T obj);

    /**
     * 查询单表基本信息
     * 
     * @param param
     * @return
     */
    public T selectBaseInfo(@Param("param") Map<String, String> param);

    /**
     * 查询单表基本信息集合
     * 
     * @param page
     *            TODO
     * @param obj
     * 
     * @return
     */
    public List<T> selectBaseInfoList(
	    @Param("param") Map<String, String> param,
	    @Param("page") SimplePage page);

    /**
     * 查询单表基本信息数量
     * 
     * @param obj
     * @return
     */
    public int selectBaseInfoCount(@Param("param") Map<String, String> param);

    /**
     * 更新
     * 
     * @param topic
     * @return
     */
    public int updateSelective(T obj);

    /**
     * 删除
     * 
     * @param id
     * @return
     */
    public int delete(@Param("param") Map<String, String> param);

}
