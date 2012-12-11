package com.lhl.blog.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lhl.blog.entity.Article;
import com.lhl.blog.service.ArticleService;

public class ArticleDetailServlet extends HttpServlet
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

		String id = request.getParameter("id");
		String comment = request.getParameter("comment");
		Article article = ArticleService.getInstance().getArticle(id);
		article.setReadCount(article.getReadCount() + 1);
		ArticleService.getInstance().updateArticleReadCount(article);
		request.setAttribute("article", article);
		request.setAttribute("comment", comment);
		request.getRequestDispatcher("detail.jsp").forward(request, response);
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

		super.service(req, resp);
	}
}
