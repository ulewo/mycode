package com.lhl.blog.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lhl.blog.entity.Article;
import com.lhl.blog.service.ArticleService;
import com.lhl.blog.util.Constant;

public class AboutServlet extends HttpServlet
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

		String id = Constant.ABOUTADMIN_ID;
		Article article = ArticleService.getInstance().getArticle(id);
		request.setAttribute("article", article);
		request.getRequestDispatcher("aboutme.jsp").forward(request, response);
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

		super.service(req, resp);
	}
}
