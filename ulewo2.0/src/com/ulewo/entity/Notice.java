package com.ulewo.entity;

public class Notice
{
	private int id;

	private String userId;

	private int type;

	private String status;

	private String url;

	private String content;

	private String postTime;

	public int getId()
	{

		return id;
	}

	public void setId(int id)
	{

		this.id = id;
	}

	public String getUserId()
	{

		return userId;
	}

	public void setUserId(String userId)
	{

		this.userId = userId;
	}

	public int getType()
	{

		return type;
	}

	public void setType(int type)
	{

		this.type = type;
	}

	public String getStatus()
	{

		return status;
	}

	public void setStatus(String status)
	{

		this.status = status;
	}

	public String getUrl()
	{

		return url;
	}

	public void setUrl(String url)
	{

		this.url = url;
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
