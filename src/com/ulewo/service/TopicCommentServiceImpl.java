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
import com.ulewo.enums.NoticeType;
import com.ulewo.enums.PageSize;
import com.ulewo.enums.QueryOrder;
import com.ulewo.enums.SourceFromEnums;
import com.ulewo.enums.TopicCommentTypeEnums;
import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.TopicCommentMapper;
import com.ulewo.mapper.TopicMapper;
import com.ulewo.mapper.UserMapper;
import com.ulewo.model.NoticeParam;
import com.ulewo.model.SessionUser;
import com.ulewo.model.Topic;
import com.ulewo.model.TopicComment;
import com.ulewo.model.User;
import com.ulewo.util.Constant;
import com.ulewo.util.SimplePage;
import com.ulewo.util.StringUtils;
import com.ulewo.util.UlewoPaginationResult;
import com.ulewo.util.UlewoPaginationResult4Json;

/**
 * @Title:
 * @Description: 文章回复业务实现
 * @author luohl
 * @date 2012-3-30
 * @version V1.0
 */
@Service("topicCommentService")
public class TopicCommentServiceImpl extends GroupAuthorityService implements TopicCommentService {

	@Resource
	private TopicCommentMapper<TopicComment> topicCommentMapper;

	@Resource
	private TopicMapper<Topic> topicMapper;

