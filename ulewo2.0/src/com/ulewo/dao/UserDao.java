package com.ulewo.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ulewo.entity.User;
import com.ulewo.enums.QueryUserType;
import com.ulewo.service.UserService;
@Component
public class UserDao extends BaseDao{
	/**
	 * 单笔查询用户
	 * @param value
	 * @param type
	 * @return
	 */
	public User findUser(String value,QueryUserType type){
		Map<String,String> paramMap = new HashMap<String,String>();
		if(type==QueryUserType.USERID){
			paramMap.put("userId", value);
			return (User)this.getSqlMapClientTemplate().queryForObject("user.findUser",paramMap);
		}else if(type==QueryUserType.USERNAME){
			paramMap.put("userName", value);
			return (User)this.getSqlMapClientTemplate().queryForObject("user.findUser",paramMap);
		}else if(type==QueryUserType.EMAIL){
			paramMap.put("email", value);
			return (User)this.getSqlMapClientTemplate().queryForObject("user.findUser",paramMap);
		}
		return null;
	}
	
	/**
	 * 添加用户
	 * @param user
	 */
	public void addUser(User user){
		this.getSqlMapClientTemplate().insert("user.addUser",user);
	}
}
