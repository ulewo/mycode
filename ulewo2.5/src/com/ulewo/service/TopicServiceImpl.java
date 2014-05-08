package com.ulewo.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ulewo.enums.AllowPostEnums;
import com.ulewo.enums.FileTypeEnums;
import com.ulewo.enums.LengthEnums;
import com.ulewo.enums.MarkEnums;
import com.ulewo.enums.NoticeType;
import com.ulewo.enums.PageSize;
import com.ulewo.enums.SurveyTypeEnums;
import com.ulewo.enums.TopicTypeEnums;
import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.AttachmentMapper;
import com.ulewo.mapper.GroupMapper;
import com.ulewo.mapper.TopicCategoryMapper;
import com.ulewo.mapper.TopicMapper;
import com.ulewo.mapper.TopicSurveyDtlMapper;
import com.ulewo.mapper.TopicSurveyMapper;
import com.ulewo.mapper.UserMapper;
import com.ulewo.model.Attachment;
import com.ulewo.model.Group;
import com.ulewo.model.NoticeParam;
import com.ulewo.model.SessionUser;
import com.ulewo.model.Topic;
import com.ulewo.model.TopicCategory;
import com.ulewo.model.TopicSurvey;
import com.ulewo.model.TopicSurveyDtl;
import com.ulewo.model.User;
import com.ulewo.util.Constant;
import com.ulewo.util.ScaleFilter2;
import com.ulewo.util.SimplePage;
import com.ulewo.util.StringUtils;
import com.ulewo.util.UlewoPaginationResult;
import com.ulewo.util.UlewoPaginationResult4Json;

