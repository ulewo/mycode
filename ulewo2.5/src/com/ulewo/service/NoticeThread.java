package com.ulewo.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ulewo.enums.NoticeType;
import com.ulewo.mapper.BlastMapper;
import com.ulewo.mapper.BlogMapper;
import com.ulewo.mapper.NoticeMapper;
import com.ulewo.mapper.TopicMapper;
import com.ulewo.mapper.UserMapper;
import com.ulewo.model.Blast;
import com.ulewo.model.Blog;
import com.ulewo.model.Notice;
import com.ulewo.model.NoticeParam;
import com.ulewo.model.Topic;
import com.ulewo.model.User;
import com.ulewo.util.SpringUtil;

public class NoticeThread implements Runnable {

	@Log
	Logger log;

	@SuppressWarnings("unchecked")
	private static NoticeMapper<Notice> noticeMapper = (NoticeMapper<Notice>) SpringUtil
			.getObject("noticeMapper");

	@SuppressWarnings("unchecked")
	private static UserMapper<User> userMapper = (UserMapper<User>) SpringUtil
			.getObject("userMapper");

	@SuppressWarnings("unchecked")
	private static TopicMapper<Topic> topicMapper = (TopicMapper<Topic>) SpringUtil
			.getObject("topicMapper");

	@SuppressWarnings("unchecked")
	private static BlogMapper<Blog> blogMapper = (BlogMapper<Blog>) SpringUtil
			.getObject("blogMapper");

	@SuppressWarnings("unchecked")
	private static BlastMapper<Blast> blastMapper = (BlastMapper<Blast>) SpringUtil
			.getObject("blastMapper");

	private NoticeParam noticeParm;

	public NoticeThread(NoticeParam noticeParm) {

		this.noticeParm = noticeParm;
	}

	@Override
	public void run() {
		NoticeType noticeType = noticeParm.getNoticeType();
		switch (noticeType) {
		case ATINTOPIC: // 在主题中@

			atInTopic(topicMapper, noticeParm.getArticleId(),
					noticeParm.getAtUserIds(), noticeParm.getSendUserId());
			break;
		case RETOPIC: // 回复文章
			this.reTopic(topicMapper, noticeParm.getArticleId(),
					noticeParm.getReceivedUserId(), noticeParm.getAtUserIds(),
					noticeParm.getSendUserId(), noticeParm.getCommentId());
			break;
		case ATINBLOG: // 在博客中@
			atInBlog(blogMapper, noticeParm.getArticleId(),
					noticeParm.getAtUserIds(), noticeParm.getSendUserId());
			break;
		case REBLOG: // 回复博客
			reBlog(blogMapper, noticeParm.getArticleId(),
					noticeParm.getReceivedUserId(), noticeParm.getAtUserIds(),
					noticeParm.getSendUserId(), noticeParm.getCommentId());
			break;
		case ATINBLAST:// 在吐槽中@
			atInBlast(blastMapper, noticeParm.getArticleId(),
					noticeParm.getAtUserIds(), noticeParm.getSendUserId());
			break;
		case REBLAST:// 回复吐槽"
			reBlast(blastMapper, noticeParm.getArticleId(),
					noticeParm.getReceivedUserId(), noticeParm.getAtUserIds(),
					noticeParm.getSendUserId(), noticeParm.getCommentId());
			break;
		}
	}

