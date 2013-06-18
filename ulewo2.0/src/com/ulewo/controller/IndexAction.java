package com.ulewo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.entity.Article;
import com.ulewo.entity.BlogArticle;
import com.ulewo.service.ArticleService;
import com.ulewo.service.BlogArticleService;
import com.ulewo.service.GroupService;
import com.ulewo.service.MemberService;
import com.ulewo.util.Constant;

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

	private final static int GROUPNAEM_LENGTH = 50;

	private final static int GROUPDESC_LENGTH = 500;

	private final static int TITLE_LENGTH = 150, KEYWORD_LENGTH = 150;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView manage(HttpSession session) {

		ModelAndView mv = new ModelAndView();

		try {
			List<Article> list = articleService.queryLatestArticle(0, 20);
			List<Article> imgArticle = articleService.queryImageArticle(0,5);
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
}
