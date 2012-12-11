package com.lhl.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lhl.entity.Article;
import com.lhl.service.ArticleService;
import com.lhl.util.MyCookie;
import com.lhl.util.StringUtil;
import com.lhl.vo.ArticleVo;

public class ShowArticleServlet extends HttpServlet
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

		Article article = null;
		ArticleVo vo = null;
		try
		{
			String idstr = request.getParameter("id");
			int id = 0;
			if (StringUtil.isNumber(idstr))
			{
				id = Integer.parseInt(idstr);
			}
			article = ArticleService.getInstance().queryArticleById(id);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		if (null != article)
		{
			vo = new ArticleVo();
			setIsOper(article, request, vo);
		}
		request.setAttribute("article", vo);
		request.getRequestDispatcher("show.jsp").forward(request, response);
	}

	private void setIsOper(Article article, HttpServletRequest request, ArticleVo vo)
	{

		String ip = request.getRemoteAddr();

		Map<String, Cookie> cookMap = MyCookie.getInstance().ReadCookieMap(request);
		int id = article.getId();
		vo.setId(id);
		vo.setImgUrl(article.getImgUrl());
		vo.setMedioType(article.getMedioType());
		vo.setVideoUrl(article.getVideoUrl());
		String content = article.getContent();
		vo.setContent(content);
		content = StringUtil.clearHtml(content);
		content = content.replace("\"", "").replace("'", "").replace("‘", "").replace("’", "").replace("“", "")
				.replace("”", "");
		vo.setCommend(content);
		vo.setDown(article.getDown());
		vo.setUp(article.getUp());
		vo.setPostTime(article.getPostTime());
		vo.setUid(article.getUid());
		vo.setUserName(article.getUserName());
		vo.setAvatar(article.getAvatar());
		vo.setTags(article.getTags());
		vo.setReCount(article.getReCount());
		String key = ip + "." + id;
		if (cookMap.get(key) != null)
		{
			vo.setHaveOper(true);
		}
		else
		{
			vo.setHaveOper(false);
		}
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

		super.service(req, resp);
	}
}
