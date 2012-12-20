/* 
 * Copyright (C), 2004-2010, 三五互联科技股份有限公司
 * File Name: com.lhl.manage.dao.ManageDao.java
 * Encoding UTF-8 
 * Version: 1.0 
 * Date: 2012-5-23
 * History:
 * 1. Date: 2012-5-23
 *    Author: 35sz
 *    Modification: 新建
 * 2. ...
 */
package com.lhl.admin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.lhl.entity.User;

/**
 * @Title:
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 35sz
 * @date 2012-5-23
 * @version V1.0
 */
public class AdminUserDao extends SqlMapClientDaoSupport {
	public int queryUserCount(String userName) throws Exception {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(userName)) {
			parmMap.put("userName", "%" + userName + "%");
		}

		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				"user.queryUsersCount", parmMap);
	}

	public List<User> queryUser(String userName, int offset, int total)
			throws Exception {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(userName)) {
			parmMap.put("userName", "%" + userName + "%");
		}
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return this.getSqlMapClientTemplate().queryForList("user.queryUsers",
				parmMap);
	}

	public void updateUser(User user) {

		this.getSqlMapClientTemplate().update(
				"user.updateUser_selective_userId", user);
	}

	public void deleteUser(String[] userIds) {

		this.getSqlMapClientTemplate().delete("user.deleteBatch", userIds);
	}
}
