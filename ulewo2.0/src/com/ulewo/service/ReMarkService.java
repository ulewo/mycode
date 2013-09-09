package com.ulewo.service;

import java.util.List;

import com.ulewo.entity.ReMark;

public interface ReMarkService {
	public void addReMark(ReMark reMark);

	public boolean isMark(String userId,String time);
	
	public List<ReMark> queryReMarkByTime(String time);
}
