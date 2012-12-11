package com.lhl.blog.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.lhl.blog.entity.Item;
import com.lhl.blog.util.ConnManage;

public class ItemDao
{
	private ItemDao()
	{

	}

	private static ItemDao instance;

	/**
	 * 构造实例
	 * 
	 * @return
	 */
	public synchronized static ItemDao getInstance()
	{

		if (instance == null)
		{
			instance = new ItemDao();
		}
		return instance;
	}

	public void addItem(Item item)
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		try
		{
			String sql = "insert into item (itemname,rang) values(?,?)";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setString(1, item.getItemName());
			ps.setInt(2, item.getRang());
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

	public void deleteItem(int id)
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		try
		{
			String sql = "delete from item where id = ?";
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

	public void updateItem(Item item)
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		try
		{
			String sql = "update item set itemname = ?,rang = ? where id =?";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setString(1, item.getItemName());
			ps.setInt(2, item.getRang());
			ps.setInt(3, item.getId());
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

	public List<Item> queryItems()
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Item> list = new ArrayList<Item>();
		try
		{
			String sql = "select id,itemname,rang,(select count(a.id) from article a where a.itemid = i.id and a.type='A') as articleCount from item i order by rang";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next())
			{
				Item item = new Item();
				item = new Item();
				item.setId(rs.getInt("id"));
				item.setItemName(rs.getString("itemname"));
				item.setRang(rs.getInt("rang"));
				item.setArticleCount(rs.getInt("articleCount"));
				list.add(item);
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
