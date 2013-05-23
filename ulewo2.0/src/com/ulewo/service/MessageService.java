package com.ulewo.service;

import java.util.List;

import com.ulewo.entity.Message;

public interface MessageService {
	public List<Message> queryList();

	public Message queryMessage(int id);

	public void delete(int id);

	public void update(Message message);
}
