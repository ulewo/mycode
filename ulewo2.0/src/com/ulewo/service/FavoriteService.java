package com.ulewo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ulewo.entity.Favorite;
import com.ulewo.util.PaginationResult;

@Service("favoriteService")
public interface FavoriteService {

	public void addFavorite(Favorite favorite);

	public int queryFavoriteCountByUserId(String userId, String type);

	public int queryFavoriteCountByArticleId(int articleId, String type);

	public void deleteFavorite(int articleId, String type, String userId);

	public List<Favorite> queryFavoriteByUserId(String userId, String type, int offset, int total);

	public PaginationResult queryFavoriteByUserIdInPage(String userId, String type, int page, int pageSize);

}
