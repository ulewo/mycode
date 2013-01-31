package com.lhl.quan.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lhl.entity.Message;
import com.lhl.entity.Notice;
import com.lhl.quan.dao.MessageDao;
import com.lhl.quan.dao.NoticeDao;
import com.lhl.quan.dao.UserDao;
import com.lhl.quan.service.MessageService;
import com.lhl.util.Constant;
import com.lhl.util.FormatAt;
import com.lhl.util.Tools;

public class MessageServiceImpl implements MessageService
{

	private MessageDao messageDao;

	private UserDao userDao;

	private NoticeDao noticeDao;

	private final SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public void setNoticeDao(NoticeDao noticeDao)
	{

		this.noticeDao = noticeDao;
	}

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
		return list;
	}

	@Override
	public int getCount(String userId) throws Exception
	{

		return messageDao.getCount(userId);
	}

	@Override
	public Message addMessage(Message message, String userId) throws Exception
	{

		String content = message.getMessage();
		String quote = message.getQuote();
		List<String> referers = new ArrayList<String>();
		String formatContent = FormatAt.getInstance().GenerateRefererLinks(userDao, content, referers);
		String subCon = formatContent;
		if (quote != null && !"".equals(quote))
		{
			subCon = quote + formatContent;
		}
		message.setMessage(subCon);
		message.setPostTime(formate.format(new Date()));
		messageDao.addMessage(message);
		int id = message.getId();
		String reUserName = message.getReUserName();
		if (Tools.isNotEmpty(message.getReUserId()) && message.getReUserId().equals(userId))
		{
			reUserName = "他";
		}

		String noticeCon = message.getReUserName() + "在" + reUserName + "的留言中提到了你";
		String url = "message.jsp?userId=" + userId + "#re" + id;
		for (String uid : referers)
		{
			Notice notice = FormatAt.getInstance().formateNotic(uid, url, Constant.NOTICE_TYPE1, noticeCon);
			noticeDao.createNotice(notice);
		}

		//发送回复的消息提示
		if (Tools.isNotEmpty(userId) && !userId.equals(message.getReUserId()))
		{
			noticeCon = message.getReUserName() + "给你留言了";
			url = "message.jsp?userId=" + userId + "#re" + id;
			Notice notice = FormatAt.getInstance().formateNotic(userId, url, Constant.NOTICE_TYPE1, noticeCon);
			noticeDao.createNotice(notice);
		}

		return message;
	}

	public boolean deletMessage(String curUserId, int id)
	{

		Message message = messageDao.queryMessageById(id);
		if (null != message && message.getUserId().equals(curUserId))
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
