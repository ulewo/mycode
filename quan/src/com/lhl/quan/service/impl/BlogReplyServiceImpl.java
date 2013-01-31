package com.lhl.quan.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lhl.entity.BlogArticle;
import com.lhl.entity.BlogReply;
import com.lhl.entity.Notice;
import com.lhl.quan.dao.BlogArticleDao;
import com.lhl.quan.dao.BlogReplyDao;
import com.lhl.quan.dao.NoticeDao;
import com.lhl.quan.dao.UserDao;
import com.lhl.quan.service.BlogReplyService;
import com.lhl.util.Constant;
import com.lhl.util.FormatAt;
import com.lhl.util.Tools;

public class BlogReplyServiceImpl implements BlogReplyService
{

	private BlogReplyDao blogReplyDao;

	private BlogArticleDao blogArticleDao;

	private UserDao userDao;

	private NoticeDao noticeDao;

	private final SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public void setNoticeDao(NoticeDao noticeDao)
	{

		this.noticeDao = noticeDao;
	}

	public void setUserDao(UserDao userDao)
	{

		this.userDao = userDao;
	}

	public void setBlogReplyDao(BlogReplyDao blogReplyDao)
	{

		this.blogReplyDao = blogReplyDao;
	}

	public void setBlogArticleDao(BlogArticleDao blogArticleDao)
	{

		this.blogArticleDao = blogArticleDao;
	}

	@Override
	public BlogReply addReply(BlogReply blogReply, String authorId, String articleTitle)
	{

		blogReply.setPostTime(formate.format(new Date()));
		String content = blogReply.getContent();
		String quote = blogReply.getQuote();
		List<String> referers = new ArrayList<String>();
		String formatContent = FormatAt.getInstance().GenerateRefererLinks(userDao, content, referers);
		String subCon = formatContent;
		if (quote != null && !"".equals(quote))
		{
			subCon = quote + formatContent;
		}
		blogReply.setContent(subCon);
		int id = blogReplyDao.addReply(blogReply);
		blogReply.setId(id);

		//发送@消息
		String noticeCon = blogReply.getUserName() + "在博客\"" + articleTitle + "\"中提到了你";
		String url = "blogdetail.jspx?id=" + blogReply.getBlogId() + "#re" + id;
		for (String userId : referers)
		{
			Notice notice = FormatAt.getInstance().formateNotic(userId, url, Constant.NOTICE_TYPE1, noticeCon);
			noticeDao.createNotice(notice);
		}

		//发送回复的消息提示
		if (Tools.isNotEmpty(authorId) && !authorId.equals(blogReply.getUserId()))
		{
			noticeCon = blogReply.getUserName() + "在\"" + articleTitle + "\"中回复了你";
			url = "blogdetail.jspx?id=" + blogReply.getBlogId() + "#re" + id;
			Notice notice = FormatAt.getInstance().formateNotic(authorId, url, Constant.NOTICE_TYPE1, noticeCon);
			noticeDao.createNotice(notice);
		}
		return blogReply;
	}

	@Override
	public List<BlogReply> queryBlogReplyByBlogId(int blogId)
	{

		return blogReplyDao.queryReplyByBlogId(blogId);
	}

	@Override
	public int queryBlogReplyCountByBlogId(int blogId)
	{

		return blogReplyDao.queryReplyCountByBlogId(blogId);
	}

	@Override
	public boolean delete(String curUserId, int id)
	{

		// userId，当前用户的userId
		BlogReply reply = blogReplyDao.queryBlogReplyById(id);
		// 通过ID查询回复
		if (null != reply)
		{
			// 通过回复获取博客ID
			BlogArticle article = blogArticleDao.queryBlogById(reply.getBlogId());
			// 删除 1,博主可以删除评论 article.getUserId() 博主ID
			if (null != article && article.getUserId().equals(curUserId))
			{
				blogReplyDao.delete(id);
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}

	public BlogReply queryBlogReplyById(int id)
	{

		return blogReplyDao.queryBlogReplyById(id);
	}
}
