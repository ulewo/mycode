/* 
 * Copyright (C), 2004-2010, 三五互联科技股份有限公司
 * File Name: com.lhl.manage.service.impl.ManageGroupServiceImpl.java
 * Encoding UTF-8 
 * Version: 1.0 
 * Date: 2012-5-25
 * History:
 * 1. Date: 2012-5-25
 *    Author: 35sz
 *    Modification: 新建
 * 2. ...
 */
package com.lhl.admin.service.impl;

import java.util.List;

import com.lhl.admin.dao.AdminGroupDao;
import com.lhl.admin.service.AdminGroupService;
import com.lhl.entity.Group;

/** 
 * @Title:
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 35sz
 * @date 2012-5-25
 * @version V1.0
*/
public class AdminGroupServiceImpl implements AdminGroupService
{

	private AdminGroupDao adminGroupDao;

	@Override
	public int queryGroupsCount(String keyWord, String isValid) throws Exception
	{

		return adminGroupDao.queryGroupCount(keyWord, isValid);
	}

	@Override
	public List<Group> queryGroups(String keyWord, String isValid, String orderInfo, int offset, int total)
			throws Exception
	{

		if ("articleCount".equals(orderInfo))
		{
			return adminGroupDao.queryGroupsByArticleCount(keyWord, isValid, offset, total);
		}
		if ("memberCount".equals(orderInfo))
		{
			return adminGroupDao.queryGroupsByMemberCount(keyWord, isValid, offset, total);
		}
		if ("createtime".equals(orderInfo))
		{
			return adminGroupDao.queryGroups(keyWord, isValid, "createtime", offset, total);
		}

		if ("visitcount".equals(orderInfo))
		{
			return adminGroupDao.queryGroups(keyWord, isValid, "visitcount", offset, total);
		}
		return adminGroupDao.queryGroups(keyWord, isValid, "visitcount", offset, total);
	}

	@Override
	public void validGroup(Group group) throws Exception
	{

		adminGroupDao.updateGroup(group);

	}

	@Override
	public void deleteGroups(String[] userIds) throws Exception
	{

		adminGroupDao.deleteGroup(userIds);

	}

	public void setAdminGroupDao(AdminGroupDao adminGroupDao)
	{

		this.adminGroupDao = adminGroupDao;
	}

}
