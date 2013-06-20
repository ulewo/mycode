package com.ulewo.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ulewo.dao.ArticleDao;
import com.ulewo.dao.ArticleItemDao;
import com.ulewo.dao.AttachedFileDao;
import com.ulewo.dao.GroupDao;
import com.ulewo.dao.NoticeDao;
import com.ulewo.dao.ReArticleDao;
import com.ulewo.dao.UserDao;
import com.ulewo.entity.Article;
import com.ulewo.entity.AttachedFile;
import com.ulewo.entity.Group;
import com.ulewo.entity.NoticeParam;
import com.ulewo.entity.ReArticle;
import com.ulewo.entity.User;
import com.ulewo.enums.ArticleEssence;
import com.ulewo.enums.ArticleGrade;
import com.ulewo.enums.FileType;
import com.ulewo.enums.NoticeType;
import com.ulewo.enums.QueryUserType;
import com.ulewo.service.ArticleService;
import com.ulewo.util.Constant;
import com.ulewo.util.FormatAt;
import com.ulewo.util.Pagination;
import com.ulewo.util.PaginationResult;
import com.ulewo.util.StringUtils;

@Service("articleService")
public class ArticleServiceImpl implements ArticleService {
	@Autowired
	private ArticleDao articleDao;

	@Autowired
	private ArticleItemDao articleItemDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private ReArticleDao reArticleDao;

	@Autowired
	private NoticeDao noticeDao;

	@Autowired
	private AttachedFileDao attachedFileDao;

	@Autowired
	private GroupDao groupDao;

	private final SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static final String TYPE_SETTOP = "0";

	private static final String TYPE_CANCELTOP = "1";

	private static final String TYPE_SETGOOD = "2";

	private static final String TYPE_CANCELGOOD = "3";

	private static final String TYPE_DELETE = "4";

	/**
	 * 新增文章
	 */
	@Override
	public void addArticle(Article article) {

		String content = article.getContent();
		String summary = StringUtils.clearHtml(content);
		if (summary.length() > Constant.summaryLength200) {
			summary = summary.substring(0, Constant.summaryLength200);
		}
		else if (summary.length() == 0) {
			summary = article.getTitle();
		}
		article.setSummary(summary + "......");

		article.setPostTime(formate.format(new Date()));

		List<String> referers = new ArrayList<String>();
		String formatContent = FormatAt.getInstance().GenerateRefererLinks(userDao, content, referers);

		article.setContent(formatContent);
		int id = articleDao.addArticle(article);

		// 更新用户的积分
		User curUser = userDao.findUser(article.getAuthorId(), QueryUserType.USERID);
		curUser.setMark(curUser.getMark() + Constant.ARTICLE_MARK5);
		userDao.update(curUser);

		article.setAuthorName(curUser.getUserName());
		article.setPostTime(StringUtils.friendly_time(article.getPostTime()));
		article.setReadNumber(0);
		article.setReNumber(0);

		/********** 添加附件 ************/
		if (null != article.getFile()) {
			AttachedFile file = article.getFile();
			file.setArticleId(article.getId());
			file.setFileName(file.getFileUrl().substring(file.getFileUrl().lastIndexOf("/") + 1));
			file.setFileType(FileType.RAR.getValue());
			attachedFileDao.addAttached(file);
		}

		// 启动一个线程发布消息
		NoticeParam noticeParm = new NoticeParam();
		noticeParm.setArticleId(id);
		noticeParm.setNoticeType(NoticeType.ATINARTICLE);
		noticeParm.setAtUserIds(referers);
		noticeParm.setSendUserId(article.getAuthorId());
		NoticeThread noticeThread = new NoticeThread(noticeParm);
		Thread thread = new Thread(noticeThread);
		thread.start();
	}

	/**
	 * 查询主题文章
	 */
	@Override
	public Article queryTopicById(int id) {

		Article article = articleDao.queryTopicById(id);
		if (null == article) {
			// throw new BaseException(30000);
		}
		article.setPostTime(StringUtils.friendly_time(article.getPostTime()));
		return article;
	}

	public void updateArticle(Article article, String groupAuthor) {

		if (checkPerm(article.getGid(), groupAuthor)) {
			articleDao.updateArticleSelective(article);
		}
	}

