package com.ulewo.model;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import com.ulewo.util.StringUtils;

/**
 * 
 * @Title:
 * @Description: 主题文章bean
 * @author luohl
 * @date 2012-3-30
 * @version V1.0
 */
public class Topic {
	private Integer topicId; // id

	private Integer gid; // 组id

	private String topicType;// 类型 0:普通帖子 1:投票贴

	private Integer categoryId; // 栏目ID

	private String title; // 标题

	private String titleStyleId;// 标题样式

	private String content; // 内容

	private String keyWord; // 关键字

	private Integer userId; // 作者

	private String userName;

	private String userIcon;

	private String createTime; // 发布时间

	private Integer readCount; // 阅读次数

	private Integer commentCount; // 回复数量

	private Integer grade; // 帖子等级

	private String essence; // 精华

	private String valid; // 是否有效

	private String topicImage;// 保存所有的图片，图片之间用|隔开

	private String topicImageSmall; //缩略图

	// 需要关联查询的信息
	private String groupName; // 群组名称 （文章所述群）

	private String groupIcon;

	private String categoryName; // 栏目名称

	private String lastCommentTime; // 最后回复时间

	private String summary;

	private Attachment file;

	private boolean newPost;

	private String[] images;

	private String showCreateTime;

	public Integer getTopicId() {
		return topicId;
	}

	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public String getTopicType() {
		return topicType;
	}

	public void setTopicType(String topicType) {
		this.topicType = topicType;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleStyleId() {
		return titleStyleId;
	}

	public void setTitleStyleId(String titleStyleId) {
		this.titleStyleId = titleStyleId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
		this.showCreateTime = StringUtils.friendly_time(createTime);
	}

	public Integer getReadCount() {
		return readCount;
	}

	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getEssence() {
		return essence;
	}

	public void setEssence(String essence) {
		this.essence = essence;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public String getTopicImage() {
		return topicImage;
	}

	public void setTopicImage(String topicImage) {
		this.topicImage = topicImage;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupIcon() {
		return groupIcon;
	}

	public void setGroupIcon(String groupIcon) {
		this.groupIcon = groupIcon;
	}

	public String getCategoryName() {
		if (StringUtils.isEmpty(categoryName)) {
			return "全部文章";
		}
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getLastCommentTime() {
		return lastCommentTime;
	}

	public void setLastCommentTime(String lastCommentTime) {
		this.lastCommentTime = lastCommentTime;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Attachment getFile() {
		return file;
	}

	public void setFile(Attachment file) {
		this.file = file;
	}

	public boolean isNewPost() {
		Date cur = null;
		Calendar c = Calendar.getInstance();
		try {
			cur = StringUtils.dateFormater.get().parse(this.createTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long max = c.getTimeInMillis() - cur.getTime();
		if (max / 86400000 <= 1) {
			newPost = true;
		}
		return newPost;
	}

	public void setNewPost(boolean newPost) {

		this.newPost = newPost;
	}

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}

	public String getShowCreateTime() {
		return showCreateTime;
	}

	public void setShowCreateTime(String showCreateTime) {
		this.showCreateTime = showCreateTime;
	}

	public String getTopicImageSmall() {
		return topicImageSmall;
	}

	public void setTopicImageSmall(String topicImageSmall) {
		this.topicImageSmall = topicImageSmall;
		if (StringUtils.isNotEmpty(topicImageSmall)) {
			this.images = this.topicImageSmall.split("\\|");
		}
	}

}
