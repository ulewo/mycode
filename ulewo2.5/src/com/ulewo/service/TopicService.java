package com.ulewo.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ulewo.exception.BusinessException;
import com.ulewo.model.SearchResult;
import com.ulewo.model.SessionUser;
import com.ulewo.model.Topic;
import com.ulewo.util.UlewoPaginationResult;
import com.ulewo.util.UlewoPaginationResult4Json;

public interface TopicService {
	/**
	 * 新增主题
	 * 
	 * @param map
	 * @param sessionUser
	 * @param surveyTitles
	 *            TODO
	 * @param request
	 *            TODO
	 * @return
	 * @throws BusinessException
	 *             TODO
	 */
	public Topic addTopic(Map<String, String> map, SessionUser sessionUser,
			String[] surveyTitles, HttpServletRequest request)
			throws BusinessException;

	/**
	 * 修改主题
	 * 
	 * @param map
	 * @param sessionUser
	 * @param request
	 *            TODO
	 * @throws BusinessException
	 *             TODO
	 */
	public void updateTopic(Map<String, String> map, SessionUser sessionUser,
			HttpServletRequest request) throws BusinessException;

	/**
	 * 删除主题
	 * 
	 * @param map
	 * @param sessionUser
	 * @throws BusinessException
	 *             TODO
	 */
	public void deleteTopic(Map<String, String> map, SessionUser sessionUser)
			throws BusinessException;

	/**
	 * 查询群组文章
	 * 
	 * @param map
	 * @return
	 * @throws BusinessException
	 *             TODO
	 */
	public UlewoPaginationResult<Topic> findTopics(Map<String, String> map)
			throws BusinessException;

	/**
	 * 查询群组文章
	 * 
	 * @param map
	 * @return
	 * @throws BusinessException
	 */
	public UlewoPaginationResult4Json<Topic> findTopics4Json(
			Map<String, String> map) throws BusinessException;

	/**
	 * 文章详情
	 * 
	 * @param map
	 * @return
	 */
	public Topic showTopic(Map<String, String> map) throws BusinessException;

	/**
	 * 主题置顶
	 * 
	 * @param map
	 * @param sessionUser
	 * @throws BusinessException
	 */
	public void topTopic(Map<String, String> map, SessionUser sessionUser)
			throws BusinessException;

	/**
	 * 主题加精华
	 * 
	 * @param map
	 * @param sessionUser
	 * @throws BusinessException
	 */
	public void essenceTopic(Map<String, String> map, SessionUser sessionUser)
			throws BusinessException;

	public void deleteTopicByAdmin(Map<String, String> map)
			throws BusinessException;

	/**
	 * 热点文章
	 * 
	 * @param map
	 * @return
	 * @throws BusinessException
	 */
	public List<Topic> hotTopics(Map<String, String> map,
			HttpServletRequest request)
			throws BusinessException;

	/**
	 * 查询窝窝文章
	 * 
	 * @param map
	 * @return
	 */
	public Map<String, Object> findTopics4Api(Map<String, String> map)
			throws BusinessException;

	public Map<String, Object> showTopic4Api(Map<String, String> map,
			Integer userId) throws BusinessException;

	public void saveTopic4Api(Map<String, String> map, SessionUser sessionUser,
			HttpServletRequest request) throws BusinessException;

	public Map<String, List<Topic>> getTopic4Index();
	
	
	public List<SearchResult> searchByLucene(Map<String,String> map)throws BusinessException;
}
