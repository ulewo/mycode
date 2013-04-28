package com.lhl.quan.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lhl.entity.BlogArticle;
import com.lhl.entity.BlogReply;
import com.lhl.entity.NoticeParam;
import com.lhl.entity.User;
import com.lhl.enums.NoticeType;
import com.lhl.quan.dao.BlogArticleDao;
import com.lhl.quan.dao.BlogReplyDao;
import com.lhl.quan.dao.NoticeDao;
import com.lhl.quan.dao.UserDao;
import com.lhl.quan.service.BlogReplyService;
import com.lhl.util.Constant;
import com.lhl.util.FormatAt;
import com.lhl.util.Tools;

public class BlogReplyServiceImpl implements BlogReplyService {

	private BlogReplyDao blogReplyDao;

	private BlogArticleDao blogArticleDao;

	private UserDao userDao;

	private NoticeDao noticeDao;

	private final SimpleDateFormat formate = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

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
		String quote = blogReply.getQuote();
		List<String> referers = new ArrayList<String>();
		String formatContent = FormatAt.getInstance().GenerateRefererLinks(
				userDao, content, referers);
		String subCon = formatContent;
		if (quote != null && !"".equals(quote)) {
			subCon = quote + formatContent;
		}
		blogReply.setContent(subCon);
		if (Tools.isEmpty(blogReply.getSourceFrom())) {
			blogReply.setSourceFrom("P");
		}
		int id = blogReplyDao.addReply(blogReply);
		blogReply.setId(id);
		blogReply.setPostTime(Tools.friendly_time(blogReply.getPostTime()));
		User curUser = userDao.queryUser(null, null, blogReply.getUserId());
		curUser.setMark(curUser.getMark() + Constant.ARTICLE_MARK2);
		userDao.updateUserSelectiveByUserId(curUser);

		// 启动一个线程发布消息
		NoticeParam noticeParm = new NoticeParam();
		noticeParm.setArticleId(blogReply.getBlogId());
		noticeParm.setNoticeType(NoticeType.REBLOG);
		noticeParm.setAtUserIds(referers);
		noticeParm.setSendUserId(blogReply.getUserId());
		// 如果At的用户Id为不为空那么就是二级回复
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
			reply.setPostTime(Tools.friendly_time(reply.getPostTime()));
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
			BlogArticle article = blogArticleDao.queryBlogById(reply
					.getBlogId());
			// 删除 1,博主可以删除评论 article.getUserId() 博主ID
			if (null != article && article.getUserId().equals(curUserId)) {
				blogReplyDao.delete(id);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public BlogReply queryBlogReplyById(int id) {
		BlogReply reply = blogReplyDao.queryBlogReplyById(id);
		reply.setPostTime(Tools.friendly_time(reply.getPostTime()));
		return reply;
	}
}
