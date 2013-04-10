package com.doov.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.doov.entity.Notice;

@Service("noticeService")
public interface NoticeService {
	/**
	 * 
	 * description: 查询所有
	 * @param title
	 * @param noStart
	 * @param offSet
	 * @return
	 * @author luohl
	 */
	public List<Notice> selectAll(String title, int noStart, int offSet);

	/**
	 * 
	 * description: 查询数量
	 * @param title
	 * @return
	 * @author luohl
	 */
	public int getCount(String title);

	/**
	 * 
	 * description: 单笔查询
	 * @param id
	 * @return
	 * @author luohl
	 */
	public Notice queryNotice(int id);

	/**
	 * 
	 * description: 新增
	 * @param notice
	 * @return
	 * @author luohl
	 */
	public int add(Notice notice);

	/**
	 * 
	 * description:更新
	 * @param notice
	 * @author luohl
	 */
	public void update(Notice notice);

	/**
	 * 
	 * description: 删除
	 * @param id
	 * @author luohl
	 */
	public void delete(int id);

}
