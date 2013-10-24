package com.ulewo.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ulewo.dao.UserDao;
import com.ulewo.entity.User;
import com.ulewo.enums.QueryUserType;
import com.ulewo.service.UserService;
import com.ulewo.util.Constant;
import com.ulewo.util.Pagination;
import com.ulewo.util.PaginationResult;
import com.ulewo.util.SendMail;
import com.ulewo.util.StringUtils;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Override
	public User findUser(String value, QueryUserType type) {

		if (null == value) {
			return null;
		}
		return userDao.findUser(value, type);
	}

	@Override
	public synchronized void addUser(User user) {

		String userId = userDao.findMaxUserId();
		user.setUserId(Integer.parseInt(userId) + 1 + "");
		user.setUserLittleIcon(Constant.DEFALUTICON);
		String date = StringUtils.dateFormater.get().format(new Date());
		user.setRegisterTime(date);
		user.setPrevisitTime(date);
		userDao.addUser(user);
	}

	@Override
	public void updateUser(User user) {

		userDao.update(user);
	}

	@Override
	public void deleteUser(String userId) {

		// TODO Auto-generated method stub

	}

	@Override
	public PaginationResult findUser(String value, QueryUserType type, int offset, int total) {

		//int total = userDao.findUser(value, type)
		return null;
	}

	public User login(String value, String password) {

		User user = null;
		if (value.contains("@")) {
			user = userDao.findUser(value, QueryUserType.EMAIL);
		}
		else {
			user = userDao.findUser(value, QueryUserType.USERNAME);
		}
		if (user != null && user.getPassword().equals(password)) {
			return user;
		}
		else {
			return null;
		}
	}

	public User queryBaseInfo(String userId) {

		return userDao.findBaseInfo(userId);
	}

	public String sendRestPwd(User user) throws Exception {

		String activationCode = createCode();
		user.setActivationCode(activationCode);
		userDao.update(user);
		sendMile(user.getEmail(), activationCode);
		return MailAdress(user.getEmail());
	}

	// 获取发送邮件的域
	private String MailAdress(String email) throws Exception {

		String maillAdress = "www.ulewo.com";
		int start = email.indexOf("@");
		int end = email.indexOf(".");
		String web = email.substring(start + 1, end);
		if ("gmail".equalsIgnoreCase(web)) {
			maillAdress = "http://www.gmail.com";
		}
		else {
			maillAdress = "http://mail." + web + ".com";
		}
		return maillAdress;
	}

	private String createCode() {

		String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String sRand = "";
		Random random = new Random();
		for (int i = 0; i < 20; i++) {
			int x = random.nextInt(s.length());
			String rand = String.valueOf(s.charAt(x));
			sRand += rand;
		}
		return sRand;
	}

	// 发送激活邮件
	private void sendMile(String email, String activationCode) throws Exception {

		String url = "http://ulewo.com/user/findPwd?account=" + email + "&code=" + activationCode;
		String title = "ulewo邮箱找回密码邮件";
		StringBuffer content = new StringBuffer("亲爱的" + email + "<br><br>");
		content.append("欢迎使用ulewo找回密码功能。(http://ulewo.com)!<br><br>");
		content.append("请点击链接重置密码：<br><br>");
		content.append("<a href=\"" + url + "\">" + url + "</a><br><br>");
		content.append("如果你的email程序不支持链接点击，请将上面的地址拷贝至你的浏览器(如IE)的地址栏进入。<br><br>");
		content.append("您的注册邮箱是:" + email + "<br><br>");
		content.append("希望你在有乐窝社区的体验有益和愉快！<br><br>");
		content.append("- 有乐窝社区(http://ulewo.com)");
		String[] address = new String[] { email };
		SendMail.sendEmail(title, String.valueOf(content), address);
	}

	public Map<String, Object> resetPwd(String account, String activationCode, String pwd) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		User user = null;
		if (account.contains("@")) {
			user = userDao.findUser(account, QueryUserType.EMAIL);
		}
		else {
			user = userDao.findUser(account, QueryUserType.USERNAME);
		}
		if (null == user) {
			modelMap.put("result", "fail");
			modelMap.put("message", "用户不存在");
			return modelMap;
		}
		else if (!user.getActivationCode().equals(activationCode)) {
			modelMap.put("result", "fail");
			modelMap.put("message", "激活码不匹配");
			return modelMap;
		}
		else {
			user.setPassword(StringUtils.encodeByMD5(pwd));
			userDao.update(user);
			modelMap.put("result", "success");
			return modelMap;
		}
	}

	public User WeiBoRegister(weibo4j.model.User user) {

		User myUser = new User();
		myUser.setUserName(user.getScreenName());
		addUser(myUser);
		return myUser;
	}

	public PaginationResult findAllUsers(String userName, int page, int pageSize) {

		int count = userDao.findUserCount(userName);
		Pagination pagination = new Pagination(page, count, pageSize);
		pagination.action();
		List<User> list = userDao.findAllUsers(userName, pagination.getOffSet(), pageSize);
		for (User user : list) {
			user.setRegisterTime(StringUtils.friendly_time(user.getRegisterTime()));
			user.setPrevisitTime(StringUtils.friendly_time(user.getPrevisitTime()));
		}
		PaginationResult result = new PaginationResult(pagination.getPage(), pagination.getPageTotal(), count, list);
		return result;
	}
	
	public List<User> selectUsersByMark(int page, int pageSize) {
		List<User> list = userDao.selectUsersByMark(page,pageSize);
		for (User user : list) {
			user.setRegisterTime(StringUtils.friendly_time(user.getRegisterTime()));
		}
		return list;
	}
}
