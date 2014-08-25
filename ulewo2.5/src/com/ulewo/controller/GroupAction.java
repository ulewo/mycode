package com.ulewo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.enums.MemberType;
import com.ulewo.enums.ResultCode;
import com.ulewo.exception.BusinessException;
import com.ulewo.model.Attachment;
import com.ulewo.model.Group;
import com.ulewo.model.GroupCategory;
import com.ulewo.model.GroupMember;
import com.ulewo.model.Topic;
import com.ulewo.model.TopicCategory;
import com.ulewo.service.AttachmentDownloadService;
import com.ulewo.service.AttachmentService;
import com.ulewo.service.GroupCategoryService;
import com.ulewo.service.GroupMemberService;
import com.ulewo.service.GroupService;
import com.ulewo.service.TopicCategoryService;
import com.ulewo.service.TopicCommentService;
import com.ulewo.service.TopicService;
import com.ulewo.service.UserService;
import com.ulewo.util.Constant;
import com.ulewo.util.UlewoPaginationResult;

@Controller
@RequestMapping("/group")
public class GroupAction extends BaseGroupAction {
	@Autowired
	GroupService groupService;

	@Autowired
	TopicService topicService;

	@Autowired
	GroupMemberService groupMemberService;

	@Autowired
	TopicCommentService topicCmmentService;

	@Autowired
	TopicCategoryService topicCategoryService;

	@Autowired
	AttachmentService attachmentService;

	@Autowired
	UserService userService;

	@Autowired
	AttachmentDownloadService attachedDownloadService;

	@Autowired
	GroupCategoryService groupCategoryService;

	private Logger log = LoggerFactory.getLogger(GroupAction.class);

