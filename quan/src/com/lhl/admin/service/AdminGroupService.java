/* 
 * Copyright (C), 2004-2010, 三五互联科技股份有限公司
 * File Name: com.lhl.manage.service.impl.ManageArticleService.java
 * Encoding UTF-8 
 * Version: 1.0 
 * Date: 2012-5-23
 * History:
 * 1. Date: 2012-5-23
 *    Author: 35sz
 *    Modification: 新建
 * 2. ...
 */
package com.lhl.admin.service;

import java.util.List;

import com.lhl.entity.Group;

/** 
 * @Title:
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 35sz
 * @date 2012-5-23
 * @version V1.0
*/
public interface AdminGroupService
{
	public int queryGroupsCount(String keyWord, String isValid) throws Exception;

	public List<Group> queryGroups(String keyWord, String isValid, String orderInfo, int offset, int total)
			throws Exception;

	public void validGroup(Group group) throws Exception;

	public void deleteGroups(String[] userIds) throws Exception;
}
