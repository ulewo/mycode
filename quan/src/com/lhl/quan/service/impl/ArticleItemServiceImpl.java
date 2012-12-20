package com.lhl.quan.service.impl;

import java.util.List;

import com.lhl.entity.ArticleItem;
import com.lhl.exception.BaseException;
import com.lhl.quan.dao.ArticleItemDao;
import com.lhl.quan.service.ArticleItemService;

/** 
 * @Title:
 * @Description: 分类业务实现
 * @author luohl
 * @date 2012-3-29
 * @version V1.0
*/
public class ArticleItemServiceImpl implements ArticleItemService
{

	private ArticleItemDao articleItemDao;

	public void setArticleItemDao(ArticleItemDao articleItemDao)
	{

		this.articleItemDao = articleItemDao;
	}

	@Override
	public void addItem(ArticleItem item) throws Exception
	{

		int count = articleItemDao.queryItemCountByGid(item.getGid());
		if (count >= 8)
		{
			throw new BaseException(50000);
		}
		articleItemDao.addItem(item);
	}

	public ArticleItem getArticleItem(int itemId) throws Exception
	{

		return articleItemDao.getArticleItemById(itemId);
	}

	@Override
	public void update(ArticleItem item) throws Exception
	{

		articleItemDao.update(item);

	}

	@Override
	public void delete(int id) throws Exception
	{

		articleItemDao.delete(id);

	}

	@Override
	public List<ArticleItem> queryItemByGid(String gid) throws Exception
	{

		return articleItemDao.queryItemByGid(gid);
	}

	@Override
	public int queryItemCountByGid(String gid) throws Exception
	{

		return articleItemDao.queryItemCountByGid(gid);
	}

}
