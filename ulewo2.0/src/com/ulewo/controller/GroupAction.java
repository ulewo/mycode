package com.ulewo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.entity.Group;
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
		int page_int = 0;
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
			mv.addObject("group", group);
			mv.addObject("articles", articleResult);
			mv.setViewName("user/userinfo");
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:/../error");
			return mv;
		}
		return mv;
	}
}
