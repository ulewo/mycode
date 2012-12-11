package com.lhl.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.lhl.entity.Article;
import com.lhl.entity.ReArticle;
import com.lhl.entity.User;
import com.lhl.service.ArticleService;
import com.lhl.service.ReArticleService;
import com.lhl.service.UserService;
import com.lhl.util.Constant;
import com.lhl.util.Pagination;
import com.lhl.util.StringUtil;

public class AdminServlet extends HttpServlet
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
		if (Constant.ADMIN_METHOD_ARTICLE.equals(method))
		{
			// 查询文章
			queryArticleList(request, response);
		}
		else if (Constant.ADMIN_METHOD_REARTICLE.equals(method))
		{
			// 回复的文章
			queryReArticleList(request, response);
		}
		else if (Constant.ADMIN_METHOD_USER.equals(method))
		{
			// 用户
			queryUser(request, response);
		}
		else if (Constant.ADMIN_METHOD_ARTICLE_DEL.equals(method))
		{
			// 删除文章
			deleteArticle(request, response);
		}
		else if (Constant.ADMIN_METHOD_ARTICLE_AUDIT.equals(method))
		{
			// 审批文章
			auditArticle(request, response);
		}

	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

		super.service(req, resp);
	}

	private void queryArticleList(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{

		String pageStr = request.getParameter("page");
		String type = request.getParameter("type");
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
		String result = "success";
		List<Article> list = new ArrayList<Article>();
		int pageTotal = 0;
		try
		{
			int count = ArticleService.getInstance().queryArticleCount(type);
			Pagination.setPageSize(pageSize);
			pageTotal = Pagination.getPageTotal(count);
			list = ArticleService.getInstance().adminQueryList(noStart, pageSize, type);
		}
		catch (Exception e)
		{
			result = "error";
		}
		JSONObject obj = new JSONObject();
		obj.put("result", result);
		obj.put("pageTotal", pageTotal);
		obj.put("list", list);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(String.valueOf(obj));
	}

	// 删除文章
	private void deleteArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{

		String idStr = request.getParameter("id");
		String result = "success";
		int id = 0;
		if (StringUtil.isNumber(idStr))
		{
			id = Integer.parseInt(idStr);
		}
		try
		{
			ArticleService.getInstance().deleteArticle(id);
		}
		catch (Exception e)
		{
			result = "error";
		}
		JSONObject obj = new JSONObject();
		obj.put("result", result);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(String.valueOf(obj));
	}

	// 审批文章
	private void auditArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{

		String idStr = request.getParameter("id");
		String status = request.getParameter("status");
		if (!Constant.STATUS_Y.equals(status) && Constant.STATUS_N.equals(status))
		{
			status = Constant.STATUS_N;
		}
		String result = "success";
		int id = 0;
		if (StringUtil.isNumber(idStr))
		{
			id = Integer.parseInt(idStr);
		}
		try
		{
			ArticleService.getInstance().auditArticle(status, id);
		}
		catch (Exception e)
		{
			result = "error";
			e.printStackTrace();
		}
		JSONObject obj = new JSONObject();
		obj.put("result", result);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(String.valueOf(obj));
	}

	// 查询回复的评论
	private void queryReArticleList(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{

		String result = "success";
		String pageStr = request.getParameter("page");
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

		List<ReArticle> list = new ArrayList<ReArticle>();
		int pageTotal = 0;
		try
		{
			int count = ReArticleService.getInstance().queryCount();
			Pagination.setPageSize(pageSize);
			pageTotal = Pagination.getPageTotal(count);
			if (page > pageTotal)
			{
				page = pageTotal;
			}
			int noStart = (page - 1) * pageSize;
			list = ReArticleService.getInstance().queryList(noStart, pageSize);
		}
		catch (Exception e)
		{
			result = "error";
		}
		JSONObject obj = new JSONObject();
		obj.put("result", result);
		obj.put("pageTotal", pageTotal);
		obj.put("list", list);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(String.valueOf(obj));
	}

	// 查询用户
	private void queryUser(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{

		String result = "success";
		List<User> list = new ArrayList<User>();
		try
		{
			list = UserService.getInstance().queryList();
		}
		catch (Exception e)
		{
			result = "error";
		}
		JSONObject obj = new JSONObject();
		obj.put("result", result);
		obj.put("list", list);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(String.valueOf(obj));
	}
}
