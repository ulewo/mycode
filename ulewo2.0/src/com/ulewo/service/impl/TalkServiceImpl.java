package com.ulewo.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ulewo.dao.ReTalkDao;
import com.ulewo.dao.TalkDao;
import com.ulewo.dao.UserDao;
import com.ulewo.dao.UserFriendDao;
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
	private UserFriendDao userFriendDao;

	@Autowired
	private ReTalkDao reTalkDao;

	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public void addTalk(Talk talk) {

		String cotent = StringUtils.clearHtml(talk.getContent());
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

	@Override
	public List<Talk> queryLatestTalkByUserId(int offset, int total, List<String> userIds) {

		List<Talk> list = talkDao.queryTalkByUserId(offset, total, userIds);
		for (Talk talk : list) {
			talk.setCreateTime(StringUtils.friendly_time(talk.getCreateTime()));
		}
		return list;
	}

	@Override
	public int queryTalkCountByUserId(List<String> userId) {

		return talkDao.queryTalkCountByUserId(userId);
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

		PaginationResult result = null;
		List<String> userIds = new ArrayList<String>();
		switch (type) {
		case 0://查询所有主题
			userIds = getUserIds(userId, sessionUser);
			result = queryTalkByUserIds(page, pageSize, userIds);
			break;
		case 1: //所有回复
			userIds = getUserIds(userId, sessionUser);
			result = queryReTalkByUserIds(page, pageSize, userIds);
			break;
		case 2: //查询我的主题
			userIds.add(userId);
			result = queryTalkByUserIds(page, pageSize, userIds);
			break;
		case 3: //查询我的回复
			userIds.add(userId);
			result = queryReTalkByUserIds(page, pageSize, userIds);
			break;
		}
		return result;
	}

	private PaginationResult queryTalkByUserIds(int page, int pageSize, List<String> userIds) {

		int count = queryTalkCountByUserId(userIds);
		Pagination pagination = new Pagination(page, count, pageSize);
		pagination.action();
		List<Talk> list = queryLatestTalkByUserId(pagination.getOffSet(), pageSize, userIds);
		PaginationResult result = new PaginationResult(pagination.getPage(), pagination.getPageTotal(), count, list);
		return result;
	}

	private PaginationResult queryReTalkByUserIds(int page, int pageSize, List<String> userIds) {

		int count = reTalkDao.queryReTalkCountByUserId(userIds);
		Pagination pagination = new Pagination(page, count, pageSize);
		pagination.action();
		List<ReTalk> list = reTalkDao.queryReTalkByUserId(pagination.getOffSet(), pageSize, userIds);
		for(ReTalk reTalk:list){
			reTalk.setCreateTime(StringUtils.friendly_time(reTalk.getCreateTime()));
		}
		PaginationResult result = new PaginationResult(pagination.getPage(), pagination.getPageTotal(), count, list);
		return result;
	}

	//获取要查看的人
	private List<String> getUserIds(String userId, Object sessionUser) {

		List<String> userIds = new ArrayList<String>();

		if (null != sessionUser && ((SessionUser) sessionUser).getUserId().equals(userId)) {
			//用户查看自己的
			//关注的人 和自己的。
			userIds = userFriendDao.queryFocusUserIds(((SessionUser) sessionUser).getUserId());
			userIds.add(userId);
		}
		else {
			userIds.add(userId);
		}
		return userIds;
	}
}
