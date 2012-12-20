package com.lhl.quan.service;

import java.util.List;

import com.lhl.entity.ArticleItem;

/** 
 * @Title:
 * @Description: 文章分类业务层
 * @author luohl
 * @date 2012-3-29
 * @version V1.0
*/
public interface ArticleItemService
{
	/**
	 * 新增分类
	 * description: 函数的目的/功能
	 * @param item
	 * @author luohl
	 */
	public void addItem(ArticleItem item) throws Exception;

	/**
	 * 
	 * description: 单笔查询分类
	 * @param itemId
	 * @return
	 * @throws Exception
	 * @author luohl
	 */
	public ArticleItem getArticleItem(int itemId) throws Exception;

	/**
	 * 更新分类
	 * description: 函数的目的/功能
	 * @param item
	 * @author luohl
	 */
	public void update(ArticleItem item) throws Exception;

	/**
	 * 
	 * description: 删除分类
	 * @param id
	 * @throws Exception
	 * @author luohl
	 */
	public void delete(int id) throws Exception;

	/**
	 * 
	 * description: 根据gid查询分类
	 * @param id
	 * @throws Exception
	 * @author luohl
	 */
	public List<ArticleItem> queryItemByGid(String gid) throws Exception;

	/**
	 * 
	 * description: 根据gid查询分类数
	 * @param gid
	 * @return
	 * @throws Exception
	 * @author luohl
	 */
	public int queryItemCountByGid(String gid) throws Exception;
}
