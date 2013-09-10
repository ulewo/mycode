package com.ulewo.service;

import java.util.List;

import com.ulewo.entity.ReMark;

public interface ReMarkService {
	public void addReMark(ReMark reMark);

	public boolean isMark(String userId, String time);

	public int userMarkCount(String userId);

	public List<ReMark> queryUserReMark(String userId);

	public List<ReMark> queryReMarkByTime(String time);

	public int allMarkCount(String markTime);
}
