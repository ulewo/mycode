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
package com.ulewo.service;

import com.ulewo.entity.ReArticle;
import com.ulewo.util.PaginationResult;

/**
 * @Title:
 * @Description: 回复文章业务处理
 * @author luohl
 * @date 2012-3-30
 * @version V1.0
 */
public interface ReArticleService {
	/**
	 * 
	 * description: 新增回复
	 * 
	 * @param reArticle
	 * @return TODO @
	 * @author luohl
	 */
	public ReArticle addReArticle(ReArticle reArticle);

	/**
	 * 
	 * description: 删除回复
	 * 
	 * @param id
	 *            @
	 * @author luohl
	 */
	public void deleteReArticle(int id);

	/**
	 * 
	 * description: 单笔查询回复
	 * 
	 * @param id
	 * @return
	 * @author luohl
	 */
	public ReArticle getReArticle(int id);

	/**
	 * 
	 * description: 更新回复
	 * 
	 * @param reArticle
	 *            @
	 * @author luohl
	 */
	public void updateReArticle(ReArticle reArticle);


	/**
	 * 
	 * description: 分页查询回复
	 * 
	 * @param articleid
	 * @param offset
	 * @param total
	 * @return @
	 * @author luohl
	 */
	public PaginationResult queryReArticles(int articleid, int page,
			int pageSize);

	/***
	 * 分页查询所有窝窝所有回复
	 * 
	 * @param gid
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public PaginationResult queryReArticleByGid(String gid, int page,
			int pageSize);
	
	/**
	 * 后台管理
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public PaginationResult queryAllReArticle(int page, int pageSize);
	
	public void deleteReArticleBatch(String keyStr);

}
