package com.ulewo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ulewo.dao.AttachedUserDao;
import com.ulewo.entity.AttachedUser;
import com.ulewo.service.AttachedUserService;

@Service("attachedUserService")
public class AttachedUserImpl implements AttachedUserService {
	@Autowired
	private AttachedUserDao attachedUserDao;

	@Override
	public void createAttachedUser(AttachedUser attachedUser) {

		attachedUserDao.createAttachedUser(attachedUser);
	}

	@Override
	public AttachedUser queryAttachedUser(int attachedId, String userId) {

		return attachedUserDao.queryAttachedUser(attachedId, userId);
	}

}
