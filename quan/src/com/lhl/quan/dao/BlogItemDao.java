package com.lhl.quan.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.lhl.entity.BlogItem;

/** 
 * @Title:
 * @Description: 文章分类Dao
 * @author luohl
 * @date 2012-3-29
 * @version V1.0
*/
public class BlogItemDao extends SqlMapClientDaoSupport
{
	/**
	 * 新增分类
	 * description: 函数的目的/功能
	 * @param item
	 * @author luohl
	 */
	public void addItem(BlogItem blogItem)
	{

		this.getSqlMapClientTemplate().insert("blogItem.addBlogItem", blogItem);
	}

	/**
	 * 更新分类
	 * description: 函数的目的/功能
	 * @param item
	 * @author luohl
	 */
	public void update(BlogItem blogItem)
	{

		this.getSqlMapClientTemplate().update("blogItem.updateBlogItem", blogItem);
	}

	/**
	 * 
	 * description: 删除分类
	 * @param id
	 * @throws Exception
	 * @author luohl
	 */
	public void delete(int id)
	{

		this.getSqlMapClientTemplate().delete("blogItem.deleteBlogItem", id);
	}

	/**
	 * 
	 * description: 根据userId查询分类
	 * @param id
	 * @throws Exception
	 * @author luohl
	 */
	public List<BlogItem> queryItemByUserId(String userId)
	{

		return (List<BlogItem>) getSqlMapClientTemplate().queryForList("BlogItem.queryBlogItemByUserId", userId);
	}

	public List<BlogItem> queryBlogItemAndCountByUserId(String userId)
	{

		return (List<BlogItem>) getSqlMapClientTemplate()
				.queryForList("BlogItem.queryBlogItemAndCountByUserId", userId);
	}

	public BlogItem queryBlogItemById(int id)
	{

		return (BlogItem) getSqlMapClientTemplate().queryForObject("BlogItem.queryBlogItemById", id);
	}
}
