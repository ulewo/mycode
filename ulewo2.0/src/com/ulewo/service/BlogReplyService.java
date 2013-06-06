package com.ulewo.service;

import com.ulewo.entity.BlogReply;
import com.ulewo.util.PaginationResult;

/** 
 * @Title:
 * @Description: 博客回复业务层
 * @author luohl
 * @date 2012-3-29
 * @version V1.0
*/
public interface BlogReplyService {
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
	public PaginationResult queryBlogReplyByBlogId(int blogId, int page, int pageSize);

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

	/**
	 * 通过ID查询回复
	 * @param id
	 * @return
	 */
	public BlogReply queryBlogReplyById(int id);

	/**
	 * 查询所有回复
	 * @param author
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public PaginationResult queryAllReply(String author, int page, int pageSize);

}
