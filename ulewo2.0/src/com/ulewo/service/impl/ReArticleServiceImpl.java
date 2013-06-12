package com.ulewo.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.ulewo.util.Pagination;
import com.ulewo.util.PaginationResult;
import com.ulewo.util.StringUtils;
import com.ulewo.vo.ReArticleVo;

/**
 * @Title:
 * @Description: 文章回复业务实现
 * @author luohl
 * @date 2012-3-30
 * @version V1.0
 */
@Service("reArticleService")
public class ReArticleServiceImpl implements ReArticleService {
	@Autowired
	private ReArticleDao reArticleDao;

	@Autowired
	private UserDao userDao;

	@Autowired
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
	public ReArticle addReArticle(ReArticle reArticle) {

		reArticle.setReTime(format.format(new Date()));
		String content = reArticle.getContent();
		List<String> referers = new ArrayList<String>();
		String formatContent = FormatAt.getInstance().GenerateRefererLinks(
				userDao, content, referers);
		String subCon = formatContent;
		reArticle.setContent(subCon);
		if (StringUtils.isEmpty(reArticle.getSourceFrom())) {
			reArticle.setSourceFrom("P");
		}
		int id = reArticleDao.addReArticle(reArticle);

		if (!"".equals(reArticle.getAuthorid())
				&& reArticle.getAuthorid() != null) {
			User user = userDao.findUser(reArticle.getAuthorid(),
					QueryUserType.USERID);
			User reUser = new User();
			reUser.setUserLittleIcon(user.getUserLittleIcon());
			reUser.setUserName(user.getUserName());
			reArticle.setAuthor(reUser);
		}
		reArticle.setId(id);
		reArticle.setReTime(StringUtils.friendly_time(reArticle.getReTime()));
		User curUser = userDao.findUser(reArticle.getAuthorid(),
				QueryUserType.USERID);
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
	public void deleteReArticle(int id) {

		reArticleDao.deleteReArticle(id);

	}

	@Override
	public ReArticle getReArticle(int id) {

		ReArticle reArticle = reArticleDao.getReArticle(id);
		if (null == reArticle) {
			// throw new BaseException(30000);
		}
		reArticle.setReTime(StringUtils.friendly_time(reArticle.getReTime()));
		return reArticle;
	}

	@Override
	public void updateReArticle(ReArticle reArticle) {

		reArticleDao.updateReArticle(reArticle);

	}

	@Override
	public int queryReArticleCount(int articleid) {

		return reArticleDao.queryReArticleCount(articleid);
	}

	/**
	 * 回复的文章
	 */
	@Override
	public PaginationResult queryReArticles(int articleid, int page,
			int pageSize) {
		int count = reArticleDao.queryReArticleCount(articleid);
		List<ReArticle> list = reArticleDao
				.queryReArticles(articleid, 0, count);
		List<ReArticle> allReArticle = formateList(list);
		int reCount = allReArticle == null ? 0 : allReArticle.size();
		Pagination pagination = new Pagination(page, reCount, pageSize);
		pagination.action();
		List<ReArticleVo> resultList = getListbyPageNum(pagination.getOffSet(),
				pageSize, allReArticle);
		PaginationResult result = new PaginationResult(pagination.getPage(),
				pagination.getPageTotal(), reCount, resultList);
		return result;
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
				} else {
					List<ReArticle> child = map.get(reArticle.getPid());
					child.add(reArticle);
				}
			}
			reArticle
					.setReTime(StringUtils.friendly_time(reArticle.getReTime()));
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

	private List<ReArticleVo> getListbyPageNum(int start, int pageSize,
			List<ReArticle> reArticleList) {

		List<ReArticleVo> resultList = new ArrayList<ReArticleVo>();
		ReArticleVo vo = null;
		ReArticle reArticle = null;
		int total = start + pageSize;
		if (total > reArticleList.size()) {
			total = reArticleList.size();
		}
		for (int i = start; i < total; i++) {
			reArticle = reArticleList.get(i);
			vo = new ReArticleVo();
			vo.setArticleId(reArticle.getArticleId());
			vo.setAtUserId(reArticle.getAtUserId());
			vo.setAtUserName(reArticle.getAtUserName());
			vo.setAuthorIcon(reArticle.getAuthorIcon());
			vo.setAuthorid(reArticle.getAuthorid());
			vo.setAuthorName(reArticle.getAuthorName());
			vo.setContent(reArticle.getContent());
			vo.setId(reArticle.getId());
			vo.setPid(reArticle.getPid());
			vo.setReTime(reArticle.getReTime());
			vo.setSourceFrom(reArticle.getSourceFrom());
			vo.setChildList(reArticle.getChildList());
			resultList.add(vo);
		}
		return resultList;
	}
}
