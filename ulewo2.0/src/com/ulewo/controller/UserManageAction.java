package com.ulewo.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jhlabs.image.ScaleFilter;
import com.ulewo.entity.BlogArticle;
import com.ulewo.entity.BlogItem;
import com.ulewo.entity.Notice;
import com.ulewo.entity.SessionUser;
import com.ulewo.entity.User;
import com.ulewo.enums.QueryUserType;
import com.ulewo.service.BlogArticleService;
import com.ulewo.service.BlogItemService;
import com.ulewo.service.BlogReplyService;
import com.ulewo.service.FavoriteService;
import com.ulewo.service.NoticeService;
import com.ulewo.service.UserService;
import com.ulewo.util.Constant;
import com.ulewo.util.ErrorReport;
import com.ulewo.util.PaginationResult;
import com.ulewo.util.StringUtils;
import com.ulewo.vo.UserVo;

@Controller
@RequestMapping("/manage")
public class UserManageAction {
	@Autowired
	private UserService userService;

	@Autowired
	private BlogItemService blogItemService;

	@Autowired
	private BlogArticleService blogArticleService;

	@Autowired
	private BlogReplyService blogReplyService;

	@Autowired
	private NoticeService noticeService;

	@Autowired
	private FavoriteService favoriteService;

	private final static int CHARACTER_LENGTH = 200;

	private final static int ADDRESS_LENGTH = 50, WORK_LENGTH = 50, ITEM_LENGTH = 50;

	private final static int PWD_MIN_LENGTH = 6;

	private final static int PWD_MAX_LENGTH = 16;

	private final static String SEX_M = "M";

	private final static String SEX_F = "F";

	private static final int SMALL_WIDTH = 60, SMALL_HEIGHT = 60;

