package com.ulewo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ulewo.dao.ArticleItemDao;
import com.ulewo.entity.ArticleItem;
import com.ulewo.service.ArticleItemService;

/** 
 * @Title:
 * @Description: 分类业务实现
 * @author luohl
 * @date 2012-3-29
 * @version V1.0
*/
@Service("articleItemService")
public class ArticleItemServiceImpl implements ArticleItemService {
	@Autowired
	private ArticleItemDao articleItemDao;

	public void setArticleItemDao(ArticleItemDao articleItemDao) {

		this.articleItemDao = articleItemDao;
	}

	@Override
	public void addItem(ArticleItem item) {

		int count = articleItemDao.queryItemCountByGid(item.getGid());
		if (count >= 8) {
			//throw new BaseException(50000);
		}
		articleItemDao.addItem(item);
	}

	public ArticleItem getArticleItem(int itemId) {

		return articleItemDao.getArticleItemById(itemId);
	}

	@Override
	public void update(ArticleItem item) {

		articleItemDao.update(item);

	}

	@Override
	public void delete(int id) {

		articleItemDao.delete(id);

	}

	@Override
	public List<ArticleItem> queryItemByGid(String gid) {

		return articleItemDao.queryItemByGid(gid);
	}

	@Override
	public List<ArticleItem> queryItemAndTopicCountByGid(String gid) {

		return articleItemDao.queryItemAndTopicCountByGid(gid);
	}

	@Override
	public int queryItemCountByGid(String gid) {

		return articleItemDao.queryItemCountByGid(gid);
	}

}
