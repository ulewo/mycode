package com.lhl.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lhl.entity.Article;
import com.lhl.service.ArticleService;
import com.lhl.util.Constant;
import com.lhl.util.MyCookie;
import com.lhl.util.Pagination;
import com.lhl.util.StringUtil;
import com.lhl.vo.ArticleVo;

public class SearchServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String pageStr = request.getParameter("page");
		String searchKey = request.getParameter("searchKey");
		if (StringUtil.isNotEmpty(searchKey)) {
			searchKey = URLDecoder.decode(searchKey, "UTF-8");
		}
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
			List<Article> articleList = ArticleService.getInstance()
					.searchArticle(noStart, pageSize, searchKey);
			int count = ArticleService.getInstance().searchArticleCount(
					searchKey);
			Pagination.setPageSize(pageSize);
			pageTotal = Pagination.getPageTotal(count);
			setIsOper(articleList, list, request, searchKey);
		} catch (Exception e) {
			result = "error";
		}
		request.setAttribute("page", page);
		request.setAttribute("searchKey", searchKey);
		request.setAttribute("pageTotal", pageTotal);
		request.setAttribute("reslut", result);
		request.setAttribute("list", list);
		request.getRequestDispatcher("search.jsp").forward(request, response);
	}

	private void setIsOper(List<Article> articleList, List<ArticleVo> list,
			HttpServletRequest request, String searchKey) {

		if (articleList != null) {
			String ip = request.getRemoteAddr();
			Map<String, Cookie> cookMap = MyCookie.getInstance().ReadCookieMap(
					request);
			for (Article article : articleList) {
				ArticleVo vo = new ArticleVo();
				int id = article.getId();
				vo.setId(id);
				vo.setImgUrl(article.getImgUrl());
				vo.setMedioType(article.getMedioType());
				vo.setVideoUrl(article.getVideoUrl());
				String content = article.getContent();
				content = content.replace(searchKey,
						"<span  class='search_key'>" + searchKey + "</span>");
				vo.setContent(content);
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
