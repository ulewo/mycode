package com.lhl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lhl.entity.Article;
import com.lhl.util.ConnManage;
import com.lhl.util.Constant;
import com.lhl.util.StringUtil;

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

	// 按照顶的倒序排列
	public List<Article> queryListByUp(int noStart, int pageSize, String beforTime, String curTime) throws Exception
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Article> list = new ArrayList<Article>();
		try
		{
			String sql = "select id,content,img_url,video_url,medio_type,tag,up,down,post_time,u.avatar,u.username,u.uid,(select count(r.id) from "
					+ "rearticle r where r.articleid = a.id) recount from article a left join user u on u.uid=a.uid where status = 'Y' and post_time between ? and ? order by up desc limit ?,?";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setString(1, beforTime);
			ps.setString(2, curTime);
			ps.setInt(3, noStart);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next())
			{
				Article article = new Article();
				article.setId(rs.getInt("id"));
				article.setContent(rs.getString("content"));
				article.setImgUrl(rs.getString("img_url"));
				article.setVideoUrl(rs.getString("video_url"));
				article.setMedioType(rs.getString("medio_type"));
				article.setAvatar(rs.getString("avatar"));
				article.setUid(rs.getString("uid"));
				article.setUserName(rs.getString("username"));
				article.setUp(rs.getInt("up"));
				article.setDown(rs.getInt("down"));
				article.setReCount(rs.getInt("recount"));
				article.setPostTime(rs.getString("post_time"));
				String tags = rs.getString("tag");
				if (StringUtil.isNotEmpty(tags))
				{
					String tag = tags.replace("，", ",");
					List<String> tagList = new ArrayList<String>();
					article.setTags(tagList);
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

	// 根究发布时间倒序排
	public List<Article> queryListByPostTime(int noStart, int pageSize, String beforTime, String curTime)
			throws Exception
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Article> list = new ArrayList<Article>();
		try
		{
			String sql = "select id,content,img_url,video_url,medio_type,tag,up,down,post_time,u.avatar,u.username,u.uid,(select count(r.id) from "
					+ "rearticle r where r.articleid = a.id) recount from article a left join user u on u.uid=a.uid where status = 'Y' and post_time between ? and ? order by post_time desc limit ?,?";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setString(1, beforTime);
			ps.setString(2, curTime);
			ps.setInt(3, noStart);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next())
			{
				Article article = new Article();
				article.setId(rs.getInt("id"));
				article.setContent(rs.getString("content"));
				article.setImgUrl(rs.getString("img_url"));
				article.setVideoUrl(rs.getString("video_url"));
				article.setMedioType(rs.getString("medio_type"));
				article.setAvatar(rs.getString("avatar"));
				article.setUid(rs.getString("uid"));
				article.setUserName(rs.getString("username"));
				article.setUp(rs.getInt("up"));
				article.setDown(rs.getInt("down"));
				article.setReCount(rs.getInt("recount"));
				article.setPostTime(rs.getString("post_time"));
				String tags = rs.getString("tag");
				if (StringUtil.isNotEmpty(tags))
				{
					String tag = tags.replace("，", ",");
					List<String> tagList = new ArrayList<String>();
					article.setTags(tagList);
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

	// 根据评论倒序排
	public List<Article> queryListByReCount(int noStart, int pageSize, String beforTime, String curTime)
			throws Exception
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Article> list = new ArrayList<Article>();
		try
		{
			String sql = "select id,content,img_url,video_url,medio_type,tag,up,down,post_time,u.avatar,u.username,u.uid,(select count(r.id) from "
					+ "rearticle r where r.articleid = a.id) recount from article a left join user u on u.uid=a.uid where status = 'Y' and post_time between ? and ? order by recount desc limit ?,?";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setString(1, beforTime);
			ps.setString(2, curTime);
			ps.setInt(3, noStart);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next())
			{
				Article article = new Article();
				article.setId(rs.getInt("id"));
				article.setContent(rs.getString("content"));
				article.setImgUrl(rs.getString("img_url"));
				article.setVideoUrl(rs.getString("video_url"));
				article.setMedioType(rs.getString("medio_type"));
				article.setAvatar(rs.getString("avatar"));
				article.setUid(rs.getString("uid"));
				article.setUserName(rs.getString("username"));
				article.setUp(rs.getInt("up"));
				article.setDown(rs.getInt("down"));
				article.setReCount(rs.getInt("recount"));
				article.setPostTime(rs.getString("post_time"));
				String tags = rs.getString("tag");
				if (StringUtil.isNotEmpty(tags))
				{
					String tag = tags.replace("，", ",");
					List<String> tagList = new ArrayList<String>();
					article.setTags(tagList);
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

	// 查询有图片的。
	public List<Article> queryListByPic(int noStart, int pageSize, String beforTime, String curTime) throws Exception
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Article> list = new ArrayList<Article>();
		try
		{
			String sql = "select id,content,img_url,video_url,medio_type,tag,up,down,post_time,u.avatar,u.username,u.uid,(select count(r.id) from "
					+ "rearticle r where r.articleid = a.id) recount from article a left join user u on u.uid=a.uid where img_url is not null and status = 'Y' and post_time between ? and ? order by post_time desc limit ?,?";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setString(1, beforTime);
			ps.setString(2, curTime);
			ps.setInt(3, noStart);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next())
			{
				Article article = new Article();
				article.setId(rs.getInt("id"));
				article.setContent(rs.getString("content"));
				article.setImgUrl(rs.getString("img_url"));
				article.setVideoUrl(rs.getString("video_url"));
				article.setMedioType(rs.getString("medio_type"));
				article.setAvatar(rs.getString("avatar"));
				article.setUid(rs.getString("uid"));
				article.setUserName(rs.getString("username"));
				article.setUp(rs.getInt("up"));
				article.setDown(rs.getInt("down"));
				article.setReCount(rs.getInt("recount"));
				article.setPostTime(rs.getString("post_time"));
				String tags = rs.getString("tag");
				if (StringUtil.isNotEmpty(tags))
				{
					String tag = tags.replace("，", ",");
					List<String> tagList = new ArrayList<String>();
					article.setTags(tagList);
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

	public void updateDownOrUp(int id, String type) throws Exception
	{

		ConnManage connManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count_d = 0;
		int count_u = 0;
		try
		{
			String sql = "select up,down from article where id=?";
			con = connManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next())
			{
				count_u = rs.getInt("up");
				count_d = rs.getInt("down");
				if (Constant.TYPE_UP.equals(type))
				{// count =
					count_u = count_u + 1;
				}
				else if (Constant.TYPE_DOWN.equals(type))
				{
					count_d = count_d + 1;
				}
			}
			sql = "update article set up=?,down = ? where id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, count_u);
			ps.setInt(2, count_d);
			ps.setInt(3, id);
			ps.executeUpdate();
		}
		catch (Exception e)
		{
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
				connManager.releaseConn(con);
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}
	}

	public void addArticle(Article article) throws Exception
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "insert into article (content,source_from,source_time,uid,username,status,img_url,video_url,up,down,avatar,tag,source_id,post_time,medio_type) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			String content = article.getContent();
			content = content.replace("<", "&lt;");
			content = content.replace(" ", "&nbsp;");
			content = content.replace("\n", "<br>");
			ps.setString(1, content);
			ps.setString(2, article.getSourceFrom());
			ps.setString(3, article.getSourceTime());
			ps.setString(4, article.getUid());
			ps.setString(5, article.getUserName());
			ps.setString(6, article.getStatus());
			ps.setString(7, article.getImgUrl());
			ps.setString(8, article.getVideoUrl());
			ps.setInt(9, article.getUp());
			ps.setInt(10, article.getDown());
			ps.setString(11, article.getAvatar());
			ps.setString(12, article.getTag());
			ps.setString(13, article.getSourceId());
			ps.setString(14, article.getPostTime());
			ps.setString(15, article.getMedioType());
			ps.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
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

	public List<Article> queryArticleByUserId(int noStart, int pageSize, String uid)
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Article> list = new ArrayList<Article>();
		try
		{
			String sql = "select id,content,img_url,video_url,medio_type,tag,up,down,post_time,u.avatar,u.username,u.uid,(select count(r.id) from "
					+ "rearticle r where r.articleid = a.id) recount from article a left join user u on u.uid=a.uid where a.uid = ? order by post_time desc limit ?,?";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setString(1, uid);
			ps.setInt(2, noStart);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next())
			{
				Article article = new Article();
				article.setId(rs.getInt("id"));
				article.setContent(rs.getString("content"));
				article.setImgUrl(rs.getString("img_url"));
				article.setPostTime(rs.getString("post_time"));
				article.setVideoUrl(rs.getString("video_url"));
				article.setMedioType(rs.getString("medio_type"));
				article.setAvatar(rs.getString("avatar"));
				article.setUid(rs.getString("uid"));
				article.setUserName(rs.getString("username"));
				article.setUp(rs.getInt("up"));
				article.setDown(rs.getInt("down"));
				article.setReCount(rs.getInt("recount"));
				String tag = rs.getString("tag").replace("，", ",");
				List<String> tagList = new ArrayList<String>();
				article.setTags(tagList);
				if (StringUtil.isNotEmpty(tag))
				{
					String[] tags = tag.split(",");
					for (int i = 0; i < tags.length; i++)
					{
						tagList.add(tags[i]);
					}
				}
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

	public int queryCountByUserId(String uid)
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		try
		{
			String sql = "select count(id) from article a left join user u on u.uid=a.uid where a.uid = ?";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setString(1, uid);
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

	public List<Article> queryArticleByReUserId(int noStart, int pageSize, String uid)
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Article> list = new ArrayList<Article>();
		try
		{
			String sql = "select distinct(a.id),a.content,a.img_url,video_url,medio_type,a.tag,up,a.down,a.post_time,u.avatar,u.username,u.uid,(select count(r.id) from "
					+ "rearticle r where r.articleid = a.id) recount from article a left join user u on u.uid=a.uid left join rearticle r on a.id=r.articleid where r.uid = ? order by post_time desc limit ?,?";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setString(1, uid);
			ps.setInt(2, noStart);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next())
			{
				Article article = new Article();
				article.setId(rs.getInt("id"));
				article.setContent(rs.getString("content"));
				article.setImgUrl(rs.getString("img_url"));
				article.setPostTime(rs.getString("post_time"));
				article.setVideoUrl(rs.getString("video_url"));
				article.setMedioType(rs.getString("medio_type"));
				article.setAvatar(rs.getString("avatar"));
				article.setUid(rs.getString("uid"));
				article.setUserName(rs.getString("username"));
				article.setUp(rs.getInt("up"));
				article.setReCount(rs.getInt("recount"));
				article.setDown(rs.getInt("down"));
				String tag = rs.getString("tag").replace("，", ",");
				List<String> tagList = new ArrayList<String>();
				article.setTags(tagList);
				if (StringUtil.isNotEmpty(tag))
				{
					String[] tags = tag.split(",");
					for (int i = 0; i < tags.length; i++)
					{
						tagList.add(tags[i]);
					}
				}
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

	public int queryCountByReUserId(String uid)
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		try
		{
			String sql = "select count(distinct(a.id)) from article a left join user u on u.uid=a.uid left join rearticle r on a.id=r.articleid where r.uid = ?";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setString(1, uid);
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

	// 详情
	public Article queryArticleById(int id) throws Exception
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Article article = null;
		try
		{
			String sql = "select id,content,img_url,video_url,medio_type,tag,up,down,post_time,u.avatar,u.username,u.uid,(select count(r.id) from "
					+ "rearticle r where r.articleid = a.id) recount from article a left join user u on u.uid=a.uid where id = ?";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next())
			{
				article = new Article();
				article.setId(rs.getInt("id"));
				article.setContent(rs.getString("content"));
				article.setImgUrl(rs.getString("img_url"));
				article.setVideoUrl(rs.getString("video_url"));
				article.setMedioType(rs.getString("medio_type"));
				article.setAvatar(rs.getString("avatar"));
				article.setUid(rs.getString("uid"));
				article.setUserName(rs.getString("username"));
				article.setUp(rs.getInt("up"));
				article.setDown(rs.getInt("down"));
				article.setReCount(rs.getInt("recount"));
				article.setPostTime(rs.getString("post_time"));
				String tags = rs.getString("tag");
				if (StringUtil.isNotEmpty(tags))
				{
					String tag = tags.replace("，", ",");
					List<String> tagList = new ArrayList<String>();
					article.setTags(tagList);
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
		return article;
	}

	public void updateStatusBatch()
	{

		ConnManage connManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count_d = 0;
		int count_u = 0;
		try
		{
			String sql = "update article set up=?,down = ? where id = ?";
			con = connManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setInt(1, count_u);
			ps.setInt(2, count_d);

			ps.executeBatch();
		}
		catch (Exception e)
		{
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
				connManager.releaseConn(con);
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}
	}

	public void auditArticle(String status, int id)
	{

		ConnManage connManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		try
		{
			String sql = "update article set status=? where id= ?";
			con = connManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setString(1, status);
			ps.setInt(2, id);
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
				connManager.releaseConn(con);
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}
	}

	public void deleteArticle(int id)
	{

		ConnManage connManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		try
		{
			// 删除主题
			String sql = "delete from article where id = ?";
			con = connManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();

			// 删除回复
			sql = "delete from rearticle where articleid = ?";
			ps = connManager.getConn().prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
		}
		catch (Exception e)
		{
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
				connManager.releaseConn(con);
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}
	}

	public List<Article> adminQueryList(int noStart, int pageSize) throws Exception
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Article> list = new ArrayList<Article>();
		try
		{
			String sql = "select id,content,img_url,video_url,medio_type,tag,up,down,post_time,status from article a order by post_time desc limit ?,?";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setInt(1, noStart);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next())
			{
				Article article = new Article();
				article.setId(rs.getInt("id"));
				article.setContent(rs.getString("content"));
				article.setImgUrl(rs.getString("img_url"));
				article.setVideoUrl(rs.getString("video_url"));
				article.setMedioType(rs.getString("medio_type"));
				article.setUp(rs.getInt("up"));
				article.setDown(rs.getInt("down"));
				article.setPostTime(rs.getString("post_time"));
				article.setStatus(rs.getString("status"));
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

	public List<Article> adminQueryListByStatus(int noStart, int pageSize, String status) throws Exception
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Article> list = new ArrayList<Article>();
		try
		{
			String sql = "select id,content,img_url,video_url,medio_type,tag,up,down,post_time,status from article a where status = ? order by post_time desc limit ?,?";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setString(1, status);
			ps.setInt(2, noStart);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next())
			{
				Article article = new Article();
				article.setId(rs.getInt("id"));
				article.setContent(rs.getString("content"));
				article.setImgUrl(rs.getString("img_url"));
				article.setVideoUrl(rs.getString("video_url"));
				article.setMedioType(rs.getString("medio_type"));
				article.setUp(rs.getInt("up"));
				article.setDown(rs.getInt("down"));
				article.setPostTime(rs.getString("post_time"));
				article.setStatus(rs.getString("status"));
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

	public int adminQueryCountByStatus(String status)
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		try
		{
			String sql = "select count(id) from article a where 1=1";
			if (StringUtil.isNotEmpty(status))
			{
				sql = sql + " and status = '" + status + "'";
			}
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
				throw new RuntimeException(e);
			}
		}
		return count;
	}

	/**
	 * 获取当前来源的最大时间
	 * 
	 * @param sourceFrom
	 * @return
	 */
	public String queryMaxSourceTime(String sourceFrom)
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sourceTime = null;
		try
		{
			String sql = "select max(source_time) sourceTime from article where source_from = ?";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setString(1, sourceFrom);
			rs = ps.executeQuery();
			if (rs.next())
			{
				sourceTime = rs.getString("sourceTime");
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
		return sourceTime;
	}

	public Map<String, String> queryCount(String time)
	{

		String beforDay = getSpecifiedDayBefore(time);
		Map<String, String> map = new HashMap<String, String>();
		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "select count(id) count from article where source_from = 'Q' and post_time between ? and ?";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setString(1, beforDay);
			ps.setString(2, time);
			rs = ps.executeQuery();
			if (rs.next())
			{
				map.put("Q", rs.getInt("count") + "");
			}

			sql = "select count(id) count from article where source_from = 'P' and post_time between ? and ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, beforDay);
			ps.setString(2, time);
			rs = ps.executeQuery();
			if (rs.next())
			{
				map.put("P", rs.getInt("count") + "");
			}

			sql = "select count(id) count from article where source_from = 'H' and post_time between ? and ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, beforDay);
			ps.setString(2, time);
			rs = ps.executeQuery();
			if (rs.next())
			{
				map.put("H", rs.getInt("count") + "");
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
		return map;
	}

	private String getSpecifiedDayBefore(String specifiedDay)
	{

		Calendar c = Calendar.getInstance();
		Date date = null;
		try
		{
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());
		return dayBefore;
	}

	public int queryCountByTimeRange(String beforTime, String curTime) throws Exception
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		try
		{
			String sql = "select count(id) from article where status = 'Y' and post_time between ? and ?";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setString(1, beforTime);
			ps.setString(2, curTime);
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

	public int queryCountByTimeRangeHavePic(String beforTime, String curTime) throws Exception
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		try
		{
			String sql = "select count(id) from article where status = 'Y' and img_url is not null and post_time between ? and ?";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setString(1, beforTime);
			ps.setString(2, curTime);
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

	public List<Article> searchArticle(int noStart, int pageSize, String searchKey) throws Exception
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Article> list = new ArrayList<Article>();
		try
		{
			String sql = "select id,content,img_url,video_url,medio_type,tag,up,down,post_time,u.avatar,u.username,u.uid,(select count(r.id) from "
					+ "rearticle r where r.articleid = a.id) recount from article a left join user u on u.uid=a.uid where status = 'Y' and content like ? or tag like ? order by post_time desc limit ?,?";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + searchKey + "%");
			ps.setString(2, "%" + searchKey + "%");
			ps.setInt(3, noStart);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next())
			{
				Article article = new Article();
				article.setId(rs.getInt("id"));
				article.setContent(rs.getString("content"));
				article.setImgUrl(rs.getString("img_url"));
				article.setVideoUrl(rs.getString("video_url"));
				article.setMedioType(rs.getString("medio_type"));
				article.setAvatar(rs.getString("avatar"));
				article.setUid(rs.getString("uid"));
				article.setUserName(rs.getString("username"));
				article.setUp(rs.getInt("up"));
				article.setDown(rs.getInt("down"));
				article.setReCount(rs.getInt("recount"));
				article.setPostTime(rs.getString("post_time"));
				String tags = rs.getString("tag");
				if (StringUtil.isNotEmpty(tags))
				{
					String tag = tags.replace("，", ",");
					List<String> tagList = new ArrayList<String>();
					article.setTags(tagList);
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

	public int searchArticleCount(String searchKey) throws Exception
	{

		ConnManage conManager = ConnManage.getInstance();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		try
		{
			String sql = "select count(id) from article where status = 'Y' and content like ? or tag like ?";
			con = conManager.getConn();
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + searchKey + "%");
			ps.setString(2, "%" + searchKey + "%");
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
}
