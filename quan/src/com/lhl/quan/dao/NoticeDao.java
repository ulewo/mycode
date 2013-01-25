package com.lhl.quan.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.lhl.entity.Notice;

public class NoticeDao extends SqlMapClientDaoSupport
{

	/**
	 * 创建消息
	 * 
	 * @param member
	 * @throws Exception
	 */
	public void createNotice(Notice notice)
	{

		this.getSqlMapClientTemplate().insert("notice.createNotice", notice);
	}

	/**
	 * 删除消息
	 * 
	 * @param id
	 */
	public void deleteNotice(int id) throws Exception
	{

		this.getSqlMapClientTemplate().delete("notice.deleteNotice", id);
	}

	/**
	 * 更新消息
	 * 
	 * @param member
	 */
	public void updateNotice(Notice notice)
	{

		this.getSqlMapClientTemplate().update("notice.updateNotice", notice);
	}

	/**
	 * 
	 * description: 查询消息
	 * 
	 * @param grade
	 *            TODO
	 * @param order
	 *            更具加入时间倒序，顺序排列
	 * @param groupNum
	 * @param itemId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @author luohl
	 */
	public List<Notice> queryNoticeByUserId(String userId, String status)
	{

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("userId", userId);
		parmMap.put("status", status);
		return this.getSqlMapClientTemplate().queryForList("notice.queryNoticeByUserId", parmMap);
	}

	/**

	/**
	 * 查询消息数
	 * 
	 * @param status
	 * @param grade
	 *            TODO
	 * @param groupNum
	 * @return
	 */
	public int queryNoticeCountByUserId(String userId, String status)
	{

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("userId", userId);
		parmMap.put("status", status);
		return (Integer) this.getSqlMapClientTemplate().queryForObject("notice.queryNoticeCountByUserId", parmMap);
	}
}