	/**
	 * 展示文章详情
	 */
	@Override
	public Article showArticle(int id) {

		Article article = articleDao.queryTopicById(id);
		if (article != null) {
			// 文章作者信息
			User author = userDao.findUser(article.getAuthorId(), QueryUserType.USERID);

			if (null == author) {
				// throw new BaseException(30000);
			}

			// 设置关键字
			if (StringUtils.isEmpty(article.getKeyWord())) {
				article.setKeyWord(article.getTitle());
			}
			article.setAuthor(author);
			article.setPostTime(StringUtils.friendly_time(article.getPostTime()));
			List<AttachedFile> list = attachedFileDao.queryAttachedbyArticleId(id, FileType.RAR);
			if (null != list && list.size() > 0) {
				article.setFile(list.get(0));
			}

		}
		return article;
	}

	/**
	 * 更新非空字段
	 */
	@Override
	public void updateArticleSelective(Article article) {

		Article result = articleDao.queryTopicById(article.getId());
		if (null == result) {
			// throw new BaseException(30000);
		}
		else {
			if (!result.getGid().equals(article.getGid())) {
				// throw new BaseException(10002);
			}
			else {
				articleDao.updateArticleSelective(article);
			}
		}

	}

	private boolean checkPerm(String gid, String groupAuthro) {

		Group group = groupDao.queryGroupBaseInfo(gid);
		if (null == group) {
			return false;
		}
		if (!group.getGroupAuthor().equals(groupAuthro)) {
			return false;
		}
		return true;
	}

	public void manangeArticle(String gid, String groupAuthor, String[] ids, String type) {

		if (!checkPerm(gid, groupAuthor)) {
			return;
		}
		if (ids != null && StringUtils.isNotEmpty(type)) {
			if (TYPE_SETTOP.equals(type)) { //设置置顶
				for (String id : ids) {
					Article article = new Article();
					article.setId(Integer.parseInt(id));
					article.setGrade(ArticleGrade.TOP.getValue());
					articleDao.updateArticleSelective(article);
				}
			}
			else if (TYPE_CANCELTOP.equals(type)) {//取消置顶
				for (String id : ids) {
					Article article = new Article();
					article.setId(Integer.parseInt(id));
					article.setGrade(ArticleGrade.NORMAL.getValue());
					articleDao.updateArticleSelective(article);
				}
			}
			else if (TYPE_SETGOOD.equals(type)) {
				for (String id : ids) {
					Article article = new Article();
					article.setId(Integer.parseInt(id));
					article.setEssence(ArticleEssence.Essence.getValue());
					articleDao.updateArticleSelective(article);
				}
			}
			else if (TYPE_CANCELGOOD.equals(type)) {
				for (String id : ids) {
					Article article = new Article();
					article.setId(Integer.parseInt(id));
					article.setEssence(ArticleEssence.NoEssence.getValue());
					articleDao.updateArticleSelective(article);
				}
			}
			else if (TYPE_DELETE.equals(type)) {
				for (String id : ids) {
					articleDao.deleteArticle(Integer.parseInt(id));
				}
			}
		}
	}

	/**
	 * 通过群编号查询主题文章 等级和最后回复时间倒叙排列 多笔查询
	 */
	@Override
	public PaginationResult queryTopicOrderByGradeAndLastReTime(String gid, int itemId, int page, int pageSize) {

		int count = articleDao.queryTopicCountByGid(gid, itemId);
		Pagination pagination = new Pagination(page, count, pageSize);
		pagination.action();
		List<Article> list = articleDao.queryTopicOrderByGradeAndLastReTime(gid, itemId, pagination.getOffSet(),
				pageSize);
		for (Article article : list) {
			article.setPostTime(StringUtils.friendly_time(article.getPostTime()));
		}
		PaginationResult result = new PaginationResult(pagination.getPage(), pagination.getPageTotal(), count, list);
		// setArticleListInfo(list);
		return result;
	}

	/**
	 * 查询文章根据时间倒叙排列
	 */
	@Override
	public List<Article> queryTopicOrderByPostTime(String gid, int itemId, String isValid, int offset, int total) {

		List<Article> list = articleDao.queryTopicOrderByPostTime(gid, itemId, isValid, offset, total);
		setArticleListInfo(list);
		return list;
	}

