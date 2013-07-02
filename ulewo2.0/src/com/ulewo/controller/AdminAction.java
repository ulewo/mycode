package com.ulewo.controller;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.entity.Article;
import com.ulewo.service.ArticleService;
import com.ulewo.service.BlogArticleService;
import com.ulewo.service.BlogItemService;
import com.ulewo.service.BlogReplyService;
import com.ulewo.service.GroupService;
import com.ulewo.service.ReTalkService;
import com.ulewo.service.TalkService;
import com.ulewo.service.UserFriendService;
import com.ulewo.service.UserService;
import com.ulewo.util.Constant;
import com.ulewo.util.PaginationResult;
import com.ulewo.util.StringUtils;

@Controller
@RequestMapping("/admin")
public class AdminAction {
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

	private final static int MAXLENGTH = 250;

	private final static int USERNAME_LENGTH = 20;

	private final static int EMAIL_LENGTH = 100;

	private final static int PWD_MIN_LENGTH = 6;

	private final static int PWD_MAX_LENGTH = 16;

	private final static int MAX_FILE = 1024 * 1024;

	private static final int MAXWIDTH = 600;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView adminIndex(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/index");
		return mv;
	}

	/**
	 *分页加载文章
	 * @param userName
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/loadArticle.action", method = RequestMethod.GET)
	public Map<String, Object> loadArticle(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String page = request.getParameter("page");
			int page_int = 1;
			if (StringUtils.isNumber(page)) {
				page_int = Integer.parseInt(page);
			}
			String keyWord = request.getParameter("q");
			if (null != keyWord) {
				keyWord = URLDecoder.decode(keyWord, "utf-8");
			}
			PaginationResult result = articleService.queryAllArticleByAdmin(page_int, Constant.pageSize25, keyWord);
			//modelMap.put("result", "success");
			modelMap.put("result", result);
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", "fail");
			modelMap.put("message", "系统异常");
			return modelMap;
		}
	}

	@RequestMapping(value = "/weekHot.action", method = RequestMethod.POST)
	public ModelAndView weekHot(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		String[] ids = request.getParameterValues("ids");
		List<Article> list = articleService.getArticleInIds(ids);
		ModelAndView mv = new ModelAndView();
		mv.addObject("list", list);
		mv.setViewName("admin/weekHot");
		return mv;
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ModelAndView adminUser(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/user");
		return mv;
	}

	/**
	 * 查询用户
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/loadUser.action", method = RequestMethod.GET)
	public Map<String, Object> loadUser(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String page = request.getParameter("page");
			int page_int = 1;
			if (StringUtils.isNumber(page)) {
				page_int = Integer.parseInt(page);
			}
			String userName = request.getParameter("userName");
			if (null != userName) {
				userName = URLDecoder.decode(userName, "utf-8");
			}
			PaginationResult result = userService.findAllUsers(userName, page_int, Constant.pageSize25);
			modelMap.put("result", result);
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", "fail");
			modelMap.put("message", "系统异常");
			return modelMap;
		}
	}

}
