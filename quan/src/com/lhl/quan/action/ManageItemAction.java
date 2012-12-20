package com.lhl.quan.action;

import java.util.List;

import com.lhl.common.action.BaseAction;
import com.lhl.config.ErrMsgConfig;
import com.lhl.entity.ArticleItem;
import com.lhl.exception.BaseException;
import com.lhl.quan.service.ArticleItemService;

public class ManageItemAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	private ArticleItemService articleItemService;

	private int id;

	private String itemName;

	private int itemCode;

	private String gid; //群组ID

	private List<ArticleItem> itemList;

	private String errMsg;

	/**
	 * 
	 * description: 群分类
	 * @return
	 * @author luohl
	 */
	public String items() {

		try {
			itemList = articleItemService.queryItemByGid(gid);
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
	 * description: 创建栏目
	 * @return
	 * @author luohl
	 */
	public String createItem() {

		try {
			ArticleItem item = new ArticleItem();
			item.setItemName(itemName);
			item.setItemCode(itemCode);
			item.setGid(gid);
			articleItemService.addItem(item);
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
	 * description: 删除分类
	 * @return
	 * @author luohl
	 */
	public String deleteItem() {

		try {
			articleItemService.delete(id);
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
	 * description: 更新分类
	 * @return
	 * @author luohl
	 */
	public String updateItem() {

		try {
			ArticleItem item = new ArticleItem();
			item.setId(id);
			item.setItemName(itemName);
			item.setItemCode(itemCode);
			articleItemService.update(item);
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

	public void setId(int id) {

		this.id = id;
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

	public String getErrMsg() {

		return errMsg;
	}

	public void setArticleItemService(ArticleItemService articleItemService) {

		this.articleItemService = articleItemService;
	}

	public void setItemName(String itemName) {

		this.itemName = itemName;
	}

	public void setItemCode(int itemCode) {

		this.itemCode = itemCode;
	}

}
