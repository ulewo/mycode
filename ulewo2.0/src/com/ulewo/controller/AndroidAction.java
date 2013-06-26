package com.ulewo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ulewo.entity.Article;
import com.ulewo.entity.BlogArticle;
import com.ulewo.entity.BlogReply;
import com.ulewo.entity.Group;
import com.ulewo.entity.ReArticle;
import com.ulewo.entity.ReTalk;
import com.ulewo.entity.SessionUser;
import com.ulewo.entity.Talk;
import com.ulewo.entity.User;
import com.ulewo.enums.QueryUserType;
import com.ulewo.service.ArticleItemService;
import com.ulewo.service.ArticleService;
import com.ulewo.service.BlogArticleService;
import com.ulewo.service.BlogReplyService;
import com.ulewo.service.GroupService;
import com.ulewo.service.MemberService;
import com.ulewo.service.ReArticleService;
import com.ulewo.service.ReTalkService;
import com.ulewo.service.TalkService;
import com.ulewo.service.UserService;
import com.ulewo.util.Constant;
import com.ulewo.util.MySessionContext;
import com.ulewo.util.PaginationResult;
import com.ulewo.util.StringUtils;
import com.ulewo.vo.ArticleVo;
import com.ulewo.vo.BlogReplyVo;
import com.ulewo.vo.BlogVo;
import com.ulewo.vo.GroupVo;
import com.ulewo.vo.ReArticleVo;
import com.ulewo.vo.UserVo;

@Controller
@RequestMapping("/android")
public class AndroidAction {
	@Autowired
	GroupService groupService;

	@Autowired
	ArticleService articleService;

	@Autowired
	BlogArticleService blogArticleService;

	@Autowired
	BlogReplyService blogReplyService;

	@Autowired
	ArticleItemService articleItemService;

	@Autowired
	MemberService memberService;

	@Autowired
	ReArticleService reArticleService;

	@Autowired
	TalkService talkService;

	@Autowired
	ReTalkService reTalkService;

	@Autowired
	UserService userService;

	private final static int MAX_FILE = 1024 * 200;

	private final static int TITLE_LENGTH = 150, KEYWORD_LENGTH = 150;

