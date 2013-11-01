package com.ulewo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ulewo.entity.BlogReply;
import com.ulewo.entity.ReTalk;

/**
 * @Title:
 * @Description: 博客回复
 * @author luohl
 * @date 2012-3-29
 * @version V1.0
 */
@Component
public class BlogReplyDao extends BaseDao {
	/**
	 * 新增分类 description: 函数的目的/功能
	 * 
	 * @param item
	 * @author luohl
	 */
	public int addReply(BlogReply blogReply) {

		return (Integer) this.getSqlMapClientTemplate().insert("blogReply.addPeply", blogReply);
	}

	/**
	 * 
	 * description: 根据blogid查询回复
	 * @param offset TODO
	 * @param pageSize TODO
	 * @param id
	 * 
	 * @throws Exception
	 * @author luohl
	 */
	public List<BlogReply> queryReplyByBlogId(int blogId, int offset, int pageSize) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("blogId", blogId);
		paramMap.put("offset", offset);
		paramMap.put("pageSize", pageSize);
		return (List<BlogReply>) getSqlMapClientTemplate().queryForList("blogReply.queryReplyByBlogId", paramMap);
	}

	/**
	 * 
	 * description: 单笔查询博客回复
	 * 
	 * @param id
	 * @return
	 * @author luohl
	 */
	public BlogReply queryBlogReplyById(int id) {

		return (BlogReply) getSqlMapClientTemplate().queryForObject("blogReply.queryPeplyById", id);
	}

	/**
	 * 
	 * description: 删除回复
	 * 
	 * @param id
	 * @param author TODO
	 * @return TODO
	 * @throws Exception
	 * @author luohl
	 */
	public boolean delete(int id, String author) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("blogAuthor", author);
		int count = this.getSqlMapClientTemplate().delete("blogReply.deleteReply", paramMap);
		if (count > 0) {
			return true;
		}
		return false;
	}

	public int queryReplyCountByBlogId(int blogId) {

		return (Integer) this.getSqlMapClientTemplate().queryForObject("blogReply.queryReplyCountByBlogId", blogId);
	}

	public List<BlogReply> queryAllReply(String author, int offSet, int pageSize) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("blogAuthor", author);
		paramMap.put("offSet", offSet);
		paramMap.put("pageSize", pageSize);
		return (List<BlogReply>) getSqlMapClientTemplate().queryForList("blogReply.queryAllReply", paramMap);
	}

	public int queryAllReplyCount(String author) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("blogAuthor", author);
		return (Integer) getSqlMapClientTemplate().queryForObject("blogReply.queryAllReplyCount", paramMap);
	}

	/**
	 * 
	 * description: 根据userId查询主题数量
	 * @param userId
	 * @return
	 * @
	 * @author luohl
	 */
	public int queryReBlogCountByUserId(String userId, int includeme) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		paramMap.put("includeme", includeme);
		return (Integer) this.getSqlMapClientTemplate().queryForObject("blogReply.queryReBlogCountByUserId", paramMap);
	}

	/**
	 * 
	 * description: 根据userId查询文章主题
	 * @param userId
	 * @param offset
	 * @param total
	 * @return
	 * @
	 * @author luohl
	 */
	public List<BlogReply> queryReBlogByUserId(int offset, int total, String userId, int includeme) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("userId", userId);
		parmMap.put("includeme", includeme);
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return this.getSqlMapClientTemplate().queryForList("blogReply.queryReBlogByUserId", parmMap);
	}
	
	public List<BlogReply> queryAllReBlog(int offset, int total) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return (List<BlogReply>) this.getSqlMapClientTemplate().queryForList("blogReply.queryAllReBlog", parmMap);
	}

	/**
	 * 查询数量
	 * 
	 * @return
	 */
	public int queryAllCount() {

		return (Integer) this.getSqlMapClientTemplate().queryForObject("blogReply.queryAllCount");
	}
	
	public boolean deleteById(int id) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		int count = this.getSqlMapClientTemplate().delete("blogReply.deleteReplyById", paramMap);
		if (count > 0) {
			return true;
		}
		return false;
	}
}
