package com.ulewo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ulewo.entity.BlogReply;

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
	 * @throws Exception
	 * @author luohl
	 */
	public void delete(int id) {

		this.getSqlMapClientTemplate().delete("blogReply.deletePeply", id);
	}

	public int queryReplyCountByBlogId(int blogId) {

		return (Integer) this.getSqlMapClientTemplate().queryForObject("blogReply.queryReplyCountByBlogId", blogId);
	}
}
