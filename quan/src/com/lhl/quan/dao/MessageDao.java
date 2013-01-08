package com.lhl.quan.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.lhl.entity.Message;

public class MessageDao extends SqlMapClientDaoSupport
{

	/**
	 * 查询所有留言
	 * @param email
	 * @param userName
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<Message> queryMessage(String userId, int offset, int total) throws Exception
	{

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("userId", userId);
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return (List<Message>) getSqlMapClientTemplate().queryForList("message.queryMessageByUserId", parmMap);
	}

	/**
	 * 查询留言条数
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public int getCount(String userId) throws Exception
	{

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("userId", userId);
		return (Integer) getSqlMapClientTemplate().queryForObject("message.queryCountByUserId", parmMap);
	}

	public List<Message> queryReMessage(int id) throws Exception
	{

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("id", id);
		return (List<Message>) getSqlMapClientTemplate().queryForList("message.queryMessageByUserId", parmMap);
	}

	public void addMessage(Message message) throws Exception
	{

		getSqlMapClientTemplate().insert("message.addMessage", message);
	}

	public Message queryMessageById(int id)
	{

		return (Message) getSqlMapClientTemplate().queryForObject("message.queryMessageById", id);
	}

	public void deleteMessage(int id)
	{

		getSqlMapClientTemplate().delete("message.deleteMessage");
	}
}