	/**
	 * 获取用户信息
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/userinfo", method = RequestMethod.GET)
	public ModelAndView queryUserInfo(HttpSession session) {

		SessionUser user = (SessionUser) session.getAttribute("user");
		String userId = user.getUserId();
		ModelAndView mv = new ModelAndView();
		try {
			User resultUser = userService.findUser(userId, QueryUserType.USERID);
			if (null != resultUser) {
				UserVo userVo = new UserVo();
				userVo.setUserId(resultUser.getUserId());
				userVo.setUserName(resultUser.getUserName());
				userVo.setEmail(resultUser.getEmail());
				userVo.setAddress(resultUser.getAddress());
				userVo.setAge(resultUser.getAge());
				userVo.setCharacters(resultUser.getCharacters());
				userVo.setSex(resultUser.getSex());
				userVo.setWork(resultUser.getWork());
				mv.addObject("userVo", userVo);
				mv.setViewName("/usermanage/userinfo");
			}
		} catch (Exception e) {
			String errorMethod = "UserManageAction-->queryUserInfo()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
		return mv;
	}

	/**
	 * 更新用户信息
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveUserInfo.action", method = RequestMethod.POST)
	public Map<String, Object> saveUserInfo(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			SessionUser sessionUser = (SessionUser) session.getAttribute("user");
			String userId = sessionUser.getUserId();
			String age = request.getParameter("age");
			String character = request.getParameter("characters");
			String sex = request.getParameter("sex");
			String address = request.getParameter("address");
			String work = request.getParameter("work");
			if (!StringUtils.isNumber(age) && StringUtils.isEmpty(age)) {
				modelMap.put("result", "fail");
				modelMap.put("message", "年龄必须是数字");
				return modelMap;
			}
			if (address != null && address.trim().length() > ADDRESS_LENGTH) {
				modelMap.put("result", "fail");
				modelMap.put("message", "地址信息不能超过50字符");
				return modelMap;
			}
			if (work != null && work.trim().length() > WORK_LENGTH) {
				modelMap.put("result", "fail");
				modelMap.put("message", "工作信息不能超过50字符");
				return modelMap;
			}
			if (null != character && character.trim().length() > CHARACTER_LENGTH) {
				modelMap.put("result", "fail");
				modelMap.put("message", "个性签名不能超过150字符");
				return modelMap;
			}
			User user = new User();
			user.setUserId(userId);
			user.setAge(age);
			user.setCharacters(character);
			user.setAddress(address);
			user.setSex(sex);
			user.setWork(work);
			userService.updateUser(user);
			modelMap.put("result", "success");
			return modelMap;
		} catch (Exception e) {
			String errorMethod = "UserManageAction-->saveUserInfo()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			modelMap.put("result", "fail");
			modelMap.put("message", "系统异常，请稍候重试");
			return modelMap;
		}
	}

	@RequestMapping(value = "/changepwd", method = RequestMethod.GET)
	public ModelAndView changepwd(HttpSession session) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("/usermanage/changepwd");
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/save_pwd.action", method = RequestMethod.POST)
	public Map<String, Object> editPwd(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		String oldpwd = request.getParameter("oldpwd");
		String newpwd = request.getParameter("newpwd");
		String checkPassWord = "^[0-9a-zA-Z]+$";
		try {
			SessionUser sessionUser = (SessionUser) session.getAttribute("user");
			String userId = sessionUser.getUserId();
			if (!oldpwd.matches(checkPassWord) || StringUtils.isEmpty(oldpwd) || oldpwd.length() < PWD_MIN_LENGTH
					|| oldpwd.length() > PWD_MAX_LENGTH) {
				modelMap.put("message", "旧密码不符合规范");
				modelMap.put("result", "fail");
				return modelMap;
			}

			if (!newpwd.matches(checkPassWord) || StringUtils.isEmpty(newpwd) || newpwd.length() < PWD_MIN_LENGTH
					|| newpwd.length() > PWD_MAX_LENGTH) {
				modelMap.put("message", "新密码不符合规范");
				modelMap.put("result", "fail");
				return modelMap;
			}
			User resultUser = userService.findUser(userId, QueryUserType.USERID);
			if (null != resultUser && resultUser.getPassword().equals(StringUtils.encodeByMD5(oldpwd))) {
				User user = new User();
				user.setUserId(userId);
				user.setPassword(StringUtils.encodeByMD5(newpwd));
				userService.updateUser(user);
				modelMap.put("result", "success");
				return modelMap;
			}
			modelMap.put("message", "你输入的旧密码错误，修改密码失败");
			modelMap.put("result", "fail");
			return modelMap;

		} catch (Exception e) {
			String errorMethod = "UserManageAction-->editPwd()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			modelMap.put("result", "fail");
			modelMap.put("message", "系统异常，请稍候重试");
			return modelMap;
		}
	}

	@RequestMapping(value = "/user_icon")
	public ModelAndView userIcon(HttpSession session) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("usermanage/usericon");
		return mv;

	}

	@ResponseBody
	@RequestMapping(value = "/saveUserIcon.action")
	public Map<String, Object> saveUserIcon(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		String tempimg = request.getParameter("img");
		String x1 = request.getParameter("x1");
		String y1 = request.getParameter("y1");
		String width = request.getParameter("width");
		String height = request.getParameter("height");
		if (!StringUtils.isNumber(x1) || !StringUtils.isNumber(y1) || !StringUtils.isNumber(width)
				|| !StringUtils.isNumber(height)) {
			modelMap.put("result", "fail");
			modelMap.put("message", "请求参数错误");
			return modelMap;
		}
		int x1_int = Integer.parseInt(x1);
		int y1_int = Integer.parseInt(y1);
		int width_int = Integer.parseInt(width);
		int height_int = Integer.parseInt(height);
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");
		String userId = sessionUser.getUserId();
		String userIcon = "";
		InputStream tempIn = null;
		ByteArrayOutputStream out = null;
		OutputStream imgOut = null;
		String imgType = "jpg";
		if (tempimg != null && !"".equals(tempimg)) {
			int idx = tempimg.lastIndexOf(".");
			if (idx >= 0) {
				imgType = tempimg.substring(idx + 1);
			}
		}
		String srcpath = session.getServletContext().getRealPath("/") + tempimg;
		try {
			BufferedImage img = ImageIO.read(new File(srcpath));
			// 裁剪图片
			BufferedImage subimg = img.getSubimage(x1_int, y1_int, width_int, height_int);
			//ScaleFilter filter = new ScaleFilter(SMALL_WIDTH,SMALL_HEIGHT);
			// 放大缩小图片
			ScaleFilter filter = new ScaleFilter(SMALL_WIDTH,SMALL_WIDTH);
			BufferedImage okimg = filter.filter(subimg, null);
			// 将图片转为字节数组
			String okSrcPath = session.getServletContext().getRealPath("/") + "upload/avatars/";
			File imagePathFile = new File(okSrcPath);
			if (!imagePathFile.exists()) {
				imagePathFile.mkdirs();
			}
			File okfile = new File(okSrcPath + sessionUser.getUserId() + "." + imgType);
			ImageIO.write(okimg, imgType, okfile);
			userIcon = "avatars/" + sessionUser.getUserId() + "." + imgType;
			User user = new User();
			user.setUserId(userId);
			user.setUserLittleIcon(userIcon);
			sessionUser.setUserLittleIcon(userIcon);
			userService.updateUser(user);
		} catch (Exception e) {
			String errorMethod = "UserManageAction-->saveUserIcon()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			modelMap.put("result", "fail");
			modelMap.put("message", "系统异常");
			return modelMap;
		} finally {
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
		modelMap.put("result", "success");
		modelMap.put("userIcon", userIcon);
		return modelMap;
	}

	@RequestMapping(value = "/new_blog")
	public ModelAndView newblog(HttpSession session) {

		ModelAndView mv = new ModelAndView();
		String userId = ((SessionUser) session.getAttribute("user")).getUserId();
		List<BlogItem> itemList = blogItemService.queryBlogItemByUserId(userId);
		mv.addObject("itemList", itemList);
		mv.setViewName("usermanage/new_blog");
		return mv;

	}

	@RequestMapping(value = "/edit_blog.action")
	public ModelAndView editBlog(HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		String id = request.getParameter("id");
		if (!StringUtils.isNumber(id)) {
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
		}
		String userId = ((SessionUser) session.getAttribute("user")).getUserId();
		int id_int = Integer.parseInt(id);
		List<BlogItem> itemList = blogItemService.queryBlogItemByUserId(userId);
		BlogArticle blog = blogArticleService.queryBlogById(id_int);
		if (!userId.equals(blog.getUserId())) {
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
		}
		mv.addObject("blog", blog);
		mv.addObject("itemList", itemList);
		mv.setViewName("usermanage/edit_blog");
		return mv;

	}

	@ResponseBody
	@RequestMapping(value = "/saveblog.action", method = RequestMethod.POST)
	public Map<String, Object> saveBlog(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String userId = ((SessionUser) session.getAttribute("user")).getUserId();
			String title = request.getParameter("title");
			String itemId = request.getParameter("itemId");
			String keyword = request.getParameter("keyword");
			String content = request.getParameter("content");
			String[] images = request.getParameterValues("image");
			String id = request.getParameter("id");
			int id_int = 0;
			if (StringUtils.isNotEmpty(id)) {
				if (!StringUtils.isNumber(id)) {
					modelMap.put("result", "fail");
					modelMap.put("message", "参数错误");
				}
				else {
					id_int = Integer.parseInt(id);
				}

			}
			int itemId_int = 0;
			if (StringUtils.isNumber(itemId)) {
				itemId_int = Integer.parseInt(itemId);
			}
			BlogArticle article = new BlogArticle();
			article.setId(id_int);
			article.setTitle(title);
			article.setItemId(itemId_int);
			article.setKeyWord(keyword);
			article.setContent(content);
			article.setUserId(userId);

			if (images != null) {
				article.setImage(images[0]);
				String allImage = "";
				for (int i = 0, length = images.length; i < length; i++) {
					if (i == (length - 1)) {
						allImage += images[i];
					}
					else {
						allImage += images[i] + "|";
					}
				}
				article.setAllImage(allImage);
			}
			blogArticleService.saveBlog(article);
			return modelMap;
		} catch (Exception e) {
			String errorMethod = "UserManageAction-->saveBlog()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			modelMap.put("result", "fail");
			modelMap.put("message", "系统异常，请稍候重试");
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/deleteBlog.action", method = RequestMethod.GET)
	public Map<String, Object> deleteBlog(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			SessionUser sessionUser = (SessionUser) session.getAttribute("user");
			String id = request.getParameter("id");
			int id_int = 0;
			if (!StringUtils.isNumber(id)) {
				modelMap.put("result", "fail");
				modelMap.put("message", "参数错误");
			}
			id_int = Integer.parseInt(id);
			BlogArticle blogArticle = blogArticleService.queryBlogById(id_int);
			if (blogArticle == null) {
				modelMap.put("result", "fail");
				modelMap.put("message", "删除失败");
			}
			if (sessionUser.getUserId().equals(blogArticle.getUserId())) {
				blogArticleService.deleteArticle(id_int);
				modelMap.put("result", "success");
			}
			return modelMap;
		} catch (Exception e) {
			String errorMethod = "UserManageAction-->deleteBlog()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			modelMap.put("result", "fail");
			modelMap.put("message", "系统异常，请稍候重试");
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/loadItem", method = RequestMethod.POST)
	public Map<String, Object> loadItem(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			SessionUser sessionUser = (SessionUser) session.getAttribute("user");
			String userId = sessionUser.getUserId();
			List<BlogItem> itemList = blogItemService.queryBlogItemByUserId(userId);
			modelMap.put("items", itemList);
			return modelMap;
		} catch (Exception e) {
			String errorMethod = "UserManageAction-->loadItem()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			modelMap.put("result", "fail");
			modelMap.put("message", "系统异常，请稍候重试");
			return modelMap;
		}
	}

	@RequestMapping(value = "/blog_item", method = RequestMethod.GET)
	public ModelAndView blogItem(HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		try {
			SessionUser sessionUser = (SessionUser) session.getAttribute("user");
			String userId = sessionUser.getUserId();
			List<BlogItem> itemList = blogItemService.queryBlogItemAndCountByUserId(userId);
			mv.addObject("imtes", itemList);
			mv.setViewName("usermanage/blog_item");
			return mv;
		} catch (Exception e) {
			String errorMethod = "UserManageAction-->blogItem()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/edit_item", method = RequestMethod.POST)
	public Map<String, Object> editItem(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		String id = request.getParameter("id");
		String itemName = request.getParameter("itemName");
		String range = request.getParameter("range");
		try {
			if (StringUtils.isEmpty(id) || !StringUtils.isNumber(id)) {
				modelMap.put("result", "fail");
				modelMap.put("message", "参数错误");
				return modelMap;
			}
			if (StringUtils.isEmpty(itemName) || itemName.length() > ITEM_LENGTH) {
				modelMap.put("result", "fail");
				modelMap.put("message", "分类名不能超过50字符");
				return modelMap;
			}
			if (StringUtils.isNumber(range)) {
				modelMap.put("result", "fail");
				modelMap.put("message", "排序必须是数字");
				return modelMap;
			}
			SessionUser sessionUser = (SessionUser) session.getAttribute("user");
			String userId = sessionUser.getUserId();
			int id_int = Integer.parseInt(id);
			int range_int = Integer.parseInt("range");
			BlogItem item = new BlogItem();
			item.setId(id_int);
			item.setItemName(itemName);
			item.setUserId(userId);
			item.setItemRang(range_int);
			blogItemService.updateItem(item);
			modelMap.put("result", "success");
			return modelMap;
		} catch (Exception e) {
			String errorMethod = "UserManageAction-->editItem()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			modelMap.put("result", "fail");
			modelMap.put("message", "系统异常，请稍候重试");
			return modelMap;
		}
	}

	@RequestMapping(value = "/saveItem.action", method = RequestMethod.POST)
	public ModelAndView saveItem(HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		try {
			String itemName = request.getParameter("itemName");
			String range = request.getParameter("itemCode");
			String id = request.getParameter("id");
			if (null != itemName) {
				itemName = itemName.trim();
			}
			if (null != range) {
				range = range.trim();
			}
			if (null != id) {
				id = id.trim();
			}
			if (StringUtils.isEmpty(itemName) || itemName.length() > ITEM_LENGTH) {
				mv.setViewName("redirect:" + Constant.ERRORPAGE);
				return mv;
			}
			if (!StringUtils.isNumber(range)) {
				mv.setViewName("redirect:" + Constant.ERRORPAGE);
				return mv;
			}
			SessionUser sessionUser = (SessionUser) session.getAttribute("user");
			String userId = sessionUser.getUserId();
			int range_int = Integer.parseInt(range);

			int id_int = 0;
			if (StringUtils.isNotEmpty(id)) {
				id_int = Integer.parseInt(id);
			}

			BlogItem item = new BlogItem();
			item.setId(id_int);
			item.setItemName(itemName);
			item.setUserId(userId);
			item.setItemRang(range_int);
			blogItemService.saveItem(item);
			mv.setViewName("redirect:blog_item");
			return mv;
		} catch (Exception e) {
			String errorMethod = "UserManageAction-->saveItem()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
	}

	/**
	 * 删除分类
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete_item", method = RequestMethod.GET)
	public ModelAndView deleteItem(HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		try {
			String id = request.getParameter("id");
			if (StringUtils.isEmpty(id) || !StringUtils.isNumber(id)) {
				mv.setViewName("redirect:" + Constant.ERRORPAGE);
				return mv;
			}
			SessionUser sessionUser = (SessionUser) session.getAttribute("user");
			String userId = sessionUser.getUserId();
			int id_int = Integer.parseInt(id);
			blogItemService.delete(userId, id_int);
			mv.setViewName("redirect:blog_item");
			return mv;
		} catch (Exception e) {
			String errorMethod = "UserManageAction-->deleteItem()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
	}

	/**
	 * 博客评论管理
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/blog_reply", method = RequestMethod.GET)
	public ModelAndView replyList(HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		int page_int = 0;
		String page = request.getParameter("page");
		if (null != page && StringUtils.isNumber(page)) {
			page_int = Integer.parseInt(page);
		}
		try {
			SessionUser sessionUser = (SessionUser) session.getAttribute("user");
			String userId = sessionUser.getUserId();
			PaginationResult pagResult = blogReplyService.queryAllReply(userId, page_int, Constant.pageSize20);
			mv.addObject("replyList", pagResult);
			mv.setViewName("usermanage/blog_reply");
			return mv;
		} catch (Exception e) {
			String errorMethod = "UserManageAction-->replyList()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
	}

	/**
	 * 删除评论
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete_reply.action", method = RequestMethod.GET)
	public ModelAndView deleteReply(HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		int id_int = 0;
		String page = request.getParameter("id");
		if (null != page && StringUtils.isNumber(page)) {
			id_int = Integer.parseInt(page);
		}
		try {
			SessionUser sessionUser = (SessionUser) session.getAttribute("user");
			String userId = sessionUser.getUserId();
			if (blogReplyService.delete(userId, id_int)) {
				mv.setViewName("redirect:blog_reply");
			}
			else {
				mv.setViewName("redirect:" + Constant.ERRORPAGE);
			}
			return mv;
		} catch (Exception e) {
			String errorMethod = "UserManageAction-->deleteReply()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
	}

	@RequestMapping(value = "/notice", method = RequestMethod.GET)
	public ModelAndView notice(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		try {
			String userId = ((SessionUser) session.getAttribute("user")).getUserId();
			List<Notice> list = noticeService.queryNoticeByUserId(userId, Constant.STATUSYN);
			mv.addObject("list", list);
			mv.setViewName("usermanage/notice");
		} catch (Exception e) {
			String errorMethod = "UserManageAction-->notice()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
		return mv;
	}

	@RequestMapping(value = "/readNotice", method = RequestMethod.POST)
	public ModelAndView deleteNotice(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		try {
			String ids[] = request.getParameterValues("ids");
			if (ids == null) {
				mv.setViewName("redirect:" + Constant.ERRORPAGE);
			}
			for (int i = 0; i < ids.length; i++) {
				int id = Integer.parseInt(ids[i]);
				noticeService.deleteNotice(id);
			}
			mv.setViewName("redirect:notice");
		} catch (Exception e) {
			String errorMethod = "UserManageAction-->deleteNotice()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
		return mv;
	}

	@RequestMapping(value = "/noticeDetail", method = RequestMethod.GET)
	public ModelAndView noticeDetail(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		try {
			String noticeId = request.getParameter("id");
			if (!StringUtils.isNumber(noticeId)) {
				mv.setViewName("redirect:" + Constant.ERRORPAGE);
				return mv;
			}
			int noticeId_int = Integer.parseInt(noticeId);
			Notice notice = noticeService.getNotice(noticeId_int);
			String url = notice.getUrl();
			noticeService.deleteNotice(noticeId_int);
			mv.setViewName("redirect:" + url);
		} catch (Exception e) {
			String errorMethod = "UserManageAction-->noticeDetail()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/noticeCount", method = RequestMethod.GET)
	public Map<String, Object> noticeCount(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String userId = ((SessionUser) session.getAttribute("user")).getUserId();
			int count = noticeService.queryNoticeCountByUserId(userId, Constant.STATUSYN);
			modelMap.put("count", count);
			return modelMap;
		} catch (Exception e) {
			String errorMethod = "UserManageAction-->noticeCount()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	@RequestMapping(value = "/favorite", method = RequestMethod.GET)
	public ModelAndView favorite(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("usermanage/favorite");
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/queryFavoriteArticle.action", method = RequestMethod.GET)
	public Map<String, Object> queryFavoriteArticle(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String page = request.getParameter("page");
			if (!StringUtils.isNumber(page)) {
				modelMap.put("result", "fail");
				modelMap.put("message", "参数错误");
				return modelMap;
			}
			int page_int = Integer.parseInt(page);
			String type = request.getParameter("type");
			if (StringUtils.isEmpty(type)) {
				modelMap.put("result", "fail");
				modelMap.put("message", "参数错误");
				return modelMap;
			}
			String userId = ((SessionUser) session.getAttribute("user")).getUserId();
			PaginationResult data = favoriteService.queryFavoriteByUserIdInPage(userId, type, page_int, 2);
			modelMap.put("data", data);
			modelMap.put("result", "success");
			return modelMap;
		} catch (Exception e) {
			String errorMethod = "UserAction-->queryFavoriteArticle()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			//thread.start();
			modelMap.put("result", "fail");
			modelMap.put("message", "系统异常");
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/deleteFavoriteArticle.action", method = RequestMethod.GET)
	public Map<String, Object> deleteFavoriteArticle(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String articleId = request.getParameter("articleId");
			if (!StringUtils.isNumber(articleId)) {
				modelMap.put("result", "fail");
				modelMap.put("message", "参数错误");
				return modelMap;
			}
			int articleId_int = Integer.parseInt(articleId);
			String type = request.getParameter("type");
			if (StringUtils.isEmpty(type)) {
				modelMap.put("result", "fail");
				modelMap.put("message", "参数错误");
				return modelMap;
			}
			String userId = ((SessionUser) session.getAttribute("user")).getUserId();
			favoriteService.deleteFavorite(articleId_int, type, userId);
			modelMap.put("result", "success");
			return modelMap;
		} catch (Exception e) {
			String errorMethod = "UserAction-->deleteFavoriteArticle()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			//	thread.start();
			modelMap.put("result", "fail");
			modelMap.put("message", "系统异常");
			return modelMap;
		}
	}
}
