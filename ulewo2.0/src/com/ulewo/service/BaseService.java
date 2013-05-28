package com.ulewo.service;

import java.util.List;

public interface BaseService {
	/**
	 * 
	 * description: 查询集合
	 * @return
	 * @author luohl
	 */
	public List<?> query4List(Object... parames);

	/**
	 * 
	 * description: 单笔查询
	 * @param parames
	 * @return
	 * @author luohl
	 */
	public <T> T query4Object(Object... parames);

	/**
	 * 
	 * description: 新增
	 * @param t
	 * @author luohl
	 */
	public <T> void add(T t);

	/**
	 * 
	 * description: 更新
	 * @param t
	 * @author luohl
	 */
	public <T> void update(T t);

	/**
	 * 
	 * description: 删除
	 * @param obj
	 * @author luohl
	 */
	public void delete(Object obj);

}
