package com.ulewo.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ulewo.enums.JoinPerm;
import com.ulewo.enums.MemberGrade;
import com.ulewo.enums.MemberType;
import com.ulewo.enums.PageSize;
import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.GroupMapper;
import com.ulewo.mapper.GroupMemberMapper;
import com.ulewo.model.Group;
import com.ulewo.model.GroupMember;
import com.ulewo.util.SimplePage;
import com.ulewo.util.StringUtils;
import com.ulewo.util.UlewoPaginationResult;
import com.ulewo.util.UlewoPaginationResult4Json;

/**
 * @Title:
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author luohl
 * @date 2012-3-29
 * @version V1.0
 */
@Service("groupMemberService")
public class GroupMemberServiceImp extends GroupAuthorityService implements
		GroupMemberService {

	@Resource
	private GroupMemberMapper<GroupMember> groupMemberMapper;

	@Resource
	private GroupMapper<Group> groupMapper;

	@Override
	public GroupMember joinGroup(Map<String, String> map, Integer userId)
			throws BusinessException {
		String gid = map.get("gid");
		if (StringUtils.isEmpty("gid") || !StringUtils.isNumber(gid)) {
			throw new BusinessException("参数错误!");
		}
		Integer gid_int = Integer.parseInt(gid);
		Group result = this.groupMapper.selectBaseInfo(map);
		if (result == null) {
			throw new BusinessException("窝窝不存在");
		}
		GroupMember member = new GroupMember();
		member.setGid(gid_int);
		member.setUserId(userId);
		member.setJoinTime(StringUtils.dateFormater.format(new Date()));
		if (result.getJoinPerm().equals(JoinPerm.APPLY)) {
			member.setMemberType(MemberType.NOTMEMBER.getValue());
		} else {
			member.setMemberType(MemberType.ISMEMBER.getValue());
		}
		member.setGrade(MemberGrade.MEMBER.getValue());
		this.groupMemberMapper.insert(member);
		return member;
	}

	@Override
	public void existGroup(Map<String, String> map, Integer userId)
			throws BusinessException {
		String gid = map.get("gid");
		if (StringUtils.isEmpty("gid") || !StringUtils.isNumber(gid)) {
			throw new BusinessException("参数错误!");
		}
		Group result = this.groupMapper.selectBaseInfo(map);
		if (result == null) {
			throw new BusinessException("窝窝不存在");
		}
		map.put("userId", String.valueOf(userId));
		groupMemberMapper.delete(map);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void deleteMember(Map<String, String> map, Integer userId)
			throws BusinessException {
		String gid = map.get("gid");
		if (StringUtils.isEmpty(gid) || !StringUtils.isNumber(gid)) {
			throw new BusinessException("参数错误!");
		}
		this.checkGroupOpAuthority(Integer.parseInt(gid), userId);
		String keyStr = map.get("key");
		String[] keys = keyStr.split(",");
		for (String key : keys) {
			map.put("userId", key);
			int count = this.groupMemberMapper.delete(map);
			if (count == 0) {
				throw new BusinessException("没有找到相应的记录!");
			}
		}
	}

	@Override
	public void accetpMember(Map<String, String> map, Integer userId)
			throws BusinessException {
		String gid = map.get("gid");
		if (StringUtils.isEmpty(gid) || !StringUtils.isNumber(gid)) {
			throw new BusinessException("参数错误!");
		}
		Integer gid_int = Integer.parseInt(gid);
		this.checkGroupOpAuthority(gid_int, userId);
		String keyStr = map.get("key");
		String[] keys = keyStr.split(",");
		for (String key : keys) {
			if (!StringUtils.isNumber(key)) {
				throw new BusinessException("参数错误!");
			}
			GroupMember member = new GroupMember();
			member.setGid(gid_int);
			member.setMemberType(MemberType.ISMEMBER.getValue());
			member.setUserId(Integer.parseInt(key));
			int count = this.groupMemberMapper.updateSelective(member);
			if (count == 0) {
				throw new BusinessException("没有找到相应的记录!");
			}
		}
	}

	@Override
	public UlewoPaginationResult<GroupMember> findMembers(
			Map<String, String> map) {
		int page_no = 0;
		if (StringUtils.isNumber(map.get("page"))) {
			page_no = Integer.parseInt(map.get("page"));
		}
		int count = groupMemberMapper.selectBaseInfoCount(map);
		SimplePage page = new SimplePage(page_no, count,
				PageSize.SIZE20.getSize());
		List<GroupMember> list = this.groupMemberMapper.selectBaseInfoList(map,
				page);
		UlewoPaginationResult<GroupMember> result = new UlewoPaginationResult<GroupMember>();
		result.setList(list);
		result.setPage(page);
		return result;
	}

	@Override
	public UlewoPaginationResult4Json<GroupMember> findMembers4Json(
			Map<String, String> map) {
		int page_no = 0;
		if (StringUtils.isNumber(map.get("page"))) {
			page_no = Integer.parseInt(map.get("page"));
		}
		int pageSize = StringUtils.isEmpty(map.get("rows")) ? 15 : Integer
				.parseInt(map.get("rows"));
		int count = groupMemberMapper.selectBaseInfoCount(map);
		SimplePage page = new SimplePage(page_no, count, pageSize);
		List<GroupMember> list = this.groupMemberMapper.selectBaseInfoList(map,
				page);
		UlewoPaginationResult4Json<GroupMember> result = new UlewoPaginationResult4Json<GroupMember>();
		result.setRows(list);
		result.setTotal(count);
		return result;
	}

	@Override
	public GroupMember findMember(Map<String, String> map) {
		String userId = map.get("userId");
		String gid = map.get("gid");
		GroupMember member = new GroupMember();
		if (!StringUtils.isNumber(userId)) {
			return null;
		}
		if (!StringUtils.isNumber(userId)) {
			return null;
		}
		return groupMemberMapper.selectBaseInfo(map);
	}

	@Override
	public List<GroupMember> findMembersActiveIndex(Map<String, String> map) {
		SimplePage page = new SimplePage(0, PageSize.SIZE20.getSize());
		map.put("orderBy", "topicCount desc");
		map.put("memberType", MemberType.ISMEMBER.getValue());
		List<GroupMember> list = this.groupMemberMapper.selectBaseInfoList(map,
				page);
		return list;
	}

	@Override
	public List<GroupMember> findAllMembersList(Map<String, String> map) {
		SimplePage page = new SimplePage(0, PageSize.SIZE20.getSize());
		map.put("memberType", MemberType.ISMEMBER.getValue());
		map.put("orderBy", null);
		List<GroupMember> list = this.groupMemberMapper.selectBaseInfoList(map,
				page);
		return list;
	}
}
