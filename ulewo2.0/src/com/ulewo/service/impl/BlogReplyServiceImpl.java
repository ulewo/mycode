package com.ulewo.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ulewo.dao.BlogArticleDao;
import com.ulewo.dao.BlogReplyDao;
import com.ulewo.dao.NoticeDao;
import com.ulewo.dao.UserDao;
import com.ulewo.entity.BlogReply;
import com.ulewo.entity.NoticeParam;
import com.ulewo.entity.User;
import com.ulewo.enums.NoticeType;
import com.ulewo.enums.QueryUserType;
import com.ulewo.service.BlogReplyService;
import com.ulewo.util.Constant;
import com.ulewo.util.FormatAt;
import com.ulewo.util.Pagination;
import com.ulewo.util.PaginationResult;
import com.ulewo.util.StringUtils;

@Service("blogReplyService")
public class BlogReplyServiceImpl implements BlogReplyService {
	@Autowired
	private BlogReplyDao blogReplyDao;

	@Autowired
	private BlogArticleDao blogArticleDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private NoticeDao noticeDao;

	private final SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public void setNoticeDao(NoticeDao noticeDao) {

		this.noticeDao = noticeDao;
	}

	public void setUserDao(UserDao userDao) {

		this.userDao = userDao;
	}

	public void setBlogReplyDao(BlogReplyDao blogReplyDao) {

		this.blogReplyDao = blogReplyDao;
	}

	public void setBlogArticleDao(BlogArticleDao blogArticleDao) {

		this.blogArticleDao = blogArticleDao;
	}

	@Override
	public BlogReply addReply(BlogReply blogReply) {

		blogReply.setPostTime(formate.format(new Date()));
		String content = blogReply.getContent();
		content = StringUtils.formateHtml(content);
		List<String> referers = new ArrayList<String>();
		String formatContent = FormatAt.getInstance().GenerateRefererLinks(userDao, content, referers);
		String subCon = formatContent;
		blogReply.setContent(subCon);
		if (StringUtils.isEmpty(blogReply.getSourceFrom())) {
			blogReply.setSourceFrom("P");
		}
		int id = blogReplyDao.addReply(blogReply);
		blogReply.setId(id);
		blogReply.setPostTime(StringUtils.friendly_time(blogReply.getPostTime()));
		User curUser = userDao.findUser(blogReply.getUserId(), QueryUserType.USERID);
		curUser.setMark(curUser.getMark() + Constant.ARTICLE_MARK2);
		userDao.update(curUser);

		// 启动一个线程发布消息
		NoticeParam noticeParm = new NoticeParam();
		noticeParm.setArticleId(blogReply.getBlogId());
		noticeParm.setNoticeType(NoticeType.REBLOG);
		noticeParm.setAtUserIds(referers);
		noticeParm.setSendUserId(blogReply.getUserId());
		// 如果At的用户Id为不为空那么就是二级回复
		if (StringUtils.isNotEmpty(blogReply.getAtUserId())) {
			noticeParm.setReceiveUserId(blogReply.getAtUserId());
		}
		noticeParm.setReId(id);
		NoticeThread noticeThread = new NoticeThread(noticeParm);
		Thread thread = new Thread(noticeThread);
		thread.start();

		return blogReply;
	}

	@Override
	public PaginationResult queryBlogReplyByBlogId(int blogId, int page, int pageSize) {

		int count = blogReplyDao.queryReplyCountByBlogId(blogId);
		Pagination pagein = new Pagination(page, count, pageSize);
		pagein.action();
		List<BlogReply> list = blogReplyDao.queryReplyByBlogId(blogId, pagein.getOffSet(), pageSize);
		for (BlogReply reply : list) {
			reply.setPostTime(StringUtils.friendly_time(reply.getPostTime()));
		}
		PaginationResult result = new PaginationResult(pagein.getPage(), pagein.getPageTotal(), count, list);
		return result;
	}

	@Override
	public int queryBlogReplyCountByBlogId(int blogId) {

		return blogReplyDao.queryReplyCountByBlogId(blogId);
	}

	@Override
	public boolean delete(String curUserId, int id) {

		return blogReplyDao.delete(id, curUserId);
	}

	public BlogReply queryBlogReplyById(int id) {

		BlogReply reply = blogReplyDao.queryBlogReplyById(id);
		reply.setPostTime(StringUtils.friendly_time(reply.getPostTime()));
		return reply;
	}

	@Override
	public PaginationResult queryAllReply(String author, int page, int pageSize) {

		int count = blogReplyDao.queryAllReplyCount(author);
		Pagination pagein = new Pagination(page, count, pageSize);
		pagein.action();
		List<BlogReply> list = blogReplyDao.queryAllReply(author, pagein.getOffSet(), pageSize);
		for (BlogReply reply : list) {
			reply.setPostTime(StringUtils.friendly_time(reply.getPostTime()));
		}
		PaginationResult result = new PaginationResult(pagein.getPage(), pagein.getPageTotal(), count, list);
		return result;
	}
}
