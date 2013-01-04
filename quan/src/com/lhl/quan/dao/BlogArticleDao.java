package com.lhl.quan.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.lhl.entity.BlogArticle;

/** 
 * @Title:
 * @Description: 博客文章DAO
 * @author luohl
 * @date 2013-1-4
 * @version V1.0
*/
public class BlogArticleDao extends SqlMapClientDaoSupport
{
	/**
	 * description: 新增博文
	 * @param item
	 * @author luohl
	 */
	public void addBlog(BlogArticle blogArticle) throws Exception
	{

		this.getSqlMapClientTemplate().insert("blogArticle.addBlogArticle", blogArticle);
	}

	/**
	 * 
	 * description: 通过ID查询文章
	 * @param id
	 * @return
	 * @throws Exception
	 * @author luohl
	 */
	public BlogArticle queryBlogById(int id) throws Exception
	{

		return (BlogArticle) this.getSqlMapClientTemplate().queryForObject("blogArticle.queryTopicById", id);
	}

	/**
	 * description: 更新文章
	 * @param item
	 * @author luohl
	 */
	public void update(BlogArticle blogArticle) throws Exception
	{

		this.getSqlMapClientTemplate().update("blogArticle.updateArticle_selective", blogArticle);
	}

	/**
	 * 
	 * description: 批量删除文章
	 * @param id
	 * @throws Exception
	 * @author luohl
	 */
	public void deleteBatch(int[] ids) throws Exception
	{

		this.getSqlMapClientTemplate().delete("blogArticle.deleteArticleItem", ids);
	}

	/**
	 * 
	 * description: 根据userId查询文章
	 * @param id
	 * @throws Exception
	 * @author luohl
	 */
	public List<BlogArticle> queryBlogByUserId(String userId, int offset, int total) throws Exception
	{

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("userId", userId);
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return (List<BlogArticle>) getSqlMapClientTemplate().queryForList("blogArticle.queryBlogByUserId", parmMap);
	}

	/**
	 * 
	 * description: 根据用户ID查询总数
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author luohl
	 */
	public int queryCountByUserId(String userId) throws Exception
	{

		return (Integer) this.getSqlMapClientTemplate().queryForObject("blogArticle.queryCountByUserId", userId);
	}
}
