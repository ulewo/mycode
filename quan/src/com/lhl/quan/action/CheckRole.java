/* 
 * Copyright (C), 2004-2010, 三五互联科技股份有限公司
 * File Name: com.lhl.quan.action.CheckRole.java
 * Encoding UTF-8 
 * Version: 1.0 
 * Date: 2012-5-31
 * History:
 * 1. Date: 2012-5-31
 *    Author: 35sz
 *    Modification: 新建
 * 2. ...
 */
package com.lhl.quan.action;

import java.util.ArrayList;

import com.lhl.cache.CacheManager;
import com.lhl.cache.GroupAdminManager;
import com.lhl.entity.Member;

/** 
 * @Title:
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 35sz
 * @date 2012-5-31
 * @version V1.0
*/
public class CheckRole {
	public static int getMemberGrade(String gid, String userId) {

		CacheManager manager = GroupAdminManager.getInstants();
		ArrayList<Member> members = manager.get(gid);
		if (null == members) {
			return -1;
		}
		for (Member member : members) {
			if (member.getUserId().equals(userId)) {
				return member.getGrade();
			}
		}
		return -1;
	}
}
