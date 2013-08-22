package com.ulewo.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.entity.BlogArticle;
import com.ulewo.entity.BlogItem;
import com.ulewo.entity.BlogReply;
import com.ulewo.entity.Favorite;
import com.ulewo.entity.Group;
import com.ulewo.entity.Notice;
import com.ulewo.entity.ReTalk;
import com.ulewo.entity.SessionUser;
import com.ulewo.entity.Talk;
import com.ulewo.entity.User;
import com.ulewo.entity.UserFriend;
import com.ulewo.enums.BlogOrderType;
import com.ulewo.enums.QueryUserType;
import com.ulewo.service.ArticleService;
import com.ulewo.service.BlogArticleService;
import com.ulewo.service.BlogItemService;
import com.ulewo.service.BlogReplyService;
import com.ulewo.service.FavoriteService;
import com.ulewo.service.GroupService;
import com.ulewo.service.NoticeService;
import com.ulewo.service.ReTalkService;
import com.ulewo.service.TalkService;
import com.ulewo.service.UserFriendService;
import com.ulewo.service.UserService;
import com.ulewo.util.Constant;
import com.ulewo.util.DrowImage;
import com.ulewo.util.ErrorReport;
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

	@Autowired
	private BlogReplyService blogReplyService;

	@Autowired
	private UserFriendService userFriendService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private TalkService talkService;

	@Autowired
	private ReTalkService reTalkService;

	@Autowired
	private ArticleService articleService;

	@Autowired
	private NoticeService noticeService;

	@Autowired
	private FavoriteService favoriteService;

	private final static int MAXLENGTH = 250;

	private final static int USERNAME_LENGTH = 20;

	private final static int EMAIL_LENGTH = 100;

	private final static int PWD_MIN_LENGTH = 6;

	private final static int PWD_MAX_LENGTH = 16;

	private final static int MAX_FILE = 1024 * 1024;

	private static final int MAXWIDTH = 600;

	/**
	 * 发送重置密码连接
	 * 
	 * @param userName
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sendRestPwd.do", method = RequestMethod.POST)
	public Map<String, Object> checkUserName(HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String account = request.getParameter("account");
			String checkCode = request.getParameter("code");
			String sessionCode = String.valueOf(session
					.getAttribute("checkCode"));
			if (StringUtils.isEmpty(account)) {
				modelMap.put("result", "fail");
				modelMap.put("message", "帐号不能为空");
				return modelMap;
			}
			if (StringUtils.isEmpty(checkCode)) {
				modelMap.put("result", "fail");
				modelMap.put("message", "验证码不能为空");
				return modelMap;
			} else if (StringUtils.isEmpty(sessionCode)
					|| !sessionCode.equalsIgnoreCase(checkCode)) {
				modelMap.put("result", "fail");
				modelMap.put("message", "验证码错误");
				return modelMap;
			}
			User user = null;
			if (account.contains("@")) {
				user = userService.findUser(account, QueryUserType.EMAIL);
			} else {
				user = userService.findUser(account, QueryUserType.USERNAME);
			}
			if (null == user) {
				modelMap.put("result", "fail");
				modelMap.put("message", "帐号不存在");
				return modelMap;
			}
			String mailAddress = userService.sendRestPwd(user);
			modelMap.put("result", "success");
			modelMap.put("address", mailAddress);
			return modelMap;
		} catch (Exception e) {
			String errorMethod = "UserAction-->sendRestPwd()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			modelMap.put("result", "fail");
			modelMap.put("message", "系统异常");
			return modelMap;
		}

	}

	@RequestMapping(value = "/findPwd", method = RequestMethod.GET)
	public ModelAndView findPwd(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		try {
			String account = request.getParameter("account");
			String code = request.getParameter("code");
			mv.addObject("account", account);
			mv.addObject("code", code);
			mv.setViewName("rest_pwd_2");
		} catch (Exception e) {
			String errorMethod = "UserAction-->findPwd()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/resetpwd.do", method = RequestMethod.POST)
	public Map<String, Object> resetpwd(HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String account = request.getParameter("account");
			String activationCode = request.getParameter("activationCode");
			String pwd = request.getParameter("pwd");
			String checkCode = request.getParameter("code");
			String sessionCode = String.valueOf(session
					.getAttribute("checkCode"));
			String checkPassWord = "^[0-9a-zA-Z]+$";

			if (StringUtils.isEmpty(account)) {
				modelMap.put("result", "fail");
				modelMap.put("message", "帐号不能为空");
				return modelMap;
			}
			if (StringUtils.isEmpty(activationCode)) {
				modelMap.put("result", "fail");
				modelMap.put("message", "激活码不能为空");
				return modelMap;
			}
			if (StringUtils.isEmpty(checkCode)) {
				modelMap.put("result", "fail");
				modelMap.put("message", "验证码不能为空");
				return modelMap;
			} else if (StringUtils.isEmpty(sessionCode)
					|| !sessionCode.equalsIgnoreCase(checkCode)) {
				modelMap.put("result", "fail");
				modelMap.put("message", "验证码错误");
				return modelMap;
			}

			if (!pwd.matches(checkPassWord) || StringUtils.isEmpty(pwd)
					|| pwd.length() < PWD_MIN_LENGTH
					|| pwd.length() > PWD_MAX_LENGTH) {
				modelMap.put("result", "fail");
				modelMap.put("message", "密码不符合规范");
				return modelMap;
			}
			return userService.resetPwd(account, activationCode, pwd);
		} catch (Exception e) {
			String errorMethod = "UserAction-->resetpwd()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			modelMap.put("result", "fail");
			modelMap.put("message", "系统异常");
			return modelMap;
		}

	}

	/**
	 * 注册
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/register.do", method = RequestMethod.POST)
	public Map<String, Object> register(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		String checkEmail = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";
		String checkUserName = "^[\\w\\u4e00-\\u9fa5]+$";
		String checkPassWord = "^[0-9a-zA-Z]+$";

		String userName = request.getParameter("userName");
		String email = request.getParameter("email");
		String password = request.getParameter("pwd");
		String checkCode = request.getParameter("code");
		String sessionCode = String.valueOf(session.getAttribute("checkCode"));
		String message = "";
		String result = "success";
		String userId = "";
		try {
			if (StringUtils.isEmpty(checkCode)) {
				message = "验证码不能为空";
				result = "fail";
			} else if (StringUtils.isEmpty(sessionCode)
					|| !sessionCode.equalsIgnoreCase(checkCode)) {
				message = "验证码错误";
				result = "fail";
			} else if (!email.matches(checkEmail) || StringUtils.isEmpty(email)
					|| email.length() > EMAIL_LENGTH) {
				message = "邮箱地址不符合规范";
				result = "fail";
			} else if (!userName.matches(checkUserName)
					|| StringUtils.isEmpty(userName.trim())
					|| StringUtils.getRealLength(userName) < 1
					|| StringUtils.getRealLength(userName) > USERNAME_LENGTH) {
				message = "用户名不符合规范";
				result = "fail";
			} else if (!password.matches(checkPassWord)
					|| StringUtils.isEmpty(password)
					|| password.length() < PWD_MIN_LENGTH
					|| password.length() > PWD_MAX_LENGTH) {
				message = "密码不符合规范";
				result = "fail";
			} else if (null != userService.findUser(email, QueryUserType.EMAIL)) {// 后台检测邮箱是否唯一
				message = "邮箱已经被占用";
				result = "fail";
			} else if (null != userService.findUser(userName,
					QueryUserType.USERNAME)) { // 后台检测用户昵称是否唯一
				message = "用户名已经被占用";
				result = "fail";
			} else {
				User user = new User();
				user.setUserName(userName);
				user.setPassword(StringUtils.encodeByMD5(password));
				user.setEmail(email);
				userService.addUser(user);
				userId = user.getUserId();
				if (StringUtils.isNotEmpty(userId)) {
					// 保存Cookie
					String infor = URLEncoder.encode(userName, "utf-8") + ","
							+ password;

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
				} else {
					message = "系统异常，请稍后再试";
					result = "fail";
				}
			}
		} catch (Exception e) {
			String errorMethod = "UserAction-->register()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			message = "系统异常，请稍后再试";
			result = "fail";
		}
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("message", message);
		modelMap.put("result", result);
		modelMap.put("userId", userId);
		return modelMap;
	}

	@ResponseBody
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public Map<String, Object> login(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

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
			} else if (StringUtils.isEmpty(sessionCode)
					|| !sessionCode.equalsIgnoreCase(checkCode)) {
				message = "验证码错误";
				result = "fail";
			} else {
				password = StringUtils.encodeByMD5(password);
				User user = userService.login(account, password);
				if (null != user) {
					if ("Y".equals(autoLogin)) {
						// 自动登录，保存用户名密码到 Cookie
						String infor = URLEncoder.encode(account, "utf-8")
								+ "," + password;

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
					} else {
						Cookie cookie = new Cookie("cookieInfo", null);
						cookie.setPath("/");
						cookie.setMaxAge(0);
					}
					SessionUser sessionUser = new SessionUser();
					sessionUser.setUserId(user.getUserId());
					sessionUser.setUserName(user.getUserName());
					sessionUser.setUserLittleIcon(user.getUserLittleIcon());
					session.setAttribute("user", sessionUser);
					// 更新最后登录时间
					User loginUser = new User();
					loginUser.setUserId(user.getUserId());
					loginUser.setPrevisitTime(StringUtils.dateFormater.get()
							.format(new Date()));
					userService.updateUser(loginUser);
				} else {
					message = "帐号或者密码错误";
					result = "fail";
				}
			}
		} catch (Exception e) {
			String errorMethod = "UserAction-->login()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			message = "系统异常，请稍后再试";
			result = "fail";
		}
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("message", message);
		modelMap.put("result", result);
		return modelMap;
	}

	@ResponseBody
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public Map<String, Object> logout(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		Cookie cookie = new Cookie("cookieInfo", null);
		cookie.setPath("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		session.invalidate();
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("result", "success");
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
	public ModelAndView queryUserInfo(@PathVariable String userId,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		try {

			UserVo userVo = checkUserInfo(userId, session);
			if (null == userVo) {
				mv.setViewName("redirect:" + Constant.ERRORPAGE);
			}
			mv.addObject("userVo", userVo);
			List<BlogArticle> list = blogArticleService.queryBlog(userId, 0, 0,
					5, BlogOrderType.POSTTIME);
			List<UserFriend> focusList = userFriendService.queryFocus2List(
					userId, 0, 15);
			List<UserFriend> fansList = userFriendService.queryFans2List(
					userId, 0, 15);
			List<Group> createdGroups = groupService.queryCreatedGroups(userId);
			List<Group> joinedGroups = groupService.queryJoinedGroups(userId);
			mv.addObject("focusList", focusList);
			mv.addObject("fansList", fansList);
			mv.addObject("bloglist", list);
			mv.addObject("createdGroups", createdGroups);
			mv.addObject("joinedGroups", joinedGroups);
			mv.setViewName("user/userhome");
		} catch (Exception e) {
			String errorMethod = "UserAction-->queryUserInfo()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
		return mv;
	}

	/**
	 * 查询粉丝，关注的人
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/loadFansFocus.action", method = RequestMethod.GET)
	public Map<String, Object> loadFansFocus(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String userId = ((SessionUser) session.getAttribute("user"))
					.getUserId();
			List<UserFriend> focusList = userFriendService.queryFocus2List(
					userId, 0, 15);
			List<UserFriend> fansList = userFriendService.queryFans2List(
					userId, 0, 15);
			modelMap.put("focusList", focusList);
			modelMap.put("fansList", fansList);
			return modelMap;
		} catch (Exception e) {
			String errorMethod = "UserAction-->loadFansFocus()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	/**
	 * 查询消息
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/loadNotice.action", method = RequestMethod.GET)
	public Map<String, Object> loadNotice(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String userId = ((SessionUser) session.getAttribute("user"))
					.getUserId();
			List<Notice> list = noticeService.queryNoticeByUserId(userId,
					Constant.STATUSYN);
			modelMap.put("list", list);
			return modelMap;
		} catch (Exception e) {
			String errorMethod = "UserAction-->loadNotice()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	/**
	 * 创建的窝窝，加入的窝窝
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/loadGroup.action", method = RequestMethod.GET)
	public Map<String, Object> loadGroup(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String userId = ((SessionUser) session.getAttribute("user"))
					.getUserId();
			List<Group> createdGroups = groupService.queryCreatedGroups(userId);
			List<Group> joinedGroups = groupService.queryJoinedGroups(userId);
			modelMap.put("createdGroups", createdGroups);
			modelMap.put("joinedGroups", joinedGroups);
			return modelMap;
		} catch (Exception e) {
			String errorMethod = "UserAction-->loadGroup()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	private UserVo checkUserInfo(String userId, HttpSession session) {

		if (StringUtils.isEmpty(userId)) {
			return null;
		}
		User user = userService.queryBaseInfo(userId);
		if (null == user) {
			return null;
		}
		boolean haveFocus = false;

		Object obj = session.getAttribute("user");
		if (obj != null) {
			UserFriend userFriend = userFriendService.queryFocusUser(
					((SessionUser) obj).getUserId(), userId);
			if (null != userFriend) {
				haveFocus = true;
			}
		}
		UserVo userVo = new UserVo();
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
		userVo.setFansCount(user.getFansCount());
		userVo.setFocusCount(user.getFocusCount());
		userVo.setHaveFocus(haveFocus);
		return userVo;
	}

	@RequestMapping(value = "/{userId}/blog", method = RequestMethod.GET)
	public ModelAndView blogList(@PathVariable String userId,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		try {
			UserVo userVo = checkUserInfo(userId, session);
			if (null == userVo) {
				mv.setViewName("redirect:" + Constant.ERRORPAGE);
			}
			mv.addObject("userVo", userVo);
			String page = request.getParameter("page");
			int page_int = 0;
			if (StringUtils.isNumber(page)) {
				page_int = Integer.parseInt(page);
			}
			PaginationResult result = blogArticleService.queryBlogByUserId(
					userId, 0, page_int, Constant.pageSize15,
					BlogOrderType.POSTTIME);
			List<BlogItem> blogItemList = blogItemService
					.queryBlogItemAndCountByUserId(userId);
			List<BlogArticle> hotlist = blogArticleService.queryBlog(userId, 0,
					0, 10, BlogOrderType.READCOUNT);
			int totalCount = blogArticleService.queryBlogCount(userId, 0);
			mv.addObject("hotlist", hotlist);
			mv.addObject("result", result);
			mv.addObject("blogItemList", blogItemList);
			mv.addObject("userId", userId);
			mv.addObject("totalCount", totalCount);
			mv.setViewName("/user/blog");
			return mv;
		} catch (Exception e) {
			String errorMethod = "UserAction-->blogList()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
	}

	@RequestMapping(value = "/{userId}/blog/itemId/{itemId}", method = RequestMethod.GET)
	public ModelAndView blogList(@PathVariable String userId,
			@PathVariable String itemId, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		try {
			UserVo userVo = checkUserInfo(userId, session);
			if (null == userVo) {
				mv.setViewName("redirect:" + Constant.ERRORPAGE);
			}
			mv.addObject("userVo", userVo);
			String page = request.getParameter("page");
			int itemId_int = 0;
			int page_int = 0;
			if (StringUtils.isNumber(itemId)) {
				itemId_int = Integer.parseInt(itemId);
			}
			if (StringUtils.isNumber(page)) {
				page_int = Integer.parseInt(page);
			}
			PaginationResult result = blogArticleService.queryBlogByUserId(
					userId, itemId_int, page_int, Constant.pageSize15,
					BlogOrderType.POSTTIME);
			List<BlogItem> blogItemList = blogItemService
					.queryBlogItemAndCountByUserId(userId);
			BlogItem item = blogItemService.queryBlogItemById(itemId_int);
			List<BlogArticle> hotlist = blogArticleService.queryBlog(userId, 0,
					0, 10, BlogOrderType.READCOUNT);
			int totalCount = blogArticleService.queryBlogCount(userId, 0);
			mv.addObject("hotlist", hotlist);
			mv.addObject("result", result);
			mv.addObject("blogItemList", blogItemList);
			mv.addObject("blogitem", item);
			mv.addObject("itemId", itemId_int);
			mv.addObject("userId", userId);
			mv.addObject("totalCount", totalCount);
			mv.setViewName("/user/blog");
			return mv;
		} catch (Exception e) {
			String errorMethod = "UserAction-->blogList()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
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
	@RequestMapping(value = "/{userId}/blog/{blogId}", method = RequestMethod.GET)
	public ModelAndView blogDetail(@PathVariable String userId,
			@PathVariable String blogId, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		try {
			UserVo userVo = checkUserInfo(userId, session);
			if (null == userVo) {
				mv.setViewName("redirect:" + Constant.ERRORPAGE);
			}
			mv.addObject("userVo", userVo);
			int blogId_int = 0;
			if (StringUtils.isNumber(blogId)) {
				blogId_int = Integer.parseInt(blogId);
			} else {
				mv.setViewName("redirect:" + Constant.ERRORPAGE);
				return mv;
			}
			BlogArticle blogArticle = blogArticleService
					.queryBlogById(blogId_int);
			BlogArticle updateReadNumber = new BlogArticle();
			updateReadNumber.setId(blogArticle.getId());
			updateReadNumber.setReadCount(blogArticle.getReadCount() + 1);
			blogArticleService.updateReadCount(updateReadNumber);
			List<BlogItem> blogItemList = blogItemService
					.queryBlogItemAndCountByUserId(userId);
			int countTotal = blogArticleService.queryBlogCount(userId, 0);
			List<BlogArticle> hotlist = blogArticleService.queryBlog(userId, 0,
					0, 10, BlogOrderType.READCOUNT);
			mv.addObject("hotlist", hotlist);
			mv.addObject("blogItemList", blogItemList);
			mv.addObject("blog", blogArticle);
			mv.addObject("countTotal", countTotal);
			mv.addObject("userId", userId);
			mv.setViewName("user/blog_detail");
			return mv;
		} catch (Exception e) {
			String errorMethod = "UserAction-->blogDetail()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
	}

	/** 博客回复 ***/
	@ResponseBody
	@RequestMapping(value = "/replayList", method = RequestMethod.GET)
	public Map<String, Object> replayList(HttpSession session,
			HttpServletRequest request) {

		String blogId = request.getParameter("blogId");
		String page = request.getParameter("page");
		String result = "success";
		int page_int = 0;
		int blogId_int = 0;
		if (StringUtils.isNumber(blogId)) {
			blogId_int = Integer.parseInt(blogId);
		}

		if (StringUtils.isNumber(page)) {
			page_int = Integer.parseInt(page);
		}
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			PaginationResult resultList = blogReplyService
					.queryBlogReplyByBlogId(blogId_int, page_int,
							Constant.pageSize25);
			modelMap.put("result", result);
			modelMap.put("paginResult", resultList);
			return modelMap;
		} catch (Exception e) {
			String errorMethod = "UserAction-->replayList()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			modelMap.put("result", "fail");
			return modelMap;
		}

	}

	/**
	 * 保存博客评论回复
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveReplay.action", method = RequestMethod.POST)
	public Map<String, Object> saveReplay(HttpSession session,
			HttpServletRequest request) {

		String content = request.getParameter("content");
		String atUserId = request.getParameter("atUserId");
		String atUserName = request.getParameter("atUserName");
		String blogId = request.getParameter("blogId");
		String blogAuthor = request.getParameter("blogAuthor");
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
			if (StringUtils.isEmpty(content)) {
				result = "fail";
				message = "输入内容为空";
				modelMap.put("message", message);
				modelMap.put("result", result);
				return modelMap;
			}
			if (!StringUtils.isNumber(blogId)
					|| StringUtils.isEmpty(blogAuthor)) {
				result = "fail";
				message = "操作错误";
				modelMap.put("message", message);
				modelMap.put("result", result);
				return modelMap;
			}
			BlogReply reply = new BlogReply();
			SessionUser sessionUser = (SessionUser) sessionObj;
			reply.setBlogAuthor(blogAuthor);
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
			modelMap.put("note", reply);
			return modelMap;
		} catch (Exception e) {
			String errorMethod = "UserAction-->saveReplay()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			message = "操作异常";
			result = "fail";
			modelMap.put("message", message);
			modelMap.put("result", result);
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/deleteReplay.action", method = RequestMethod.GET)
	public Map<String, Object> deleteReply(HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String replyId = request.getParameter("replyId");
			Object sessionObj = session.getAttribute("user");
			SessionUser sessionUser = (SessionUser) sessionObj;
			if (StringUtils.isNumber(replyId)
					&& blogReplyService.delete(sessionUser.getUserId(),
							Integer.parseInt(replyId))) {
				modelMap.put("result", "success");
				return modelMap;
			} else {
				modelMap.put("message", "请求参数错误");
				modelMap.put("result", "fail");
				return modelMap;
			}

		} catch (Exception e) {
			String errorMethod = "UserAction-->deleteReply()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			modelMap.put("message", "系统异常");
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/focusFriend.action", method = RequestMethod.POST)
	public Map<String, Object> focusFriend(HttpSession session,
			HttpServletRequest request) {

		String friendId = request.getParameter("friendid");
		Object sessionObj = session.getAttribute("user");
		String message = "";
		String result = "success";
		try {
			if (null != sessionObj) {
				SessionUser user = (SessionUser) sessionObj;
				String userId = user.getUserId();
				UserFriend friend = new UserFriend();
				friend.setUserId(userId);
				friend.setFriendId(friendId);
				userFriendService.addFriend(friend);
			} else {
				message = "你无权进行此操作";
				result = "fail";
			}
		} catch (Exception e) {
			String errorMethod = "UserAction-->focusFriend()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			message = "操作异常";
			result = "fail";
		}
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("message", message);
		modelMap.put("result", result);
		return modelMap;
	}

	@ResponseBody
	@RequestMapping(value = "/cancelFocus.action", method = RequestMethod.POST)
	public Map<String, Object> cancelFocus(HttpSession session,
			HttpServletRequest request) {

		String friendId = request.getParameter("friendid");
		Object sessionObj = session.getAttribute("user");
		String message = "";
		String result = "success";
		try {
			if (null != sessionObj) {
				SessionUser user = (SessionUser) sessionObj;
				String userId = user.getUserId();
				userFriendService.deleteFirend(userId, friendId);
			} else {
				message = "你无权进行此操作";
				result = "fail";
			}
		} catch (Exception e) {
			String errorMethod = "UserAction-->cancelFocus()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			message = "操作异常";
			result = "fail";
		}
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("message", message);
		modelMap.put("result", result);
		return modelMap;
	}

	@RequestMapping(value = "/talkImgUpload", method = RequestMethod.POST)
	public ModelAndView fileupload(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		try {
			Object user = session.getAttribute("user");
			if (null == user) {
				mv.addObject("result", "fail");
				mv.addObject("message", "你登陆已超时，请重新登陆");
				mv.setViewName("common/talkimgupload");
				return mv;
			}
			String realPath = session.getServletContext().getRealPath("/");
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartRequest.getFile("file");
			long size = multipartFile.getSize();
			if (size > MAX_FILE) {
				mv.addObject("result", "fail");
				mv.addObject("message", "文件超过1M");
				mv.setViewName("common/talkimgupload");
				return mv;
			}
			String fileName = multipartFile.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			if (!"JPG".equalsIgnoreCase(suffix)
					&& !"PNG".equalsIgnoreCase(suffix)
					&& !"gif".equalsIgnoreCase(suffix)
					&& !"BMP".equalsIgnoreCase(suffix)) {
				mv.addObject("result", "fail");
				mv.addObject("message", "文件类型只能是图片");
				mv.setViewName("common/talkimgupload");
				return mv;
			}
			String current = String.valueOf(System.currentTimeMillis());
			fileName = current + "." + suffix;
			SimpleDateFormat formater = new SimpleDateFormat("yyyyMM");
			String saveDir = formater.format(new Date());
			String savePath = saveDir + "/" + fileName;
			String fileDir = realPath + "upload" + "/" + saveDir;
			File dir = new File(fileDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			String filePath = fileDir + "/" + fileName;
			File file = new File(filePath);
			multipartFile.transferTo(file);

			File fromFile = new File(filePath);
			BufferedImage srcImage = ImageIO.read(fromFile);
			int width = srcImage.getWidth();
			int height = srcImage.getHeight();
			int w = MAXWIDTH;
			int h = MAXWIDTH;
			if (fromFile.length() > 200 * 1024 || width > MAXWIDTH) {
				if (fromFile.length() > 200 * 1024 && width < MAXWIDTH) {// 图片太大，长宽不大的情况
					w = width;
					h = height;
				}
				String drowPath = fileDir + "/" + current + "x.jpg";
				DrowImage.saveImageAsJpg(filePath, drowPath, w, h, false);
				fromFile.delete();
				savePath = saveDir + "/" + current + "x.jpg";
			}
			mv.addObject("result", "success");
			mv.addObject("savePath", savePath);
			mv.setViewName("common/talkimgupload");
			return mv;
		} catch (Exception e) {
			String errorMethod = "UserAction-->fileupload()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			mv.addObject("result", "fail");
			mv.setViewName("common/talkimgupload");
			return mv;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/saveTalk.action", method = RequestMethod.POST)
	public Map<String, Object> saveTalk(HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String content = request.getParameter("content");
			String imgurl = request.getParameter("imgUrl");
			if (StringUtils.isEmpty(content) || content.length() > MAXLENGTH) {
				modelMap.put("message", "吐槽内容不能为空");
				modelMap.put("result", "fail");
				return modelMap;
			}
			SessionUser sessionUser = (SessionUser) session
					.getAttribute("user");
			Talk talk = new Talk();
			talk.setContent(content);
			talk.setImgurl(imgurl);
			talk.setUserId(sessionUser.getUserId());
			talk.setUserName(sessionUser.getUserName());
			talk.setUserIcon(sessionUser.getUserLittleIcon());
			talkService.addTalk(talk);
			talk.setCreateTime(StringUtils.friendly_time(talk.getCreateTime()));
			modelMap.put("result", "success");
			modelMap.put("talk", talk);
			return modelMap;
		} catch (Exception e) {
			String errorMethod = "UserAction-->saveTalk()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			modelMap.put("message", "系统异常");
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/loadTalk", method = RequestMethod.GET)
	public Map<String, Object> loadTalk(HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String userId = request.getParameter("userId");
			String page = request.getParameter("page");
			int page_int = 1;
			if (StringUtils.isNumber(page)) {
				page_int = Integer.parseInt(page);
			}
			String type = request.getParameter("type");
			int type_int = 0;
			if (StringUtils.isNumber(type)) {
				type_int = Integer.parseInt(type);
			}
			Object sessionUser = session.getAttribute("user");
			PaginationResult data = talkService.queryTalkByUserIdByPag(
					page_int, Constant.pageSize15, userId, sessionUser,
					type_int);
			modelMap.put("result", "success");
			modelMap.put("data", data);
			return modelMap;
		} catch (Exception e) {
			e.printStackTrace();
			String errorMethod = "UserAction-->loadTalk()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			// Thread thread = new Thread(report);
			// thread.start();
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/loadArticle", method = RequestMethod.GET)
	public Map<String, Object> loadArticle(HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String userId = request.getParameter("userId");
			String page = request.getParameter("page");
			int page_int = 1;
			if (StringUtils.isNumber(page)) {
				page_int = Integer.parseInt(page);
			}
			String type = request.getParameter("type");
			int type_int = 0;
			if (StringUtils.isNumber(type)) {
				type_int = Integer.parseInt(type);
			}
			Object sessionUser = session.getAttribute("user");
			PaginationResult data = articleService.queryArticleByUserIdByPag(
					page_int, Constant.pageSize15, userId, sessionUser,
					type_int);
			modelMap.put("result", "success");
			modelMap.put("data", data);
			return modelMap;
		} catch (Exception e) {
			e.printStackTrace();
			String errorMethod = "UserAction-->loadTalk()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			// Thread thread = new Thread(report);
			// thread.start();
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/loadBlog", method = RequestMethod.GET)
	public Map<String, Object> loadBlog(HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String userId = request.getParameter("userId");
			String page = request.getParameter("page");
			int page_int = 1;
			if (StringUtils.isNumber(page)) {
				page_int = Integer.parseInt(page);
			}
			String type = request.getParameter("type");
			int type_int = 0;
			if (StringUtils.isNumber(type)) {
				type_int = Integer.parseInt(type);
			}
			Object sessionUser = session.getAttribute("user");
			PaginationResult data = blogArticleService
					.queryArticleByUserIdByPag(page_int, Constant.pageSize15,
							userId, sessionUser, type_int);
			modelMap.put("result", "success");
			modelMap.put("data", data);
			return modelMap;
		} catch (Exception e) {
			e.printStackTrace();
			String errorMethod = "UserAction-->loadTalk()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			// Thread thread = new Thread(report);
			// thread.start();
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	@RequestMapping(value = "/{userId}/talk/{talkId}", method = RequestMethod.GET)
	public ModelAndView talkDetal(@PathVariable String userId,
			@PathVariable String talkId, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		try {

			UserVo userVo = checkUserInfo(userId, session);
			if (null == userVo) {
				mv.setViewName("redirect:" + Constant.ERRORPAGE);
			}
			if (!StringUtils.isNumber(talkId)) {
				mv.setViewName("redirect:" + Constant.ERRORPAGE);
			}
			int talkId_int = Integer.parseInt(talkId);
			mv.addObject("userVo", userVo);
			List<UserFriend> focusList = userFriendService.queryFocus2List(
					userId, 0, 15);
			List<UserFriend> fansList = userFriendService.queryFans2List(
					userId, 0, 15);
			List<Group> createdGroups = groupService.queryCreatedGroups(userId);
			List<Group> joinedGroups = groupService.queryJoinedGroups(userId);
			Talk talk = talkService.queryDetail(talkId_int);
			mv.addObject("focusList", focusList);
			mv.addObject("fansList", fansList);
			mv.addObject("createdGroups", createdGroups);
			mv.addObject("joinedGroups", joinedGroups);
			mv.addObject("talk", talk);
			mv.addObject("userId", userId);
			mv.setViewName("user/talk_detal");
		} catch (Exception e) {
			String errorMethod = "UserAction-->talkDetal()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/loadReTalk", method = RequestMethod.GET)
	public Map<String, Object> loadReTalk(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String talkId = request.getParameter("talkId");
			if (!StringUtils.isNumber(talkId)) {
				modelMap.put("result", "fail");
				modelMap.put("message", "参数错误");
			}
			int talkId_int = Integer.parseInt(talkId);
			PaginationResult data = reTalkService.queryReTalkByPag(1,
					Constant.pageSize50, talkId_int);
			modelMap.put("result", "success");
			modelMap.put("list", data);
			return modelMap;
		} catch (Exception e) {
			String errorMethod = "UserAction-->loadReTalk()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/saveReTalk.action", method = RequestMethod.POST)
	public Map<String, Object> saveReTalk(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String content = request.getParameter("content");
			String talkId = request.getParameter("talkId");
			if (StringUtils.isEmpty(content) || content.length() > MAXLENGTH) {
				modelMap.put("result", "fail");
				modelMap.put("message", "回复内容不能为空");
				return modelMap;
			}
			if (!StringUtils.isNumber(talkId)) {
				modelMap.put("result", "fail");
				modelMap.put("message", "请求参数错误");
				return modelMap;
			}
			int talkId_int = Integer.parseInt(talkId);
			String talkUserId = request.getParameter("talkUserId");
			String atUserId = request.getParameter("atUserId");
			String atUserName = request.getParameter("atUserName");
			SessionUser sessionUser = (SessionUser) session
					.getAttribute("user");
			ReTalk retalk = new ReTalk();
			retalk.setTalkId(talkId_int);
			retalk.setTalkUserId(talkUserId);
			retalk.setContent(content);
			retalk.setUserId(sessionUser.getUserId());
			retalk.setUserName(sessionUser.getUserName());
			retalk.setUserIcon(sessionUser.getUserLittleIcon());
			retalk.setAtUserId(atUserId);
			retalk.setAtUserName(atUserName);
			reTalkService.addReTalk(retalk);
			retalk.setCreateTime(StringUtils.friendly_time(retalk
					.getCreateTime()));
			modelMap.put("result", "success");
			modelMap.put("retalk", retalk);
			return modelMap;
		} catch (Exception e) {
			String errorMethod = "UserAction-->saveReTalk()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	/**
	 * 获取文章收藏情况
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkFavorite", method = RequestMethod.GET)
	public Map<String, Object> checkFavorite(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String articleId = request.getParameter("articleId");
			if (!StringUtils.isNumber(articleId)) {
				modelMap.put("result", "fail");
				return modelMap;
			}
			int articleId_int = Integer.parseInt(articleId);
			String type = request.getParameter("type");
			if (StringUtils.isEmpty(type)) {
				modelMap.put("result", "fail");
				return modelMap;
			}
			Object sessionObj = session.getAttribute("user");
			boolean haveFavorite = false;
			if (null != sessionObj) {
				SessionUser user = (SessionUser) sessionObj;
				int count = favoriteService
						.queryFavoriteCountByUserIdAndArticleId(
								user.getUserId(), articleId_int, type);
				if (count > 0) {
					haveFavorite = true;
				}
			}
			int haveFavoriteCount = favoriteService
					.queryFavoriteCountByArticleId(articleId_int, type);
			modelMap.put("haveFavoriteCount", haveFavoriteCount);
			modelMap.put("haveFavorite", haveFavorite);
			modelMap.put("result", "success");
			return modelMap;
		} catch (Exception e) {
			String errorMethod = "UserAction-->checkFavorite()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/favoriteArticle.action", method = RequestMethod.POST)
	public Map<String, Object> favoriteArticle(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String articleId = request.getParameter("articleId");
			if (!StringUtils.isNumber(articleId)) {
				modelMap.put("result", "fail");
				modelMap.put("message", "参数错误");
				return modelMap;
			}
			int articleId_int = Integer.parseInt(articleId);
			String type = request.getParameter("type");
			if (StringUtils.isEmpty(type)) {
				modelMap.put("result", "fail");
				modelMap.put("message", "参数错误");
				return modelMap;
			}
			String title = request.getParameter("title");
			if (StringUtils.isEmpty(title)) {
				modelMap.put("result", "fail");
				modelMap.put("message", "参数错误");
				return modelMap;
			}
			String partId = request.getParameter("partId");
			if (StringUtils.isEmpty(partId)) {
				modelMap.put("result", "fail");
				modelMap.put("message", "参数错误");
				return modelMap;
			}
			String userId = ((SessionUser) session.getAttribute("user"))
					.getUserId();
			int count = favoriteService.queryFavoriteCountByUserIdAndArticleId(
					userId, articleId_int, type);

			if (count > 0) {
				modelMap.put("result", "fail");
				modelMap.put("message", "此文章你已经收藏");
				return modelMap;
			}
			Favorite favorite = new Favorite();
			favorite.setArticleId(articleId_int);
			favorite.setTitle(title);
			favorite.setType(type);
			favorite.setUserId(userId);
			favorite.setPartId(partId);
			favoriteService.addFavorite(favorite);
			modelMap.put("result", "success");
			return modelMap;
		} catch (Exception e) {
			String errorMethod = "UserAction-->favoriteArticle()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			// thread.start();
			modelMap.put("result", "fail");
			modelMap.put("message", "系统异常");
			return modelMap;
		}
	}
}
