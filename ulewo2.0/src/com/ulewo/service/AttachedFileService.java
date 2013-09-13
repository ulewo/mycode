package com.ulewo.service;

import java.util.List;

import com.ulewo.entity.AttachedFile;
import com.ulewo.enums.FileType;
import com.ulewo.util.PaginationResult;

/**
 * @Title:
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author luohl
 * @date 2012-3-29
 * @version V1.0
 */
public interface AttachedFileService {
	public void addAttached(AttachedFile file);

	public void deleteAttached(int articleId, FileType fileType);

	public List<AttachedFile> queryAttachedbyArticleId(int articleId,
			FileType fileType);

	public void addAttachedPatch(List<AttachedFile> list);

	public AttachedFile queryFileById(int id);

	public void updateAttachedFile(AttachedFile file);

	public PaginationResult attachedArticle(int page, int pageSize, String gid);

}
