package com.ulewo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ulewo.dao.MessageDao;
import com.ulewo.entity.Message;
import com.ulewo.service.MessageService;

@Service("messageService")
public class MessageServiceImpl implements MessageService {
	@Autowired
	private MessageDao messageDao;

	public void setMessageDao(MessageDao messageDao) {

		this.messageDao = messageDao;
	}

	@Override
	public List<Message> queryList() {

		return messageDao.queryList();
	}

	@Override
	public Message queryMessage(int id) {

		return messageDao.queryMessage(id);
	}

	@Override
	public void delete(int id) {

		messageDao.delete(id);
	}

	@Override
	public void update(Message message) {

		messageDao.update(message);
	}
}
