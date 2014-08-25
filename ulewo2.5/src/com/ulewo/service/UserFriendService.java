package com.ulewo.service;

import java.util.List;
import java.util.Map;

import com.ulewo.exception.BusinessException;
import com.ulewo.model.UserFriend;
import com.ulewo.util.UlewoPaginationResult;

public interface UserFriendService {

    /**
     * 新增好友
     * 
     * @param map
     * @param userId
     *            TODO
     * @throws BusinessException
     *             TODO
     */
    public void addFriend(Map<String, String> map, Integer userId)
	    throws BusinessException;

    /**
     * 删除好友
     * 
     * @param map
     * @param userId
     * @throws BusinessException
     *             TODO
     */
    public void deleteFirend(Map<String, String> map, Integer userId)
	    throws BusinessException;

    /**
     * 查询粉丝
     * 
     * @param map
     * @return
     * @throws BusinessException
     *             TODO
     */
    public UlewoPaginationResult<UserFriend> queryFans(Map<String, String> map)
	    throws BusinessException;

    public List<UserFriend> queryFans4List(Integer userId)
	    throws BusinessException;

    /**
     * 查询关注的人
     * 
     * @param map
     * @return
     * @throws BusinessException
     *             TODO
     */
    public UlewoPaginationResult<UserFriend> queryFocus(Map<String, String> map)
	    throws BusinessException;

    public List<UserFriend> queryFocus4List(Integer userId)
	    throws BusinessException;

    /**
     * 判断是否已经关注
     * 
     * @param map
     * @return
     */
    public boolean isHaveFocus(Map<String, String> map);
}
