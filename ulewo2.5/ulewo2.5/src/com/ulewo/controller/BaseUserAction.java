package com.ulewo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.ulewo.exception.BusinessException;
import com.ulewo.model.SessionUser;
import com.ulewo.model.User;
import com.ulewo.service.UserFriendService;
import com.ulewo.service.UserService;
import com.ulewo.util.StringUtils;
import com.ulewo.vo.UserVo;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-2-13 下午2:57:57
 * @version 0.1.0
 * @copyright yougou.com
 */
public class BaseUserAction extends BaseAction {
    @Autowired
    private UserService userService;

    @Autowired
    private UserFriendService userFriendService;

    public UserVo checkUserInfo(String userIdStr, HttpSession session)
	    throws BusinessException {
	if (StringUtils.isEmpty(userIdStr) || !StringUtils.isNumber(userIdStr)) {
	    return null;
	}
	Integer userId = Integer.parseInt(userIdStr);
	User user = userService.queryUserBaseInfo(userId);
	if (null == user) {
	    return null;
	}
	boolean haveFocus = false;

	SessionUser sessionUser = getSessionUser(session);
	if (sessionUser != null && !sessionUser.getUserId().equals(userId)) {
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("friendUserId", String.valueOf(userId));
	    map.put("userId", String.valueOf(sessionUser.getUserId()));
	    boolean isHaveFocus = userFriendService.isHaveFocus(map);
	    if (isHaveFocus) {
		haveFocus = true;
	    }
	}
	UserVo userVo = new UserVo();
	userVo.setUserId(user.getUserId());
	userVo.setUserName(user.getUserName());
	userVo.setUserIcon(user.getUserIcon());
	userVo.setAddress(user.getAddress());
	userVo.setAge(user.getAge());
	userVo.setCharacters(user.getCharacters());
	userVo.setMark(user.getMark());
	userVo.setPrevisitTime(StringUtils.friendly_time(user.getPreVisitTime()));
	userVo.setRegisterTime(StringUtils.friendly_time(user.getRegisterTime()));
	userVo.setSex(user.getSex());
	userVo.setWork(user.getWork());
	userVo.setFansCount(user.getFansCount());
	userVo.setFocusCount(user.getFocusCount());
	userVo.setHaveFocus(haveFocus);
	userVo.setCenterTheme(user.getCenterTheme());
	return userVo;
    }
}
