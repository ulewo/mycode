package com.ulewo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ulewo.util.SimplePage;

/**
 * 好友
 * 
 * @author luo.hl
 * @date 2013-12-8 下午4:41:20
 * @version 3.0
 * @copyright www.ulewo.com
 */
public interface UserFriendMapper<T> extends BaseMapper<T> {

    /**
     * 查询关注人数
     * 
     * @param obj
     * @return
     */
    public int selectFocusCount(@Param("param") T obj);

    /**
     * 查询关注的人
     * 
     * @param obj
     * @param page
     * @return
     */
    public List<T> selectFocusList(@Param("param") T obj,
	    @Param("page") SimplePage page);

    /**
     * 查询粉丝数量
     * 
     * @param obj
     * @return
     */
    public int selectFansCount(@Param("param") T obj);

    /**
     * 查询粉丝
     * 
     * @param obj
     * @param page
     * @return
     */
    public List<T> selectFansList(@Param("param") T obj,
	    @Param("page") SimplePage page);

}
