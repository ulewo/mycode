/* 
 * Copyright (C), 2004-2010, 三五互联科技股份有限公司
 * File Name: com.lhl.quan.service.impl.MemberServiceImp.java
 * Encoding UTF-8 
 * Version: 1.0 
 * Date: 2012-3-29
 * History:
 * 1. Date: 2012-3-29
 *    Author: luohl
 *    Modification: 新建
 * 2. ...
 */
package com.lhl.quan.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lhl.cache.CacheManager;
import com.lhl.cache.GroupAdminManager;
import com.lhl.entity.Group;
import com.lhl.entity.Member;
import com.lhl.entity.User;
import com.lhl.exception.BaseException;
import com.lhl.quan.dao.GroupDao;
import com.lhl.quan.dao.MemberDao;
import com.lhl.quan.dao.UserDao;
import com.lhl.quan.service.MemberService;
import com.lhl.util.Constant;
import com.lhl.util.Tools;

/**
 * @Title:
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author luohl
 * @date 2012-3-29
 * @version V1.0
 */
public class MemberServiceImp implements MemberService {

	private MemberDao memberDao;

	private UserDao userDao;

	private GroupDao groupDao;

	private final SimpleDateFormat formate = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public void setMemberDao(MemberDao memberDao) {

		this.memberDao = memberDao;
	}

	public void setUserDao(UserDao userDao) {

		this.userDao = userDao;
	}

	public void setGroupDao(GroupDao groupDao) {

		this.groupDao = groupDao;
	}

	@Override
	public void addMember(Member member) throws Exception {

		member.setJoinTime(formate.format(new Date()));
		memberDao.addMember(member);
	}

	@Override
	public void deleteMember(int[] ids) throws Exception {

		for (int i = 0; i < ids.length; i++) {
			memberDao.deleteMember(ids[i]);
		}

	}

	@Override
	public void updateMember(Member member) throws Exception {

		memberDao.updateMemberSelective(member);

	}

	public void acceptMember(int[] ids) throws Exception {

		Member member;
		for (int i = 0; i < ids.length; i++) {
			member = new Member();
			member.setId(ids[i]);
			member.setIsMember(Constant.ISVALIDY);
			memberDao.updateMemberSelective(member);
		}
	}

	public void set2Admin(int[] ids) throws Exception {

		CacheManager manager = GroupAdminManager.getInstants();

		Member member;
		for (int i = 0; i < ids.length; i++) {
			member = memberDao.getMember(ids[i]);
			if (null != member) {
				member.setGrade(Constant.grade1);
				memberDao.updateMemberSelective(member);
				// 刷新缓存
				manager.add(member.getGid(), member);
			}

		}
	}

	public void cancelAdmin(int[] ids) throws Exception {

		CacheManager manager = GroupAdminManager.getInstants();
		Member member;
		for (int i = 0; i < ids.length; i++) {
			member = memberDao.getMember(ids[i]);
			if (null != member) {
				member.setGrade(Constant.grade0);
				memberDao.updateMemberSelective(member);
				// 刷新缓存
				manager.remove(member.getGid(), member);
			}
		}
	}

	@Override
	public Member getMember(int id) throws Exception {

		return memberDao.getMember(id);
	}

	@Override
	public List<Member> queryMembers(String gid, String isMember, String order,
			int offset, int total) throws Exception {

		List<Member> list = memberDao.queryMembers(gid, isMember, "", order,
				offset, total);
		SetExtendInfo(list);
		return list;
	}

	@Override
	public List<Member> queryActiveMembers(String gid, int offset, int total)
			throws Exception {

		List<Member> list = memberDao.queryActiveMembers(gid,
				Constant.ISVALIDY, offset, total);
		SetExtendInfo(list);
		return list;
	}

	@Override
	public int queryMemberCount(String gid, String ismember) throws Exception {

		return memberDao.queryMemberCount(gid, ismember, "");
	}

	@Override
	public List<Member> queryComMembers(String gid, int offset, int total)
			throws Exception {

		List<Member> list = memberDao.queryMembersByGrade(gid,
				Constant.ISVALIDY, "0", offset, total);
		SetExtendInfo(list);
		return list;
	}

	public int queryComMemberCount(String gid) throws Exception {

		return memberDao.queryMemberCount(gid, Constant.ISVALIDY, "0");
	}

	/**
	 * 判断是不是管理员 description: 函数的目的/功能
	 * 
	 * @return
	 * @author luohl
	 */
	public boolean isAdmin(String gid, String curUserId, String type) {

		List<Member> list = memberDao.queryAdmins(gid);
		List<String> userList = new ArrayList<String>();
		for (Member member : list) {
			userList.add(member.getUserId());
		}
		if (userList.contains(curUserId)) {
			if (Constant.ADMIN_TYPE_S.equals(type)) {
				Group group = groupDao.getGroup(gid);
				if (group != null && curUserId.equals(group.getGroupAuthor())) {
					return true;
				} else {
					return false;
				}
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	/**
	 * 查询圈主
	 */
	public Member queryAdmin(String gid) throws Exception {

		List<Member> members = memberDao.queryMembersByGrade(gid,
				Constant.ISVALIDY, "2", 0, 1);
		if (members != null && members.size() > 0) {
			Member admin = members.get(0);
			User adminUser = userDao.queryUser(null, null, admin.getUserId());
			admin.setUserName(adminUser.getUserName());
			admin.setUserIcon(adminUser.getUserLittleIcon());
			return admin;
		} else {
			throw new BaseException(10000);
		}
	}

	/**
	 * 查询管理成员
	 */
	public List<Member> queryAdmins(String gid) throws Exception {

		List<Member> list = memberDao.queryAdmins(gid);
		SetExtendInfo(list);
		return list;
	}

	private void SetExtendInfo(List<Member> list) throws Exception {

		User user = null;
		for (Member member : list) {
			user = userDao.queryUser(null, null, member.getUserId());
			if (null != user) {
				member.setUserName(user.getUserName());
				member.setUserIcon(user.getUserLittleIcon());
			} else {
				list.remove(member);
			}
			member.setJoinTime(Tools.friendly_time(member.getJoinTime()));
		}
	}

	public List<Member> queryAllAdmins() throws Exception {

		List<Member> list = memberDao.queryAllAdmins();
		return list;
	}

	public boolean isMember(String gid, String userId) throws Exception {

		Member member = memberDao.queryMemberByGidAndUserId(gid, userId);
		if (null != member && Constant.ISVALIDY.equals(member.getIsMember())) {
			return true;
		}
		return false;
	}

	public Member getMember(String gid, String userId) throws Exception {

		return memberDao.queryMemberByGidAndUserId(gid, userId);
	}
}
