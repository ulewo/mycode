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

public class UserArticleServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String pageStr = request.getParameter("page");
		String type = request.getParameter("type");
		String uid = request.getParameter("uid");
		int page = 1;
		if (StringUtil.isNumber(pageStr)) {
			page = Integer.parseInt(pageStr);
		}
		if (page <= 1) {
			page = 1;
		}
		int pageSize = Constant.PAGE_SIZE20;
		int noStart = (page - 1) * pageSize;
		String result = "success";
		List<ArticleVo> list = new ArrayList<ArticleVo>();
		int pageTotal = 0;
		try {
			Map<String, Object> map = ArticleService.getInstance()
					.queryUserArticleList(noStart, pageSize, type, uid);
			List<Article> articleList = (List<Article>) map.get("list");
			setIsOper(articleList, list, request);
			int count = (Integer) map.get("count");
			Pagination.setPageSize(pageSize);
			pageTotal = Pagination.getPageTotal(count);
		} catch (Exception e) {
			result = "error";
		}
		JSONObject obj = new JSONObject();
		obj.put("result", result);
		obj.put("list", list);
		obj.put("pageTotal", pageTotal);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(String.valueOf(obj));
	}

	private void setIsOper(List<Article> articleList, List<ArticleVo> list,
			HttpServletRequest request) {

		if (articleList != null) {
			String ip = request.getRemoteAddr();
			Map<String, Cookie> cookMap = MyCookie.getInstance().ReadCookieMap(
					request);
			for (Article article : articleList) {
				ArticleVo vo = new ArticleVo();
				int id = article.getId();
				vo.setId(id);
				vo.setImgUrl(article.getImgUrl());
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
				if (cookMap.get(key) != null) {
					vo.setHaveOper(true);
				} else {
					vo.setHaveOper(false);
				}
				list.add(vo);
			}
		}
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		super.service(req, resp);
	}
}
