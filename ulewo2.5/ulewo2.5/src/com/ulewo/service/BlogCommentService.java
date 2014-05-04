package com.ulewo.service;

import java.util.Map;

import com.ulewo.exception.BusinessException;
import com.ulewo.model.BlogComment;
import com.ulewo.model.SessionUser;
import com.ulewo.util.UlewoPaginationResult;

/**
 * @Title:
 * @Description: 博客回复业务层
 * @author luohl
 * @date 2012-3-29
 * @version V1.0
 */
public interface BlogCommentService {
    /**
     * description: 新增回复
     * 
     * @param sessionUser
     *            TODO
     * @param item
     * @author luohl
     */
    public BlogComment addBlogComment(Map<String, String> map,
	    SessionUser sessionUser) throws BusinessException;

    /**
     * 批量删除评论
     * 
     * @param param
     * @param userId
     * @throws BusinessException
     */
    public void deleteBatch(Map<String, String> param, Integer userId)
	    throws BusinessException;

    /**
     * 
     * description: 根据blogid查询回复
     * 
     * @param map
     *            TODO
     * @param id
     * @throws BusinessException
     *             TODO
     * @throws Exception
     * @author luohl
     */
    public UlewoPaginationResult<BlogComment> queryBlogCommentByBlogId(
	    Map<String, String> map) throws BusinessException;

}
