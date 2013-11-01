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
import com.ulewo.service.ReArticleService;
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
	//这里采用注解的方式，获取servicebean
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
	private ReArticleService reArticleService;

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
	 * 分页加载文章
	 * 
	 * @param userName
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/loadArticle.action", method = RequestMethod.POST)
	public Map<String, Object> loadArticle(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			int page = StringUtils.isEmpty(request.getParameter("page")) ? 1 : Integer.parseInt(request
					.getParameter("page"));
			int pageSize = StringUtils.isEmpty(request.getParameter("rows")) ? 20 : Integer.parseInt(request
					.getParameter("rows"));
			String keyWord = request.getParameter("q");
			if (null != keyWord) {
				keyWord = URLDecoder.decode(keyWord, "utf-8");
			}

			PaginationResult result = articleService.queryAllArticleByAdmin(page, pageSize, keyWord);
			modelMap.put("total", result.getCountTotal());
			modelMap.put("rows", result.getList());
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", "fail");
			modelMap.put("message", "系统异常");
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/loadArticle2.action", method = RequestMethod.POST)
	public Map<String, Object> loadArticle2(HttpSession session, HttpServletRequest request) {
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
			// modelMap.put("result", "success");
			modelMap.put("result", result);
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", "fail");
			modelMap.put("message", "系统异常");
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/deleteArticle.action", method = RequestMethod.POST)
	public Map<String, Object> deleteArticle(String keyStr, HttpSession session, HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			articleService.deleteArticleBatch(keyStr);
			modelMap.put("result", "success");
			return modelMap;
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("result", "fail");
			modelMap.put("message", "系统异常");
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/loadReArticle.action", method = RequestMethod.POST)
	public Map<String, Object> loadReArticle(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			int page = StringUtils.isEmpty(request.getParameter("page")) ? 1 : Integer.parseInt(request
					.getParameter("page"));
			int pageSize = StringUtils.isEmpty(request.getParameter("rows")) ? 20 : Integer.parseInt(request
					.getParameter("rows"));
			String keyWord = request.getParameter("q");
			if (null != keyWord) {
				keyWord = URLDecoder.decode(keyWord, "utf-8");
			}
			PaginationResult result = reArticleService.queryAllReArticle(page, pageSize);
			modelMap.put("total", result.getCountTotal());
			modelMap.put("rows", result.getList());
			return modelMap;
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("result", "fail");
			modelMap.put("message", "系统异常");
			return modelMap;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteReArticle.action", method = RequestMethod.POST)
	public Map<String, Object> deleteReArticle(String keyStr, HttpSession session, HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			reArticleService.deleteReArticleBatch(keyStr);
			modelMap.put("result", "success");
			return modelMap;
		} catch (Exception e) {
			e.printStackTrace();
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
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/loadUser.action", method = RequestMethod.POST)
	public Map<String, Object> loadUser(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			int page = StringUtils.isEmpty(request.getParameter("page")) ? 1 : Integer.parseInt(request
					.getParameter("page"));
			int pageSize = StringUtils.isEmpty(request.getParameter("rows")) ? 20 : Integer.parseInt(request
					.getParameter("rows"));
			String userName = request.getParameter("userName");
			if (null != userName) {
				userName = URLDecoder.decode(userName, "utf-8");
			}
			PaginationResult result = userService.findAllUsers(userName, page, pageSize);
			modelMap.put("total", result.getCountTotal());
			modelMap.put("rows", result.getList());
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", "fail");
			modelMap.put("message", "系统异常");
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/deleteUser.action", method = RequestMethod.POST)
	public Map<String, Object> deleteUser(String keyStr, HttpSession session, HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			userService.deleteUserBatch(keyStr);
			modelMap.put("result", "success");
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", "fail");
			modelMap.put("message", "系统异常");
			return modelMap;
		}
	}

	/**
	 * 吐槽
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/loadTalk.action", method = RequestMethod.POST)
	public Map<String, Object> loadTalk(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			int page = StringUtils.isEmpty(request.getParameter("page")) ? 1 : Integer.parseInt(request
					.getParameter("page"));
			int pageSize = StringUtils.isEmpty(request.getParameter("rows")) ? 20 : Integer.parseInt(request
					.getParameter("rows"));
			String keyWord = request.getParameter("q");
			if (null != keyWord) {
				keyWord = URLDecoder.decode(keyWord, "utf-8");
			}
			PaginationResult result = talkService.queryLatestTalkByPag(page, pageSize);
			modelMap.put("total", result.getCountTotal());
			modelMap.put("rows", result.getList());
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", "fail");
			modelMap.put("message", "系统异常");
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/deleteTalk.action", method = RequestMethod.POST)
	public Map<String, Object> deleteTalk(String keyStr, HttpSession session, HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			talkService.deleteTalkBatch(keyStr);
			modelMap.put("result", "success");
			return modelMap;
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("result", "fail");
			modelMap.put("message", "系统异常");
			return modelMap;
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/loadReTalk.action", method = RequestMethod.POST)
	public Map<String, Object> loadReTalk(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			int page = StringUtils.isEmpty(request.getParameter("page")) ? 1 : Integer.parseInt(request
					.getParameter("page"));
			int pageSize = StringUtils.isEmpty(request.getParameter("rows")) ? 20 : Integer.parseInt(request
					.getParameter("rows"));
			String keyWord = request.getParameter("q");
			if (null != keyWord) {
				keyWord = URLDecoder.decode(keyWord, "utf-8");
			}
			PaginationResult result = reTalkService.queryAllReTalkByPag(page, pageSize);
			modelMap.put("total", result.getCountTotal());
			modelMap.put("rows", result.getList());
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", "fail");
			modelMap.put("message", "系统异常");
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/deleteReTalk.action", method = RequestMethod.POST)
	public Map<String, Object> deleteReTalk(String keyStr, HttpSession session, HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			reTalkService.deleteReTalkBatch(keyStr);
			modelMap.put("result", "success");
			return modelMap;
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("result", "fail");
			modelMap.put("message", "系统异常");
			return modelMap;
		}
	}

	/**
	 * 博客
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/loadBlog.action", method = RequestMethod.POST)
	public Map<String, Object> loadBlog(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			int page = StringUtils.isEmpty(request.getParameter("page")) ? 1 : Integer.parseInt(request
					.getParameter("page"));
			int pageSize = StringUtils.isEmpty(request.getParameter("rows")) ? 20 : Integer.parseInt(request
					.getParameter("rows"));
			String keyWord = request.getParameter("q");
			if (null != keyWord) {
				keyWord = URLDecoder.decode(keyWord, "utf-8");
			}
			PaginationResult result = blogArticleService.queryLatestBlog(page, pageSize);
			modelMap.put("total", result.getCountTotal());
			modelMap.put("rows", result.getList());
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", "fail");
			modelMap.put("message", "系统异常");
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/deleteBlog.action", method = RequestMethod.POST)
	public Map<String, Object> deleteBlog(String keyStr, HttpSession session, HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			blogArticleService.deleteBlogBatch(keyStr);
			modelMap.put("result", "success");
			return modelMap;
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("result", "fail");
			modelMap.put("message", "系统异常");
			return modelMap;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/loadReply.action", method = RequestMethod.POST)
	public Map<String, Object> loadReply(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			int page = StringUtils.isEmpty(request.getParameter("page")) ? 1 : Integer.parseInt(request
					.getParameter("page"));
			int pageSize = StringUtils.isEmpty(request.getParameter("rows")) ? 20 : Integer.parseInt(request
					.getParameter("rows"));
			String keyWord = request.getParameter("q");
			if (null != keyWord) {
				keyWord = URLDecoder.decode(keyWord, "utf-8");
			}
			PaginationResult result = blogReplyService.queryAllReplyByPag(page, pageSize);
			modelMap.put("total", result.getCountTotal());
			modelMap.put("rows", result.getList());
			return modelMap;
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("result", "fail");
			modelMap.put("message", "系统异常");
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/deleteReply.action", method = RequestMethod.POST)
	public Map<String, Object> deleteReply(String keyStr, HttpSession session, HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			blogReplyService.deleteReplyBatch(keyStr);
			modelMap.put("result", "success");
			return modelMap;
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("result", "fail");
			modelMap.put("message", "系统异常");
			return modelMap;
		}
	}
}
