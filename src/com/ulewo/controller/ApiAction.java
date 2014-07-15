package com.ulewo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ulewo.enums.ResultCode;
import com.ulewo.exception.BusinessException;
import com.ulewo.model.SessionUser;
import com.ulewo.service.BlastCommentService;
import com.ulewo.service.BlastService;
import com.ulewo.service.BlogCommentService;
import com.ulewo.service.BlogService;
import com.ulewo.service.GroupMemberService;
import com.ulewo.service.GroupService;
import com.ulewo.service.Log;
import com.ulewo.service.SignInService;
import com.ulewo.service.TopicCommentService;
import com.ulewo.service.TopicService;
import com.ulewo.service.UserService;
import com.ulewo.vo.GroupVo;
import com.ulewo.vo.UserVo4Api;

@Controller
@RequestMapping("/api")
public class ApiAction extends BaseAction {
	@Autowired
	GroupService groupService;

	@Autowired
	SignInService signInService;

	@Autowired
	TopicService articleService;

	@Autowired
	BlogService blogArticleService;

	@Autowired
	BlogCommentService blogReplyService;

	@Autowired
	GroupMemberService memberService;

	@Autowired
	TopicCommentService reArticleService;

	@Autowired
	BlastService blastService;

	@Autowired
	BlastCommentService reTalkService;

	@Autowired
	UserService userService;

	@Log
	Logger log;

	/**
	 * 查询最新文章
	 * 
	 * @param session
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "/group_list")
	public Map<String, Object> groupList(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 所有群组
			List<GroupVo> groupVoList = groupService.api_findCommendGroup();
			result.put("resultCode", ResultCode.SUCCESS.getCode());
			result.put("list", groupVoList);
			return result;
		} catch (Exception e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "服务器异常!");
			return result;
		}
	}

	/**
	 * 今日签到情况
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/signin_list")
	public Map<String, Object> signinList(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 所有群组
			result = signInService.api_queryCurDaySigin(this.builderParams(
					request, false));
			result.put("mySignIn",
					signInService.api_signInInfo(this.getSessionUser(session)));
			result.put("resultCode", ResultCode.SUCCESS.getCode());
			return result;
		} catch (Exception e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "服务器异常!");
			return result;
		}
	}

	// 签到
	@ResponseBody
	@RequestMapping(value = "/signin_do.action")
	public Map<String, Object> reMark(HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			modelMap = this.signInService
					.doSignIn(this.getSessionUser(session));
			modelMap.put("resultCode", ResultCode.SUCCESS.getCode());
			return modelMap;
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			modelMap.put("resultCode", ResultCode.ERROR.getCode());
			modelMap.put("msg", e.getMessage());
			return modelMap;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("resultCode", ResultCode.ERROR.getCode());
			modelMap.put("msg", "服务器异常");
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/login")
	public Map<String, Object> login(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 所有群组
			UserVo4Api userVo = userService.api_login(this.builderParams(
					request, false));
			result.put("user", userVo);
			result.put("resultCode", ResultCode.SUCCESS.getCode());
			SessionUser sessionUser = new SessionUser();
			sessionUser.setUserId(userVo.getUserId());
			sessionUser.setUserName(userVo.getUserName());
			sessionUser.setUserIcon(userVo.getUserIcon());
			session.setAttribute("user", sessionUser);
			return result;
		} catch (BusinessException e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", e.getMessage());
			return result;
		} catch (Exception e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "服务器异常!");
			return result;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/blast_list_latest")
	public Map<String, Object> blastListLatest(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 所有群组
			result = blastService.apiQueryAllBlast(this.builderParams(request,
					false));
			result.put("resultCode", ResultCode.SUCCESS.getCode());
			return result;
		} catch (BusinessException e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", e.getMessage());
			return result;
		} catch (Exception e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "服务器异常!");
			return result;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/blast_list_mine.action")
	public Map<String, Object> blastListMine(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 所有群组
			result = blastService.apiQueryMyBlast(
					this.builderParams(request, false),
					this.getSessionUserId(session));
			result.put("resultCode", ResultCode.SUCCESS.getCode());
			return result;
		} catch (BusinessException e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", e.getMessage());
			return result;
		} catch (Exception e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "服务器异常!");
			return result;
		}
	}
}
