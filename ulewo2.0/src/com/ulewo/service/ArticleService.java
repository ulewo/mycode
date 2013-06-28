package com.ulewo.service;

import java.util.List;

import com.ulewo.entity.Article;
import com.ulewo.util.PaginationResult;

public interface ArticleService {
	/**
	 * 新增文章
	 * description: 添加文章
	 * @param article
	 * @author luohl
	 */
	public void addArticle(Article article);

	/**
	 * description: 根据ID查询文章
	 * @param id
	 * @return
	 * @
	 * @author luohl
	 */
	public Article queryTopicById(int id);

	/**
	 * 
	 * description: 管理员更新文章
	 * @param article
	 * @
	 * @author luohl
	 */
	public void updateArticle(Article article, String groupAuthor);

	/**
	 * 
	 * description: 更新不为空的字段
	 * @param article
	 * @
	 * @author luohl
	 */

	public void updateArticleSelective(Article article);

	/**
	 * 管理文章
	 * @param gid TODO
	 * @param userId TODO
	 * @param ids
	 * @param type
	 */
	public void manangeArticle(String gid, String userId, String[] ids, String type);

	/**
	 * 
	 * description: 显示具体的文章
	 * @return
	 * @
	 * @author lhl
	 */
	public Article showArticle(int id);

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
	public PaginationResult queryTopicOrderByGradeAndLastReTime(String gid, int itemId, int page, int pageSize);

	/**
	 * 
	 * description: 查询作者发表的主题数量
	 * @param userId
	 * @return
	 * @
	 * @author lhl
	 */
	public int queryPostTopicCount(String userId);

	/**
	 * 
	 * description: 查询作者发表的主题
	 * @param userId
	 * @param offset TODO
	 * @param total TODO
	 * @return
	 * @
	 * @author lhl
	 */
	public List<Article> queryPostTopic(String userId, int offset, int total);

	/**
	 * 
	 * description: 查询作者回复的主题数量
	 * @param userId
	 * @return
	 * @
	 * @author lhl
	 */
	public int queryReTopicCount(String userId);

	/**
	 * 
	 * description: 查询作者回复的主题
	 * @param userId
	 * @param offset TODO
	 * @param total TODO
	 * @return
	 * @
	 * @author lhl
	 */
	public List<Article> queryReTopic(String userId, int offset, int total);

	/**
	 * 
	 * description: 相关文章
	 * @param keyWord
	 * @param gid
	 * @return
	 * @
	 * @author lhl
	 */
	public List<Article> aboutArticle(String keyWord, String gid);

	/**
	 * 
	 * description: 查询群组当日发帖数量
	 * @param gid
	 * @return
	 * @
	 * @author lhl
	 */
	public int queryTopicCountByTime(String gid);

	/**
	 * 
	 * description: 搜索文章的数量
	 * @param keyWord
	 * @param gid
	 * @param isValid
	 * @return
	 * @author lhl
	 */
	public int searchTopicCount(String keyWord, String gid);

	/**
	 * 
	 * description: 搜索文章
	 * @param keyWord
	 * @param gid
	 * @param offset
	 * @param total
	 * @param isHilight TODO
	 * @param isValid
	 * @return
	 * @
	 * @author lhl
	 */
	public List<Article> searchTopic(String keyWord, String gid, int offset, int total, boolean isHilight);

	public PaginationResult searchTopic2PageResult(String keyWord, String gid, int page, int pageSize, boolean isHilight);

	public List<Article> queryComendArticle(String sysCode, String subCode, int offset, int total);

	public List<Article> queryImageArticle(String gid, int offset, int total);

	public PaginationResult queryImageArticle2PagResult(String gid, int page, int pageSize);

	public List<Article> queryLatestArticle(int offset, int total);

	public int queryAllCount();

	public List<Article> queryTopicOrderByPostTime(String gid, int itemId, int offset, int total);

	public PaginationResult queryLatestArticle2PagResult(int page, int pageSize);

	public List<Article> queryHotArticle(int offset, int total);
}
