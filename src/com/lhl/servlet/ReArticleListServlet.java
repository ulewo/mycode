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

import com.lhl.entity.ReArticle;
import com.lhl.service.ReArticleService;
import com.lhl.util.StringUtil;

public class ReArticleListServlet extends HttpServlet
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

		String result = "success";
		String articleId = request.getParameter("articleid");
		List<ReArticle> list = new ArrayList<ReArticle>();
		if (StringUtil.isNumber(articleId))
		{
			try
			{
				list = ReArticleService.getInstance().queryListByArticleId(Integer.parseInt(articleId));
			}
			catch (Exception e)
			{
				result = "error";
			}
		}
		JSONObject obj = new JSONObject();
		obj.put("result", result);
		obj.put("list", list);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(String.valueOf(obj));
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

		super.service(req, resp);
	}
}
