package com.lhl.blog.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.lhl.blog.entity.Note;
import com.lhl.blog.util.ConnManage;

public class NoteDao
{
	private NoteDao()
	{

	}

	private static NoteDao instance;

	/**
	 * 构造实例
	 * 
	 * @return
	 */
	public synchronized static NoteDao getInstance()
	{

		if (instance == null)
		{
			instance = new NoteDao();
		}
		return instance;
	}

	public void addNote(Note note)
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		try
		{
			String sql = "insert into note (username,content,postTime,type) values(?,?,?,?)";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setString(1, note.getUserName());
			ps.setString(2, note.getContent());
			ps.setString(3, note.getPostTime());
			ps.setString(4, note.getType());
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

	public void deleteNote(int id)
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		try
		{
			String sql = "delete from note where id = ?";
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

	public List<Note> queryNotes(int noStart, int pageSize)
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Note> list = new ArrayList<Note>();
		try
		{
			String sql = "select id,username,content,posttime,type from note order by posttime desc limit ?,?";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setInt(1, noStart);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next())
			{
				Note note = new Note();
				note.setId(rs.getInt("id"));
				note.setUserName(rs.getString("username"));
				note.setContent(rs.getString("content"));
				note.setPostTime(rs.getString("posttime"));
				note.setType(rs.getString("type"));
				list.add(note);
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

	public int queryNoteCount()
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		try
		{
			String sql = "select count(id) from note ";
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
}
