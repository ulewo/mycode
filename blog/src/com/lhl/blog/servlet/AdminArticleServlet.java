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

import com.lhl.blog.entity.Article;
import com.lhl.blog.service.ArticleService;
import com.lhl.blog.util.Constant;
import com.lhl.blog.util.Pagination;
import com.lhl.blog.util.StringUtil;

public class AdminArticleServlet extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5418150958875883756L;

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
			queryList(request, response);
		}
		else if (Constant.ADMIN_METHOD_SAVE.equals(method))
		{
			saveArticle(request, response);
		}
		else if (Constant.ADMIN_METHOD_EDITE.equals(method))
		{
			getEditInfo(request, response);
		}
		else if (Constant.ADMIN_METHOD_UPDATE.equals(method))
		{
			updateArticle(request, response);
		}
		else if (Constant.ADMIN_METHOD_DELETE.equals(method))
		{
			deleteArticle(request, response);
		}
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

		super.service(req, resp);
	}

	private void queryList(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{

		String pageStr = request.getParameter("page");
		String itemIdStr = request.getParameter("itemId");
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
		int pageTotal = 0;
		try
		{
			if (page <= 1)
			{
				page = 1;
			}
			int pageSize = Constant.PAGE_SIZE20;
			int countTotal = ArticleService.getInstance().queryCount(itemId);
			Pagination.setPageSize(pageSize);
			pageTotal = Pagination.getPageTotal(countTotal);
			if (page > pageTotal)
			{
				page = pageTotal;
			}

			int noStart = (page - 1) * pageSize;
			list = ArticleService.getInstance().queryList(itemId, noStart, pageSize);
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

	//新增
	private void saveArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{

		int itemId = 0;
		String itemIdStr = request.getParameter("itemId");
		if (StringUtil.isNumber(itemIdStr))
		{
			itemId = Integer.parseInt(itemIdStr);
		}
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String tag = request.getParameter("tag");
		Article article = new Article();
		article.setId(id);
		article.setItemId(itemId);
		article.setTitle(title);
		article.setContent(content);
		article.setTags(tag);
		ArticleService.getInstance().saveArticle(article);
		response.sendRedirect("admin_index.jsp");
	}

	//删除
	private void deleteArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{

		String result = "success";
		String id = request.getParameter("id");
		ArticleService.getInstance().deleteArticle(id);
		JSONObject obj = new JSONObject();
		obj.put("result", result);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(String.valueOf(obj));
	}

	//更新获取信息
	private void getEditInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{

		String id = request.getParameter("id");
		Article article = ArticleService.getInstance().getArticle(id);
		JSONObject obj = new JSONObject();
		obj.put("article", article);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(String.valueOf(obj));
	}

	//更新信息
	private void updateArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{

		int itemId = 0;
		String itemIdStr = request.getParameter("itemId");
		if (StringUtil.isNumber(itemIdStr))
		{
			itemId = Integer.parseInt(itemIdStr);
		}
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String tag = request.getParameter("tag");
		Article article = new Article();
		article.setItemId(itemId);
		article.setTitle(title);
		article.setContent(content);
		article.setTags(tag);
		ArticleService.getInstance().updateArticle(article);
		request.getRequestDispatcher("admin_article.jsp").forward(request, response);
	}
}
