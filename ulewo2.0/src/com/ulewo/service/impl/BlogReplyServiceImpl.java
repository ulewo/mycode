package com.ulewo.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ulewo.dao.BlogArticleDao;
import com.ulewo.dao.BlogReplyDao;
import com.ulewo.dao.NoticeDao;
import com.ulewo.dao.UserDao;
import com.ulewo.entity.BlogArticle;
import com.ulewo.entity.BlogReply;
import com.ulewo.entity.NoticeParam;
import com.ulewo.entity.User;
import com.ulewo.enums.NoticeType;
import com.ulewo.enums.QueryUserType;
import com.ulewo.service.BlogReplyService;
import com.ulewo.util.Constant;
import com.ulewo.util.FormatAt;
import com.ulewo.util.StringUtils;

public class BlogReplyServiceImpl implements BlogReplyService {

	private BlogReplyDao blogReplyDao;

	private BlogArticleDao blogArticleDao;

	private UserDao userDao;

	private NoticeDao noticeDao;

	private final SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public void setNoticeDao(NoticeDao noticeDao) {

		this.noticeDao = noticeDao;
	}

	public void setUserDao(UserDao userDao) {

		this.userDao = userDao;
	}

	public void setBlogReplyDao(BlogReplyDao blogReplyDao) {

		this.blogReplyDao = blogReplyDao;
	}

	public void setBlogArticleDao(BlogArticleDao blogArticleDao) {

		this.blogArticleDao = blogArticleDao;
	}

	@Override
	public BlogReply addReply(BlogReply blogReply) {

		blogReply.setPostTime(formate.format(new Date()));
		String content = blogReply.getContent();
		List<String> referers = new ArrayList<String>();
		String formatContent = FormatAt.getInstance().GenerateRefererLinks(userDao, content, referers);
		String subCon = formatContent;
		blogReply.setContent(subCon);
		if (StringUtils.isEmpty(blogReply.getSourceFrom())) {
			blogReply.setSourceFrom("P");
		}
		int id = blogReplyDao.addReply(blogReply);
		blogReply.setId(id);
		blogReply.setPostTime(StringUtils.friendly_time(blogReply.getPostTime()));
		User curUser = userDao.findUser(blogReply.getUserId(), QueryUserType.USERID);
		curUser.setMark(curUser.getMark() + Constant.ARTICLE_MARK2);
		userDao.update(curUser);

		// 启动一个线程发布消息
		NoticeParam noticeParm = new NoticeParam();
		noticeParm.setArticleId(blogReply.getBlogId());
		noticeParm.setNoticeType(NoticeType.REBLOG);
		noticeParm.setAtUserIds(referers);
		noticeParm.setSendUserId(blogReply.getUserId());
		// 如果At的用户Id为不为空那么就是二级回复
		if (StringUtils.isNotEmpty(blogReply.getAtUserId())) {
			noticeParm.setReceiveUserId(blogReply.getAtUserId());
		}
		noticeParm.setReId(id);
		NoticeThread noticeThread = new NoticeThread(noticeParm);
		Thread thread = new Thread(noticeThread);
		thread.start();

		return blogReply;
	}

	@Override
	public List<BlogReply> queryBlogReplyByBlogId(int blogId) {

		List<BlogReply> list = new ArrayList<BlogReply>();
		list = blogReplyDao.queryReplyByBlogId(blogId);
		for (BlogReply reply : list) {
			reply.setPostTime(StringUtils.friendly_time(reply.getPostTime()));
		}
		return list;
	}

	@Override
	public int queryBlogReplyCountByBlogId(int blogId) {

		return blogReplyDao.queryReplyCountByBlogId(blogId);
	}

	@Override
	public boolean delete(String curUserId, int id) {

		// userId，当前用户的userId
		BlogReply reply = blogReplyDao.queryBlogReplyById(id);
		// 通过ID查询回复
		if (null != reply) {
			// 通过回复获取博客ID
			BlogArticle article = blogArticleDao.queryBlogById(reply.getBlogId());
			// 删除 1,博主可以删除评论 article.getUserId() 博主ID
			if (null != article && article.getUserId().equals(curUserId)) {
				blogReplyDao.delete(id);
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}

	public BlogReply queryBlogReplyById(int id) {

		BlogReply reply = blogReplyDao.queryBlogReplyById(id);
		reply.setPostTime(StringUtils.friendly_time(reply.getPostTime()));
		return reply;
	}
}
