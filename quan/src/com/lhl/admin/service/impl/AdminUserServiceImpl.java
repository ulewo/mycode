/* 
 * Copyright (C), 2004-2010, 三五互联科技股份有限公司
 * File Name: com.lhl.manage.service.impl.ManageArticleServiceImpl.java
 * Encoding UTF-8 
 * Version: 1.0 
 * Date: 2012-5-23
 * History:
 * 1. Date: 2012-5-23
 *    Author: 35sz
 *    Modification: 新建
 * 2. ...
 */
package com.lhl.admin.service.impl;

import java.util.List;

import com.lhl.admin.dao.AdminUserDao;
import com.lhl.admin.service.AdminUserService;
import com.lhl.entity.User;

/** 
 * @Title:
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 35sz
 * @date 2012-5-23
 * @version V1.0
*/
public class AdminUserServiceImpl implements AdminUserService
{
	private AdminUserDao adminUserDao;

	public void setAdminUserDao(AdminUserDao adminUserDao)
	{

		this.adminUserDao = adminUserDao;
	}

	@Override
	public int queryUserCount(String userName) throws Exception
	{

		return adminUserDao.queryUserCount(userName);
	}

	@Override
	public List<User> queryUsers(String userName, int offset, int total) throws Exception
	{

		return adminUserDao.queryUser(userName, offset, total);
	}

	@Override
	public void commendUser(User user) throws Exception
	{

		adminUserDao.updateUser(user);

	}

	@Override
	public void deleteUsers(String[] userIds) throws Exception
	{

		adminUserDao.deleteUser(userIds);

	}

}
