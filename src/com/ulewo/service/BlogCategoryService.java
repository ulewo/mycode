package com.ulewo.service;

import java.util.List;
import java.util.Map;

import com.ulewo.exception.BusinessException;
import com.ulewo.model.BlogCategory;
import com.ulewo.model.SessionUser;

/**
 * @Title:
 * @Description: 博客分类
 * @author luohl
 * @date 2012-3-29
 * @version V1.0
 */
public interface BlogCategoryService {

    /**
     * 单笔查询
     * 
     * @param map
     * @param sessionUser
     *            TODO
     * @return
     * @throws BusinessException
     *             TODO
     */
    public BlogCategory findCategoryById(Map<String, String> map,
	    SessionUser sessionUser) throws BusinessException;

    /**
     * 
     * description: 根据userId查询分类和文章数
     * 
     * @param userId
     * @return
     * @throws Exception
     * @author luohl
     */
    public List<BlogCategory> selectCategoryWithBlogCount(Integer userId);

    /**
     * 保存分类 新增，修改，删除
     * 
     * @param map
     * @param userId
     * @throws BusinessException
     */
    public void saveCategory(Map<String, String> map, Integer userId)
	    throws BusinessException;

}
