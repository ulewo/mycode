package com.lhl.quan.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.lhl.entity.Notice;
import com.lhl.entity.ReArticle;
import com.lhl.entity.User;
import com.lhl.exception.BaseException;
import com.lhl.quan.dao.NoticeDao;
import com.lhl.quan.dao.ReArticleDao;
import com.lhl.quan.dao.UserDao;
import com.lhl.quan.service.ReArticleService;
import com.lhl.util.Constant;
import com.lhl.util.FormatAt;

/**
 * @Title:
 * @Description: 文章回复业务实现
 * @author luohl
 * @date 2012-3-30
 * @version V1.0
 */
public class ReArticleServiceImpl implements ReArticleService {

	private ReArticleDao reArticleDao;

	private UserDao userDao;

	private NoticeDao noticeDao;

	private SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public void setReArticleDao(ReArticleDao reArticleDao) {

		this.reArticleDao = reArticleDao;
	}

	public void setUserDao(UserDao userDao) {

		this.userDao = userDao;
	}

	public void setNoticeDao(NoticeDao noticeDao) {
		this.noticeDao = noticeDao;
	}

	@Override
	public ReArticle addReArticle(ReArticle reArticle, String authorId,
			String articleTitle) throws Exception {

		reArticle.setReTime(format.format(new Date()));
		int id = reArticleDao.addReArticle(reArticle);
		if (!"".equals(reArticle.getAuthorid())
				&& reArticle.getAuthorid() != null) {
			User user = userDao.queryUser("", "", reArticle.getAuthorid());
			User reUser = new User();
			reUser.setUserLittleIcon(user.getUserLittleIcon());
			reUser.setUserName(user.getUserName());
			reArticle.setAuthor(reUser);
		}
		reArticle.setId(id);
		if (!authorId.equals(reArticle.getAuthorid())) {
			String noticeCon = reArticle.getAuthorName() + "在\"" + articleTitle
					+ "\"中回复了你";
			String url = "../group/post.jspx?id=" + reArticle.getArticleId()
					+ "#re" + id;
			Notice notice = FormatAt.getInstance().formateNotic(authorId, url,
					Constant.NOTICE_TYPE1, noticeCon);
			noticeDao.createNotice(notice);
		}

		return reArticle;
	}

	@Override
	public void deleteReArticle(int id) throws Exception {

		reArticleDao.deleteReArticle(id);

	}

	@Override
	public ReArticle getReArticle(int id) throws Exception {

		ReArticle reArticle = reArticleDao.getReArticle(id);
		if (null == reArticle) {
			throw new BaseException(30000);
		}
		return reArticle;
	}

	@Override
	public void updateReArticle(ReArticle reArticle) throws Exception {

		reArticleDao.updateReArticle(reArticle);

	}

	@Override
	public int queryReArticleCount(int articleid) throws Exception {

		return reArticleDao.queryReArticleCount(articleid);
	}

	/**
	 * 回复的文章
	 */
	@Override
	public List<ReArticle> queryReArticles(int articleid, int offset, int total)
			throws Exception {

		List<ReArticle> list = reArticleDao.queryReArticles(articleid, offset,
				total);
		/*
		 * User author = null; User sAuthor = null; List<ReArticle> reList =
		 * null; for (ReArticle reParticle : list) { if
		 * (Tools.isNotEmpty(reParticle.getAuthorid())) { author =
		 * userDao.queryUser(null, null, reParticle.getAuthorid()); } else {
		 * author = new User(); author.setUserName(reParticle.getAuthorName());
		 * author.setUserLittleIcon(Constant.USER_DEFAULT_LITTLELICON); }
		 * reParticle.setAuthor(author);
		 * 
		 * reList = reArticleDao.queryReArticleByPid(reParticle.getId()); for
		 * (ReArticle reSarticle : reList) { if
		 * (Tools.isNotEmpty(reSarticle.getAuthorid())) { sAuthor =
		 * userDao.queryUser(null, null, reSarticle.getAuthorid()); } else {
		 * sAuthor = new User();
		 * sAuthor.setUserName(reSarticle.getAuthorName());
		 * sAuthor.setUserLittlelIcon(Constant.USER_DEFAULT_LITTLELICON); }
		 * reSarticle.setAuthor(sAuthor); } reParticle.setReArticles(reList);
		 * 
		 * }
		 */
		return list;
	}
}
