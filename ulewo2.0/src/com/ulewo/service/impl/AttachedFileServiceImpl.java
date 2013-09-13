package com.ulewo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ulewo.dao.AttachedFileDao;
import com.ulewo.entity.Article;
import com.ulewo.entity.AttachedFile;
import com.ulewo.enums.FileType;
import com.ulewo.service.AttachedFileService;
import com.ulewo.util.Pagination;
import com.ulewo.util.PaginationResult;
import com.ulewo.util.StringUtils;

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

	public AttachedFile queryFileById(int id) {

		return attachedFileDao.queryFileById(id);
	}

	public void updateAttachedFile(AttachedFile file) {

		attachedFileDao.updateAttachedFile(file);
	}

	public PaginationResult attachedArticle(int page, int pageSize, String gid) {
		int count = attachedFileDao.attachedArticleCount(gid);
		Pagination pagination = new Pagination(page, count, pageSize);
		pagination.action();
		List<Article> list = attachedFileDao.attachedArticle(gid,
				pagination.getOffSet(), pageSize);
		for (Article article : list) {
			article.setPostTime(StringUtils.friendly_time(article.getPostTime()));
		}
		PaginationResult result = new PaginationResult(pagination.getPage(),
				pagination.getPageTotal(), count, list);
		return result;
	}
}
