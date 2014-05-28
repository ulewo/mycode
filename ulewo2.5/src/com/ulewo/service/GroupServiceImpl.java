package com.ulewo.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ulewo.enums.JoinPerm;
import com.ulewo.enums.LengthEnums;
import com.ulewo.enums.PageSize;
import com.ulewo.enums.PostPerm;
import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.GroupMapper;
import com.ulewo.mapper.TopicMapper;
import com.ulewo.model.Group;
import com.ulewo.model.SessionUser;
import com.ulewo.model.Topic;
import com.ulewo.util.Constant;
import com.ulewo.util.SimplePage;
import com.ulewo.util.StringUtils;
import com.ulewo.util.UlewoPaginationResult;

@Service("groupService")
public class GroupServiceImpl implements GroupService {

	@Resource
	private GroupMapper<Group> groupMapper;

	@Resource
	private TopicMapper<Topic> topicMapper;

	@Override
	public Group createGroup(Map<String, String> map, SessionUser sessionUser) throws BusinessException {
		String groupName = map.get("groupName");
		String groupDesc = map.get("groupDesc");
		String joinPerm = map.get("joinPerm");
		String postPerm = map.get("postPerm");
		if (StringUtils.isEmpty(groupName) || StringUtils.getRealLength(groupName) > LengthEnums.Length50.getLength()) {
			throw new BusinessException("窝窝名称不能为空且不能超过50字符!");
		}
		if (StringUtils.isEmpty(groupDesc) || StringUtils.getRealLength(groupDesc) > LengthEnums.Length500.getLength()) {
			throw new BusinessException("窝窝简介不能为空且超过500字符!");
		}
		if (!JoinPerm.ALL.getValue().equals(joinPerm) && !JoinPerm.APPLY.getValue().equals(joinPerm)) {
			throw new BusinessException("参数错误!");
		}
		if (!PostPerm.ALL.getValue().equals(postPerm) && !PostPerm.YES.getValue().equals(postPerm)) {
			throw new BusinessException("参数错误!");
		}
		Group group = new Group();
		group.setGroupName(groupName);
		group.setGroupDesc(groupDesc);
		group.setJoinPerm(joinPerm);
		group.setPostPerm(postPerm);
		group.setGroupIcon(Constant.DEFALUTICON);
		group.setGroupUserId(sessionUser.getUserId());
		group.setCreateTime(StringUtils.dateFormater.get().format(new Date()));
		this.groupMapper.insert(group);
		return group;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void updateGroup(Map<String, String> map, Integer userId) throws BusinessException {
		String gid = map.get("gid");
		String groupName = map.get("groupName");
		String groupDesc = map.get("groupDesc");
		String joinPerm = map.get("joinPerm");
		String postPerm = map.get("postPerm");
		if (StringUtils.isEmpty(gid) || !StringUtils.isNumber(gid)) {
			throw new BusinessException("参数错误!");
		}
		if (StringUtils.isEmpty(groupName) || StringUtils.getRealLength(groupName) > LengthEnums.Length50.getLength()) {
			throw new BusinessException("窝窝名称不能为空且不能超过50字符!");
		}
		if (StringUtils.isEmpty(groupDesc) || StringUtils.getRealLength(groupDesc) > LengthEnums.Length500.getLength()) {
			throw new BusinessException("窝窝简介不能为空且超过500字符!");
		}
		if (!JoinPerm.ALL.getValue().equals(joinPerm) && !JoinPerm.APPLY.getValue().equals(joinPerm)) {
			throw new BusinessException("参数错误!");
		}
		if (!PostPerm.ALL.getValue().equals(postPerm) && !PostPerm.YES.getValue().equals(postPerm)) {
			throw new BusinessException("参数错误!");
		}
		Group group = new Group();
		group.setGid(Integer.parseInt(gid));
		group.setGroupName(groupName);
		group.setGroupDesc(groupDesc);
		group.setJoinPerm(joinPerm);
		group.setPostPerm(postPerm);
		group.setGroupUserId(userId);
		int count = this.groupMapper.updateSelective(group);
		if (count == 0) {
			throw new BusinessException("更新的窝窝不存在!");
		}
	}

	public void updateGroupIcon(Map<String, String> map, Integer userId) throws BusinessException {
		String gid = map.get("gid");
		String groupIcon = map.get("groupIcon");
		if (StringUtils.isEmpty(gid) || !StringUtils.isNumber(gid) || StringUtils.isEmpty(groupIcon)) {
			throw new BusinessException("参数错误!");
		}
		Group group = new Group();
		group.setGid(Integer.parseInt(gid));
		group.setGroupUserId(userId);
		group.setGroupIcon(groupIcon);
		int count = this.groupMapper.updateSelective(group);
		if (count == 0) {
			throw new BusinessException("更新的窝窝不存在!");
		}
	}

	@Override
	public void updateGroupNotice(Map<String, String> map, Integer userId) throws BusinessException {
		String gid = map.get("gid");
		String groupNotice = map.get("groupNotice");
		if (StringUtils.isEmpty(gid) || !StringUtils.isNumber(gid)) {
			throw new BusinessException("参数错误!");
		}
		if (!StringUtils.isEmpty(groupNotice)
				&& StringUtils.getRealLength(groupNotice) > LengthEnums.Length300.getLength()) {
			throw new BusinessException("窝窝公告不能超过300字符!");
		}
		Group group = new Group();
		group.setGid(Integer.parseInt(gid));
		group.setGroupNotice(groupNotice);
		group.setGroupUserId(userId);
		int count = this.groupMapper.updateSelective(group);
		if (count == 0) {
			throw new BusinessException("更新的窝窝不存在!");
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void deleteGroup(Map<String, String> map) throws BusinessException {
		String keyStr = map.get("key");
		String[] keys = keyStr.split(",");
		for (String key : keys) {
			map.put("gid", key);
			int count = this.groupMapper.delete(map);
			if (count == 0) {
				throw new BusinessException("没有找到相应的记录!");
			}
		}
	}

	@Override
	public UlewoPaginationResult<Group> findAllGroup(Map<String, String> map) {
		int page_no = 0;
		if (StringUtils.isNumber(map.get("page"))) {
			page_no = Integer.parseInt(map.get("page"));
		}
		int count = groupMapper.selectBaseInfoCount(map);
		int pageSize = PageSize.SIZE20.getSize();
		if (StringUtils.isNumber(map.get("rows"))) {
			pageSize = Integer.parseInt(map.get("rows"));
		}
		SimplePage page = new SimplePage(page_no, count, pageSize);
		List<Group> list = groupMapper.selectExtendInfoList(map, page);
		UlewoPaginationResult<Group> result = new UlewoPaginationResult<Group>(page, list);
		return result;
	}

	@Override
	public UlewoPaginationResult<Group> findCreatedGroups(Map<String, String> map) throws BusinessException {
		int page_no = 0;
		if (StringUtils.isNumber(map.get("page"))) {
			page_no = Integer.parseInt(map.get("page"));
		}
		String userIdstr = map.get("userId");
		if (!StringUtils.isNumber(userIdstr)) {
			throw new BusinessException("参数错误!");
		}
		Integer userId = Integer.parseInt(userIdstr);
		int count = this.groupMapper.selectCreatedGroupsCount(userId);
		SimplePage page = new SimplePage(page_no, count, PageSize.SIZE20.getSize());
		List<Group> list = this.groupMapper.selectCreatedGroups(userId, page);
		UlewoPaginationResult<Group> result = new UlewoPaginationResult<Group>(page, list);
		return result;
	}

	@Override
	public List<Group> findCreatedGroups(Integer userId, SimplePage page) {
		return this.groupMapper.selectCreatedGroups(userId, page);
	}

	@Override
	public UlewoPaginationResult<Group> findJoinedGroups(Map<String, String> map) throws BusinessException {
		int page_no = 0;
		if (StringUtils.isNumber(map.get("page"))) {
			page_no = Integer.parseInt(map.get("page"));
		}
		String userIdstr = map.get("userId");
		if (!StringUtils.isNumber(userIdstr)) {
			throw new BusinessException("参数错误!");
		}
		Integer userId = Integer.parseInt(userIdstr);
		int count = this.groupMapper.selectJoinedGroupsCount(userId);
		SimplePage page = new SimplePage(page_no, count, PageSize.SIZE20.getSize());
		List<Group> list = this.groupMapper.selectJoinedGroups(userId, page);
		UlewoPaginationResult<Group> result = new UlewoPaginationResult<Group>(page, list);
		return result;
	}

	@Override
	public List<Group> findJoinedGroups(Integer userId, SimplePage page) {
		return this.groupMapper.selectJoinedGroups(userId, page);
	}

	@Override
	public UlewoPaginationResult<Group> searchGroups(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Group findGroupBaseInfo(Map<String, String> map) throws BusinessException {
		String gid = map.get("gid");
		if (!StringUtils.isNumber(gid)) {
			throw new BusinessException("参数错误!");
		}
		Group result = this.groupMapper.selectExtendInfo(map);
		if (null == result) {
			throw new BusinessException("窝窝不存在!");
		}
		return result;
	}

	@Override
	public List<Group> findCommendGroup() {
		return this.groupMapper.selectCommendGroup();
	}

	@Override
	public List<Group> findCommendGroupAndTopic() {
		List<Group> list = this.groupMapper.selectCommendGroup();
		SimplePage page = new SimplePage(0, PageSize.SIZE5.getSize());
		Map<String, String> param = null;
		for (Group group : list) {
			param = new HashMap<String, String>();
			param.put("gid", String.valueOf(group.getGid()));
			param.put("orderBy", "create_time desc");

			group.setTopicList(this.topicMapper.selectTopicList(param, page));
		}
		return list;
	}
}
