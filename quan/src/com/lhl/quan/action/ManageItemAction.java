package com.lhl.quan.action;

import java.util.List;

import com.lhl.common.action.BaseAction;
import com.lhl.config.ErrMsgConfig;
import com.lhl.entity.ArticleItem;
import com.lhl.entity.User;
import com.lhl.exception.BaseException;
import com.lhl.quan.service.ArticleItemService;
import com.lhl.util.Constant;

public class ManageItemAction extends BaseAction
{
	private static final long serialVersionUID = 1L;

	private ArticleItemService articleItemService;

	private int id;

	private String itemName;

	private int itemCode;

	private String gid; //群组ID

	private List<ArticleItem> itemList;

	private String errMsg;

	private boolean isAdmin(String groupId)
	{

		User sessionUser = (User) getSession().getAttribute("user");
		int grade = CheckRole.getMemberGrade(groupId, sessionUser.getUserId());
		if (Constant.grade1 == grade || Constant.grade2 == grade || Constant.SUPERADMIN.equals(sessionUser.getUserId()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * 
	 * description: 群分类
	 * @return
	 * @author luohl
	 */
	public String items()
	{

		try
		{
			if (!isAdmin(gid))
			{
				return ERROR;
			}
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
	 * description: 创建栏目
	 * @return
	 * @author luohl
	 */
	public String createItem()
	{

		try
		{
			if (!isAdmin(gid))
			{
				return ERROR;
			}
			ArticleItem item = new ArticleItem();
			item.setItemName(itemName);
			item.setItemCode(itemCode);
			item.setGid(gid);
			articleItemService.addItem(item);
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
	 * description: 删除分类
	 * @return
	 * @author luohl
	 */
	public String deleteItem()
	{

		try
		{
			if (!isAdmin(gid))
			{
				return ERROR;
			}
			articleItemService.delete(id);
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
	 * description: 更新分类
	 * @return
	 * @author luohl
	 */
	public String updateItem()
	{

		try
		{
			if (!isAdmin(gid))
			{
				return ERROR;
			}
			ArticleItem item = new ArticleItem();
			item.setId(id);
			item.setItemName(itemName);
			item.setItemCode(itemCode);
			articleItemService.update(item);
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

	public void setId(int id)
	{

		this.id = id;
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

	public String getErrMsg()
	{

		return errMsg;
	}

	public void setArticleItemService(ArticleItemService articleItemService)
	{

		this.articleItemService = articleItemService;
	}

	public void setItemName(String itemName)
	{

		this.itemName = itemName;
	}

	public void setItemCode(int itemCode)
	{

		this.itemCode = itemCode;
	}

}
