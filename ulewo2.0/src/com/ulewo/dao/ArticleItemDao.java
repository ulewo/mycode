package com.ulewo.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ulewo.entity.ArticleItem;

/**
 * @Title:
 * @Description: 文章分类Dao
 * @author luohl
 * @date 2012-3-29
 * @version V1.0
 */
@Component
public class ArticleItemDao extends BaseDao {
	/**
	 * 新增分类 description: 函数的目的/功能
	 * 
	 * @param item
	 * @author luohl
	 */
	public void addItem(ArticleItem item) {

		this.getSqlMapClientTemplate().insert("articleItem.addArticleItem", item);
	}

	/**
	 * 
	 * description: 通过ID单笔查询分类
	 * 
	 * @param id
	 * @return @
	 * @author luohl
	 */
	public ArticleItem getArticleItemById(int id) {

		return (ArticleItem) this.getSqlMapClientTemplate().queryForObject("articleItem.queryItemById", id);
	}

	/**
	 * 更新分类 description: 函数的目的/功能
	 * 
	 * @param item
	 * @author luohl
	 */
	public void update(ArticleItem item) {

		this.getSqlMapClientTemplate().update("articleItem.updateArticleItem", item);
	}

	/**
	 * 
	 * description: 删除分类
	 * 
	 * @param id
	 *            @
	 * @author luohl
	 */
	public void delete(int id) {

		this.getSqlMapClientTemplate().delete("articleItem.deleteArticleItem", id);
	}

	/**
	 * 
	 * description: 根据gid查询分类
	 * 
	 * @param id
	 *            @
	 * @author luohl
	 */
	public List<ArticleItem> queryItemByGid(String gid) {

		return (List<ArticleItem>) getSqlMapClientTemplate().queryForList("articleItem.queryItemByGid", gid);
	}

	public List<ArticleItem> queryItemAndTopicCountByGid(String gid) {

		return (List<ArticleItem>) getSqlMapClientTemplate().queryForList("articleItem.queryItemAndTopicCountByGid",
				gid);
	}

	/**
	 * 
	 * description: 根据gid查询分类数
	 * 
	 * @param gid
	 * @return @
	 * @author luohl
	 */
	public int queryItemCountByGid(String gid) {

		return (Integer) this.getSqlMapClientTemplate().queryForObject("articleItem.queryItemCountByGid", gid);
	}
}
