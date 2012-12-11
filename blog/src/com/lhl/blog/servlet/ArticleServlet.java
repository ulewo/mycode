package com.lhl.blog.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lhl.blog.entity.Article;
import com.lhl.blog.service.ArticleService;
import com.lhl.blog.util.Constant;
import com.lhl.blog.util.Pagination;
import com.lhl.blog.util.StringUtil;

public class ArticleServlet extends HttpServlet
{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{

		String pageStr = request.getParameter("page");
		String itemIdStr = request.getParameter("itemId");
		String time = request.getParameter("time");
		int itemId = 0;
		if (StringUtil.isNumber(itemIdStr))
		{
			itemId = Integer.parseInt(itemIdStr);
		}
		List<Article> list = new ArrayList<Article>();
		int page = 1;
		if (StringUtil.isNumber(pageStr))
		{
			page = Integer.parseInt(pageStr);
		}
		if (page <= 1)
		{
			page = 1;
		}
		int pageSize = Constant.PAGE_SIZE20;
		int noStart = (page - 1) * pageSize;
		int countTotal = 0;
		try
		{
			if (StringUtil.isEmpty(time))
			{
				countTotal = ArticleService.getInstance().queryCount(itemId);
				list = ArticleService.getInstance().queryList(itemId, noStart, pageSize);
			}
			else
			{
				countTotal = ArticleService.getInstance().queryCountByTime(time);
				list = ArticleService.getInstance().queryListByTime(time, noStart, pageSize);
			}

		}
		catch (Exception e)
		{
		}
		Pagination.setPageSize(pageSize);
		int pageTotal = Pagination.getPageTotal(countTotal);
		request.setAttribute("page", page);
		request.setAttribute("itemId", itemId);
		request.setAttribute("time", time);
		request.setAttribute("pageTotal", pageTotal);
		request.setAttribute("list", list);
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

		super.service(req, resp);
	}
}
