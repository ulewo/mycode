package com.ulewo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.entity.Article;
import com.ulewo.entity.ArticleItem;
import com.ulewo.entity.Group;
import com.ulewo.enums.MemberStatus;
import com.ulewo.enums.QueryOrder;
import com.ulewo.service.ArticleItemService;
import com.ulewo.service.ArticleService;
import com.ulewo.service.GroupService;
import com.ulewo.service.MemberService;
import com.ulewo.util.Constant;
import com.ulewo.util.PaginationResult;
import com.ulewo.util.StringUtils;

@Controller
@RequestMapping("/group")
public class GroupAction {
	@Autowired
	GroupService groupService;

	@Autowired
	ArticleService articleService;

	@Autowired
	ArticleItemService articleItemService;

	@Autowired
	MemberService memberService;

	/**
	 * 所有圈子
	 * @param gid
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView allGroups(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		String page = request.getParameter("page");
		ModelAndView mv = new ModelAndView();
		int page_int = 0;
		if (StringUtils.isNumber(page)) {
			page_int = Integer.parseInt(page);
		}
		try {
			//所有群组
			PaginationResult articleResult = groupService.queryGroupsOderArticleCount(page_int, Constant.pageSize10);
			mv.addObject("result", articleResult);
			mv.setViewName("group/allgroups");
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:/../error");
			return mv;
		}
		return mv;
	}

	/**
	 * 窝窝首页
	 * @param gid
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/{gid}", method = RequestMethod.GET)
	public ModelAndView queryUserInfo(@PathVariable String gid, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		String itemId = request.getParameter("itemId");
		String page = request.getParameter("page");
		int itemId_int = 0;
		int page_int = 1;
		if (StringUtils.isNumber(itemId)) {
			itemId_int = Integer.parseInt(itemId);
		}
		if (StringUtils.isNumber(page)) {
			page_int = Integer.parseInt(page);
		}
		try {
			if (StringUtils.isEmpty(gid)) {
				mv.setViewName("redirect:" + Constant.WEBSTIE);
				return mv;
			}
			Group group = groupService.queryGorup(gid);
			if (null == group) {
				mv.setViewName("redirect:" + Constant.WEBSTIE);
				return mv;
			}
			//查询文章
			PaginationResult articleResult = articleService.queryTopicOrderByGradeAndLastReTime(gid, itemId_int,
					page_int, Constant.pageSize15);
			//查询分类
			List<ArticleItem> itemList = articleItemService.queryItemByGid(gid);
			mv.addObject("group", group);
			mv.addObject("articles", articleResult);
			mv.addObject("itemList", itemList);
			mv.addObject("itemId", itemId_int);
			mv.addObject("page", page_int);
			mv.addObject("gid", gid);
			if (itemId_int == 0) {
				mv.setViewName("group/group");
			}
			else {
				mv.setViewName("group/group_articles");
			}

		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:/../error");
			return mv;
		}
		return mv;
	}

	@RequestMapping(value = "/{gid}/img", method = RequestMethod.GET)
	public ModelAndView queryImage(@PathVariable String gid, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		String itemId = request.getParameter("itemId");
		String page = request.getParameter("page");
		int itemId_int = 0;
		int page_int = 1;
		if (StringUtils.isNumber(itemId)) {
			itemId_int = Integer.parseInt(itemId);
		}
		if (StringUtils.isNumber(page)) {
			page_int = Integer.parseInt(page);
		}
		try {
			if (StringUtils.isEmpty(gid)) {
				mv.setViewName("redirect:" + Constant.WEBSTIE);
				return mv;
			}
			Group group = groupService.queryGorup(gid);
			if (null == group) {
				mv.setViewName("redirect:" + Constant.WEBSTIE);
				return mv;
			}
			//查询文章
			PaginationResult articleResult = articleService.queryTopicOrderByGradeAndLastReTime(gid, itemId_int,
					page_int, Constant.pageSize15);
			//查询分类
			List<ArticleItem> itemList = articleItemService.queryItemByGid(gid);
			mv.addObject("group", group);
			mv.addObject("articles", articleResult);
			mv.addObject("itemList", itemList);
			mv.addObject("itemId", itemId_int);
			mv.addObject("page", page_int);
			mv.addObject("gid", gid);
			mv.setViewName("group/group_img");
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:/../error");
			return mv;
		}
		return mv;
	}

	@RequestMapping(value = "/{gid}/member", method = RequestMethod.GET)
	public ModelAndView queryMember(@PathVariable String gid, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		String page = request.getParameter("page");
		int page_int = 1;
		if (StringUtils.isNumber(page)) {
			page_int = Integer.parseInt(page);
		}
		try {
			if (StringUtils.isEmpty(gid)) {
				mv.setViewName("redirect:" + Constant.WEBSTIE);
				return mv;
			}
			Group group = groupService.queryGorup(gid);
			if (null == group) {
				mv.setViewName("redirect:" + Constant.WEBSTIE);
				return mv;
			}
			PaginationResult result = memberService.queryMembers(gid, MemberStatus.ISMEMBER, QueryOrder.ASC, page_int,
					Constant.pageSize30);
			mv.addObject("gid", gid);
			mv.addObject("group", group);
			mv.addObject("result", result);
			mv.setViewName("group/group_member");
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:/../error");
			return mv;
		}
		return mv;
	}

	@RequestMapping(value = "/{gid}/topic", method = RequestMethod.GET)
	public ModelAndView showDetail(@PathVariable String gid, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		String id = request.getParameter("id");
		int id_int = 0;
		if (StringUtils.isNumber(id)) {
			id_int = Integer.parseInt(id);
		}
		try {
			if (StringUtils.isEmpty(gid)) {
				mv.setViewName("redirect:" + Constant.WEBSTIE);
				return mv;
			}
			Group group = groupService.queryGorup(gid);
			if (null == group) {
				mv.setViewName("redirect:" + Constant.WEBSTIE);
				return mv;
			}
			Article article = articleService.showArticle(id_int);
			if (null == article || !article.getGid().equals(gid)) {
				mv.setViewName("redirect:/../error");
				return mv;
			}
			//查询分类
			List<ArticleItem> itemList = articleItemService.queryItemByGid(gid);
			mv.addObject("group", group);
			mv.addObject("gid", gid);
			mv.addObject("itemList", itemList);
			mv.addObject("article", article);
			mv.setViewName("group/show_detail");
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:/../error");
			return mv;
		}
		return mv;
	}

}
