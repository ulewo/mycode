package com.lhl.entity;

/**
 * 
 * @Title:
 * @Description: 博客分类
 * @author luohl
 * @date 2013-1-4
 * @version V1.0
 */
public class BlogReply
{

	private int id;

	private int blogId;

	private String content;

	private String postTime;

	private String userId;

	private String userName;

	private String reUserIcon;

	public int getId()
	{

		return id;
	}

	public void setId(int id)
	{

		this.id = id;
	}

	public int getBlogId()
	{

		return blogId;
	}

	public void setBlogId(int blogId)
	{

		this.blogId = blogId;
	}

	public String getContent()
	{

		return content;
	}

	public void setContent(String content)
	{

		this.content = content;
	}

	public String getPostTime()
	{

		return postTime;
	}

	public void setPostTime(String postTime)
	{

		this.postTime = postTime;
	}

	public String getUserId()
	{

		return userId;
	}

	public void setUserId(String userId)
	{

		this.userId = userId;
	}

	public String getUserName()
	{

		return userName;
	}

	public void setUserName(String userName)
	{

		this.userName = userName;
	}

	public String getReUserIcon()
	{

		return reUserIcon;
	}

	public void setReUserIcon(String reUserIcon)
	{

		this.reUserIcon = reUserIcon;
	}

}
