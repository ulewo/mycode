package com.lhl.admin.action;

import java.util.ArrayList;
import java.util.List;

import com.lhl.admin.service.AdminUserService;
import com.lhl.common.action.BaseAction;
import com.lhl.config.ErrMsgConfig;
import com.lhl.entity.User;
import com.lhl.exception.BaseException;
import com.lhl.util.Constant;
import com.lhl.util.Pagination;

/** 
 * @Title:
 * @Description: 
 * @author 35sz
 * @date 2012-5-23
 * @version V1.0
*/
public class AdminUserAction extends BaseAction
{

	private AdminUserService adminUserService;

	private String userId;

	private String[] userIds;

	private String userName;

	private int pageTotal;

	private int page;

	private List<User> userList = new ArrayList<User>();

	private String errMsg;

	/**
	 * 
	 * description: 所有用户
	 * @return
	 * @author 35sz
	 */
	public String queryUsers()
	{

		try
		{
			int countNumber = adminUserService.queryUserCount(userName);
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
			userList = adminUserService.queryUsers(userName, noStart, pageSize);
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
	 * description: 删除用户
	 * @return
	 * @author 35sz
	 */
	public String deleteUser()
	{

		try
		{
			adminUserService.deleteUsers(userIds);
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

	public List<User> getUserList()
	{

		return userList;
	}

	public String getErrMsg()
	{

		return errMsg;
	}

	public void setUserName(String userName)
	{

		this.userName = userName;
	}

	public void setUserId(String userId)
	{

		this.userId = userId;
	}

	public void setUserIds(String[] userIds)
	{

		this.userIds = userIds;
	}

	public void setAdminUserService(AdminUserService adminUserService)
	{

		this.adminUserService = adminUserService;
	}

}
