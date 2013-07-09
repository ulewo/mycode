package com.ulewo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ulewo.entity.Favorite;

@Component
public class FavoriteDao extends BaseDao {

	public void addFavorite(Favorite favorite) {

		this.getSqlMapClientTemplate().insert("favorite.addFavorite", favorite);
	}

	public int queryFavoriteCountByUserId(String userId, String type) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("userId", userId);
		parmMap.put("type", type);
		return (Integer) this.getSqlMapClientTemplate().queryForObject("favorite.queryFavoriteCountByUserId", parmMap);
	}

	public int queryFavoriteCountByArticleId(int articleId, String type) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("articleId", articleId);
		parmMap.put("type", type);
		return (Integer) this.getSqlMapClientTemplate().queryForObject("favorite.queryFavoriteCountByArticleId",
				parmMap);
	}

	public void deleteFavorite(int articleId, String type, String userId) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("articleId", articleId);
		parmMap.put("userId", userId);
		parmMap.put("type", type);
		this.getSqlMapClientTemplate().queryForObject("favorite.deleteFavorite", parmMap);
	}

	public List<Favorite> queryFavoriteByUserId(String userId, String type, int offset, int total) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("userId", userId);
		parmMap.put("type", type);
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return this.getSqlMapClientTemplate().queryForList("favorite.findAllUsers", parmMap);
	}

}
