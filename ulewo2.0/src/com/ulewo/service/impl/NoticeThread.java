package com.ulewo.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.ulewo.dao.ArticleDao;
import com.ulewo.dao.BlogArticleDao;
import com.ulewo.dao.NoticeDao;
import com.ulewo.dao.TalkDao;
import com.ulewo.dao.UserDao;
import com.ulewo.entity.Article;
import com.ulewo.entity.BlogArticle;
import com.ulewo.entity.Notice;
import com.ulewo.entity.NoticeParam;
import com.ulewo.entity.Talk;
import com.ulewo.entity.User;
import com.ulewo.enums.NoticeType;
import com.ulewo.enums.QueryUserType;
import com.ulewo.util.Constant;
import com.ulewo.util.Springfactory;
import com.ulewo.util.StringUtils;

public class NoticeThread implements Runnable {

	private ArticleDao articleDao;

	private NoticeDao noticeDao;

	private UserDao userDao;

	private BlogArticleDao blogArticleDao;

	private NoticeParam noticeParm;

	private TalkDao talkDao;

	private final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public NoticeThread(NoticeParam noticeParm) {

		this.noticeParm = noticeParm;
	}

	@Override
	public void run() {
		/*
		noticeDao = (NoticeDao) Springfactory.getBean("noticeDao");
		userDao = (UserDao) Springfactory.getBean("userDao");
		NoticeType noticeType = noticeParm.getNoticeType();
		int articleId = noticeParm.getArticleId();
		String receiveUserId = noticeParm.getReceiveUserId();
		String sendUserId = noticeParm.getSendUserId();
		List<String> atUserIds = noticeParm.getAtUserIds();
		int reId = noticeParm.getReId();
		String userId = noticeParm.getUserId();
		String url = "";
		switch (noticeType) {
		case REARTICLE: // 回复主题
			articleDao = (ArticleDao) Springfactory.getBean("articleDao");
			url = "../group/post.jspx?id=" + articleId + "#re" + reId;
			reArticle(articleId, receiveUserId, atUserIds, sendUserId, url);
			break;
		case ATINARTICLE: // 在文章中@
			articleDao = (ArticleDao) Springfactory.getBean("articleDao");
			url = "../group/post.jspx?id=" + articleId;
			atInArticle(articleId, atUserIds, sendUserId, url);
			break;
		case REBLOG: // 回复博客
			blogArticleDao = (BlogArticleDao) Springfactory.getBean("blogArticleDao");
			url = "blogdetail.jspx?id=" + articleId + "#re" + reId;
			reBlog(articleId, receiveUserId, atUserIds, sendUserId, url);
			break;
		case ATINBLOG: // 在博客中@
			blogArticleDao = (BlogArticleDao) Springfactory.getBean("blogArticleDao");
			url = "blogdetail.jspx?id=" + articleId;
			atInBlog(articleId, atUserIds, sendUserId, url);
			break;
		case REMESSAGE: // 回复留言
			url = "message.jsp?userId=" + userId + "#re" + reId;
			reMessage(userId, receiveUserId, atUserIds, sendUserId, url);
			break;
		case ATINTALK:
			url = "talkDetail.jspx?userId=" + sendUserId + "&talkId=" + articleId;
			atInTalk(articleId, atUserIds, sendUserId, url);
			break;
		case RETALK:
			talkDao = (TalkDao) Springfactory.getBean("talkDao");
			url = "talkDetail.jspx?userId=" + receiveUserId + "&talkId=" + articleId;
			reTalk(articleId, receiveUserId, atUserIds, sendUserId, url);
			break;
		}*/
	}

