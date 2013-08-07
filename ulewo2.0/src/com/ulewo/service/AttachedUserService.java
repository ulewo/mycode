package com.ulewo.service;

import java.util.List;

import com.ulewo.entity.AttachedUser;

public interface AttachedUserService {
	public void createAttachedUser(AttachedUser attachedUser);

	public AttachedUser queryAttachedUser(int attachedId, String userId);

	public List<AttachedUser> queryAttachedUserByAttachedId(int attachedId);
}
