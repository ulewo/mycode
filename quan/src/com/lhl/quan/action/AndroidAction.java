package com.lhl.quan.action;

import java.util.ArrayList;
import java.util.List;

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
import com.lhl.entity.Response;
import com.lhl.entity.User;
import com.lhl.quan.service.ArticleService;
import com.lhl.quan.service.BlogArticleService;
import com.lhl.quan.service.BlogReplyService;
import com.lhl.quan.service.GroupService;
import com.lhl.quan.service.ReArticleService;
import com.lhl.quan.service.UserService;
import com.lhl.util.Constant;
import com.lhl.util.MySessionContext;
import com.lhl.util.Pagination;
import com.lhl.util.Tools;
import com.lhl.vo.ArticleVo;
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

	private int pid;

	private static final int RESULTCODE_SUCCESS = 200;

	private static final int RESULTCODE_FAIL = 400;

	private static final int RESULTCODE_LOGINFAILL = 100;

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
			vo.setPostTime(Tools.friendly_time(article.getPostTime()));
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
				vo.setContent(article.getContent());
				vo.setId(article.getId());
				vo.setPostTime(Tools.friendly_time(article.getPostTime()));
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
			vo.setAuthorIcon(reArticle.getAuthorIcon());
			vo.setAuthorid(reArticle.getAuthorid());
			vo.setAuthorName(reArticle.getAuthorName());
			vo.setContent(reArticle.getContent());
			vo.setId(reArticle.getId());
			vo.setPid(reArticle.getPid());
			vo.setReTime(Tools.friendly_time(reArticle.getReTime()));
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
				if (!login2(session, userName, password)) {
					isLogin = false;
				} else {
					sessionId = session.getId();
					MySessionContext.AddSession(session);
				}
			} else {
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
				vo.setReTime(Tools.friendly_time(re.getReTime()));
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
				session = getSession();
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
				vo.setPostTime(Tools.friendly_time(blog.getPostTime()));
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
			vo.setContent(blog.getContent());
			vo.setId(blog.getId());
			vo.setPostTime(Tools.friendly_time(blog.getPostTime()));
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

		int resultCode = RESULTCODE_SUCCESS;
		List<BlogReply> list = null;
		try {
			list = blogReplyService.queryBlogReplyByBlogId(articleId);
		} catch (Exception e) {
			resultCode = RESULTCODE_FAIL;
			e.printStackTrace();
		}
		JSONObject obj = new JSONObject();
		Response response = new Response(resultCode, pageTotal, list);
		obj.put("response", response);
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
				vo.setGroupIcon(group.getGroupIcon());
				resultList.add(vo);
			}
			for (Group group : list) {
				group.setGroupDesc("");
				group.setGroupHeadIcon("");
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
				vo.setPostTime(Tools.friendly_time(article.getPostTime()));
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
				userVo.setUserName(userInfo.getUserName());
				userVo.setWork(userInfo.getWork());
				userVo.setAddress(userInfo.getAddress());
				userVo.setAge(userInfo.getAge());
				userVo.setCharacters(userInfo.getCharacters());
				userVo.setRegisterTime(Tools.friendly_time(userInfo
						.getRegisterTime()));
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
				userVo.setUserLittleIcon(userInfo.getUserLittleIcon());
				userVo.setUserId(userInfo.getUserId());
				userVo.setUserName(userInfo.getUserName());
				userVo.setWork(userInfo.getWork());
				userVo.setAddress(userInfo.getAddress());
				userVo.setAge(userInfo.getAge());
				userVo.setCharacters(userInfo.getCharacters());
				userVo.setRegisterTime(Tools.friendly_time(userInfo
						.getRegisterTime()));
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

	public void fetchVersion() {
		JSONObject obj = new JSONObject();
		obj.put("version", Constant.VERSION);
		getOut().print(String.valueOf(obj));
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
}
