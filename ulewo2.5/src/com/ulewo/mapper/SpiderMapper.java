package com.ulewo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ulewo.model.Spider;

/**
 * 爬虫
 * 
 * @author luo.hl
 * @date 2013-12-8 下午4:22:00
 * @version 3.0
 * @copyright www.ulewo.com
 */
public interface SpiderMapper<T> extends BaseMapper<T> {
	public List<Spider> selectSpiderByIds(@Param("list") List<String> list);

	public void insertBatch(@Param("list") List<Spider> list);

	public void updateBatch(@Param("list") List<Spider> list,
			@Param("status") String status);
}
