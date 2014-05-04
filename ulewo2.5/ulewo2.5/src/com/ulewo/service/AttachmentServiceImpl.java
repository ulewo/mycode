package com.ulewo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ulewo.enums.PageSize;
import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.AttachmentMapper;
import com.ulewo.model.Attachment;
import com.ulewo.util.SimplePage;
import com.ulewo.util.StringUtils;
import com.ulewo.util.UlewoPaginationResult;

@Service("attachedService")
public class AttachmentServiceImpl implements AttachmentService {

	@Autowired
	private AttachmentMapper<Attachment> attachmentMapper;

	@Override
	public void addAttached(Attachment file) {
		attachmentMapper.insert(file);
	}

	@Override
	public void deleteAttached(Map<String, String> map) {
		attachmentMapper.delete(map);
	}

	@Override
	public List<Attachment> queryAttachedbyArticleId(Map<String, String> map) {
		return attachmentMapper.selectBaseInfoList(map, null);
	}

	public Attachment queryFileById(Map<String, String> map) {
		return attachmentMapper.selectBaseInfo(map);
	}

	public void updateAttachedFile(Map<String, String> map) {
		Attachment attachment = new Attachment();
		attachmentMapper.updateSelective(attachment);
	}

	public UlewoPaginationResult<Attachment> attachedTopic(Map<String, String> map) throws BusinessException {

		String gid = map.get("gid");
		int page_no = 0;
		if (StringUtils.isNumber(map.get("page"))) {
			page_no = Integer.parseInt(map.get("page"));
		}
		if (!StringUtils.isNumber(gid)) {
			throw new BusinessException("参数错误!");
		}
		int count = this.attachmentMapper.selectAttachment4TopicCount(map);
		SimplePage page = new SimplePage(page_no, count, PageSize.SIZE20.getSize());
		List<Attachment> list = this.attachmentMapper.selectAttachment4Topic(map, page);
		UlewoPaginationResult<Attachment> result = new UlewoPaginationResult<Attachment>(page, list);
		return result;
	}
}