	/**
	 * 所有圈子
	 * 
	 * @param gid
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "all", method = RequestMethod.GET)
	public ModelAndView allGroups(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		try {
			// 所有群组
			Map<String, String> map = this.builderParams(request, false);
			map.put("orderBy", "topicCount desc");
			UlewoPaginationResult<Group> groupResult = groupService.findAllGroup(map);
			List<GroupCategory> groupCateGroy = groupCategoryService.selectGroupCategoryList4Index();
			mv.addObject("result", groupResult);
			mv.addObject("categroyId", map.get("categroyId"));
			mv.addObject("pCategroyId", map.get("pCategroyId"));
			mv.addObject("groupCateGroy", groupCateGroy);
			mv.setViewName("group/allgroups");
		} catch (Exception e) {
			log.error(e.getMessage());
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
		return mv;
	}

	/**
	 * 窝窝首页
	 * 
	 * @param gid
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/{gid}", method = RequestMethod.GET)
	public ModelAndView groupIndex(@PathVariable String gid, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		try {
			Map<String, String> map = this.builderParams(request, true);
			map.put("gid", gid);
			Group group = checkGroup(map, session);
			// 查询文章
			UlewoPaginationResult<Topic> topicResult = topicService.findTopics(map);
			// 查询分类
			List<TopicCategory> categoryList = topicCategoryService.queryCategoryAndTopicCount(map);
			// mv.addObject("memberStatus", memberStatus(session, gid));
			mv.addObject("group", group);
			mv.addObject("topicResult", topicResult);
			mv.addObject("categoryList", categoryList);
			mv.addObject("gid", gid);
			mv.setViewName("group/group");
		} catch (Exception e) {
			log.error(e.getMessage());
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
		return mv;
	}

	/**
	 * 群成员，活跃成员
	 * 
	 * @param gid
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/{gid}/loadMembers", method = RequestMethod.GET)
	public Map<String, Object> loadMembers(@PathVariable String gid, HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			map.put("gid", gid);
			List<GroupMember> activerList = groupMemberService.findMembersActiveIndex(map);
			List<GroupMember> memberList = groupMemberService.findAllMembersList(map);
			modelMap.put("activerList", activerList);
			modelMap.put("memberList", memberList);
			return modelMap;
		} catch (Exception e) {
			log.error(e.getMessage());
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	/**
	 * 群图片
	 * 
	 * @param gid
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/{gid}/img", method = RequestMethod.GET)
	public ModelAndView queryImage(@PathVariable String gid, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		try {
			Map<String, String> map = this.builderParams(request, true);
			map.put("gid", gid);
			Group group = checkGroup(map, session);
			map.put("userId", null);
			map.put("haveimg", "Y");
			UlewoPaginationResult<Topic> result = topicService.findTopics(map);
			List<Topic> list = (List<Topic>) result.getList();
			List<Topic> square1 = new ArrayList<Topic>();
			List<Topic> square2 = new ArrayList<Topic>();
			List<Topic> square3 = new ArrayList<Topic>();
			List<Topic> square4 = new ArrayList<Topic>();
			set2Square(square1, square2, square3, square4, list);
			mv.addObject("square1", square1);
			mv.addObject("square2", square2);
			mv.addObject("square3", square3);
			mv.addObject("square4", square4);
			mv.addObject("page", result.getPage());
			mv.setViewName("square");
			mv.addObject("group", group);
			mv.addObject("gid", gid);
			mv.addObject("page", result.getPage().getPage());
			mv.addObject("pageTotal", result.getPage().getPageTotal());
			mv.setViewName("group/group_img");
		} catch (Exception e) {
			log.error(e.getMessage());
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
		return mv;
	}

	/**
	 * 群附件
	 * 
	 * @param gid
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/{gid}/attachedFile", method = RequestMethod.GET)
	public ModelAndView queryAttachFile(@PathVariable String gid, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("gid", gid);
			Group group = this.checkGroup(map, session);
			map.put("userId", null);
			UlewoPaginationResult<Attachment> list = attachmentService.attachedTopic(map);
			mv.addObject("group", group);
			mv.addObject("result", list);
			mv.setViewName("group/group_attache");
		} catch (Exception e) {
			log.error(e.getMessage());
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
		return mv;
	}

	/**
	 * 群成员
	 * 
	 * @param gid
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/{gid}/member", method = RequestMethod.GET)
	public ModelAndView queryMember(@PathVariable String gid, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		try {
			Map<String, String> map = this.builderParams(request, true);
			map.put("gid", gid);
			Group group = this.checkGroup(map, session);
			map.put("userId", null);
			map.put("memberType", MemberType.ISMEMBER.getValue());
			UlewoPaginationResult<GroupMember> result = groupMemberService.findMembers(map);
			mv.addObject("group", group);
			mv.addObject("result", result);
			mv.setViewName("group/group_member");
		} catch (Exception e) {
			log.error(e.getMessage());
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
		return mv;
	}

	private void set2Square(List<Topic> square1, List<Topic> square2, List<Topic> square3, List<Topic> square4,
			List<Topic> squareList) {

		int num = 0;
		int j = 0;
		for (Topic article : squareList) {
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

	/*****
	 * 加入圈子
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/joinGroup.action", method = RequestMethod.GET)
	public Map<String, String> joinGroup(HttpSession session, HttpServletRequest request) {

		Map<String, String> modelMap = new HashMap<String, String>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			GroupMember member = groupMemberService.joinGroup(map, this.getSessionUserId(session));
			modelMap.put("result", ResultCode.SUCCESS.getCode());
			modelMap.put("memberType", member.getMemberType());
			return modelMap;
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			modelMap.put("result", ResultCode.ERROR.getCode());
			modelMap.put("msg", e.getMessage());
			return modelMap;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("result", ResultCode.ERROR.getCode());
			modelMap.put("msg", "系统异常!");
			return modelMap;
		}
	}

	/**
	 * 退出窝窝
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/existGroup.action", method = RequestMethod.GET)
	public Map<String, String> existGroup(HttpSession session, HttpServletRequest request) {

		Map<String, String> modelMap = new HashMap<String, String>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			groupMemberService.existGroup(map, this.getSessionUserId(session));
			modelMap.put("result", ResultCode.SUCCESS.getCode());
			return modelMap;
		} catch (BusinessException e) {
			log.error(e.getMessage());
			modelMap.put("result", ResultCode.ERROR.getCode());
			modelMap.put("msg", e.getMessage());
			return modelMap;
		} catch (Exception e) {
			log.error(e.getMessage());
			modelMap.put("result", ResultCode.ERROR.getCode());
			modelMap.put("msg", "系统异常!");
			return modelMap;
		}
	}
}
