package com.ulewo.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ulewo.exception.BusinessException;
import com.ulewo.model.Spider;
import com.ulewo.util.UlewoPaginationResult;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-6-4 上午10:39:29
 * @version 0.1.0
 * @copyright yougou.com
 */
public interface SpidrService {

	public UlewoPaginationResult<Spider> querySpiderList(Map<String, String> map)
			throws BusinessException;

	public void spiderArticle(String type) throws BusinessException;

	public void sendTopic(Map<String, String> map, HttpServletRequest request)
			throws BusinessException;

	public void sendTopicLocal(Map<String, String> map,
			HttpServletRequest request) throws BusinessException;
}