	// 回复主题
	private void reArticle(int articleId, String receiveUserId, List<String> atUserIds, String sendUserId, String url) {

		try {
			Article article = articleDao.queryTopicById(articleId);
			if (article != null) {
				String title = article.getTitle();
				String noticeCon = "";
				Notice notice = null;
				String sendUserName = "";
				if (StringUtils.isNotEmpty(sendUserId)) {
					User reUser = userDao.findUser(sendUserId, QueryUserType.USERID);
					if (reUser != null) {
						sendUserName = reUser.getUserName();
					}
				}
				// 如果接受用户ID为空，那么就是回复主题
				if (StringUtils.isEmpty(receiveUserId)) {
					receiveUserId = article.getAuthorId();
				}
				// 如果发送人和接受人不一样就发送消息
				if (!receiveUserId.equals(sendUserId)) {
					// 发送消息给被回复的人
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
				// 发送消息给被@的人
				if (atUserIds != null) {
					for (String atUserId : atUserIds) {
						// 如果发送人@自己，自己就不给自己发信息
						if (!atUserId.equals(sendUserId)) {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 在文章中@
	private void atInArticle(int articleId, List<String> atUserIds, String sendUserId, String url) {

		try {
			Article article = articleDao.queryTopicById(articleId);
			if (StringUtils.isNotEmpty(sendUserId)) {
				User reUser = userDao.findUser(sendUserId, QueryUserType.USERID);
				Notice notice;
				String noticeCon = "";
				if (reUser != null && article != null) {
					for (String atUserId : atUserIds) {
						if (!atUserId.equals(sendUserId)) {
							notice = new Notice();
							noticeCon = reUser.getUserName() + "在文章\"" + article.getTitle() + "\"中提到了你";
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 回复博客
	private void reBlog(int articleId, String receiveUserId, List<String> atUserIds, String sendUserId, String url) {

		try {
			BlogArticle article = blogArticleDao.queryBlogById(articleId);
			if (article != null) {
				String title = article.getTitle();
				String noticeCon = "";
				Notice notice = null;
				String sendUserName = "";
				if (StringUtils.isNotEmpty(sendUserId)) {
					User reUser = userDao.findUser(sendUserId, QueryUserType.USERID);
					if (reUser != null) {
						sendUserName = reUser.getUserName();
					}
				}
				// 如果接受用户ID为空，那么就是回复主题
				if (StringUtils.isEmpty(receiveUserId)) {
					receiveUserId = article.getUserId();
				}
				// 如果发送人和接受人不一样就发送消息
				if (!receiveUserId.equals(sendUserId)) {
					// 发送消息给被回复的人
					noticeCon = sendUserName + "在\"" + title + "\"中回复了你";
					notice = new Notice();
					notice.setUrl(url);
					notice.setUserId(receiveUserId);
					notice.setPostTime(format.format(new Date()));
					notice.setContent(noticeCon);
					notice.setType(Constant.NOTICE_TYPE3);
					notice.setStatus("N");
					noticeDao.createNotice(notice);
				}
				// 发送消息给被@的人
				if (atUserIds != null) {
					for (String atUserId : atUserIds) {
						// 如果发送人@自己，自己就不给自己发信息
						if (atUserId.equals(sendUserId)) {
							continue;
						}
						noticeCon = sendUserName + "在回复\"" + title + "\"中提到了你";
						notice = new Notice();
						notice.setUrl(url);
						notice.setUserId(atUserId);
						notice.setPostTime(format.format(new Date()));
						notice.setContent(noticeCon);
						notice.setType(Constant.NOTICE_TYPE3);
						notice.setStatus("N");
						noticeDao.createNotice(notice);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 在文章中@
	private void atInBlog(int articleId, List<String> atUserIds, String sendUserId, String url) {

		try {
			BlogArticle article = blogArticleDao.queryBlogById(articleId);
			if (StringUtils.isNotEmpty(sendUserId)) {
				User reUser = userDao.findUser(sendUserId, QueryUserType.USERID);
				Notice notice;
				String noticeCon = "";
				if (reUser != null && article != null) {
					for (String atUserId : atUserIds) {
						if (!atUserId.equals(sendUserId)) {
							notice = new Notice();
							noticeCon = reUser.getUserName() + "在文章\"" + article.getTitle() + "\"中提到了你";
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 留言
	private void reMessage(String userId, String receiveUserId, List<String> atUserIds, String sendUserId, String url) {

		try {

			String noticeCon = "";
			Notice notice = null;
			String sendUserName = "";
			if (StringUtils.isNotEmpty(sendUserId)) {
				User reUser = userDao.findUser(sendUserId, QueryUserType.USERID);
				if (reUser != null) {
					sendUserName = reUser.getUserName();
				}
			}
			// 如果发送人和接受人不一样就发送消息
			if (!receiveUserId.equals(sendUserId)) {
				// 发送消息给被回复的人
				if (receiveUserId.equals(userId)) {// 如果接受人和留言板主人一致消息标题
					noticeCon = sendUserName + "给你留言了";
				}
				else {
					noticeCon = sendUserName + "回复了你的留言了";
				}
				notice = new Notice();
				notice.setUrl(url);
				notice.setUserId(receiveUserId);
				notice.setPostTime(format.format(new Date()));
				notice.setContent(noticeCon);
				notice.setType(Constant.NOTICE_TYPE3);
				notice.setStatus("N");
				noticeDao.createNotice(notice);
			}
			// 发送消息给被@的人
			if (atUserIds != null) {
				for (String atUserId : atUserIds) {
					// 如果发送人@自己，自己就不给自己发信息
					if (atUserId.equals(sendUserId)) {
						continue;
					}
					noticeCon = sendUserName + "在留言中提到了你";
					notice = new Notice();
					notice.setUrl(url);
					notice.setUserId(atUserId);
					notice.setPostTime(format.format(new Date()));
					notice.setContent(noticeCon);
					notice.setType(Constant.NOTICE_TYPE3);
					notice.setStatus("N");
					noticeDao.createNotice(notice);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 在文章中@
	private void atInTalk(int articleId, List<String> atUserIds, String sendUserId, String url) {

		try {
			if (StringUtils.isNotEmpty(sendUserId)) {
				User reUser = userDao.findUser(sendUserId, QueryUserType.USERID);
				Notice notice;
				String noticeCon = "";
				if (reUser != null) {
					for (String atUserId : atUserIds) {
						if (!atUserId.equals(sendUserId)) {
							notice = new Notice();
							noticeCon = reUser.getUserName() + "在吐槽中提到了你";
							notice.setUrl(url);
							notice.setUserId(atUserId);
							notice.setPostTime(format.format(new Date()));
							notice.setContent(noticeCon);
							notice.setType(Constant.NOTICE_TYPE6);
							notice.setStatus("N");
							noticeDao.createNotice(notice);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void reTalk(int articleId, String receiveUserId, List<String> atUserIds, String sendUserId, String url) {

		try {
			Talk talk = talkDao.queryDetail(articleId);
			if (talk != null) {
				String noticeCon = "";
				Notice notice = null;
				String sendUserName = "";
				if (StringUtils.isNotEmpty(sendUserId)) {
					User reUser = userDao.findUser(sendUserId, QueryUserType.USERID);
					if (reUser != null) {
						sendUserName = reUser.getUserName();
					}
				}
				// 如果接受用户ID为空，那么就是回复主题
				if (StringUtils.isEmpty(receiveUserId)) {
					receiveUserId = talk.getUserId();
				}
				// 如果发送人和接受人不一样就发送消息
				if (!receiveUserId.equals(sendUserId)) {
					// 发送消息给被回复的人
					noticeCon = sendUserName + "在吐槽中回复了你";
					notice = new Notice();
					notice.setUrl(url);
					notice.setUserId(receiveUserId);
					notice.setPostTime(format.format(new Date()));
					notice.setContent(noticeCon);
					notice.setType(Constant.NOTICE_TYPE7);
					notice.setStatus("N");
					noticeDao.createNotice(notice);
				}
				// 发送消息给被@的人
				if (atUserIds != null) {
					for (String atUserId : atUserIds) {
						// 如果发送人@自己，自己就不给自己发信息
						if (!atUserId.equals(sendUserId)) {
							noticeCon = sendUserName + "在吐槽中提到了你";
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
