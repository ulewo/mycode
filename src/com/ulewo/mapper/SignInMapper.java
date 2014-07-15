package com.ulewo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 签到
 * 
 * @author luo.hl
 * @date 2013-12-8 下午4:33:07
 * @version 3.0
 * @copyright www.ulewo.com
 */
public interface SignInMapper<SignIn> extends BaseMapper<SignIn> {
	public List<SignIn> selectAllSignInByYear(@Param("param") Map<String, String> map);
}
