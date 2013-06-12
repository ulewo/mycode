package com.ulewo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ulewo.dao.AttachedFileDao;
import com.ulewo.entity.AttachedFile;
import com.ulewo.enums.FileType;
import com.ulewo.service.AttachedFileService;

@Service("attachedFileService")
public class AttachedFileServiceImpl implements AttachedFileService {
	@Autowired
	private AttachedFileDao attachedFileDao;

	@Override
	public void addAttached(AttachedFile file) {
		attachedFileDao.addAttached(file);
	}

	@Override
	public void deleteAttached(int articleId, FileType fileType) {
		attachedFileDao.deleteAttached(articleId, fileType);
	}

	@Override
	public List<AttachedFile> queryAttachedbyArticleId(int articleId,
			FileType fileType) {
		return attachedFileDao.queryAttachedbyArticleId(articleId, fileType);
	}

	public void addAttachedPatch(List<AttachedFile> list) {
		for (AttachedFile file : list) {
			addAttached(file);
		}
	}

}
