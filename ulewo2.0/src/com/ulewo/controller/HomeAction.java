package com.ulewo.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
	public ModelAndView manage(HttpSession session) {

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
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
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
}
