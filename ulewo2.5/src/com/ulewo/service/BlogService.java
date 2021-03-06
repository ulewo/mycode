package com.ulewo.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ulewo.exception.BusinessException;
import com.ulewo.model.Blog;
import com.ulewo.model.SearchResult;
import com.ulewo.model.SessionUser;
import com.ulewo.util.UlewoPaginationResult;

public interface BlogService {

	/**
	 * 保存
	 * 
	 * @param map
	 * @param sessionUser
	 * @param request TODO
	 * @return
	 */
	public Blog saveBlog(Map<String, String> map, SessionUser sessionUser, HttpServletRequest request) throws BusinessException;

	/**
	 * 
	 * description: 删除文章
	 * 
	 * @param map
	 * @param user
	 *            TODO
	 * @author luohl
	 */
	public void deleteBlogBatch(Map<String, String> map, SessionUser user) throws BusinessException;

	/**
	 * 分页查询用户博文
	 * 
	 * @param map
	 * @param offset
	 * @param total
	 * @return
	 * @throws BusinessException
	 *             TODO
	 */
	public UlewoPaginationResult<Blog> queryBlogByUserId(Map<String, String> map) throws BusinessException;

	/**
	 * 查询博客
	 * 
	 * @param map
	 * @return
	 */
	public List<Blog> queryBlogByUserId4List(Map<String, String> map);

	/**
	 * 单笔查询
	 * 
	 * @param map
	 * @return
	 * @throws BusinessException
	 *             TODO
	 */
	public Blog showBlogById(Map<String, String> map) throws BusinessException;

	public int selectBaseInfoCount(Map<String, String> map);

	public List<Blog> queryLatestBlog4Index() throws BusinessException;

	public UlewoPaginationResult<Blog> queryLatestBlog(Map<String, String> map) throws BusinessException;
	
	public void deleteBlogByAdmin(Map<String, String> map) throws BusinessException;
	
	public Map<String,Object> queryLatestBlog4Api(Map<String, String> map) throws BusinessException;
	
	public Map<String,Object> queryBlogByUserId4Api(Map<String, String> map,Integer userId) throws BusinessException;

	public Map<String,Object> blogDetail4Api(Map<String, String> map, Integer userId) throws BusinessException;
	
	public List<SearchResult> searchByLucene(Map<String, String> map)throws BusinessException;
}
