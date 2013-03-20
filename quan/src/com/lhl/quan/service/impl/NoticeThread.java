package com.lhl.quan.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.lhl.entity.Article;
import com.lhl.entity.Notice;
import com.lhl.entity.NoticeParam;
import com.lhl.entity.User;
import com.lhl.enums.NoticeType;
import com.lhl.quan.dao.ArticleDao;
import com.lhl.quan.dao.NoticeDao;
import com.lhl.quan.dao.UserDao;
import com.lhl.util.Constant;
import com.lhl.util.Springfactory;
import com.lhl.util.Tools;

public class NoticeThread implements Runnable {

	private ArticleDao articleDao;

	private NoticeDao noticeDao;

	private UserDao userDao;

	private NoticeParam noticeParm;

	private final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public NoticeThread(NoticeParam noticeParm) {

		this.noticeParm = noticeParm;
	}

	@Override
	public void run() {

		articleDao = (ArticleDao) Springfactory.getBean("articleDao");
		noticeDao = (NoticeDao) Springfactory.getBean("noticeDao");
		userDao = (UserDao) Springfactory.getBean("userDao");

		NoticeType noticeType = noticeParm.getNoticeType();
		int articleId = noticeParm.getArticleId();
		String receiveUserId = noticeParm.getReceiveUserId();
		String sendUserId = noticeParm.getSendUserId();
		List<String> atUserIds = noticeParm.getAtUserIds();
		int reId = noticeParm.getReId();
		String url = "";
		switch (noticeType) {
		case REARTICLE: //回复主题
			url = "../group/post.jspx?id=" + articleId + "#re" + reId;

			reArticle(articleId, receiveUserId, atUserIds, sendUserId, url);
			break;
		case ATINARTICLE: //在文章中@
			url = "../group/post.jspx?id=" + articleId;
			atInArticle(articleId, atUserIds, sendUserId, url);
			break;
		case ATINREARTICLE: //在回复中@
			break;
		}
	}

	//回复主题
	private void reArticle(int articleId, String receiveUserId, List<String> atUserIds, String sendUserId, String url) {

		try {
			Article article = articleDao.queryTopicById(articleId);
			if (article != null) {
				String title = article.getTitle();
				String noticeCon = "";
				Notice notice = null;
				String sendUserName = "";
				if (Tools.isNotEmpty(sendUserId)) {
					User reUser = userDao.queryUser("", "", sendUserId);
					if (reUser != null) {
						sendUserName = reUser.getUserName();
					}
				}
				//如果接受用户ID为空，那么就是回复主题
				if (Tools.isEmpty(receiveUserId)) {
					receiveUserId = article.getAuthorId();
				}
				//如果发送人和接受人不一样就发送消息
				if (!receiveUserId.equals(sendUserId)) {
					//发送消息给被回复的人
					noticeCon = sendUserName + "在\"" + title + "\"中回复了你";
					notice = new Notice();
					notice.setUrl(url);
					notice.setUserId(receiveUserId);
					notice.setPostTime(format.format(new Date()));
					notice.setContent(noticeCon);
					notice.setType(Constant.NOTICE_TYPE2);
					notice.setStatus("N");
					noticeDao.createNotice(notice);
				}
				//发送消息给被@的人
				if (atUserIds != null) {
					for (String atUserId : atUserIds) {
						//如果发送人@自己，自己就不给自己发信息
						if (atUserId.equals(sendUserId)) {
							continue;
						}
						noticeCon = sendUserName + "在回复\"" + title + "\"中提到了你";
						notice = new Notice();
						notice.setUrl(url);
						notice.setUserId(atUserId);
						notice.setPostTime(format.format(new Date()));
						notice.setContent(noticeCon);
						notice.setType(Constant.NOTICE_TYPE2);
						notice.setStatus("N");
						noticeDao.createNotice(notice);
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	//在文章中@
	private void atInArticle(int articleId, List<String> atUserIds, String sendUserId, String url) {

		try {
			Article article = articleDao.queryTopicById(articleId);
			if (Tools.isNotEmpty(sendUserId)) {
				User reUser = userDao.queryUser("", "", sendUserId);
				Notice notice;
				String noticeCon = "";
				if (reUser != null && article != null) {
					for (String atUserId : atUserIds) {
						notice = new Notice();
						noticeCon = reUser.getUserName() + "在文章\"" + article.getTitle() + "\"中提到了你";
						notice = new Notice();
						notice.setUrl(url);
						notice.setUserId(atUserId);
						notice.setPostTime(format.format(new Date()));
						notice.setContent(noticeCon);
						notice.setType(Constant.NOTICE_TYPE1);
						notice.setStatus("N");
						noticeDao.createNotice(notice);
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
