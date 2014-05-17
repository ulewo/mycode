package com.ulewo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ulewo.util.SimplePage;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2013-12-9 下午5:52:35
 * @version 3.0
 * @copyright www.ulewo.com
 */
public interface CommonDynamicMapper<T> extends BaseMapper<T> {

	/**
	 * 查询关注的人所有动态的数量
	 * @param param
	 * @param page
	 * @return
	 */
	public int selectFocusAllDynamicCount(@Param("param") Map<String, String> param);

	/**
	 * 查询关注的人所有的动态
	 * @param param
	 * @param page
	 * @return
	 */
	public List<T> selectFocusAllDynamic(@Param("param") Map<String, String> param, @Param("page") SimplePage page);

	/**
	 * 查询用户所有动态的数量
	 * @param param
	 * @param page
	 * @return
	 */
	public int selectUserAllDynamicCount(@Param("param") Map<String, String> param);

	/**
	 * 查询用户所有动态
	 * @param param
	 * @param page
	 * @return
	 */
	public List<T> selectUserAllDynamic(@Param("param") Map<String, String> param, @Param("page") SimplePage page);
}
