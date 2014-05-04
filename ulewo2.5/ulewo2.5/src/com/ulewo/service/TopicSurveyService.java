package com.ulewo.service;

import java.util.Map;

import com.ulewo.exception.BusinessException;
import com.ulewo.model.SessionUser;
import com.ulewo.model.TopicSurvey;

public interface TopicSurveyService {
    public TopicSurvey findTopicSurveyById(Map<String, String> map,
	    SessionUser sessionUser) throws BusinessException;

    public void SurveyDo(Map<String, String> map, String[] ids,
	    SessionUser sessionUser) throws BusinessException;

}
