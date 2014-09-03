package com.ulewo.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ulewo.enums.AllowPostEnums;
import com.ulewo.enums.CollectionTypeEnums;
import com.ulewo.enums.FileTypeEnums;
import com.ulewo.enums.LengthEnums;
import com.ulewo.enums.LikeType;
import com.ulewo.enums.MarkEnums;
import com.ulewo.enums.NoticeType;
import com.ulewo.enums.PageSize;
import com.ulewo.enums.SurveyTypeEnums;
import com.ulewo.enums.TopicTypeEnums;
import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.AttachmentMapper;
import com.ulewo.mapper.CollectionMapper;
import com.ulewo.mapper.GroupMapper;
import com.ulewo.mapper.TopicCategoryMapper;
import com.ulewo.mapper.TopicMapper;
import com.ulewo.mapper.TopicSurveyDtlMapper;
import com.ulewo.mapper.TopicSurveyMapper;
import com.ulewo.mapper.UserMapper;
import com.ulewo.model.Attachment;
import com.ulewo.model.Collection;
import com.ulewo.model.Group;
import com.ulewo.model.Like;
import com.ulewo.model.NoticeParam;
import com.ulewo.model.SessionUser;
import com.ulewo.model.Topic;
import com.ulewo.model.TopicCategory;
import com.ulewo.model.TopicSurvey;
import com.ulewo.model.TopicSurveyDtl;
import com.ulewo.model.User;
import com.ulewo.util.Constant;
import com.ulewo.util.ImageUtils;
import com.ulewo.util.SendMailThread;
import com.ulewo.util.SimplePage;
import com.ulewo.util.StringUtils;
import com.ulewo.util.UlewoPaginationResult;
import com.ulewo.util.UlewoPaginationResult4Json;
import com.ulewo.vo.TopicVo;

