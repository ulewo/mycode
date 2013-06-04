package com.ulewo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ulewo.entity.BlogArticle;

/** 
 * @Title:
 * @Description: 博客文章DAO
 * @author luohl
 * @date 2013-1-4
 * @version V1.0
*/
public class BlogArticleDao extends SqlMapClientDaoSupport {
	/**
	 * description: 新增博文
	 * @param item
	 * @author luohl
	 */
	public int addBlog(BlogArticle blogArticle) {

		return (Integer) this.getSqlMapClientTemplate().insert("blogArticle.addBlogArticle", blogArticle);
	}

	/**
	 * 
	 * description: 通过ID查询文章
	 * @param id
	 * @return
	 * @throws Exception
	 * @author luohl
	 */
	public BlogArticle queryBlogById(int id) {

		return (BlogArticle) this.getSqlMapClientTemplate().queryForObject("blogArticle.queryTopicById", id);
	}

	/**
	 * description: 更新文章
	 * @param item
	 * @author luohl
	 */
	public void update(BlogArticle blogArticle) {

		this.getSqlMapClientTemplate().update("blogArticle.updateArticle_selective", blogArticle);
	}

	/**
	 * description: 更新阅读数
	 * @param item
	 * @author luohl
	 */
	public void updateReadCount(BlogArticle blogArticle) {

		this.getSqlMapClientTemplate().update("blogArticle.updatReadCount", blogArticle);
	}

	/**
	 * 
	 * description: 批量删除文章
	 * @param id
	 * @throws Exception
	 * @author luohl
	 */
	public void deleteBatch(int[] ids) {

		this.getSqlMapClientTemplate().delete("blogArticle.deleteArticleItem", ids);
	}

	public void delete(int id) {

		this.getSqlMapClientTemplate().delete("blogArticle.deleteArticle", id);
	}

	/**
	 * 
	 * description: 根据userId ,itemId查询文章
	 * @param id
	 * @throws Exception
	 * @author luohl
	 */
	public List<BlogArticle> queryBlog(String userId, int itemId, int offset, int total) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("userId", userId);
		parmMap.put("itemId", itemId);
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return (List<BlogArticle>) getSqlMapClientTemplate().queryForList("blogArticle.queryBlog", parmMap);
	}

	/**
	 * 查询数量
	 * @param userId
	 * @param itemId
	 * @return
	 */
	public int queryBlogCount(String userId, int itemId) {

		return (Integer) this.getSqlMapClientTemplate().queryForObject("blogArticle.queryBlogCount", userId);
	}

	public List<BlogArticle> indexLatestBlog(int offset, int total) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return (List<BlogArticle>) getSqlMapClientTemplate().queryForList("blogArticle.indexLatestBlog", parmMap);
	}

	public int queryCount() {

		return (Integer) getSqlMapClientTemplate().queryForObject("blogArticle.querCount");
	}

}
