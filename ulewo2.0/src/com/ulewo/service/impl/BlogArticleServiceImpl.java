package com.ulewo.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ulewo.dao.BlogArticleDao;
import com.ulewo.dao.BlogReplyDao;
import com.ulewo.dao.UserDao;
import com.ulewo.dao.UserFriendDao;
import com.ulewo.entity.BlogArticle;
import com.ulewo.entity.BlogReply;
import com.ulewo.entity.NoticeParam;
import com.ulewo.entity.SessionUser;
import com.ulewo.entity.User;
import com.ulewo.enums.BlogOrderType;
import com.ulewo.enums.NoticeType;
import com.ulewo.enums.QueryUserType;
import com.ulewo.service.BlogArticleService;
import com.ulewo.util.Constant;
import com.ulewo.util.FormatAt;
import com.ulewo.util.Pagination;
import com.ulewo.util.PaginationResult;
import com.ulewo.util.StringUtils;

@Service("blogArticleService")
public class BlogArticleServiceImpl implements BlogArticleService {
	@Autowired
	private BlogArticleDao blogArticleDao;

	@Autowired
	private BlogReplyDao blogReplyDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserFriendDao userFriendDao;

	private final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public boolean saveBlog(BlogArticle blogArticle) {

		if (blogArticle.getId() == 0) {
			return addBlog(blogArticle);
		}
		else {
			blogArticleDao.update(blogArticle);
			return true;
		}

	}

	@Override
	public boolean addBlog(BlogArticle blogArticle) {

		blogArticle.setPostTime(format.format(new Date()));
		String content = blogArticle.getContent();

		String summary = StringUtils.clearHtml(content);
		if (summary.length() > Constant.summaryLength100) {
			summary = summary.substring(0, Constant.summaryLength100);
		}
		else if (summary.length() == 0) {
			summary = blogArticle.getTitle();
		}
		blogArticle.setSummary(summary + "......");

		List<String> referers = new ArrayList<String>();
		String formatContent = FormatAt.getInstance().GenerateRefererLinks(userDao, content, referers);

		blogArticle.setContent(formatContent);

		int blogId = blogArticleDao.addBlog(blogArticle);

		User curUser = userDao.findUser(blogArticle.getUserId(), QueryUserType.USERID);
		curUser.setMark(curUser.getMark() + Constant.ARTICLE_MARK5);
		userDao.update(curUser);

		// 启动一个线程发布消息
		NoticeParam noticeParm = new NoticeParam();
		noticeParm.setArticleId(blogId);
		noticeParm.setNoticeType(NoticeType.ATINBLOG);
		noticeParm.setAtUserIds(referers);
		noticeParm.setSendUserId(blogArticle.getUserId());
		NoticeThread noticeThread = new NoticeThread(noticeParm);
		Thread thread = new Thread(noticeThread);
		thread.start();
		return true;
	}

	@Override
	public BlogArticle queryBlogById(int id) {

		BlogArticle blog = blogArticleDao.queryBlogById(id);
		blog.setPostTime(StringUtils.friendly_time(blog.getPostTime()));
		return blog;
	}

