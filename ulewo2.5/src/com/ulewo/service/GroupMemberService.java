package com.ulewo.service;

import java.util.List;
import java.util.Map;


import com.ulewo.exception.BusinessException;
import com.ulewo.model.GroupMember;
import com.ulewo.util.UlewoPaginationResult;
import com.ulewo.util.UlewoPaginationResult4Json;

/**
 * @Title:
 * @author luohl
 * @date 2012-3-29
 * @version V1.0
 */
public interface GroupMemberService {

    /**
     * 加入群组
     * 
     * @param userId
     *            TODO
     * @param member
     * @return TODO
     */
    public GroupMember joinGroup(Map<String, String> map, Integer userId)
	    throws BusinessException;

    /**
     * 退出群组
     * 
     * @param member
     * @return
     */
    public void existGroup(Map<String, String> map, Integer userId)
	    throws BusinessException;

    /**
     * 接受成员
     * 
     * @param map
     * @param userId
     * @return
     */
    public void accetpMember(Map<String, String> map, Integer userId)
	    throws BusinessException;

    /**
     * 删除成员
     * 
     * @param map
     * @param userId
     * @throws BusinessException
     */
    public void deleteMember(Map<String, String> map, Integer userId)
	    throws BusinessException;

    /**
     * 查询所有的成员
     * 
     * @param map
     * @return
     */
    public UlewoPaginationResult<GroupMember> findMembers(
	    Map<String, String> map);

    public UlewoPaginationResult4Json<GroupMember> findMembers4Json(
	    Map<String, String> map);

    /**
     * 查询成员
     * 
     * @param map
     * @return
     */
    public GroupMember findMember(Map<String, String> map);

    /**
     * 查询活跃成员
     * 
     * @param map
     * @return
     */
    public List<GroupMember> findMembersActiveIndex(Map<String, String> map);

    /**
     * 查询所有成员
     * 
     * @param map
     * @return
     */
    public List<GroupMember> findAllMembersList(Map<String, String> map);
}
