package com.lhl.quan.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lhl.entity.Message;
import com.lhl.entity.NoticeParam;
import com.lhl.enums.NoticeType;
import com.lhl.quan.dao.MessageDao;
import com.lhl.quan.dao.NoticeDao;
import com.lhl.quan.dao.UserDao;
import com.lhl.quan.service.MessageService;
import com.lhl.util.FormatAt;

public class MessageServiceImpl implements MessageService {

	private MessageDao messageDao;

	private UserDao userDao;

	private NoticeDao noticeDao;

	private final SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public void setNoticeDao(NoticeDao noticeDao) {

		this.noticeDao = noticeDao;
	}

	public void setMessageDao(MessageDao messageDao) {

		this.messageDao = messageDao;
	}

	public void setUserDao(UserDao userDao) {

		this.userDao = userDao;
	}

	@Override
	public List<Message> queryMessage(String userId, int offset, int total) throws Exception {

		List<Message> list = messageDao.queryMessage(userId, offset, total);
		return list;
	}

	@Override
	public int getCount(String userId) throws Exception {

		return messageDao.getCount(userId);
	}

	@Override
	public Message addMessage(Message message, String userId) throws Exception {

		String content = message.getMessage();
		String quote = message.getQuote();
		List<String> referers = new ArrayList<String>();
		String formatContent = FormatAt.getInstance().GenerateRefererLinks(userDao, content, referers);
		String subCon = formatContent;
		if (quote != null && !"".equals(quote)) {
			subCon = quote + formatContent;
		}
		message.setMessage(subCon);
		message.setPostTime(formate.format(new Date()));
		messageDao.addMessage(message);
		int id = message.getId();
		//启动一个线程发布消息
		NoticeParam noticeParm = new NoticeParam();
		noticeParm.setReceiveUserId(message.getUserId());
		noticeParm.setNoticeType(NoticeType.REMESSAGE);
		noticeParm.setAtUserIds(referers);
		noticeParm.setSendUserId(message.getReUserId());
		noticeParm.setReId(id);
		NoticeThread noticeThread = new NoticeThread(noticeParm);
		Thread thread = new Thread(noticeThread);
		thread.start();

		return message;
	}

	public boolean deletMessage(String curUserId, int id) {

		Message message = messageDao.queryMessageById(id);
		if (null != message && message.getUserId().equals(curUserId)) {
			messageDao.deleteMessage(id);
			return true;
		}
		else {
			return false;
		}
	}
}
