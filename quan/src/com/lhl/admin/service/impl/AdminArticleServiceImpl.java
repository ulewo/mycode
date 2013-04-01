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

import com.lhl.admin.dao.AdminArticleDao;
import com.lhl.admin.service.AdminArticleService;
import com.lhl.entity.Article;

/**
 * @Title:
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 35sz
 * @date 2012-5-23
 * @version V1.0
 */
public class AdminArticleServiceImpl implements AdminArticleService {
	private AdminArticleDao adminArticleDao;

	public void setAdminArticleDao(AdminArticleDao adminArticleDao) {

		this.adminArticleDao = adminArticleDao;
	}

	@Override
	public int queryArticleCount(String keyWord, String isValid) {

		return adminArticleDao.queryArticleCount(keyWord, isValid);
	}

	@Override
	public List<Article> queryList(String keyWord, String isValid, int offset, int total) {

		List<Article> list = adminArticleDao.queryArticle(keyWord, isValid, offset, total);
		return list;
	}

	@Override
	public void commendArticle(Article article) {

		adminArticleDao.updateArticle(article);

	}

	@Override
	public void deleteArticle(int[] ids) {

		adminArticleDao.deleteArticle(ids);

	}

}
