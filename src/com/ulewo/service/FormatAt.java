package com.ulewo.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ulewo.mapper.UserMapper;
import com.ulewo.model.User;
import com.ulewo.util.Constant;

public class FormatAt {
    private FormatAt() {

    };

    private static FormatAt instance = null;

    private static Pattern referer_pattern = Pattern
	    .compile("@([^@\\s\\:\\;\\,\\\\.\\<\\?\\？\\{\\}\\&]{1,})");// @.+?[\\s:]

    private static String userUrl = Constant.WEBSTIE + "user/";

    public static FormatAt getInstance(String... type) {

	if (instance == null) {
	    instance = new FormatAt();
	}
	return instance;
    }

    public String GenerateRefererLinks(UserMapper<User> userMapper, String msg,
	    List<Integer> userIds) {

	StringBuilder html = new StringBuilder();
	int lastIdx = 0;
	Matcher matchr = referer_pattern.matcher(msg);
	while (matchr.find()) {
	    String origion_str = matchr.group();
	    String userName = origion_str.substring(1, origion_str.length())
		    .trim();
	    html.append(msg.substring(lastIdx, matchr.start()));
	    // System.out.println(str + "...............");

	    /*
	     * User u = null; List<User> users =
	     * User.INSTANCE.LoadList(User.CheckUsername(str)); if (users !=
	     * null && users.size() > 0) { u = users.get(0); for (User ref :
	     * users) { if (ref.getThis_login_time() != null &&
	     * u.getThis_login_time() != null &&
	     * ref.getThis_login_time().after(u.getThis_login_time())) { u =
	     * ref; } } } if (u == null) { u = User.GetByIdent(str); }
	     */

	    /*
	     * if (u != null && !u.IsBlocked()) { html.append("<a href='" +
	     * LinkTool.user(u) + "' class='referer' target='_blank'>@");
	     * html.append(str.trim()); html.append("</a> "); if (referers !=
	     * null && !referers.contains(u.getId())) referers.add(u.getId()); }
	     * else { html.append(origion_str); }
	     */
	    User user = userMapper.selectUserByUserName(userName);
	    if (null != user) {
		html.append("<a href=\"" + userUrl + user.getUserId()
			+ "\" class=\"referer\" target=\"_blank\">@");
		html.append(userName.trim());
		html.append("</a> ");
		userIds.add(user.getUserId());
	    } else {
		html.append(origion_str);
	    }
	    lastIdx = matchr.end();

	}
	html.append(msg.substring(lastIdx));
	return html.toString();
    }
}
