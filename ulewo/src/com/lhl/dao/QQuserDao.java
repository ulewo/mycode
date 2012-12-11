package com.lhl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.lhl.entity.User;
import com.lhl.util.ConnManage;

public class QQuserDao
{
	private QQuserDao()
	{

	}

	private static QQuserDao instance;

	public synchronized static QQuserDao getInstance()
	{

		if (instance == null)
		{
			instance = new QQuserDao();
		}
		return instance;
	}

	public List<String> queryQQUserList() throws Exception
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		try
		{
			String sql = "select qq from qqnumber";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				list.add(rs.getString("qq"));
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

	public User queryUserByUid(String uid)
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		try
		{
			String sql = "select uid,username,password,avatar,email from user where uid = ?";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setString(1, uid);
			rs = ps.executeQuery();
			if (rs.next())
			{
				user = new User();
				user.setUid(rs.getString("uid"));
				user.setUserName(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setAvatar(rs.getString("avatar"));
				user.setEmail(rs.getString("email"));
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
		return user;
	}

	public User queryUserByName(String userName)
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		try
		{
			String sql = "select uid,username,password,avatar,email from user where username = ?";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setString(1, userName);
			rs = ps.executeQuery();
			if (rs.next())
			{
				user = new User();
				user.setUid(rs.getString("uid"));
				user.setUserName(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setAvatar(rs.getString("avatar"));
				user.setEmail(rs.getString("email"));
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
		return user;
	}

	public void deleteUser(String uid)
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		try
		{
			String sql = "delete from user where uid= ?";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setString(1, uid);
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

	public void addUser(User user)
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "insert into user (uid,username,password,avatar,email) values(?,?,?,?,?)";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setString(1, user.getUid());
			ps.setString(2, user.getUserName());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getAvatar());
			ps.setString(5, user.getEmail());
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

	public String getMaxUid()
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String uid = "";
		try
		{
			String sql = "select max(uid) maxuid from user";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				uid = rs.getInt("maxuid") + "";
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
		return uid;
	}

	public void updateAvatar(String uid, String avatar)
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		try
		{
			String sql = "update user set avatar = ? where uid = ?";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setString(1, avatar);
			ps.setString(2, uid);
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
