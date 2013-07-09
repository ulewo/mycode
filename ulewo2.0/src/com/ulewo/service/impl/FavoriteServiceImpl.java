package com.ulewo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ulewo.dao.FavoriteDao;
import com.ulewo.entity.Favorite;
import com.ulewo.service.FavoriteService;
import com.ulewo.util.Pagination;
import com.ulewo.util.PaginationResult;

@Service("favoriteService")
public class FavoriteServiceImpl implements FavoriteService {
	@Autowired
	private FavoriteDao favoriteDao;

	public void addFavorite(Favorite favorite) {

		favoriteDao.addFavorite(favorite);
	}

	public int queryFavoriteCountByUserId(String userId, String type) {

		return favoriteDao.queryFavoriteCountByUserId(userId, type);
	}

	public int queryFavoriteCountByArticleId(int articleId, String type) {

		return favoriteDao.queryFavoriteCountByArticleId(articleId, type);
	}

	public void deleteFavorite(int articleId, String type, String userId) {

		favoriteDao.deleteFavorite(articleId, type, userId);
	}

	public List<Favorite> queryFavoriteByUserId(String userId, String type, int offset, int total) {

		return favoriteDao.queryFavoriteByUserId(userId, type, offset, total);
	}

	public PaginationResult queryFavoriteByUserIdInPage(String userId, String type, int page, int pageSize) {

		int count = queryFavoriteCountByUserId(userId, type);
		Pagination pagein = new Pagination(page, count, pageSize);
		pagein.action();
		List<Favorite> list = queryFavoriteByUserId(userId, type, pagein.getOffSet(), pageSize);
		PaginationResult result = new PaginationResult(pagein.getPage(), pagein.getPageTotal(), count, list);
		return result;
	}

	@Override
	public int queryFavoriteCountByUserIdAndArticleId(String userId,
			int articleId, String type) {
		return favoriteDao.queryFavoriteCountByUserIdAndArticleId(userId, articleId, type);
	}

}
