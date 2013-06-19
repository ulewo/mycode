package com.ulewo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.entity.Article;
import com.ulewo.entity.BlogArticle;
import com.ulewo.entity.Talk;
import com.ulewo.service.ArticleService;
import com.ulewo.service.BlogArticleService;
import com.ulewo.service.GroupService;
import com.ulewo.service.MemberService;
import com.ulewo.service.TalkService;
import com.ulewo.util.Constant;
import com.ulewo.util.PaginationResult;
import com.ulewo.util.StringUtils;

@Controller
public class IndexAction {
	@Autowired
	GroupService groupService;

	@Autowired
	ArticleService articleService;

	@Autowired
	MemberService memberService;

	@Autowired
	BlogArticleService blogArticleService;

	@Autowired
	TalkService talkService;

	private final static int GROUPNAEM_LENGTH = 50;

	private final static int GROUPDESC_LENGTH = 500;

	private final static int TITLE_LENGTH = 150, KEYWORD_LENGTH = 150;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView manage(HttpSession session) {

		ModelAndView mv = new ModelAndView();

		try {
			List<Article> list = articleService.queryLatestArticle(0, 20);
			List<Article> imgArticle = articleService.queryImageArticle(0, 5);
			List<BlogArticle> blogList = blogArticleService.indexLatestBlog(0, 20);
			mv.addObject("list", list);
			mv.addObject("imgArticle", imgArticle);
			mv.addObject("blogList", blogList);
			mv.setViewName("home");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:" + Constant.WEBSTIE);
			return mv;
		}
	}

	@RequestMapping(value = "/square", method = RequestMethod.GET)
	public ModelAndView square(HttpSession session) {

		ModelAndView mv = new ModelAndView();

		try {
			List<Article> list = articleService.queryLatestArticle(0, 50);
			mv.addObject("list", list);
			mv.setViewName("square");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:" + Constant.WEBSTIE);
			return mv;
		}
	}

	@RequestMapping(value = "/blog", method = RequestMethod.GET)
	public ModelAndView blog(HttpSession session) {

		ModelAndView mv = new ModelAndView();

		try {
			//List<BlogArticle> list = blogArticleService.queryCount();
			//	mv.addObject("list", list);
			mv.setViewName("square");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:" + Constant.WEBSTIE);
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

	@RequestMapping(value = "/talk", method = RequestMethod.GET)
	public ModelAndView talk(HttpSession session) {

		ModelAndView mv = new ModelAndView();

		try {
			//List<BlogArticle> list = blogArticleService.queryCount();
			//	mv.addObject("list", list);
			mv.setViewName("talk");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:" + Constant.WEBSTIE);
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

}
