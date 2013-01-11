package com.lhl.quan.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.lhl.common.action.BaseAction;
import com.lhl.entity.BlogArticle;
import com.lhl.entity.BlogItem;
import com.lhl.entity.BlogReply;
import com.lhl.entity.User;
import com.lhl.quan.service.BlogArticleService;
import com.lhl.quan.service.BlogItemService;
import com.lhl.quan.service.BlogReplyService;
import com.lhl.util.Constant;
import com.lhl.util.Pagination;
import com.lhl.util.Tools;

public class BlogArticleAction extends BaseAction
{
	private static final long serialVersionUID = 1L;

	private BlogArticleService blogArticleService;

	private BlogItemService blogItemService;

	private BlogReplyService blogReplyService;

	private List<BlogArticle> blogList = new ArrayList<BlogArticle>();

	private List<BlogItem> itemList = new ArrayList<BlogItem>();

	private int pageTotal;

	private int page;

	private String userId;

	private String title;

	private String content;

	private int itemId;

	private String keyWord;

	private int allowReplay;

	private int id;

	private int blogId;

	private BlogArticle blogArticle;

	private String itemName;

	private int itemRang;

	private BlogItem blogItem;

	private String reUserName;

	private String checkCode;

	private List<BlogReply> replyList = new ArrayList<BlogReply>();

