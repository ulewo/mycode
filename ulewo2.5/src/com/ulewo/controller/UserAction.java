package com.ulewo.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.enums.PageSize;
import com.ulewo.enums.ResultCode;
import com.ulewo.exception.BusinessException;
import com.ulewo.model.Blog;
import com.ulewo.model.Collection;
import com.ulewo.model.Group;
import com.ulewo.model.SessionUser;
import com.ulewo.model.User;
import com.ulewo.model.UserFriend;
import com.ulewo.service.BlogService;
import com.ulewo.service.CollectionService;
import com.ulewo.service.GroupService;
import com.ulewo.service.UserFriendService;
import com.ulewo.service.UserService;
import com.ulewo.util.Constant;
import com.ulewo.util.ErrorReport;
import com.ulewo.util.SimplePage;
import com.ulewo.util.StringUtils;
import com.ulewo.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserAction extends BaseUserAction {
	@Autowired
	private UserService userService;

	@Autowired
	private BlogService blogService;

	@Autowired
	private UserFriendService userFriendService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private CollectionService collectionService;

	private Logger log = LoggerFactory.getLogger(UserAction.class);

	/**
	 * 注册
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/register.do", method = RequestMethod.POST)
	public Map<String, Object> register(HttpSession session,
			HttpServletRequest request, Model model,
			HttpServletResponse response) throws UnsupportedEncodingException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Integer userId = 0;
		try {
			User user = userService.register(builderParams(request, true),
					String.valueOf(session.getAttribute("checkCode")));
			// 保存Cookie
			String infor = URLEncoder.encode(user.getUserId().toString(),
					"utf-8") + "," + user.getPassword();
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
			sessionUser.setUserId(user.getUserId());
			sessionUser.setUserName(user.getUserName());
			sessionUser.setUserIcon(user.getUserIcon());
			session.setAttribute("user", sessionUser);
			userId = user.getUserId();
			modelMap.put("userId", userId);
			modelMap.put("code", ResultCode.SUCCESS.getCode());
			return modelMap;
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			modelMap.put("msg", e.getMessage());
			modelMap.put("code", ResultCode.ERROR.getCode());
			return modelMap;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("msg", "系统异常");
			modelMap.put("code", ResultCode.ERROR.getCode());
			return modelMap;
		}

	}

	/**
	 * 登陆
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public Map<String, Object> login(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> parms = builderParams(request, true);
			User user = userService.login(parms,
					String.valueOf(session.getAttribute("checkCode")));
			if ("Y".equals(parms.get("autoLogin"))) {
				// 自动登录，保存用户名密码到 Cookie
				String infor = URLEncoder.encode(parms.get("account")
						.toString(), "utf-8")
						+ "," + user.getPassword();

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
			sessionUser.setUserIcon(user.getUserIcon());
			session.setAttribute("user", sessionUser);
			// 更新最后登录时间
			User loginUser = new User();
			loginUser.setUserId(user.getUserId());
			loginUser.setPreVisitTime(StringUtils.dateFormater
					.format(new Date()));
			userService.updateSelective(loginUser);
			modelMap.put("code", ResultCode.SUCCESS.getCode());
			return modelMap;
		} catch (BusinessException e) {
			log.error(e.getMessage());
			modelMap.put("msg", e.getMessage());
			modelMap.put("code", ResultCode.ERROR.getCode());
			return modelMap;
		} catch (Exception e) {
			log.error(e.getMessage());
			modelMap.put("msg", "系统异常");
			modelMap.put("code", ResultCode.ERROR.getCode());
			return modelMap;
		}

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
		modelMap.put("code", ResultCode.SUCCESS.getCode());
		return modelMap;
	}

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
	public Map<String, Object> sendRestPwd(HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> parms = builderParams(request, true);
			User user = userService.sendRestPwd(parms,
					String.valueOf(session.getAttribute("checkCode")));
			modelMap.put("address", user.getEmail());
			modelMap.put("result", ResultCode.SUCCESS.getCode());
			return modelMap;
		} catch (BusinessException e) {
			modelMap.put("result", ResultCode.ERROR.getCode());
			modelMap.put("msg", e.getMessage());
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", ResultCode.ERROR.getCode());
			modelMap.put("msg", "系统异常");
			return modelMap;
		}
	}

	/**
	 * 重置密码跳转页
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
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
			Map<String, String> parms = builderParams(request, true);
			userService.resetPwd(parms,
					String.valueOf(session.getAttribute("checkCode")));
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
			modelMap.put("message", "系统异常");
			return modelMap;
		}

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
				return mv;
			}
			Integer userId_int = userVo.getUserId();
			mv.addObject("userVo", userVo);
			SimplePage page = new SimplePage();
			page.setStart(0);
			page.setEnd(PageSize.SIZE5.getSize());
			Map<String, String> map = this.builderParams(request, true);
			map.put("userId", String.valueOf(userId));
			List<Blog> list = blogService.queryBlogByUserId4List(map);
			page.setEnd(PageSize.SIZE15.getSize());
			List<UserFriend> fansList = userFriendService
					.queryFans4List(userId_int);
			List<UserFriend> focusList = userFriendService
					.queryFocus4List(userId_int);
			List<Group> createdGroups = groupService.findCreatedGroups(
					userId_int, page);
			List<Group> joinedGroups = groupService.findJoinedGroups(
					userId_int, page);
			mv.addObject("focusList", focusList);
			mv.addObject("fansList", fansList);
			mv.addObject("bloglist", list);
			mv.addObject("createdGroups", createdGroups);
			mv.addObject("joinedGroups", joinedGroups);
			mv.setViewName("/user/userhome");
		} catch (BusinessException e) {
			log.error(e.getMessage());
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		} catch (Exception e) {
			log.error(e.getMessage());
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
			Integer userId = this.getSessionUserId(session);
			List<UserFriend> focusList = userFriendService
					.queryFocus4List(userId);
			List<UserFriend> fansList = userFriendService
					.queryFans4List(userId);
			modelMap.put("focusList", focusList);
			modelMap.put("fansList", fansList);
			modelMap.put("result", ResultCode.SUCCESS.getCode());
			return modelMap;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("result", ResultCode.ERROR.getCode());
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
			Integer userId = this.getSessionUserId(session);
			SimplePage page = new SimplePage();
			page.setStart(0);
			page.setEnd(PageSize.SIZE15.getSize());
			List<Group> createdGroups = groupService.findCreatedGroups(userId,
					page);
			List<Group> joinedGroups = groupService.findJoinedGroups(userId,
					page);
			modelMap.put("createdGroups", createdGroups);
			modelMap.put("joinedGroups", joinedGroups);
			modelMap.put("result", ResultCode.SUCCESS.getCode());
			return modelMap;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("result", ResultCode.ERROR.getCode());
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/focusFriend.action", method = RequestMethod.POST)
	public Map<String, Object> focusFriend(HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			this.userFriendService.addFriend(this.builderParams(request, true),
					this.getSessionUserId(session));
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

	@ResponseBody
	@RequestMapping(value = "/cancelFocus.action", method = RequestMethod.POST)
	public Map<String, Object> cancelFocus(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			this.userFriendService.deleteFirend(
					this.builderParams(request, true),
					this.getSessionUserId(session));
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
			Map<String, String> map = builderParams(request, true);
			Collection collection = collectionService.articleCollectionInfo(
					map, this.getSessionUser(session));
			modelMap.put("haveFavoriteCount", collection.getCollectionCount());
			modelMap.put("haveFavorite", collection.isHaveCollection());
			modelMap.put("result", ResultCode.SUCCESS.getCode());
			return modelMap;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("result", ResultCode.ERROR.getCode());
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/favoriteArticle.action", method = RequestMethod.POST)
	public Map<String, Object> favoriteArticle(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> modelMap = new HashMap<String, Object>();

		try {
			Map<String, String> map = builderParams(request, true);
			this.collectionService.addCollection(map,
					this.getSessionUser(session));
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

	@ResponseBody
	@RequestMapping(value = "/loadUserInfo.action")
	public Map<String, Object> loadUserInfo(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Integer userId = this.getSessionUserId(session);
			List<UserFriend> focusList = userFriendService
					.queryFocus4List(userId);
			List<UserFriend> fansList = userFriendService
					.queryFans4List(userId);
			UserVo userVo = checkUserInfo(userId.toString(), session);
			modelMap.put("userVo", userVo);
			modelMap.put("focusList", focusList);
			modelMap.put("fansList", fansList);
			modelMap.put("result", ResultCode.SUCCESS.getCode());
			return modelMap;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("result", ResultCode.ERROR.getCode());
			return modelMap;
		}
	}
}
