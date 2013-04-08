package com.lhl.quan.action;

import java.util.List;

import net.sf.json.JSONObject;

import com.lhl.UserVo;
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
import com.lhl.util.Pagination;
import com.lhl.util.Tools;

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

	private static final int RESULTCODE_SUCCESS = 200;

	private static final int RESULTCODE_FAIL = 400;

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
		for (Article article : list) {
			article.setSummary("");
			article.setPostTime(article.getPostTime().substring(0, 19));
		}
		try {
			JSONObject obj = new JSONObject();
			Response response = new Response(RESULTCODE_SUCCESS, null, list);
			obj.put("response", response);
			getOut().print(String.valueOf(obj));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * description: 展示文章
	 * 
	 * @author luohl
	 */
	public void showArticle() {

		int resultCode = RESULTCODE_SUCCESS;
		Article article = null;
		try {
			article = articleService.queryTopicById(articleId);
		} catch (Exception e) {
			resultCode = RESULTCODE_FAIL;
			e.printStackTrace();
		}
		JSONObject obj = new JSONObject();
		Response response = new Response(resultCode, article, null);
		obj.put("response", response);
		getOut().print(String.valueOf(obj));
	}

	/**
	 * 
	 * description: 查询回复
	 * 
	 * @author luohl
	 */
	public void fetchReComment() {

		int resultCode = RESULTCODE_SUCCESS;
		List<ReArticle> reArticleList = null;
		try {
			int countNumber = reArticleService.queryReArticleCount(articleId);
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
			reArticleList = reArticleService.queryReArticles(articleId,
					noStart, countNumber);
		} catch (Exception e) {
			resultCode = RESULTCODE_FAIL;
			e.printStackTrace();
		}
		JSONObject obj = new JSONObject();
		Response response = new Response(resultCode, pageTotal, reArticleList);
		obj.put("response", response);
		getOut().print(String.valueOf(obj));
	}

	/**
	 * 
	 * description: 查询博客
	 * 
	 * @author luohl
	 */
	public void fetchBlog() {

		int resultCode = RESULTCODE_SUCCESS;
		List<BlogArticle> list = null;
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
			list = blogArticleService.indexLatestBlog(noStart, pageSize);
		} catch (Exception e) {
			resultCode = RESULTCODE_FAIL;
		}
		JSONObject obj = new JSONObject();
		Response response = new Response(resultCode, pageTotal, list);
		obj.put("response", response);
		getOut().print(String.valueOf(obj));
	}

	public void showBlog() {

		int resultCode = RESULTCODE_SUCCESS;
		BlogArticle article = null;
		try {
			article = blogArticleService.queryBlogById(articleId);
		} catch (Exception e) {
			resultCode = RESULTCODE_FAIL;
		}
		JSONObject obj = new JSONObject();
		Response response = new Response(resultCode, article, null);
		obj.put("response", response);
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

		int resultCode = RESULTCODE_SUCCESS;
		List<Group> list = null;
		try {
			int countNumber = groupService.queryGroupsCount();
			Pagination.setPageSize(Constant.pageSize50);
			int pageSize = Pagination.getPageSize();
			pageTotal = Pagination.getPageTotal(countNumber);
			if (page > pageTotal) {
				page = pageTotal;
			}
			if (page < 1) {
				page = 1;
			}
			int noStart = (page - 1) * pageSize;
			list = groupService.queryGroupsOderArticleCount(noStart, pageSize);
		} catch (Exception e) {
			resultCode = RESULTCODE_FAIL;
		}
		JSONObject obj = new JSONObject();
		Response response = new Response(resultCode, pageTotal, list);
		obj.put("response", response);
		getOut().print(String.valueOf(obj));
	}

	public void fetchArticleByGid() {

		int resultCode = RESULTCODE_SUCCESS;
		List<Article> list = null;
		try {
			int countNumber = articleService.queryTopicCountByGid(gid, 0,
					Constant.ISVALIDY);
			Pagination.setPageSize(Constant.pageSize50);
			int pageSize = Pagination.getPageSize();
			pageTotal = Pagination.getPageTotal(countNumber);
			if (page > pageTotal) {
				page = pageTotal;
			}
			if (page < 1) {
				page = 1;
			}
			int noStart = (page - 1) * pageSize;
			list = articleService.queryTopicOrderByPostTime(gid, 0,
					Constant.ISVALIDY, noStart, countNumber);
		} catch (Exception e) {
			resultCode = RESULTCODE_FAIL;
		}
		JSONObject obj = new JSONObject();
		Response response = new Response(resultCode, pageTotal, list);
		obj.put("response", response);
		getOut().print(String.valueOf(obj));
	}

	/**
	 * 
	 * description: 登录
	 * 
	 * @author luohl
	 */
	public void login() {

		int resultCode = RESULTCODE_SUCCESS;
		try {
			User user = userService.login(userName);
			if (!Tools.encodeByMD5(password).equals(user.getPassword())) {
				resultCode = RESULTCODE_FAIL;
			}
		} catch (Exception e) {
			resultCode = RESULTCODE_FAIL;
			e.printStackTrace();
		}
		JSONObject obj = new JSONObject();
		Response response = new Response(resultCode, null, null);
		obj.put("response", response);
		getOut().print(String.valueOf(obj));
	}

	public void fetchUserInfo() {

		int resultCode = RESULTCODE_SUCCESS;
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
				userVo.setRegisterTime(userInfo.getRegisterTime());
				userVo.setSex(userInfo.getSex());
				userVo.setPrevisitTime(userInfo.getPrevisitTime());
				userVo.setMark(userInfo.getMark());
			} else {
				resultCode = RESULTCODE_FAIL;
			}
		} catch (Exception e) {
			resultCode = RESULTCODE_FAIL;
		}
		JSONObject obj = new JSONObject();
		Response response = new Response(resultCode, userVo, null);
		obj.put("response", response);
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

}
