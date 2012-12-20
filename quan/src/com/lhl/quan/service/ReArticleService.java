/* 
 * Copyright (C), 2004-2010, 三五互联科技股份有限公司
 * File Name: com.lhl.quan.service.ReArticleService.java
 * Encoding UTF-8 
 * Version: 1.0 
 * Date: 2012-3-30
 * History:
 * 1. Date: 2012-3-30
 *    Author: luohl
 *    Modification: 新建
 * 2. ...
 */
package com.lhl.quan.service;

import java.util.List;

import com.lhl.entity.ReArticle;

/** 
 * @Title:
 * @Description: 回复文章业务处理
 * @author luohl
 * @date 2012-3-30
 * @version V1.0
*/
public interface ReArticleService
{
	/**
	 * 
	 * description: 新增回复
	 * @param reArticle
	 * @return TODO
	 * @throws Exception
	 * @author luohl
	 */
	public ReArticle addReArticle(ReArticle reArticle) throws Exception;

	/**
	 * 
	 * description: 删除回复
	 * @param id
	 * @throws Exception
	 * @author luohl
	 */
	public void deleteReArticle(int id) throws Exception;

	/**
	 * 
	 * description: 单笔查询回复
	 * @param id
	 * @return
	 * @author luohl
	 */
	public ReArticle getReArticle(int id) throws Exception;

	/**
	 * 
	 * description: 更新回复
	 * @param reArticle
	 * @throws Exception
	 * @author luohl
	 */
	public void updateReArticle(ReArticle reArticle) throws Exception;

	/**
	 * 
	 * description: 查询回复总条数
	 * @param articleid
	 * @return
	 * @throws Exception
	 * @author luohl
	 */
	public int queryReArticleCount(int articleid) throws Exception;

	/**
	 * 
	 * description: 分页查询回复
	 * @param articleid
	 * @param offset
	 * @param total
	 * @return
	 * @throws Exception
	 * @author luohl
	 */
	public List<ReArticle> queryReArticles(int articleid, int offset, int total) throws Exception;

}
