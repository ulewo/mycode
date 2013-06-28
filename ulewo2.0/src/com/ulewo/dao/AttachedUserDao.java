package com.ulewo.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ulewo.entity.AttachedUser;

@Component
public class AttachedUserDao extends BaseDao {
	public void createAttachedUser(AttachedUser attachedUser) {

		this.getSqlMapClientTemplate().insert("attachedUser.createAttachedUser", attachedUser);
	}

	public AttachedUser queryAttachedUser(int attachedId, String userId) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("attachedId", attachedId);
		parmMap.put("userId", userId);
		return (AttachedUser) this.getSqlMapClientTemplate().queryForObject("attachedUser.queryAttachedUser", parmMap);
	}
}
