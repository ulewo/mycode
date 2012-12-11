package com.lhl.blog.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.lhl.blog.entity.ReArticle;
import com.lhl.blog.service.ReArticleService;
import com.lhl.blog.util.Constant;
import com.lhl.blog.util.StringUtil;

public class ReArticleServlet extends HttpServlet
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
		if ("add".equals(method))
		{
			addReArticle(request, response);
		}
		else
		{
			reArticleList(request, response);
		}

	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

		super.service(req, resp);
	}

	private void addReArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{

		String articleId = request.getParameter("articleId");
		String userName = request.getParameter("userName");
		String content = request.getParameter("content");
		String quote = request.getParameter("quote");
		ReArticle reArticle = new ReArticle();
		reArticle.setArticleId(articleId);
		String sessionName = String.valueOf(request.getSession().getAttribute("name"));

		if (Constant.SESSIONID.equals(sessionName))
		{
			reArticle.setUserName(Constant.ADMIN_USER_NAME);
			reArticle.setType("A");
		}
		else
		{
			reArticle.setUserName(userName);
			reArticle.setType("B");
		}
		reArticle.setContent(quote + StringUtil.formateHtml(content));
		reArticle = ReArticleService.getInstance().addReArticle(reArticle);
		JSONObject obj = new JSONObject();
		obj.put("reArticle", reArticle);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(String.valueOf(obj));
	}

	private void reArticleList(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{

		String articleId = request.getParameter("articleId");
		List<ReArticle> list = ReArticleService.getInstance().queryReArticlesByArticleId(articleId);

		JSONObject obj = new JSONObject();
		obj.put("list", list);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(String.valueOf(obj));
	}
}
