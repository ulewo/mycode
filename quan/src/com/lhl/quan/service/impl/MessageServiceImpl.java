package com.lhl.quan.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.lhl.entity.Message;
import com.lhl.quan.dao.MessageDao;
import com.lhl.quan.dao.UserDao;
import com.lhl.quan.service.MessageService;

public class MessageServiceImpl implements MessageService
{

	private MessageDao messageDao;

	private UserDao userDao;

	private final SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public void setMessageDao(MessageDao messageDao)
	{

		this.messageDao = messageDao;
	}

	public void setUserDao(UserDao userDao)
	{

		this.userDao = userDao;
	}

	@Override
	public List<Message> queryMessage(String userId, int offset, int total) throws Exception
	{

		List<Message> list = messageDao.queryMessage(userId, offset, total);
		for (Message message : list)
		{
			message.setReMessages(messageDao.queryReMessage(message.getId()));
		}
		return list;
	}

	@Override
	public int getCount(String userId) throws Exception
	{

		return messageDao.getCount(userId);
	}

	@Override
	public Message addMessage(Message message) throws Exception
	{

		message.setPostTime(formate.format(new Date()));
		messageDao.addMessage(message);
		return message;
	}

	public boolean deletMessage(String userId, String curUserId, int id)
	{

		Message message = messageDao.queryMessageById(id);
		if (null != message && (message.getUserId().equals(curUserId) || curUserId.equals(userId)))
		{
			messageDao.deleteMessage(id);
			return true;
		}
		else
		{
			return false;
		}
	}
}
