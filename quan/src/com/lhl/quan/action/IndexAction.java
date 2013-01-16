package com.lhl.quan.action;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import com.lhl.admin.service.AdminArticleService;
import com.lhl.common.action.BaseAction;
import com.lhl.config.ErrMsgConfig;
import com.lhl.entity.Article;
import com.lhl.entity.BlogArticle;
import com.lhl.entity.Group;
import com.lhl.entity.User;
import com.lhl.exception.BaseException;
import com.lhl.quan.service.ArticleService;
import com.lhl.quan.service.BlogArticleService;
import com.lhl.quan.service.GroupService;
import com.lhl.quan.service.UserService;
import com.lhl.util.Constant;
import com.lhl.util.Pagination;
import com.lhl.util.Tools;

public class IndexAction extends BaseAction
{
	private static final long serialVersionUID = 1L;

	private AdminArticleService adminArticleService;

	private ArticleService articleService;

	private GroupService groupService;

	private UserService userService;

	private BlogArticleService blogArticleService;

	private String keyWord;

	private int pageTotal;

	private int page;

	private String errMsg;

	private List<Article> imgArticle = new ArrayList();

	private List<Article> commendArticle = new ArrayList();

	private List<Article> list = new ArrayList<Article>();

	private List<BlogArticle> blogList = new ArrayList<BlogArticle>();

	private List<Group> groupList = new ArrayList<Group>();

	private List<Group> commendGroupList = new ArrayList<Group>();

	private List<User> activeUserList = new ArrayList<User>();

	private List<Article> articleList = new ArrayList<Article>();

	private List<Article> square1 = new ArrayList<Article>();

	private List<Article> square2 = new ArrayList<Article>();

	private List<Article> square3 = new ArrayList<Article>();

	private List<Article> square4 = new ArrayList<Article>();

	private int countNumber;

	public String index()
	{

		try
		{
			commendArticle = articleService.queryComendArticle("index", "", 0, 10);
			imgArticle = articleService.queryImageArticle(0, 4);
			list = articleService.queryLatestArticle(0, 15);
			activeUserList = userService.queryActiveUsers(0, 15);
			commendGroupList = groupService.queryGroupsOderArticleCount(0, 5);
			blogList = blogArticleService.indexLatestBlog(0, 15);
		}
		catch (BaseException e)
		{
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		}
		catch (Exception e)
		{
			errMsg = ErrMsgConfig.getErrMsg(10000);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String square()
	{

		try
		{
			int countNumber = adminArticleService.queryArticleCount(keyWord, Constant.ISVALIDY);
			Pagination.setPageSize(Constant.pageSize50);
			int pageSize = Pagination.getPageSize();
			pageTotal = Pagination.getPageTotal(countNumber);
			if (page > pageTotal)
			{
				page = pageTotal;
			}
			if (page < 1)
			{
				page = 1;
			}
			int noStart = (page - 1) * pageSize;
			List<Article> squareList = adminArticleService.queryList(keyWord, Constant.ISVALIDY, noStart, pageSize);
			set2Square(squareList);
		}
		catch (BaseException e)
		{
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		}
		catch (Exception e)
		{
			errMsg = ErrMsgConfig.getErrMsg(10000);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	private void set2Square(List<Article> squareList)
	{

		int num = 0;
		int j = 0;
		for (Article article : squareList)
		{
			j++;
			if (1 + num * 4 == j)
			{
				square1.add(article);
				continue;
			}
			if (2 + num * 4 == j)
			{
				square2.add(article);
				continue;
			}
			if (3 + num * 4 == j)
			{
				square3.add(article);
				continue;
			}
			if (4 + num * 4 == j)
			{
				square4.add(article);
				num++;
				continue;
			}
		}
	}

	public String groups()
	{

		try
		{
			countNumber = groupService.queryGroupsCount();
			Pagination.setPageSize(10);
			int pageSize = Pagination.getPageSize();
			pageTotal = Pagination.getPageTotal(countNumber);
			if (page > pageTotal)
			{
				page = pageTotal;
			}
			if (page < 1)
			{
				page = 1;
			}
			int noStart = (page - 1) * pageSize;
			groupList = groupService.queryGroupsOderArticleCount(noStart, pageSize);
		}
		catch (BaseException e)
		{
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		}
		catch (Exception e)
		{
			errMsg = ErrMsgConfig.getErrMsg(10000);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String search()
	{

		try
		{
			//groupList = groupService.searchGroups(keyWord, 0, 10);
			if (Tools.isEmpty(keyWord))
			{
				return SUCCESS;
			}
			keyWord = URLDecoder.decode(keyWord, "utf-8");
			countNumber = articleService.searchTopicCount(keyWord, "", Constant.ISVALIDY);
			Pagination.setPageSize(10);
			int pageSize = Pagination.getPageSize();
			pageTotal = Pagination.getPageTotal(countNumber);
			if (page > pageTotal)
			{
				page = pageTotal;
			}
			if (page < 1)
			{
				page = 1;
			}
			int noStart = (page - 1) * pageSize;
			articleList = articleService.searchTopic(keyWord, "", Constant.ISVALIDY, noStart, pageSize);
		}
		catch (BaseException e)
		{
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		}
		catch (Exception e)
		{
			errMsg = ErrMsgConfig.getErrMsg(10000);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String getKeyWord()
	{

		return keyWord;
	}

	public void setKeyWord(String keyWord)
	{

		this.keyWord = keyWord;
	}

	public int getPageTotal()
	{

		return pageTotal;
	}

	public void setPageTotal(int pageTotal)
	{

		this.pageTotal = pageTotal;
	}

	public int getPage()
	{

		return page;
	}

	public void setPage(int page)
	{

		this.page = page;
	}

	public String getErrMsg()
	{

		return errMsg;
	}

	public void setAdminArticleService(AdminArticleService adminArticleService)
	{

		this.adminArticleService = adminArticleService;
	}

	public List<Article> getList()
	{

		return list;
	}

	public List<Article> getSquare1()
	{

		return square1;
	}

	public List<Article> getSquare2()
	{

		return square2;
	}

	public List<Article> getSquare3()
	{

		return square3;
	}

	public List<Article> getSquare4()
	{

		return square4;
	}

	public int getCountNumber()
	{

		return countNumber;
	}

	public void setGroupService(GroupService groupService)
	{

		this.groupService = groupService;
	}

	public List<Group> getGroupList()
	{

		return groupList;
	}

	public List<Article> getImgArticle()
	{

		return imgArticle;
	}

	public List<Article> getCommendArticle()
	{

		return commendArticle;
	}

	public void setArticleService(ArticleService articleService)
	{

		this.articleService = articleService;
	}

	public List<Article> getArticleList()
	{

		return articleList;
	}

	public List<Group> getCommendGroupList()
	{

		return commendGroupList;
	}

	public List<User> getActiveUserList()
	{

		return activeUserList;
	}

	public void setUserService(UserService userService)
	{

		this.userService = userService;
	}

	public void setBlogArticleService(BlogArticleService blogArticleService)
	{

		this.blogArticleService = blogArticleService;
	}

	public List<BlogArticle> getBlogList()
	{

		return blogList;
	}

}
