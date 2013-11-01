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
import com.ulewo.dao.AttachedFileDao;
import com.ulewo.dao.ReArticleDao;
import com.ulewo.dao.UserDao;
import com.ulewo.dao.UserFriendDao;
import com.ulewo.entity.Article;
import com.ulewo.entity.AttachedFile;
import com.ulewo.entity.NoticeParam;
import com.ulewo.entity.ReArticle;
import com.ulewo.entity.SessionUser;
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
	private UserDao userDao;

	@Autowired
	private UserFriendDao userFriendDao;

	@Autowired
	private ReArticleDao reArticleDao;

	@Autowired
	private AttachedFileDao attachedFileDao;

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
		if (summary.length() > Constant.summaryLength100) {
			summary = summary.substring(0, Constant.summaryLength100);
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

		articleDao.updateArticleSelective(article);
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
			//更新阅读数量
			Article updatearticle = new Article();
			updatearticle.setId(id);
			updatearticle.setReadNumber(article.getReadNumber() + 1);
			articleDao.updateArticleSelective(updatearticle);
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

	public void manangeArticle(String gid, String groupAuthor, String[] ids, String type) {

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
			List<Article> aboutList = articleDao.searchTopic(keyWords[i], gid, 0, 5);
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

	@Override
	public int searchTopicCount(String keyWord, String gid) {

		return articleDao.searchTopicCount(keyWord, gid);
	}

	@Override
	public List<Article> searchTopic(String keyWord, String gid, int offset, int total, boolean isHilight) {

		List<Article> list = articleDao.searchTopic(keyWord, gid, offset, total);
		for (Article article : list) {
			article.setPostTime(StringUtils.friendly_time(article.getPostTime()));
			if (isHilight) {
				article.setTitle(article.getTitle().replace(keyWord, "<span class='hilight'>" + keyWord + "</span>"));
				article.setSummary(article.getSummary()
						.replace(keyWord, "<span class='hilight'>" + keyWord + "</span>"));
			}
		}
		return list;
	}

	public PaginationResult searchTopic2PageResult(String keyWord, String gid, int page, int pageSize, boolean isHilight) {

		int count = searchTopicCount(keyWord, gid);
		Pagination pagination = new Pagination(page, count, pageSize);
		pagination.action();
		List<Article> list = searchTopic(keyWord, gid, pagination.getOffSet(), pageSize, isHilight);
		PaginationResult result = new PaginationResult(pagination.getPage(), pagination.getPageTotal(), count, list);
		return result;
	}

	public List<Article> queryComendArticle(String sysCode, String subCode, int offset, int total) {

		List<Article> list = articleDao.queryComendArticle(sysCode, subCode, offset, total);
		return list;
	}

	@Override
	public List<Article> queryImageArticle(String gid, int offset, int total) {

		List<Article> list = articleDao.queryImageArticle(gid, offset, total);
		return list;
	}

	public PaginationResult queryImageArticle2PagResult(String gid, int page, int pageSize) {

		int count = articleDao.queryImageArticleCount(gid);
		Pagination pagination = new Pagination(page, count, pageSize);
		pagination.action();
		List<Article> list = queryImageArticle(gid, pagination.getOffSet(), pageSize);
		PaginationResult result = new PaginationResult(pagination.getPage(), pagination.getPageTotal(), count, list);
		return result;
	}

	@Override
	public List<Article> queryLatestArticle(int offset, int total) {

		List<Article> list = articleDao.queryLatestArticle(offset, total);
		for (Article article : list) {
			article.setPostTime(StringUtils.friendly_time(article.getPostTime()));
		}
		return list;
	}

	public int queryAllCount() {

		return articleDao.queryAllCount();
	}

	public PaginationResult queryLatestArticle2PagResult(int page, int pageSize) {

		int count = queryAllCount();
		Pagination pagination = new Pagination(page, count, pageSize);
		pagination.action();
		List<Article> list = queryTopicOrderByPostTime(null, 0, pagination.getOffSet(), pageSize);
		PaginationResult result = new PaginationResult(pagination.getPage(), pagination.getPageTotal(), count, list);
		return result;
	}

	public List<Article> queryTopicOrderByPostTime(String gid, int itemId, int offset, int total) {

		List<Article> list = articleDao.queryTopicOrderByPostTime(gid, itemId, offset, total);
		for (Article article : list) {
			article.setPostTime(StringUtils.friendly_time(article.getPostTime()));
		}
		return list;
	}

	@Override
	public List<Article> queryHotArticle(int offset, int total) {

		return articleDao.queryHotArticle(offset, total);
	}

	public PaginationResult queryArticleByUserIdByPag(int page, int pageSize, String userId, Object sessionUser,
			int type) {

		int includeme = includeme(userId, sessionUser); //0 查询所有  1查询自己
		PaginationResult result = null;
		switch (type) {
		case 0://查询所有主题
			result = queryArticleByUserIds(page, pageSize, userId, includeme);
			break;
		case 1: //所有回复
			result = queryReArticleByUserIds(page, pageSize, userId, includeme);
			break;
		case 2: //查询我的主题
			result = queryArticleByUserIds(page, pageSize, userId, 1);
			break;
		case 3: //查询我的回复
			result = queryReArticleByUserIds(page, pageSize, userId, 1);
			break;
		}
		return result;
	}

	private PaginationResult queryArticleByUserIds(int page, int pageSize, String userId, int includeme) {

		int count = articleDao.queryArticleCountByUserId(userId, includeme);
		Pagination pagination = new Pagination(page, count, pageSize);
		pagination.action();
		List<Article> list = articleDao.queryArticleByUserId(pagination.getOffSet(), pageSize, userId, includeme);
		for (Article article : list) {
			article.setPostTime(StringUtils.friendly_time(article.getPostTime()));
		}
		PaginationResult result = new PaginationResult(pagination.getPage(), pagination.getPageTotal(), count, list);
		return result;
	}

	private PaginationResult queryReArticleByUserIds(int page, int pageSize, String userId, int includeme) {

		int count = reArticleDao.queryReArticleCountByUserId(userId, includeme);
		Pagination pagination = new Pagination(page, count, pageSize);
		pagination.action();
		List<ReArticle> list = reArticleDao.queryReArticleByUserId(pagination.getOffSet(), pageSize, userId, includeme);
		for (ReArticle reArticle : list) {
			reArticle.setReTime(StringUtils.friendly_time(reArticle.getReTime()));
		}
		PaginationResult result = new PaginationResult(pagination.getPage(), pagination.getPageTotal(), count, list);
		return result;
	}

	//是否查询自己的文章
	private int includeme(String userId, Object sessionUser) {

		if (null != sessionUser && ((SessionUser) sessionUser).getUserId().equals(userId)) {
			return 0;
		}
		else {
			return 1;
		}
	}

	@Override
	public PaginationResult queryAllArticleByAdmin(int page, int pageSize, String keyWord) {

		if (StringUtils.isEmpty(keyWord)) {
			return queryLatestArticle2PagResult(page, pageSize);
		}
		else {
			return searchTopic2PageResult(keyWord, null, page, pageSize, true);
		}
	}

	public List<Article> getArticleInIds(String[] ids) {

		List<Article> list = articleDao.getArticleInIds(ids);
		for (Article article : list) {
			article.setPostTime(StringUtils.friendly_time(article.getPostTime()));
		}
		return list;
	}
	
	@Override
	public void deleteArticleBatch(String keyStr) {
		if (StringUtils.isEmpty(keyStr)) {
			return;
		}
		String ids[] = keyStr.split(",");
		for (String id : ids) {
			Article article = articleDao.queryTopicById(Integer.parseInt(id));
			User user = userDao.findUser(article.getAuthorId(),QueryUserType.USERID);
			user.setMark(user.getMark()-Constant.ARTICLE_MARK5);
			userDao.update(user);
			articleDao.deleteArticle(Integer.parseInt(id));
		}
		
	}
}
