package com.ulewo.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ulewo.dao.ReTalkDao;
import com.ulewo.dao.TalkDao;
import com.ulewo.dao.UserDao;
import com.ulewo.entity.NoticeParam;
import com.ulewo.entity.ReTalk;
import com.ulewo.entity.SessionUser;
import com.ulewo.entity.Talk;
import com.ulewo.entity.User;
import com.ulewo.enums.NoticeType;
import com.ulewo.enums.QueryUserType;
import com.ulewo.service.TalkService;
import com.ulewo.util.Constant;
import com.ulewo.util.FormatAt;
import com.ulewo.util.Pagination;
import com.ulewo.util.PaginationResult;
import com.ulewo.util.StringUtils;

@Service("talkService")
public class TalkServiceImpl implements TalkService {
	@Autowired
	private TalkDao talkDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private ReTalkDao reTalkDao;

	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public void addTalk(Talk talk) {

		String cotent = StringUtils.clearHtml(talk.getContent());
		cotent = addLink(cotent);
		List<String> referers = new ArrayList<String>();
		String formatContent = FormatAt.getInstance(Constant.TYPE_TALK).GenerateRefererLinks(userDao, cotent, referers);
		talk.setContent(formatContent);
		talk.setCreateTime(format.format(new Date()));
		talkDao.addTalk(talk);

		User curUser = userDao.findUser(talk.getUserId(), QueryUserType.USERID);
		curUser.setMark(curUser.getMark() + Constant.ARTICLE_MARK1);
		userDao.update(curUser);

		NoticeParam noticeParm = new NoticeParam();
		noticeParm.setArticleId(talk.getId());
		noticeParm.setNoticeType(NoticeType.ATINTALK);
		noticeParm.setAtUserIds(referers);
		noticeParm.setSendUserId(talk.getUserId());
		NoticeThread noticeThread = new NoticeThread(noticeParm);
		Thread thread = new Thread(noticeThread);
		thread.start();
	}

	private String addLink(String str) {
		String regex = "((http:|https:)//|www.)[^[A-Za-z0-9\\._\\?%&+\\-=/#]]*";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		StringBuffer result = new StringBuffer();
		while (matcher.find()) {
			StringBuffer replace = new StringBuffer();
			replace.append("<a href=\"").append(matcher.group());
			replace.append("\" target=\"_blank\">" + matcher.group() + "</a>");
			matcher.appendReplacement(result, replace.toString());
		}
		matcher.appendTail(result);
		return result.toString();
	}
	
	@Override
	public List<Talk> queryLatestTalk(int offset, int total) {

		List<Talk> list = talkDao.queryLatestTalk(offset, total);
		for (Talk talk : list) {
			talk.setCreateTime(StringUtils.friendly_time(talk.getCreateTime()));
		}
		return list;
	}

	@Override
	public int queryTalkCount() {

		return talkDao.queryTalkCount();
	}

	public Talk queryDetail(int talkId) {

		Talk talk = talkDao.queryDetail(talkId);
		talk.setCreateTime(StringUtils.friendly_time(talk.getCreateTime()));
		return talk;
	}

	@Override
	public PaginationResult queryLatestTalkByPag(int page, int pageSize) {

		int count = queryTalkCount();
		Pagination pagination = new Pagination(page, count, pageSize);
		pagination.action();
		List<Talk> list = queryLatestTalk(pagination.getOffSet(), pageSize);
		PaginationResult result = new PaginationResult(pagination.getPage(), pagination.getPageTotal(), count, list);
		return result;
	}

	@Override
	public PaginationResult queryTalkByUserIdByPag(int page, int pageSize, String userId, Object sessionUser, int type) {

		int includeme = includeme(userId, sessionUser); //0 查询所有  1查询自己
		PaginationResult result = null;
		switch (type) {
		case 0://查询所有主题
			result = queryTalkByUserIds(page, pageSize, userId, includeme);
			break;
		case 1: //所有回复
			result = queryReTalkByUserIds(page, pageSize, userId, includeme);
			break;
		case 2: //查询我的主题
			result = queryTalkByUserIds(page, pageSize, userId, 1);
			break;
		case 3: //查询我的回复
			result = queryReTalkByUserIds(page, pageSize, userId, 1);
			break;
		}
		return result;
	}

	private PaginationResult queryTalkByUserIds(int page, int pageSize, String userId, int includeme) {

		int count = talkDao.queryTalkCountByUserId(userId, includeme);
		Pagination pagination = new Pagination(page, count, pageSize);
		pagination.action();
		List<Talk> list = talkDao.queryTalkByUserId(pagination.getOffSet(), pageSize, userId, includeme);
		for (Talk talk : list) {
			talk.setCreateTime(StringUtils.friendly_time(talk.getCreateTime()));
		}
		PaginationResult result = new PaginationResult(pagination.getPage(), pagination.getPageTotal(), count, list);
		return result;
	}

	private PaginationResult queryReTalkByUserIds(int page, int pageSize, String userId, int includeme) {

		int count = reTalkDao.queryReTalkCountByUserId(userId, includeme);
		Pagination pagination = new Pagination(page, count, pageSize);
		pagination.action();
		List<ReTalk> list = reTalkDao.queryReTalkByUserId(pagination.getOffSet(), pageSize, userId, includeme);
		for (ReTalk reTalk : list) {
			reTalk.setCreateTime(StringUtils.friendly_time(reTalk.getCreateTime()));
		}
		PaginationResult result = new PaginationResult(pagination.getPage(), pagination.getPageTotal(), count, list);
		return result;
	}

	//是否查询自己的文章
	private int includeme(String userId, Object sessionUser) {

		if (null != sessionUser && ((SessionUser) sessionUser).getUserId().equals(userId)) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void deleteTalkBatch(String keyStr) {
		if (StringUtils.isEmpty(keyStr)) {
			return;
		}
		String ids[] = keyStr.split(",");
		for (String id : ids) {
			Talk talk = talkDao.queryDetail(Integer.parseInt(id));
			User user = userDao.findUser(talk.getUserId(),QueryUserType.USERID);
			user.setMark(user.getMark()-Constant.ARTICLE_MARK1);
			userDao.update(user);
			talkDao.deleteTalk(Integer.parseInt(id));
		}
	}
}
