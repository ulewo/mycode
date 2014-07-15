package com.ulewo.service;

import java.util.Map;

import com.ulewo.exception.BusinessException;
import com.ulewo.model.Notice;
import com.ulewo.util.UlewoPaginationResult;

/**
 * @Title:
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author luohl
 * @date 2012-3-29
 * @version V1.0
 */
public interface NoticeService {

    public UlewoPaginationResult<Notice> queryNoticeByUserId(Map<String, String> map,
	    Integer userId);

    public int queryNoticeCountByUserId(Map<String, String> map);

    public Notice readNotice(Map<String, String> map, Integer userId);

    public void signNoticeRead(Map<String, String> map, Integer userId)
	    throws BusinessException;

    public void deleteNotice(Map<String, String> map, Integer userId)
	    throws BusinessException;
}
