package com.ulewo.service;

import java.util.List;
import java.util.Map;

import com.ulewo.enums.QueryUserType;
import com.ulewo.exception.BusinessException;
import com.ulewo.model.SessionUser;
import com.ulewo.model.User;
import com.ulewo.util.UlewoPaginationResult;
import com.ulewo.vo.UserVo4Api;

public interface UserService {

	/**
	 * 注册
	 * 
	 * @param map
	 * @param sessionCode
	 * @return
	 * @throws BusinessException
	 *             TODO
	 */
	public User register(Map<String, String> map, String sessionCode)
			throws BusinessException;

	/**
	 * 登陆
	 * 
	 * @param map
	 * @param sessionCode
	 *            TODO
	 * @return TODO
	 * @throws BusinessException
	 *             TODO
	 */
	public User login(Map<String, String> map, String sessionCode)
			throws BusinessException;

	/**
	 * 发送找回密码重置链接
	 * 
	 * @param map
	 * @param sessionCode
	 * @return TODO
	 * @throws BusinessException
	 *             TODO
	 */
	public User sendRestPwd(Map<String, String> map, String sessionCode)
			throws Exception, BusinessException;

	/**
	 * 重置密码
	 * 
	 * @param map
	 * @param sessionCode
	 *            TODO
	 * @throws BusinessException
	 *             TODO
	 */
	public void resetPwd(Map<String, String> map, String sessionCode)
			throws BusinessException;

	/**
	 * 查询用户
	 * 
	 * @param value
	 * @param type
	 * @return
	 */
	public User findUser(String value, QueryUserType type)
			throws BusinessException;

	/**
	 * 更新用户
	 * 
	 * @param map
	 * @param sessionUser
	 *            TODO
	 * @return TODO
	 * @throws BusinessException
	 *             TODO
	 */
	public User updateUserInfo(Map<String, String> map, SessionUser sessionUser)
			throws BusinessException;

	public void setTheme(Map<String, String> map, SessionUser sessionUser)
			throws BusinessException;

	/**
	 * 更新密码
	 * 
	 * @param map
	 * @param sessionUser
	 * @throws BusinessException
	 *             TODO
	 */
	public void updatePassword(Map<String, String> map, SessionUser sessionUser)
			throws BusinessException;

	/**
	 * 更新非空属性
	 * 
	 * @param user
	 */
	public void updateSelective(User user);

	/**
	 * 批量删除用户
	 * 
	 * @param keyStr
	 * @throws BusinessException
	 *             TODO
	 */
	public void deleteUserBatch(Map<String, String> map)
			throws BusinessException;

	/**
	 * 登录
	 * 
	 * @param value
	 * @param password
	 *            TODO
	 * @return
	 */
	public User login(String value, String password) throws BusinessException;

	public User queryUserBaseInfo(Integer userId) throws BusinessException;

	public List<User> selectUsersByMark();

	/*********************** 后台管理 ******************************/
	/**
	 * 用户
	 * 
	 * @param map
	 * @return
	 * @throws BusinessException
	 */
	public UlewoPaginationResult<User> findAllUsers(Map<String, String> map)
			throws BusinessException;

	/************ api ************/
	/**
	 * 
	 * @param map
	 * @param sessionCode
	 * @return
	 * @throws BusinessException
	 */
	public UserVo4Api api_login(Map<String, String> map) throws BusinessException;
}
