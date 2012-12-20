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

import com.lhl.entity.Article;

/**
 * @Title:
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 35sz
 * @date 2012-5-23
 * @version V1.0
 */
public class AdminArticleDao extends SqlMapClientDaoSupport {

	public int queryArticleCount(String keyWord, String isValid)
			throws Exception {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(keyWord)) {
			parmMap.put("keyWord", "%" + keyWord + "%");
		}
		parmMap.put("isValid", isValid);
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				"article.searchTopicCount", parmMap);
	}

	public List<Article> queryArticle(String keyWord, String isValid,
			int offset, int total) throws Exception {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(keyWord)) {
			parmMap.put("keyWord", "%" + keyWord + "%");
		}
		parmMap.put("isValid", isValid);
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return this.getSqlMapClientTemplate().queryForList(
				"article.searchTopic", parmMap);
	}

	public void updateArticle(Article article) {

		this.getSqlMapClientTemplate().update(
				"article.updateArticle_selective", article);
	}

	public void deleteArticle(int[] ids) {

		this.getSqlMapClientTemplate().delete("article.deleteBatch", ids);
	}
}
