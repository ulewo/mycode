package com.lhl.quan.service.impl;

import java.util.List;

import com.lhl.entity.FriendGroup;
import com.lhl.entity.Group;
import com.lhl.exception.BaseException;
import com.lhl.quan.dao.ArticleDao;
import com.lhl.quan.dao.FriendGroupDao;
import com.lhl.quan.dao.GroupDao;
import com.lhl.quan.dao.MemberDao;
import com.lhl.quan.service.FriendGroupService;
import com.lhl.util.Constant;

/** 
 * @Title:
 * @Description: 友情圈业务实现
 * @author luohl
 * @date 2012-3-29
 * @version V1.0
*/
public class FriendGroupServiceImpl implements FriendGroupService {

	private FriendGroupDao friendGroupDao;

	private GroupDao groupDao;

	private MemberDao memberDao;

	private ArticleDao articleDao;

	public void setGroupDao(GroupDao groupDao) {

		this.groupDao = groupDao;
	}

	public void setMemberDao(MemberDao memberDao) {

		this.memberDao = memberDao;
	}

	public void setArticleDao(ArticleDao articleDao) {

		this.articleDao = articleDao;
	}

	public void setFriendGroupDao(FriendGroupDao friendGroupDao) {

		this.friendGroupDao = friendGroupDao;
	}

	@Override
	public void addFriendGroup(FriendGroup friendGroup) throws Exception {

		this.friendGroupDao.addFriendGroup(friendGroup);
	}

	@Override
	public void deleteFriendGroup(int id, String gid) throws Exception {

		FriendGroup friendGroup = friendGroupDao.getFriendGroup(id);
		if (friendGroup == null) {
			throw new BaseException(20000);
		}
		if (!gid.equals(friendGroup.getFriendGid())) {
			throw new BaseException(20001);
		}
		friendGroupDao.deleteFriendGroup(id);
	}

	@Override
	public FriendGroup getFriendGroup(int id) throws Exception {

		return friendGroupDao.getFriendGroup(id);
	}

	@Override
	public List<FriendGroup> queryFriendGroups(String gid) throws Exception {

		List<FriendGroup> list = friendGroupDao.queryFriendGroups(gid);
		Group group = null;
		for (FriendGroup friendGroup : list) {
			group = groupDao.getGroup(friendGroup.getFriendGid());

			if (group != null) {
				//设置友情圈子名称
				friendGroup.setGroupName(group.getGroupName());

				//设置友情圈子成员
				int memberCount = memberDao.queryMemberCount(group.getId(), Constant.ISVALID01, "");
				friendGroup.setMemberCount(memberCount);

				//设置帖子数
				int articleCount = articleDao.queryTopicCountByGid(group.getId(), 0, "00");
				friendGroup.setArticleCount(articleCount);

				friendGroup.setGroupIcon(group.getGroupIcon());
			}
			else {
				list.remove(friendGroup);
			}
		}
		return list;

	}

	@Override
	public int queryFriendGroupCount(String gid) throws Exception {

		return friendGroupDao.queryFriendGroupsCount(gid);
	}

}
