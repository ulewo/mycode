package com.lhl.quan.action;

import java.util.ArrayList;
import java.util.List;

import com.lhl.common.action.BaseAction;
import com.lhl.entity.BlogArticle;
import com.lhl.entity.User;
import com.lhl.quan.service.BlogArticleService;
import com.lhl.util.Constant;
import com.lhl.util.Pagination;

public class BlogArticleAction extends BaseAction
{
	private static final long serialVersionUID = 1L;

	private BlogArticleService blogArticleService;

	private List<BlogArticle> blogList = new ArrayList<BlogArticle>();

	private int pageTotal;

	private int page;

	private String userId;

	private String title;

	private String content;

	private int itemId;

	private String keyWord;

	private int allowReplay;

	public String blog()
	{

		try
		{
			int countNumber = blogArticleService.queryCountByUserId(userId);
			Pagination.setPageSize(Constant.pageSize50);
			int pageSize = Pagination.getPageSize();
			pageTotal = Pagination.getPageTotal(countNumber);
			if (page > pageTotal)
			{
				page = pageTotal;
			}
			if (page < 1)
			{
				page = 1;
			}
			int noStart = (page - 1) * pageSize;
			blogList = blogArticleService.queryBlogByUserId(userId, noStart, pageSize);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String addBlog()
	{

		try
		{
			User sessionUser = getSessionUser();
			if (sessionUser != null)
			{
				BlogArticle blogArticle = new BlogArticle();
				blogArticle.setUserId(sessionUser.getUserId());
				blogArticle.setItemId(itemId);
				blogArticle.setTitle(title);
				blogArticle.setContent(content);
				blogArticle.setKeyWord(keyWord);
				blogArticleService.addBlog(blogArticle);
			}
		}
		catch (Exception e)
		{
			return ERROR;
		}
		return SUCCESS;
	}

	public void setBlogArticleService(BlogArticleService blogArticleService)
	{

		this.blogArticleService = blogArticleService;
	}

	public int getPage()
	{

		return page;
	}

	public void setPage(int page)
	{

		this.page = page;
	}

	public List<BlogArticle> getBlogList()
	{

		return blogList;
	}

	public int getPageTotal()
	{

		return pageTotal;
	}

	public String getUserId()
	{

		return userId;
	}

	public void setUserId(String userId)
	{

		this.userId = userId;
	}

}