	// 在文章中@
	private void atInTopic(TopicMapper<Topic> topicMapper, int articleId,
			List<Integer> atUserIds, Integer sendUserId) {
		try {
			Topic topic = topicMapper.selectTopicByTopicId(articleId);
			if (topic != null) {
				String url = "group/" + topic.getGid() + "/topic/" + articleId;
				if (null != sendUserId) {
					User reUser = userMapper.selectUserByUserId(sendUserId);
					Notice notice;
					String title = "";
					String curDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(new Date());
					if (reUser != null) {
						for (Integer atUserId : atUserIds) {
							if (!atUserId.equals(sendUserId)) {// 如果at了自己，不给自己发消息
								notice = new Notice();
								title = reUser.getUserName()
										+ "在文章<span class='notice_tit'>"
										+ topic.getTitle() + "</span>中提到了你";
								notice.setTitle(title);
								notice.setUrl(url);
								notice.setReceivedUserId(atUserId);
								notice.setCreateTime(curDate);
								noticeMapper.insert(notice);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	private void reTopic(TopicMapper<Topic> topicMapper, int articleId,
			Integer receiveUserId, List<Integer> atUserIds, Integer sendUserId,
			int reId) {
		try {
			Topic topic = topicMapper.selectTopicByTopicId(articleId);
			if (topic != null) {
				if (receiveUserId == null || receiveUserId == 0) {// 回复的时候
					// 接收人为空那么就是回复主题
					receiveUserId = topic.getUserId();
				}
				String url = "group/" + topic.getGid() + "/topic/" + articleId
						+ "#" + reId;
				String title = "";
				Notice notice = null;
				String curDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(new Date());
				if (null != sendUserId) {
					User reUser = userMapper.selectUserByUserId(sendUserId);
					if (reUser != null) {
						// 如果发送人和接受人不一样就发送消息
						if (!receiveUserId.equals(sendUserId)) {
							// 发送消息给被回复的人
							title = reUser.getUserName()
									+ "在文章<span class='notice_tit'>"
									+ topic.getTitle() + "</span>中回复了你";
							notice = new Notice();
							notice.setUrl(url);
							notice.setTitle(title);
							notice.setReceivedUserId(receiveUserId);
							notice.setCreateTime(curDate);
							noticeMapper.insert(notice);
						}
						// 发送消息给被@的人
						if (atUserIds != null) {
							for (Integer atUserId : atUserIds) {
								// 如果发送人@自己，自己就不给自己发信息
								if (!atUserId.equals(sendUserId)) {
									title = reUser.getUserName()
											+ "在<span class='notice_tit'>回复</span>文章<span class='notice_tit'>"
											+ topic.getTitle() + "</span>中提到了你";
									notice = new Notice();
									notice.setUrl(url);
									notice.setReceivedUserId(atUserId);
									notice.setCreateTime(curDate);
									notice.setTitle(title);
									noticeMapper.insert(notice);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 在博客中@
	private void atInBlog(BlogMapper<Blog> blogMapper, int articleId,
			List<Integer> atUserIds, Integer sendUserId) {
		try {
			Blog blog = blogMapper.selectBlogByBlogId(articleId);
			if (blog != null) {
				String url = "user/" + blog.getUserId() + "/blog/"
						+ blog.getBlogId();
				if (null != sendUserId) {
					User reUser = userMapper.selectUserByUserId(sendUserId);
					Notice notice;
					String title = "";
					String curDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(new Date());
					if (reUser != null) {
						for (Integer atUserId : atUserIds) {
							if (!atUserId.equals(sendUserId)) {// 如果at了自己，不给自己发消息
								notice = new Notice();
								title = reUser.getUserName()
										+ "在博客<span class='notice_tit'>"
										+ blog.getTitle() + "</span>中提到了你";
								notice.setTitle(title);
								notice.setUrl(url);
								notice.setReceivedUserId(atUserId);
								notice.setCreateTime(curDate);
								noticeMapper.insert(notice);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	private void reBlog(BlogMapper<Blog> blogMapper, int articleId,
			Integer receiveUserId, List<Integer> atUserIds, Integer sendUserId,
			int reId) {
		try {
			Blog blog = blogMapper.selectBlogByBlogId(articleId);
			if (blog != null) {
				if (receiveUserId == null || receiveUserId == 0) {// 回复的时候
					// 接收人为空那么就是回复主题
					receiveUserId = blog.getUserId();
				}
				String url = "user/" + blog.getUserId() + "/blog/"
						+ blog.getBlogId() + "#" + reId;
				String title = "";
				Notice notice = null;
				String curDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(new Date());
				if (null != sendUserId) {
					User reUser = userMapper.selectUserByUserId(sendUserId);
					if (reUser != null) {
						// 如果发送人和接受人不一样就发送消息
						if (!receiveUserId.equals(sendUserId)) {
							// 发送消息给被回复的人
							title = reUser.getUserName()
									+ "在博客<span class='notice_tit'>"
									+ blog.getTitle() + "</span>中回复了你";
							notice = new Notice();
							notice.setUrl(url);
							notice.setReceivedUserId(receiveUserId);
							notice.setTitle(title);
							notice.setCreateTime(curDate);
							noticeMapper.insert(notice);
						}
						// 发送消息给被@的人
						if (atUserIds != null) {
							for (Integer atUserId : atUserIds) {
								// 如果发送人@自己，自己就不给自己发信息
								if (!atUserId.equals(sendUserId)) {
									title = reUser.getUserName()
											+ "在<span class='notice_tit'>回复</span>博客<span class='notice_tit'>"
											+ blog.getTitle() + "</span>中提到了你";
									notice = new Notice();
									notice.setUrl(url);
									notice.setReceivedUserId(atUserId);
									notice.setCreateTime(curDate);
									notice.setTitle(title);
									noticeMapper.insert(notice);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 在吐槽中@
	private void atInBlast(BlastMapper<Blast> blastMapper, int articleId,
			List<Integer> atUserIds, Integer sendUserId) {
		try {
			Blast blast = blastMapper.selectBlastByBlastId(articleId);
			if (blast != null) {
				String url = "user/" + blast.getUserId() + "/blast/"
						+ blast.getBlastId();
				if (null != sendUserId) {
					User reUser = userMapper.selectUserByUserId(sendUserId);
					Notice notice;
					String title = "";
					String curDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(new Date());
					if (reUser != null) {
						for (Integer atUserId : atUserIds) {
							if (!atUserId.equals(sendUserId)) {// 如果at了自己，不给自己发消息
								notice = new Notice();
								title = reUser.getUserName()
										+ "在吐槽<span class='notice_tit'>"
										+ blast.getContent() + "</span>中提到了你";
								notice.setTitle(title);
								notice.setUrl(url);
								notice.setReceivedUserId(atUserId);
								notice.setCreateTime(curDate);
								noticeMapper.insert(notice);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	private void reBlast(BlastMapper<Blast> blastMapper, int articleId,
			Integer receiveUserId, List<Integer> atUserIds, Integer sendUserId,
			int reId) {
		try {
			Blast blast = blastMapper.selectBlastByBlastId(articleId);
			if (blast != null) {
				if (receiveUserId == null) {// 回复的时候 接收人为空那么就是回复主题
					receiveUserId = blast.getUserId();
				}
				String url = "user/" + blast.getUserId() + "/blast/"
						+ blast.getBlastId() + "#" + reId;
				String title = "";
				Notice notice = null;
				String curDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(new Date());
				if (null != sendUserId) {
					User reUser = userMapper.selectUserByUserId(sendUserId);
					if (reUser != null) {
						// 如果发送人和接受人不一样就发送消息
						if (!receiveUserId.equals(sendUserId)) {
							// 发送消息给被回复的人
							title = reUser.getUserName()
									+ "在吐槽<span class='notice_tit'>"
									+ blast.getContent() + "</span>中回复了你";
							notice = new Notice();
							notice.setUrl(url);
							notice.setReceivedUserId(receiveUserId);
							notice.setCreateTime(curDate);
							notice.setTitle(title);
							noticeMapper.insert(notice);
						}
						// 发送消息给被@的人
						if (atUserIds != null) {
							for (Integer atUserId : atUserIds) {
								// 如果发送人@自己，自己就不给自己发信息
								if (!atUserId.equals(sendUserId)) {
									title = reUser.getUserName()
											+ "在<span class='notice_tit'>回复</span>吐槽<span class='notice_tit'>"
											+ blast.getContent()
											+ "</span>中提到了你";
									notice = new Notice();
									notice.setUrl(url);
									notice.setReceivedUserId(atUserId);
									notice.setCreateTime(curDate);
									notice.setTitle(title);
									noticeMapper.insert(notice);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * 
	 * private void reArticle(int articleId, String receiveUserId, List<String>
	 * atUserIds, String sendUserId, int reId) {
	 * 
	 * try { Topic topic = topicMapper.selectTopicByTopicId(articleId); if
	 * (topic != null) { String url = "group/" + topic.getGid() + "/topic/" +
	 * articleId + "#" + reId; String title = article.getTitle(); String
	 * noticeCon = ""; Notice notice = null; String sendUserName = ""; if
	 * (StringUtils.isNotEmpty(sendUserId)) { User reUser =
	 * userDao.findUser(sendUserId, QueryUserType.USERID); if (reUser != null) {
	 * sendUserName = reUser.getUserName(); } } // 如果接受用户ID为空，那么就是回复主题 if
	 * (StringUtils.isEmpty(receiveUserId)) { receiveUserId =
	 * article.getAuthorId(); } // 如果发送人和接受人不一样就发送消息 if
	 * (!receiveUserId.equals(sendUserId)) { // 发送消息给被回复的人 noticeCon =
	 * sendUserName + "在\"" + title + "\"中回复了你"; notice = new Notice();
	 * notice.setUrl(url); notice.setUserId(receiveUserId);
	 * notice.setPostTime(format.format(new Date()));
	 * notice.setContent(noticeCon); notice.setType(Constant.NOTICE_TYPE2);
	 * notice.setStatus("N"); noticeDao.createNotice(notice); } // 发送消息给被@的人 if
	 * (atUserIds != null) { for (String atUserId : atUserIds) { //
	 * 如果发送人@自己，自己就不给自己发信息 if (!atUserId.equals(sendUserId)) { noticeCon =
	 * sendUserName + "在回复\"" + title + "\"中提到了你"; notice = new Notice();
	 * notice.setUrl(url); notice.setUserId(atUserId);
	 * notice.setPostTime(format.format(new Date()));
	 * notice.setContent(noticeCon); notice.setType(Constant.NOTICE_TYPE2);
	 * notice.setStatus("N"); noticeDao.createNotice(notice); } } } } } catch
	 * (Exception e) { e.printStackTrace(); } }
	 * 
	 * // 回复博客 private void reBlog(int articleId, String receiveUserId,
	 * List<String> atUserIds, String sendUserId, int reId) {
	 * 
	 * try { Blog article = blogArticleDao.queryBlogById(articleId); if (article
	 * != null) { String url = "user/" + article.getUserId() + "/blog/" +
	 * articleId; String title = article.getTitle(); String noticeCon = "";
	 * Notice notice = null; String sendUserName = ""; if
	 * (StringUtils.isNotEmpty(sendUserId)) { User reUser =
	 * userDao.findUser(sendUserId, QueryUserType.USERID); if (reUser != null) {
	 * sendUserName = reUser.getUserName(); } } // 如果接受用户ID为空，那么就是回复主题 if
	 * (StringUtils.isEmpty(receiveUserId)) { receiveUserId =
	 * article.getUserId(); } // 如果发送人和接受人不一样就发送消息 if
	 * (!receiveUserId.equals(sendUserId)) { // 发送消息给被回复的人 noticeCon =
	 * sendUserName + "在\"" + title + "\"中回复了你"; notice = new Notice();
	 * notice.setUrl(url); notice.setUserId(receiveUserId);
	 * notice.setPostTime(format.format(new Date()));
	 * notice.setContent(noticeCon); notice.setType(Constant.NOTICE_TYPE3);
	 * notice.setStatus("N"); noticeDao.createNotice(notice); } // 发送消息给被@的人 if
	 * (atUserIds != null) { for (String atUserId : atUserIds) { //
	 * 如果发送人@自己，自己就不给自己发信息 if (atUserId.equals(sendUserId)) { continue; }
	 * noticeCon = sendUserName + "在回复\"" + title + "\"中提到了你"; notice = new
	 * Notice(); notice.setUrl(url); notice.setUserId(atUserId);
	 * notice.setPostTime(format.format(new Date()));
	 * notice.setContent(noticeCon); notice.setType(Constant.NOTICE_TYPE3);
	 * notice.setStatus("N"); noticeDao.createNotice(notice); } } } } catch
	 * (Exception e) { e.printStackTrace(); } }
	 * 
	 * // 在文章中@ private void atInBlog(int articleId, List<String> atUserIds,
	 * String sendUserId, String url) {
	 * 
	 * try { Blog article = blogArticleDao.queryBlogById(articleId); url =
	 * "user/" + article.getUserId() + "/blog/" + articleId; if
	 * (StringUtils.isNotEmpty(sendUserId)) { User reUser = userDao
	 * .findUser(sendUserId, QueryUserType.USERID); Notice notice; String
	 * noticeCon = ""; if (reUser != null && article != null) { for (String
	 * atUserId : atUserIds) { if (!atUserId.equals(sendUserId)) { notice = new
	 * Notice(); noticeCon = reUser.getUserName() + "在文章\"" + article.getTitle()
	 * + "\"中提到了你"; notice.setUrl(url); notice.setUserId(atUserId);
	 * notice.setPostTime(format.format(new Date()));
	 * notice.setContent(noticeCon); notice.setType(Constant.NOTICE_TYPE1);
	 * notice.setStatus("N"); noticeDao.createNotice(notice); } } } } } catch
	 * (Exception e) { e.printStackTrace(); } }
	 * 
	 * // 留言 private void reMessage(String userId, String receiveUserId,
	 * List<String> atUserIds, String sendUserId, String url) {
	 * 
	 * try {
	 * 
	 * String noticeCon = ""; Notice notice = null; String sendUserName = ""; if
	 * (StringUtils.isNotEmpty(sendUserId)) { User reUser = userDao
	 * .findUser(sendUserId, QueryUserType.USERID); if (reUser != null) {
	 * sendUserName = reUser.getUserName(); } } // 如果发送人和接受人不一样就发送消息 if
	 * (!receiveUserId.equals(sendUserId)) { // 发送消息给被回复的人 if
	 * (receiveUserId.equals(userId)) {// 如果接受人和留言板主人一致消息标题 noticeCon =
	 * sendUserName + "给你留言了"; } else { noticeCon = sendUserName + "回复了你的留言了"; }
	 * notice = new Notice(); notice.setUrl(url);
	 * notice.setUserId(receiveUserId); notice.setPostTime(format.format(new
	 * Date())); notice.setContent(noticeCon);
	 * notice.setType(Constant.NOTICE_TYPE3); notice.setStatus("N");
	 * noticeDao.createNotice(notice); } // 发送消息给被@的人 if (atUserIds != null) {
	 * for (String atUserId : atUserIds) { // 如果发送人@自己，自己就不给自己发信息 if
	 * (atUserId.equals(sendUserId)) { continue; } noticeCon = sendUserName +
	 * "在留言中提到了你"; notice = new Notice(); notice.setUrl(url);
	 * notice.setUserId(atUserId); notice.setPostTime(format.format(new
	 * Date())); notice.setContent(noticeCon);
	 * notice.setType(Constant.NOTICE_TYPE3); notice.setStatus("N");
	 * noticeDao.createNotice(notice); } } } catch (Exception e) {
	 * e.printStackTrace(); } }
	 * 
	 * // 在文章中@ private void atInTalk(int articleId, List<String> atUserIds,
	 * String sendUserId, String url) {
	 * 
	 * try { if (StringUtils.isNotEmpty(sendUserId)) { Blast talk =
	 * talkDao.queryDetail(articleId); url = "user/" + talk.getUserId() +
	 * "/talk/" + articleId; User reUser = userDao .findUser(sendUserId,
	 * QueryUserType.USERID); Notice notice; String noticeCon = ""; if (reUser
	 * != null) { for (String atUserId : atUserIds) { if
	 * (!atUserId.equals(sendUserId)) { notice = new Notice(); noticeCon =
	 * reUser.getUserName() + "在吐槽中提到了你"; notice.setUrl(url);
	 * notice.setUserId(atUserId); notice.setPostTime(format.format(new
	 * Date())); notice.setContent(noticeCon);
	 * notice.setType(Constant.NOTICE_TYPE6); notice.setStatus("N");
	 * noticeDao.createNotice(notice); } } } } } catch (Exception e) {
	 * e.printStackTrace(); } }
	 * 
	 * private void reTalk(int articleId, String receiveUserId, List<String>
	 * atUserIds, String sendUserId, String url) {
	 * 
	 * try { Blast talk = talkDao.queryDetail(articleId); if (talk != null) {
	 * url = "user/" + talk.getUserId() + "/talk/" + articleId; String noticeCon
	 * = ""; Notice notice = null; String sendUserName = ""; if
	 * (StringUtils.isNotEmpty(sendUserId)) { User reUser =
	 * userDao.findUser(sendUserId, QueryUserType.USERID); if (reUser != null) {
	 * sendUserName = reUser.getUserName(); } } // 如果接受用户ID为空，那么就是回复主题 if
	 * (StringUtils.isEmpty(receiveUserId)) { receiveUserId = talk.getUserId();
	 * } // 如果发送人和接受人不一样就发送消息 if (!receiveUserId.equals(sendUserId)) { //
	 * 发送消息给被回复的人 noticeCon = sendUserName + "在吐槽中回复了你"; notice = new Notice();
	 * notice.setUrl(url); notice.setUserId(receiveUserId);
	 * notice.setPostTime(format.format(new Date()));
	 * notice.setContent(noticeCon); notice.setType(Constant.NOTICE_TYPE7);
	 * notice.setStatus("N"); noticeDao.createNotice(notice); } // 发送消息给被@的人 if
	 * (atUserIds != null) { for (String atUserId : atUserIds) { //
	 * 如果发送人@自己，自己就不给自己发信息 if (!atUserId.equals(sendUserId)) { noticeCon =
	 * sendUserName + "在吐槽中提到了你"; notice = new Notice(); notice.setUrl(url);
	 * notice.setUserId(atUserId); notice.setPostTime(format.format(new
	 * Date())); notice.setContent(noticeCon);
	 * notice.setType(Constant.NOTICE_TYPE2); notice.setStatus("N");
	 * noticeDao.createNotice(notice); } } } } } catch (Exception e) {
	 * e.printStackTrace(); } }
	 */

}
