package com.ulewo.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jhlabs.image.ScaleFilter;
import com.ulewo.enums.QueryUserType;
import com.ulewo.enums.ResultCode;
import com.ulewo.exception.BusinessException;
import com.ulewo.model.Blog;
import com.ulewo.model.BlogCategory;
import com.ulewo.model.BlogComment;
import com.ulewo.model.Collection;
import com.ulewo.model.SessionUser;
import com.ulewo.model.User;
import com.ulewo.service.BlogCategoryService;
import com.ulewo.service.BlogCommentService;
import com.ulewo.service.BlogService;
import com.ulewo.service.CollectionService;
import com.ulewo.service.Log;
import com.ulewo.service.UserService;
import com.ulewo.util.Constant;
import com.ulewo.util.ErrorReport;
import com.ulewo.util.StringUtils;
import com.ulewo.util.UlewoPaginationResult;
import com.ulewo.vo.UserVo;

@Controller
@RequestMapping("/manage")
public class UserManageAction extends BaseAction {
	@Autowired
	private UserService userService;

	@Autowired
	private BlogCategoryService blogItemService;

	@Autowired
	private BlogService blogService;

	@Autowired
	private BlogCategoryService blogCategoryService;

	@Autowired
	private BlogCommentService blogCommentService;

	@Autowired
	private CollectionService collectionService;

	private static final int SMALL_WIDTH = 60;

