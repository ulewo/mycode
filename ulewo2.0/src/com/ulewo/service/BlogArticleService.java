package com.ulewo.service;

import java.util.List;

import com.ulewo.entity.BlogArticle;
import com.ulewo.enums.BlogOrderType;
import com.ulewo.util.PaginationResult;

public interface BlogArticleService {

	public boolean saveBlog(BlogArticle blogArticle);

	/**
	 * 
	 * description: 新增博文
	 * @param blogArticle
	 * @author luohl
	 */
	public boolean addBlog(BlogArticle blogArticle);

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
	 * @param blogOrderType TODO
	 * @return
	 * @author luohl
	 */
	public List<BlogArticle> queryBlog(String userId, int itemId, int offset, int total, BlogOrderType blogOrderType);

	/**
	 * 分页查询用户博文
	 * @param userId
	 * @param itemId
	 * @param blogOrderType TODO
	 * @param offset
	 * @param total
	 * @return
	 */
	public PaginationResult queryBlogByUserId(String userId, int itemId, int page, int pageSize,
			BlogOrderType blogOrderType);

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

	public PaginationResult queryLatestBlog(int page, int pageSize);

	public int searchBlogCount(String keyword);

	public List<BlogArticle> searchBlog(String keyword, int offset, int total, boolean isHilight);

	public PaginationResult searchBlog2PageResult(String keyword, int page, int pageSize, boolean isHilight);
}
