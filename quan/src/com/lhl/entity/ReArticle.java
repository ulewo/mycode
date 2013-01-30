package com.lhl.entity;

import java.util.List;

/**
 * @Title:
 * @Description: 回复贴
 * @author luohl
 * @date 2012-3-30
 * @version V1.0
 */
public class ReArticle
{
	private int id;

	private int articleId; // 主题帖Id

	private String quote; //引用

	private String content; // 回复内容

	private String gid;

	private String authorIcon;//回复者图像

	private String authorid; // 回复作者

	private String authorName; // 名称

	private User author; // 回复人

	private String reTime; // 回复时间

	List<ReArticle> reArticles; // 回复的回复列表

	public int getId()
	{

		return id;
	}

	public void setId(int id)
	{

		this.id = id;
	}

	public int getArticleId()
	{

		return articleId;
	}

	public void setArticleId(int articleId)
	{

		this.articleId = articleId;
	}

	public String getContent()
	{

		return content;
	}

	public void setContent(String content)
	{

		this.content = content;
	}

	public String getAuthorid()
	{

		return authorid;
	}

	public void setAuthorid(String authorid)
	{

		this.authorid = authorid;
	}

	public String getReTime()
	{

		return reTime;
	}

	public void setReTime(String reTime)
	{

		this.reTime = reTime;
	}

	public String getAuthorName()
	{

		return authorName;
	}

	public void setAuthorName(String authorName)
	{

		this.authorName = authorName;
	}

	public List<ReArticle> getReArticles()
	{

		return reArticles;
	}

	public void setReArticles(List<ReArticle> reArticles)
	{

		this.reArticles = reArticles;
	}

	public User getAuthor()
	{

		return author;
	}

	public void setAuthor(User author)
	{

		this.author = author;
	}

	public String getGid()
	{

		return gid;
	}

	public void setGid(String gid)
	{

		this.gid = gid;
	}

	public String getAuthorIcon()
	{

		return authorIcon;
	}

	public void setAuthorIcon(String authorIcon)
	{

		this.authorIcon = authorIcon;
	}

	public String getQuote()
	{

		return quote;
	}

	public void setQuote(String quote)
	{

		this.quote = quote;
	}

}
