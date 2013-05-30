package com.ulewo.controller;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ulewo.entity.SessionUser;
import com.ulewo.entity.User;
import com.ulewo.enums.QueryUserType;
import com.ulewo.service.UserService;
import com.ulewo.util.StringUtils;

@Controller
@RequestMapping("/user")
public class UserAction {
	@Autowired
	private UserService userService;

	/**
	 * 检测用户名是否已经存在
	 * @param userName
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkUserName/{userName}", method = RequestMethod.GET)
	public Map<String, Object> checkUserName(@PathVariable String userName, HttpSession session,
			HttpServletRequest request) {

		User user = userService.findUser(userName, QueryUserType.USERNAME);
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (null != user) {
			modelMap.put("result", "fail");
		}
		else {
			modelMap.put("result", "succuess");
		}
		return modelMap;
	}

	/**
	 * 注册
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/register/{userName}", method = RequestMethod.POST)
	public Map<String, Object> register(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		String checkEmail = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";
		String checkUserName = "^[\\w\\u4e00-\\u9fa5]+$";
		String checkPassWord = "^[0-9a-zA-Z]+$";

		String userName = request.getParameter("userName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String checkCode = request.getParameter("checkCode");
		String sessionCode = String.valueOf(session.getAttribute("checkCode"));
		String message = "";
		String result = "success";
		try {
			if (StringUtils.isEmpty(checkCode)) {
				message = "验证码不能为空";
				result = "fail";
			}
			else if (StringUtils.isEmpty(sessionCode) || !sessionCode.equalsIgnoreCase(checkCode)) {
				message = "验证码错误";
				result = "fail";
			}
			else if (!email.matches(checkEmail) || StringUtils.isEmpty(email)) {
				message = "邮箱地址不符合规范";
				result = "fail";
			}
			else if (!userName.matches(checkUserName) || StringUtils.isEmpty(userName)
					|| StringUtils.getRealLength(userName) < 1 || StringUtils.getRealLength(userName) > 20) {
				message = "昵称不符合规范";
				result = "fail";
			}
			else if (!password.matches(checkPassWord) || StringUtils.isEmpty(password) || password.length() < 6
					|| password.length() > 16) {
				message = "密码不符合规范";
				result = "fail";
			}
			else if (null != userService.findUser(email, QueryUserType.EMAIL)) {// 后台检测邮箱是否唯一
				message = "邮箱已经被占用";
				result = "fail";
			}
			else if (null != userService.findUser(userName, QueryUserType.USERNAME)) { // 后台检测用户昵称是否唯一
				message = "用户名已经被占用";
				result = "fail";
			}
			else {
				User user = new User();
				user.setUserName(userName);
				user.setPassword(StringUtils.encodeByMD5(password));
				user.setEmail(email);
				userService.addUser(user);
				String userId = user.getUserId();
				if (null != userId) {
					// 保存Cookie
					String infor = URLEncoder.encode(userName, "utf-8") + "," + password;

					// 清除之前的Cookie 信息
					Cookie cookie = new Cookie("cookieInfo", null);
					cookie.setPath("/");
					cookie.setMaxAge(0);

					// 建用户信息保存到Cookie中
					Cookie cookieInfo = new Cookie("cookieInfo", infor);
					cookieInfo.setPath("/");
					// 设置最大生命周期为1年。
					cookieInfo.setMaxAge(31536000);
					response.addCookie(cookieInfo);

					SessionUser sessionUser = new SessionUser();
					sessionUser.setUserId(userId);
					sessionUser.setUserName(userName);
					sessionUser.setUserLittleIcon(user.getUserLittleIcon());
					session.setAttribute("user", sessionUser);
				}
				else {
					message = "系统异常，请稍后再试";
					result = "fail";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "系统异常，请稍后再试";
			result = "fail";
		}
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("message", message);
		modelMap.put("result", result);
		return modelMap;
	}

	@ResponseBody
	@RequestMapping(value = "/{userId}", method = RequestMethod.POST)
	public Map<String, Object> queryUserInfo(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		return modelMap;
	}
}
