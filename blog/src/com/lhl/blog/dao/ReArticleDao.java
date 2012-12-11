package com.lhl.blog.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.lhl.blog.entity.ReArticle;
import com.lhl.blog.util.ConnManage;

public class ReArticleDao
{
	private ReArticleDao()
	{

	}

	private static ReArticleDao instance;

	/**
	 * 构造实例
	 * 
	 * @return
	 */
	public synchronized static ReArticleDao getInstance()
	{

		if (instance == null)
		{
			instance = new ReArticleDao();
		}
		return instance;
	}

	public void addReArticle(ReArticle reArticle)
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		try
		{
			String sql = "insert into rearticle (articleId,username,content,postTime,type) values(?,?,?,?,?)";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);

			ps.setString(1, reArticle.getArticleId());
			ps.setString(2, reArticle.getUserName());
			ps.setString(3, reArticle.getContent());
			ps.setString(4, reArticle.getPostTime());
			ps.setString(5, reArticle.getType());
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

	public void deleteReArticle(int id)
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		try
		{
			String sql = "delete from rearticle where id = ?";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
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

	public List<ReArticle> queryReArticles(int noStart, int pageSize)
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ReArticle> list = new ArrayList<ReArticle>();
		try
		{
			String sql = "select id,articleid,username,content,posttime,type from rearticle  order by posttime desc limit ?,?";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setInt(1, noStart);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next())
			{
				ReArticle reArticle = new ReArticle();
				reArticle.setId(rs.getInt("id"));
				reArticle.setArticleId(rs.getString("articleId"));
				reArticle.setUserName(rs.getString("username"));
				reArticle.setContent(rs.getString("content"));
				reArticle.setPostTime(rs.getString("posttime"));
				reArticle.setType(rs.getString("type"));
				list.add(reArticle);
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

	public int queryCount()
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		try
		{
			String sql = "select count(id) from rearticle";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
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

	public List<ReArticle> queryReArticlesByArticleId(String articleId)
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ReArticle> list = new ArrayList<ReArticle>();
		try
		{
			String sql = "select id,articleid,username,content,posttime,type from rearticle where articleid = ? order by posttime desc";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setString(1, articleId);
			rs = ps.executeQuery();
			while (rs.next())
			{
				ReArticle reArticle = new ReArticle();
				reArticle.setId(rs.getInt("id"));
				reArticle.setArticleId(rs.getString("articleId"));
				reArticle.setUserName(rs.getString("username"));
				reArticle.setContent(rs.getString("content"));
				reArticle.setPostTime(rs.getString("posttime"));
				reArticle.setType(rs.getString("type"));
				list.add(reArticle);
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
