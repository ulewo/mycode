/* 
 * Copyright (C), 2004-2010, 三五互联科技股份有限公司
 * File Name: com.lhl.entity.Poll.java
 * Encoding UTF-8 
 * Version: 1.0 
 * Date: 2012-3-29
 * History:
 * 1. Date: 2012-3-29
 *    Author: luohl
 *    Modification: 新建
 * 2. ...
 */
package com.lhl.entity;

/** 
 * @Title:
 * @Description: 调查帖子
 * @author luohl
 * @date 2012-3-29
 * @version V1.0
*/
public class Poll
{
	private int id;

	private int pid;

	private int articleId;

	private String pollway;

	private String endTime;

	private String title;

	private int number;

	public int getId()
	{

		return id;
	}

	public void setId(int id)
	{

		this.id = id;
	}

	public int getPid()
	{

		return pid;
	}

	public void setPid(int pid)
	{

		this.pid = pid;
	}

	public int getArticleId()
	{

		return articleId;
	}

	public void setArticleId(int articleId)
	{

		this.articleId = articleId;
	}

	public String getPollway()
	{

		return pollway;
	}

	public void setPollway(String pollway)
	{

		this.pollway = pollway;
	}

	public String getEndTime()
	{

		return endTime;
	}

	public void setEndTime(String endTime)
	{

		this.endTime = endTime;
	}

	public String getTitle()
	{

		return title;
	}

	public void setTitle(String title)
	{

		this.title = title;
	}

	public int getNumber()
	{

		return number;
	}

	public void setNumber(int number)
	{

		this.number = number;
	}

}