@Service("topicService")
@Transactional
public class TopicServiceImpl extends GroupAuthorityService implements TopicService {
	@Resource
	private TopicMapper<Topic> topicMapper;
	@Resource
	private AttachmentMapper<Attachment> attachmentMapper;
	@Resource
	private UserMapper<User> userMapper;
	@Resource
	private GroupMapper<Group> groupMapper;
	@Resource
	private TopicCategoryMapper<TopicCategory> topicCategoryMapper;
	@Resource
	private TopicSurveyMapper<TopicSurvey> topicSurveyMapper;
	@Resource
	private TopicSurveyDtlMapper<TopicSurveyDtl> topicSurveyDtlMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public Topic addTopic(Map<String, String> map, SessionUser sessionUser, String[] surveyTitles,
			HttpServletRequest request) throws BusinessException {
		String title = map.get("title");
		String content = map.get("content");
		String gid = map.get("gid");
		String categoryId = map.get("categoryId");
		String keyWord = map.get("keyWord");
		String topicType = map.get("topicType");
		if (StringUtils.isEmpty(title) || title.length() > LengthEnums.Length200.getLength()) {
			throw new BusinessException("标题不能为空,且不能长度超过200!");
		}
		if (StringUtils.isEmpty(content) && TopicTypeEnums.COMMON.getValue().equals(topicType)) {
			throw new BusinessException("内容不能为空");
		}
		if (StringUtils.isEmpty(gid)) {
			throw new BusinessException("参数错误");
		}
		Group group = groupMapper.selectBaseInfo(map);
		if (null == group) {
			throw new BusinessException("窝窝不存在!");
		}
		if (!StringUtils.isNumber(categoryId)) {
			throw new BusinessException("参数错误");
		}
		if (Integer.parseInt(categoryId) != 0) {
			TopicCategory category = topicCategoryMapper.selectBaseInfo(map);
			if (!AllowPostEnums.ALLOW.getValue().equals(category.getAllowPost())
					&& group.getGroupUserId().intValue() != sessionUser.getUserId().intValue()) {
				throw new BusinessException("此模块不允许发帖");
			}
		}
		topicType = TopicTypeEnums.COMMON.getValue().equals(topicType) ? TopicTypeEnums.COMMON.getValue()
				: TopicTypeEnums.SURVEY.getValue();

		int gid_int = Integer.parseInt(gid);
		Topic topic = new Topic();
		topic.setGid(gid_int);
		title = StringUtils.clearHtml(title);
		topic.setTitle(title);
		topic.setCategoryId(Integer.parseInt(categoryId));
		topic.setKeyWord(StringUtils.clearHtml(keyWord));
		String summary = StringUtils.clearHtml(content);
		if (summary.length() > LengthEnums.Length200.getLength()) {
			summary = summary.substring(0, LengthEnums.Length100.getLength()) + "......";
		}
		topic.setSummary(summary);
		List<Integer> userIds = new ArrayList<Integer>();
		String formatContent = FormatAt.getInstance(Constant.TYPE_TALK).GenerateRefererLinks(userMapper, content,
				userIds);
		topic.setContent(formatContent);
		topic.setUserId(sessionUser.getUserId());
		String curDate = StringUtils.dateFormater.get().format(new Date());
		topic.setCreateTime(curDate);
		topic.setLastCommentTime(curDate);
		topic.setTopicType(topicType);
		String topicImage = StringUtils.getImages(content);
		topic.setTopicImage(topicImage);
		String topicImageSmall = getTopicImageSmall(topicImage, request);
		topic.setTopicImageSmall(topicImageSmall);
		this.topicMapper.insert(topic);
		topic.setReadCount(0);
		topic.setCommentCount(0);
		// 更新用户积分
		User user = this.userMapper.selectUserByUserId(sessionUser.getUserId());
		topic.setUserName(user.getUserName());
		topic.setUserIcon(user.getUserIcon());
		user.setMark(user.getMark() + MarkEnums.MARK5.getMark());
		this.userMapper.updateSelective(user);
		// 判断是否是投票帖子
		if (TopicTypeEnums.SURVEY.getValue().equals(topicType)) {
			saveSurvey(topic, surveyTitles, map);
		}
		// 是否有附件
		String attached_file = map.get("attached_file");
		String attached_file_name = map.get("attached_file_name");
		String markStr = map.get("mark");
		if (!StringUtils.isNumber(markStr)) {
			throw new BusinessException("积分必须是数字!");
		}
		if (StringUtils.isNotEmpty(attached_file) && StringUtils.isNotEmpty(attached_file_name)) {
			Attachment attachment = new Attachment();
			attachment.setGid(gid_int);
			attachment.setTopicId(topic.getTopicId());
			attachment.setCreateTime(curDate);
			attachment.setFileName(attached_file_name);
			attachment.setFileUrl(attached_file);
			attachment.setFileType(FileTypeEnums.RAR.getValue());
			attachment.setDownloadMark(Integer.parseInt(markStr));
			attachment.setDownloadCount(0);
			attachmentMapper.insert(attachment);
		}
		NoticeParam noticeParm = new NoticeParam();
		noticeParm.setArticleId(topic.getTopicId());
		noticeParm.setNoticeType(NoticeType.ATINTOPIC);
		noticeParm.setAtUserIds(userIds);
		noticeParm.setSendUserId(sessionUser.getUserId());
		NoticeThread noticeThread = new NoticeThread(noticeParm);
		Thread thread = new Thread(noticeThread);
		thread.start();
		return topic;
	}

	private String getTopicImageSmall(String topicImage, HttpServletRequest request) {
		StringBuilder topicImageSmall = new StringBuilder();
		if (topicImage != null) {
			String port = request.getServerPort() == 80 ? "" : ":" + request.getServerPort();
			String hostPath = "http://" + request.getServerName() + port + request.getContextPath();
			String realPath = request.getSession().getServletContext().getRealPath("");
			String[] topoicImages = topicImage.split("\\|");
			for (String img : topoicImages) {
				if (StringUtils.isEmpty(img)) {
					continue;
				}
				String imagePath = img.replaceAll(hostPath, "");
				String sourcePath = realPath + imagePath;
				String smallPath = sourcePath + ".small.jpg";
				String smallSavePath = hostPath + imagePath + ".small.jpg";
				BufferedImage src = null;
				try {
					src = ImageIO.read(new File(sourcePath));
					BufferedImage dst = new ScaleFilter2(120).filter(src, null);
					ImageIO.write(dst, "JPEG", new File(smallPath));
				} catch (IOException e) {
					e.printStackTrace();
					break;
				}
				topicImageSmall.append(smallSavePath).append("|");
			}
		}
		return topicImageSmall.toString();
	}

