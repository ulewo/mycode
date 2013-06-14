package com.ulewo.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ulewo.dao.UserDao;
import com.ulewo.entity.Notice;
import com.ulewo.entity.User;
import com.ulewo.enums.QueryUserType;

public class FormatAt {
	private FormatAt() {

	};

	private static FormatAt instance = null;

	private static Pattern referer_pattern = Pattern.compile("@([^@\\s\\:\\;\\,\\\\.\\<\\?\\？\\{\\}\\&]{1,})");// @.+?[\\s:]

	private final SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static String userUrl = "../user/userInfo.jspx?userId=";

	public static FormatAt getInstance(String... type) {

		if (instance == null) {
			instance = new FormatAt();
		}
		if (null != type && type.length > 0 && Constant.TYPE_TALK.equals(type[0])) {
			userUrl = Constant.WEBSTIE + "user/userInfo.jspx?userId=";
		}
		return instance;
	}

	public String GenerateRefererLinks(UserDao userDao, String msg, List<String> referers) {

		StringBuilder html = new StringBuilder();
		int lastIdx = 0;
		Matcher matchr = referer_pattern.matcher(msg);
		while (matchr.find()) {
			String origion_str = matchr.group();
			String userName = origion_str.substring(1, origion_str.length()).trim();
			html.append(msg.substring(lastIdx, matchr.start()));
			// System.out.println(str + "...............");
			User user = userDao.findUser(userName, QueryUserType.USERNAME);
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
			if (null != user) {
				html.append("<a href=\"" + userUrl + user.getUserId() + "\" class=\"referer\" target=\"_blank\">@");
				html.append(userName.trim());
				html.append("</a> ");
				referers.add(user.getUserId());
			}
			else {
				html.append(origion_str);
			}
			lastIdx = matchr.end();
		}
		html.append(msg.substring(lastIdx));
		return html.toString();
	}

	public Notice formateNotic(String userId, String url, int type, String content) {

		Notice notice = new Notice();
		notice.setUrl(url);
		notice.setUserId(userId);
		notice.setPostTime(formate.format(new Date()));
		notice.setContent(content);
		notice.setType(type);
		notice.setStatus("N");
		return notice;
	}

	private String getUrl(int type) {

		ResourceBundle rb = ResourceBundle.getBundle("config.noticeurl");
		String url = "";
		switch (type) {
		case 1:
			url = rb.getString("addArticle");
			break;
		case 2:
			url = rb.getString("reArticle");
		default:
			break;
		}
		return url;
	}
}
