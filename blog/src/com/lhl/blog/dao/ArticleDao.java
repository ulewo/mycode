package com.lhl.blog.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lhl.blog.entity.Article;
import com.lhl.blog.util.ConnManage;
import com.lhl.blog.util.StringUtil;

public class ArticleDao
{
	private ArticleDao()
	{

	}

	private static ArticleDao instance;

	/**
	 * 构造实例
	 * 
	 * @return
	 */
	public synchronized static ArticleDao getInstance()
	{

		if (instance == null)
		{
			instance = new ArticleDao();
		}
		return instance;
	}

	public void addArticle(Article article)
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		try
		{
			String sql = "insert into article (id,itemid,title,comment,content,readcount,posttime,tag) values(?,?,?,?,?,?,?,?)";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setString(1, article.getId());
			ps.setInt(2, article.getItemId());
			ps.setString(3, article.getTitle());
			ps.setString(4, article.getComment());
			ps.setString(5, article.getContent());
			ps.setInt(6, article.getReadCount());
			ps.setString(7, article.getPostTime());
			ps.setString(8, article.getTags());
			ps.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();

		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
				}
				conManager.releaseConn(con);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public void deleteArticle(String id)
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		try
		{
			String sql = "delete from article where id = ?";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
				}
				conManager.releaseConn(con);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public Article getArticle(String id)
	{

		Article article = null;
		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "select id,itemid,title,comment,content,readcount,posttime,(select count(r.id) from rearticle r where r.articleid = a.id) recount,tag from article a where id = ?";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if (rs.next())
			{
				article = new Article();
				article.setId(rs.getString("id"));
				article.setItemId(rs.getInt("itemid"));
				article.setTitle(rs.getString("title"));
				article.setComment(rs.getString("comment"));
				article.setContent(rs.getString("content"));
				article.setReadCount(rs.getInt("readcount"));
				article.setPostTime(rs.getString("posttime"));
				article.setReCount(rs.getInt("recount"));
				article.setTags(rs.getString("tag"));
				String tags = rs.getString("tag");
				if (StringUtil.isNotEmpty(tags))
				{
					String tag = tags.replace("，", ",");
					List<String> tagList = new ArrayList<String>();
					article.setTagList(tagList);
					if (StringUtil.isNotEmpty(tag))
					{
						String[] _tags = tag.split(",");
						for (int i = 0; i < _tags.length; i++)
						{
							tagList.add(_tags[i]);
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
				}

				if (ps != null)
				{
					ps.close();
				}
				conManager.releaseConn(con);
			}
			catch (Exception e)
			{
			}
		}
		return article;
	}

	public void updateArticle(Article article)
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		try
		{
			String sql = "update article set itemid = ?,title = ?,comment = ?,content = ?,tag = ? where id =?";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setInt(1, article.getItemId());
			ps.setString(2, article.getTitle());
			ps.setString(3, article.getComment());
			ps.setString(4, article.getContent());
			ps.setString(5, article.getTags());
			ps.setString(6, article.getId());
			ps.execute();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
				}
				conManager.releaseConn(con);
			}
			catch (Exception e)
			{
			}
		}
	}

	public void updateArticleReadCount(Article article)
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		try
		{
			String sql = "update article set readcount = ? where id =?";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setInt(1, article.getReadCount());
			ps.setString(2, article.getId());
			ps.execute();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
				}
				conManager.releaseConn(con);
			}
			catch (Exception e)
			{
			}
		}
	}

	public List<Article> queryList(int itemId, int noStart, int pageSize)
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Article> list = new ArrayList<Article>();
		try
		{
			StringBuffer sql = new StringBuffer(
					"select id,itemid,title,comment,readcount,posttime,(select count(r.id) from rearticle r where r.articleid = a.id) recount,tag from article a where type='A'");
			if (itemId != 0)
			{
				sql.append(" and itemid = ?");
			}
			sql.append("  order by posttime desc limit ?,?");
			con = conManager.getConn();
			ps = con.prepareStatement(String.valueOf(sql));
			if (itemId != 0)
			{
				ps.setInt(1, itemId);
				ps.setInt(2, noStart);
				ps.setInt(3, pageSize);
			}
			else
			{
				ps.setInt(1, noStart);
				ps.setInt(2, pageSize);
			}
			rs = ps.executeQuery();
			while (rs.next())
			{
				Article article = new Article();
				article.setId(rs.getString("id"));
				article.setItemId(rs.getInt("itemid"));
				article.setTitle(rs.getString("title"));
				article.setContent(rs.getString("comment"));
				article.setReadCount(rs.getInt("readcount"));
				article.setPostTime(rs.getString("posttime"));
				article.setReCount(rs.getInt("recount"));
				String tags = rs.getString("tag");
				if (StringUtil.isNotEmpty(tags))
				{
					String tag = tags.replace("，", ",");
					List<String> tagList = new ArrayList<String>();
					article.setTagList(tagList);
					if (StringUtil.isNotEmpty(tag))
					{
						String[] _tags = tag.split(",");
						for (int i = 0; i < _tags.length; i++)
						{
							tagList.add(_tags[i]);
						}
					}
				}
				list.add(article);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
				}

				if (ps != null)
				{
					ps.close();
				}
				conManager.releaseConn(con);
			}
			catch (Exception e)
			{
			}
		}
		return list;
	}

	public int queryCount(int itemId)
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		try
		{
			StringBuffer sql = new StringBuffer("select count(id) recount from article a where type='A'");
			if (itemId != 0)
			{
				sql.append(" and itemid = ?");
			}
			con = conManager.getConn();
			ps = con.prepareStatement(String.valueOf(sql));
			if (itemId != 0)
			{
				ps.setInt(1, itemId);
			}
			rs = ps.executeQuery();
			if (rs.next())
			{
				count = rs.getInt(1);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
				}

				if (ps != null)
				{
					ps.close();
				}
				conManager.releaseConn(con);
			}
			catch (Exception e)
			{
			}
		}
		return count;
	}

	public int queryCountByTime(String startTime, String endTime)
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		try
		{
			String sql = "select count(id) recount from article a where type='A' and posttime between ? and ?";
			con = conManager.getConn();
			ps = con.prepareStatement(String.valueOf(sql));
			ps.setString(1, startTime);
			ps.setString(2, endTime);
			rs = ps.executeQuery();
			if (rs.next())
			{
				count = rs.getInt(1);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
				}

				if (ps != null)
				{
					ps.close();
				}
				conManager.releaseConn(con);
			}
			catch (Exception e)
			{
			}
		}
		return count;
	}

	public List<Article> queryListByTime(String startTime, String endTime, int noStart, int pageSize)
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Article> list = new ArrayList<Article>();
		try
		{
			String sql = "select id,itemid,title,comment,readcount,posttime,(select count(r.id) from rearticle r where r.articleid = a.id) recount,tag from article a where type='A' and posttime between ? and ? order by posttime desc limit ?,?";
			con = conManager.getConn();
			ps = con.prepareStatement(String.valueOf(sql));
			ps.setString(1, startTime);
			ps.setString(2, endTime);
			ps.setInt(3, noStart);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next())
			{
				Article article = new Article();
				article.setId(rs.getString("id"));
				article.setItemId(rs.getInt("itemid"));
				article.setTitle(rs.getString("title"));
				article.setContent(rs.getString("comment"));
				article.setReadCount(rs.getInt("readcount"));
				article.setPostTime(rs.getString("posttime"));
				article.setReCount(rs.getInt("recount"));
				String tags = rs.getString("tag");
				if (StringUtil.isNotEmpty(tags))
				{
					String tag = tags.replace("，", ",");
					List<String> tagList = new ArrayList<String>();
					article.setTagList(tagList);
					if (StringUtil.isNotEmpty(tag))
					{
						String[] _tags = tag.split(",");
						for (int i = 0; i < _tags.length; i++)
						{
							tagList.add(_tags[i]);
						}
					}
				}
				list.add(article);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
				}

				if (ps != null)
				{
					ps.close();
				}
				conManager.releaseConn(con);
			}
			catch (Exception e)
			{
			}
		}
		return list;
	}

	public List<Map<String, String>> queryArticleTime()
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try
		{
			String sql = "select count(*) as acount,left(posttime,7) as time  from article where type='A' group by left(posttime,7) order by posttime desc";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				Map<String, String> map = new HashMap<String, String>();
				map.put("time", StringUtil.formateTime(rs.getString("time")));
				map.put("retime", rs.getString("time"));
				map.put("acount", rs.getString("acount"));
				list.add(map);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
				}

				if (ps != null)
				{
					ps.close();
				}
				conManager.releaseConn(con);
			}
			catch (Exception e)
			{
			}
		}
		return list;
	}
}
