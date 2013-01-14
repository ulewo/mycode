package com.lhl.quan.service;

import java.util.List;

import com.lhl.entity.BlogReply;

/** 
 * @Title:
 * @Description: 博客回复业务层
 * @author luohl
 * @date 2012-3-29
 * @version V1.0
*/
public interface BlogReplyService
{
	/**
	 * description: 新增回复
	 * @param item
	 * @author luohl
	 */
	public BlogReply addReply(BlogReply blogReply);

	/**
	 * 
	 * description: 根据blogid查询回复
	 * @param id
	 * @throws Exception
	 * @author luohl
	 */
	public List<BlogReply> queryBlogReplyByBlogId(int blogId);

	/**
	 * 
	 * description: 查询博客数量
	 * @param blogId
	 * @return
	 * @author luohl
	 */
	public int queryBlogReplyCountByBlogId(int blogId);

	/**
	 * 
	 * description: 删除回复
	 * @param id
	 * @throws Exception
	 * @author luohl
	 */
	public boolean delete(String userId, int id);

}
