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

import com.lhl.entity.User;

/** 
 * @Title:
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 35sz
 * @date 2012-5-23
 * @version V1.0
*/
public interface AdminUserService
{
	public int queryUserCount(String userName) throws Exception;

	public List<User> queryUsers(String userName, int offset, int total) throws Exception;

	public void commendUser(User user) throws Exception;

	public void deleteUsers(String[] userIds) throws Exception;
}
