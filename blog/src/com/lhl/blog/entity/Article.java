package com.lhl.blog.entity;

import java.util.ArrayList;
import java.util.List;

public class Article
{
	private String id;

	private int itemId;

	private String title;

	private String comment;

	private String content;

	private int readCount;

	private String postTime;

	private int reCount;

	private String tags;

	private List<String> tagList = new ArrayList<String>();

	private String type; //A:文章  B 作者简介

	public String getId()
	{

		return id;
	}

	public void setId(String id)
	{

		this.id = id;
	}

	public int getItemId()
	{

		return itemId;
	}

	public void setItemId(int itemId)
	{

		this.itemId = itemId;
	}

	public String getTitle()
	{

		return title;
	}

	public void setTitle(String title)
	{

		this.title = title;
	}

	public String getComment()
	{

		return comment;
	}

	public void setComment(String comment)
	{

		this.comment = comment;
	}

	public String getContent()
	{

		return content;
	}

	public void setContent(String content)
	{

		this.content = content;
	}

	public int getReadCount()
	{

		return readCount;
	}

	public void setReadCount(int readCount)
	{

		this.readCount = readCount;
	}

	public String getPostTime()
	{

		return postTime;
	}

	public void setPostTime(String postTime)
	{

		this.postTime = postTime;
	}

	public int getReCount()
	{

		return reCount;
	}

	public void setReCount(int reCount)
	{

		this.reCount = reCount;
	}

	public String getTags()
	{

		return tags;
	}

	public void setTags(String tags)
	{

		this.tags = tags;
	}

	public List<String> getTagList()
	{

		return tagList;
	}

	public void setTagList(List<String> tagList)
	{

		this.tagList = tagList;
	}

	public String getType()
	{

		return type;
	}

	public void setType(String type)
	{

		this.type = type;
	}

}
