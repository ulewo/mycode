package com.ulewo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ulewo.entity.Article;

/**
 * 
 * @Title:
 * @Description: 文章数据操作
 * @author lhl
 * @date 2012-3-29
 * @version V1.0
 */
@Component
public class ArticleDao extends BaseDao {

	/**
	 * 新增文章
	 * description: 添加文章
	 * @param article
	 * @author luohl
	 */
	public int addArticle(Article article) {

		return (Integer) this.getSqlMapClientTemplate().insert("article.addArticle", article);
	}

	/**
	 * description: 根据ID查询文章
	 * @param id
	 * @return
	 * @
	 * @author luohl
	 */
	public Article queryTopicById(int id) {

		return (Article) this.getSqlMapClientTemplate().queryForObject("article.queryTopicById", id);
	}

	/**
	 * 
	 * description: 更新文章 全更新
	 * @param article
	 * @
	 * @author luohl
	 */
	public void updateArticle(Article article) {

		this.getSqlMapClientTemplate().update("article.updateArticle", article);
	}

	/**
	 * 
	 * description: 更新不为空的字段
	 * @param article
	 * @
	 * @author luohl
	 */
	public void updateArticleSelective(Article article) {

		this.getSqlMapClientTemplate().update("article.updateArticle_selective", article);
	}

	public void deleteArticle(int id) {

		getSqlMapClientTemplate().delete("article.deleteArticle", id);
	}

	/**
	 * 
	 * description: 根据gid,itemid查询主题总数
	 * @param gid
	 * @param itemId
	 * @param isValid
	 * @return
	 * @author lhl
	 */
	public int queryTopicCountByGid(String gid, int itemId) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("gid", gid);
		parmMap.put("itemId", itemId);
		return (Integer) this.getSqlMapClientTemplate().queryForObject("article.queryTopicCountByGid", parmMap);
	}

	/**
	 * 
	 * description: 通过群编号查询主题文章  等级和最后回复时间倒叙排列 多笔查询
	 * @param gid
	 * @param itemId
	 * @param isValid
	 * @return
	 * @
	 * @author lhl
	 */
	public List<Article> queryTopicOrderByGradeAndLastReTime(String gid, int itemId, int offset, int total) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("gid", gid);
		parmMap.put("itemId", itemId);
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return this.getSqlMapClientTemplate().queryForList("article.queryTopicOrderByGradeAndLastReTime", parmMap);
	}

	/**
	 * 
	 * description: 查询文章根据时间倒叙排列
	 * @param gid
	 * @param itemId
	 * @param isValid
	 * @return
	 * @
	 * @author lhl
	 */
	public List<Article> queryTopicOrderByPostTime(String gid, int itemId, String isValid, int offset, int total) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("gid", gid);
		parmMap.put("itemId", itemId);
		parmMap.put("isValid", isValid);
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return this.getSqlMapClientTemplate().queryForList("article.queryTopicOrderByPostTime", parmMap);
	}

	/**
	 * 
	 * description: 搜索文章的数量
	 * @param keyWord
	 * @param gid
	 * @param isValid
	 * @return
	 * @author lhl
	 */
	public int searchTopicCount(String keyWord, String gid, String isValid) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("keyWord", "%" + keyWord + "%");
		parmMap.put("gid", gid);
		parmMap.put("isValid", isValid);
		return (Integer) this.getSqlMapClientTemplate().queryForObject("article.searchTopicCount", parmMap);
	}

	/**
	 * 
	 * description: 搜索文章
	 * @param keyWord
	 * @param gid
	 * @param isValid
	 * @param offset
	 * @param total
	 * @return
	 * @
	 * @author lhl
	 */
	public List<Article> searchTopic(String keyWord, String gid, String isValid, int offset, int total) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("keyWord", "%" + keyWord + "%");
		parmMap.put("gid", gid);
		parmMap.put("isValid", isValid);
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return this.getSqlMapClientTemplate().queryForList("article.searchTopic", parmMap);
	}

	/**
	 * 
	 * description:根据userId查询主题文章
	 * @param userId
	 * @param offset
	 * @param total
	 * @return
	 * @author luohl
	 */
	public List<Article> queryTopicByUserId(String userId, int offset, int total) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("authorid", userId);
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return this.getSqlMapClientTemplate().queryForList("article.queryTopicByUserId", parmMap);
	}

	/**
	 * 
	 * description: 根据userId查询主题数量
	 * @param userId
	 * @return
	 * @
	 * @author luohl
	 */
	public int queryCountByUserId(String userId) {

		return (Integer) this.getSqlMapClientTemplate().queryForObject("article.queryCountByUserId", userId);
	}

	/**
	 * 
	 * description: 根据userId查询回复的文章主题
	 * @param userId
	 * @param offset
	 * @param total
	 * @return
	 * @
	 * @author luohl
	 */
	public List<Article> queryTopicByReUserId(String userId, int offset, int total) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("authorid", userId);
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return this.getSqlMapClientTemplate().queryForList("article.queryTopicByReUserId", parmMap);
	}

	/**
	 * 
	 * description:根据userId查询回复的文章主题数
	 * @param userId
	 * @return
	 * @
	 * @author luohl
	 */
	public int queryTopicCountByReUserId(String userId) {

		return (Integer) this.getSqlMapClientTemplate().queryForObject("article.queryTopicCountByReUserId", userId);
	}

	public int queryTopicCountByTime(String startTime, String endTime, String gid) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("startTime", startTime);
		parmMap.put("endTime", endTime);
		parmMap.put("gid", gid);
		return (Integer) this.getSqlMapClientTemplate().queryForObject("article.queryTopicCountByTime", parmMap);
	}

	public List<Article> queryComendArticle(String sysCode, String subCode, int offset, int total) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("sysCode", sysCode);
		parmMap.put("subCode", subCode);
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return this.getSqlMapClientTemplate().queryForList("article.queryComendArticle", parmMap);
	}

	public List<Article> queryImageArticle(String gid, int offset, int total) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("gid", gid);
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return this.getSqlMapClientTemplate().queryForList("article.queryImageArticle", parmMap);
	}

	public int queryImageArticleCount(String gid) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("gid", gid);
		return (Integer) this.getSqlMapClientTemplate().queryForObject("article.queryImageArticleCount", parmMap);
	}

	public List<Article> queryLatestArticle(int offset, int total) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return this.getSqlMapClientTemplate().queryForList("article.queryLatestArticle", parmMap);
	}

	public List<Article> queryHotArticle(int offset, int total) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return this.getSqlMapClientTemplate().queryForList("article.queryHotArticle", parmMap);
	}

}
