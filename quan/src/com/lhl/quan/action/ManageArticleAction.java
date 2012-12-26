package com.lhl.quan.action;

import java.util.List;

import com.lhl.common.action.BaseAction;
import com.lhl.config.ErrMsgConfig;
import com.lhl.entity.Article;
import com.lhl.entity.ArticleItem;
import com.lhl.entity.Group;
import com.lhl.entity.User;
import com.lhl.exception.BaseException;
import com.lhl.quan.service.ArticleItemService;
import com.lhl.quan.service.ArticleService;
import com.lhl.util.Constant;
import com.lhl.util.Pagination;

public class ManageArticleAction extends BaseAction
{
	private static final long serialVersionUID = 1L;

	private ArticleService articleService;

	private ArticleItemService articleItemService;

	private int id;

	private int[] ids;

	private int itemId;

	private String gid; //群组ID

	private int page; //页数

	private int pageTotal; //总页数

	private Group group;

	private List<ArticleItem> itemList;

	private List<Article> articleList;

	private Article article;

	private String errMsg;

	private int countNumber;

	private String opType;

	private String title;

	private String content;

	private String keyWord;

	/**
	 * 
	 * description:管理文章
	 * @return
	 * @author luohl
	 */
	public String article()
	{

		try
		{
			/*在过滤器中已经验证是否登录*/
			if (isAdmin())
			{
				//itemList = articleItemService.queryItemByGid(gid);
				countNumber = articleService.queryTopicCountByGid(gid, itemId, Constant.ISVALIDY);
				Pagination.setPageSize(Constant.pageSize15);
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
				articleList = articleService.queryTopicOrderByGradeAndLastReTime(gid, itemId, Constant.ISVALIDY,
						noStart, pageSize);
			}
			else
			{
				errMsg = ErrMsgConfig.getErrMsg(10002);
				return ERROR;
			}
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

	public String setTop()
	{

		try
		{
			if (isAdmin())
			{
				Article article = null;
				for (int i = 0; i < ids.length; i++)
				{
					article = new Article();
					article.setGid(gid);
					article.setId(ids[i]);
					if ("set".equals(opType))
					{
						article.setGrade(1);
					}
					else if ("cancel".equals(opType))
					{
						article.setGrade(0);
					}
					articleService.updateArticleSelective(article);
				}
			}
			else
			{
				errMsg = ErrMsgConfig.getErrMsg(10002);
				return ERROR;
			}
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

	public String setGood()
	{

		try
		{
			if (isAdmin())
			{
				Article article = null;
				for (int i = 0; i < ids.length; i++)
				{
					article = new Article();
					article.setGid(gid);
					article.setId(ids[i]);
					if ("set".equals(opType))
					{
						article.setEssence(Constant.essenceY);
					}
					else if ("cancel".equals(opType))
					{
						article.setEssence(Constant.essenceN);
					}
					articleService.updateArticleSelective(article);
				}
			}
			else
			{
				errMsg = ErrMsgConfig.getErrMsg(10002);
				return ERROR;
			}
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

	public String setTitle()
	{

		try
		{
			if (isAdmin())
			{
				Article article = null;
				for (int i = 0; i < ids.length; i++)
				{
					article = new Article();
					article.setGid(gid);
					article.setId(ids[i]);
					article.setTitleStyle(Constant.titStyle);
					articleService.updateArticleSelective(article);
				}
			}
			else
			{
				errMsg = ErrMsgConfig.getErrMsg(10002);
				return ERROR;
			}
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

	//获取更新信息
	public String editArticle()
	{

		try
		{
			article = articleService.queryTopicById(id);
			itemList = articleItemService.queryItemByGid(gid);
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

	/**
	 * 
	 * description: 更新文章
	 * @return
	 * @author luohl
	 */
	public String updateArticle()
	{

		try
		{
			if (isAdmin())
			{
				article = articleService.queryTopicById(id);
				article.setTitle(title);
				article.setKeyWord(keyWord);
				article.setContent(content);
				article.setItemId(itemId);
				articleService.updateArticleSelective(article);
			}
			else
			{
				errMsg = ErrMsgConfig.getErrMsg(10002);
				return ERROR;
			}

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

	public String deleteArticle()
	{

		try
		{
			if (isAdmin())
			{
				Article article = null;
				for (int i = 0; i < ids.length; i++)
				{
					article = new Article();
					article.setGid(gid);
					article.setId(ids[i]);
					article.setIsValid(Constant.ISVALIDN);
					articleService.updateArticleSelective(article);
				}
			}
			else
			{
				errMsg = ErrMsgConfig.getErrMsg(10002);
				return ERROR;
			}
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

	private boolean isAdmin()
	{

		User sessionUser = (User) getSession().getAttribute("user");
		int grade = CheckRole.getMemberGrade(gid, sessionUser.getUserId());
		if (Constant.grade2 == grade || Constant.SUPERADMIN.equals(sessionUser.getUserId()))
		{
			return true;
		}
		return false;
	}

	public int getPage()
	{

		return page;
	}

	public void setPage(int page)
	{

		this.page = page;
	}

	public int getPageTotal()
	{

		return pageTotal;
	}

	public void setId(int id)
	{

		this.id = id;
	}

	public Group getGroup()
	{

		return group;
	}

	public List<ArticleItem> getItemList()
	{

		return itemList;
	}

	public String getGid()
	{

		return gid;
	}

	public void setGid(String gid)
	{

		this.gid = gid;
	}

	public int getItemId()
	{

		return itemId;
	}

	public void setItemId(int itemId)
	{

		this.itemId = itemId;
	}

	public List<Article> getArticleList()
	{

		return articleList;
	}

	public String getErrMsg()
	{

		return errMsg;
	}

	public Article getArticle()
	{

		return article;
	}

	public void setArticleService(ArticleService articleService)
	{

		this.articleService = articleService;
	}

	public void setArticleItemService(ArticleItemService articleItemService)
	{

		this.articleItemService = articleItemService;
	}

	public int getCountNumber()
	{

		return countNumber;
	}

	public void setOpType(String opType)
	{

		this.opType = opType;
	}

	public void setIds(int[] ids)
	{

		this.ids = ids;
	}

	public void setTitle(String title)
	{

		this.title = title;
	}

	public void setContent(String content)
	{

		this.content = content;
	}

	public void setKeyWord(String keyWord)
	{

		this.keyWord = keyWord;
	}

	public int getId()
	{

		return id;
	}

}
