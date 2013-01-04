package com.lhl.quan.service;

import java.util.List;

import com.lhl.entity.BlogItem;

/** 
 * @Title:
 * @Description: 文章分类业务层
 * @author luohl
 * @date 2012-3-29
 * @version V1.0
*/
public interface BlogItemService
{
	/**
	 * 新增分类
	 * description: 函数的目的/功能
	 * @param item
	 * @author luohl
	 */
	public void addItem(BlogItem item);

	/**
	 * 更新分类
	 * description: 函数的目的/功能
	 * @param item
	 * @author luohl
	 */
	public boolean update(BlogItem item);

	/**
	 * 
	 * description: 删除分类
	 * @param id
	 * @throws Exception
	 * @author luohl
	 */
	public boolean delete(String userId, int id);

	/**
	 * 
	 * description: 根据userId查询分类
	 * @param id
	 * @throws Exception
	 * @author luohl
	 */
	public List<BlogItem> queryBlogItemByUserId(String userId) throws Exception;

	/**
	 * 
	 * description: 根据userId查询分类和文章数
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author luohl
	 */
	public List<BlogItem> queryBlogItemAndCountByUserId(String userId) throws Exception;

}
