package com.ulewo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ulewo.entity.ReArticle;

/**
 * @Title:
 * @Description: 回复文章dao数据操作
 * @author luohl
 * @date 2012-3-30
 * @version V1.0
 */
@Component
public class ReArticleDao extends BaseDao {
	/**
	 * 
	 * description: 新增回复
	 * 
	 * @param reArticle
	 * @throws Exception
	 * @author luohl
	 */
	public int addReArticle(ReArticle reArticle) {

		int id = (Integer) this.getSqlMapClientTemplate().insert("reArticle.addReArticle", reArticle);
		return id;
	}

	/**
	 * 
	 * description: 删除回复
	 * 
	 * @param id
	 * @throws Exception
	 * @author luohl
	 */
	public void deleteReArticle(int id) {

		this.getSqlMapClientTemplate().delete("reArticle.deleteReArticle", id);
	}

	/**
	 * 
	 * description: 单笔查询回复
	 * 
	 * @param id
	 * @return
	 * @author luohl
	 */
	public ReArticle getReArticle(int id) {

		return (ReArticle) this.getSqlMapClientTemplate().queryForObject("reArticle.getReArticle", id);
	}

	/**
	 * 
	 * description: 更新回复
	 * 
	 * @param reArticle
	 * @throws Exception
	 * @author luohl
	 */
	public void updateReArticle(ReArticle reArticle) {

		this.getSqlMapClientTemplate().update("reArticle.updateReArticle", reArticle);
	}

	/**
	 * 
	 * description: 分页查询回复
	 * 
	 * @param articleid
	 * @param offset
	 * @param total
	 * @return
	 * @throws Exception
	 * @author luohl
	 */
	public List<ReArticle> queryReArticles(int articleid) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("articleid", articleid);
		return this.getSqlMapClientTemplate().queryForList("reArticle.queryReArticles", parmMap);
	}

	/**
	 * description: 根据父ID查询回复
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 * @author luohl
	 */
	public List<ReArticle> queryReArticleByPid(int pid) {

		return this.getSqlMapClientTemplate().queryForList("reArticle.queryReArticleByPid", pid);
	}

	/**
	 * 
	 * description: 查询最后回复的文章
	 * 
	 * @param articleid
	 * @param offset
	 * @param total
	 * @return
	 * @throws Exception
	 * @author luohl
	 */
	public ReArticle queryLatestReArticle(int articleid) {

		return (ReArticle) this.getSqlMapClientTemplate().queryForObject("reArticle.queryLatestReArticle", articleid);
	}

	public int queryReArticleByGid(String gid) {

		return (Integer) this.getSqlMapClientTemplate().queryForObject("reArticle.queryAllReArticleCountByGid", gid);
	}

	public List<ReArticle> queryAllReArticleByGid(String gid, int offset, int total) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("gid", gid);
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return this.getSqlMapClientTemplate().queryForList("reArticle.queryAllReArticleByGid", parmMap);
	}

	/**
	 * 查询所有回复
	 * @param gid
	 * @return
	 */
	public int queryReArticleCount() {

		return (Integer) this.getSqlMapClientTemplate().queryForObject("reArticle.queryAllReArticleCount");
	}

	public List<ReArticle> queryAllReArticle(int offset, int total) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return this.getSqlMapClientTemplate().queryForList("reArticle.queryAllReArticle", parmMap);
	}
	
	/**
	 * 根据userId查询回复的主题数量
	 * @param userIds
	 * @return
	 */
	public int queryReArticleCountByUserId(String userId, int includeme) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("userId", userId);
		parmMap.put("includeme", includeme);
		return (Integer) this.getSqlMapClientTemplate()
				.queryForObject("reArticle.queryReArticleCountByUserId", parmMap);
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
	public List<ReArticle> queryReArticleByUserId(int offset, int total, String userId, int includeme) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("userId", userId);
		parmMap.put("includeme", includeme);
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return this.getSqlMapClientTemplate().queryForList("reArticle.queryReArticleByUserId", parmMap);
	}
}
