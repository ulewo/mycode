package com.ulewo.mapper;

import org.apache.ibatis.annotations.Param;

import com.ulewo.model.User;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2013-12-8 下午12:09:34
 * @version 3.0
 * @copyright www.ulewo.com
 */
public interface UserMapper<T> extends BaseMapper<T> {
    /**
     * 根据用户名、or 邮箱 、or userId查询用户信息
     * 
     * @param user
     * @return
     */
    public User selectUser(@Param("user") User user);

    public User selectUserByUserName(@Param("userName") String userName);

    public User selectUserByUserId(@Param("userId") Integer userId);

    public User selectUserBaseInfo(@Param("userId") Integer userId);

}