	@Log
	Logger log;

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView userMain(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		try {
			mv.setViewName("/usermanage/main_real");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
		return mv;
	}

	@RequestMapping(value = "/dispatcher/{url}")
	public ModelAndView dispatcher(HttpSession session, @PathVariable String url) {
		ModelAndView mv = new ModelAndView();
		try {
			if (!StringUtils.isEmpty(url)) {
				url = url + "_real";
			} else {
				url = Constant.ERRORPAGE;
			}
			mv.setViewName("/usermanage/" + url);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
		return mv;
	}

	/**
	 * 获取用户信息
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/user_inf", method = RequestMethod.GET)
	public ModelAndView queryUserInfo(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		SessionUser user = (SessionUser) session.getAttribute("user");
		Integer userId = user.getUserId();
		try {
			User resultUser = userService.findUser(userId.toString(),
					QueryUserType.USERID);
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
				mv.setViewName("/usermanage/user_inf_real");
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

	@RequestMapping(value = "/queryTheme.action", method = RequestMethod.GET)
	public ModelAndView queryTheme(HttpSession session,
			HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		try {
			User user = userService.findUser(
					String.valueOf(this.getSessionUserId(session)),
					QueryUserType.USERID);
			mv.addObject("theme", user.getCenterTheme());
			mv.setViewName("/usermanage/center_setting_real");
			return mv;
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/setTheme.action", method = RequestMethod.POST)
	public Map<String, Object> setTheme(HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> map = builderParams(request, true);
			SessionUser sessionUser = (SessionUser) session
					.getAttribute("user");
			userService.setTheme(map, sessionUser);
			modelMap.put("result", ResultCode.SUCCESS.getCode());
			return modelMap;
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			modelMap.put("result", ResultCode.ERROR.getCode());
			modelMap.put("msg", e.getMessage());
			return modelMap;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("result", ResultCode.ERROR.getCode());
			modelMap.put("msg", "系统异常");
			return modelMap;
		}
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
	public Map<String, Object> saveUserInfo(HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> map = builderParams(request, true);
			SessionUser sessionUser = (SessionUser) session
					.getAttribute("user");
			User user = userService.updateUserInfo(map, sessionUser);
			modelMap.put("user", user);
			modelMap.put("result", ResultCode.SUCCESS.getCode());
			return modelMap;
		} catch (Exception e) {
			String errorMethod = "UserManageAction-->saveUserInfo()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			modelMap.put("result", ResultCode.ERROR.getCode());
			modelMap.put("msg", "系统异常");
			return modelMap;
		}
	}

	@RequestMapping(value = "/changepwd", method = RequestMethod.GET)
	public ModelAndView changepwd(HttpSession session) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("/usermanage/changepwd");
		return mv;
	}

	/**
	 * 修改密码
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save_pwd.action", method = RequestMethod.POST)
	public Map<String, Object> editPwd(HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> map = builderParams(request, true);
			SessionUser sessionUser = (SessionUser) session
					.getAttribute("user");
			userService.updatePassword(map, sessionUser);
			modelMap.put("result", ResultCode.SUCCESS.getCode());
			return modelMap;
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			modelMap.put("result", ResultCode.ERROR.getCode());
			modelMap.put("msg", e.getMessage());
			return modelMap;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("result", ResultCode.ERROR.getCode());
			modelMap.put("msg", "系统异常");
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
	public Map<String, Object> saveUserIcon(HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		String tempimg = request.getParameter("img");
		String x1 = request.getParameter("x1");
		String y1 = request.getParameter("y1");
		String width = request.getParameter("width");
		String height = request.getParameter("height");
		if (!StringUtils.isNumber(x1) || !StringUtils.isNumber(y1)
				|| !StringUtils.isNumber(width)
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
		Integer userId = sessionUser.getUserId();
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
			BufferedImage subimg = img.getSubimage(x1_int, y1_int, width_int,
					height_int);
			// 放大缩小图片
			ScaleFilter filter = new ScaleFilter(SMALL_WIDTH);
			BufferedImage okimg = filter.filter(subimg, null);
			// 将图片转为字节数组
			String okSrcPath = session.getServletContext().getRealPath("/")
					+ "upload/avatars/";
			File imagePathFile = new File(okSrcPath);
			if (!imagePathFile.exists()) {
				imagePathFile.mkdirs();
			}
			File okfile = new File(okSrcPath + sessionUser.getUserId() + "."
					+ imgType);
			ImageIO.write(okimg, imgType, okfile);
			userIcon = "avatars/" + sessionUser.getUserId() + "." + imgType;
			User user = new User();
			user.setUserId(userId);
			user.setUserIcon(userIcon);
			sessionUser.setUserIcon(userIcon);
			userService.updateSelective(user);
			modelMap.put("result", ResultCode.SUCCESS.getCode());
			modelMap.put("userIcon", userIcon);
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", ResultCode.ERROR.getCode());
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
	}

	@RequestMapping(value = "/new_blog")
	public ModelAndView newblog(HttpSession session) {

		ModelAndView mv = new ModelAndView();
		Integer userId = ((SessionUser) session.getAttribute("user"))
				.getUserId();
		List<BlogCategory> itemList = blogItemService
				.selectCategoryWithBlogCount(userId);
		mv.addObject("categorys", itemList);
		mv.setViewName("usermanage/new_blog_real");
		return mv;

	}

	/**
	 * 查询博客
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/loadBlog.action")
	public Map<String, Object> loadBlog(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SessionUser sessionUser = this.getSessionUser(session);
			Map<String, String> param = this.builderParams(request, true);
			param.put("userId", String.valueOf(sessionUser.getUserId()));
			UlewoPaginationResult<Blog> result = this.blogService
					.queryBlogByUserId(param);
			map.put("rows", result.getList());
			map.put("total", result.getPage().getCountTotal());
			return map;
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return map;
	}

	/**
	 * 更新博文
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edit_blog.action")
	public ModelAndView editBlog(HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		try {
			Map<String, String> map = this.builderParams(request, true);
			int userId = this.getSessionUserId(session);
			map.put("userId", String.valueOf(userId));
			Blog blog = blogService.showBlogById(map);
			List<BlogCategory> categoryList = blogCategoryService
					.selectCategoryWithBlogCount(userId);
			mv.addObject("blog", blog);
			mv.addObject("categorys", categoryList);
			mv.setViewName("usermanage/edit_blog_real");
			return mv;
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}

	}

	/**
	 * 保存博客
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveBlog.action")
	public Map<String, Object> saveBlog(HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> map = builderParams(request, false);
			Blog blog = this.blogService.saveBlog(map,
					this.getSessionUser(session));
			modelMap.put("result", ResultCode.SUCCESS.getCode());
			modelMap.put("blog", blog);
			return modelMap;
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			modelMap.put("result", ResultCode.ERROR.getCode());
			modelMap.put("msg", e.getMessage());
			return modelMap;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("result", ResultCode.ERROR.getCode());
			modelMap.put("msg", "系统异常");
			return modelMap;
		}
	}

	/**
	 * 修改博客
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateBlog.action")
	public Map<String, Object> updateBlog(HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> map = builderParams(request, false);
			Blog blog = this.blogService.saveBlog(map,
					this.getSessionUser(session));
			modelMap.put("result", ResultCode.SUCCESS.getCode());
			modelMap.put("blog", blog);
			return modelMap;
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			modelMap.put("result", ResultCode.ERROR.getCode());
			modelMap.put("msg", e.getMessage());
			return modelMap;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("result", ResultCode.ERROR.getCode());
			modelMap.put("msg", "系统异常");
			return modelMap;
		}
	}

	/**
	 * 删除博文
	 * 
	 * @param session
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "/deleteBlog.action")
	public Map<String, Object> deleteBlog(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> parms = builderParams(request, true);
			this.blogService.deleteBlogBatch(parms,
					this.getSessionUser(session));
			modelMap.put("result", ResultCode.SUCCESS.getCode());
			return modelMap;
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			modelMap.put("result", ResultCode.ERROR.getCode());
			modelMap.put("msg", e.getMessage());
			return modelMap;
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("result", ResultCode.ERROR.getCode());
			modelMap.put("msg", "系统异常");
			return modelMap;
		}
	}

	/**
	 * 加载评论
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/loadComment.action")
	public Map<String, Object> replyList(HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			map.put("userId", String.valueOf(this.getSessionUserId(session)));
			UlewoPaginationResult<BlogComment> listResult = this.blogCommentService
					.queryBlogCommentByBlogId(map);
			result.put("rows", listResult.getList());
			result.put("total", listResult.getPage().getCountTotal());
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			result.put("msg", e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 删除评论
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteComent.action")
	public Map<String, Object> deleteComent(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, String> param = this.builderParams(request, true);
			this.blogCommentService.deleteBatch(param,
					this.getSessionUserId(session));
			result.put("result", ResultCode.SUCCESS.getCode());
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 加载分类
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/loadCategory.action")
	public Map<String, Object> loadCategory(HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");
		List<BlogCategory> categoryList = blogCategoryService
				.selectCategoryWithBlogCount(sessionUser.getUserId());
		modelMap.put("rows", categoryList);
		modelMap.put("total", categoryList.size());
		return modelMap;
	}

	@ResponseBody
	@RequestMapping(value = "/saveCategory.action")
	public Map<String, Object> editItem(HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			this.blogCategoryService.saveCategory(
					this.builderParams(request, true),
					this.getSessionUserId(session));
			modelMap.put("result", ResultCode.SUCCESS.getCode());
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			modelMap.put("msg", e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("msg", "系统异常!");
		}
		return modelMap;
	}

	@ResponseBody
	@RequestMapping(value = "/queryFavoriteArticle.action")
	public Map<String, Object> queryFavoriteArticle(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			UlewoPaginationResult<Collection> result = this.collectionService
					.queryCollectionByUserId(map, this.getSessionUser(session));
			modelMap.put("rows", result.getList());
			modelMap.put("total", result.getPage().getCountTotal());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return modelMap;
	}

	@ResponseBody
	@RequestMapping(value = "/deleteFavoriteArticle.action")
	public Map<String, Object> deleteFavoriteArticle(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			this.collectionService.deleteCollection(map,
					this.getSessionUser(session));
			modelMap.put("result", ResultCode.SUCCESS.getCode());
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			modelMap.put("msg", e.getMessage());
		} catch (Exception e) {
			modelMap.put("msg", "系统异常");
			log.error(e.getMessage(), e);
		}
		return modelMap;
	}
}