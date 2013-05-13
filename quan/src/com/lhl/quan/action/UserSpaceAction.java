package com.lhl.quan.action;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.lhl.common.action.BaseAction;
import com.lhl.config.ErrMsgConfig;
import com.lhl.entity.Article;
import com.lhl.entity.BlogArticle;
import com.lhl.entity.Group;
import com.lhl.entity.Message;
import com.lhl.entity.User;
import com.lhl.exception.BaseException;
import com.lhl.quan.service.ArticleService;
import com.lhl.quan.service.BlogArticleService;
import com.lhl.quan.service.GroupService;
import com.lhl.quan.service.MessageService;
import com.lhl.quan.service.UserService;
import com.lhl.util.Constant;
import com.lhl.util.Pagination;
import com.lhl.util.SendMail;
import com.lhl.util.Tools;
import com.lhl.vo.UserVo;

public class UserSpaceAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	private static final int MAXLENGTH = 500;

	private final SimpleDateFormat formate = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private static final int SMALL_WIDTH = 60;

	private static final int SMALL_HEIGHT = 60;

	private UserService userService;

	private MessageService messageService;

	private GroupService groupService;

	private ArticleService articleService;

	private BlogArticleService blogArticleService;

	private int id;

	private String email;

	private String userId;

	private String userName;

	private String atUserId;

	private String atUserName;

	private String passWord;

	private String checkCode;

	private String autoLogin;

	private String message;

	private String maillAdress;

	private String account;

	private String code = "";

	private String sex;

	private String age;

	private String work;

	private String address;

	private String characters;

	private String content;

	private String oldPwd;

	private String newPwd;

	private UserVo userVo = new UserVo();

	private String userIcon;

	private int page; // 页数

	private int pageTotal; // 总页数

	private String errMsg;

	private String quote;

	private int x1;

	private int y1;

	private int width;

	private int height;

	private int imgtype;

	private List<Message> messageList = new ArrayList<Message>();

	private List<Group> createGroups = new ArrayList<Group>();

	private List<Group> joinGroups = new ArrayList<Group>();

	private List<Article> articleList = new ArrayList<Article>();

	private List<BlogArticle> blogList = new ArrayList<BlogArticle>();

	private String reUserName;

	public void checkUserName() {

		String result = "{\"result\":\"Y\",\"msg\":\"恭喜你，用户名可以使用\"}";
		try {
			if (Tools.isNotEmpty(userName)) {
				if (null != userService.checkUserName(userName)) {
					result = "{\"result\":\"N\",\"msg\":\"抱歉，昵称已经被使用\"}";
				}
			} else {
				result = "{\"result\":\"N\",\"msg\":\"用户名不能为空\"}";
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = "{\"result\":\"N\",\"msg\":\"抱歉，系统异常\"}";
		}
		getOut().write(result);
	}

	public void checkMail() {

		String result = "{\"result\":\"Y\",\"msg\":\"恭喜你，邮箱可以使用\"}";
		try {
			if (Tools.isNotEmpty(email)) {
				if (null != userService.checkEmail(email)) {
					result = "{\"result\":\"N\",\"msg\":\"抱歉，邮箱已经被使用\"}";
				}
			} else {
				result = "{\"result\":\"N\",\"msg\":\"邮箱不能为空\"}";
			}

		} catch (Exception e) {
			result = "{\"result\":\"N\",\"msg\":\"抱歉，系统异常\"}";
		}
		getOut().write(result);
	}

	/**
	 * description: 用户注册
	 * 
	 * @return
	 */
	public String register() {

		String sessionCcode = (String) getSession().getAttribute("checkCode");
		/*
		 * var checkUserId = /^(?!\d)\w+$/; //只能是数字，字母，下划线，不能以数字开头; var
		 * checkUserName = /^(?!\d)[\w\u4e00-\u9fa5]+$/; //只能是数字，字母，下划线，中文。 var
		 * checkPassWord = /^[0-9a-zA-Z]+$/; //只能是数字，字母 var checkEmail =
		 * /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;// 验证email
		 */
		String checkEmail = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";
		String checkUserName = "^[\\w\\u4e00-\\u9fa5]+$";
		String checkPassWord = "^[0-9a-zA-Z]+$";
		try {
			if (Tools.isEmpty(checkCode)) {
				message = "验证码错误";
			} else if (Tools.isEmpty(sessionCcode)
					|| !sessionCcode.equalsIgnoreCase(checkCode)) {
				message = "验证码错误";
			} else if (!email.matches(checkEmail) || Tools.isEmpty(email)) {
				message = "邮箱地址不符合规范";
			} else if (!userName.matches(checkUserName)
					|| Tools.isEmpty(userName)
					|| Tools.getRealLength(userName) < 1
					|| Tools.getRealLength(userName) > 20) {
				message = "昵称不符合规范";
			} else if (!passWord.matches(checkPassWord)
					|| Tools.isEmpty(passWord) || passWord.length() < 6
					|| passWord.length() > 16) {
				message = "密码不符合规范";
			} else if (null != userService.checkEmail(email)) {// 后台检测邮箱是否唯一
				message = "邮箱已经被占用";
			} else if (null != userService.checkUserName(userName)) { // 后台检测用户昵称是否唯一
				message = "用户名已经被占用";
			} else {
				User user = new User();
				user.setUserName(userName);
				user.setPassword(Tools.encodeByMD5(passWord));
				user.setEmail(email);
				userId = userService.register(user);
				if (null != userId) {
					// 保存Cookie
					String infor = URLEncoder.encode(userName, "utf-8") + ","
							+ passWord;

					// 清除之前的Cookie 信息
					Cookie cookie = new Cookie("cookieInfo", null);
					cookie.setPath("/");
					cookie.setMaxAge(0);

					// 建用户信息保存到Cookie中
					Cookie cookieInfo = new Cookie("cookieInfo", infor);
					cookieInfo.setPath("/");
					// 设置最大生命周期为1年。
					cookieInfo.setMaxAge(31536000);
					getResponse().addCookie(cookieInfo);

					User loginUser = new User();
					loginUser.setUserId(userId);
					loginUser.setUserName(userName);
					loginUser.setUserLittleIcon(user.getUserLittleIcon());
					getSession().setAttribute("user", loginUser);
					return SUCCESS;
				} else {
					message = "系统异常，请稍后再试";
					return INPUT;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "系统异常，请稍后再试";
		}
		return INPUT;
	}

	/**
	 * 登录
	 */
	public void login() {

		String result = "{\"result\":\"success\",\"msg\":\"登录成功\"}";
		String sessionCcode = (String) getSession().getAttribute("checkCode");
		try {
			if (Tools.isEmpty(checkCode)) {
				result = "{\"result\":\"error\",\"msg\":\"验证码不能为空\"}";
			} else if (Tools.isEmpty(sessionCcode)
					|| !sessionCcode.equalsIgnoreCase(checkCode)) {
				result = "{\"result\":\"error\",\"msg\":\"验证码错误\"}";
			} else if (Tools.isEmpty(userName)) {
				result = "{\"result\":\"error\",\"msg\":\"帐号不能为空\"}";
			} else if (Tools.isEmpty(passWord)) {
				result = "{\"result\":\"error\",\"msg\":\"密码不能为空\"}";
			} else {
				User user = userService.login(userName);
				if (user != null) {
					// 用户名，密码匹配 登录成功
					if (Tools.encodeByMD5(passWord).equals(user.getPassword())) {
						// 是否自动登录
						if ("Y".equals(autoLogin)) {
							// 自动登录，保存用户名密码到 Cookie
							String infor = URLEncoder.encode(userName, "utf-8")
									+ "," + passWord;

							// 清除之前的Cookie 信息
							Cookie cookie = new Cookie("cookieInfo", null);
							cookie.setPath("/");
							cookie.setMaxAge(0);

							// 建用户信息保存到Cookie中
							Cookie cookieInfo = new Cookie("cookieInfo", infor);
							cookieInfo.setPath("/");
							// 设置最大生命周期为1年。
							cookieInfo.setMaxAge(31536000);
							getResponse().addCookie(cookieInfo);
						} else {
							Cookie cookie = new Cookie("cookieInfo", null);
							cookie.setPath("/");
							cookie.setMaxAge(0);
						}
						// 设置session信息
						User loginUser = new User();
						loginUser.setUserId(user.getUserId());
						loginUser.setUserName(user.getUserName());
						loginUser.setUserLittleIcon(user.getUserLittleIcon());
						getSession().setAttribute("user", loginUser);
						// 更新最后登录时间
						user.setPrevisitTime(formate.format(new Date()));
						userService.updateUserSelective(loginUser);
					}
					// 密码错误
					else {
						result = "{\"result\":\"error\",\"msg\":\"密码错误\"}";
					}
				} // 用户不存在
				else {
					result = "{\"result\":\"error\",\"msg\":\"用户不存在\"}";
				}
			}
		} catch (Exception e) {
			result = "{\"result\":\"error\",\"msg\":\"抱歉，系统异常\"}";
		}
		getOut().write(result);
	}

	// 用户注销
	public void logout() {

		String message = "ok";
		try {
			Cookie cookie = new Cookie("cookieInfo", null);
			cookie.setPath("/");
			cookie.setMaxAge(0);
			getResponse().addCookie(cookie);
			getSession().invalidate();
		} catch (Exception e) {
			message = "error";
		}
		JSONObject obj = new JSONObject();
		obj.put("msg", message);
		getOut().print(String.valueOf(obj));
	}

	private Cookie getCookieByName(HttpServletRequest request, String name) {

		Map<String, Cookie> cookieMap = ReadCookieMap(request);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = (Cookie) cookieMap.get(name);
			return cookie;
		} else {
			return null;
		}
	}

	private Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {

		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}

	/**
	 * 检查找回密码邮箱，发送邮件
	 */
	public String showFetch() {

		String sessionCcode = (String) getSession().getAttribute("checkCode");

		try {
			if (Tools.isEmpty(email)) {
				message = "邮箱不能为空";
				return INPUT;
			} else if (Tools.isEmpty(checkCode)) {
				message = "验证码不能为空";
				return INPUT;
			} else if (Tools.isEmpty(sessionCcode)
					|| !sessionCcode.equalsIgnoreCase(checkCode)) {
				message = "验证码错误";
				return INPUT;
			} else if (null == userService.checkEmail(email)) {// 如果邮箱存在
				message = "邮箱不存在";
				return INPUT;
			} else {
				String activationCode = createCode();
				// 更新用户激活码
				User user = new User();
				user.setEmail(email);
				user.setActivationCode(activationCode);
				userService.updateUserSelective(user);
				// 发送邮件
				sendMile(email, activationCode);
				maillAdress = MailAdress(email);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	// 生成状态码
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

		String url = "http://www.ulewo.com/user/findPwd.jspx?account=" + email
				+ "&code=" + activationCode;
		String title = "ulewo邮箱找回密码邮件";
		StringBuffer content = new StringBuffer("亲爱的" + email + "<br><br>");
		content.append("欢迎使用ulewo找回密码功能。(www.ulewo.com)!<br><br>");
		content.append("请点击链接重置密码：<br><br>");
		content.append("<a href=\"" + url + "\">" + url + "</a><br><br>");
		content.append("如果你的email程序不支持链接点击，请将上面的地址拷贝至你的浏览器(如IE)的地址栏进入。<br><br>");
		content.append("您的注册邮箱是:" + email + "<br><br>");
		content.append("希望你在有乐窝社区的体验有益和愉快！<br><br>");
		content.append("- 有乐窝社区(www.ulewo.com)");
		String[] address = new String[] { email };
		SendMail.sendEmail(title, String.valueOf(content), address);
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

	// 验证发送到邮箱的链接
	public String checkFindPwd() {

		if (Tools.isEmpty(account) || Tools.isEmpty(code)) {
			return ERROR;
		}
		try {
			User user = userService.checkEmail(account);
			if (null == user || !code.equals(user.getActivationCode())) {
				return ERROR;
			}
			return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}
	}

	public void resetPasswordOuter() {

		String result = "ok";

		if (Tools.isEmpty(account) || Tools.isEmpty(code)
				|| Tools.isEmpty(newPwd)) {
			result = "rpwerror";
		}
		try {
			User user = userService.checkEmail(account);
			if (null == user || !code.equals(user.getActivationCode())) {
				result = "rpwerror";
			} else {
				user.setPassword(Tools.encodeByMD5(newPwd));
				userService.updateUserSelective(user);
			}
		} catch (Exception e) {
			result = "error";
		}
		JSONObject obj = new JSONObject();
		obj.put("result", result);
		getOut().print(String.valueOf(obj));
	}

	// 检查邮箱链接是否正确
	public String checkEmail() {

		if (Tools.isEmpty(account) || Tools.isEmpty(code)) {
			return ERROR;
		}
		String sessionCcode = (String) getSession().getAttribute("checkCode");
		try {
			if (Tools.isEmpty(passWord)) {
				message = "邮箱不能为空";
				return INPUT;
			} else if (Tools.isEmpty(checkCode)) {
				message = "验证码不能为空";
				return INPUT;
			} else if (Tools.isEmpty(sessionCcode)
					|| !sessionCcode.equalsIgnoreCase(checkCode)) {
				message = "验证码错误";
				return INPUT;
			}

			User user = userService.checkEmail(account);
			if (null != user && code.equals(user.getActivationCode())) {
				user.setPassword(Tools.encodeByMD5(passWord));
				userService.updateUserSelective(user);
				return SUCCESS;
			} else {
				return ERROR;
			}

		} catch (Exception e) {
			return SUCCESS;
		}
	}

	/**
	 * 
	 * description: 获取用户信息
	 * 
	 * @return
	 * @author luohl
	 */
	public String userInfo() {

		if (Tools.isEmpty(userId)) {
			return ERROR;
		}
		try {
			User userInfo = userService.getUserInfo(userId);
			if (null == userInfo) {
				return ERROR;
			}
			userVo.setUserName(userInfo.getUserName());
			userVo.setWork(userInfo.getWork());
			userVo.setAddress(userInfo.getAddress());
			userVo.setAge(userInfo.getAge());
			userVo.setCharacters(userInfo.getCharacters());
			userVo.setRegisterTime(userInfo.getRegisterTime());
			userVo.setSex(userInfo.getSex());
			userVo.setPrevisitTime(userInfo.getPrevisitTime());
			userVo.setMark(userInfo.getMark());
			messageList = messageService.queryMessage(userId, 0, 5);
			blogList = blogArticleService.queryBlogByUserIdOrItem(userId, 0, 0,
					10);
		} catch (BaseException e) {
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public void message() {

		String msg = "ok";
		JSONObject obj = new JSONObject();
		if (Tools.isEmpty(userId)) {
			msg = "error";
		}
		try {
			int countNumber = messageService.getCount(userId);
			Pagination.setPageSize(10);
			int pageSize = Pagination.getPageSize();
			pageTotal = Pagination.getPageTotal(countNumber);
			if (page > pageTotal) {
				page = pageTotal;
			}
			if (page < 1) {
				page = 1;
			}
			int noStart = (page - 1) * pageSize;
			List<Message> list = messageService.queryMessage(userId, noStart,
					pageSize);
			obj.put("totalPage", pageTotal);
			obj.put("page", page);
			obj.put("list", list);
		} catch (Exception e) {
			msg = "error";
			e.printStackTrace();
		}
		obj.put("msg", msg);
		PrintWriter out = getOut();
		out.println(String.valueOf(obj));
	}

	public void getUserInfoAjax() throws IOException {

		String msg = "ok";
		if (Tools.isEmpty(userId)) {
			msg = "error";
		}
		UserVo userBaesInfo = new UserVo();
		try {
			User userInfo = userService.getUserInfo(userId);
			if (null == userInfo) {
				msg = "error";
			}
			userBaesInfo.setUserId(userId);
			userBaesInfo.setUserLittleIcon(userInfo.getUserLittleIcon());
			userBaesInfo.setUserName(userInfo.getUserName());
			userBaesInfo.setCharacters(userInfo.getCharacters());
		} catch (Exception e) {
			msg = "error";
		}
		JSONObject obj = new JSONObject();
		obj.put("result", msg);
		obj.put("userBaesInfo", userBaesInfo);
		PrintWriter out = getOut();
		out.println(String.valueOf(obj));
	}

	public void addMessage() {

		String msg = "";
		String result = "success";
		JSONObject obj = new JSONObject();
		try {
			// 检测用户名
			Object sessionObj = getSession().getAttribute("user");
			if (sessionObj == null) {
				result = "fail";
				msg = "请先登录再发帖";
				obj.put("msg", msg);
				obj.put("result", result);
				getOut().print(String.valueOf(obj));
				return;
			}
			/*
			 * String sessionCcode = (String)
			 * getSession().getAttribute("checkCode"); if
			 * (Tools.isEmpty(checkCode) ||
			 * !checkCode.equalsIgnoreCase(sessionCcode)) { result = "fail"; msg
			 * = "验证码错误"; obj.put("msg", msg); obj.put("result", result);
			 * getOut().print(String.valueOf(obj)); return; }
			 */
			if (Tools.isEmpty(content) || content.length() > MAXLENGTH) {
				result = "fail";
				msg = "输入内容为空或者超过长度";
				obj.put("msg", msg);
				obj.put("result", result);
				getOut().print(String.valueOf(obj));
				return;
			}

			Message message = new Message();
			User sessionUser = (User) sessionObj;
			message.setReUserId(sessionUser.getUserId());
			message.setReUserName(sessionUser.getUserName());
			message.setReUserIcon(sessionUser.getUserLittleIcon());
			message.setMessage(Tools.formateHtml(content));
			message.setUserId(userId);
			message.setAtUserId(atUserId);
			message.setAtUserName(atUserName);
			Message resultMsg = messageService.addMessage(message);
			message.setPostTime(resultMsg.getPostTime());
			message.setId(resultMsg.getId());

			obj.put("note", message);
			obj.put("result", result);
			getOut().print(String.valueOf(obj));
		} catch (Exception e) {
			msg = "error";
			obj.put("msg", msg);
			getOut().print(String.valueOf(obj));
		}
	}

	public void deleteMessage() {

		User sessionUser = getSessionUser();
		String msg = "ok";
		JSONObject obj = new JSONObject();
		try {
			if (sessionUser == null) {
				msg = "noperm";
			} else {
				if (!messageService.deletMessage(sessionUser.getUserId(), id)) {
					msg = "noperm";
				}
			}
		} catch (Exception e) {
			msg = "error";
		}
		obj.put("msg", msg);
		getOut().print(String.valueOf(obj));
	}

	/**
	 * 
	 * description: 获取用户信息
	 * 
	 * @return
	 * @author luohl
	 */
	public String getInfo() {

		try {
			User sessionUser = getSessionUser();
			User userInfo = userService.getUserInfo(sessionUser.getUserId());
			if (null == userInfo) {
				return ERROR;
			}
			userVo.setUserId(userId);
			userVo.setUserName(userInfo.getUserName());
			userVo.setWork(userInfo.getWork());
			userVo.setAddress(userInfo.getAddress());
			userVo.setAge(userInfo.getAge());
			userVo.setCharacters(userInfo.getCharacters());
			userVo.setRegisterTime(userInfo.getRegisterTime());
			userVo.setPrevisitTime(userInfo.getPrevisitTime());
			userVo.setSex(userInfo.getSex());
		} catch (BaseException e) {
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 
	 * description: 更新用户信息
	 * 
	 * @return
	 * @author luohl
	 * @throws IOException
	 */
	public void updateUserInfo() {

		JSONObject obj = new JSONObject();
		String result = "ok";
		try {
			User sesionUser = getSessionUser();
			userId = sesionUser.getUserId();
			User updateUser = new User();
			updateUser.setUserId(userId);
			updateUser.setSex(sex);
			updateUser.setAge(age);
			updateUser.setWork(work);
			updateUser.setAddress(address);
			updateUser.setCharacters(characters);
			userService.updateInfo(updateUser);
		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
		}

		obj.put("result", result);
		getOut().println(String.valueOf(obj));
	}

	public String userIcon() {

		try {
			User sessionUser = getSessionUser();
			User userInfo = userService.getUserInfo(sessionUser.getUserId());
			if (null == userInfo) {
				return ERROR;
			}
		} catch (BaseException e) {
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	// 更新用户头像
	public void updateUserIcon() {

		String result = "ok";
		try {
			User sessionUser = getSessionUser();
			if (imgtype == 0) {
				userIcon = cutImg();
			}
			sessionUser.setUserLittleIcon(userIcon);
			sessionUser.setUserBigIcon(userIcon);
			userService.updateUserSelective(sessionUser);
			result = userIcon;
		} catch (Exception e) {
			System.out.print(e);
			result = "error";
		}
		JSONObject obj = new JSONObject();
		obj.put("result", result);
		getOut().print(String.valueOf(obj));
	}

	private String cutImg() {

		String resultPath = "";
		InputStream tempIn = null;
		ByteArrayOutputStream out = null;
		OutputStream imgOut = null;
		String imgType = "jpg";
		if (userIcon != null && !"".equals(userIcon)) {
			int idx = userIcon.lastIndexOf(".");
			if (idx >= 0) {
				imgType = userIcon.substring(idx + 1);
			}
		}
		String srcpath = ServletActionContext.getServletContext().getRealPath(
				"/")
				+ userIcon;
		try {
			User sessionUser = getSessionUser();
			File tempfile = new File(srcpath);
			tempIn = new FileInputStream(tempfile);
			BufferedImage img = ImageIO.read(tempIn);
			// 裁剪图片
			BufferedImage subimg = img.getSubimage(x1, y1, width, height);
			// 放大缩小图片
			BufferedImage okimg = new BufferedImage(SMALL_WIDTH, SMALL_HEIGHT,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = okimg.createGraphics();
			g.drawImage(subimg, 0, 0, SMALL_WIDTH, SMALL_HEIGHT, null);

			// 将图片转为字节数组
			out = new ByteArrayOutputStream();
			ImageIO.write(okimg, imgType, out);
			byte[] data = out.toByteArray();
			String okSrcPath = ServletActionContext.getServletContext()
					.getRealPath("/") + "upload/avatars/";
			File imagePathFile = new File(okSrcPath);
			if (!imagePathFile.exists()) {
				imagePathFile.mkdirs();
			}
			File okfile = new File(okSrcPath + sessionUser.getUserId() + "."
					+ imgType);
			imgOut = new FileOutputStream(okfile);
			imgOut.write(data);
			imgOut.flush();
			resultPath = "avatars/" + sessionUser.getUserId() + "." + imgType;
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			try {
				if (null != tempIn) {
					tempIn.close();
					tempIn = null;
				}
				if (null != out) {
					out.close();
					out = null;
				}
				if (imgOut != null) {
					imgOut.close();
				}
			} catch (Exception e) {
			}
			new File(srcpath).delete();
		}
		return resultPath;
	}

	public String getRealyPath(String path) {

		return ServletActionContext.getServletContext().getRealPath(path);
	}

	public String changePassword() {

		try {
			User sessionUser = getSessionUser();
			User userInfo = userService.getUserInfo(sessionUser.getUserId());
			if (null == userInfo) {
				return ERROR;
			}
		} catch (BaseException e) {
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 
	 * description: 修改密码
	 * 
	 * @return
	 * @author luohl
	 */
	public void resetPassword() {

		String result = "ok";
		User sessionUser = getSessionUser();
		User userInfo;
		try {
			userInfo = userService.getUserInfo(sessionUser.getUserId());
			if (userInfo != null) {
				if (Tools.encodeByMD5(oldPwd).equals(userInfo.getPassword())) {
					userInfo.setPassword(Tools.encodeByMD5(newPwd));
					userService.updateUserSelective(userInfo);
				} else {
					result = "pwdError";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject obj = new JSONObject();
		obj.put("result", result);
		getOut().print(String.valueOf(obj));
	}

	/**
	 * 
	 * description: 创建的群组
	 * 
	 * @return
	 * @author luohl
	 */
	public String createdGroups() {

		try {
			createGroups = groupService.queryCreatedGroups(userId);
		} catch (BaseException e) {
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		} catch (Exception e) {
			errMsg = ErrMsgConfig.getErrMsg(10000);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String joinedGroups() {

		try {
			joinGroups = groupService.queryJoinedGroups(userId);
		} catch (BaseException e) {
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		} catch (Exception e) {
			errMsg = ErrMsgConfig.getErrMsg(10000);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * description: 发表的文章
	 * 
	 * @return
	 * @author luohl
	 */
	public String postTopics() {

		try {
			int countNumber = articleService.queryPostTopicCount(userId);
			Pagination.setPageSize(Constant.pageSize20);
			int pageSize = Pagination.getPageSize();
			pageTotal = Pagination.getPageTotal(countNumber);
			if (page > pageTotal) {
				page = pageTotal;
			}
			if (page < 1) {
				page = 1;
			}
			int noStart = (page - 1) * pageSize;
			articleList = articleService.queryPostTopic(userId, noStart,
					pageSize);
		} catch (BaseException e) {
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		} catch (Exception e) {
			errMsg = ErrMsgConfig.getErrMsg(10000);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * description: 回复的文章
	 * 
	 * @return
	 * @author luohl
	 */
	public String reTopics() {

		try {
			int countNumber = articleService.queryReTopicCount(userId);
			Pagination.setPageSize(Constant.pageSize20);
			int pageSize = Pagination.getPageSize();
			pageTotal = Pagination.getPageTotal(countNumber);
			if (page > pageTotal) {
				page = pageTotal;
			}
			if (page < 1) {
				page = 1;
			}
			int noStart = (page - 1) * pageSize;
			articleList = articleService
					.queryReTopic(userId, noStart, pageSize);
		} catch (BaseException e) {
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		} catch (Exception e) {
			errMsg = ErrMsgConfig.getErrMsg(10000);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * description: 创建群
	 * 
	 * @return
	 * @author luohl
	 */
	public String createGroup() {

		return null;
	}

	public void setEmail(String email) {

		this.email = email;
	}

	public void setUserName(String userName) {

		this.userName = userName;
	}

	public void setPassWord(String passWord) {

		this.passWord = passWord;
	}

	public void setCheckCode(String checkCode) {

		this.checkCode = checkCode;
	}

	public void setUserService(UserService userService) {

		this.userService = userService;
	}

	public void setMessageService(MessageService messageService) {

		this.messageService = messageService;
	}

	public String getMessage() {

		return message;
	}

	public void setAutoLogin(String autoLogin) {

		this.autoLogin = autoLogin;
	}

	public String getMaillAdress() {

		return maillAdress;
	}

	public String getAccount() {

		return account;
	}

	public void setAccount(String account) {

		this.account = account;
	}

	public String getCode() {

		return code;
	}

	public void setCode(String code) {

		this.code = code;
	}

	public String getEmail() {

		return email;
	}

	public void setMessage(String message) {

		this.message = message;
	}

	public String getUserId() {

		return userId;
	}

	public void setUserId(String userId) {

		this.userId = userId;
	}

	public void setSex(String sex) {

		this.sex = sex;
	}

	public void setAge(String age) {

		this.age = age;
	}

	public void setWork(String work) {

		this.work = work;
	}

	public void setAddress(String address) {

		this.address = address;
	}

	public void setCharacters(String characters) {

		this.characters = characters;
	}

	public List<Message> getMessageList() {

		return messageList;
	}

	public void setContent(String content) {

		this.content = content;
	}

	public List<Group> getCreateGroups() {

		return createGroups;
	}

	public List<Group> getJoinGroups() {

		return joinGroups;
	}

	public List<Article> getArticleList() {

		return articleList;
	}

	public int getPage() {

		return page;
	}

	public void setPage(int page) {

		this.page = page;
	}

	public int getPageTotal() {

		return pageTotal;
	}

	public void setGroupService(GroupService groupService) {

		this.groupService = groupService;
	}

	public void setArticleService(ArticleService articleService) {

		this.articleService = articleService;
	}

	public String getErrMsg() {

		return errMsg;
	}

	public void setReUserName(String reUserName) {

		this.reUserName = reUserName;
	}

	public void setUserIcon(String userIcon) {

		this.userIcon = userIcon;
	}

	public void setX1(int x1) {

		this.x1 = x1;
	}

	public void setY1(int y1) {

		this.y1 = y1;
	}

	public void setWidth(int width) {

		this.width = width;
	}

	public void setHeight(int height) {

		this.height = height;
	}

	public void setImgtype(int imgtype) {

		this.imgtype = imgtype;
	}

	public UserVo getUserVo() {

		return userVo;
	}

	public List<BlogArticle> getBlogList() {

		return blogList;
	}

	public void setBlogArticleService(BlogArticleService blogArticleService) {

		this.blogArticleService = blogArticleService;
	}

	public void setOldPwd(String oldPwd) {

		this.oldPwd = oldPwd;
	}

	public void setNewPwd(String newPwd) {

		this.newPwd = newPwd;
	}

	public void setQuote(String quote) {

		this.quote = quote;
	}

	public void setId(int id) {

		this.id = id;
	}

	public void setAtUserId(String atUserId) {
		this.atUserId = atUserId;
	}

	public void setAtUserName(String atUserName) {
		this.atUserName = atUserName;
	}

}
