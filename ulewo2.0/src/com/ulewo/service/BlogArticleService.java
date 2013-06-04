package com.ulewo.service;

import java.util.List;

import com.ulewo.entity.BlogArticle;
import com.ulewo.entity.User;
import com.ulewo.util.PaginationResult;

public interface BlogArticleService {
	/**
	 * 
	 * description: 新增博文
	 * @param blogArticle
	 * @author luohl
	 */
	public boolean addBlog(BlogArticle blogArticle, User user);

	/**
	 * 
	 * description: 单笔查询博客
	 * @param id
	 * @return
	 * @author luohl
	 */
	public BlogArticle queryBlogById(int id);

	/**
	 * 
	 * description: 更新文章
	 * @param blogArticle
	 * @author luohl
	 */
	public boolean update(BlogArticle blogArticle);

	/**
	 * 
	 * description: 更新阅读数
	 * @param blogArticle
	 * @author luohl
	 */
	public void updateReadCount(BlogArticle blogArticle);

	/**
	 * 
	 * description: 删除文章
	 * @param id
	 * @return
	 * @author luohl
	 */
	public void deleteArticle(int id);

	/**
	 * 
	 * description: 通过用户ID查询博客
	 * @param userId
	 * @param offset
	 * @param total
	 * @return
	 * @author luohl
	 */
	public List<BlogArticle> queryBlog(String userId, int itemId, int offset, int total);

	/**
	 * 分页查询用户博文
	 * @param userId
	 * @param itemId
	 * @param offset
	 * @param total
	 * @return
	 */
	public PaginationResult queryBlogByUserId(String userId, int itemId, int page, int pageSize);

	/**
	 * 
	 * description: 查询文章数量
	 * @param userId
	 * @return
	 * @author luohl
	 */
	public int queryBlogCount(String userId, int itemId);

	public List<BlogArticle> indexLatestBlog(int offset, int total);

	public int queryCount();
}
