package com.lhl.quan.service.impl;

import java.util.List;

import com.lhl.entity.BlogArticle;
import com.lhl.entity.BlogReply;
import com.lhl.quan.dao.BlogArticleDao;
import com.lhl.quan.dao.BlogReplyDao;
import com.lhl.quan.service.BlogReplyService;

public class BlogReplyServiceImpl implements BlogReplyService
{

	private BlogReplyDao blogReplyDao;

	private BlogArticleDao blgoArticleDao;

	public void setBlogReplyDao(BlogReplyDao blogReplyDao)
	{

		this.blogReplyDao = blogReplyDao;
	}

	public void setBlgoArticleDao(BlogArticleDao blgoArticleDao)
	{

		this.blgoArticleDao = blgoArticleDao;
	}

	@Override
	public void addReply(BlogReply blogReply)
	{

		blogReplyDao.addReply(blogReply);
	}

	@Override
	public List<BlogReply> queryBlogReplyByBlogId(int blogId)
	{

		return blogReplyDao.queryReplyByBlogId(blogId);
	}

	@Override
	public boolean delete(String userId, int id)
	{

		BlogReply reply = blogReplyDao.queryBlogReplyById(id);
		//通过ID查询回复
		if (null != reply)
		{
			//通过回复获取博客ID
			BlogArticle article = blgoArticleDao.queryBlogById(reply.getBlogId());
			//如果博客的发布人和当前用户id匹配，那么就可以删除。
			if (null != article && article.getUserId().equals(userId))
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
}
