package com.ulewo.service;

import com.ulewo.entity.User;
import com.ulewo.enums.QueryUserType;
import com.ulewo.util.PaginationResult;

public interface UserService {
	/**
	 * 查询用户
	 * @param value
	 * @param type
	 * @return
	 */
	public User findUser(String value, QueryUserType type);

	/**
	 * 添加用户
	 * @param user
	 */
	public void addUser(User user);

	/**
	 * 更新用户
	 * @param user
	 */
	public void updateUser(User user);

	/**
	 * 删除用户
	 * @param userId
	 */
	public void deleteUser(String userId);

	/**
	 * 分页查询用户
	 * @param value
	 * @param type
	 * @param offset
	 * @param total
	 * @return
	 */
	public PaginationResult findUser(String value, QueryUserType type, int offset, int total);

	/**
	 * 登录
	 * @param value
	 * @param password TODO
	 * @return
	 */
	public User login(String value, String password);
	
	public User queryBaseInfo(String userId);
}
