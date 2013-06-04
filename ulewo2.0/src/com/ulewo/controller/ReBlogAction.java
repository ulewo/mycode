package com.ulewo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ulewo.entity.BlogReply;
import com.ulewo.entity.SessionUser;
import com.ulewo.entity.User;
import com.ulewo.service.BlogReplyService;
import com.ulewo.service.UserService;
import com.ulewo.util.Constant;
import com.ulewo.util.PaginationResult;
import com.ulewo.util.StringUtils;

@Controller
@RequestMapping("/reblog")
public class ReBlogAction {
	@Autowired
	private UserService userService;
	@Autowired
	private BlogReplyService blogReplyService;

	private static final int MAXLENGTH = 250;

	/**
	 * 回复列表
	 * @param userName
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/replay.do", method = RequestMethod.GET)
	public Map<String, Object> checkUserName(HttpSession session, HttpServletRequest request) {

		String blogId = request.getParameter("blogId");
		String page = request.getParameter("page");
		int page_int = 0;
		int blogId_int = 0;
		if (StringUtils.isNumber(blogId)) {
			blogId_int = Integer.parseInt(blogId);
		}

		if (StringUtils.isNumber(page)) {
			page_int = Integer.parseInt(page);
		}
		PaginationResult result = blogReplyService.queryBlogReplyByBlogId(blogId_int, page_int, Constant.pageSize25);
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("result", result);
		return modelMap;
	}

	/**
	 * 保存回复
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveReplay.do", method = RequestMethod.POST)
	public Map<String, Object> saveReplay(HttpSession session, HttpServletRequest request) {

		String content = request.getParameter("content");
		String atUserId = request.getParameter("atUserId");
		String atUserName = request.getParameter("atUserName");
		String blogId = request.getParameter("blogId");
		String message = "";
		String result = "success";
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			// 检测用户名
			Object sessionObj = session.getAttribute("user");
			if (sessionObj == null) {
				result = "fail";
				message = "请先登录再发帖";
				modelMap.put("message", message);
				modelMap.put("result", result);
				return modelMap;
			}
			if (StringUtils.isEmpty(content) || content.length() > MAXLENGTH) {
				result = "fail";
				message = "输入内容为空或者超过长度";
				modelMap.put("message", message);
				modelMap.put("result", result);
				return modelMap;
			}
			if (!StringUtils.isNumber(blogId)) {
				result = "fail";
				message = "操作错误";
				modelMap.put("message", message);
				modelMap.put("result", result);
				return modelMap;
			}
			BlogReply reply = new BlogReply();
			User sessionUser = (User) sessionObj;
			reply.setUserId(sessionUser.getUserId());
			reply.setUserName(sessionUser.getUserName());
			reply.setReUserIcon(sessionUser.getUserLittleIcon());
			reply.setAtUserId(atUserId);
			reply.setAtUserName(atUserName);
			reply.setContent(content);
			reply.setBlogId(Integer.parseInt(blogId));
			BlogReply blogReply = blogReplyService.addReply(reply);
			reply.setId(blogReply.getId());
			reply.setPostTime(blogReply.getPostTime());
			modelMap.put("result", result);
			return modelMap;
		} catch (Exception e) {
			message = "操作异常";
			result = "fail";
			modelMap.put("message", message);
			modelMap.put("result", result);
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/deleteReplay.do", method = RequestMethod.GET)
	public Map<String, Object> deleteReply(HttpSession session, HttpServletRequest request) {

		String replyId = request.getParameter("replyId");
		Object sessionObj = session.getAttribute("user");
		String message = "";
		String result = "success";
		try {
			if (null != sessionObj) {
				SessionUser user = (SessionUser) sessionObj;
				String userId = user.getUserId();
				if (StringUtils.isNumber(replyId) && blogReplyService.delete(userId, Integer.parseInt(replyId))) {
					result = "success";
				}
				else {
					message = "操作错误";
					result = "fail";
				}
			}
			else {
				message = "你无权进行此操作";
				result = "fail";
			}
		} catch (Exception e) {
			message = "操作异常";
			result = "fail";
		}
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("message", message);
		modelMap.put("result", result);
		return modelMap;
	}
}
