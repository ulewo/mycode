package com.ulewo.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ulewo.entity.BlogItem;

/** 
 * @Title:
 * @Description: 文章分类Dao
 * @author luohl
 * @date 2012-3-29
 * @version V1.0
*/

@Component
public class BlogItemDao extends BaseDao {
	/**
	 * 新增分类
	 * description: 函数的目的/功能
	 * @param item
	 * @author luohl
	 */
	public int addItem(BlogItem blogItem) {

		return (Integer) this.getSqlMapClientTemplate().insert("blogItem.addBlogItem", blogItem);
	}

	/**
	 * 更新分类
	 * description: 函数的目的/功能
	 * @param item
	 * @author luohl
	 */
	public void update(BlogItem blogItem) {

		this.getSqlMapClientTemplate().update("blogItem.updateBlogItem", blogItem);
	}

	/**
	 * 
	 * description: 删除分类
	 * @param id
	 * @throws Exception
	 * @author luohl
	 */
	public void delete(int id) {

		this.getSqlMapClientTemplate().delete("blogItem.deleteBlogItem", id);
	}

	/**
	 * 
	 * description: 根据userId查询分类
	 * @param id
	 * @throws Exception
	 * @author luohl
	 */
	public List<BlogItem> queryItemByUserId(String userId) {

		return (List<BlogItem>) getSqlMapClientTemplate().queryForList("blogItem.queryBlogItemByUserId", userId);
	}

	public List<BlogItem> queryBlogItemAndCountByUserId(String userId) {

		return (List<BlogItem>) getSqlMapClientTemplate()
				.queryForList("blogItem.queryBlogItemAndCountByUserId", userId);
	}

	public BlogItem queryBlogItemById(int id) {

		return (BlogItem) getSqlMapClientTemplate().queryForObject("blogItem.queryBlogItemById", id);
	}
}
