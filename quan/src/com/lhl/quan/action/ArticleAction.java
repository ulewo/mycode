package com.lhl.quan.action;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.lhl.admin.service.AdminArticleService;
import com.lhl.common.action.BaseAction;
import com.lhl.config.ErrMsgConfig;
import com.lhl.entity.Article;
import com.lhl.entity.ArticleItem;
import com.lhl.entity.Group;
import com.lhl.entity.ReArticle;
import com.lhl.entity.User;
import com.lhl.exception.BaseException;
import com.lhl.quan.service.ArticleItemService;
import com.lhl.quan.service.ArticleService;
import com.lhl.quan.service.GroupService;
import com.lhl.quan.service.MemberService;
import com.lhl.quan.service.ReArticleService;
import com.lhl.util.Constant;
import com.lhl.util.Pagination;
import com.lhl.util.Tools;

public class ArticleAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	private ArticleService articleService;

	private ArticleItemService articleItemService;

	private GroupService groupService;

	private ReArticleService reArticleService;

	private MemberService memberService;

	private AdminArticleService adminArticleService;

	private int id;

	private int itemId;

	private String gid; // 群组ID

	private int page; // 页数

	private int pageTotal; // 总页数

	private Group group;

	private String image;

	private List<ArticleItem> itemList;

	private List<Article> articleList;

	private List<ReArticle> reArticleList = new ArrayList<ReArticle>();

	private Article article;

	private ReArticle reArticle;

	private String errMsg;

	private int articleid;

	private String content;

	private String authorName;

	private String checkCode;

	private String reUserName;

	private String reContent;

	private String showType;

	private String keyWord;

	private String title;

	//显示删除
	private String showDelete = "N";

	/**
	 * 
	 * description: 群文章
	 * 
	 * @return
	 * @author luohl
	 */
	public String article() {

		try {
			group = groupService.queryGorup(gid);
			if (itemId != 0) {
				ArticleItem articlItem = articleItemService.getArticleItem(itemId);
				if (null == articlItem || !gid.equals(articlItem.getGid())) {
					errMsg = ErrMsgConfig.getErrMsg(40001);
					return ERROR;
				}
			}
			itemList = articleItemService.queryItemByGid(gid);
			int countNumber = articleService.queryTopicCountByGid(gid, itemId, Constant.ISVALIDY);
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
			articleList = articleService.queryTopicOrderByGradeAndLastReTime(gid, itemId, Constant.ISVALIDY, noStart,
					pageSize);
			// latestArticles = articleService.queryLatestArticles(0, 20);
			showType = "2";
		}
		catch (BaseException e) {
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		}
		catch (Exception e) {
			errMsg = ErrMsgConfig.getErrMsg(10000);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * description: 展示文章
	 * 
	 * @return
	 * @author luohl
	 */

	public String showArticle() {

		try {
			article = articleService.showArticle(id);
			article.setReadNumber(article.getReadNumber() + 1);
			gid = article.getGid();
			//判断权限，删除回复
			Object obj = getSession().getAttribute("user");
			if (null == obj) {
				showDelete = "N";
			}
			else {
				User sessionUser = (User) getSession().getAttribute("user");
				int grade = CheckRole.getMemberGrade(gid, sessionUser.getUserId());
				if (Constant.grade1 == grade || Constant.grade2 == grade
						|| Constant.SUPERADMIN.equals(sessionUser.getUserId())) {
					showDelete = "Y";
				}
				else {
					showDelete = "N";
				}
			}

			group = groupService.queryGorup(gid);
			itemId = article.getItemId();
			itemList = articleItemService.queryItemByGid(gid);
			int countNumber = reArticleService.queryReArticleCount(id);
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
			reArticleList = reArticleService.queryReArticles(id, noStart, pageSize);
			showType = "2";
			articleService.updateArticleSelective(article);
		}
		catch (BaseException e) {
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		}
		catch (Exception e) {
			errMsg = ErrMsgConfig.getErrMsg(10000);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public void aboutArticles() {

		JSONArray ja = new JSONArray();
		JSONObject obj = new JSONObject();
		try {
			List<Article> articleList = articleService.aboutArticle(keyWord, gid);
			ja.addAll(articleList);

		}
		catch (BaseException e) {
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());

		}
		catch (Exception e) {
		}
		obj.put("articleList", ja);
		getOut().print(String.valueOf(obj));
	}

	/**
	 * 回复的文章 description: 函数的目的/功能
	 * 
	 * @author lhl
	 */
	/*public void reArticles()
	{

		try
		{
			int countNumber = reArticleService.queryReArticleCount(articleid);
			Pagination.setPageSize(Constant.pageSize20);
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
			List<ReArticle> rejsonArticleList = reArticleService.queryReArticles(articleid, noStart, pageSize);
			JSONArray ja = new JSONArray();
			ja.addAll(rejsonArticleList);
			JSONObject obj = new JSONObject();
			obj.put("total", countNumber);
			obj.put("reArticles", ja);
			getOut().print(String.valueOf(obj));
		}
		catch (BaseException e)
		{
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}*/

	/**
	 * 
	 * description: 快速回复
	 * @author lhl
	 */
	public void addReArticle() {

		String msg = "";
		JSONObject obj = new JSONObject();
		try {
			ReArticle reArticle = new ReArticle();
			reArticle.setArticleId(articleid);
			reArticle.setContent(Tools.formateHtml(reContent));
			reArticle.setGid(gid);
			Object sessionObj = getSession().getAttribute("user");
			if (sessionObj != null) {
				User sessionUser = (User) sessionObj;
				reArticle.setAuthorid(sessionUser.getUserId());
			}
			else {//如果用户名为空那么名字就用访客
				String sessionCcode = (String) getSession().getAttribute("checkCode");
				if (Tools.isEmpty(checkCode) || !checkCode.equalsIgnoreCase(sessionCcode)) {
					msg = "checkCodeErr";
					obj.put("reArticle", msg);
					getOut().print(String.valueOf(obj));
					return;
				}
				if ("".equals(reUserName)) {
					reArticle.setAuthorName("访客");
				}
				else {
					reArticle.setAuthorName(Tools.formateHtml(reUserName));
				}
			}
			ReArticle re = reArticleService.addReArticle(reArticle);
			obj.put("reArticle", re);
			getOut().print(String.valueOf(obj));
		}
		catch (BaseException e) {
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * description: 添加文章
	 * 
	 * @return
	 * @author luohl
	 */
	public String addArticle() {

		try {
			Object userObj = getSession().getAttribute("user");
			if (userObj == null) {
				errMsg = ErrMsgConfig.getErrMsg(10001);
				return ERROR;
			}
			User sessionUser = (User) userObj;
			if (!checkAddPermission(gid, sessionUser.getUserId())) {
				errMsg = ErrMsgConfig.getErrMsg(30001);
				return ERROR;
			}
			group = groupService.queryGorup(gid);
			itemList = articleItemService.queryItemByGid(gid);
			showType = "2";
		}
		catch (BaseException e) {
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		}
		catch (Exception e) {
			errMsg = ErrMsgConfig.getErrMsg(10000);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * description: 发表文章
	 * 
	 * @return
	 * @author luohl
	 */
	public String subArticle() {

		try {
			Object userObj = getSession().getAttribute("user");
			if (userObj == null) {
				errMsg = ErrMsgConfig.getErrMsg(10001);
				return ERROR;
			}
			User sessionUser = (User) userObj;
			if (!checkAddPermission(gid, sessionUser.getUserId())) {
				errMsg = ErrMsgConfig.getErrMsg(30001);
				return ERROR;
			}
			Article article = new Article();
			article.setItemId(itemId);
			article.setImage(image);
			article.setTitle(title);
			article.setKeyWord(keyWord);
			article.setContent(content);
			article.setGid(gid);
			article.setAuthorId(sessionUser.getUserId());
			article.setType("0");
			articleService.addArticle(article);
		}
		catch (BaseException e) {
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		}
		catch (Exception e) {
			errMsg = ErrMsgConfig.getErrMsg(10000);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	private boolean checkAddPermission(String gid, String userId) throws Exception {

		if (StringUtils.isEmpty(gid) || StringUtils.isEmpty(userId)) {
			return false;
		}
		Group group = groupService.queryGorup(gid);
		if (Constant.ALL_PERA.equals(group.getPostPerm())) {
			return true;
		}
		else if (Constant.MEMBER_PERM.equals(group.getPostPerm()) && memberService.isMember(gid, userId)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * description: 回复文章
	 * 
	 * @return
	 * @author luohl
	 */
	public String reArticle() {

		try {
			article = articleService.queryTopicById(id);
			gid = article.getGid();
			group = groupService.queryGorup(gid);
			showType = "2";
		}
		catch (BaseException e) {
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		}
		catch (Exception e) {
			errMsg = ErrMsgConfig.getErrMsg(10000);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * description: 发表回复
	 * @return
	 * @author luohl
	 */
	public String subReArticle() {

		try {
			Object userObj = getSession().getAttribute("user");
			if (userObj == null) {
				errMsg = ErrMsgConfig.getErrMsg(10001);
				return ERROR;
			}
			User sessionUser = (User) userObj;

			ReArticle rearticle = new ReArticle();
			rearticle.setArticleId(id);
			rearticle.setGid(gid);
			rearticle.setContent(content);
			rearticle.setAuthorid(sessionUser.getUserId());
			reArticleService.addReArticle(rearticle);
		}
		catch (BaseException e) {
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		}
		catch (Exception e) {
			errMsg = ErrMsgConfig.getErrMsg(10000);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * description: 引用文章
	 * 
	 * @return
	 * @author luohl
	 */
	public String quote() {

		try {
			reArticle = reArticleService.getReArticle(id);
			gid = reArticle.getGid();
			group = groupService.queryGorup(gid);
			showType = "2";
		}
		catch (BaseException e) {
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		}
		catch (Exception e) {
			errMsg = ErrMsgConfig.getErrMsg(10000);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * description: 提交引用
	 * @return
	 * @author 35sz
	 */
	public String subQuote() {

		try {
			Object userObj = getSession().getAttribute("user");
			if (userObj == null) {
				errMsg = ErrMsgConfig.getErrMsg(10001);
				return ERROR;
			}
			User sessionUser = (User) userObj;
			ReArticle quote = reArticleService.getReArticle(id);
			String qutoStr = "<div class='ad_quto2'>" + "<img border='0' src='../images/quto.gif'>" + "<b>引用&nbsp;"
					+ "<span style='color:blue'>" + quote.getAuthorName() + "</span>" + "&nbsp;在&nbsp;"
					+ quote.getReTime().substring(0, 16) + "&nbsp;的发表</b>" + "<div style='margin-top:5px;'>"
					+ quote.getContent() + "</div></div>";
			String quoteStr = qutoStr + "<div>" + content + "</div>";
			ReArticle rearticle = new ReArticle();
			gid = quote.getGid();
			articleid = quote.getArticleId();
			rearticle.setArticleId(articleid);
			rearticle.setGid(gid);
			rearticle.setContent(quoteStr);
			rearticle.setAuthorid(sessionUser.getUserId());
			reArticleService.addReArticle(rearticle);
		}
		catch (BaseException e) {
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		}
		catch (Exception e) {
			errMsg = ErrMsgConfig.getErrMsg(10000);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * description: 编辑文章
	 * 
	 * @return
	 * @author luohl
	 */
	public String editeArticle() {

		try {
			article = articleService.queryTopicById(id);
			gid = article.getGid();
			group = groupService.queryGorup(gid);
			itemId = article.getItemId();
			itemList = articleItemService.queryItemByGid(gid);
			showType = "2";
		}
		catch (BaseException e) {
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		}
		catch (Exception e) {
			errMsg = ErrMsgConfig.getErrMsg(10000);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * description: 更新文章
	 * 
	 * @return
	 * @author luohl
	 */
	public String updateArticle() {

		try {
			article = articleService.queryTopicById(id);
			article.setItemId(itemId);
			article.setContent(content);
			article.setTitle(title);
			articleService.updateArticleSelective(article);
			gid = article.getGid();
		}
		catch (BaseException e) {
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		}
		catch (Exception e) {
			errMsg = ErrMsgConfig.getErrMsg(10000);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * description: 删除回复
	 * 
	 * @return
	 * @author luohl
	 */
	public String deleteReArticle() {

		try {
			//判断权限，删除回复
			Object obj = getSession().getAttribute("user");
			if (null == obj) {
				errMsg = ErrMsgConfig.getErrMsg(10002);
				return ERROR;
			}
			else {
				User sessionUser = (User) getSession().getAttribute("user");
				int grade = CheckRole.getMemberGrade(gid, sessionUser.getUserId());
				if (Constant.grade1 == grade || Constant.grade2 == grade
						|| Constant.SUPERADMIN.equals(sessionUser.getUserId())) {
					reArticleService.deleteReArticle(id);
				}
				else {
					errMsg = ErrMsgConfig.getErrMsg(10002);
					return ERROR;
				}
			}

		}
		catch (BaseException e) {
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		}
		catch (Exception e) {
			errMsg = ErrMsgConfig.getErrMsg(10000);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public int getPage() {

		return page;
	}

	public void setPage(int page) {

		this.page = page;
	}

	public int getPageTotal() {

		return pageTotal;
	}

	public void setId(int id) {

		this.id = id;
	}

	public Group getGroup() {

		return group;
	}

	public List<ArticleItem> getItemList() {

		return itemList;
	}

	public String getGid() {

		return gid;
	}

	public void setGid(String gid) {

		this.gid = gid;
	}

	public int getItemId() {

		return itemId;
	}

	public void setItemId(int itemId) {

		this.itemId = itemId;
	}

	public List<Article> getArticleList() {

		return articleList;
	}

	public String getErrMsg() {

		return errMsg;
	}

	public Article getArticle() {

		return article;
	}

	public void setArticleid(int articleid) {

		this.articleid = articleid;
	}

	public void setContent(String content) {

		this.content = content;
	}

	public void setAuthorName(String authorName) {

		this.authorName = authorName;
	}

	public void setArticleService(ArticleService articleService) {

		this.articleService = articleService;
	}

	public void setArticleItemService(ArticleItemService articleItemService) {

		this.articleItemService = articleItemService;
	}

	public void setGroupService(GroupService groupService) {

		this.groupService = groupService;
	}

	public void setReArticleService(ReArticleService reArticleService) {

		this.reArticleService = reArticleService;
	}

	public String getShowType() {

		return showType;
	}

	public List<ReArticle> getReArticleList() {

		return reArticleList;
	}

	public void setReUserName(String reUserName) {

		this.reUserName = reUserName;
	}

	public void setReContent(String reContent) {

		this.reContent = reContent;
	}

	public int getId() {

		return id;
	}

	public void setKeyWord(String keyWord) {

		this.keyWord = keyWord;
	}

	public void setTitle(String title) {

		this.title = title;
	}

	public ReArticle getReArticle() {

		return reArticle;
	}

	public int getArticleid() {

		return articleid;
	}

	public String getShowDelete() {

		return showDelete;
	}

	public void setMemberService(MemberService memberService) {

		this.memberService = memberService;
	}

	public void setAdminArticleService(AdminArticleService adminArticleService) {

		this.adminArticleService = adminArticleService;
	}

	public void setImage(String image) {

		this.image = image;
	}

	public void setCheckCode(String checkCode) {

		this.checkCode = checkCode;
	}

	public String getKeyWord() {

		return keyWord;
	}

}
