package com.ulewo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ulewo.enums.MarkEnums;
import com.ulewo.enums.NoticeType;
import com.ulewo.enums.PageSize;
import com.ulewo.enums.QueryUserType;
import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.BlogCommentMapper;
import com.ulewo.mapper.BlogMapper;
import com.ulewo.mapper.UserMapper;
import com.ulewo.model.Blog;
import com.ulewo.model.BlogComment;
import com.ulewo.model.NoticeParam;
import com.ulewo.model.SessionUser;
import com.ulewo.model.User;
import com.ulewo.util.SimplePage;
import com.ulewo.util.StringUtils;
import com.ulewo.util.UlewoPaginationResult;

@Service("blogReplyService")
public class BlogCommentServiceImpl implements BlogCommentService {
    @Resource
    private BlogCommentMapper<BlogComment> blogCommentMapper;

    @Resource
    private BlogMapper<Blog> blogMapper;

    @Resource
    private UserService userService;

    @Resource
    private UserMapper<User> userMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
    public BlogComment addBlogComment(Map<String, String> map,
	    SessionUser sessionUser) throws BusinessException {

	String content = map.get("content");
	String atUserIdStr = map.get("atUserId");
	String blogIdStr = map.get("blogId");
	String blogUserIdStr = map.get("blogAuthor");
	String sourceFrom = map.get("sourceFrom");
	Integer blogAuthor = 0;
	Integer blogId = 0;
	Integer atUserId = 0;
	if (StringUtils.isEmpty(content)) {
	    throw new BusinessException("输入内容为空");
	}
	if (!StringUtils.isNumber(blogIdStr)
		|| !StringUtils.isNumber(blogUserIdStr)) {
	    throw new BusinessException("传入的参数有误");
	}

	blogAuthor = Integer.parseInt(blogUserIdStr);
	blogId = Integer.parseInt(blogIdStr);

	BlogComment comment = new BlogComment();
	comment.setBlogUserId(blogAuthor);
	comment.setUserId(sessionUser.getUserId());
	comment.setUserName(sessionUser.getUserName());

	if (StringUtils.isNotEmpty(atUserIdStr)
		&& !StringUtils.isNumber(atUserIdStr)) {
	    throw new BusinessException("传入的参数有误");
	} else if (StringUtils.isNotEmpty(atUserIdStr)
		&& StringUtils.isNumber(atUserIdStr)) {
	    atUserId = Integer.parseInt(atUserIdStr);
	    comment.setAtUserId(atUserId);
	    comment.setAtUserName(map.get("atUserName"));
	}
	comment.setCreateTime(StringUtils.dateFormater.get().format(new Date()));
	// 格式化内容
	content = StringUtils.formateHtml(content);

	List<Integer> userIds = new ArrayList<Integer>();
	String formatContent = FormatAt.getInstance().GenerateRefererLinks(
		userMapper, content, userIds);
	comment.setContent(formatContent);
	if (StringUtils.isEmpty(sourceFrom)) {
	    comment.setSourceFrom("P");
	}
	comment.setBlogId(blogId);
	blogCommentMapper.insert(comment);

	// 更新积分
	User curUser = userService.findUser(sessionUser.getUserId().toString(),
		QueryUserType.USERID);
	curUser.setMark(curUser.getMark() + MarkEnums.MARK2.getMark());
	userMapper.updateSelective(curUser);

	NoticeParam noticeParm = new NoticeParam();
	noticeParm.setArticleId(blogId);
	noticeParm.setNoticeType(NoticeType.REBLOG);
	noticeParm.setAtUserIds(userIds);
	noticeParm.setSendUserId(sessionUser.getUserId());
	noticeParm.setCommentId(comment.getId());
	noticeParm.setReceivedUserId(atUserId);
	NoticeThread noticeThread = new NoticeThread(noticeParm);
	Thread thread = new Thread(noticeThread);
	thread.start();

	return comment;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
    public void deleteBatch(Map<String, String> param, Integer userId)
	    throws BusinessException {
	String keyStr = param.get("kyes");

	if (StringUtils.isEmpty(keyStr)) {
	    throw new BusinessException("参数错误!");
	}
	String blogIdStr = param.get("blogId");
	if (StringUtils.isEmpty(blogIdStr) || !StringUtils.isNumber(blogIdStr)) {
	    throw new BusinessException("参数错误!");
	}
	Blog blog = this.blogMapper.selectBaseInfo(param);
	if (blog == null || blog.getUserId().intValue() != userId) {
	    throw new BusinessException("你没有权限删除次评论");
	}
	String[] keys = keyStr.split(",");
	List<String> list = Arrays.asList(keys);
	blogCommentMapper.deleteBatch(Integer.parseInt(blogIdStr), list);
    }

    @Override
    public UlewoPaginationResult<BlogComment> queryBlogCommentByBlogId(
	    Map<String, String> map) throws BusinessException {
	String blogIdStr = map.get("blogId");
	String pageStr = map.get("page");
	UlewoPaginationResult<BlogComment> result = new UlewoPaginationResult<BlogComment>();
	if (!StringUtils.isNumber(blogIdStr)
		|| !StringUtils.isNumber(map.get("userId"))) {
	    throw new BusinessException("参数错误");
	}
	Integer pageNo = 0;
	if (StringUtils.isNumber(pageStr)) {
	    pageNo = Integer.parseInt(pageStr);
	}
	int count = blogCommentMapper.selectBaseInfoCount(map);
	int pageSize = PageSize.SIZE15.getSize();
	if (!StringUtils.isEmpty(map.get("pageSize"))) {
	    pageSize = Integer.parseInt(map.get("pageSize"));
	}
	SimplePage page = new SimplePage(pageNo, count, pageSize);
	List<BlogComment> list = blogCommentMapper
		.selectBaseInfoList(map, page);
	result.setPage(page);
	result.setList(list);
	return result;
    }

}
