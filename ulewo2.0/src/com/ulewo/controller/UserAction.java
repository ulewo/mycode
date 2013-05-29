package com.ulewo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ulewo.entity.User;
import com.ulewo.enums.QueryUserType;
import com.ulewo.service.UserService;

@Controller
@RequestMapping("/user")
public class UserAction {
	@Autowired
	private UserService userService;
	
	@ResponseBody
	@RequestMapping(value="/checkUserName/{userName}",method=RequestMethod.GET)
	public Map<String,Object> getMessage(@PathVariable String userName,HttpSession session, HttpServletRequest request) {
		User user =userService.findUser(userName, QueryUserType.USERNAME);
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if(null!=user){
			modelMap.put("result", "fail");
		}else{
			modelMap.put("result", "succuess");
		}
		return modelMap;
	}
}
