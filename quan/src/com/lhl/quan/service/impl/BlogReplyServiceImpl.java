package com.lhl.quan.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.lhl.entity.BlogArticle;
import com.lhl.entity.BlogReply;
import com.lhl.quan.dao.BlogArticleDao;
import com.lhl.quan.dao.BlogReplyDao;
import com.lhl.quan.service.BlogReplyService;

public class BlogReplyServiceImpl implements BlogReplyService {

	private BlogReplyDao blogReplyDao;

	private BlogArticleDao blogArticleDao;

	private final SimpleDateFormat formate = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public void setBlogReplyDao(BlogReplyDao blogReplyDao) {

		this.blogReplyDao = blogReplyDao;
	}

	public void setBlogArticleDao(BlogArticleDao blogArticleDao) {
		this.blogArticleDao = blogArticleDao;
	}

	@Override
	public BlogReply addReply(BlogReply blogReply) {

		blogReply.setPostTime(formate.format(new Date()));
		int id = blogReplyDao.addReply(blogReply);
		blogReply.setId(id);
		return blogReply;
	}

	@Override
	public List<BlogReply> queryBlogReplyByBlogId(int blogId) {

		return blogReplyDao.queryReplyByBlogId(blogId);
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
}
