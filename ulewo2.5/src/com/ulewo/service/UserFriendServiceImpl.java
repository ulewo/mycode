package com.ulewo.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ulewo.enums.PageSize;
import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.UserFriendMapper;
import com.ulewo.model.UserFriend;
import com.ulewo.util.SimplePage;
import com.ulewo.util.StringUtils;
import com.ulewo.util.UlewoPaginationResult;

@Service("userFriendService")
public class UserFriendServiceImpl implements UserFriendService {

	@Resource
	UserFriendMapper<UserFriend> userFriendMapper;

	@Override
	public void addFriend(Map<String, String> map, Integer userId)
			throws BusinessException {

		String friendIdStr = map.get("friendUserId");
		if (!StringUtils.isNumber(friendIdStr)) {
			throw new BusinessException("参数错误");
		}

		UserFriend friend = new UserFriend();
		friend.setUserId(userId);
		friend.setFriendUserId(Integer.parseInt(friendIdStr));
		map.put("userId", String.valueOf(userId));
		UserFriend resultFriend = userFriendMapper.selectBaseInfo(map);
		if (resultFriend != null) {
			throw new BusinessException("你已经关注了该用户");
		}
		friend.setCreateTime(StringUtils.dateFormater.format(new Date()));
		userFriendMapper.insert(friend);
	}

	@Override
	public void deleteFirend(Map<String, String> map, Integer userId)
			throws BusinessException {
		String friendIdStr = map.get("friendUserId");
		if (!StringUtils.isNumber(friendIdStr)) {
			throw new BusinessException("参数错误");
		}
		map.put("userId", String.valueOf(userId));
		int count = userFriendMapper.delete(map);
		if (count == 0) {
			throw new BusinessException("取消关注的人不存在");
		}
	}

	@Override
	public UlewoPaginationResult<UserFriend> queryFocus(Map<String, String> map)
			throws BusinessException {
		String userId = map.get("userId");
		String page = map.get("page");
		if (!StringUtils.isNumber(userId)) {
			throw new BusinessException("参数错误");
		}
		Integer userId_int = Integer.valueOf(userId);
		Integer pageNo = 0;
		if (StringUtils.isNumber(page)) {
			pageNo = Integer.parseInt(page);
		}
		UserFriend friend = new UserFriend();
		friend.setUserId(userId_int);
		int count = userFriendMapper.selectFocusCount(friend);
		SimplePage simplePage = new SimplePage(pageNo, count,
				PageSize.SIZE20.getSize());
		List<UserFriend> list = userFriendMapper.selectFocusList(friend,
				simplePage);
		UlewoPaginationResult<UserFriend> result = new UlewoPaginationResult<UserFriend>(
				simplePage, list);
		return result;
	}

	@Override
	public UlewoPaginationResult<UserFriend> queryFans(Map<String, String> map)
			throws BusinessException {
		String userId = map.get("friendUserId");
		String page = map.get("page");
		if (!StringUtils.isNumber(userId)) {
			throw new BusinessException("参数错误");
		}
		Integer userId_int = Integer.valueOf(userId);
		Integer pageNo = 0;
		if (StringUtils.isNumber(page)) {
			pageNo = Integer.parseInt(page);
		}
		UserFriend friend = new UserFriend();
		friend.setFriendUserId(userId_int);
		int count = userFriendMapper.selectFansCount(friend);
		SimplePage simplePage = new SimplePage(pageNo, count,
				PageSize.SIZE20.getSize());
		List<UserFriend> list = userFriendMapper.selectFansList(friend,
				simplePage);
		UlewoPaginationResult<UserFriend> result = new UlewoPaginationResult<UserFriend>(
				simplePage, list);
		return result;
	}

	@Override
	public List<UserFriend> queryFans4List(Integer userId)
			throws BusinessException {
		UserFriend friend = new UserFriend();
		friend.setFriendUserId(userId);
		SimplePage page = new SimplePage(0, PageSize.SIZE15.getSize());
		List<UserFriend> list = userFriendMapper.selectFansList(friend, page);
		return list;
	}

	@Override
	public List<UserFriend> queryFocus4List(Integer userId)
			throws BusinessException {
		UserFriend friend = new UserFriend();
		friend.setUserId(userId);
		SimplePage page = new SimplePage(0, PageSize.SIZE15.getSize());
		List<UserFriend> list = userFriendMapper.selectFocusList(friend, page);
		return list;
	}

	@Override
	public boolean isHaveFocus(Map<String, String> map) {
		UserFriend friend = userFriendMapper.selectBaseInfo(map);
		if (null == friend) {
			return false;
		} else {
			return true;
		}
	}
}