	@Override
	public boolean update(BlogArticle blogArticle) {

		if (isCurUser(blogArticle.getUserId(), blogArticle.getId())) {
			blogArticleDao.update(blogArticle);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void updateReadCount(BlogArticle blogArticle) {

		blogArticleDao.updateReadCount(blogArticle);
	}

	@Override
	public void deleteArticle(int id) {

		blogArticleDao.delete(id);

	}
	
	@Override
	public void deleteBlogBatch(String keyStr) {
		if (StringUtils.isEmpty(keyStr)) {
			return;
		}
		String ids[] = keyStr.split(",");
		for (String id : ids) {
			deleteArticle(Integer.parseInt(id));
		}
	}
	

	@Override
	public List<BlogArticle> queryBlog(String userId, int itemId, int offset, int total, BlogOrderType blogOrderType) {

		List<BlogArticle> list = blogArticleDao.queryBlog(userId, itemId, offset, total, blogOrderType);
		for (BlogArticle blog : list) {
			blog.setPostTime(StringUtils.friendly_time(blog.getPostTime()));
		}
		return list;
	}

	public PaginationResult queryBlogByUserId(String userId, int itemId, int page, int pageSize,
			BlogOrderType blogOrderType) {

		int count = blogArticleDao.queryBlogCount(userId, itemId);
		Pagination pagination = new Pagination(page, count, pageSize);
		pagination.action();
		List<BlogArticle> list = queryBlog(userId, itemId, pagination.getOffSet(), pageSize, blogOrderType);
		PaginationResult result = new PaginationResult(pagination.getPage(), pagination.getPageTotal(), count, list);
		return result;
	}

	@Override
	public int queryBlogCount(String userId, int itemId) {

		return blogArticleDao.queryBlogCount(userId, itemId);
	}

	private boolean isCurUser(String userId, int id) {

		BlogArticle article = blogArticleDao.queryBlogById(id);
		if (article != null && (article.getUserId().equals(userId))) {
			return true;
		}
		else {
			return false;
		}
	}

	public List<BlogArticle> indexLatestBlog(int offset, int total) {

		List<BlogArticle> list = new ArrayList<BlogArticle>();
		list = blogArticleDao.indexLatestBlog(offset, total);
		for (BlogArticle blog : list) {
			blog.setPostTime(StringUtils.friendly_time(blog.getPostTime()));
		}
		return list;
	}

	public int queryCount() {

		return blogArticleDao.queryCount();
	}

	public PaginationResult queryLatestBlog(int page, int pageSize) {

		int count = queryCount();
		Pagination pagination = new Pagination(page, count, pageSize);
		pagination.action();
		List<BlogArticle> list = indexLatestBlog(pagination.getOffSet(), pageSize);
		PaginationResult result = new PaginationResult(pagination.getPage(), pagination.getPageTotal(), count, list);
		return result;
	}

	public int searchBlogCount(String keyword) {

		return blogArticleDao.searchBlogCount(keyword);
	}

	public List<BlogArticle> searchBlog(String keyword, int offset, int total, boolean isHilight) {

		List<BlogArticle> list = blogArticleDao.searchBlog(keyword, offset, total);

		for (BlogArticle blog : list) {
			blog.setPostTime(StringUtils.friendly_time(blog.getPostTime()));
			if (isHilight) {
				blog.setTitle(blog.getTitle().replace(keyword, "<span class='hilight'>" + keyword + "</span>"));
				blog.setSummary(blog.getSummary().replace(keyword, "<span class='hilight'>" + keyword + "</span>"));
			}
		}
		return list;
	}

	public PaginationResult searchBlog2PageResult(String keyword, int page, int pageSize, boolean isHilight) {

		int count = searchBlogCount(keyword);
		Pagination pagination = new Pagination(page, count, pageSize);
		pagination.action();
		List<BlogArticle> list = searchBlog(keyword, pagination.getOffSet(), pageSize, isHilight);
		PaginationResult result = new PaginationResult(pagination.getPage(), pagination.getPageTotal(), count, list);
		return result;
	}

	public PaginationResult queryArticleByUserIdByPag(int page, int pageSize, String userId, Object sessionUser,
			int type) {

		int includeme = includeme(userId, sessionUser); //0 查询所有  1查询自己
		PaginationResult result = null;
		switch (type) {
		case 0://查询所有主题
			result = queryBlogByUserIds(page, pageSize, userId, includeme);
			break;
		case 1: //所有回复
			result = queryReBlogByUserIds(page, pageSize, userId, includeme);
			break;
		}
		return result;
	}

	private PaginationResult queryBlogByUserIds(int page, int pageSize, String userid, int includeme) {

		int count = blogArticleDao.queryBlogArticleCountByUserId(userid, includeme);
		Pagination pagination = new Pagination(page, count, pageSize);
		pagination.action();
		List<BlogArticle> list = blogArticleDao.queryBlogArticleByUserId(pagination.getOffSet(), pageSize, userid,
				includeme);
		for (BlogArticle article : list) {
			article.setPostTime(StringUtils.friendly_time(article.getPostTime()));
		}
		PaginationResult result = new PaginationResult(pagination.getPage(), pagination.getPageTotal(), count, list);
		return result;
	}

	private PaginationResult queryReBlogByUserIds(int page, int pageSize, String userid, int includeme) {

		int count = blogReplyDao.queryReBlogCountByUserId(userid, includeme);
		Pagination pagination = new Pagination(page, count, pageSize);
		pagination.action();
		List<BlogReply> list = blogReplyDao.queryReBlogByUserId(pagination.getOffSet(), pageSize, userid, includeme);
		for (BlogReply reArticle : list) {
			reArticle.setPostTime(StringUtils.friendly_time(reArticle.getPostTime()));
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
}
