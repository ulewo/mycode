package com.ulewo.service;

import java.util.List;
import java.util.Map;

import com.ulewo.exception.BusinessException;
import com.ulewo.model.SessionUser;
import com.ulewo.model.TopicCategory;
import com.ulewo.util.UlewoPaginationResult4Json;

/**
 * @Title:
 * @Description: 文章分类
 * @author luohl
 * @date 2012-3-29
 * @version V1.0
 */
public interface TopicCategoryService {
    /**
     * 新增分类 description: 函数的目的/功能
     * 
     * @param map
     * @param userId
     *            TODO
     * @author luohl
     */
    public void saveCategroy(Map<String, String> map,
	    Integer userId) throws BusinessException;

    /**
     * 更新分类 description: 函数的目的/功能
     * 
     * @param map
     * @param sessionUser
     *            TODO
     * @author luohl
     * @throws BusinessException
     *             TODO
     */
    public void updateCategory(Map<String, String> map, SessionUser sessionUser)
	    throws BusinessException;

    /**
     * 
     * description: 单笔查询分类
     * 
     * @param map
     * @return @
     * @author luohl
     * @throws BusinessException
     *             TODO
     */
    public TopicCategory getCategroy(Map<String, String> map) throws BusinessException;

    /**
     * 
     * description: 删除分类
     * 
     * @param map
     * @param sessionUser
     *            TODO @
     * @author luohl
     * @throws BusinessException
     *             TODO
     */
    public void deleteCategroy(Map<String, String> map, SessionUser sessionUser)
	    throws BusinessException;

    /**
     * 
     * description: 根据gid查询分类
     * 
     * @param id
     *            @
     * @author luohl
     */
    public List<TopicCategory> queryCategoryAndTopicCount(
	    Map<String, String> map);

    public UlewoPaginationResult4Json<TopicCategory> queryCategory4Json(
	    Map<String, String> map);

}
