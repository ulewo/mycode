package com.ulewo.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ulewo.AppException;

/**
 * @Title:
 * @Description: 回复贴
 * @author luohl
 * @date 2012-3-30
 * @version V1.0
 */
public class ReArticle implements Serializable {
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

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public Integer getPid() {

		return pid;
	}

	public void setPid(Integer pid) {

		this.pid = pid;
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

	public String getAuthorIcon() {

		return authorIcon;
	}

	public void setAuthorIcon(String authorIcon) {

		this.authorIcon = authorIcon;
	}

	public String getAuthorid() {

		return authorid;
	}

	public void setAuthorid(String authorid) {

		this.authorid = authorid;
	}

	public String getAuthorName() {

		return authorName;
	}

	public void setAuthorName(String authorName) {

		this.authorName = authorName;
	}

	public String getReTime() {

		return reTime;
	}

	public void setReTime(String reTime) {

		this.reTime = reTime;
	}

	public List<ReArticle> getChildList() {

		return childList;
	}

	public void setChildList(List<ReArticle> childList) {

		this.childList = childList;
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

	public static ReArticle parse(JSONObject obj) throws AppException {

		try {
			ReArticle reArticle = new ReArticle();
			reArticle.setId(obj.getInt("id"));
			reArticle.setPid(obj.getInt("pid"));
			reArticle.setReTime(obj.getString("reTime"));
			reArticle.setContent(obj.getString("content"));
			reArticle.setAuthorName(obj.getString("authorName"));
			reArticle.setArticleId(obj.getInt("articleId"));
			reArticle.setAtUserId(obj.getString("atUserId"));
			reArticle.setAtUserName(obj.getString("atUserName"));
			reArticle.setAuthorIcon(obj.getString("authorIcon"));
			reArticle.setAuthorid(obj.getString("authorid"));
			reArticle.setAuthorName(obj.getString("authorName"));
			ArrayList<ReArticle> childList = new ArrayList<ReArticle>();
			if (!"null".equals(obj.getString("childList"))) {
				JSONArray jsonArray = new JSONArray(obj.getString("childList"));
				int jsonLength = jsonArray.length();
				ReArticle subRe = null;
				for (int i = 0; i < jsonLength; i++) {
					JSONObject childObj = jsonArray.getJSONObject(i);
					subRe = ReArticle.parse(childObj);
					childList.add(subRe);
				}
			}
			reArticle.setChildList(childList);
			return reArticle;
		} catch (JSONException e) {
			throw AppException.josn(e);
		}
	}
}
