package com.lhl.admin.action;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.lhl.admin.service.AdminArticleService;
import com.lhl.common.action.BaseAction;
import com.lhl.config.ErrMsgConfig;
import com.lhl.entity.Article;
import com.lhl.util.Constant;
import com.lhl.util.Pagination;

/** 
 * @Title:
 * @Description: 
 * @author 35sz
 * @date 2012-5-23
 * @version V1.0
*/
public class AdminArticleAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	private AdminArticleService adminArticleService;

	private int id;

	private int[] ids;

	private String sysCode;

	private String subCode;

	private String image;

	private String keyWord;

	private int pageTotal;

	private int page;

	private List<Article> articleList = new ArrayList<Article>();

	private String errMsg;

	/**
	 * 
	 * description: 所有文章
	 * @return
	 * @author 35sz
	 */
	public String queryArticles() {

		try {
			int countNumber = adminArticleService.queryArticleCount(keyWord, Constant.ISVALIDY);
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
			articleList = adminArticleService.queryList(keyWord, Constant.ISVALIDY, noStart, pageSize);
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
	 * description: 推荐文章
	 * @return
	 * @author 35sz
	 */
	public void commendArticle() {

		String msg = "ok";
		JSONObject obj = new JSONObject();
		try {
			Article article = new Article();
			article.setId(id);
			article.setSysCode(sysCode);
			article.setSubCode(subCode);
			article.setImage(image);
			adminArticleService.commendArticle(article);
		}
		catch (Exception e) {
			msg = "error";
		}
		obj.put("msg", msg);
		getOut().print(String.valueOf(obj));
	}

	/**
	 * 
	 * description: 回收站  已经删除的文章
	 * @return
	 * @author 35sz
	 */
	public String recycleArticle() {

		try {
			int countNumber = adminArticleService.queryArticleCount(keyWord, Constant.ISVALIDN);
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
			articleList = adminArticleService.queryList(keyWord, Constant.ISVALIDN, noStart, pageSize);
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
	 * description: 删除文章
	 * @return
	 * @author 35sz
	 */
	public String deleteArticle() {

		try {
			adminArticleService.deleteArticle(ids);
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

	public List<Article> getArticleList() {

		return articleList;
	}

	public String getErrMsg() {

		return errMsg;
	}

	public void setKeyWord(String keyWord) {

		this.keyWord = keyWord;
	}

	public void setId(int id) {

		this.id = id;
	}

	public void setSysCode(String sysCode) {

		this.sysCode = sysCode;
	}

	public void setSubCode(String subCode) {

		this.subCode = subCode;
	}

	public void setImage(String image) {

		this.image = image;
	}

	public void setIds(int[] ids) {

		this.ids = ids;
	}

	public void setAdminArticleService(AdminArticleService adminArticleService) {

		this.adminArticleService = adminArticleService;
	}

}
