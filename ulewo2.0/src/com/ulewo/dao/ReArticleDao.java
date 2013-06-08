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
	 * @param reArticle
	 * @throws Exception
	 * @author luohl
	 */
	public void updateReArticle(ReArticle reArticle) {

		this.getSqlMapClientTemplate().update("reArticle.updateReArticle", reArticle);
	}

	/**
	 * 
	 * description: 查询回复总条数
	 * @param articleid
	 * @return
	 * @throws Exception
	 * @author luohl
	 */
	public int queryReArticleCount(int articleid) {

		return (Integer) this.getSqlMapClientTemplate().queryForObject("reArticle.queryReArticleCount", articleid);
	}

	/**
	 * 
	 * description: 分页查询回复
	 * @param articleid
	 * @param offset
	 * @param total
	 * @return
	 * @throws Exception
	 * @author luohl
	 */
	public List<ReArticle> queryReArticles(int articleid, int offset, int total) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("articleid", articleid);
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return this.getSqlMapClientTemplate().queryForList("reArticle.queryReArticles", parmMap);
	}

	/**
	 * description: 根据父ID查询回复
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
}