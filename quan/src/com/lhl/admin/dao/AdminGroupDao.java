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

import com.lhl.entity.Group;

/**
 * @Title:
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 35sz
 * @date 2012-5-23
 * @version V1.0
 */
public class AdminGroupDao extends SqlMapClientDaoSupport {
	public int queryGroupCount(String keyWord, String isValid) throws Exception {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(keyWord)) {
			parmMap.put("keyWord", "%" + keyWord + "%");
		}

		parmMap.put("isValid", isValid);
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				"group.queryGroupsCount", parmMap);
	}

	// 查询所有群组，根据访问量排序,创建时间
	public List<Group> queryGroups(String keyWord, String isValid,
			String orderBy, int offset, int total) throws Exception {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(keyWord)) {
			parmMap.put("keyWord", "%" + keyWord + "%");
		}
		parmMap.put("isValid", isValid);
		parmMap.put("orderBy", orderBy);
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return this.getSqlMapClientTemplate().queryForList("group.queryGroups",
				parmMap);
	}

	// 查询所有群组，根据成员数
	public List<Group> queryGroupsByMemberCount(String keyWord, String isValid,
			int offset, int total) throws Exception {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(keyWord)) {
			parmMap.put("keyWord", "%" + keyWord + "%");
		}
		parmMap.put("isValid", isValid);
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return this.getSqlMapClientTemplate().queryForList(
				"group.queryGroupsByMemberCount", parmMap);
	}

	// 查询所有群组 根据文章数量排序

	public List<Group> queryGroupsByArticleCount(String keyWord,
			String isValid, int offset, int total) throws Exception {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(keyWord)) {
			parmMap.put("keyWord", "%" + keyWord + "%");
		}
		parmMap.put("isValid", isValid);
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return this.getSqlMapClientTemplate().queryForList(
				"group.queryGroupsByArticleCount", parmMap);
	}

	public void updateGroup(Group group) {

		this.getSqlMapClientTemplate().update("group.updateGroup_selective",
				group);
	}

	public void deleteGroup(String[] userIds) {

		this.getSqlMapClientTemplate().delete("group.deleteBatch", userIds);
	}
}
