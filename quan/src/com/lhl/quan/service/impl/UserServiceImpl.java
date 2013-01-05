package com.lhl.quan.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.lhl.entity.Article;
import com.lhl.entity.Group;
import com.lhl.entity.User;
import com.lhl.quan.dao.ArticleDao;
import com.lhl.quan.dao.GroupDao;
import com.lhl.quan.dao.UserDao;
import com.lhl.quan.service.UserService;
import com.lhl.util.Tools;

public class UserServiceImpl implements UserService
{

	private UserDao userDao;

	private ArticleDao articleDao;

	private GroupDao groupDao;

	private final SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public void setUserDao(UserDao userDao)
	{

		this.userDao = userDao;
	}

	public void setArticleDao(ArticleDao articleDao)
	{

		this.articleDao = articleDao;
	}

	public void setGroupDao(GroupDao groupDao)
	{

		this.groupDao = groupDao;
	}

	@Override
	public User checkEmail(String email) throws Exception
	{

		return userDao.queryUser(email, null, null);
	}

	@Override
	public User checkUserName(String userName) throws Exception
	{

		return userDao.queryUser(null, userName, null);
	}

	@Override
	public User login(String account) throws Exception
	{

		String checkEmail = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";
		String result = "";
		User user = null;
		if (account.matches(checkEmail))
		{//是邮箱
			user = checkEmail(account);
		}
		else
		{
			user = checkUserName(account);
		}

		return user;
	}

	@Override
	public String register(User user) throws Exception
	{

		//生成userId
		int id = userDao.getMaxUserId();
		String userId = String.valueOf(id + 1);
		user.setUserId(userId);
		/*String userName = user.getUserName();
		/*if (Tools.getRealLength(userName) > 10) {
			user.setShortName(userName.substring(0, 10));
		}
		else {
			user.setShortName(userName);
		}*/
		user.setRegisterTime(formate.format(new Date()));
		user.setUserLittleIcon("defaultsmall.gif");
		user.setUserBigIcon("defaultbig.gif");
		userDao.addUser(user);
		return userId;
	}

	@Override
	public void updateUserSelective(User user) throws Exception
	{

		if (Tools.isNotEmpty(user.getEmail()))
		{
			userDao.updateUserSelectiveByEmail(user);
		}
		else if (Tools.isNotEmpty(user.getUserId()))
		{
			userDao.updateUserSelectiveByUserId(user);
		}
		else
		{
			userDao.updateUserSelectiveByUserId(user);
		}
	}

	@Override
	public User getUserInfo(String userId) throws Exception
	{

		User user = userDao.queryUser("", "", userId);
		String sexCode = user.getSex();
		user.setSex(sexCode);
		return user;
	}

	@Override
	public void updateInfo(User user) throws Exception
	{

		userDao.updateUserSelectiveByUserId(user);
	}

	@Override
	public List<Article> queryTopics(String userId) throws Exception
	{

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Article> queryReTopics(String userId) throws Exception
	{

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Group> queryJoinGroup(String userId) throws Exception
	{

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Group> queryCreateGroup(String userId) throws Exception
	{

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> queryActiveUsers(int offset, int total) throws Exception
	{

		return userDao.queryActiveUser(offset, total);
	}
}
