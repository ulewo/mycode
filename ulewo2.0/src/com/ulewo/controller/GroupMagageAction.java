package com.ulewo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.entity.ArticleItem;
import com.ulewo.entity.SessionUser;
import com.ulewo.service.ArticleItemService;
import com.ulewo.service.GroupService;
import com.ulewo.util.Constant;

@Controller
@RequestMapping("/groupManage")
public class GroupMagageAction {
	@Autowired
	GroupService groupService;

	@Autowired
	ArticleItemService articleItemService;

	@RequestMapping(value = "/{gid}/manage", method = RequestMethod.GET)
	public ModelAndView queryUserInfo(@PathVariable String gid, HttpSession session) {

		SessionUser user = (SessionUser) session.getAttribute("user");
		String userId = "10001";
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/groupmanage/edit_group");
		try {

		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:" + Constant.WEBSTIE);
			return mv;
		}
		return mv;
	}

	/**
	 * 分类管理
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{gid}/manage/group_item", method = RequestMethod.GET)
	public ModelAndView blogItem(@PathVariable String gid, HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		try {
			SessionUser sessionUser = (SessionUser) session.getAttribute("user");
			String userId = "10001";
			List<ArticleItem> itemList = articleItemService.queryItemAndTopicCountByGid(gid);
			mv.addObject("imtes", itemList);
			mv.setViewName("groupmanage/group_item");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:" + Constant.WEBSTIE);
			return mv;
		}
	}
}
