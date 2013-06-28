package com.ulewo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ulewo.entity.AttachedFile;
import com.ulewo.enums.FileType;

@Component
public class AttachedFileDao extends BaseDao {

	/**
	 * 创建消息
	 * 
	 * @param member
	 * @throws Exception
	 */
	public void addAttached(AttachedFile file) {

		this.getSqlMapClientTemplate().insert("attachedFile.addAttached", file);
	}

	/**
	 * 删除消息
	 * 
	 * @param id
	 */
	public void deleteAttached(int articleId, FileType fileType) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("articleId", articleId);
		parmMap.put("fileType", fileType.getValue());
		this.getSqlMapClientTemplate().delete("attachedFile.deleteAttached", parmMap);
	}

	/***
	 * 
	 * @param articleId
	 * @param fileType
	 * @return
	 */
	public List<AttachedFile> queryAttachedbyArticleId(int articleId, FileType fileType) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("articleId", articleId);
		parmMap.put("fileType", fileType.getValue());
		return this.getSqlMapClientTemplate().queryForList("attachedFile.queryAttachedbyArticleId", parmMap);
	}

	public AttachedFile queryFileById(int id) {

		return (AttachedFile) this.getSqlMapClientTemplate().queryForObject("attachedFile.queryAttachedbyId", id);
	}

	public void updateAttachedFile(AttachedFile file) {

		this.getSqlMapClientTemplate().update("attachedFile.updateAttached", file);
	}
}
