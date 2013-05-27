package com.lhl.quan.action;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.jsoup.helper.StringUtil;

import com.lhl.admin.service.AdminArticleService;
import com.lhl.common.action.BaseAction;
import com.lhl.entity.Article;
import com.lhl.entity.BlogArticle;
import com.lhl.entity.BlogReply;
import com.lhl.entity.Group;
import com.lhl.entity.ReArticle;
import com.lhl.entity.ReTalk;
import com.lhl.entity.Talk;
import com.lhl.entity.User;
import com.lhl.quan.service.ArticleService;
import com.lhl.quan.service.BlogArticleService;
import com.lhl.quan.service.BlogReplyService;
import com.lhl.quan.service.GroupService;
import com.lhl.quan.service.ReArticleService;
import com.lhl.quan.service.ReTalkService;
import com.lhl.quan.service.TalkService;
import com.lhl.quan.service.UserService;
import com.lhl.util.Constant;
import com.lhl.util.MySessionContext;
import com.lhl.util.Pagination;
import com.lhl.util.Tools;
import com.lhl.vo.ArticleVo;
import com.lhl.vo.BlogReplyVo;
import com.lhl.vo.BlogVo;
import com.lhl.vo.GroupVo;
import com.lhl.vo.ReArticleVo;
import com.lhl.vo.UserVo;

