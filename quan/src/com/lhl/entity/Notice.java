package com.lhl.entity;

public class Notice
{
	private int id;

	private String userId;

	private String type;

	private int status;

	private String url;

	private String content;

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

	public String getType()
	{

		return type;
	}

	public void setType(String type)
	{

		this.type = type;
	}

	public int getStatus()
	{

		return status;
	}

	public void setStatus(int status)
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

}
