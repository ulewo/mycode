package com.ulewo.controller;

import java.util.HashMap;
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
import com.ulewo.model.Group;
import com.ulewo.model.User;
import com.ulewo.service.GroupService;
import com.ulewo.service.Log;
import com.ulewo.service.UserService;
import com.ulewo.util.UlewoPaginationResult;

@Controller
@RequestMapping("/admin")
public class AdminAction extends BaseAction {
	@Autowired
	private UserService userService;

	@Autowired
	private GroupService groupService;

	@Log
	Logger log;

	/***********  成员 **************/
	@RequestMapping(value = "/users")
	@ResponseBody
	public Map<String, Object> users(HttpSession session, HttpServletRequest request) {
		Map<String, Object> resultObj = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			UlewoPaginationResult<User> result = this.userService.findAllUsers(map);
			resultObj.put("rows", result.getList());
			resultObj.put("total", result.getPage().getCountTotal());
			return resultObj;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return resultObj;
		}
	}

	@RequestMapping(value = "/deleteUsers")
	@ResponseBody
	public Map<String, Object> deleteUsers(HttpSession session, HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			this.userService.deleteUserBatch(map);
			modelMap.put("result", ResultCode.SUCCESS.getCode());
			return modelMap;
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			modelMap.put("result", ResultCode.ERROR.getCode());
			modelMap.put("msg", e.getMessage());
			return modelMap;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("result", ResultCode.ERROR.getCode());
			modelMap.put("msg", "系统异常");
			return modelMap;
		}
	}

	/***************窝窝********************/

	@RequestMapping(value = "/groups")
	@ResponseBody
	public Map<String, Object> groups(HttpSession session, HttpServletRequest request) {
		Map<String, Object> resultObj = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			map.put("orderBy", "create_time desc");
			UlewoPaginationResult<Group> result = this.groupService.findAllGroup(map);
			resultObj.put("rows", result.getList());
			resultObj.put("total", result.getPage().getCountTotal());
			return resultObj;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return resultObj;
		}
	}

	@RequestMapping(value = "/deleteGroups")
	@ResponseBody
	public Map<String, Object> deleteGroups(HttpSession session, HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			this.groupService.deleteGroup(map);
			modelMap.put("result", ResultCode.SUCCESS.getCode());
			return modelMap;
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			modelMap.put("result", ResultCode.ERROR.getCode());
			modelMap.put("msg", e.getMessage());
			return modelMap;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("result", ResultCode.ERROR.getCode());
			modelMap.put("msg", "系统异常");
			return modelMap;
		}
	}
}
