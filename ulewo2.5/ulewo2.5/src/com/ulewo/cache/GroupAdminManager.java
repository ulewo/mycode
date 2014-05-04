/* 
 * Copyright (C), 2004-2010, 三五互联科技股份有限公司
 * File Name: com.lhl.cache.GroupAdminManager.java
 * Encoding UTF-8 
 * Version: 1.0 
 * Date: 2012-4-19
 * History:
 * 1. Date: 2012-4-19
 *    Author: luohl
 *    Modification: 新建
 * 2. ...
 */
package com.ulewo.cache;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ulewo.model.GroupMember;

/** 
 * @Title:
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author luohl
 * @date 2012-4-19
 * @version V1.0
*/
public class GroupAdminManager implements CacheManager {

	private final Map<String, ArrayList<GroupMember>> admins = new ConcurrentHashMap<String, ArrayList<GroupMember>>();

	private static GroupAdminManager manager = null;

	private void GroupAdminManager() {

	};

	public static GroupAdminManager getInstants() {

		if (null == manager) {
			manager = new GroupAdminManager();
		}
		return manager;
	}

	@Override
	public void add(String key, Object value) {

		if (null != admins.get(key)) {//缓存中是否有该群组
			ArrayList<GroupMember> members = admins.get(key);
			members.add((GroupMember) value);
		}
		else {
			ArrayList<GroupMember> members = new ArrayList<GroupMember>();
			members.add((GroupMember) value);
			admins.put(key, members);
		}
	}

	@Override
	public <T> T get(String key) {

		if (key == null) {
			return null;
		}
		return (T) admins.get(key);
	}

	@Override
	public boolean remove(String key, Object value) {

		try {
			if (key == null || admins.get(key) == null || null == value) {
				return false;
			}
			ArrayList<GroupMember> members = admins.get(key);
			GroupMember mem = (GroupMember) value;
			for (GroupMember member : members) {
				if (member.getUserId().equals(mem.getUserId())) {
					members.remove(value);
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