public class AndroidAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	private AdminArticleService adminArticleService;

	private ArticleService articleService;

	private GroupService groupService;

	private UserService userService;

	private ReArticleService reArticleService;

	private BlogArticleService blogArticleService;

	private BlogReplyService blogReplyService;

	private TalkService talkservice;

	private ReTalkService reTalkService;

	private int page;

	private int articleId;

	private int pageTotal;

	private String gid;

	private String userName;

	private String password;

	private String userId;

	private String atUserId;

	private String content;

	private String sessionId;

	private int talkId;

	private int pid;

	private static final int RESULTCODE_SUCCESS = 200;

	private static final int RESULTCODE_FAIL = 400;

	private static final int RESULTCODE_LOGINFAILL = 100;

	private String imageUrl;

	/**
	 * 
	 * description: 查询最新的文章
	 * 
	 * @author luohl
	 */
	public void fetchArticle() {

		int countNumber = adminArticleService.queryArticleCount("",
				Constant.ISVALIDY);
		Pagination.setPageSize(Constant.pageSize20);
		int pageSize = Pagination.getPageSize();
		pageTotal = Pagination.getPageTotal(countNumber);
		if (page > pageTotal) {
			page = pageTotal;
		}
		if (page < 1) {
			page = 1;
		}
		int noStart = (page - 1) * pageSize;
		List<Article> list = adminArticleService.queryList("",
				Constant.ISVALIDY, noStart, pageSize);
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
		JSONObject obj = new JSONObject();
		obj.put("list", resultList);
		obj.put("pageTotal", pageTotal);
		getOut().print(String.valueOf(obj));
	}

	/**
	 * 
	 * description: 展示文章
	 * 
	 * @author luohl
	 */
	public void showArticle() {

		ArticleVo vo = null;
		try {
			Article article = articleService.queryTopicById(articleId);
			if (null != article) {
				vo = new ArticleVo();
				vo.setAuthorId(article.getAuthorId());
				vo.setAuthorName(article.getAuthorName());
				vo.setContent(Tools.pathRelative2Absolutely(article
						.getContent()));
				vo.setId(article.getId());
				vo.setPostTime(article.getPostTime());
				vo.setReadNumber(article.getReadNumber());
				vo.setReNumber(article.getReNumber());
				vo.setTitle(article.getTitle());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject obj = new JSONObject();
		obj.put("article", vo);
		getOut().print(String.valueOf(obj));
	}

	/**
	 * 
	 * description: 查询回复
	 * 
	 * @author luohl
	 */
	public void fetchReComment() {

		List<ReArticle> reArticleList = null;
		int pageSize = 10;
		try {
			int countNumber = reArticleService.queryReArticleCount(articleId);
			reArticleList = reArticleService.queryReArticles(articleId, 0,
					countNumber);

			Pagination.setPageSize(Constant.pageSize10);
			pageSize = Pagination.getPageSize();
			pageTotal = Pagination.getPageTotal(reArticleList.size());
			if (page > pageTotal) {
				page = pageTotal;
			}
			if (page < 1) {
				page = 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject obj = new JSONObject();
		List<ReArticleVo> resultList = getListbyPageNum(page, reArticleList,
				pageSize);
		obj.put("list", resultList);
		obj.put("pageTotal", pageTotal);
		getOut().print(String.valueOf(obj));
	}

	private List<ReArticleVo> getListbyPageNum(int page,
			List<ReArticle> reArticleList, int pageSize) {

		List<ReArticleVo> resultList = new ArrayList<ReArticleVo>();
		int count = reArticleList.size();
		int start = (page - 1) * pageSize;
		int offset = page * pageSize;
		if (offset > count) {
			offset = count;
		}
		ReArticleVo vo = null;
		ReArticle reArticle = null;
		for (int i = start; i < offset; i++) {
			reArticle = reArticleList.get(i);
			vo = new ReArticleVo();
			vo.setArticleId(reArticle.getArticleId());
			vo.setAtUserId(reArticle.getAtUserId());
			vo.setAtUserName(reArticle.getAtUserName());
			vo.setAuthorIcon(Constant.WEBSTIE_IMAGEURL
					+ reArticle.getAuthorIcon());
			vo.setAuthorid(reArticle.getAuthorid());
			vo.setAuthorName(reArticle.getAuthorName());
			vo.setContent(Tools.pathRelative2Absolutely(reArticle.getContent()));
			vo.setId(reArticle.getId());
			vo.setPid(reArticle.getPid());
			vo.setReTime(reArticle.getReTime());
			vo.setSourceFrom(reArticle.getSourceFrom());
			vo.setChildList(reArticle.getChildList());
			resultList.add(vo);
		}
		return resultList;
	}

	/**
	 * 
	 * description: 评论时候先获取session如果获取不到就登录，然后重新设置sesssion.
	 * 
	 * @author luohl
	 */

	public void addArticleComment() {

		ReArticleVo vo = null;
		boolean isLogin = true;
		try {
			HttpSession session = MySessionContext.getSession(sessionId);
			if (session == null) {
				// session失效，重新登录
				session = getSession();
				if (!login2(session, userName, password)) {
					isLogin = false;
				} else {
					sessionId = session.getId();
					MySessionContext.AddSession(session);
				}
			}
			if (isLogin) {
				Object sessionObj = session.getAttribute("user");
				User sessionUser = (User) sessionObj;
				ReArticle reArticle = new ReArticle();
				reArticle.setArticleId(articleId);
				reArticle.setContent(Tools.formateHtml(content));
				reArticle.setGid(gid);
				reArticle.setAuthorid(sessionUser.getUserId());
				reArticle.setAuthorName(sessionUser.getUserName());
				reArticle.setAuthorIcon(sessionUser.getUserLittleIcon());
				reArticle.setAtUserId(atUserId);
				reArticle.setPid(pid);
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

		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject obj = new JSONObject();
		obj.put("reArticle", vo);
		obj.put("sessionId", sessionId);
		obj.put("isLogin", isLogin);
		getOut().print(String.valueOf(obj));
	}

	private boolean login2(HttpSession session, String userName, String password) {

		try {
			User userInfo = userService.login(userName);
			if (null != userInfo && password.equals(userInfo.getPassword())) {
				User loginUser = new User();
				loginUser.setUserId(userInfo.getUserId());
				loginUser.setUserName(userName);
				loginUser.setUserLittleIcon(userInfo.getUserLittleIcon());
				getSession().setAttribute("user", loginUser);
				MySessionContext.AddSession(session);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 
	 * description: 查询博客
	 * 
	 * @author luohl
	 */
	public void fetchBlog() {

		List<BlogVo> resultList = new ArrayList<BlogVo>();
		try {
			int countNumber = blogArticleService.queryCount();
			Pagination.setPageSize(Constant.pageSize20);
			int pageSize = Pagination.getPageSize();
			pageTotal = Pagination.getPageTotal(countNumber);
			if (page > pageTotal) {
				page = pageTotal;
			}
			if (page < 1) {
				page = 1;
			}
			int noStart = (page - 1) * pageSize;
			List<BlogArticle> list = blogArticleService.indexLatestBlog(
					noStart, pageSize);
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
		} catch (Exception e) {

		}
		JSONObject obj = new JSONObject();
		obj.put("list", resultList);
		obj.put("pageTotal", pageTotal);
		getOut().print(String.valueOf(obj));
	}

	public void showBlog() {

		BlogVo vo = null;
		try {
			BlogArticle blog = blogArticleService.queryBlogById(articleId);
			vo = new BlogVo();
			vo.setAuthorId(blog.getUserId());
			vo.setAuthorName(blog.getUserName());
			vo.setContent(Tools.pathRelative2Absolutely(blog.getContent()));
			vo.setId(blog.getId());
			vo.setPostTime(blog.getPostTime());
			vo.setReadNumber(blog.getReadCount());
			vo.setReNumber(blog.getReCount());
			vo.setTitle(blog.getTitle());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject obj = new JSONObject();
		obj.put("blog", vo);
		getOut().print(String.valueOf(obj));
	}

	public void fetchBlogComment() {

		List<BlogReplyVo> resultList = new ArrayList<BlogReplyVo>();
		int pageSize = 10;
		try {
			int countNumber = blogReplyService
					.queryBlogReplyCountByBlogId(articleId);

			Pagination.setPageSize(Constant.pageSize50);
			pageTotal = Pagination.getPageTotal(countNumber);
			/*
			 * if (page > pageTotal) { page = pageTotal; } if (page < 1) { page
			 * = 1; } int noStart = (page - 1) * pageSize;
			 */
			List<BlogReply> reArticleList = blogReplyService
					.queryBlogReplyByBlogId(articleId);
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

		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject obj = new JSONObject();
		obj.put("list", resultList);
		obj.put("pageTotal", pageTotal);
		getOut().print(String.valueOf(obj));
	}

	public void addBlogComment() {

		BlogReplyVo vo = null;
		boolean isLogin = true;
		try {
			HttpSession session = MySessionContext.getSession(sessionId);
			if (session == null) {
				// session失效，重新登录
				if (!login2(session, userName, password)) {
					isLogin = false;
				} else {
					sessionId = session.getId();
					MySessionContext.AddSession(session);
				}
			}
			if (isLogin) {
				Object sessionObj = session.getAttribute("user");
				User sessionUser = (User) sessionObj;
				BlogReply blogReply = new BlogReply();

				blogReply.setBlogId(articleId);
				blogReply.setContent(Tools.formateHtml(content));
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject obj = new JSONObject();
		obj.put("reBlog", vo);
		obj.put("sessionId", sessionId);
		obj.put("isLogin", isLogin);
		getOut().print(String.valueOf(obj));
	}

	/**
	 * 
	 * description: 查询窝窝
	 * 
	 * @author luohl
	 */
	public void fetchWoWo() {

		List<GroupVo> resultList = new ArrayList<GroupVo>();
		try {
			int countNumber = groupService.queryGroupsCount();
			Pagination.setPageSize(Constant.pageSize20);
			int pageSize = Pagination.getPageSize();
			pageTotal = Pagination.getPageTotal(countNumber);
			if (page > pageTotal) {
				page = pageTotal;
			}
			if (page < 1) {
				page = 1;
			}
			int noStart = (page - 1) * pageSize;
			List<Group> list = groupService.queryGroupsOderArticleCount(
					noStart, pageSize);
			GroupVo vo = null;
			for (Group group : list) {
				vo = new GroupVo();
				vo.setgArticleCount(group.getTopicCount());
				vo.setgAuthorId(group.getGroupAuthor());
				vo.setgAuthorName(group.getAuthorName());
				vo.setGid(group.getId());
				vo.setgMember(group.getMembers());
				vo.setgName(group.getGroupName());
				vo.setGroupIcon(Constant.WEBSTIE_IMAGEURL
						+ group.getGroupIcon());
				resultList.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject obj = new JSONObject();
		obj.put("list", resultList);
		obj.put("pageTotal", pageTotal);
		getOut().print(String.valueOf(obj));
	}

	public void fetchArticleByGid() {

		List<ArticleVo> resultList = new ArrayList<ArticleVo>();
		try {
			int countNumber = articleService.queryTopicCountByGid(gid, 0,
					Constant.ISVALIDY);
			Pagination.setPageSize(Constant.pageSize20);
			int pageSize = Pagination.getPageSize();
			pageTotal = Pagination.getPageTotal(countNumber);
			if (page > pageTotal) {
				page = pageTotal;
			}
			if (page < 1) {
				page = 1;
			}
			int noStart = (page - 1) * pageSize;
			List<Article> list = articleService.queryTopicOrderByPostTime(gid,
					0, Constant.ISVALIDY, noStart, pageSize);
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject obj = new JSONObject();
		obj.put("list", resultList);
		obj.put("pageTotal", pageTotal);
		getOut().print(String.valueOf(obj));
	}

	/**
	 * 
	 * description: 登录
	 * 
	 * @author luohl
	 */
	public void login() {

		String loginResult = "success";
		UserVo userVo = null;
		try {
			if (StringUtil.isBlank(userName)) {
				loginResult = "fail";
			}
			User userInfo = userService.login(userName);
			if (null != userInfo && password.equals(userInfo.getPassword())) {
				HttpSession session = getSession();
				User loginUser = new User();
				loginUser.setUserId(userInfo.getUserId());
				loginUser.setUserName(userName);
				loginUser.setUserLittleIcon(userInfo.getUserLittleIcon());
				getSession().setAttribute("user", loginUser);
				MySessionContext.AddSession(session);

				userVo = new UserVo();
				userVo.setUserId(userInfo.getUserId());
				userVo.setUserLittleIcon(Constant.WEBSTIE_IMAGEURL
						+ userInfo.getUserLittleIcon());
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
			} else {
				loginResult = "fail";
			}
		} catch (Exception e) {
			loginResult = "fail";
			e.printStackTrace();
		}
		JSONObject obj = new JSONObject();
		obj.put("user", userVo);
		obj.put("loginResult", loginResult);
		getOut().print(String.valueOf(obj));
	}

	public void fetchUserInfo() {

		UserVo userVo = null;
		try {
			User userInfo = userService.getUserInfo(userId);
			if (userInfo != null) {
				userVo = new UserVo();
				userVo.setUserLittleIcon(Constant.WEBSTIE_IMAGEURL
						+ userInfo.getUserLittleIcon());
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject obj = new JSONObject();
		obj.put("user", userVo);
		getOut().print(String.valueOf(obj));
	}

	/**
	 * 查询吐槽
	 */
	public void fetchTalk() {

		List<Talk> resultList = new ArrayList<Talk>();
		try {
			int countNumber = talkservice.queryTalkCount();
			Pagination.setPageSize(Constant.pageSize20);
			int pageSize = Pagination.getPageSize();
			pageTotal = Pagination.getPageTotal(countNumber);
			if (page > pageTotal) {
				page = pageTotal;
			}
			if (page < 1) {
				page = 1;
			}
			int noStart = (page - 1) * pageSize;
			resultList = talkservice.queryLatestTalk(noStart, pageSize);
			for (Talk talk : resultList) {
				if (Tools.isNotEmpty(talk.getImgurl())) {
					talk.setImgurl(Constant.WEBSTIE_IMAGEURL + talk.getImgurl());
				}
				talk.setUserIcon(Constant.WEBSTIE_IMAGEURL + talk.getUserIcon());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject obj = new JSONObject();
		obj.put("list", resultList);
		obj.put("pageTotal", pageTotal);
		getOut().print(String.valueOf(obj));
	}

	public void addTalk() {

		Talk talk = null;
		boolean isLogin = true;
		try {
			HttpSession session = MySessionContext.getSession(sessionId);
			if (session == null) {
				session = getSession();
				// session失效，重新登录
				if (!login2(session, userName, password)) {
					isLogin = false;
				} else {
					sessionId = session.getId();
					MySessionContext.AddSession(session);
				}
			}
			if (isLogin) {
				Object sessionObj = session.getAttribute("user");
				User sessionUser = (User) sessionObj;
				talk = new Talk();
				talk.setContent(content);
				talk.setImgurl(imageUrl);
				talk.setUserId(sessionUser.getUserId());
				talk.setUserName(sessionUser.getUserName());
				talk.setUserIcon(sessionUser.getUserLittleIcon());
				talk.setSourceFrom("A");
				talkservice.addTalk(talk);
				talk.setCreateTime(Tools.friendly_time(talk.getCreateTime()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject obj = new JSONObject();
		obj.put("talk", talk);
		obj.put("sessionId", sessionId);
		obj.put("isLogin", isLogin);
		getOut().print(String.valueOf(obj));
	}

	/**
	 * 吐槽详情
	 */
	public void fetchReTalk() {

		List<ReTalk> list = new ArrayList<ReTalk>();
		try {
			int countNumber = reTalkService.queryReTalkCount(talkId);
			Pagination.setPageSize(Constant.pageSize20);
			int pageSize = Pagination.getPageSize();
			pageTotal = Pagination.getPageTotal(countNumber);
			if (page > pageTotal) {
				page = pageTotal;
			}
			if (page < 1) {
				page = 1;
			}
			int noStart = (page - 1) * pageSize;
			list = reTalkService.queryReTalk(noStart, pageSize, talkId);
			for (ReTalk talk : list) {
				talk.setUserIcon(Constant.WEBSTIE_IMAGEURL + talk.getUserIcon());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject obj = new JSONObject();
		obj.put("list", list);
		obj.put("pageTotal", pageTotal);
		getOut().print(String.valueOf(obj));
	}

	public void addReTalk() {

		ReTalk retalk = null;
		boolean isLogin = true;
		try {
			HttpSession session = MySessionContext.getSession(sessionId);
			if (session == null) {
				session = getSession();
				// session失效，重新登录
				if (!login2(session, userName, password)) {
					isLogin = false;
				} else {
					sessionId = session.getId();
					MySessionContext.AddSession(session);
				}
			}
			if (isLogin) {
				Object sessionObj = session.getAttribute("user");
				User sessionUser = (User) sessionObj;
				retalk = new ReTalk();
				retalk.setTalkId(talkId);
				retalk.setContent(content);
				retalk.setUserId(sessionUser.getUserId());
				retalk.setUserName(sessionUser.getUserName());
				retalk.setUserIcon(sessionUser.getUserLittleIcon());
				retalk.setSourceFrom("A");
				reTalkService.addReTalk(retalk);
				retalk.setCreateTime(Tools.friendly_time(retalk.getCreateTime()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject obj = new JSONObject();
		obj.put("retalk", retalk);
		obj.put("sessionId", sessionId);
		obj.put("isLogin", isLogin);
		getOut().print(String.valueOf(obj));
	}

	public void fetchVersion() {

		JSONObject obj = new JSONObject();
		try {
			ResourceBundle rb = ResourceBundle.getBundle("config.config");
			String version = rb.getString("version");
			String app_name = rb.getString("app_name");
			obj.put("version", version);
			obj.put("app_name", app_name);
			getOut().print(String.valueOf(obj));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setArticleService(ArticleService articleService) {

		this.articleService = articleService;
	}

	public void setBlogReplyService(BlogReplyService blogReplyService) {

		this.blogReplyService = blogReplyService;
	}

	public void setGroupService(GroupService groupService) {

		this.groupService = groupService;
	}

	public void setUserService(UserService userService) {

		this.userService = userService;
	}

	public void setBlogArticleService(BlogArticleService blogArticleService) {

		this.blogArticleService = blogArticleService;
	}

	public void setAdminArticleService(AdminArticleService adminArticleService) {

		this.adminArticleService = adminArticleService;
	}

	public void setReArticleService(ReArticleService reArticleService) {

		this.reArticleService = reArticleService;
	}

	public void setGid(String gid) {

		this.gid = gid;
	}

	public int getPage() {

		return page;
	}

	public void setPage(int page) {

		this.page = page;
	}

	public void setArticleId(int articleId) {

		this.articleId = articleId;
	}

	public int getPageTotal() {

		return pageTotal;
	}

	public void setUserName(String userName) {

		this.userName = userName;
	}

	public void setPassword(String password) {

		this.password = password;
	}

	public void setUserId(String userId) {

		this.userId = userId;
	}

	public void setReContent(String content) {

		this.content = content;
	}

	public void setSessionId(String sessionId) {

		this.sessionId = sessionId;
	}

	public void setContent(String content) {

		this.content = content;
	}

	public void setAtUserId(String atUserId) {

		this.atUserId = atUserId;
	}

	public void setPid(int pid) {

		this.pid = pid;
	}

	public void setTalkservice(TalkService talkservice) {

		this.talkservice = talkservice;
	}

	public void setReTalkService(ReTalkService reTalkService) {

		this.reTalkService = reTalkService;
	}

	public void setImageUrl(String imageUrl) {

		this.imageUrl = imageUrl;
	}

	public void setTalkId(int talkId) {
		this.talkId = talkId;
	}

}
