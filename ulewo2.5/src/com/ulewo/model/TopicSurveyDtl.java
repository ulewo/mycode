package com.ulewo.model;

/**
 * 调查详情
 * 
 * @author luo.hl
 * @date 2013-12-8 下午3:34:02
 * @version 3.0
 * @copyright www.ulewo.com
 */
public class TopicSurveyDtl {
    private Integer id;
    private Integer topicId;
    private String title;
    private Integer count;

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public Integer getTopicId() {
	return topicId;
    }

    public void setTopicId(Integer topicId) {
	this.topicId = topicId;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public Integer getCount() {
	return count;
    }

    public void setCount(Integer count) {
	this.count = count;
    }

}