	private void setArticleListInfo(List<Article> list) {

		ReArticle lastReArticle = null; // 最后回复的文章
		String lastReAuthorId = null; // 最后回复人id
		String lastReAuthorName = Constant.USER_DEFAULT_NAME; // 最后回复人名称
		String lastReTime = null; // 最后回复时间
		for (Article article : list) {
			// 最后回复的文章
			lastReArticle = reArticleDao.queryLatestReArticle(article.getId());
			if (null == lastReArticle) {// 无最后回复，最后回复就是作者
				lastReAuthorId = article.getAuthorId();
				lastReAuthorName = article.getAuthorName();
				lastReTime = article.getPostTime();
			}
			else {
				lastReTime = lastReArticle.getReTime();
				lastReAuthorName = lastReArticle.getAuthorName();
				lastReAuthorId = lastReArticle.getAuthorid();
			}
			if (StringUtils.isEmpty(article.getItemName())) {
				article.setItemName("全部分类");
			}
			article.setPostTime(StringUtils.friendly_time(article.getPostTime()));
			article.setLastReAuthorId(lastReAuthorId);
			article.setLastReAuthorName(lastReAuthorName);
			article.setLastReTime(StringUtils.friendly_time(lastReTime));
		}
	}

	@Override
	public int queryPostTopicCount(String userId) {

		return articleDao.queryCountByUserId(userId);
	}

	@Override
	public List<Article> queryPostTopic(String userId, int offset, int total) {

		List<Article> list = articleDao.queryTopicByUserId(userId, offset, total);
		for (Article article : list) {
			article.setPostTime(StringUtils.friendly_time(article.getPostTime()));
		}
		return list;
	}

	@Override
	public int queryReTopicCount(String userId) {

		return articleDao.queryTopicCountByReUserId(userId);
	}

	@Override
	public List<Article> queryReTopic(String userId, int offset, int total) {

		List<Article> list = articleDao.queryTopicByReUserId(userId, offset, total);
		for (Article article : list) {
			article.setPostTime(StringUtils.friendly_time(article.getPostTime()));
		}
		return list;
	}

	@Override
	public List<Article> aboutArticle(String keyWord, String gid) {

		List<Article> list = new ArrayList<Article>();
		if (null == keyWord) {
			return list;
		}
		String[] keyWords = null;
		if (keyWord.contains(",")) {
			keyWords = keyWord.split(",");
		}
		else if (keyWord.contains("，")) {
			keyWords = keyWord.split("，");
		}
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < keyWords.length; i++) {
			List<Article> aboutList = articleDao.searchTopic(keyWords[i], gid, Constant.ISVALIDY, 0, 5);
			for (Article article : aboutList) {
				if (null == map.get(article.getTitle()) && !keyWord.equals(article.getKeyWord())) {
					list.add(article);
					map.put(article.getTitle(), article.getTitle());
				}
			}
			if (list.size() >= 5) {
				break;
			}
		}
		return list;
	}

	public int queryTopicCountByTime(String gid) {

		String endTime = formate.format(new Date());
		String startTime = endTime.substring(0, 10) + " 00:00:00";
		return articleDao.queryTopicCountByTime(startTime, endTime, gid);
	}

	@Override
	public int searchTopicCount(String keyWord, String gid, String isValid) {

		return articleDao.searchTopicCount(keyWord, gid, isValid);
	}

	@Override
	public List<Article> searchTopic(String keyWord, String gid, String isValid, int offset, int total) {

		List<Article> list = articleDao.searchTopic(keyWord, gid, isValid, offset, total);
		for (Article article : list) {
			article.setPostTime(StringUtils.friendly_time(article.getPostTime()));
		}
		return list;
	}

	public List<Article> queryList(String keyWord, String isValid, int offset, int total) {

		return null;
	}

	public List<Article> queryComendArticle(String sysCode, String subCode, int offset, int total) {

		List<Article> list = articleDao.queryComendArticle(sysCode, subCode, offset, total);
		return list;
	}

	@Override
	public List<Article> queryImageArticle(int offset, int total) {

		List<Article> list = articleDao.queryImageArticle(offset, total);
		return list;
	}

	@Override
	public List<Article> queryLatestArticle(int offset, int total) {

		List<Article> list = articleDao.queryLatestArticle(offset, total);
		for (Article article : list) {
			article.setPostTime(StringUtils.friendly_time(article.getPostTime()));
		}
		return list;
	}

	@Override
	public List<Article> queryHotArticle(int offset, int total) {

		return articleDao.queryHotArticle(offset, total);
	}

}
