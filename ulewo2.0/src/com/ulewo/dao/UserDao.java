package com.ulewo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ulewo.entity.User;
import com.ulewo.enums.QueryUserType;
import com.ulewo.util.StringUtils;

@Component
public class UserDao extends BaseDao {
	/**
	 * 单笔查询用户
	 * @param value
	 * @param type
	 * @return
	 */
	public User findUser(String value, QueryUserType type) {

		Map<String, String> paramMap = new HashMap<String, String>();
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		if (type == QueryUserType.USERID) {
			paramMap.put("userId", value);
			return (User) this.getSqlMapClientTemplate().queryForObject("user.findUser", paramMap);
		}
		else if (type == QueryUserType.USERNAME) {
			paramMap.put("userName", value);
			return (User) this.getSqlMapClientTemplate().queryForObject("user.findUser", paramMap);
		}
		else if (type == QueryUserType.EMAIL) {
			paramMap.put("email", value);
			return (User) this.getSqlMapClientTemplate().queryForObject("user.findUser", paramMap);
		}
		return null;
	}

	public User findBaseInfo(String userId) {

		return (User) this.getSqlMapClientTemplate().queryForObject("user.baseInfo", userId);
	}

	/**
	 * 添加用户
	 * @param user
	 */
	public void addUser(User user) {

		this.getSqlMapClientTemplate().insert("user.addUser", user);
	}

	/**
	 * 查询最大的ID
	 */

	public String findMaxUserId() {

		return String.valueOf(this.getSqlMapClientTemplate().queryForObject("user.getMaxUserId"));
	}

	/**
	 * 查询数量
	 * @param userName
	 * @return
	 */
	public int findUserCount(String userName) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("userName", userName);
		return (Integer) this.getSqlMapClientTemplate().queryForObject("user.findUsersCount", parmMap);
	}

	/**
	 * 查询用户
	 * @param user
	 */
	public List<User> findAllUsers(String userName, int offset, int total) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return this.getSqlMapClientTemplate().queryForList("user.findAllUsers", parmMap);
	}

	public void update(User user) {

		this.getSqlMapClientTemplate().update("user.updateUser", user);
	}

	public List<User> selectUsersByMark(int offset, int total){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return this.getSqlMapClientTemplate().queryForList("user.selectUsersByMark", parmMap);
	}
}
