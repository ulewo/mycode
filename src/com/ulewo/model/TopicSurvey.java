package com.ulewo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 调查
 * 
 * @author luo.hl
 * @date 2013-12-8 下午3:33:06
 * @version 3.0
 * @copyright www.ulewo.com
 */
public class TopicSurvey {
    private Integer gid;
    private Integer topicId;
    private String surveyType;
    private String endDate;
    private int sumCount;
    private List<TopicSurveyDtl> dtlList = new ArrayList<TopicSurveyDtl>();
    private List<TopicSurveyUser> userSurveys = new ArrayList<TopicSurveyUser>();
    private String canSurvy;

    public Integer getTopicId() {
	return topicId;
    }

    public void setTopicId(Integer topicId) {
	this.topicId = topicId;
    }

    public String getSurveyType() {
	return surveyType;
    }

    public void setSurveyType(String surveyType) {
	this.surveyType = surveyType;
    }

    public Integer getGid() {
	return gid;
    }

    public void setGid(Integer gid) {
	this.gid = gid;
    }

    public List<TopicSurveyDtl> getDtlList() {
	return dtlList;
    }

    public void setDtlList(List<TopicSurveyDtl> dtlList) {
	this.dtlList = dtlList;
    }

    public String getEndDate() {
	return endDate;
    }

    public void setEndDate(String endDate) {
	this.endDate = endDate;
    }

    public int getSumCount() {
	return sumCount;
    }

    public void setSumCount(int sumCount) {
	this.sumCount = sumCount;
    }

    public List<TopicSurveyUser> getUserSurveys() {
	return userSurveys;
    }

    public void setUserSurveys(List<TopicSurveyUser> userSurveys) {
	this.userSurveys = userSurveys;
    }

    public String getCanSurvy() {
	return canSurvy;
    }

    public void setCanSurvy(String canSurvy) {
	this.canSurvy = canSurvy;
    }

}
