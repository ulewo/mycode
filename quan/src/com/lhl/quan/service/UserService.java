package com.lhl.quan.service;

import java.util.List;

import com.lhl.entity.Article;
import com.lhl.entity.Group;
import com.lhl.entity.User;

public interface UserService
{
	/**
	 * 用户注册
	 * @param user
	 * @throws Exception
	 */
	public String register(User user) throws Exception;

	/**
	 * 检测用户名是否占用
	 * @param userName
	 * @throws Exception
	 */
	public User checkUserName(String userName);

	/**
	 * 检测用户邮箱是否占用
	 * @param email
	 * @return
	 * @throws Exception
	 */
	public User checkEmail(String email) throws Exception;

	/**
	 * 用户登录
	 * @param account  帐号可能是邮箱，也可能是用户名
	 * @return
	 * @throws Exception
	 */
	public User login(String account) throws Exception;

	/**
	 * 更新用户非空属性
	 */
	public void updateUserSelective(User user) throws Exception;

	/**
	 * 查询用户信息
	 * @param userId
	 * @throws Exception
	 */
	public User getUserInfo(String userId) throws Exception;

	/**
	 * 更新用户信息
	 * @param user
	 * @throws Exception
	 */
	public void updateInfo(User user) throws Exception;

	/**
	 * 
	 * description: 查询作者发表的主题帖
	 * @return
	 * @throws Exception
	 * @author luohl
	 */
	public List<Article> queryTopics(String userId) throws Exception;

	/**
	 * 
	 * description: 查询回复过的主题
	 * @return
	 * @throws Exception
	 * @author luohl
	 */
	public List<Article> queryReTopics(String userId) throws Exception;

	/**
	 * 
	 * description: 查询加入的群组
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author luohl
	 */
	public List<Group> queryJoinGroup(String userId) throws Exception;

	/**
	 * 
	 * description: 查询创建的群组
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author luohl
	 */
	public List<Group> queryCreateGroup(String userId) throws Exception;

	/**
	 * 
	 * description: 活跃成员
	 * @param offset TODO
	 * @param total TODO
	 * @return
	 * @throws Exception
	 * @author lhl
	 */
	public List<User> queryActiveUsers(int offset, int total) throws Exception;
}
