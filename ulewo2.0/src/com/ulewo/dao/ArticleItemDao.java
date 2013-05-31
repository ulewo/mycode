package com.ulewo.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ulewo.entity.ArticleItem;

/** 
 * @Title:
 * @Description: 文章分类Dao
 * @author luohl
 * @date 2012-3-29
 * @version V1.0
*/
public class ArticleItemDao extends SqlMapClientDaoSupport {
	/**
	 * 新增分类
	 * description: 函数的目的/功能
	 * @param item
	 * @author luohl
	 */
	public void addItem(ArticleItem item) throws Exception {

		this.getSqlMapClientTemplate().insert("articleItem.addArticleItem", item);
	}

	/**
	 * 
	 * description: 通过ID单笔查询分类
	 * @param id
	 * @return
	 * @throws Exception
	 * @author luohl
	 */
	public ArticleItem getArticleItemById(int id) throws Exception {

		return (ArticleItem) this.getSqlMapClientTemplate().queryForObject("articleItem.queryItemById", id);
	}

	/**
	 * 更新分类
	 * description: 函数的目的/功能
	 * @param item
	 * @author luohl
	 */
	public void update(ArticleItem item) throws Exception {

		this.getSqlMapClientTemplate().update("articleItem.updateArticleItem", item);
	}

	/**
	 * 
	 * description: 删除分类
	 * @param id
	 * @throws Exception
	 * @author luohl
	 */
	public void delete(int id) throws Exception {

		this.getSqlMapClientTemplate().delete("articleItem.deleteArticleItem", id);
	}

	/**
	 * 
	 * description: 根据gid查询分类
	 * @param id
	 * @throws Exception
	 * @author luohl
	 */
	public List<ArticleItem> queryItemByGid(String gid) throws Exception {

		return (List<ArticleItem>) getSqlMapClientTemplate().queryForList("articleItem.queryItemByGid", gid);
	}

	/**
	 * 
	 * description: 根据gid查询分类数
	 * @param gid
	 * @return
	 * @throws Exception
	 * @author luohl
	 */
	public int queryItemCountByGid(String gid) throws Exception {

		return (Integer) this.getSqlMapClientTemplate().queryForObject("articleItem.queryItemCountByGid", gid);
	}
}