	/**
	 * 投票帖
	 * 
	 * @param topic
	 * @param surveyTitles
	 * @param map
	 * @throws BusinessException
	 */
	private void saveSurvey(Topic topic, String[] surveyTitles, Map<String, String> map) throws BusinessException {
		// 保存投票主档
		int gid_int = Integer.parseInt(map.get("gid"));
		TopicSurvey survey = new TopicSurvey();
		survey.setTopicId(topic.getTopicId());
		String surveyType = map.get("surveyType");
		surveyType = SurveyTypeEnums.MULTI.getValue().equals(surveyType) ? SurveyTypeEnums.MULTI.getValue()
				: SurveyTypeEnums.SINGLE.getValue();
		survey.setSurveyType(surveyType);
		String endDate = map.get("endDate");
		if (StringUtils.isEmpty(endDate)) {
			throw new BusinessException("投票结束日期不能为空");
		}
		if (StringUtils.beforeNowDate(endDate)) {
			throw new BusinessException("投票结束日期必须大于当天");
		}
		survey.setEndDate(map.get("endDate"));
		survey.setGid(gid_int);
		topicSurveyMapper.insert(survey); // 保存投票明细
		if (surveyTitles == null || surveyTitles.length <= 1) {
			throw new BusinessException("投票选项必须大于1");
		}
		int notEmptyCount = 0;
		for (int i = 0, length = surveyTitles.length; i < length; i++) {
			if (StringUtils.isNotEmpty(surveyTitles[i])) {
				if (StringUtils.getRealLength(surveyTitles[i]) > LengthEnums.Length30.getLength()) {
					throw new BusinessException("第" + (i + 1) + "行超过30个字");
				}
				TopicSurveyDtl dtl = new TopicSurveyDtl();
				dtl.setTopicId(topic.getTopicId());
				dtl.setTitle(StringUtils.clearHtml(surveyTitles[i]));
				topicSurveyDtlMapper.insert(dtl);
				notEmptyCount++;
			}
		}
		if (notEmptyCount <= 1) {
			throw new BusinessException("投票选项必须大于1");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void updateTopic(Map<String, String> map, SessionUser sessionUser, HttpServletRequest request)
			throws BusinessException {
		String topicIdStr = map.get("topicId");
		String title = map.get("title");
		String content = map.get("content");
		String categoryId = map.get("categoryId");
		String keyWord = map.get("keyWord");
		if (StringUtils.isEmpty(topicIdStr) || !StringUtils.isNumber(topicIdStr)) {
			throw new BusinessException("参数错误!");
		}
		if (StringUtils.isEmpty(title) || title.length() > LengthEnums.Length200.getLength()) {
			throw new BusinessException("标题不能为空,且不能长度超过200!");
		}
		if (StringUtils.isEmpty(content)) {
			throw new BusinessException("内容不能为空");
		}
		Map<String, String> tempParam = new HashMap<String, String>();
		tempParam.put("topicId", topicIdStr);
		Topic tempTopic = topicMapper.selectBaseInfo(tempParam);
		if (null == tempTopic) {
			throw new BusinessException("修改的主题不存在!");
		}
		// 校验权限
		this.checkGroupOpAuthority(tempTopic.getGid(), sessionUser.getUserId());
		Topic topic = new Topic();
		topic.setGid(tempTopic.getGid());
		topic.setTopicId(tempTopic.getTopicId());
		topic.setTitle(StringUtils.clearHtml(title));
		topic.setCategoryId(Integer.parseInt(categoryId));
		topic.setKeyWord(StringUtils.clearHtml(keyWord));
		topic.setContent(content);
		String summary = StringUtils.clearHtml(content);
		if (summary.length() > LengthEnums.Length100.getLength()) {
			summary = summary.substring(0, LengthEnums.Length100.getLength()) + "......";
		}
		topic.setSummary(summary);
		List<Integer> userIds = new ArrayList<Integer>();
		String formatContent = FormatAt.getInstance(Constant.TYPE_TALK).GenerateRefererLinks(userMapper, content,
				userIds);
		topic.setContent(formatContent);

		String topicImage = StringUtils.getImages(content);
		topic.setTopicImage(topicImage);
		String topicImageSmall = getTopicImageSmall(topicImage, request);
		topic.setTopicImageSmall(topicImageSmall);

		int count = this.topicMapper.updateSelective(topic);
		if (count == 0) {
			throw new BusinessException("修改的主题不存在!");
		}
		NoticeParam noticeParm = new NoticeParam();
		noticeParm.setArticleId(topic.getTopicId());
		noticeParm.setNoticeType(NoticeType.ATINTOPIC);
		noticeParm.setAtUserIds(userIds);
		noticeParm.setSendUserId(sessionUser.getUserId());
		NoticeThread noticeThread = new NoticeThread(noticeParm);
		Thread thread = new Thread(noticeThread);
		thread.start();
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void topTopic(Map<String, String> map, SessionUser sessionUser) throws BusinessException {
		String gid = map.get("gid");
		if (StringUtils.isEmpty(gid) || StringUtils.isEmpty(map.get("key"))) {
			throw new BusinessException("参数错误!");
		}
		Integer userId = sessionUser.getUserId();
		this.checkGroupOpAuthority(Integer.parseInt(gid), userId);
		String keyStr = map.get("key");
		String[] keys = keyStr.split(",");
		String grade = map.get("grade");
		int grade_int = Integer.parseInt(grade);
		for (String key : keys) {
			String topicId = key;
			if (!StringUtils.isNumber(topicId)) {
				throw new BusinessException("参数错误");
			}
			map.put("topicId", key);
			Topic tempTopic = topicMapper.selectBaseInfo(map);
			if (null == tempTopic) {
				throw new BusinessException("主题不存在!");
			}
			this.checkGroupOpAuthority(tempTopic.getGid(), sessionUser.getUserId());
			tempTopic.setGrade(grade_int);
			topicMapper.updateSelective(tempTopic);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void essenceTopic(Map<String, String> map, SessionUser sessionUser) throws BusinessException {
		String gid = map.get("gid");
		if (StringUtils.isEmpty(gid) || StringUtils.isEmpty(map.get("key"))) {
			throw new BusinessException("参数错误!");
		}
		Integer userId = sessionUser.getUserId();
		this.checkGroupOpAuthority(Integer.parseInt(gid), userId);
		String essence = map.get("essence");
		String keyStr = map.get("key");
		String[] keys = keyStr.split(",");
		for (String key : keys) {
			String topicId = key;
			if (!StringUtils.isNumber(topicId)) {
				throw new BusinessException("参数错误");
			}
			map.put("topicId", key);
			Topic tempTopic = topicMapper.selectBaseInfo(map);
			if (null == tempTopic) {
				throw new BusinessException("主题不存在!");
			}
			this.checkGroupOpAuthority(tempTopic.getGid(), sessionUser.getUserId());
			tempTopic.setEssence(essence);
			topicMapper.updateSelective(tempTopic);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void deleteTopic(Map<String, String> map, SessionUser sessionUser) throws BusinessException {
		String gid = map.get("gid");
		if (StringUtils.isEmpty(gid) || StringUtils.isEmpty(map.get("key"))) {
			throw new BusinessException("参数错误!");
		}
		Integer userId = sessionUser.getUserId();
		this.checkGroupOpAuthority(Integer.parseInt(gid), userId);
		String keyStr = map.get("key");
		String[] keys = keyStr.split(",");
		for (String key : keys) {
			map.put("topicId", key);
			int count = this.topicMapper.delete(map);
			if (count == 0) {
				throw new BusinessException("没有找到相应的记录!");
			}
		}
	}

	@Override
	public UlewoPaginationResult<Topic> findTopics(Map<String, String> map) throws BusinessException {
		String gid = map.get("gid");
		int page_no = 0;
		if (StringUtils.isNumber(map.get("page"))) {
			page_no = Integer.parseInt(map.get("page"));
		}
		if (StringUtils.isNotEmpty(gid) && !StringUtils.isNumber(gid)) {
			throw new BusinessException("参数错误!");
		}
		int pageSize = PageSize.SIZE20.getSize();
		if (StringUtils.isNumber(map.get("rows"))) {
			pageSize = Integer.parseInt(map.get("rows"));
		}
		int count = this.topicMapper.selectTopicCount(map);
		SimplePage page = new SimplePage(page_no, count, pageSize);
		List<Topic> list = this.topicMapper.selectTopicList(map, page);
		UlewoPaginationResult<Topic> result = new UlewoPaginationResult<Topic>(page, list);
		return result;
	}

	@Override
	public UlewoPaginationResult4Json<Topic> findTopics4Json(Map<String, String> map) throws BusinessException {
		String gid = map.get("gid");
		int page_no = 0;
		if (StringUtils.isNumber(map.get("page"))) {
			page_no = Integer.parseInt(map.get("page"));
		}
		if (!StringUtils.isNumber(gid)) {
			throw new BusinessException("参数错误!");
		}
		int pageSize = StringUtils.isEmpty(map.get("rows")) ? 15 : Integer.parseInt(map.get("rows"));
		int count = this.topicMapper.selectTopicCount(map);
		SimplePage page = new SimplePage(page_no, count, pageSize);
		List<Topic> list = this.topicMapper.selectTopicList(map, page);
		UlewoPaginationResult4Json<Topic> result = new UlewoPaginationResult4Json<Topic>();
		result.setRows(list);
		result.setTotal(count);
		return result;
	}

	public Topic showTopic(Map<String, String> map) throws BusinessException {
		String id = map.get("topicId");
		if (!StringUtils.isNumber(id)) {
			throw new BusinessException("参数错误!");
		}
		Topic topic = this.topicMapper.showTopic(map);
		if (null == topic) {
			throw new BusinessException("访问的文章不存在!");
		}
		topic.setReadCount(topic.getReadCount() + 1);
		this.topicMapper.updateSelective(topic);
		// 查询附件
		Attachment attachment = attachmentMapper.selectBaseInfo(map);
		if (attachment != null) {
			attachment.setFileUrl("");
			topic.setFile(attachment);
		}
		return topic;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void deleteTopicByAdmin(Map<String, String> map) throws BusinessException {
		if (StringUtils.isEmpty(map.get("key"))) {
			throw new BusinessException("参数错误!");
		}
		String keyStr = map.get("key");
		String[] keys = keyStr.split(",");
		for (String key : keys) {
			map.put("topicId", key);
			int count = this.topicMapper.delete(map);
			if (count == 0) {
				throw new BusinessException("没有找到相应的记录!");
			}
		}
	}

	@Override
	public List<Topic> hotTopics(Map<String, String> map) throws BusinessException {
		if (StringUtils.isEmpty(map.get("key"))) {
			throw new BusinessException("参数错误!");
		}
		String keyStr = map.get("key");
		String[] keys = keyStr.split(",");
		List<Topic> list = this.topicMapper.selectTopicByTopicids(keys);
		return list;
	}
}
