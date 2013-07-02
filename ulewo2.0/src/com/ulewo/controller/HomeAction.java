package com.ulewo.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import weibo4j.Account;
import weibo4j.Oauth;
import weibo4j.Users;
import weibo4j.http.AccessToken;
import weibo4j.model.User;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;

import com.ulewo.entity.Article;
import com.ulewo.entity.BlogArticle;
import com.ulewo.entity.Group;
import com.ulewo.entity.Talk;
import com.ulewo.service.ArticleService;
import com.ulewo.service.BlogArticleService;
import com.ulewo.service.GroupService;
import com.ulewo.service.TalkService;
import com.ulewo.service.UserService;
import com.ulewo.util.Constant;
import com.ulewo.util.ErrorReport;
import com.ulewo.util.PaginationResult;
import com.ulewo.util.StringUtils;

@Controller
public class HomeAction {
	@Autowired
	GroupService groupService;

	@Autowired
	ArticleService articleService;

	@Autowired
	UserService userService;

	@Autowired
	BlogArticleService blogArticleService;

	@Autowired
	TalkService talkService;

	private static final String TYPE_BLOG = "blog";

	private static final String TYPE_GROUP = "group";

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login() {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("login");
		return mv;
	}

	@RequestMapping(value = "/restpwd", method = RequestMethod.GET)
	public ModelAndView restPwd() {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("rest_pwd");
		return mv;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("register");
		return mv;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpSession session) {

		ModelAndView mv = new ModelAndView();

		try {
			List<Article> list = articleService.queryLatestArticle(0, 20);
			List<Article> imgArticle = articleService.queryImageArticle(null, 0, 5);
			List<BlogArticle> blogList = blogArticleService.indexLatestBlog(0, 20);
			List<Group> groupList = (List<Group>) groupService.queryGroupsOderArticleCount(0, 10).getList();
			mv.addObject("list", list);
			mv.addObject("imgArticle", imgArticle);
			mv.addObject("blogList", blogList);
			mv.addObject("groupList", groupList);
			mv.setViewName("home");
			return mv;
		} catch (Exception e) {
			String errorMethod = "HomeAction-->index()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
	}

	@RequestMapping(value = "/square", method = RequestMethod.GET)
	public ModelAndView square(HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();

		try {
			String page = request.getParameter("page");
			int page_int = 0;
			if (StringUtils.isNumber(page)) {
				page_int = Integer.parseInt(page);
			}
			PaginationResult result = articleService.queryImageArticle2PagResult(null, page_int, Constant.pageSize30);
			List<Article> list = (List<Article>) result.getList();
			List<Article> square1 = new ArrayList<Article>();
			List<Article> square2 = new ArrayList<Article>();
			List<Article> square3 = new ArrayList<Article>();
			List<Article> square4 = new ArrayList<Article>();
			set2Square(square1, square2, square3, square4, list);
			mv.addObject("square1", square1);
			mv.addObject("square2", square2);
			mv.addObject("square3", square3);
			mv.addObject("square4", square4);
			mv.addObject("page", result.getPage());
			mv.addObject("pageTotal", result.getPageTotal());
			mv.setViewName("square");
			return mv;
		} catch (Exception e) {
			String errorMethod = "HomeAction-->square()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
	}

	private void set2Square(List<Article> square1, List<Article> square2, List<Article> square3, List<Article> square4,
			List<Article> squareList) {

		int num = 0;
		int j = 0;
		for (Article article : squareList) {
			j++;
			if (1 + num * 4 == j) {
				square1.add(article);
				continue;
			}
			if (2 + num * 4 == j) {
				square2.add(article);
				continue;
			}
			if (3 + num * 4 == j) {
				square3.add(article);
				continue;
			}
			if (4 + num * 4 == j) {
				square4.add(article);
				num++;
				continue;
			}
		}
	}

	@RequestMapping(value = "/blog", method = RequestMethod.GET)
	public ModelAndView blog(HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		try {
			String page = request.getParameter("page");
			int page_int = 0;
			if (StringUtils.isNumber(page)) {
				page_int = Integer.parseInt(page);
			}
			PaginationResult list = blogArticleService.queryLatestBlog(page_int, Constant.pageSize25);
			mv.addObject("result", list);
			mv.setViewName("blog");
			return mv;
		} catch (Exception e) {
			String errorMethod = "HomeAction-->blog()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/loadLatestTalk", method = RequestMethod.GET)
	public Map<String, Object> loadTalk(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			List<Talk> list = talkService.queryLatestTalk(0, 5);
			modelMap.put("result", "success");
			modelMap.put("list", list);
			return modelMap;
		} catch (Exception e) {
			String errorMethod = "HomeAction-->loadTalk()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/loadGroupAndMember", method = RequestMethod.GET)
	public Map<String, Object> loadGroupAndMember(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			PaginationResult groupResult = groupService.queryGroupsOderArticleCount(1, Constant.pageSize15);
			return modelMap;
		} catch (Exception e) {
			String errorMethod = "HomeAction-->loadGroupAndMember()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	@RequestMapping(value = "/talk", method = RequestMethod.GET)
	public ModelAndView talk(HttpSession session) {

		ModelAndView mv = new ModelAndView();

		try {
			mv.setViewName("talk");
			return mv;
		} catch (Exception e) {
			String errorMethod = "HomeAction-->talk()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/loadMoreTalk", method = RequestMethod.GET)
	public Map<String, Object> loadMoreTalk(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String page = request.getParameter("page");
			int page_int = 0;
			if (StringUtils.isNumber(page)) {
				page_int = Integer.parseInt(page);
			}
			PaginationResult data = talkService.queryLatestTalkByPag(page_int, Constant.pageSize30);
			modelMap.put("result", "success");
			modelMap.put("list", data);
			return modelMap;
		} catch (Exception e) {
			String errorMethod = "HomeAction-->loadMoreTalk()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public ModelAndView error() {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("error");
		return mv;
	}

	@RequestMapping(value = "/createWoWo", method = RequestMethod.GET)
	public ModelAndView createWoWo(HttpSession session) {

		ModelAndView mv = new ModelAndView();
		if (null == session.getAttribute("user")) {
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
		mv.setViewName("create_group");
		return mv;
	}

	@RequestMapping(value = "/downloadApp", method = RequestMethod.GET)
	public void downApp(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		InputStream in = null;
		BufferedInputStream bf = null;
		OutputStream toClient = null;
		try {
			// path是指欲下载的文件的路径。
			String realpath = session.getServletContext().getRealPath("/");
			ResourceBundle rb = ResourceBundle.getBundle("config.config");
			String app_name = rb.getString("app_name");
			File file = new File(realpath + "app" + "/" + app_name);
			in = new FileInputStream(file);
			bf = new BufferedInputStream(in);
			byte[] buffer = new byte[bf.available()];
			bf.read(buffer);
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(app_name.getBytes("utf-8"), "ISO-8859-1"));
			response.addHeader("Content-Length", "" + file.length());
			toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
		} catch (Exception ex) {
			// ex.printStackTrace();
		} finally {
			if (toClient != null) {
				try {
					toClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bf != null) {
				try {
					bf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@RequestMapping(value = "/app", method = RequestMethod.GET)
	public ModelAndView fileupload(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("ulewoapp");
		return mv;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		try {
			String type = request.getParameter("type");
			String keyword = request.getParameter("q");
			if (null != keyword) {
				keyword = URLDecoder.decode(keyword, "utf-8");
			}
			String page = request.getParameter("page");
			int page_int = 1;
			if (StringUtils.isNumber(page)) {
				page_int = Integer.parseInt(page);
			}
			PaginationResult result = null;
			if (TYPE_BLOG.equals(type)) {
				result = blogArticleService.searchBlog2PageResult(keyword, page_int, Constant.pageSize25, true);
			}
			else {
				result = articleService.searchTopic2PageResult(keyword, null, page_int, Constant.pageSize25, true);
			}
			mv.addObject("result", result);
			mv.addObject("keyword", keyword);
			mv.addObject("type", type);
			mv.setViewName("search");
			return mv;
		} catch (Exception e) {
			String errorMethod = "HomeAction-->search()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
	}

	@RequestMapping(value = "/open_weibo", method = RequestMethod.GET)
	public ModelAndView open_weibo(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		String code = request.getParameter("code");
		//获取token
		Oauth oauth = new Oauth();
		AccessToken accessToken = null;
		String token = "";
		try {
			accessToken = oauth.getAccessTokenByCode(code);
		} catch (WeiboException e) {
			e.printStackTrace();
		}
		if (null != accessToken) {
			token = accessToken.getAccessToken();
		}
		//获取UID
		String uid = "";
		Account am = new Account();
		am.client.setToken(token);
		try {
			JSONObject uidJson = am.getUid();
			try {
				uid = uidJson.getString("uid");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			System.out.println(uid);
		} catch (WeiboException e) {
			e.printStackTrace();
		}
		//获取用户信息
		Users um = new Users();
		um.client.setToken(token);
		try {
			User user = um.showUserById(uid);
		} catch (WeiboException e) {
			e.printStackTrace();
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("ulewoapp");
		return mv;
	}
}
