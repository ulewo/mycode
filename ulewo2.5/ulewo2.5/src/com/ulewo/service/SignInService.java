package com.ulewo.service;

import java.util.List;
import java.util.Map;

import com.ulewo.exception.BusinessException;
import com.ulewo.model.Calendar4Signin;
import com.ulewo.model.SessionUser;
import com.ulewo.model.SignIn;
import com.ulewo.util.UlewoPaginationResult;

public interface SignInService {
	/**
	 * 获取用户当天签到情况
	 * 
	 * @param sessionUser
	 * @return
	 * @throws BusinessException
	 */
	public Map<String, Object> signInInfo(SessionUser sessionUser) throws BusinessException;

	/**
	 * 签到
	 * @param sessionUser
	 * @return TODO
	 * 
	 * @throws BusinessException
	 */
	public Map<String, Object> doSignIn(SessionUser sessionUser) throws BusinessException;

	/**
	 * 用户签到情况
	 * 
	 * @param sessionUser
	 * @return
	 * @throws BusinessException TODO
	 */
	public List<Calendar4Signin> queryUserSigin(SessionUser sessionUser, Map<String, String> map) throws BusinessException;

	/**
	 * 查询当天签到情况
	 * @param map TODO
	 * 
	 * @return
	 */
	public UlewoPaginationResult<SignIn> queryCurDaySigin(Map<String, String> map);
}
