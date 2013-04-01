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

import com.lhl.entity.Article;

/** 
 * @Title:
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 35sz
 * @date 2012-5-23
 * @version V1.0
*/
public interface AdminArticleService {
	public int queryArticleCount(String keyWord, String isValid);

	public List<Article> queryList(String keyWord, String isValid, int offset, int total);

	public void commendArticle(Article article);

	public void deleteArticle(int[] ids);

}
