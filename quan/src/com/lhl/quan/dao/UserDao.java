package com.lhl.quan.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.lhl.entity.User;

public class UserDao extends SqlMapClientDaoSupport
{
	/**
	 * 
	 * description:  条件查询用户
	 * @param email
	 * @param userName
	 * @param userId
	 * @return
	 * @author lhl
	 */
	public User queryUser(String email, String userName, String userId)
	{

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("email", email);
		parmMap.put("userName", userName);
		parmMap.put("userId", userId);
		return (User) getSqlMapClientTemplate().queryForObject("user.queryUser", parmMap);
	}

	/**
	 * 查询用户的详细信息
	 * description: 函数的目的/功能
	 * @param userId
	 * @return
	 * @author luohl
	 */
	public User queryUserDetail(String userId)
	{

		return (User) getSqlMapClientTemplate().queryForObject("user.queryUserDetail", userId);
	}

	/**
	 * 新增用户
	 * @param user
	 */
	public void addUser(User user) throws Exception
	{

		getSqlMapClientTemplate().insert("user.addUser", user);
	}

	/**
	 * 通过userId跟新非空字段
	 * @param user
	 * @throws Exception
	 */
	public void updateUserSelectiveByUserId(User user) throws Exception
	{

		getSqlMapClientTemplate().update("user.updateUser_selective_userId", user);
	}

	/**
	 * 通过email跟新非空字段
	 * @param user
	 * @throws Exception
	 */
	public void updateUserSelectiveByEmail(User user) throws Exception
	{

		getSqlMapClientTemplate().update("user.updateUser_selective_email", user);
	}

	/**
	 * 通过userId全更新
	 * @param user
	 * @throws Exception
	 */
	public void updateUserInfo(User user) throws Exception
	{

		getSqlMapClientTemplate().insert("user.updateUser_userId", user);
	}

	public List<User> queryActiveUser(int offset, int total) throws Exception
	{

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return getSqlMapClientTemplate().queryForList("user.queryActiveUser", parmMap);
	}

	public int getMaxUserId()
	{

		Object obj = getSqlMapClientTemplate().queryForObject("user.getMaxUserId");
		if (null == obj)
		{
			return 10000;
		}
		else
		{
			return (Integer) obj;
		}
	}
}
