package com.ulewo.service;

import java.util.List;
import java.util.Map;

import com.ulewo.exception.BusinessException;
import com.ulewo.model.Attachment;
import com.ulewo.util.UlewoPaginationResult;

/**
 * @Title:
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author luohl
 * @date 2012-3-29
 * @version V1.0
 */
public interface AttachmentService {
	public void addAttached(Attachment file);

	public void deleteAttached(Map<String, String> map);

	public List<Attachment> queryAttachedbyArticleId(Map<String, String> map);

	public Attachment queryFileById(Map<String, String> map);

	public void updateAttachedFile(Map<String, String> map);

	public UlewoPaginationResult<Attachment> attachedTopic(Map<String, String> map) throws BusinessException;

}
