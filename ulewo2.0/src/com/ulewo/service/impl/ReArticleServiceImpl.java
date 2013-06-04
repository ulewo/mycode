package com.ulewo.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ulewo.dao.NoticeDao;
import com.ulewo.dao.ReArticleDao;
import com.ulewo.dao.UserDao;
import com.ulewo.entity.NoticeParam;
import com.ulewo.entity.ReArticle;
import com.ulewo.entity.User;
import com.ulewo.enums.NoticeType;
import com.ulewo.enums.QueryUserType;
import com.ulewo.service.ReArticleService;
import com.ulewo.util.Constant;
import com.ulewo.util.FormatAt;
import com.ulewo.util.StringUtils;

/**
 * @Title:
 * @Description: 文章回复业务实现
 * @author luohl
 * @date 2012-3-30
 * @version V1.0
 */
@Service("reArticleService")
public class ReArticleServiceImpl implements ReArticleService {

	private ReArticleDao reArticleDao;

	private UserDao userDao;

	private NoticeDao noticeDao;

	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
	public ReArticle addReArticle(ReArticle reArticle) throws Exception {

		reArticle.setReTime(format.format(new Date()));
		String content = reArticle.getContent();
		List<String> referers = new ArrayList<String>();
		String formatContent = FormatAt.getInstance().GenerateRefererLinks(userDao, content, referers);
		String subCon = formatContent;
		reArticle.setContent(subCon);
		if (StringUtils.isEmpty(reArticle.getSourceFrom())) {
			reArticle.setSourceFrom("P");
		}
		int id = reArticleDao.addReArticle(reArticle);

		if (!"".equals(reArticle.getAuthorid()) && reArticle.getAuthorid() != null) {
			User user = userDao.findUser(reArticle.getAuthorid(), QueryUserType.USERID);
			User reUser = new User();
			reUser.setUserLittleIcon(user.getUserLittleIcon());
			reUser.setUserName(user.getUserName());
			reArticle.setAuthor(reUser);
		}
		reArticle.setId(id);
		reArticle.setReTime(StringUtils.friendly_time(reArticle.getReTime()));
		User curUser = userDao.findUser(reArticle.getAuthorid(), QueryUserType.USERID);
		curUser.setMark(curUser.getMark() + Constant.ARTICLE_MARK2);
		userDao.update(curUser);

		// 启动一个线程发布消息
		NoticeParam noticeParm = new NoticeParam();
		noticeParm.setArticleId(reArticle.getArticleId());
		noticeParm.setNoticeType(NoticeType.REARTICLE);
		noticeParm.setAtUserIds(referers);
		noticeParm.setSendUserId(reArticle.getAuthorid());
		// 如果At的用户Id为不为空那么就是二级回复
		if (StringUtils.isNotEmpty(reArticle.getAtUserId())) {
			noticeParm.setReceiveUserId(reArticle.getAtUserId());
		}
		noticeParm.setReId(id);
		NoticeThread noticeThread = new NoticeThread(noticeParm);
		Thread thread = new Thread(noticeThread);
		thread.start();
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
			//throw new BaseException(30000);
		}
		reArticle.setReTime(StringUtils.friendly_time(reArticle.getReTime()));
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
	public List<ReArticle> queryReArticles(int articleid, int offset, int total) throws Exception {

		List<ReArticle> list = reArticleDao.queryReArticles(articleid, offset, total);
		return formateList(list);
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
	}

	private List<ReArticle> formateList(List<ReArticle> list) {

		List<ReArticle> resultlist = new ArrayList<ReArticle>();
		Map<Integer, List<ReArticle>> map = new HashMap<Integer, List<ReArticle>>();
		for (ReArticle reArticle : list) {
			if (reArticle.getPid() != null && reArticle.getPid() != 0) {
				if (map.get(reArticle.getPid()) == null) {
					List<ReArticle> child = new ArrayList<ReArticle>();
					child.add(reArticle);
					map.put(reArticle.getPid(), child);
				}
				else {
					List<ReArticle> child = map.get(reArticle.getPid());
					child.add(reArticle);
				}
			}
			reArticle.setReTime(StringUtils.friendly_time(reArticle.getReTime()));
		}

		for (ReArticle reArticle : list) {
			if (reArticle.getPid() != null && reArticle.getPid() != 0) {
				continue;
			}
			reArticle.setChildList(map.get(reArticle.getId()));
			resultlist.add(reArticle);
		}
		return resultlist;
	}
}
