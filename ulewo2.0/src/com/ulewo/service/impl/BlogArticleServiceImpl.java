package com.ulewo.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ulewo.dao.BlogArticleDao;
import com.ulewo.dao.NoticeDao;
import com.ulewo.dao.UserDao;
import com.ulewo.entity.BlogArticle;
import com.ulewo.entity.NoticeParam;
import com.ulewo.entity.User;
import com.ulewo.enums.NoticeType;
import com.ulewo.enums.QueryUserType;
import com.ulewo.service.BlogArticleService;
import com.ulewo.util.Constant;
import com.ulewo.util.FormatAt;
import com.ulewo.util.StringUtils;

public class BlogArticleServiceImpl implements BlogArticleService {
	private BlogArticleDao blogArticleDao;

	private UserDao userDao;

	private NoticeDao noticeDao;

	public void setUserDao(UserDao userDao) {

		this.userDao = userDao;
	}

	public void setNoticeDao(NoticeDao noticeDao) {

		this.noticeDao = noticeDao;
	}

	public void setBlogArticleDao(BlogArticleDao blogArticleDao) {

		this.blogArticleDao = blogArticleDao;
	}

	private final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public boolean addBlog(BlogArticle blogArticle, User user) {

		blogArticle.setPostTime(format.format(new Date()));
		String content = blogArticle.getContent();
		List<String> referers = new ArrayList<String>();
		String formatContent = FormatAt.getInstance().GenerateRefererLinks(userDao, content, referers);

		blogArticle.setContent(formatContent);

		int blogId = blogArticleDao.addBlog(blogArticle);

		User curUser = userDao.findUser(user.getUserId(), QueryUserType.USERID);
		curUser.setMark(curUser.getMark() + Constant.ARTICLE_MARK5);
		userDao.update(curUser);

		// 启动一个线程发布消息
		NoticeParam noticeParm = new NoticeParam();
		noticeParm.setArticleId(blogId);
		noticeParm.setNoticeType(NoticeType.ATINBLOG);
		noticeParm.setAtUserIds(referers);
		noticeParm.setSendUserId(blogArticle.getUserId());
		NoticeThread noticeThread = new NoticeThread(noticeParm);
		Thread thread = new Thread(noticeThread);
		thread.start();
		return true;
	}

	@Override
	public BlogArticle queryBlogById(int id) {

		BlogArticle blog = blogArticleDao.queryBlogById(id);
		blog.setPostTime(StringUtils.friendly_time(blog.getPostTime()));
		return blog;
	}

	@Override
	public boolean update(BlogArticle blogArticle) {

		if (isCurUser(blogArticle.getUserId(), blogArticle.getId())) {
			blogArticleDao.update(blogArticle);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void updateReadCount(BlogArticle blogArticle) {

		blogArticleDao.updateReadCount(blogArticle);
	}

	@Override
	public void deleteArticle(int id) {

		blogArticleDao.delete(id);

	}

	@Override
	public List<BlogArticle> queryBlogByUserIdOrItem(String userId, int itemId, int offset, int total) {

		List<BlogArticle> list = new ArrayList<BlogArticle>();
		if (itemId == 0) {
			list = blogArticleDao.queryBlogByUserId(userId, offset, total);
		}
		else {
			list = blogArticleDao.queryBlogByItemId(itemId, offset, total);
		}
		for (BlogArticle blog : list) {
			blog.setPostTime(StringUtils.friendly_time(blog.getPostTime()));
		}
		return list;
	}

	@Override
	public int queryCountByUserIdOrItem(String userId, int itemId) {

		if (itemId == 0) {
			return blogArticleDao.queryCountByUserId(userId);
		}
		else {
			return blogArticleDao.queryCountByItemId(itemId);
		}

	}

	private boolean isCurUser(String userId, int id) {

		BlogArticle article = blogArticleDao.queryBlogById(id);
		if (article != null && (article.getUserId().equals(userId))) {
			return true;
		}
		else {
			return false;
		}
	}

	public List<BlogArticle> indexLatestBlog(int offset, int total) {

		List<BlogArticle> list = new ArrayList<BlogArticle>();
		list = blogArticleDao.indexLatestBlog(offset, total);
		for (BlogArticle blog : list) {
			blog.setPostTime(StringUtils.friendly_time(blog.getPostTime()));
		}
		return list;
	}

	public int queryCount() {

		return blogArticleDao.queryCount();
	}

}
