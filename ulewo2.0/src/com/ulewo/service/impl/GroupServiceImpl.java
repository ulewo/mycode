package com.ulewo.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ulewo.dao.ArticleDao;
import com.ulewo.dao.GroupDao;
import com.ulewo.dao.MemberDao;
import com.ulewo.dao.UserDao;
import com.ulewo.entity.Group;
import com.ulewo.service.GroupService;
import com.ulewo.util.Constant;
import com.ulewo.util.Pagination;
import com.ulewo.util.PaginationResult;
import com.ulewo.util.StringUtils;

@Service("groupService")
public class GroupServiceImpl implements GroupService {
	@Autowired
	private ArticleDao articleDao;

	@Autowired
	private GroupDao groupDao;

	@Autowired
	private MemberDao memberDao;

	@Autowired
	private UserDao userDao;

	private final SimpleDateFormat formate = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	@Override
	public String createGroup(Group group) {

		// 获取最大的群ID
		int gid = groupDao.getMaxGId();
		String id = String.valueOf(gid + 1);
		group.setId(id);
		// 输入名称过滤html
		group.setGroupName(StringUtils.clearHtml(group.getGroupName()));
		if (StringUtils.isEmpty(group.getGroupIcon())) {
			group.setGroupIcon(Constant.GROUP_DEFAULT_LOGO);
		}
		if (StringUtils.isEmpty(group.getGroupHeadIcon())) {
			group.setGroupHeadIcon(Constant.GROUP_DEFAULT_HEADIMAG);
		}
		group.setCreateTime(formate.format(new Date()));
		groupDao.createGroup(group);
		/*
		 * CacheManager manager = GroupAdminManager.getInstants(); Member member
		 * = new Member(); member.setGid(id); member.setGrade(2);
		 * member.setUserId(group.getGroupAuthor()); member.setIsMember("Y");
		 * member.setJoinTime(formate.format(new Date())); manager.add(id,
		 * member); memberDao.addMember(member);
		 */
		return id;
	}

	/**
	 * 单笔查询群组
	 * 
	 * @param gid
	 * @return @
	 */
	public Group queryGorup(String gid) {

		Group group = groupDao.getGroup(gid);
		if (null != group) {
			group.setCreateTime(StringUtils.friendly_time(group.getCreateTime()));
		}
		return group;
	}

	@Override
	public void updateGroup(Group group) {
		group.setGroupName(StringUtils.clearHtml(group.getGroupName()));
		group.setGroupDesc(StringUtils.formateHtml(group.getGroupDesc()));
		group.setGroupNotice(StringUtils.formateHtml(group.getGroupNotice()));
		groupDao.updateGroup(group);
	}

	@Override
	public PaginationResult queryGroupsOderArticleCount(int page, int pageSize) {

		int count = groupDao.queryGroupsCount();
		Pagination pagination = new Pagination(page, count, pageSize);
		pagination.action();
		List<Group> list = groupDao.queryGroupsByArticleCount(
				pagination.getOffSet(), pageSize);
		PaginationResult result = new PaginationResult(pagination.getPage(),
				pagination.getPageTotal(), count, list);
		return result;
	}

	@Override
	public int queryGroupsCount() {

		return groupDao.queryGroupsCount();
	}

	@Override
	public int queryCreatedGroupCount(String userId) {

		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Group> queryCreatedGroups(String userId) {

		List<Group> list = groupDao.queryCreatedGroups(userId);
		return list;
	}

	@Override
	public int queryJoinedGroupCount(String userId) {

		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Group> queryJoinedGroups(String userId) {

		List<Group> list = groupDao.queryJoinedGroups(userId);
		return list;
	}

	@Override
	public List<Group> searchGroups(String keyWord, int offset, int total) {

		return groupDao.searchGroup(keyWord, offset, total);
	}

	@Override
	public int searchGroupCount(String keyWord) {

		return groupDao.searchGroupCount(keyWord);
	}

	public void setArticleDao(ArticleDao articleDao) {

		this.articleDao = articleDao;
	}

	public void setGroupDao(GroupDao groupDao) {

		this.groupDao = groupDao;
	}

	public void setMemberDao(MemberDao memberDao) {

		this.memberDao = memberDao;
	}

	public void setUserDao(UserDao userDao) {

		this.userDao = userDao;
	}

}
