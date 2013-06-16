package com.ulewo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ulewo.dao.ArticleDao;
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
	
	@Autowired
	private ArticleDao articleDao;

	private final int MAX_ITEM_LENGTH = 8;

	@Override
	public boolean saveItem(ArticleItem item) {
		if(item.getId()!=0){
			articleItemDao.update(item);
		}else{
			int count = articleItemDao.queryItemCountByGid(item.getGid());
			if (count >= MAX_ITEM_LENGTH) {
				return false;
			}
			articleItemDao.addItem(item);
		}
		return true;
	}

	public ArticleItem getArticleItem(int itemId) {

		return articleItemDao.getArticleItemById(itemId);
	}

	@Override
	public void update(ArticleItem item) {

		articleItemDao.update(item);

	}

	@Override
	public boolean delete(int id) {
		ArticleItem item = articleItemDao.getArticleItemById(id);
		if(item.getArticleCount()>0){
			return false;
		}
		articleItemDao.delete(id);
		return true;

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
