package com.ulewo.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ulewo.dao.MessageDao;
import com.ulewo.dao.UserDao;
import com.ulewo.entity.Message;
import com.ulewo.entity.NoticeParam;
import com.ulewo.enums.NoticeType;
import com.ulewo.service.MessageService;
import com.ulewo.util.FormatAt;
import com.ulewo.util.StringUtils;

@Service("messageService")
public class MessageServiceImpl implements MessageService {
	@Autowired
	private MessageDao messageDao;

	@Autowired
	private UserDao userDao;

	private final SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public List<Message> queryMessage(String userId, int offset, int total) throws Exception {

		List<Message> list = messageDao.queryMessage(userId, offset, total);
		for (Message message : list) {
			message.setPostTime(StringUtils.friendly_time(message.getPostTime()));
		}
		return list;
	}

	@Override
	public int getCount(String userId) throws Exception {

		return messageDao.getCount(userId);
	}

	@Override
	public Message addMessage(Message message) throws Exception {

		String content = message.getMessage();
		List<String> referers = new ArrayList<String>();
		String formatContent = FormatAt.getInstance().GenerateRefererLinks(userDao, content, referers);
		String subCon = formatContent;
		message.setMessage(subCon);
		message.setPostTime(formate.format(new Date()));
		messageDao.addMessage(message);
		message.setPostTime(StringUtils.friendly_time(message.getPostTime()));
		int id = message.getId();
		// 启动一个线程发布消息
		NoticeParam noticeParm = new NoticeParam();
		noticeParm.setUserId(message.getUserId());
		// 如果不是二级回复接受人就是楼主，否则就是@的人。
		if (StringUtils.isEmpty(message.getAtUserId())) {
			noticeParm.setReceiveUserId(message.getUserId());
		}
		else {
			noticeParm.setReceiveUserId(message.getAtUserId());
		}
		noticeParm.setNoticeType(NoticeType.REMESSAGE);
		noticeParm.setAtUserIds(referers);
		noticeParm.setSendUserId(message.getReUserId());

		if (StringUtils.isNotEmpty(message.getAtUserId())) {
			noticeParm.setReceiveUserId(message.getAtUserId());
		}
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
