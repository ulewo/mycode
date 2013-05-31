package com.ulewo.service;

import java.util.List;

import com.ulewo.entity.Article;
import com.ulewo.entity.User;

public interface ArticleService {
	/**
	 * 新增文章
	 * description: 添加文章
	 * @param article
	 * @author luohl
	 */
	public void addArticle(Article article, User user) throws Exception;

	/**
	 * description: 根据ID查询文章
	 * @param id
	 * @return
	 * @throws Exception
	 * @author luohl
	 */
	public Article queryTopicById(int id) throws Exception;

	/**
	 * 
	 * description: 更新文章 全更新
	 * @param article
	 * @throws Exception
	 * @author luohl
	 */
	public void updateArticle(Article article) throws Exception;

	/**
	 * 
	 * description: 更新不为空的字段
	 * @param article
	 * @throws Exception
	 * @author luohl
	 */

	public void updateArticleSelective(Article article) throws Exception;

	/**
	 * 
	 * description: 显示具体的文章
	 * @return
	 * @throws Exception
	 * @author lhl
	 */
	public Article showArticle(int id) throws Exception;

	/**
	 * 
	 * description: 根据gid,itemid查询主题总数
	 * @param gid
	 * @param itemId
	 * @param isValid
	 * @return
	 * @author lhl
	 */
	public int queryTopicCountByGid(String gid, int itemId, String isValid) throws Exception;

	/**
	 * 
	 * description: 通过群编号查询主题文章  等级和最后回复时间倒叙排列 多笔查询
	 * @param gid
	 * @param itemId
	 * @param isValid
	 * @return
	 * @throws Exception
	 * @author lhl
	 */
	public List<Article> queryTopicOrderByGradeAndLastReTime(String gid, int itemId, String isValid, int offset,
			int total) throws Exception;

	/**
	 * 
	 * description: 查询文章根据时间倒叙排列
	 * @param gid
	 * @param itemId
	 * @param isValid
	 * @return
	 * @throws Exception
	 * @author lhl
	 */
	public List<Article> queryTopicOrderByPostTime(String gid, int itemId, String isValid, int offset, int total)
			throws Exception;

	/**
	 * 
	 * description: 查询作者发表的主题数量
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author lhl
	 */
	public int queryPostTopicCount(String userId) throws Exception;

	/**
	 * 
	 * description: 查询作者发表的主题
	 * @param userId
	 * @param offset TODO
	 * @param total TODO
	 * @return
	 * @throws Exception
	 * @author lhl
	 */
	public List<Article> queryPostTopic(String userId, int offset, int total) throws Exception;

	/**
	 * 
	 * description: 查询作者回复的主题数量
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author lhl
	 */
	public int queryReTopicCount(String userId) throws Exception;

	/**
	 * 
	 * description: 查询作者回复的主题
	 * @param userId
	 * @param offset TODO
	 * @param total TODO
	 * @return
	 * @throws Exception
	 * @author lhl
	 */
	public List<Article> queryReTopic(String userId, int offset, int total) throws Exception;

	/**
	 * 
	 * description: 相关文章
	 * @param keyWord
	 * @param gid
	 * @return
	 * @throws Exception
	 * @author lhl
	 */
	public List<Article> aboutArticle(String keyWord, String gid) throws Exception;

	/**
	 * 
	 * description: 查询群组当日发帖数量
	 * @param gid
	 * @return
	 * @throws Exception
	 * @author lhl
	 */
	public int queryTopicCountByTime(String gid) throws Exception;

	/**
	 * 
	 * description: 搜索文章的数量
	 * @param keyWord
	 * @param gid
	 * @param isValid
	 * @return
	 * @author lhl
	 */
	public int searchTopicCount(String keyWord, String gid, String isValid);

	/**
	 * 
	 * description: 搜索文章
	 * @param keyWord
	 * @param gid
	 * @param isValid
	 * @param offset
	 * @param total
	 * @return
	 * @throws Exception
	 * @author lhl
	 */
	public List<Article> searchTopic(String keyWord, String gid, String isValid, int offset, int total)
			throws Exception;

	public List<Article> queryList(String keyWord, String isValid, int offset, int total) throws Exception;

	public List<Article> queryComendArticle(String sysCode, String subCode, int offset, int total) throws Exception;

	public List<Article> queryImageArticle(int offset, int total);

	public List<Article> queryLatestArticle(int offset, int total);

}
