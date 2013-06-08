package com.ulewo.service;

import java.util.List;

import com.ulewo.entity.ArticleItem;

/** 
 * @Title:
 * @Description: 文章分类业务层
 * @author luohl
 * @date 2012-3-29
 * @version V1.0
*/
public interface ArticleItemService {
	/**
	 * 新增分类
	 * description: 函数的目的/功能
	 * @param item
	 * @author luohl
	 */
	public void addItem(ArticleItem item);

	/**
	 * 
	 * description: 单笔查询分类
	 * @param itemId
	 * @return
	 * @
	 * @author luohl
	 */
	public ArticleItem getArticleItem(int itemId);

	/**
	 * 更新分类
	 * description: 函数的目的/功能
	 * @param item
	 * @author luohl
	 */
	public void update(ArticleItem item);

	/**
	 * 
	 * description: 删除分类
	 * @param id
	 * @
	 * @author luohl
	 */
	public void delete(int id);

	/**
	 * 
	 * description: 根据gid查询分类
	 * @param id
	 * @
	 * @author luohl
	 */
	public List<ArticleItem> queryItemByGid(String gid);

	public List<ArticleItem> queryItemAndTopicCountByGid(String gid);

	/**
	 * 
	 * description: 根据gid查询分类数
	 * @param gid
	 * @return
	 * @
	 * @author luohl
	 */
	public int queryItemCountByGid(String gid);
}
