package com.ulewo.service;

import java.util.List;
import java.util.Map;

import com.ulewo.exception.BusinessException;
import com.ulewo.model.GroupCategory;
import com.ulewo.model.SessionUser;

/**
 * @Title:
 * @Description: 博客分类
 * @author luohl
 * @date 2012-3-29
 * @version V1.0
 */
public interface GroupCategoryService {

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
	public GroupCategory findCategoryById(Map<String, String> map, SessionUser sessionUser) throws BusinessException;

	/**
	 * 保存分类 新增，修改，删除
	 * 
	 * @param map
	 * @throws BusinessException
	 */
	public void saveCategory(Map<String, String> map) throws BusinessException;

	/**
	 * 查询分类
	 */
	public List<GroupCategory> selectGroupCategoryList(Map<String, String> map) throws BusinessException;
}