	@Resource
	private UserMapper<User> userMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public TopicComment addComment(Map<String, String> map, SessionUser sessionUser) throws BusinessException {
		String gid = map.get("gid");
		String topicId = map.get("topicId");
		String content = map.get("content");
		String pid = map.get("pid");
		String atUserId = map.get("atUserId");
		if (StringUtils.isEmpty(gid) || !StringUtils.isNumber(gid) || StringUtils.isEmpty(topicId)
				|| !StringUtils.isNumber(topicId) || StringUtils.isEmpty(content)
				|| (!StringUtils.isEmpty(pid) && !StringUtils.isNumber(pid))
				|| (!StringUtils.isEmpty(atUserId) && !StringUtils.isNumber(atUserId))) {
			throw new BusinessException("参数错误");
		}
		int pid_int = 0;
		if (StringUtils.isNotEmpty(pid)) {
			pid_int = Integer.parseInt(pid);
		}
		Integer atUserId_int = null;
		if (StringUtils.isNotEmpty(atUserId)) {
			atUserId_int = Integer.parseInt(atUserId);
		}
		int topicid_int = Integer.parseInt(topicId);
		int gid_int = Integer.parseInt(gid);
		TopicComment comment = new TopicComment();
		comment.setGid(gid_int);
		comment.setTopicId(topicid_int);
		if (TopicCommentTypeEnums.SUBCOMMENT.getValue().equals(map.get("commentType"))) {
			content = StringUtils.formateHtml(content);
		}
		List<Integer> userIds = new ArrayList<Integer>();
		String formatContent = FormatAt.getInstance(Constant.TYPE_TALK).GenerateRefererLinks(userMapper, content,
				userIds);
		comment.setContent(formatContent);
		comment.setUserId(sessionUser.getUserId());
		comment.setUserName(sessionUser.getUserName());
		comment.setUserIcon(sessionUser.getUserIcon());
		comment.setPid(pid_int);
		if (atUserId_int != null) {
			User user = userMapper.selectUserByUserId(atUserId_int);
			if (user == null) {
				throw new BusinessException("回复的人不存在");
			}
			comment.setAtUserName(user.getUserName());
			comment.setAtUserIcon(user.getUserIcon());
		}
		comment.setAtUserId(atUserId_int);
		comment.setSourceFrom(SourceFromEnums.PC.getValue());
		String curDate = StringUtils.dateFormater.get().format(new Date());
		comment.setCreateTime(curDate);
		this.topicCommentMapper.insert(comment);

		User user = this.userMapper.selectUserByUserId(sessionUser.getUserId());
		user.setMark(user.getMark() + MarkEnums.MARK2.getMark());
		this.userMapper.updateSelective(user);

		Topic topic = new Topic();
		topic.setTopicId(topicid_int);
		topic.setLastCommentTime(curDate);
		topic.setGid(gid_int);
		this.topicMapper.updateSelective(topic);
		NoticeParam noticeParm = new NoticeParam();
		noticeParm.setArticleId(comment.getTopicId());
		noticeParm.setNoticeType(NoticeType.RETOPIC);
		noticeParm.setAtUserIds(userIds);
		noticeParm.setSendUserId(sessionUser.getUserId());
		noticeParm.setCommentId(comment.getId());
		noticeParm.setReceivedUserId(atUserId_int);
		NoticeThread noticeThread = new NoticeThread(noticeParm);
		Thread thread = new Thread(noticeThread);
		thread.start();
		return comment;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void deleteComment(Map<String, String> map, SessionUser sessionUser) throws BusinessException {
		String topicId = map.get("topicId");
		String idstr = map.get("key");
		if (StringUtils.isEmpty(topicId) || !StringUtils.isNumber(topicId) || StringUtils.isEmpty(idstr)) {
			throw new BusinessException("参数错误");
		}

		Topic topic = topicMapper.selectBaseInfo(map);
		if (null == topic) {
			throw new BusinessException("主题不存在");
		}
		this.checkGroupOpAuthority(topic.getGid(), sessionUser.getUserId());
		String[] ids = idstr.split(",");
		for (String id : ids) {
			map.put("id", id);
			int count = this.topicCommentMapper.delete(map);
			if (count == 0) {
				throw new BusinessException("评论不存在");
			}
		}
	}

	/**
	 * 回复的文章
	 */
	@Override
	public UlewoPaginationResult<TopicComment> queryCommentByTopicId(Map<String, String> map) {
		String topicId = map.get("topicId");
		if (!StringUtils.isNumber(topicId)) {
			return null;
		}
		int page_no = 0;
		if (StringUtils.isNumber(map.get("page"))) {
			page_no = Integer.parseInt(map.get("page"));
		}
		int count = this.topicCommentMapper.pComentCount(map);
		int pageSize = PageSize.SIZE20.getSize();
		if (StringUtils.isNumber(map.get("rows"))) {
			pageSize = Integer.parseInt(map.get("rows"));
		}
		SimplePage page = new SimplePage(page_no, count, pageSize);
		List<TopicComment> list = this.topicCommentMapper.selectPComentList(map, page);
		if (null != list) {
			map.put("pidStart", String.valueOf(list.get(0).getId()));
			map.put("pidEnd", String.valueOf(list.get(list.size() - 1).getId()));
			List<TopicComment> subList = this.topicCommentMapper.selectSubComment(map);
			for (TopicComment comment : list) {
				for (TopicComment sub : subList) {
					if (comment.getId().intValue() == sub.getPid().intValue()) {
						comment.getChildList().add(sub);
					}
				}
			}
		}

		UlewoPaginationResult<TopicComment> result = new UlewoPaginationResult<TopicComment>(page, list);
		return result;
	}

	public UlewoPaginationResult4Json<TopicComment> queryComment4JsonByTopicId(Map<String, String> map)
			throws BusinessException {
		String topicId = map.get("topicId");
		map.put("order", QueryOrder.DESC.getValue());
		int page_no = 0;
		if (StringUtils.isNumber(map.get("page"))) {
			page_no = Integer.parseInt(map.get("page"));
		}
		if (!StringUtils.isNumber(topicId)) {
			throw new BusinessException("参数错误!");
		}
		int pageSize = PageSize.SIZE20.getSize();
		if (StringUtils.isNumber(map.get("rows"))) {
			pageSize = Integer.parseInt(map.get("rows"));
		}
		int count = this.topicCommentMapper.selectBaseInfoCount(map);
		SimplePage page = new SimplePage(page_no, count, pageSize);
		List<TopicComment> list = this.topicCommentMapper.selectBaseInfoList(map, page);
		UlewoPaginationResult4Json<TopicComment> result = new UlewoPaginationResult4Json<TopicComment>();
		result.setRows(list);
		result.setTotal(count);
		return result;
	}

	public void deleteCommentByAdmin(Map<String, String> map) throws BusinessException {
		String topicId = map.get("topicId");
		String idstr = map.get("key");
		if (StringUtils.isEmpty(topicId) || !StringUtils.isNumber(topicId) || StringUtils.isEmpty(idstr)) {
			throw new BusinessException("参数错误");
		}
		String[] ids = idstr.split(",");
		for (String id : ids) {
			map.put("id", id);
			int count = this.topicCommentMapper.delete(map);
			if (count == 0) {
				throw new BusinessException("评论不存在");
			}
		}
	}
}
