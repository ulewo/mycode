package com.lhl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.lhl.entity.ReArticle;
import com.lhl.util.ConnManage;
import com.lhl.util.Constant;
import com.lhl.util.StringUtil;

public class ReArticleDao
{
	private ReArticleDao()
	{

	}

	private static ReArticleDao instance;

	public synchronized static ReArticleDao getInstance()
	{

		if (instance == null)
		{
			instance = new ReArticleDao();
		}
		return instance;
	}

	public List<ReArticle> queryListByArticleId(int articleId) throws Exception
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ReArticle> list = new ArrayList<ReArticle>();
		try
		{
			String sql = "select id,articleid,content,retime,u.uid,u.username,u.avatar from rearticle a left join user u on u.uid=a.uid where articleid=?";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setInt(1, articleId);
			rs = ps.executeQuery();
			while (rs.next())
			{
				ReArticle article = new ReArticle();
				article.setId(rs.getInt("id"));
				article.setArticleId(rs.getInt("articleid"));
				article.setContent(rs.getString("content"));
				article.setUid(rs.getString("uid"));
				if (StringUtil.isNotEmpty(rs.getString("uid")))
				{
					article.setUserName(rs.getString("username"));
					article.setAvatar(rs.getString("avatar"));
				}
				else
				{
					article.setUserName(Constant.DEFALT_NAME);
					article.setAvatar(Constant.DEFALT_AVATAR);
				}
				article.setReTime(rs.getString("retime"));
				list.add(article);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
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
				throw new RuntimeException(e);
			}
		}
		return list;
	}

	public List<ReArticle> queryList(int noStart, int pageSize) throws Exception
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ReArticle> list = new ArrayList<ReArticle>();
		try
		{
			String sql = "select id,articleid,content,retime,u.uid,u.username,u.avatar from rearticle a left join user u on u.uid=a.uid limit ?,?";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setInt(1, noStart);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next())
			{
				ReArticle article = new ReArticle();
				article.setId(rs.getInt("id"));
				article.setArticleId(rs.getInt("articleid"));
				article.setContent(rs.getString("content"));
				article.setUid(rs.getString("uid"));
				if (StringUtil.isNotEmpty(rs.getString("uid")))
				{
					article.setUserName(rs.getString("username"));
					article.setAvatar(rs.getString("avatar"));
				}
				else
				{
					article.setUserName(Constant.DEFALT_NAME);
					article.setAvatar(Constant.DEFALT_AVATAR);
				}
				article.setReTime(rs.getString("retime"));
				list.add(article);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
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
				throw new RuntimeException(e);
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
			throw new RuntimeException(e);
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
				throw new RuntimeException(e);
			}
		}
		return count;
	}

	public void addReArticle(ReArticle reArticle)
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "insert into rearticle (articleid,content,uid,username,avatar,retime) values(?,?,?,?,?,?)";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setInt(1, reArticle.getArticleId());
			ps.setString(2, reArticle.getContent());
			ps.setString(3, reArticle.getUid());
			ps.setString(4, reArticle.getUserName());
			ps.setString(5, reArticle.getAvatar());
			ps.setString(6, reArticle.getReTime());
			ps.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
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
				throw new RuntimeException(e);
			}
		}
	}
}