@Service("topicService")
@Transactional
public class TopicServiceImpl extends GroupAuthorityService implements
		TopicService {
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
	@Resource
	private LikeService likeService;
	@Resource
	private CollectionMapper<Collection> collectionMapper;
	@Log
	Logger log;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public Topic addTopic(Map<String, String> map, SessionUser sessionUser,
			String[] surveyTitles, HttpServletRequest request)
			throws BusinessException {
		String title = map.get("title");
		String content = map.get("content");
		String gid = map.get("gid");
		String categoryId = map.get("categoryId");
		String keyWord = map.get("keyWord");
		String topicType = map.get("topicType");
		if (StringUtils.isEmpty(title)
				|| title.length() > LengthEnums.Length200.getLength()) {
			throw new BusinessException("标题不能为空,且不能长度超过200!");
		}
		if (StringUtils.isEmpty(content)
				&& TopicTypeEnums.COMMON.getValue().equals(topicType)) {
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
			if (!AllowPostEnums.ALLOW.getValue()
					.equals(category.getAllowPost())
					&& group.getGroupUserId().intValue() != sessionUser
							.getUserId().intValue()) {
				throw new BusinessException("此模块不允许发帖");
			}
		}
		topicType = TopicTypeEnums.COMMON.getValue().equals(topicType) ? TopicTypeEnums.COMMON
				.getValue() : TopicTypeEnums.SURVEY.getValue();

		int gid_int = Integer.parseInt(gid);
		Topic topic = new Topic();
		topic.setGid(gid_int);
		title = StringUtils.clearHtml(title);
		topic.setTitle(title);
		topic.setCategoryId(Integer.parseInt(categoryId));
		topic.setKeyWord(StringUtils.clearHtml(keyWord));
		String summary = StringUtils.clearHtml(content);
		if (summary.length() > LengthEnums.Length200.getLength()) {
			summary = summary.substring(0, LengthEnums.Length100.getLength())
					+ "......";
		}
		topic.setSummary(summary);
		List<Integer> userIds = new ArrayList<Integer>();
		String formatContent = FormatAt.getInstance(Constant.TYPE_TALK)
				.GenerateRefererLinks(userMapper, content, userIds);
		topic.setContent(formatContent);
		topic.setUserId(sessionUser.getUserId());
		String curDate = StringUtils.dateFormater.format(new Date());
		topic.setCreateTime(curDate);
		topic.setLastCommentTime(curDate);
		topic.setTopicType(topicType);
		String topicImage = StringUtils.getImages(content);
		topic.setTopicImage(topicImage);
		String topicImageSmall = ImageUtils.geImageSmall(topicImage, request,
				true);
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
		if (Integer.parseInt(markStr) > 20) {
			throw new BusinessException("最大积分不能超过20!");
		}
		if (StringUtils.isNotEmpty(attached_file)
				&& StringUtils.isNotEmpty(attached_file_name)) {
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

	/**
	 * 投票帖
	 * 
	 * @param topic
	 * @param surveyTitles
	 * @param map
	 * @throws BusinessException
	 */
	private void saveSurvey(Topic topic, String[] surveyTitles,
			Map<String, String> map) throws BusinessException {
		// 保存投票主档
		int gid_int = Integer.parseInt(map.get("gid"));
		TopicSurvey survey = new TopicSurvey();
		survey.setTopicId(topic.getTopicId());
		String surveyType = map.get("surveyType");
		surveyType = SurveyTypeEnums.MULTI.getValue().equals(surveyType) ? SurveyTypeEnums.MULTI
				.getValue() : SurveyTypeEnums.SINGLE.getValue();
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
				if (StringUtils.getRealLength(surveyTitles[i]) > LengthEnums.Length100
						.getLength()) {
					throw new BusinessException("第" + (i + 1) + "行超过100个字");
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
	public void updateTopic(Map<String, String> map, SessionUser sessionUser,
			HttpServletRequest request) throws BusinessException {
		String topicIdStr = map.get("topicId");
		String title = map.get("title");
		String content = map.get("content");
		String categoryId = map.get("categoryId");
		String keyWord = map.get("keyWord");
		if (StringUtils.isEmpty(topicIdStr)
				|| !StringUtils.isNumber(topicIdStr)) {
			throw new BusinessException("参数错误!");
		}
		if (StringUtils.isEmpty(title)
				|| title.length() > LengthEnums.Length200.getLength()) {
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
			summary = summary.substring(0, LengthEnums.Length100.getLength())
					+ "......";
		}
		topic.setSummary(summary);
		List<Integer> userIds = new ArrayList<Integer>();
		String formatContent = FormatAt.getInstance(Constant.TYPE_TALK)
				.GenerateRefererLinks(userMapper, content, userIds);
		topic.setContent(formatContent);

		String topicImage = StringUtils.getImages(content);
		topic.setTopicImage(topicImage);
		String topicImageSmall = ImageUtils.geImageSmall(topicImage, request,
				true);
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
	public void topTopic(Map<String, String> map, SessionUser sessionUser)
			throws BusinessException {
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
			this.checkGroupOpAuthority(tempTopic.getGid(),
					sessionUser.getUserId());
			tempTopic.setGrade(grade_int);
			topicMapper.updateSelective(tempTopic);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void essenceTopic(Map<String, String> map, SessionUser sessionUser)
			throws BusinessException {
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
			this.checkGroupOpAuthority(tempTopic.getGid(),
					sessionUser.getUserId());
			tempTopic.setEssence(essence);
			topicMapper.updateSelective(tempTopic);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void deleteTopic(Map<String, String> map, SessionUser sessionUser)
			throws BusinessException {
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
	public UlewoPaginationResult<Topic> findTopics(Map<String, String> map)
			throws BusinessException {
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
		UlewoPaginationResult<Topic> result = new UlewoPaginationResult<Topic>(
				page, list);
		return result;
	}

	@Override
	public UlewoPaginationResult4Json<Topic> findTopics4Json(
			Map<String, String> map) throws BusinessException {
		String gid = map.get("gid");
		int page_no = 0;
		if (StringUtils.isNumber(map.get("page"))) {
			page_no = Integer.parseInt(map.get("page"));
		}
		if (!StringUtils.isNumber(gid)) {
			throw new BusinessException("参数错误!");
		}
		int pageSize = StringUtils.isEmpty(map.get("rows")) ? 15 : Integer
				.parseInt(map.get("rows"));
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
	public void deleteTopicByAdmin(Map<String, String> map)
			throws BusinessException {
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
	public List<Topic> hotTopics(Map<String, String> map,
			HttpServletRequest request) throws BusinessException {
		if (StringUtils.isEmpty(map.get("key"))) {
			throw new BusinessException("参数错误!");
		}
		String keyStr = map.get("key");
		String[] keys = keyStr.split(",");
		List<Topic> list = this.topicMapper.selectTopicByTopicids(keys);
		StringBuffer sb = new StringBuffer();
		sb.append("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">");
		for (Topic data : list) {
			sb.append("<tr>");
			sb.append("<td style=\"padding:10px;\"><a href=\"http://ulewo.com/group/"
					+ data.getGid()
					+ "/topic/"
					+ data.getTopicId()
					+ "\" style=\"color:#3E62A6;text-decoration:none;font-size:16px;\">"
					+ data.getTitle() + "</a></td>");
			sb.append("<td style=\"color:#3E62A6;text-decoration:none;font-size:14px;\">"
					+ data.getUserName() + "</td>");
			sb.append("<td style=\"color:#666666;font-size:14px;\">发表于"
					+ data.getShowCreateTime() + "</td>");
			sb.append("</tr>");

			sb.append("<tr>");
			sb.append("<td colspan=\"3\" style=\"color:#666666;font-size:14px;padding:5px;\">"
					+ data.getSummary() + "</td>");
			sb.append("</tr>");
			if (data.getImages() != null && data.getImages().length > 0) {
				sb.append("<tr>");
				sb.append("<td colspan=\"3\" style=\"padding-top:5px;\">");
				int count = data.getImages().length;
				if (count > 5) {
					count = 5;
				}
				for (int i = 0; i < count; i++) {
					sb.append("<a href=\"http://ulewo.com/group/"
							+ data.getGid()
							+ "/topic/"
							+ data.getTopicId()
							+ "\" style=\"display:inline-block;margin-right:10px;padding-left:10px;\"><img src=\""
							+ data.getImages()[i]
							+ "\" style=\"max-width:100px;border:null\"></a>");
				}
				sb.append("</td></tr>");
			}
			sb.append("<tr>");
			sb.append("<td colspan=\"3\"><div style=\"border-bottom: 1px dashed #B2B3B2;height:10px;\"></div></td>");
			sb.append("</tr>");
		}
		sb.append("</table>");
		try {
			sendMail(sb.toString(), request);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}

		return list;
	}

	private void sendMail(String content, HttpServletRequest request)
			throws Exception {
		String path = request.getSession().getServletContext().getRealPath("/")
				+ "email/" + "address.txt";
		BufferedReader read = null;
		InputStreamReader inr = null;
		InputStream in = null;
		try {
			in = new FileInputStream(path);
			inr = new InputStreamReader(in);
			read = new BufferedReader(inr);
			String qq = null;
			while (null != (qq = read.readLine())) {
					Thread thread = new Thread(new SendMailThread(content,
 qq));
					thread.start();
					Thread.sleep(5000);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inr != null) {
				try {
					inr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (read != null) {
				try {
					read.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public Map<String, Object> findTopics4Api(Map<String, String> map)
			throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		String gid = map.get("gid");
		int page_no = 0;
		if (StringUtils.isNumber(map.get("pageIndex"))) {
			page_no = Integer.parseInt(map.get("pageIndex"));
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
		List<TopicVo> resultList = new ArrayList<TopicVo>();
		TopicVo vo = null;
		for (Topic topic : list) {
			vo = new TopicVo();
			vo.setTopicId(topic.getTopicId());
			vo.setUserIcon(topic.getUserIcon());
			vo.setUserId(topic.getUserId());
			vo.setUserName(topic.getUserName());
			vo.setGid(topic.getGid());
			vo.setCreateTime(topic.getShowCreateTime());
			vo.setTitle(topic.getTitle());

			String summary = topic.getSummary();
			if (summary.length() > LengthEnums.Length100.getLength()) {
				summary = summary.substring(0,
						LengthEnums.Length100.getLength()).trim();
			}
			vo.setSummary(summary);
			if (topic.getImages() != null && topic.getImages().length > 0) {
				int imageCount = 3;
				if (topic.getImages().length < imageCount) {
					imageCount = topic.getImages().length;
				}
				String[] images = new String[imageCount];
				for (int i = 0; i < imageCount; i++) {
					images[i] = topic.getImages()[i];
				}
				vo.setTopicImages(images);
			}
			vo.setReadCount(topic.getReadCount());
			vo.setCommentCount(topic.getCommentCount());
			resultList.add(vo);
		}
		result.put("page", page);
		result.put("list", resultList);
		return result;
	}

	@Override
	public Map<String, Object> showTopic4Api(Map<String, String> map,
			Integer userId) throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
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

		TopicVo vo = new TopicVo();
		if (null != userId) {
			Like like = new Like();
			like.setOpId(topic.getTopicId());
			like.setUserId(userId);
			like.setType(LikeType.TOPIC.getValue());
			int likeCount = likeService.getLikeCount(like);
			if (likeCount > 0) {
				vo.setLike(true);
			} else {
				vo.setLike(false);
			}
			Map<String, String> param = new HashMap<String, String>();
			param.put("userId", String.valueOf(userId));
			param.put("type", CollectionTypeEnums.TOPIC.getValue());
			param.put("articleId", String.valueOf(topic.getTopicId()));
			int cCount = collectionMapper.selectTopicInfoCount(param);
			if (cCount > 0) {
				vo.setCollection(true);
			} else {
				vo.setCollection(false);
			}
		} else {
			vo.setLike(false);
			vo.setCollection(false);
		}

		vo.setTopicId(topic.getTopicId());
		vo.setGid(topic.getGid());
		vo.setUserName(topic.getUserName());
		vo.setUserIcon(topic.getUserIcon());
		vo.setUserId(topic.getUserId());
		vo.setTitle(topic.getTitle());
		vo.setCreateTime(topic.getShowCreateTime());
		vo.setReadCount(topic.getReadCount());
		vo.setCommentCount(topic.getCommentCount());
		vo.setContent(topic.getContent());
		vo.setLickCount(topic.getLikeCount());
		vo.setCollectionCount(topic.getCollectionCount());
		result.put("topic", vo);
		return result;
	}

	@Override
	public void saveTopic4Api(Map<String, String> map, SessionUser sessionUser,
			HttpServletRequest request) throws BusinessException {
		String title = map.get("title");
		String content = map.get("content");
		String gid = map.get("gid");
		if (StringUtils.isEmpty(title)
				|| title.length() > LengthEnums.Length200.getLength()) {
			throw new BusinessException("标题不能为空,且不能长度超过200!");
		}
		if (StringUtils.isEmpty(content)) {
			throw new BusinessException("内容不能为空");
		}
		if (StringUtils.isEmpty(gid)) {
			throw new BusinessException("参数错误");
		}
		Group group = groupMapper.selectBaseInfo(map);
		if (null == group) {
			throw new BusinessException("窝窝不存在!");
		}

		int gid_int = Integer.parseInt(gid);
		Topic topic = new Topic();
		topic.setGid(gid_int);
		title = StringUtils.clearHtml(title);
		topic.setTitle(title);
		String summary = StringUtils.clearHtml(content);
		if (summary.length() > LengthEnums.Length200.getLength()) {
			summary = summary.substring(0, LengthEnums.Length100.getLength())
					+ "......";
		}
		topic.setSummary(summary);
		if (!StringUtils.isEmpty(map.get("imgUrl"))) {
			String[] images = map.get("imgUrl").split(",");
			for (String image : images) {
				content = "<div style='text-align:left;margin-top:5px;'><img src='"
						+ image + "'/></div>" + content;
			}
		}
		List<Integer> userIds = new ArrayList<Integer>();
		String formatContent = FormatAt.getInstance(Constant.TYPE_TALK)
				.GenerateRefererLinks(userMapper, content, userIds);
		topic.setContent(formatContent);
		topic.setUserId(sessionUser.getUserId());
		String curDate = StringUtils.dateFormater.format(new Date());
		topic.setCreateTime(curDate);
		topic.setLastCommentTime(curDate);
		topic.setTopicType(TopicTypeEnums.COMMON.getValue());
		String topicImage = StringUtils.getImages(content);
		topic.setTopicImage(topicImage);
		String topicImageSmall = ImageUtils.geImageSmall(topicImage, request,
				true);
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

		NoticeParam noticeParm = new NoticeParam();
		noticeParm.setArticleId(topic.getTopicId());
		noticeParm.setNoticeType(NoticeType.ATINTOPIC);
		noticeParm.setAtUserIds(userIds);
		noticeParm.setSendUserId(sessionUser.getUserId());
		NoticeThread noticeThread = new NoticeThread(noticeParm);
		Thread thread = new Thread(noticeThread);
		thread.start();
	}

	public Map<String, List<Topic>> getTopic4Index() {
		final int pCategroyId2 = 2; // 网事
		final int categroyId11 = 11; // IT
		final int categroyId13 = 13; // 社会热点
		final int pCategroyId1 = 1;// 编程
		final int pCategroyId5 = 5;// 生活
		final String catId = "84";// 公告
		Map<String, List<Topic>> resultMap = new HashMap<String, List<Topic>>();

		SimplePage page = new SimplePage(0, 5);

		List<Topic> imageTopics = topicMapper.selectImageTopic4Index(
				pCategroyId2, page);

		HashMap<String, String> param = new HashMap<String, String>();
		param.put("categoryId", catId);
		param.put("orderBy", "create_time desc");
		List<Topic> noticTopic = topicMapper.selectTopicList(param, page);

		page.setStart(0);
		page.setEnd(10);
		List<Topic> newsTopics4Life = topicMapper
				.selectTopic4IndexNoImageByCategroyId(categroyId13,
						imageTopics, page);

		List<Topic> newsTopics4IT = topicMapper
				.selectTopic4IndexNoImageByCategroyId(categroyId11,
						imageTopics, page);

		List<Topic> itTopics = topicMapper
				.selectTopic4Index(pCategroyId1, page);

		page.setStart(0);
		page.setEnd(1);
		List<Topic> imageTopics4Life = topicMapper.selectImageTopic4Index(
				pCategroyId5, page);

		page.setStart(0);
		page.setEnd(8);
		List<Topic> lifeTopics = topicMapper
				.selectTopic4IndexNoImageByPCategroyId(pCategroyId5,
						imageTopics4Life, page);

		resultMap.put("imageTopics", imageTopics);
		resultMap.put("newsTopics4Life", newsTopics4Life);
		resultMap.put("newsTopics4IT", newsTopics4IT);
		resultMap.put("itTopics", itTopics);
		resultMap.put("imageTopics4Life", imageTopics4Life);
		resultMap.put("lifeTopics", lifeTopics);
		resultMap.put("noticTopic", noticTopic);
		return resultMap;
	}
}
