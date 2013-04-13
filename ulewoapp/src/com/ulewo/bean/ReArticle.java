package com.ulewo.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @Title:
 * @Description: 回复贴
 * @author luohl
 * @date 2012-3-30
 * @version V1.0
 */
public class ReArticle {
	private int id;

	private Integer pid;

	private int articleId; // 主题帖Id

	private String content; // 回复内容

	private String authorIcon;// 回复者图像

	private String authorid; // 回复作者

	private String authorName; // 名称

	private String reTime; // 回复时间

	List<ReArticle> childList; // 回复的回复列表

	private String atUserName;

	private String atUserId;

	public ReArticle(JSONObject json) {
		try {
			constructJson(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void constructJson(JSONObject json) throws Exception {
		id = json.getInt("id");
		pid = json.getInt("pid");
		articleId = json.getInt("articleId");
		reTime = json.getString("reTime") == null ? "" : json.getString(
				"reTime").substring(0, 16);
		content = json.getString("content");

		authorIcon = json.getString("authorIcon");
		authorName = json.getString("authorName");
		authorid = json.getString("authorid");

		atUserName = json.getString("atUserName");
		atUserId = json.getString("atUserId");
		JSONArray jsonArray = new JSONArray(String.valueOf(json
				.get("childList")));
		childList = new ArrayList<ReArticle>();
		int jsonLength = jsonArray.length();
		ReArticle reArticle = null;
		for (int i = 0; i < jsonLength; i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			reArticle = new ReArticle(obj);
			childList.add(reArticle);
		}
	}

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public int getArticleId() {

		return articleId;
	}

	public void setArticleId(int articleId) {

		this.articleId = articleId;
	}

	public String getContent() {

		return content;
	}

	public void setContent(String content) {

		this.content = content;
	}

	public String getAuthorid() {

		return authorid;
	}

	public void setAuthorid(String authorid) {

		this.authorid = authorid;
	}

	public String getReTime() {

		return reTime;
	}

	public void setReTime(String reTime) {

		this.reTime = reTime;
	}

	public String getAuthorName() {

		return authorName;
	}

	public void setAuthorName(String authorName) {

		this.authorName = authorName;
	}

	public List<ReArticle> getChildList() {
		return childList;
	}

	public void setChildList(List<ReArticle> childList) {
		this.childList = childList;
	}

	public String getAuthorIcon() {

		return authorIcon;
	}

	public void setAuthorIcon(String authorIcon) {

		this.authorIcon = authorIcon;
	}

	public String getAtUserName() {
		return atUserName;
	}

	public void setAtUserName(String atUserName) {
		this.atUserName = atUserName;
	}

	public String getAtUserId() {
		return atUserId;
	}

	public void setAtUserId(String atUserId) {
		this.atUserId = atUserId;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

}
