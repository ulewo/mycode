package com.lhl.quan.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lhl.entity.BlogArticle;
import com.lhl.entity.Notice;
import com.lhl.entity.User;
import com.lhl.quan.dao.BlogArticleDao;
import com.lhl.quan.dao.NoticeDao;
import com.lhl.quan.dao.UserDao;
import com.lhl.quan.service.BlogArticleService;
import com.lhl.util.Constant;
import com.lhl.util.FormatAt;

public class BlogArticleServiceImpl implements BlogArticleService
{
	private BlogArticleDao blogArticleDao;

	private UserDao userDao;

	private NoticeDao noticeDao;

	public void setUserDao(UserDao userDao)
	{

		this.userDao = userDao;
	}

	public void setNoticeDao(NoticeDao noticeDao)
	{

		this.noticeDao = noticeDao;
	}

	public void setBlogArticleDao(BlogArticleDao blogArticleDao)
	{

		this.blogArticleDao = blogArticleDao;
	}

	private final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public boolean addBlog(BlogArticle blogArticle, User user)
	{

		blogArticle.setPostTime(format.format(new Date()));
		String content = blogArticle.getContent();
		List<String> referers = new ArrayList<String>();
		String formatContent = FormatAt.getInstance().GenerateRefererLinks(userDao, content, referers);

		blogArticle.setContent(formatContent);

		int blogId = blogArticleDao.addBlog(blogArticle);

		// 发送消息
		String noticeCon = user.getUserName() + "在博客\"" + blogArticle.getTitle() + "\"中提到了你";
		String url = "blogdetail.jspx?id=" + blogId;
		for (String userId : referers)
		{
			//自己发表的文章@自己不发消息
			if (userId != null && !userId.equals(user.getUserId()))
			{
				Notice notice = FormatAt.getInstance().formateNotic(userId, url, Constant.NOTICE_TYPE1, noticeCon);
				noticeDao.createNotice(notice);
			}
		}
		return true;
	}

	@Override
	public BlogArticle queryBlogById(int id)
	{

		return blogArticleDao.queryBlogById(id);
	}

	@Override
	public boolean update(BlogArticle blogArticle)
	{

		if (isCurUser(blogArticle.getUserId(), blogArticle.getId()))
		{
			blogArticleDao.update(blogArticle);
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public void updateReadCount(BlogArticle blogArticle)
	{

		blogArticleDao.updateReadCount(blogArticle);
	}

	@Override
	public void deleteArticle(int id)
	{

		blogArticleDao.delete(id);

	}

	@Override
	public List<BlogArticle> queryBlogByUserIdOrItem(String userId, int itemId, int offset, int total)
	{

		if (itemId == 0)
		{
			return blogArticleDao.queryBlogByUserId(userId, offset, total);
		}
		else
		{
			return blogArticleDao.queryBlogByItemId(itemId, offset, total);
		}

	}

	@Override
	public int queryCountByUserIdOrItem(String userId, int itemId)
	{

		if (itemId == 0)
		{
			return blogArticleDao.queryCountByUserId(userId);
		}
		else
		{
			return blogArticleDao.queryCountByItemId(itemId);
		}

	}

	private boolean isCurUser(String userId, int id)
	{

		BlogArticle article = blogArticleDao.queryBlogById(id);
		if (article != null && (article.getUserId().equals(userId)))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public List<BlogArticle> indexLatestBlog(int offset, int total)
	{

		return blogArticleDao.indexLatestBlog(offset, total);
	}
}
