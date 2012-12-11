package com.lhl.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.lhl.entity.Article;
import com.lhl.service.ArticleService;
import com.lhl.util.Constant;
import com.lhl.util.MyCookie;
import com.lhl.util.Pagination;
import com.lhl.util.StringUtil;
import com.lhl.vo.ArticleVo;

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
		String type = request.getParameter("type");
		String timeRange = request.getParameter("timeRange");
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
		List<ArticleVo> list = new ArrayList<ArticleVo>();
		List<ArticleVo> picList1 = new ArrayList<ArticleVo>();
		List<ArticleVo> picList2 = new ArrayList<ArticleVo>();
		List<ArticleVo> picList3 = new ArrayList<ArticleVo>();
		List<ArticleVo> picList4 = new ArrayList<ArticleVo>();
		int pageTotal = 0;
		try
		{
			Map<String, Object> resultMap = ArticleService.getInstance().queryList(noStart, pageSize, type, timeRange);
			List<Article> articleList = (List<Article>) resultMap.get("list");
			int count = (Integer) resultMap.get("count");
			Pagination.setPageSize(pageSize);
			pageTotal = Pagination.getPageTotal(count);
			if (Constant.TYPE_PIC.equals(type))
			{
				setPicOper(articleList, request, picList1, picList2, picList3, picList4);
			}
			else
			{
				setIsOper(articleList, list, request);
			}
		}
		catch (Exception e)
		{
			result = "error";
		}
		/*
		 * JSONObject obj = new JSONObject(); obj.put("result", result);
		 * obj.put("pageTotal", pageTotal); if (Constant.TYPE_PIC.equals(type))
		 * { obj.put("picList1", picList1); obj.put("picList2", picList2);
		 * obj.put("picList3", picList3); obj.put("picList4", picList4); } else
		 * { obj.put("list", list); }
		 * response.setContentType("text/html;charset=UTF-8"); PrintWriter out =
		 * response.getWriter(); out.println(String.valueOf(obj));
		 */
		if (Constant.TYPE_PIC.equals(type))
		{
			JSONObject obj = new JSONObject();
			obj.put("result", result);
			obj.put("pageTotal", pageTotal);
			if (Constant.TYPE_PIC.equals(type))
			{
				obj.put("picList1", picList1);
				obj.put("picList2", picList2);
				obj.put("picList3", picList3);
				obj.put("picList4", picList4);
			}
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println(String.valueOf(obj));
		}
		else
		{
			request.setAttribute("page", page);
			request.setAttribute("pageTotal", pageTotal);
			request.setAttribute("reslut", result);
			request.setAttribute("list", list);
			if (Constant.TYPE_NEW.equals(type))
			{
				request.getRequestDispatcher("new.jsp").forward(request, response);
			}
			else if (Constant.TYPE_HOT.equals(type))
			{
				request.getRequestDispatcher("hot.jsp").forward(request, response);
			}
			else if (Constant.TYPE_INDEX.equals(type))
			{
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}

		}

	}

	private void setIsOper(List<Article> articleList, List<ArticleVo> list, HttpServletRequest request)
	{

		if (articleList != null)
		{
			String ip = request.getRemoteAddr();
			Map<String, Cookie> cookMap = MyCookie.getInstance().ReadCookieMap(request);
			for (Article article : articleList)
			{
				ArticleVo vo = new ArticleVo();
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
				list.add(vo);
			}
		}
	}

	private void setPicOper(List<Article> list, HttpServletRequest request, List<ArticleVo> picList1,
			List<ArticleVo> picList2, List<ArticleVo> picList3, List<ArticleVo> picList4)
	{

		String ip = request.getRemoteAddr();

		Map<String, Cookie> cookMap = MyCookie.getInstance().ReadCookieMap(request);
		int num = 0;
		int j = 0;
		for (Article article : list)
		{
			ArticleVo vo = new ArticleVo();
			int id = article.getId();
			vo.setId(id);
			vo.setImgUrl(article.getImgUrl());
			vo.setMedioType(article.getMedioType());
			vo.setVideoUrl(article.getVideoUrl());
			vo.setContent(article.getContent());
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
			j++;
			if (1 + num * 4 == j)
			{
				picList1.add(vo);
				continue;
			}
			if (2 + num * 4 == j)
			{
				picList2.add(vo);
				continue;
			}
			if (3 + num * 4 == j)
			{
				picList3.add(vo);
				continue;
			}
			if (4 + num * 4 == j)
			{
				picList4.add(vo);
				num++;
				continue;
			}
		}
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

		super.service(req, resp);
	}
}
