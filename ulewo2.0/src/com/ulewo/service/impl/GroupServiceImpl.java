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
import com.ulewo.entity.User;
import com.ulewo.enums.QueryUserType;
import com.ulewo.service.GroupService;
import com.ulewo.util.Constant;
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

	private final SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public String createGroup(Group group) throws Exception {

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
		/*		CacheManager manager = GroupAdminManager.getInstants();
				Member member = new Member();
				member.setGid(id);
				member.setGrade(2);
				member.setUserId(group.getGroupAuthor());
				member.setIsMember("Y");
				member.setJoinTime(formate.format(new Date()));
				manager.add(id, member);
				memberDao.addMember(member);*/
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
			//	throw new BaseException(40000);
		}
		group.setCreateTime(StringUtils.friendly_time(group.getCreateTime()));
		return group;
	}

	public Group queryGroupExtInfo(String gid) throws Exception {

		Group group = groupDao.getGroup(gid);
		if (null == group) {
			//throw new BaseException(40000);
		}
		group.setMembers(memberDao.queryMemberCount(group.getId(), Constant.ISVALIDY, ""));
		group.setTopicCount(articleDao.queryTopicCountByGid(group.getId(), 0, Constant.ISVALIDY));
		group.setCreateTime(StringUtils.friendly_time(group.getCreateTime()));
		return group;
	}

	@Override
	public void updateGroup(Group group) throws Exception {

		groupDao.updateGroup(group);
	}

	@Override
	public List<Group> queryGroupsOderArticleCount(int pageNumber, int pageSize) {

		List<Group> list = groupDao.queryGroupsByArticleCount(pageNumber, pageSize);

		for (Group group : list) {
			String groupdesc = group.getGroupDesc().replaceAll("<[.[^<]]*>", "").replaceAll("[\\n|\\r]", "")
					.replaceAll("&nbsp;", "");
			group.setGroupDesc(groupdesc);
			// 查询群作者信息
			User user = userDao.findUser(group.getGroupAuthor(), QueryUserType.USERID);
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
			group.setCreateTime(StringUtils.friendly_time(group.getCreateTime()));
		}
		return list;
	}

	@Override
	public int queryGroupsCount() {

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
