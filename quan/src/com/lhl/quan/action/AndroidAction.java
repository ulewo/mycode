package com.lhl.quan.action;

import java.util.List;

import net.sf.json.JSONObject;

import com.lhl.admin.service.AdminArticleService;
import com.lhl.common.action.BaseAction;
import com.lhl.entity.Article;
import com.lhl.entity.BlogArticle;
import com.lhl.entity.Group;
import com.lhl.quan.service.ArticleService;
import com.lhl.quan.service.BlogArticleService;
import com.lhl.quan.service.GroupService;
import com.lhl.quan.service.UserService;
import com.lhl.util.Constant;
import com.lhl.util.Pagination;

public class AndroidAction extends BaseAction {
	private AdminArticleService adminArticleService;

	private ArticleService articleService;

	private GroupService groupService;

	private UserService userService;

	private BlogArticleService blogArticleService;

	public void setArticleService(ArticleService articleService) {

		this.articleService = articleService;
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

	private int page;

	private int articleId;

	public int getPage() {

		return page;
	}

	public void setPage(int page) {

		this.page = page;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

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
		int pageTotal = Pagination.getPageTotal(countNumber);
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
		JSONObject obj = new JSONObject();
		obj.put("list", list);
		getOut().print(String.valueOf(obj));
	}

	public void showArticle() {

		Article article = null;
		try {
			article = articleService.queryTopicById(articleId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject obj = new JSONObject();
		obj.put("article", article);
		getOut().print(String.valueOf(obj));
	}

	/**
	 * 
	 * description: 查询博客
	 * 
	 * @author luohl
	 */
	public void fetchBlog() {

		int countNumber = blogArticleService.queryCount();
		Pagination.setPageSize(Constant.pageSize20);
		int pageSize = Pagination.getPageSize();
		int pageTotal = Pagination.getPageTotal(countNumber);
		if (page > pageTotal) {
			page = pageTotal;
		}
		if (page < 1) {
			page = 1;
		}
		int noStart = (page - 1) * pageSize;
		List<BlogArticle> list = blogArticleService.indexLatestBlog(noStart,
				pageSize);
		JSONObject obj = new JSONObject();
		obj.put("list", list);
		getOut().print(String.valueOf(obj));
	}

	public void showBlog() {

		BlogArticle article = null;
		try {
			article = blogArticleService.queryBlogById(articleId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject obj = new JSONObject();
		obj.put("article", article);
		getOut().print(String.valueOf(obj));
	}

	/**
	 * 
	 * description: 查询窝窝
	 * 
	 * @author luohl
	 */
	public void fetchWoWo() {

		int countNumber = groupService.queryGroupsCount();
		Pagination.setPageSize(Constant.pageSize50);
		int pageSize = Pagination.getPageSize();
		int pageTotal = Pagination.getPageTotal(countNumber);
		if (page > pageTotal) {
			page = pageTotal;
		}
		if (page < 1) {
			page = 1;
		}
		int noStart = (page - 1) * pageSize;
		List<Group> list = groupService.queryGroupsOderArticleCount(noStart,
				pageSize);
		JSONObject obj = new JSONObject();
		obj.put("list", list);
		getOut().print(String.valueOf(obj));
	}

}
