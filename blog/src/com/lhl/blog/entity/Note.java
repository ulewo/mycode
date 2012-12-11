package com.lhl.blog.entity;

public class Note
{
	private int id;

	private String userName;

	private String content;

	private String postTime;

	private String type;

	public String getType()
	{

		return type;
	}

	public void setType(String type)
	{

		this.type = type;
	}

	public int getId()
	{

		return id;
	}

	public void setId(int id)
	{

		this.id = id;
	}

	public String getUserName()
	{

		return userName;
	}

	public void setUserName(String userName)
	{

		this.userName = userName;
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

}
