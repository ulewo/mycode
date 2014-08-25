package com.ulewo.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.ulewo.enums.MemberType;
import com.ulewo.exception.BusinessException;
import com.ulewo.model.Group;
import com.ulewo.model.GroupMember;
import com.ulewo.model.SessionUser;
import com.ulewo.service.GroupMemberService;
import com.ulewo.service.GroupService;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-2-13 下午2:57:57
 * @version 0.1.0
 * @copyright yougou.com
 */
public class BaseGroupAction extends BaseAction {
	@Autowired
	private GroupService groupService;

	@Autowired
	private GroupMemberService groupMemberService;

	public Group checkGroup(Map<String, String> map, HttpSession session)
			throws BusinessException {
		Group group = groupService.findGroupBaseInfo(map);
		if (null == group) {
			throw new BusinessException("访问的窝窝不存在!");
		}
		SessionUser sessionUser = getSessionUser(session);
		if (null != sessionUser) {
			map.put("userId", sessionUser.getUserId().toString());
			GroupMember member = groupMemberService.findMember(map);
			if (null == member) {
				group.setMemberStatus(MemberType.ALLMEMBER.getValue());
			} else {
				group.setMemberStatus(member.getMemberType());
			}

		}
		return group;
	}
}