	public String blog()
	{

		try
		{
			if (itemId != 0)
			{
				blogItem = blogItemService.queryBlogItemById(itemId);
				if (blogItem == null || !blogItem.getUserId().equals(userId))
				{
					return ERROR;
				}
			}
			int countNumber = blogArticleService.queryCountByUserIdOrItem(userId, itemId);
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
			blogList = blogArticleService.queryBlogByUserIdOrItem(userId, itemId, noStart, pageSize);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String saveBlog()
	{

		try
		{
			User sessionUser = getSessionUser();
			if (sessionUser != null)
			{
				userId = sessionUser.getUserId();
				BlogArticle blogArticle = new BlogArticle();
				blogArticle.setUserId(userId);
				blogArticle.setItemId(itemId);
				blogArticle.setTitle(title);
				blogArticle.setContent(content);
				blogArticle.setKeyWord(keyWord);
				blogArticle.setAllowReplay(allowReplay);
				blogArticleService.addBlog(blogArticle);
			}
			else
			{
				return ERROR;
			}
		}
		catch (Exception e)
		{
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * description: 发表文章获取分类信息
	 * 
	 * @return
	 * @throws IOException
	 * @author luohl
	 */
	public String addBlog()
	{

		try
		{
			User sessionUser = getSessionUser();
			if (sessionUser != null)
			{
				userId = sessionUser.getUserId();
				itemList = blogItemService.queryBlogItemByUserId(userId);
			}
			else
			{
				return ERROR;
			}
		}
		catch (Exception e)
		{
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * description: 查询回复
	 * 
	 * @param blogId
	 * @throws IOException
	 * @author luohl
	 */
	public void queryBlogReply(int blogId) throws IOException
	{

		List<BlogReply> list = blogReplyService.queryBlogReplyByBlogId(blogId);
		JSONObject obj = new JSONObject();
		obj.put("list", list);
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(String.valueOf(obj));
	}

	public void queryItem() throws IOException
	{

		List<BlogItem> list = blogItemService.queryBlogItemAndCountByUserId(userId);
		int count = blogArticleService.queryCountByUserIdOrItem(userId, 0);
		JSONObject obj = new JSONObject();
		obj.put("list", list);
		obj.put("allcount", count);
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(String.valueOf(obj));
	}

	public String blogdetail()
	{

		try
		{

			blogArticle = blogArticleService.queryBlogById(id);
			if (null == blogArticle)
			{
				return ERROR;
			}
			else
			{
				blogArticle.setReadCount(blogArticle.getReadCount() + 1);
				blogArticleService.updateReadCount(blogArticle);
				userId = blogArticle.getUserId();
				blogItem = blogItemService.queryBlogItemById(blogArticle.getItemId());
				replyList = blogReplyService.queryBlogReplyByBlogId(blogArticle.getId());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public void savaReply()
	{

		String msg = "ok";
		JSONObject obj = new JSONObject();
		try
		{
			// 检测用户名
			Object sessionObj = getSession().getAttribute("user");
			BlogReply reply = new BlogReply();
			if (sessionObj != null)
			{
				User sessionUser = (User) sessionObj;
				reply.setUserId(sessionUser.getUserId());
				reply.setUserName(sessionUser.getUserName());
			}
			else
			{// 如果用户名为空那么名字就用访客

				if ("".equals(reUserName))
				{
					msg = "noUserName";
					obj.put("msg", msg);
					getOut().print(String.valueOf(obj));
					return;
				}
				else
				{
					reply.setUserName(Tools.formateHtml(reUserName));
				}
				// 检测checkCode
				String sessionCcode = (String) getSession().getAttribute("checkCode");
				if (Tools.isEmpty(checkCode) || !checkCode.equalsIgnoreCase(sessionCcode))
				{
					msg = "checkCodeErr";
					obj.put("msg", msg);
					getOut().print(String.valueOf(obj));
					return;
				}
			}
			// 检测内容
			if (Tools.isEmpty(content))
			{
				msg = "noContent";
				obj.put("msg", msg);
				getOut().print(String.valueOf(obj));
				return;
			}
			else
			{
				reply.setContent(Tools.formateHtml(content));
			}
			reply.setBlogId(blogId);
			BlogReply result = blogReplyService.addReply(reply);
			obj.put("msg", result);
			getOut().print(String.valueOf(obj));
		}
		catch (Exception e)
		{
			msg = "error";
			obj.put("msg", msg);
			getOut().print(String.valueOf(obj));
		}
	}

	public String manageItem() throws IOException
	{

		try
		{
			User sessionUser = getSessionUser();
			if (sessionUser != null)
			{
				itemList = blogItemService.queryBlogItemByUserId(sessionUser.getUserId());
			}
			else
			{
				return ERROR;
			}
		}
		catch (Exception e)
		{
			return ERROR;
		}
		return SUCCESS;
	}

	public void saveItem() throws IOException
	{

		String msg = "ok";
		JSONObject obj = new JSONObject();
		try
		{
			BlogItem item = new BlogItem();
			User sessionUser = getSessionUser();
			item.setId(id);
			item.setUserId(sessionUser.getUserId());
			item.setItemName(itemName);
			item.setItemRang(itemRang);
			int id = blogItemService.saveItem(item);
			obj.put("id", id);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			msg = "error";
		}

		obj.put("msg", msg);
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(String.valueOf(obj));
	}

	public void deleteItem() throws IOException
	{

		JSONObject obj = new JSONObject();
		obj.put("result", "ok");
		User sessionUser = getSessionUser();
		blogItemService.delete(sessionUser.getUserId(), id);
		int count = blogArticleService.queryCountByUserIdOrItem(userId, id);
		if (count > 0)
		{
			obj.put("result", "havearticle");
		}
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(String.valueOf(obj));
	}

	public void setBlogArticleService(BlogArticleService blogArticleService)
	{

		this.blogArticleService = blogArticleService;
	}

	public void setBlogItemService(BlogItemService blogItemService)
	{

		this.blogItemService = blogItemService;
	}

	public void setBlogReplyService(BlogReplyService blogReplyService)
	{

		this.blogReplyService = blogReplyService;
	}

	public int getPage()
	{

		return page;
	}

	public void setPage(int page)
	{

		this.page = page;
	}

	public List<BlogArticle> getBlogList()
	{

		return blogList;
	}

	public int getPageTotal()
	{

		return pageTotal;
	}

	public String getUserId()
	{

		return userId;
	}

	public void setUserId(String userId)
	{

		this.userId = userId;
	}

	public void setTitle(String title)
	{

		this.title = title;
	}

	public void setContent(String content)
	{

		this.content = content;
	}

	public void setItemId(int itemId)
	{

		this.itemId = itemId;
	}

	public void setKeyWord(String keyWord)
	{

		this.keyWord = keyWord;
	}

	public List<BlogItem> getItemList()
	{

		return itemList;
	}

	public void setAllowReplay(int allowReplay)
	{

		this.allowReplay = allowReplay;
	}

	public void setId(int id)
	{

		this.id = id;
	}

	public BlogArticle getBlogArticle()
	{

		return blogArticle;
	}

	public BlogItem getBlogItem()
	{

		return blogItem;
	}

	public void setItemName(String itemName)
	{

		this.itemName = itemName;
	}

	public void setItemRang(int itemRang)
	{

		this.itemRang = itemRang;
	}

	public List<BlogReply> getReplyList()
	{

		return replyList;
	}

	public void setBlogId(int blogId)
	{

		this.blogId = blogId;
	}

	public void setReUserName(String reUserName)
	{

		this.reUserName = reUserName;
	}

	public void setCheckCode(String checkCode)
	{

		this.checkCode = checkCode;
	}

}
