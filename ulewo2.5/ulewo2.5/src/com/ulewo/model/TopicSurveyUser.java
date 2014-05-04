package com.ulewo.model;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2013-12-15 下午8:09:37
 * @version 3.0
 * @copyright www.ulewo.com
 */
public class TopicSurveyUser {
    private Integer surveyDtlId;
    private String title;
    private Integer userId;
    private String surveyDate;

    public Integer getSurveyDtlId() {
	return surveyDtlId;
    }

    public void setSurveyDtlId(Integer surveyDtlId) {
	this.surveyDtlId = surveyDtlId;
    }

    public Integer getUserId() {
	return userId;
    }

    public void setUserId(Integer userId) {
	this.userId = userId;
    }

    public String getSurveyDate() {
	return surveyDate;
    }

    public void setSurveyDate(String surveyDate) {
	this.surveyDate = surveyDate;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

}
