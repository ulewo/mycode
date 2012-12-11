package com.lhl.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lhl.dao.ArticleDao;
import com.lhl.entity.Article;
import com.lhl.util.Constant;
import com.lhl.util.StringUtil;

public class ArticleService
{
	private ArticleService()
	{

	}

	private static ArticleService instance;

	/**
	 * 构造实例
	 * 
	 * @return
	 */
	public synchronized static ArticleService getInstance()
	{

		if (instance == null)
		{
			instance = new ArticleService();
		}
		return instance;
	}

	public Map<String, Object> queryList(int noStart, int pageSize, String type, String timeRnage) throws Exception
	{

		Map<String, Object> result = new HashMap<String, Object>();
		if (Constant.TYPE_INDEX.equals(type))
		{// 热门 首页
			// 24小时内最热门
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String curTime = format.format(date);
			String beforTime = StringUtil.getBeforTime(date, timeRnage); // 一天内
			if (Constant.TIME_RANGE_WEEK.equals(timeRnage))
			{// 一周内
				beforTime = StringUtil.getBeforTime(date, timeRnage);
			}
			else if (Constant.TIME_RANGE_MONTH.equals(timeRnage))
			{ // 一个月内
				beforTime = StringUtil.getBeforTime(date, timeRnage);
			}
			else if (Constant.TIME_RANGE_YEAR.equals(timeRnage))
			{
				beforTime = StringUtil.getBeforTime(date, timeRnage);
			}
			List<Article> list = ArticleDao.getInstance().queryListByUp(noStart, pageSize, beforTime, curTime);
			int count = ArticleDao.getInstance().queryCountByTimeRange(beforTime, curTime);
			result.put("list", list);
			result.put("count", count);
			return result;
		}
		if (Constant.TYPE_NEW.equals(type))
		{// 最新
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String curTime = format.format(date);
			String beforTime = StringUtil.getBeforTime(date, Constant.TIME_RANGE_WEEK); // 一星期内
			List<Article> list = ArticleDao.getInstance().queryListByPostTime(noStart, pageSize, beforTime, curTime);
			int count = ArticleDao.getInstance().queryCountByTimeRange(beforTime, curTime);
			result.put("list", list);
			result.put("count", count);
			return result;
		}
		if (Constant.TYPE_HOT.equals(type))
		{// 最热

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String curTime = format.format(date);
			String beforTime = StringUtil.getBeforTime(date, Constant.TIME_RANGE_WEEK);
			List<Article> list = ArticleDao.getInstance().queryListByReCount(noStart, pageSize, beforTime, curTime);
			int count = ArticleDao.getInstance().queryCountByTimeRange(beforTime, curTime);
			result.put("list", list);
			result.put("count", count);
			return result;
		}
		if (Constant.TYPE_PIC.equals(type))
		{// 有图有真相

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String curTime = format.format(date);
			String beforTime = StringUtil.getBeforTime(date, Constant.TIME_RANGE_WEEK);
			List<Article> list = ArticleDao.getInstance().queryListByPic(noStart, pageSize, beforTime, curTime);
			int count = ArticleDao.getInstance().queryCountByTimeRangeHavePic(beforTime, curTime);
			result.put("list", list);
			result.put("count", count);
			return result;
		}
		return result;
	}

	public void updateDownOrUp(int id, String type) throws Exception
	{

		ArticleDao.getInstance().updateDownOrUp(id, type);
	}

	public void deleteArticle(int id)
	{

		ArticleDao.getInstance().deleteArticle(id);
	}

	public void addArticle(Article article) throws Exception
	{

		ArticleDao.getInstance().addArticle(article);
	}

	public Article queryArticleById(int id) throws Exception
	{

		return ArticleDao.getInstance().queryArticleById(id);
	}

	public void auditArticle(String status, int id)
	{

		ArticleDao.getInstance().auditArticle(status, id);
	}

	public List<Article> adminQueryList(int noStart, int pageSize, String type) throws Exception
	{

		if (Constant.QEURY_TYPE_AUDITY.equals(type))
		{
			return ArticleDao.getInstance().adminQueryListByStatus(noStart, pageSize, Constant.STATUS_Y);
		}
		else if (Constant.QEURY_TYPE_AUDITN.equals(type))
		{
			return ArticleDao.getInstance().adminQueryListByStatus(noStart, pageSize, Constant.STATUS_N);
		}
		else
		{
			return ArticleDao.getInstance().adminQueryList(noStart, pageSize);
		}

	}

	public int queryArticleCount(String type)
	{

		String status = "";
		if (Constant.QEURY_TYPE_AUDITY.equals(type))
		{
			status = Constant.STATUS_Y;
		}
		else if (Constant.QEURY_TYPE_AUDITN.equals(type))
		{
			status = Constant.STATUS_N;
		}
		return ArticleDao.getInstance().adminQueryCountByStatus(status);
	}

	public Map<String, String> queryCount(String time) throws Exception
	{

		return ArticleDao.getInstance().queryCount(time);
	}

	public Map<String, Object> queryUserArticleList(int noStart, int pageSize, String type, String uid)
	{

		Map<String, Object> map = new HashMap<String, Object>();
		if (Constant.TYPE_MY.equals(type))
		{
			List<Article> list = ArticleDao.getInstance().queryArticleByUserId(noStart, pageSize, uid);
			int count = ArticleDao.getInstance().queryCountByUserId(uid);
			map.put("list", list);
			map.put("count", count);
		}
		else if (Constant.TYPE_RE.equals(type))
		{
			List<Article> list = ArticleDao.getInstance().queryArticleByReUserId(noStart, pageSize, uid);
			int count = ArticleDao.getInstance().queryCountByReUserId(uid);
			map.put("list", list);
			map.put("count", count);
		}
		return map;
	}

	public int queryCountByTimeRange(String beforTime, String curTime) throws Exception
	{

		return ArticleDao.getInstance().queryCountByTimeRange(beforTime, curTime);
	}

	/**
	 * 
	 * description: 搜索文章
	 * @param noStart
	 * @param pageSize
	 * @param searchKey
	 * @return
	 * @throws Exception
	 * @author luohl
	 */
	public List<Article> searchArticle(int noStart, int pageSize, String searchKey) throws Exception
	{

		return ArticleDao.getInstance().searchArticle(noStart, pageSize, searchKey);
	}

	/**
	 * 
	 * description: 搜索数量
	 * @param searchKey
	 * @return
	 * @throws Exception
	 * @author luohl
	 */
	public int searchArticleCount(String searchKey) throws Exception
	{

		return ArticleDao.getInstance().searchArticleCount(searchKey);
	}
}