	/**
	 * 查询最新文章
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/fetchArticle", method = RequestMethod.GET)
	public Map<String, Object> fetchArticle(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String page = request.getParameter("page");
			int page_int = 0;
			if (StringUtils.isNumber(page)) {
				page_int = Integer.parseInt(page);
			}
			PaginationResult result = articleService.queryLatestArticle2PagResult(page_int, Constant.pageSize20);
			List<Article> list = (List<Article>) result.getList();
			List<ArticleVo> resultList = new ArrayList<ArticleVo>();
			ArticleVo vo = null;
			for (Article article : list) {
				vo = new ArticleVo();
				vo.setAuthorId(article.getAuthorId());
				vo.setAuthorName(article.getAuthorName());
				vo.setId(article.getId());
				vo.setPostTime(article.getPostTime());
				vo.setReadNumber(article.getReadNumber());
				vo.setReNumber(article.getReNumber());
				vo.setTitle(article.getTitle());
				resultList.add(vo);
			}
			modelMap.put("list", resultList);
			modelMap.put("pageTotal", result.getPageTotal());
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", "fail");
			e.printStackTrace();
			return modelMap;
		}
	}

	/**
	 * 展示详情
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/showArticle", method = RequestMethod.GET)
	public Map<String, Object> showArticle(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String articleId = request.getParameter("articleId");
			int articleId_int = 0;
			if (StringUtils.isNumber(articleId)) {
				articleId_int = Integer.parseInt(articleId);
			}
			ArticleVo vo = null;
			Article article = articleService.queryTopicById(articleId_int);
			if (null != article) {
				vo = new ArticleVo();
				vo.setAuthorId(article.getAuthorId());
				vo.setAuthorName(article.getAuthorName());
				vo.setContent(StringUtils.pathRelative2Absolutely(article.getContent()));
				vo.setId(article.getId());
				vo.setPostTime(article.getPostTime());
				vo.setReadNumber(article.getReadNumber());
				vo.setReNumber(article.getReNumber());
				vo.setTitle(article.getTitle());
			}
			modelMap.put("article", vo);
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	/**
	 * 查询回复
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/fetchReComment", method = RequestMethod.GET)
	public Map<String, Object> fetchReComment(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String articleId = request.getParameter("articleId");
			String page = request.getParameter("page");
			int page_int = 0;
			int articleId_int = 0;
			if (StringUtils.isNumber(articleId)) {
				articleId_int = Integer.parseInt(articleId);
			}
			if (StringUtils.isNumber(page)) {
				page_int = Integer.parseInt(page);
			}
			PaginationResult result = reArticleService.queryReArticles(articleId_int, page_int, Constant.pageSize10);
			modelMap.put("list", result.getList());
			modelMap.put("pageTotal", result.getPageTotal());
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	/**添加评论
	 * 评论时候先获取session如果获取不到就登录，然后重新设置sesssion.
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addArticleComment", method = RequestMethod.POST)
	public Map<String, Object> addArticleComment(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		ReArticleVo vo = null;
		boolean isLogin = true;
		try {
			String sessionId = request.getParameter("sessionId");
			String userName = request.getParameter("userName");
			String password = request.getParameter("password");
			HttpSession androidsession = MySessionContext.getSession(sessionId);
			String articleId = request.getParameter("articleId");
			int articleId_id = 0;
			if (StringUtils.isNotEmpty(articleId)) {
				articleId_id = Integer.parseInt(articleId);
			}
			String atUserId = request.getParameter("atUserId");
			String pid = request.getParameter("pid");
			int pid_int = 0;
			if (StringUtils.isNumber(pid)) {
				pid_int = Integer.parseInt(pid);
			}
			String content = request.getParameter("content");
			if (androidsession == null) {
				// session失效，重新登录
				androidsession = session;
				if (!login2(session, userName, password)) {
					isLogin = false;
				}
				else {
					sessionId = session.getId();
					MySessionContext.AddSession(session);
				}
			}
			if (isLogin) {
				String gid = "";
				Article article = articleService.queryTopicById(articleId_id);
				if (article != null) {
					gid = article.getGid();
				}
				Object sessionObj = androidsession.getAttribute("user");
				SessionUser sessionUser = (SessionUser) sessionObj;
				ReArticle reArticle = new ReArticle();
				reArticle.setArticleId(articleId_id);
				reArticle.setContent(StringUtils.formateHtml(content));
				reArticle.setGid(gid);
				reArticle.setAuthorid(sessionUser.getUserId());
				reArticle.setAuthorName(sessionUser.getUserName());
				reArticle.setAuthorIcon(sessionUser.getUserLittleIcon());
				reArticle.setAtUserId(atUserId);
				reArticle.setPid(pid_int);
				reArticle.setSourceFrom("A");
				ReArticle re = reArticleService.addReArticle(reArticle);

				vo = new ReArticleVo();
				vo.setArticleId(re.getArticleId());
				vo.setAtUserId(re.getAtUserId());
				vo.setAtUserName(re.getAtUserName());
				vo.setAuthorIcon(re.getAuthorIcon());
				vo.setAuthorid(re.getAuthorid());
				vo.setAuthorName(re.getAuthorName());
				vo.setAtUserId(atUserId);
				vo.setContent(re.getContent());
				vo.setId(re.getId());
				vo.setPid(re.getPid());
				vo.setReTime(re.getReTime());
				vo.setSourceFrom(re.getSourceFrom());
			}
			modelMap.put("reArticle", vo);
			modelMap.put("sessionId", sessionId);
			modelMap.put("isLogin", isLogin);
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	private boolean login2(HttpSession session, String userName, String password) {

		try {
			User userInfo = userService.login(userName, password);
			if (null != userInfo) {
				SessionUser sessionUser = new SessionUser();
				sessionUser.setUserId(userInfo.getUserId());
				sessionUser.setUserName(userInfo.getUserName());
				sessionUser.setUserLittleIcon(userInfo.getUserLittleIcon());
				session.setAttribute("user", sessionUser);
				MySessionContext.AddSession(session);
				return true;
			}
			else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 *	查询博客
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/fetchBlog", method = RequestMethod.GET)
	public Map<String, Object> fetchBlog(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			List<BlogVo> resultList = new ArrayList<BlogVo>();
			String page = request.getParameter("page");
			int page_int = 0;
			if (StringUtils.isNumber(page)) {
				page_int = Integer.parseInt(page);
			}
			PaginationResult result = blogArticleService.queryLatestBlog(page_int, Constant.pageSize20);
			List<BlogArticle> list = (List<BlogArticle>) result.getList();
			BlogVo vo = null;
			for (BlogArticle blog : list) {
				vo = new BlogVo();
				vo.setAuthorId(blog.getUserId());
				vo.setAuthorName(blog.getUserName());
				vo.setId(blog.getId());
				vo.setPostTime(blog.getPostTime());
				vo.setReadNumber(blog.getReadCount());
				vo.setReNumber(blog.getReCount());
				vo.setTitle(blog.getTitle());
				resultList.add(vo);
			}
			modelMap.put("list", resultList);
			modelMap.put("pageTotal", result.getPageTotal());
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/showBlog", method = RequestMethod.GET)
	public Map<String, Object> showBlog(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String articleId = request.getParameter("articleId");
			int articleId_int = 0;
			if (StringUtils.isNumber(articleId)) {
				articleId_int = Integer.parseInt(articleId);
			}
			BlogVo vo = null;
			BlogArticle blog = blogArticleService.queryBlogById(articleId_int);
			vo = new BlogVo();
			vo.setAuthorId(blog.getUserId());
			vo.setAuthorName(blog.getUserName());
			vo.setContent(StringUtils.pathRelative2Absolutely(blog.getContent()));
			vo.setId(blog.getId());
			vo.setPostTime(blog.getPostTime());
			vo.setReadNumber(blog.getReadCount());
			vo.setReNumber(blog.getReCount());
			vo.setTitle(blog.getTitle());
			modelMap.put("blog", vo);
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/fetchBlogComment", method = RequestMethod.GET)
	public Map<String, Object> fetchBlogComment(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			List<BlogReplyVo> resultList = new ArrayList<BlogReplyVo>();
			String articleId = request.getParameter("articleId");
			String page = request.getParameter("page");
			int page_int = 0;
			int articleId_int = 0;
			if (StringUtils.isNumber(articleId)) {
				articleId_int = Integer.parseInt(articleId);
			}
			if (StringUtils.isNumber(page)) {
				page_int = Integer.parseInt(page);
			}
			PaginationResult result = blogReplyService.queryBlogReplyByBlogId(articleId_int, page_int,
					Constant.pageSize10);
			List<BlogReply> reArticleList = (List<BlogReply>) result.getList();
			BlogReplyVo vo = null;
			for (BlogReply re : reArticleList) {
				vo = new BlogReplyVo();
				vo.setArticleId(re.getBlogId());
				vo.setAuthorIcon(Constant.WEBSTIE_IMAGEURL + re.getReUserIcon());
				vo.setAuthorid(re.getUserId());
				vo.setAuthorName(re.getUserName());
				vo.setAtUserId(re.getAtUserId());
				vo.setAtUserName(re.getAtUserName());
				vo.setContent(re.getContent());
				vo.setReTime(re.getPostTime());
				vo.setSourceFrom(re.getSourceFrom());
				resultList.add(vo);
			}
			modelMap.put("list", resultList);
			modelMap.put("pageTotal", result.getPageTotal());
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	/**
	 * 添加博客回复
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addBlogComment", method = RequestMethod.POST)
	public Map<String, Object> addBlogComment(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		BlogReplyVo vo = null;
		boolean isLogin = true;
		try {
			String sessionId = request.getParameter("sessionId");
			String userName = request.getParameter("userName");
			String password = request.getParameter("password");
			HttpSession androidsession = MySessionContext.getSession(sessionId);
			String articleId = request.getParameter("articleId");
			int articleId_id = 0;
			if (StringUtils.isNotEmpty(articleId)) {
				articleId_id = Integer.parseInt(articleId);
			}
			String content = request.getParameter("content");
			if (androidsession == null) {
				// session失效，重新登录
				androidsession = session;
				if (!login2(session, userName, password)) {
					isLogin = false;
				}
				else {
					sessionId = session.getId();
					MySessionContext.AddSession(session);
				}
			}
			if (isLogin) {
				Object sessionObj = androidsession.getAttribute("user");
				SessionUser sessionUser = (SessionUser) sessionObj;
				BlogReply blogReply = new BlogReply();

				blogReply.setBlogId(articleId_id);
				blogReply.setContent(StringUtils.formateHtml(content));
				blogReply.setUserId(sessionUser.getUserId());
				blogReply.setUserName(sessionUser.getUserName());
				blogReply.setReUserIcon(sessionUser.getUserLittleIcon());
				blogReply.setSourceFrom("A");
				BlogReply re = blogReplyService.addReply(blogReply);

				vo = new BlogReplyVo();
				vo.setArticleId(re.getBlogId());
				vo.setContent(content);
				vo.setId(re.getId());
				vo.setAuthorid(re.getUserId());
				vo.setAuthorName(re.getUserName());
				vo.setAuthorIcon(re.getReUserIcon());
				vo.setReTime(re.getPostTime());
				vo.setSourceFrom(re.getSourceFrom());
			}
			modelMap.put("reBlog", vo);
			modelMap.put("sessionId", sessionId);
			modelMap.put("isLogin", isLogin);
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	/**
	 * 查询窝窝
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/fetchWoWo", method = RequestMethod.GET)
	public Map<String, Object> fetchWoWo(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			List<GroupVo> resultList = new ArrayList<GroupVo>();
			String page = request.getParameter("page");
			int page_int = 0;
			if (StringUtils.isNumber(page)) {
				page_int = Integer.parseInt(page);
			}
			PaginationResult result = groupService.queryGroupsOderArticleCount(page_int, Constant.pageSize20);
			List<Group> list = (List<Group>) result.getList();
			GroupVo vo = null;
			for (Group group : list) {
				vo = new GroupVo();
				vo.setgArticleCount(group.getTopicCount());
				vo.setgAuthorId(group.getGroupAuthor());
				vo.setgAuthorName(group.getAuthorName());
				vo.setGid(group.getId());
				vo.setgMember(group.getMembers());
				vo.setgName(group.getGroupName());
				vo.setGroupIcon(Constant.WEBSTIE_IMAGEURL + group.getGroupIcon());
				resultList.add(vo);
			}
			modelMap.put("list", resultList);
			modelMap.put("pageTotal", result.getPageTotal());
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	/**
	 * 窝窝文章
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/fetchArticleByGid", method = RequestMethod.GET)
	public Map<String, Object> fetchArticleByGid(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			List<ArticleVo> resultList = new ArrayList<ArticleVo>();
			String gid = request.getParameter("gid");
			String page = request.getParameter("page");
			int page_int = 0;
			if (StringUtils.isNumber(page)) {
				page_int = Integer.parseInt(page);
			}
			PaginationResult result = articleService.queryTopicOrderByGradeAndLastReTime(gid, 0, page_int,
					Constant.pageSize20);
			List<Article> list = (List<Article>) result.getList();
			ArticleVo vo = null;
			for (Article article : list) {
				vo = new ArticleVo();
				vo.setAuthorId(article.getAuthorId());
				vo.setAuthorName(article.getAuthorName());
				vo.setId(article.getId());
				vo.setPostTime(article.getPostTime());
				vo.setReadNumber(article.getReadNumber());
				vo.setReNumber(article.getReNumber());
				vo.setTitle(article.getTitle());
				resultList.add(vo);
			}
			modelMap.put("list", resultList);
			modelMap.put("pageTotal", result.getPageTotal());
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	/**
	 * 登录
	 * @param session
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Map<String, Object> login(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String userName = request.getParameter("userName");
			String password = request.getParameter("password");

			String loginResult = "success";
			UserVo userVo = null;
			if (StringUtils.isEmpty(userName)) {
				loginResult = "fail";
			}
			User userInfo = userService.login(userName, password);
			if (null != userInfo) {
				SessionUser sessionUser = new SessionUser();
				sessionUser.setUserId(userInfo.getUserId());
				sessionUser.setUserName(userInfo.getUserName());
				sessionUser.setUserLittleIcon(userInfo.getUserLittleIcon());
				session.setAttribute("user", sessionUser);
				MySessionContext.AddSession(session);
				userVo = new UserVo();
				userVo.setUserId(userInfo.getUserId());
				userVo.setUserLittleIcon(Constant.WEBSTIE_IMAGEURL + userInfo.getUserLittleIcon());
				userVo.setUserName(userInfo.getUserName());
				userVo.setWork(userInfo.getWork());
				userVo.setAddress(userInfo.getAddress());
				userVo.setAge(userInfo.getAge());
				userVo.setCharacters(userInfo.getCharacters());
				userVo.setRegisterTime(userInfo.getRegisterTime());
				userVo.setSex(userInfo.getSex());
				userVo.setPrevisitTime(userInfo.getPrevisitTime());
				userVo.setMark(userInfo.getMark());
				userVo.setSessionId(session.getId());
			}
			else {
				loginResult = "fail";
			}
			modelMap.put("user", userVo);
			modelMap.put("loginResult", loginResult);
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	/**
	 * 获取用户信息
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/fetchUserInfo", method = RequestMethod.GET)
	public Map<String, Object> fetchUserInfo(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String userId = request.getParameter("userId");
			UserVo userVo = null;
			User userInfo = userService.findUser(userId, QueryUserType.USERID);
			if (userInfo != null) {
				userVo = new UserVo();
				userVo.setUserLittleIcon(Constant.WEBSTIE_IMAGEURL + userInfo.getUserLittleIcon());
				userVo.setUserId(userInfo.getUserId());
				userVo.setUserName(userInfo.getUserName());
				userVo.setWork(userInfo.getWork());
				userVo.setAddress(userInfo.getAddress());
				userVo.setAge(userInfo.getAge());
				userVo.setCharacters(userInfo.getCharacters());
				userVo.setRegisterTime(userInfo.getRegisterTime());
				userVo.setSex(userInfo.getSex());
				userVo.setPrevisitTime(userInfo.getPrevisitTime());
				userVo.setMark(userInfo.getMark());
			}
			modelMap.put("user", userVo);
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	/**
	 * 获取吐槽
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/fetchTalk", method = RequestMethod.GET)
	public Map<String, Object> fetchTalk(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String page = request.getParameter("page");
			int page_int = 0;
			if (StringUtils.isNumber(page)) {
				page_int = Integer.parseInt(page);
			}
			PaginationResult result = talkService.queryLatestTalkByPag(page_int, Constant.pageSize20);
			List<Talk> resultList = (List<Talk>) result.getList();
			for (Talk talk : resultList) {
				if (StringUtils.isNotEmpty(talk.getImgurl())) {
					talk.setImgurl(Constant.WEBSTIE_IMAGEURL + talk.getImgurl());
				}
				talk.setUserIcon(Constant.WEBSTIE_IMAGEURL + talk.getUserIcon());
				talk.setContent(StringUtils.clearHtml(talk.getContent()));
			}
			modelMap.put("list", resultList);
			modelMap.put("pageTotal", result.getPageTotal());
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	/**
	 * 新增吐槽
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addTalk", method = RequestMethod.POST)
	public Map<String, Object> addTalk(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		boolean isLogin = true;
		try {
			String sessionId = request.getParameter("sessionId");
			String userName = request.getParameter("userName");
			String password = request.getParameter("password");
			HttpSession androidsession = MySessionContext.getSession(sessionId);
			String content = request.getParameter("content");
			String imageUrl = request.getParameter("imageUrl");
			if (androidsession == null) {
				// session失效，重新登录
				androidsession = session;
				if (!login2(session, userName, password)) {
					isLogin = false;
				}
				else {
					sessionId = session.getId();
					MySessionContext.AddSession(session);
				}
			}
			Talk talk = null;
			if (isLogin) {
				Object sessionObj = androidsession.getAttribute("user");
				SessionUser sessionUser = (SessionUser) sessionObj;
				talk = new Talk();
				talk.setContent(content);
				talk.setImgurl(imageUrl);
				talk.setUserId(sessionUser.getUserId());
				talk.setUserName(sessionUser.getUserName());
				talk.setUserIcon(sessionUser.getUserLittleIcon());
				talk.setSourceFrom("A");
				talkService.addTalk(talk);
				talk.setCreateTime(StringUtils.friendly_time(talk.getCreateTime()));
			}
			modelMap.put("talk", talk);
			modelMap.put("sessionId", sessionId);
			modelMap.put("isLogin", isLogin);
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/fetchReTalk", method = RequestMethod.GET)
	public Map<String, Object> fetchReTalk(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String talkId = request.getParameter("talkId");
			String page = request.getParameter("page");
			int talkId_int = 0;
			if (StringUtils.isNumber(talkId)) {
				talkId_int = Integer.parseInt(talkId);
			}
			int page_int = 0;
			if (StringUtils.isNumber(page)) {
				page_int = Integer.parseInt(page);
			}
			PaginationResult result = reTalkService.queryReTalkByPag(page_int, Constant.pageSize20, talkId_int);
			List<ReTalk> list = (List<ReTalk>) result.getList();
			modelMap.put("list", list);
			modelMap.put("pageTotal", result.getPageTotal());
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	/**
	 * 回复吐槽
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addReTalk", method = RequestMethod.POST)
	public Map<String, Object> addReTalk(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		ReTalk retalk = null;
		boolean isLogin = true;
		try {
			String sessionId = request.getParameter("sessionId");
			String userName = request.getParameter("userName");
			String password = request.getParameter("password");
			HttpSession androidsession = MySessionContext.getSession(sessionId);
			String content = request.getParameter("content");
			String talkId = request.getParameter("talkId");
			int talkId_int = 0;
			if (StringUtils.isNumber(talkId)) {
				talkId_int = Integer.parseInt(talkId);
			}
			if (androidsession == null) {
				// session失效，重新登录
				androidsession = session;
				if (!login2(session, userName, password)) {
					isLogin = false;
				}
				else {
					sessionId = session.getId();
					MySessionContext.AddSession(session);
				}
			}
			if (isLogin) {
				Object sessionObj = androidsession.getAttribute("user");
				SessionUser sessionUser = (SessionUser) sessionObj;
				retalk = new ReTalk();
				retalk.setTalkId(talkId_int);
				retalk.setContent(content);
				retalk.setUserId(sessionUser.getUserId());
				retalk.setUserName(sessionUser.getUserName());
				retalk.setUserIcon(sessionUser.getUserLittleIcon());
				retalk.setSourceFrom("A");
				reTalkService.addReTalk(retalk);
				retalk.setCreateTime(StringUtils.friendly_time(retalk.getCreateTime()));
			}
			modelMap.put("retalk", retalk);
			modelMap.put("sessionId", sessionId);
			modelMap.put("isLogin", isLogin);
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	/*
	 * 获取版本信息
	 */
	@ResponseBody
	@RequestMapping(value = "/fetchVersion", method = RequestMethod.GET)
	public Map<String, Object> fetchVersion(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			ResourceBundle rb = ResourceBundle.getBundle("config.config");
			String version = rb.getString("version");
			String app_name = rb.getString("app_name");
			modelMap.put("version", version);
			modelMap.put("app_name", app_name);
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", "fail");
			return modelMap;
		}
	}
}
