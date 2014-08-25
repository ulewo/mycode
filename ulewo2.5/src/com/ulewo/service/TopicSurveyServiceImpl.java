package com.ulewo.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ulewo.enums.SurveyTypeEnums;
import com.ulewo.enums.YNTypeEnums;
import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.TopicSurveyDtlMapper;
import com.ulewo.mapper.TopicSurveyMapper;
import com.ulewo.mapper.TopicSurveyUserMapper;
import com.ulewo.model.SessionUser;
import com.ulewo.model.TopicSurvey;
import com.ulewo.model.TopicSurveyDtl;
import com.ulewo.model.TopicSurveyUser;
import com.ulewo.util.StringUtils;

@Service("topicSurveyService")
public class TopicSurveyServiceImpl implements TopicSurveyService {
	@Resource
	private TopicSurveyMapper<TopicSurvey> topicSurveyMapper;

	@Resource
	private TopicSurveyUserMapper<TopicSurveyUser> topicSurveyUserMapper;

	@Resource
	private TopicSurveyDtlMapper<TopicSurveyDtl> topicSurveyDtlMapper;

	@Override
	public TopicSurvey findTopicSurveyById(Map<String, String> map,
			SessionUser sessionUser) throws BusinessException {
		TopicSurvey survey = topicSurveyMapper.selectBaseInfo(map);
		if (survey == null) {
			throw new BusinessException("文章调查没找到");
		}
		List<TopicSurveyDtl> dtlList = topicSurveyDtlMapper
				.selectTopicSurveyDtls(map);
		if (dtlList == null || dtlList.size() <= 1) {
			throw new BusinessException("文章调查明细没找到");
		}
		survey.setDtlList(dtlList);
		// 查询用户是否已经投票
		if (sessionUser != null) {
			map.put("userId", String.valueOf(sessionUser.getUserId()));
			survey.setUserSurveys(topicSurveyUserMapper.selectSurveys(map));
		}
		// 投票是否结束
		if (StringUtils.beforeNowDate(survey.getEndDate())) {
			// 投票结束日期在当天之前，那么没有结束投票
			survey.setCanSurvy(YNTypeEnums.TYPEN.getValue());
		} else {
			survey.setCanSurvy(YNTypeEnums.TYPEY.getValue());
		}
		return survey;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void SurveyDo(Map<String, String> map, String[] ids,
			SessionUser sessionUser) throws BusinessException {
		if (ids == null) {
			throw new BusinessException("参数错误");
		}
		// 查询当前调查类型
		TopicSurvey survey = topicSurveyMapper.selectBaseInfo(map);
		if (StringUtils.beforeNowDate(survey.getEndDate())) {
			throw new BusinessException("当前投票已经结束");
		}
		if (SurveyTypeEnums.SINGLE.getValue().equals(survey.getSurveyType())
				&& ids.length > 1) {
			throw new BusinessException("参数错误");
		}
		for (int i = 0, length = ids.length; i < length; i++) {
			map.put("id", ids[i]);
			TopicSurveyDtl dtl = topicSurveyDtlMapper.selectBaseInfo(map);
			if (dtl == null) {
				throw new BusinessException("明细不存在");
			}
			dtl.setCount(dtl.getCount() + 1);
			topicSurveyDtlMapper.updateSelective(dtl);
			// 记录投票人
			TopicSurveyUser user = new TopicSurveyUser();
			user.setSurveyDtlId(Integer.parseInt(ids[i]));
			user.setUserId(sessionUser.getUserId());
			user.setSurveyDate(StringUtils.dateFormater.format(new Date()));
			topicSurveyUserMapper.insert(user);
		}
	}
}
