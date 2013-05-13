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
import com.lhl.util.SubStringHTML;
import com.lhl.util.Tools;

public class BlogArticleAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	private static final int MAXLENGTH = 500;

	private BlogArticleService blogArticleService;

	private BlogItemService blogItemService;

	private BlogReplyService blogReplyService;

	private List<BlogArticle> blogList = new ArrayList<BlogArticle>();

	private List<BlogItem> itemList = new ArrayList<BlogItem>();

	private int pageTotal;

	private int page;

	private String userId;

	private String atUserId;

	private String atUserName;

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

	private String checkCode;

	private String quote;

	private List<BlogReply> replyList = new ArrayList<BlogReply>();

	public String blog() {

		try {
			if (itemId != 0) {
				blogItem = blogItemService.queryBlogItemById(itemId);
				if (blogItem == null || !blogItem.getUserId().equals(userId)) {
					return ERROR;
				}
			}
			int countNumber = blogArticleService.queryCountByUserIdOrItem(
					userId, itemId);
			Pagination.setPageSize(Constant.pageSize25);
			int pageSize = Pagination.getPageSize();
			pageTotal = Pagination.getPageTotal(countNumber);
			if (page > pageTotal) {
				page = pageTotal;
			}
			if (page < 1) {
				page = 1;
			}
			int noStart = (page - 1) * pageSize;
			blogList = blogArticleService.queryBlogByUserIdOrItem(userId,
					itemId, noStart, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String saveBlog() {

		try {
			User sessionUser = getSessionUser();
			if (sessionUser != null) {
				userId = sessionUser.getUserId();
				BlogArticle blogArticle = new BlogArticle();
				blogArticle.setUserId(userId);
				blogArticle.setItemId(itemId);
				blogArticle.setTitle(title);
				blogArticle.setContent(content);
				blogArticle.setSummary(SubStringHTML.subStringHTML(content,
						Constant.CUT_LENTH, "......"));
				blogArticle.setKeyWord(keyWord);
				blogArticle.setAllowReplay(allowReplay);
				blogArticleService.addBlog(blogArticle, sessionUser);
			} else {
				return ERROR;
			}
		} catch (Exception e) {
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
	public String addBlog() {

		try {
			User sessionUser = getSessionUser();
			if (sessionUser != null) {
				userId = sessionUser.getUserId();
				itemList = blogItemService.queryBlogItemByUserId(userId);
			} else {
				return ERROR;
			}
		} catch (Exception e) {
			return ERROR;
		}
		return SUCCESS;
	}

	public String getEditinfo() {

		try {
			User sessionUser = getSessionUser();
			blogArticle = blogArticleService.queryBlogById(id);
			if (null == blogArticle) {
				return ERROR;
			} else {
				userId = blogArticle.getUserId();
				if (!userId.equals(sessionUser.getUserId())) {
					return ERROR;
				} else {
					itemList = blogItemService.queryBlogItemByUserId(userId);
				}

			}
		} catch (Exception e) {
			return ERROR;
		}
		return SUCCESS;
	}

	public String editBlog() {

		try {
			User sessionUser = getSessionUser();
			blogArticle = blogArticleService.queryBlogById(id);
			if (null == blogArticle) {
				return ERROR;
			} else {
				userId = blogArticle.getUserId();
				if (!userId.equals(sessionUser.getUserId())) {
					return ERROR;
				} else {
					blogArticle.setTitle(title);
					blogArticle.setItemId(itemId);
					blogArticle.setContent(content);
					blogArticle.setSummary(SubStringHTML.subStringHTML(content,
							Constant.CUT_LENTH, "......"));
					blogArticle.setKeyWord(keyWord);
					blogArticle.setAllowReplay(allowReplay);
					blogArticleService.update(blogArticle);
				}
			}
		} catch (Exception e) {
			return ERROR;
		}
		return SUCCESS;
	}

	public String deleteBlog() {

		try {
			User sessionUser = getSessionUser();
			blogArticle = blogArticleService.queryBlogById(id);
			if (null == blogArticle) {
				return ERROR;
			} else {
				userId = blogArticle.getUserId();
				if (!userId.equals(sessionUser.getUserId())) {
					return ERROR;
				} else {
					blogArticleService.deleteArticle(id);
				}
			}
		} catch (Exception e) {
			return ERROR;
		}
		return SUCCESS;
	}

	public void queryItem() throws IOException {

		List<BlogItem> list = blogItemService
				.queryBlogItemAndCountByUserId(userId);
		int count = blogArticleService.queryCountByUserIdOrItem(userId, 0);
		JSONObject obj = new JSONObject();
		obj.put("list", list);
		obj.put("allcount", count);
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(String.valueOf(obj));
	}

	public String blogdetail() {

		try {

			blogArticle = blogArticleService.queryBlogById(id);
			if (null == blogArticle) {
				return ERROR;
			} else {
				blogArticle.setReadCount(blogArticle.getReadCount() + 1);
				blogArticleService.updateReadCount(blogArticle);
				userId = blogArticle.getUserId();
				blogItem = blogItemService.queryBlogItemById(blogArticle
						.getItemId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public void loadReply() {

		String msg = "ok";
		JSONObject obj = new JSONObject();
		try {
			List<BlogReply> list = blogReplyService
					.queryBlogReplyByBlogId(blogId);
			obj.put("list", list);
		} catch (Exception e) {
			msg = "error";
			e.printStackTrace();
		}
		obj.put("msg", msg);
		PrintWriter out = getOut();
		out.println(String.valueOf(obj));
	}

	public void savaReply() {

		String msg = "";
		String result = "success";
		JSONObject obj = new JSONObject();
		try {
			// 检测用户名
			Object sessionObj = getSession().getAttribute("user");
			if (sessionObj == null) {
				result = "fail";
				msg = "请先登录再发帖";
				obj.put("msg", msg);
				obj.put("result", result);
				getOut().print(String.valueOf(obj));
				return;
			}
			/*
			 * String sessionCcode = (String)
			 * getSession().getAttribute("checkCode"); if
			 * (Tools.isEmpty(checkCode) ||
			 * !checkCode.equalsIgnoreCase(sessionCcode)) { result = "fail"; msg
			 * = "验证码错误"; obj.put("msg", msg); obj.put("result", result);
			 * getOut().print(String.valueOf(obj)); return; }
			 */
			if (Tools.isEmpty(content) || content.length() > MAXLENGTH) {
				result = "fail";
				msg = "输入内容为空或者超过长度";
				obj.put("msg", msg);
				obj.put("result", result);
				getOut().print(String.valueOf(obj));
				return;
			}

			BlogReply reply = new BlogReply();
			User sessionUser = (User) sessionObj;
			reply.setUserId(sessionUser.getUserId());
			reply.setUserName(sessionUser.getUserName());
			reply.setReUserIcon(sessionUser.getUserLittleIcon());
			reply.setAtUserId(atUserId);
			reply.setAtUserName(atUserName);
			// 检测内容
			reply.setQuote(quote);
			// 引用回复
			reply.setContent(content);
			reply.setBlogId(blogId);
			BlogReply blogReply = blogReplyService.addReply(reply);
			reply.setId(blogReply.getId());
			reply.setPostTime(blogReply.getPostTime());
			obj.put("note", reply);
			obj.put("result", result);
			getOut().print(String.valueOf(obj));
		} catch (Exception e) {
			msg = "error";
			obj.put("msg", msg);
			getOut().print(String.valueOf(obj));
		}
	}

	public void deleteReply() {

		User sessionUser = getSessionUser();
		String msg = "ok";
		JSONObject obj = new JSONObject();
		try {
			if (sessionUser == null) {
				msg = "noperm";
			} else {
				if (!blogReplyService.delete(sessionUser.getUserId(), id)) {
					msg = "noperm";
				}
			}
		} catch (Exception e) {
			msg = "error";
		}
		obj.put("msg", msg);
		getOut().print(String.valueOf(obj));
	}

	public String manageItem() throws IOException {

		try {
			User sessionUser = getSessionUser();
			if (sessionUser != null) {
				itemList = blogItemService.queryBlogItemByUserId(sessionUser
						.getUserId());
			} else {
				return ERROR;
			}
		} catch (Exception e) {
			return ERROR;
		}
		return SUCCESS;
	}

	public void saveItem() throws IOException {

		String msg = "ok";
		JSONObject obj = new JSONObject();
		try {
			BlogItem item = new BlogItem();
			User sessionUser = getSessionUser();
			item.setId(id);
			item.setUserId(sessionUser.getUserId());
			item.setItemName(itemName);
			item.setItemRang(itemRang);
			int id = blogItemService.saveItem(item);
			obj.put("id", id);
		} catch (Exception e) {
			e.printStackTrace();
			msg = "error";
		}

		obj.put("msg", msg);
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(String.valueOf(obj));
	}

	public void deleteItem() throws IOException {

		JSONObject obj = new JSONObject();
		obj.put("result", "ok");
		User sessionUser = getSessionUser();
		blogItemService.delete(sessionUser.getUserId(), id);
		int count = blogArticleService.queryCountByUserIdOrItem(userId, id);
		if (count > 0) {
			obj.put("result", "havearticle");
		}
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(String.valueOf(obj));
	}

	public void setBlogArticleService(BlogArticleService blogArticleService) {

		this.blogArticleService = blogArticleService;
	}

	public void setBlogItemService(BlogItemService blogItemService) {

		this.blogItemService = blogItemService;
	}

	public void setBlogReplyService(BlogReplyService blogReplyService) {

		this.blogReplyService = blogReplyService;
	}

	public int getPage() {

		return page;
	}

	public void setPage(int page) {

		this.page = page;
	}

	public List<BlogArticle> getBlogList() {

		return blogList;
	}

	public int getPageTotal() {

		return pageTotal;
	}

	public String getUserId() {

		return userId;
	}

	public void setUserId(String userId) {

		this.userId = userId;
	}

	public void setTitle(String title) {

		this.title = title;
	}

	public void setContent(String content) {

		this.content = content;
	}

	public void setItemId(int itemId) {

		this.itemId = itemId;
	}

	public void setKeyWord(String keyWord) {

		this.keyWord = keyWord;
	}

	public List<BlogItem> getItemList() {

		return itemList;
	}

	public void setAllowReplay(int allowReplay) {

		this.allowReplay = allowReplay;
	}

	public void setId(int id) {

		this.id = id;
	}

	public BlogArticle getBlogArticle() {

		return blogArticle;
	}

	public BlogItem getBlogItem() {

		return blogItem;
	}

	public void setItemName(String itemName) {

		this.itemName = itemName;
	}

	public void setItemRang(int itemRang) {

		this.itemRang = itemRang;
	}

	public List<BlogReply> getReplyList() {

		return replyList;
	}

	public void setBlogId(int blogId) {

		this.blogId = blogId;
	}

	public void setCheckCode(String checkCode) {

		this.checkCode = checkCode;
	}

	public void setQuote(String quote) {

		this.quote = quote;
	}

	public void setAtUserId(String atUserId) {
		this.atUserId = atUserId;
	}

	public void setAtUserName(String atUserName) {
		this.atUserName = atUserName;
	}

}
