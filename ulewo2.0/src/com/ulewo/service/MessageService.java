package com.ulewo.service;

import java.util.List;

import com.ulewo.entity.Message;

public interface MessageService {

	/**
	 * 查询留言
	 * 
	 * @param userId
	 * @param offset
	 * @param total
	 * @return
	 * @throws Exception
	 */
	public List<Message> queryMessage(String userId, int offset, int total) throws Exception;

	/**
	 * 查询留言条数
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public int getCount(String userId) throws Exception;

	/**
	 * 添加留言
	 * @param user
	 * 
	 * @return TODO
	 * @throws Exception
	 */
	public Message addMessage(Message message) throws Exception;

	/**
	 * 
	 * description: 删除评论
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 * @author luohl
	 */
	public boolean deletMessage(String curUserId, int id);
}
