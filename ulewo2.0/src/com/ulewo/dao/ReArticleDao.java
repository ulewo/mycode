package com.ulewo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ulewo.entity.ReArticle;

/** 
 * @Title:
 * @Description: 回复文章dao数据操作
 * @author luohl
 * @date 2012-3-30
 * @version V1.0
*/
public class ReArticleDao extends SqlMapClientDaoSupport {
	/**
	 * 
	 * description: 新增回复
	 * @param reArticle
	 * @throws Exception
	 * @author luohl
	 */
	public int addReArticle(ReArticle reArticle) throws Exception {

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
	public void deleteReArticle(int id) throws Exception {

		this.getSqlMapClientTemplate().delete("reArticle.deleteReArticle", id);
	}

	/**
	 * 
	 * description: 单笔查询回复
	 * @param id
	 * @return
	 * @author luohl
	 */
	public ReArticle getReArticle(int id) throws Exception {

		return (ReArticle) this.getSqlMapClientTemplate().queryForObject("reArticle.getReArticle", id);
	}

	/**
	 * 
	 * description: 更新回复
	 * @param reArticle
	 * @throws Exception
	 * @author luohl
	 */
	public void updateReArticle(ReArticle reArticle) throws Exception {

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
	public int queryReArticleCount(int articleid) throws Exception {

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
	public List<ReArticle> queryReArticles(int articleid, int offset, int total) throws Exception {

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
	public List<ReArticle> queryReArticleByPid(int pid) throws Exception {

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
	public ReArticle queryLatestReArticle(int articleid) throws Exception {

		return (ReArticle) this.getSqlMapClientTemplate().queryForObject("reArticle.queryLatestReArticle", articleid);
	}
}
