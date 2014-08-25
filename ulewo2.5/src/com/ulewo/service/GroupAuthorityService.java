package com.ulewo.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.GroupMapper;
import com.ulewo.model.Group;

public class GroupAuthorityService {
    @Resource
    private GroupMapper<Group> groupMapper;

    /**
     * 校验操作权限
     * 
     * @param gid
     * @param userId
     * @return
     * @throws BusinessException
     */
    public Group checkGroupOpAuthority(Integer gid, Integer userId)
	    throws BusinessException {
	Group group = new Group();
	Map<String, String> map = new HashMap<String, String>();
	map.put("gid", String.valueOf(gid));
	group = groupMapper.selectBaseInfo(map);
	if (null == group) {
	    throw new BusinessException("你访问的窝窝不存在!");
	}
	if (!userId.equals(group.getGroupUserId())) {
	    throw new BusinessException("你没有权限进行此操作!");
	}
	return group;
    }
}
