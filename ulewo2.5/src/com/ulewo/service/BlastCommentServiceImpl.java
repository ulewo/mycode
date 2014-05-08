package com.ulewo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ulewo.enums.MarkEnums;
import com.ulewo.enums.MaxLengthEnums;
import com.ulewo.enums.NoticeType;
import com.ulewo.enums.PageSize;
import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.BlastCommentMapper;
import com.ulewo.mapper.UserMapper;
import com.ulewo.model.BlastComment;
import com.ulewo.model.NoticeParam;
import com.ulewo.model.User;
import com.ulewo.util.Constant;
import com.ulewo.util.SimplePage;
import com.ulewo.util.StringUtils;
import com.ulewo.util.UlewoPaginationResult;

@Service("reTalkService")
public class BlastCommentServiceImpl implements BlastCommentService {
	@Resource
	private UserMapper<User> userMapper;

	@Resource
	private BlastCommentMapper<BlastComment> blastCommentMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public BlastComment saveBlastComment(Map<String, String> map, Integer userId) throws BusinessException {

		String content = map.get("content");
		String blastIdStr = map.get("blastId");
		String atUserIdStr = map.get("atUserId");
		String atUserName = map.get("atUserName");

		if (StringUtils.isEmpty(content)) {
			throw new BusinessException("回复内容不能为空");
		}

		if (content.length() > MaxLengthEnums.MAXLENGTH250.getLength()) {
			throw new BusinessException("内容长度不能超过250字符");
		}

		if (!StringUtils.isNumber(blastIdStr)) {
			throw new BusinessException("请求参数错误");
		}
		Integer atUserId = null;
		int blastId = Integer.parseInt(blastIdStr);
		BlastComment comment = new BlastComment();
		comment.setBlastId(blastId);
		comment.setContent(content);
		comment.setUserId(userId);

		// 如果atUserId不为空 ，判断是否是int类型
		if (StringUtils.isNotEmpty(atUserIdStr) && !StringUtils.isNumber(atUserIdStr)) {
			throw new BusinessException("传入的参数有误");
		} else if (StringUtils.isNotEmpty(atUserIdStr) && StringUtils.isNumber(atUserIdStr)) {
			atUserId = Integer.parseInt(atUserIdStr);
			comment.setAtUserId(atUserId);
			comment.setAtUserName(atUserName);
		}

		String cotent = StringUtils.clearHtml(comment.getContent());
		List<Integer> userIds = new ArrayList<Integer>();
		String formatContent = FormatAt.getInstance(Constant.TYPE_TALK).GenerateRefererLinks(userMapper, cotent,
				userIds);
		comment.setContent(formatContent);
		comment.setCreateTime(StringUtils.dateFormater.get().format(new Date()));
		blastCommentMapper.insert(comment);

		User user = this.userMapper.selectUserByUserId(userId);
		comment.setUserName(user.getUserName());
		comment.setUserIcon(user.getUserIcon());
		user.setMark(user.getMark() + MarkEnums.MARK1.getMark());

		NoticeParam noticeParm = new NoticeParam();
		noticeParm.setArticleId(blastId);
		noticeParm.setNoticeType(NoticeType.REBLAST);
		noticeParm.setAtUserIds(userIds);
		noticeParm.setSendUserId(userId);
		noticeParm.setCommentId(comment.getId());
		noticeParm.setReceivedUserId(atUserId);
		NoticeThread noticeThread = new NoticeThread(noticeParm);
		Thread thread = new Thread(noticeThread);
		thread.start();

		return comment;
	}

	public UlewoPaginationResult<BlastComment> queryBlastCommentByPag(Map<String, String> map) throws BusinessException {
		String blastIdStr = map.get("blastId");
		if (!StringUtils.isNumber(blastIdStr)) {
			throw new BusinessException("参数错误");
		}
		int page_int = 0;
		String pageStr = map.get("page");
		if (!StringUtils.isEmpty(pageStr) && StringUtils.isNumber(pageStr)) {
			page_int = Integer.parseInt(pageStr);
		}
		int count = blastCommentMapper.selectBaseInfoCount(map);
		int pageSize = PageSize.SIZE20.getSize();
		if (StringUtils.isNumber(map.get("rows"))) {
			pageSize = Integer.parseInt(map.get("rows"));
		}
		SimplePage page = new SimplePage(page_int, count, pageSize);
		List<BlastComment> list = this.blastCommentMapper.selectBaseInfoList(map, page);
		UlewoPaginationResult<BlastComment> result = new UlewoPaginationResult<BlastComment>(page, list);
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void deleteComments(Map<String, String> map) throws BusinessException {
		String keyStr = map.get("key");
		String[] keys = keyStr.split(",");
		for (String key : keys) {
			map.put("id", key);
			int count = this.blastCommentMapper.delete(map);
			if (count == 0) {
				throw new BusinessException("没有找到相应的记录!");
			}
		}
	}
}
