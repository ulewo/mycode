package com.lhl.blog.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.lhl.blog.entity.ReArticle;
import com.lhl.blog.service.ReArticleService;
import com.lhl.blog.util.Constant;
import com.lhl.blog.util.Pagination;
import com.lhl.blog.util.StringUtil;

public class AdminReArticleServlet extends HttpServlet
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

		String method = request.getParameter("method");
		if (Constant.ADMIN_METHOD_LIST.equals(method))
		{
			reArticleList(request, response);
		}
		else if (Constant.ADMIN_METHOD_DELETE.equals(method))
		{
			deleteReArticle(request, response);
		}
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

		super.service(req, resp);
	}

	private void deleteReArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{

		String result = "success";
		String idstr = request.getParameter("id");
		int id = 0;
		if (StringUtil.isNumber(idstr))
		{
			id = Integer.parseInt(idstr);
		}
		ReArticleService.getInstance().deleteReArticle(id);
		JSONObject obj = new JSONObject();
		obj.put("result", result);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(String.valueOf(obj));
	}

	private void reArticleList(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{

		String pageStr = request.getParameter("page");
		List<ReArticle> list = new ArrayList<ReArticle>();
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
		int pageTotal = 0;
		try
		{
			int count = ReArticleService.getInstance().queryCount();
			if (page <= 1)
			{
				page = 1;
			}
			Pagination.setPageSize(pageSize);
			pageTotal = Pagination.getPageTotal(count);
			if (page > pageTotal)
			{
				page = pageTotal;
			}
			int noStart = (page - 1) * pageSize;
			list = ReArticleService.getInstance().queryReArticles(noStart, pageSize);
		}
		catch (Exception e)
		{
		}
		JSONObject obj = new JSONObject();
		obj.put("list", list);
		obj.put("pageTotal", pageTotal);
		obj.put("page", page);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(String.valueOf(obj));
	}
}
