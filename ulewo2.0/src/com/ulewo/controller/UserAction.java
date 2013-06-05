package com.ulewo.controller;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.entity.BlogArticle;
import com.ulewo.entity.BlogItem;
import com.ulewo.entity.SessionUser;
import com.ulewo.entity.User;
import com.ulewo.enums.QueryUserType;
import com.ulewo.service.BlogArticleService;
import com.ulewo.service.BlogItemService;
import com.ulewo.service.UserService;
import com.ulewo.util.Constant;
import com.ulewo.util.PaginationResult;
import com.ulewo.util.StringUtils;
import com.ulewo.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserAction {
	@Autowired
	private UserService userService;

	@Autowired
	private BlogArticleService blogArticleService;

	@Autowired
	private BlogItemService blogItemService;

	/**
	 * 检测用户名是否已经存在
	 * 
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
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/register_do", method = RequestMethod.POST)
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
	@RequestMapping(value = "/login_do", method = RequestMethod.POST)
	public Map<String, Object> login(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		String account = request.getParameter("account");
		String password = request.getParameter("password");
		String checkCode = request.getParameter("checkCode");
		String autoLogin = request.getParameter("autoLogin");
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
			else {
				password = StringUtils.encodeByMD5(password);
				User user = userService.login(account, password);
				if (null != user) {
					if ("Y".equals(autoLogin)) {
						// 自动登录，保存用户名密码到 Cookie
						String infor = URLEncoder.encode(account, "utf-8") + "," + password;

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
					}
					else {
						Cookie cookie = new Cookie("cookieInfo", null);
						cookie.setPath("/");
						cookie.setMaxAge(0);
					}
					SessionUser sessionUser = new SessionUser();
					sessionUser.setUserId(user.getUserId());
					sessionUser.setUserName(user.getUserName());
					sessionUser.setUserLittleIcon(user.getUserLittleIcon());
					request.getSession().setAttribute("user", sessionUser);
					// 更新最后登录时间
					User loginUser = new User();
					loginUser.setUserId(user.getUserId());
					loginUser.setPrevisitTime(StringUtils.dateFormater.get().format(new Date()));
					userService.updateUser(loginUser);
				}
				else {
					message = "帐号或者密码错误";
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

	/**
	 * 用户中心
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public ModelAndView queryUserInfo(@PathVariable String userId, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		UserVo userVo = new UserVo();
		ModelAndView mv = new ModelAndView();
		try {
			if (StringUtils.isEmpty(userId)) {
				mv.setViewName("redirect:/../error");
				return mv;
			}
			User user = userService.findUser(userId, QueryUserType.USERID);
			if (null == user) {
				mv.setViewName("redirect:/../error");
				return mv;
			}
			userVo.setUserId(user.getUserId());
			userVo.setUserName(user.getUserName());
			userVo.setUserLittleIcon(user.getUserLittleIcon());
			userVo.setAddress(user.getAddress());
			userVo.setAge(user.getAge());
			userVo.setCharacters(user.getCharacters());
			userVo.setMark(user.getMark());
			userVo.setPrevisitTime(StringUtils.friendly_time(user.getPrevisitTime()));
			userVo.setRegisterTime(StringUtils.friendly_time(user.getRegisterTime()));
			userVo.setSex(user.getSex());
			userVo.setWork(user.getWork());
			mv.addObject("userVo", userVo);
			List<BlogArticle> list = blogArticleService.queryBlog(userId, 0, 0, 5);
			mv.addObject("bloglist", list);
			mv.addObject("userId", userId);
			mv.setViewName("user/userinfo");
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:/../error");
			return mv;
		}
		return mv;
	}

	/**
	 * ajax查询用户信息 头像，性别，积分，粉丝，关注
	 * 
	 * @param userId
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
	public Map<String, Object> queryUserInfoAjax(@PathVariable String userId, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			if (StringUtils.isEmpty(userId)) {
				modelMap.put("result", "fail");
				return modelMap;
			}
			User user = userService.findUser(userId, QueryUserType.USERID);
			if (null == user) {
				modelMap.put("result", "fail");
				return modelMap;
			}
			UserVo userVo = new UserVo();
			userVo.setUserId(user.getUserId());
			userVo.setUserName(user.getUserName());
			userVo.setUserLittleIcon(user.getUserLittleIcon());
			userVo.setAddress(user.getAddress());
			userVo.setAge(user.getAge());
			userVo.setCharacters(user.getCharacters());
			userVo.setMark(user.getMark());
			userVo.setPrevisitTime(user.getPrevisitTime());
			userVo.setRegisterTime(user.getRegisterTime());
			userVo.setSex(user.getSex());
			userVo.setWork(user.getWork());
			modelMap.put("result", "success");
			modelMap.put("user", userVo);
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	@RequestMapping(value = "/{userId}/blog", method = RequestMethod.GET)
	public ModelAndView blogList(@PathVariable String userId, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		try {
			if (StringUtils.isEmpty(userId)) {
				mv.setViewName("redirect:/../error");
				return mv;
			}
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
			PaginationResult result = blogArticleService.queryBlogByUserId(userId, itemId_int, page_int,
					Constant.pageSize15);
			List<BlogItem> blogItemList = blogItemService.queryBlogItemAndCountByUserId(userId);
			BlogItem item = blogItemService.queryBlogItemById(itemId_int);
			mv.addObject("result", result);
			mv.addObject("blogItemList", blogItemList);
			mv.addObject("blogitem", item);
			mv.setViewName("/user/blog");
			return mv;
		} catch (Exception e) {
			mv.setViewName("redirect:/../error");
			return mv;
		}
	}

	/**
	 * 博客详情
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/{userId}#blog/{blogId}/", method = RequestMethod.GET)
	public ModelAndView blogDetail(@PathVariable String userId, @PathVariable String blogId, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		try {
			if (StringUtils.isEmpty(userId)) {
				mv.setViewName("redirect:/../error");
				return mv;
			}
			int blogId_int = 0;
			if (StringUtils.isNumber(blogId)) {
				blogId_int = Integer.parseInt(blogId);
			}
			else {
				mv.setViewName("redirect:/../error");
				return mv;
			}
			BlogArticle blogArticle = blogArticleService.queryBlogById(blogId_int);
			mv.addObject("detail", blogArticle);
			mv.setViewName("blog_detail");
			return mv;
		} catch (Exception e) {
			mv.setViewName("redirect:/../error");
			return mv;
		}
	}
}
