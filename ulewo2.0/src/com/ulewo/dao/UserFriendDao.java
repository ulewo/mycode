package com.ulewo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ulewo.entity.UserFriend;

@Component
public class UserFriendDao extends BaseDao {
	public void addFriend(UserFriend friend) {
		this.getSqlMapClientTemplate().insert("userFriend.addFriend", friend);
	}

	public void deleteFriend(String userId, String friendId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("friendId", friendId);
		this.getSqlMapClientTemplate().delete("userFriend.deleteFriend", map);
	}

	@SuppressWarnings("unchecked")
	public List<UserFriend> queryFans(String userId, int offset, int total) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("offset", offset);
		map.put("total", total);
		return (List<UserFriend>) this.getSqlMapClientTemplate().queryForList(
				"userFriend.queryFans", map);
	}

	public int queryFansCount(String userId) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				"userFriend.queryFocusCount", userId);
	}

	@SuppressWarnings("unchecked")
	public List<UserFriend> queryFocus(String userId, int offset, int total) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("offset", offset);
		map.put("total", total);
		return (List<UserFriend>) this.getSqlMapClientTemplate().queryForList(
				"userFriend.queryFocus", map);
	}

	public int queryFocusCount(String userId) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				"userFriend.queryFocusCount", userId);
	}

	public UserFriend queryFocusUser(String userId,String friendId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("friendId", friendId);
		return (UserFriend) this.getSqlMapClientTemplate().queryForObject(
				"userFriend.queryFocusUser", map);
	}
}
