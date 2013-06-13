package com.ulewo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.entity.ArticleItem;
import com.ulewo.entity.Group;
import com.ulewo.entity.SessionUser;
import com.ulewo.service.ArticleItemService;
import com.ulewo.service.ArticleService;
import com.ulewo.service.GroupService;
import com.ulewo.util.Constant;
import com.ulewo.util.PaginationResult;
import com.ulewo.util.StringUtils;

@Controller
@RequestMapping("/groupManage")
public class GroupMagageAction {
	@Autowired
	GroupService groupService;

	@Autowired
	ArticleItemService articleItemService;

	@Autowired
	ArticleService articleService;

	private final static int GROUPNAEM_LENGTH = 50;

	private final static int GROUPDESC_LENGTH = 500;

	@RequestMapping(value = "/{gid}/manage", method = RequestMethod.GET)
	public ModelAndView manage(@PathVariable String gid, HttpSession session) {

		ModelAndView mv = new ModelAndView();

		try {
			SessionUser user = (SessionUser) session.getAttribute("user");
			String userId = "10001";
			Group group = groupService.queryGorup(gid);
			group.setGroupDesc(StringUtils.reFormateHtml(group.getGroupDesc()));
			mv.addObject("group", group);
			mv.addObject("gid", gid);
			mv.setViewName("/groupmanage/edit_group");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:" + Constant.WEBSTIE);
			return mv;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/{gid}/editGroup.action", method = RequestMethod.POST)
	public Map<String, Object> editGroup(@PathVariable String gid, HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			SessionUser user = (SessionUser) session.getAttribute("user");
			String userId = "10001";
			String groupName = request.getParameter("groupName");
			String groupDesc = request.getParameter("groupDesc");
			String joinPerm = request.getParameter("joinPerm");
			String postPerm = request.getParameter("postPerm");
			if (groupName == "" || groupName.length() > GROUPNAEM_LENGTH) {
				modelMap.put("result", "fail");
				modelMap.put("message", "窝窝名称为空，或者超过规定长度");
				return modelMap;
			}
			if (groupDesc == "" || groupDesc.length() > GROUPDESC_LENGTH) {
				modelMap.put("result", "fail");
				modelMap.put("message", "窝窝简介为空，或者超过规定长度");
				return modelMap;
			}
			Group group = new Group();
			group.setId(gid);
			group.setGroupName(groupName);
			group.setGroupDesc(groupDesc);
			group.setJoinPerm(joinPerm);
			group.setPostPerm(postPerm);
			groupService.updateGroup(group);
			modelMap.put("result", "success");
			modelMap.put("gid", gid);
			return modelMap;
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("result", "fail");
			modelMap.put("message", "系统异常");
			return modelMap;
		}
	}

	/**
	 * 公告管理
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{gid}/manage/group_notice", method = RequestMethod.GET)
	public ModelAndView groupNotice(@PathVariable String gid, HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		try {
			SessionUser user = (SessionUser) session.getAttribute("user");
			String userId = "10001";
			Group group = groupService.queryGorup(gid);
			group.setGroupNotice(StringUtils.reFormateHtml(group.getGroupNotice()));
			mv.addObject("group", group);
			mv.addObject("gid", gid);
			mv.setViewName("/groupmanage/group_notice");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:" + Constant.WEBSTIE);
			return mv;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/{gid}/editNotice.action", method = RequestMethod.POST)
	public Map<String, Object> editNotice(@PathVariable String gid, HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			SessionUser user = (SessionUser) session.getAttribute("user");
			String userId = "10001";
			String groupNotice = request.getParameter("groupNotice");
			Group group = new Group();
			group.setId(gid);
			group.setGroupNotice(groupNotice);
			groupService.updateGroup(group);
			modelMap.put("result", "success");
			modelMap.put("gid", gid);
			return modelMap;
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("result", "fail");
			modelMap.put("message", "系统异常");
			return modelMap;
		}
	}

	/**
	 * 分类管理
	 * 
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
			mv.addObject("gid", gid);
			mv.setViewName("groupmanage/group_item");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:" + Constant.WEBSTIE);
			return mv;
		}
	}

	/**
	 * 文章管理
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{gid}/manage/group_article", method = RequestMethod.GET)
	public ModelAndView groupArticle(@PathVariable String gid, HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		try {
			String itemId = request.getParameter("itemId");
			int itemId_int = 0;
			if (StringUtils.isNumber(itemId)) {
				itemId_int = Integer.parseInt(itemId);
			}
			String page = request.getParameter("page");
			int page_int = 0;
			if (StringUtils.isNumber(page)) {
				page_int = Integer.parseInt(page);
			}
			SessionUser sessionUser = (SessionUser) session.getAttribute("user");
			String userId = "10001";
			List<ArticleItem> itemList = articleItemService.queryItemAndTopicCountByGid(gid);
			PaginationResult result = articleService.queryTopicOrderByGradeAndLastReTime(gid, itemId_int, page_int,
					Constant.pageSize20);
			mv.addObject("itemList", itemList);
			mv.addObject("itemId", itemId_int);
			mv.addObject("result", result);
			mv.addObject("gid", gid);
			mv.setViewName("groupmanage/group_article");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:" + Constant.WEBSTIE);
			return mv;
		}
	}

	@RequestMapping(value = "/{gid}/manage/manageArticle", method = RequestMethod.POST)
	public ModelAndView manageArticle(@PathVariable String gid, HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		try {
			String itemId = request.getParameter("itemId");
			int itemId_int = 0;
			if (StringUtils.isNumber(itemId)) {
				itemId_int = Integer.parseInt(itemId);
			}
			String page = request.getParameter("page");
			int page_int = 0;
			if (StringUtils.isNumber(page)) {
				page_int = Integer.parseInt(page);
			}
			String type = request.getParameter("type");
			String[] id = request.getParameterValues("id");
			SessionUser sessionUser = (SessionUser) session.getAttribute("user");
			String userId = "10001";
			articleService.manangeArticle(userId, gid, id, type);
			mv.addObject("itemId", itemId_int);
			mv.addObject("gid", gid);
			mv.setViewName("groupmanage/group_article");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:" + Constant.WEBSTIE);
			return mv;
		}
	}
}
