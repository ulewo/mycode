package com.lhl.quan.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.lhl.cache.CacheManager;
import com.lhl.cache.GroupAdminManager;
import com.lhl.entity.Group;
import com.lhl.entity.Member;
import com.lhl.entity.User;
import com.lhl.exception.BaseException;
import com.lhl.quan.dao.ArticleDao;
import com.lhl.quan.dao.GroupDao;
import com.lhl.quan.dao.MemberDao;
import com.lhl.quan.dao.UserDao;
import com.lhl.quan.service.GroupService;
import com.lhl.util.Constant;
import com.lhl.util.Tools;

public class GroupServiceImpl implements GroupService {

	private ArticleDao articleDao;

	private GroupDao groupDao;

	private MemberDao memberDao;

	private UserDao userDao;

	private final SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public String createGroup(Group group) throws Exception {

		// 获取最大的群ID
		int gid = groupDao.getMaxGId();
		String id = String.valueOf(gid + 1);
		group.setId(id);
		// 输入名称过滤html
		group.setGroupName(Tools.clearHtml(group.getGroupName()));
		if (Tools.isEmpty(group.getGroupIcon())) {
			group.setGroupIcon(Constant.GROUP_DEFAULT_LOGO);
		}
		if (Tools.isEmpty(group.getGroupHeadIcon())) {
			group.setGroupHeadIcon(Constant.GROUP_DEFAULT_HEADIMAG);
		}
		group.setCreateTime(formate.format(new Date()));
		groupDao.createGroup(group);
		CacheManager manager = GroupAdminManager.getInstants();
		Member member = new Member();
		member.setGid(id);
		member.setGrade(2);
		member.setUserId(group.getGroupAuthor());
		member.setIsMember("Y");
		member.setJoinTime(formate.format(new Date()));
		manager.add(id, member);
		memberDao.addMember(member);
		return id;
	}

	/**
	 * 单笔查询群组
	 * 
	 * @param gid
	 * @return
	 * @throws Exception
	 */
	public Group queryGorup(String gid) throws Exception {

		Group group = groupDao.getGroup(gid);
		if (null == group) {
			throw new BaseException(40000);
		}
		return group;
	}

	public Group queryGroupExtInfo(String gid) throws Exception {

		Group group = groupDao.getGroup(gid);
		if (null == group) {
			throw new BaseException(40000);
		}
		group.setMembers(memberDao.queryMemberCount(group.getId(), Constant.ISVALIDY, ""));
		group.setTopicCount(articleDao.queryTopicCountByGid(group.getId(), 0, Constant.ISVALIDY));
		return group;
	}

	@Override
	public void updateGroup(Group group) throws Exception {

		groupDao.updateGroup(group);
	}

	@Override
	public List<Group> queryGroupsOderArticleCount(int pageNumber, int pageSize) throws Exception {

		List<Group> list = groupDao.queryGroupsByArticleCount(pageNumber, pageSize);

		for (Group group : list) {
			String groupdesc = group.getGroupDesc().replaceAll("<[.[^<]]*>", "").replaceAll("[\\n|\\r]", "")
					.replaceAll("&nbsp;", "");
			group.setGroupDesc(groupdesc);
			// 查询群作者信息
			User user = userDao.queryUser("", "", group.getGroupAuthor());
			// 如果群作者不存在，那么群就不展示
			if (user == null) {
				continue;
			}
			// 设置群作者姓名
			group.setAuthorName(user.getUserName());

			// 设置群成员数量
			group.setMembers(memberDao.queryMemberCount(group.getId(), Constant.ISVALIDY, ""));

			// 设置群文章数量
			group.setTopicCount(articleDao.queryTopicCountByGid(group.getId(), 0, Constant.ISVALIDY));
		}
		return list;
	}

	@Override
	public int queryGroupsCount() throws Exception {

		return groupDao.queryGroupsCount();
	}

	@Override
	public int queryCreatedGroupCount(String userId) throws Exception {

		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Group> queryCreatedGroups(String userId) throws Exception {

		List<Group> list = groupDao.queryCreatedGroups(userId);
		return list;
	}

	@Override
	public int queryJoinedGroupCount(String userId) throws Exception {

		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Group> queryJoinedGroups(String userId) throws Exception {

		List<Group> list = groupDao.queryJoinedGroups(userId);
		return list;
	}

	@Override
	public List<Group> searchGroups(String keyWord, int offset, int total) throws Exception {

		return groupDao.searchGroup(keyWord, offset, total);
	}

	@Override
	public int searchGroupCount(String keyWord) throws Exception {

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
