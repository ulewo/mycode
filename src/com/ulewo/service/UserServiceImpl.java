package com.ulewo.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ulewo.enums.MaxLengthEnums;
import com.ulewo.enums.PageSize;
import com.ulewo.enums.QueryUserType;
import com.ulewo.enums.ThemeTypeEnums;
import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.UserMapper;
import com.ulewo.model.SessionUser;
import com.ulewo.model.User;
import com.ulewo.util.Constant;
import com.ulewo.util.SendMail;
import com.ulewo.util.SimplePage;
import com.ulewo.util.StringUtils;
import com.ulewo.util.UlewoPaginationResult;
import com.ulewo.util.UlewoResult;
import com.ulewo.vo.UserVo4Api;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	UserMapper<User> userMapper;

	private static final int EMAIL_LENGTH = 50;
	private static final int USERNAME_LENGTH = 20;
	private static final int PWD_MAX_LENGTH = 16;
	private static final int PWD_MIN_LENGTH = 6;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public User register(Map<String, String> map, String sessionCode)
			throws BusinessException {
		String userName = map.get("userName");
		String email = map.get("email");
		String password = map.get("pwd");
		String checkCode = map.get("code");
		if (StringUtils.isEmpty(checkCode)) {
			throw new BusinessException("验证码不能为空");
		} else if (StringUtils.isEmpty(sessionCode)
				|| !sessionCode.equalsIgnoreCase(checkCode)) {
			throw new BusinessException("验证码错误");
		} else if (!StringUtils.checkEmail(email) || StringUtils.isEmpty(email)
				|| email.length() > EMAIL_LENGTH) {
			throw new BusinessException("邮箱地址不符合规范");
		} else if (!StringUtils.checkUserName(userName)
				|| StringUtils.isEmpty(userName.trim())
				|| StringUtils.getRealLength(userName) < 1
				|| StringUtils.getRealLength(userName) > USERNAME_LENGTH) {
			throw new BusinessException("用户名不符合规范");
		} else if (!StringUtils.checkPassWord(password)
				|| StringUtils.isEmpty(password)
				|| password.length() < PWD_MIN_LENGTH
				|| password.length() > PWD_MAX_LENGTH) {
			throw new BusinessException("密码不符合规范");
		} else if (null != checkEmail(email)) {// 后台检测邮箱是否唯一
			throw new BusinessException("邮箱已经被占用");
		} else if (null != checkUserName(userName)) { // 后台检测用户昵称是否唯一
			throw new BusinessException("用户名已经被占用");
		} else {
			User user = new User();
			user.setUserName(userName);
			user.setPassword(StringUtils.encodeByMD5(password));
			user.setEmail(email);
			String curDate = StringUtils.dateFormater.get().format(new Date());
			user.setRegisterTime(curDate);
			user.setPreVisitTime(curDate);
			user.setUserIcon(Constant.DEFALUTICON);
			userMapper.insert(user);
			return user;
		}
	}

	private User checkUserName(String userName) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userName", userName);
		User user = userMapper.selectBaseInfo(map);
		return user;
	}

	private User checkEmail(String email) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("email", email);
		User user = userMapper.selectBaseInfo(map);
		return user;
	}

	@Override
	public User login(Map<String, String> map, String sessionCode)
			throws BusinessException {
		String account = map.get("account");
		String password = map.get("password");
		String checkCode = map.get("checkCode");
		if (StringUtils.isEmpty(account)) {
			throw new BusinessException("账号不能为空");
		} else if (StringUtils.isEmpty(password)) {
			throw new BusinessException("密码不能为空");
		} else if (StringUtils.isEmpty(checkCode)) {
			throw new BusinessException("验证码不能为空");
		} else if (StringUtils.isEmpty(sessionCode)
				|| !sessionCode.equalsIgnoreCase(checkCode)) {
			throw new BusinessException("验证码错误");
		} else {
			password = StringUtils.encodeByMD5(password);
			User user = null;
			if (account.contains("@")) {
				user = findUser(account, QueryUserType.EMAIL);
			} else {
				user = findUser(account, QueryUserType.USERNAME);
			}
			if (null == user || !user.getPassword().equals(password)) {
				throw new BusinessException("帐号或者密码错误");
			} else {
				return user;
			}
		}
	}

	@Override
	public User sendRestPwd(Map<String, String> map, String sessionCode)
			throws Exception, BusinessException {
		String account = map.get("account");
		String checkCode = map.get("code");
		UlewoResult<User> result = new UlewoResult<User>();
		StringBuilder msg = new StringBuilder();
		if (StringUtils.isEmpty(account)) {
			throw new BusinessException("账号不能为空");
		}
		if (StringUtils.isEmpty(checkCode)) {
			throw new BusinessException("验证码不能为空");
		} else if (StringUtils.isEmpty(sessionCode)
				|| !sessionCode.equalsIgnoreCase(checkCode)) {
			throw new BusinessException("验证码错误");
		}
		User user = null;
		if (account.contains("@")) {
			user = findUser(account, QueryUserType.EMAIL);
		} else {
			user = findUser(account, QueryUserType.USERNAME);
		}
		if (null == user) {
			throw new BusinessException("帐号不存在");
		}
		// 发送邮件
		String activationCode = createCode();
		user.setActivationCode(activationCode);
		userMapper.updateSelective(user);
		sendMile(user.getEmail(), activationCode);
		return user;
	}

	/**
	 * 重置密码
	 */
	public void resetPwd(Map<String, String> map, String sessionCode)
			throws BusinessException {
		String account = map.get("account");
		String activationCode = map.get("activationCode");
		String pwd = map.get("pwd");
		String checkCode = map.get("code");

		if (StringUtils.isEmpty(account)) {
			throw new BusinessException("帐号不能为空");
		}
		if (StringUtils.isEmpty(activationCode)) {
			throw new BusinessException("激活码不能为空");
		}
		if (StringUtils.isEmpty(checkCode)) {
			throw new BusinessException("验证码不能为空");
		} else if (StringUtils.isEmpty(sessionCode)
				|| !sessionCode.equalsIgnoreCase(checkCode)) {
			throw new BusinessException("验证码错误");
		}

		if (!StringUtils.checkPassWord(pwd) || StringUtils.isEmpty(pwd)
				|| pwd.length() < PWD_MIN_LENGTH
				|| pwd.length() > PWD_MAX_LENGTH) {
			throw new BusinessException("密码不符合规范");
		}
		User user = null;
		if (account.contains("@")) {
			user = findUser(account, QueryUserType.EMAIL);
		} else {
			user = findUser(account, QueryUserType.USERNAME);
		}
		if (null == user) {
			throw new BusinessException("用户不存在");
		} else if (!user.getActivationCode().equals(activationCode)) {
			throw new BusinessException("激活码不匹配");
		} else {
			user.setPassword(StringUtils.encodeByMD5(pwd));
			userMapper.updateSelective(user);
		}
	}

	public void updatePassword(Map<String, String> map, SessionUser sessionUser)
			throws BusinessException {
		String oldpwd = map.get("oldpwd");
		String newpwd = map.get("newpwd");

		if (!StringUtils.checkPassWord(newpwd) || StringUtils.isEmpty(newpwd)
				|| newpwd.length() < MaxLengthEnums.MAXLENGTH6.getLength()
				|| newpwd.length() > MaxLengthEnums.MAXLENGTH16.getLength()) {
			throw new BusinessException("新密码不符合规范");
		}
		Integer userId = sessionUser.getUserId();
		User resultUser = findUser(userId.toString(), QueryUserType.USERID);
		if (null != resultUser
				&& resultUser.getPassword().equals(
						StringUtils.encodeByMD5(oldpwd))) {
			User user = new User();
			user.setUserId(userId);
			user.setPassword(StringUtils.encodeByMD5(newpwd));
			this.userMapper.updateSelective(user);
		} else {
			throw new BusinessException("你输入的旧密码错误，修改密码失败");
		}
	}

	@Override
	public User findUser(String value, QueryUserType type)
			throws BusinessException {
		if (null == value) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		if (type == QueryUserType.EMAIL) {
			map.put("email", value);
		} else if (type == QueryUserType.USERNAME) {
			map.put("userName", value);
		} else if (type == QueryUserType.USERID) {
			map.put("userId", value);
		}
		User user = userMapper.selectBaseInfo(map);
		if (user == null) {
			throw new BusinessException("用户不存在");
		}
		return user;

	}

	public void setTheme(Map<String, String> map, SessionUser sessionUser)
			throws BusinessException {
		String theme = map.get("theme");
		if (!ThemeTypeEnums.TYPE0.getValue().equals(theme)
				&& !ThemeTypeEnums.TYPE1.getValue().equals(theme)) {
			throw new BusinessException("风格参数错误");
		}
		Integer userId = sessionUser.getUserId();
		User user = new User();
		user.setUserId(userId);
		user.setCenterTheme(theme);
		userMapper.updateSelective(user);
	}

	@Override
	public User updateUserInfo(Map<String, String> map, SessionUser sessionUser)
			throws BusinessException {

		Integer userId = sessionUser.getUserId();
		String age = map.get("age");
		String character = map.get("characters");
		String sex = map.get("sex");
		String address = map.get("address");
		String work = map.get("work");
		if (!StringUtils.isNumber(age) && StringUtils.isEmpty(age)) {
			throw new BusinessException("年龄必须是数字");
		}
		if (address != null
				&& address.trim().length() > MaxLengthEnums.MAXLENGTH50
						.getLength()) {
			throw new BusinessException("地址信息不能超过50字符");
		}
		if (work != null
				&& work.trim().length() > MaxLengthEnums.MAXLENGTH50
						.getLength()) {
			throw new BusinessException("工作信息不能超过50字符");
		}
		if (null != character
				&& character.trim().length() > MaxLengthEnums.MAXLENGTH150
						.getLength()) {
			throw new BusinessException("个性签名不能超过150字符");
		}
		User user = new User();
		user.setUserId(userId);
		user.setAge(age);
		user.setCharacters(character);
		user.setAddress(address);
		user.setSex(sex);
		user.setWork(work);
		userMapper.updateSelective(user);
		return user;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void deleteUserBatch(Map<String, String> map)
			throws BusinessException {
		String keyStr = map.get("key");
		String[] keys = keyStr.split(",");
		for (String key : keys) {
			map.put("userId", key);
			int count = this.userMapper.delete(map);
			if (count == 0) {
				throw new BusinessException("没有找到相应的记录!");
			}
		}
	}

	public User login(String value, String password) throws BusinessException {

		User user = null;
		if (value.contains("@")) {
			user = findUser(value, QueryUserType.EMAIL);
		} else {
			user = findUser(value, QueryUserType.USERNAME);
		}
		if (user != null && user.getPassword().equals(password)) {
			return user;
		} else {
			return null;
		}
	}

	public User queryUserBaseInfo(Integer userId) throws BusinessException {

		return this.userMapper.selectUserBaseInfo(userId);
	}

	// 获取发送邮件的域
	private String MailAdress(String email) throws Exception {

		String maillAdress = "www.ulewo.com";
		int start = email.indexOf("@");
		int end = email.indexOf(".");
		String web = email.substring(start + 1, end);
		if ("gmail".equalsIgnoreCase(web)) {
			maillAdress = "http://www.gmail.com";
		} else {
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

		String url = "http://ulewo.com/user/findPwd?account=" + email
				+ "&code=" + activationCode;
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

	public UlewoPaginationResult<User> findAllUsers(Map<String, String> map) {
		map.put("orderBy", "register_time desc");
		int page_no = 0;
		if (StringUtils.isNumber(map.get("page"))) {
			page_no = Integer.parseInt(map.get("page"));
		}
		int count = this.userMapper.selectBaseInfoCount(map);
		int pageSize = PageSize.SIZE20.getSize();
		if (StringUtils.isNumber(map.get("rows"))) {
			pageSize = Integer.parseInt(map.get("rows"));
		}
		SimplePage page = new SimplePage(page_no, count, pageSize);
		List<User> list = this.userMapper.selectBaseInfoList(map, page);
		UlewoPaginationResult<User> result = new UlewoPaginationResult<User>(
				page, list);
		return result;
	}

	public List<User> selectUsersByMark() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderBy", "mark desc");
		SimplePage page = new SimplePage(0, 50);
		List<User> list = userMapper.selectBaseInfoList(map, page);
		return list;
	}

	public void updateSelective(User user) {
		this.userMapper.updateSelective(user);
	}

	@Override
	public UserVo4Api api_login(Map<String, String> map) throws BusinessException {
		String account = map.get("userName");
		String password = map.get("password");
		if (StringUtils.isEmpty(account)) {
			throw new BusinessException("账号不能为空");
		} else if (StringUtils.isEmpty(password)) {
			throw new BusinessException("密码不能为空");
		} else {
			password = StringUtils.encodeByMD5(password);
			User user = null;
			if (account.contains("@")) {
				user = findUser(account, QueryUserType.EMAIL);
			} else {
				user = findUser(account, QueryUserType.USERNAME);
			}
			if (null == user || !user.getPassword().equals(password)) {
				throw new BusinessException("帐号或者密码错误");
			} else {
				UserVo4Api userVo = new UserVo4Api();
				userVo.setUserId(user.getUserId());
				userVo.setUserName(user.getUserName());
				userVo.setUserIcon(user.getUserIcon());
				userVo.setMark(user.getMark());
				userVo.setPrevisitTime(user.getShowPreVisitTime());
				userVo.setRegisterTime(user.getShowPreVisitTime());
				userVo.setMark(user.getMark());
				return userVo;
			}
		}
	}
}
