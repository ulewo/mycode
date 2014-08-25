package com.ulewo.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ulewo.enums.ResultCode;
import com.ulewo.enums.SourceFromEnums;
import com.ulewo.exception.BusinessException;
import com.ulewo.model.Calendar4Signin;
import com.ulewo.model.DaySignInfo;
import com.ulewo.model.SessionUser;
import com.ulewo.service.BlastCommentService;
import com.ulewo.service.BlastService;
import com.ulewo.service.BlogCommentService;
import com.ulewo.service.BlogService;
import com.ulewo.service.CollectionService;
import com.ulewo.service.GroupService;
import com.ulewo.service.LikeService;
import com.ulewo.service.Log;
import com.ulewo.service.SignInService;
import com.ulewo.service.TopicCommentService;
import com.ulewo.service.TopicService;
import com.ulewo.service.UserService;
import com.ulewo.util.ScaleFilter2;
import com.ulewo.util.StringUtils;
import com.ulewo.vo.UserVo4Api;

@Controller
@RequestMapping("/api")
public class ApiAction extends BaseAction {
	@Autowired
	GroupService groupService;

	@Autowired
	SignInService signInService;

	@Autowired
	TopicService topicService;

	@Autowired
	BlogService blogService;

	@Autowired
	BlogCommentService blogCommentService;

	@Autowired
	TopicCommentService topicCommentService;

	@Autowired
	BlastService blastService;

	@Autowired
	BlastCommentService blastCommentService;

	@Autowired
	LikeService likeService;

	@Autowired
	CollectionService collectionService;

	@Autowired
	UserService userService;

	@Log
	Logger log;

	private final static int MAXSIZE = 2 * 1024, MAXWIDTH = 1000;

	/**
	 * 今日签到情况
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/signin_list")
	public Map<String, Object> signinList(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = signInService.api_queryCurDaySigin(this.builderParams(
					request, false));
			result.put("mySignIn",
					signInService.api_signInInfo(this.getSessionUser(session)));
			result.put("resultCode", ResultCode.SUCCESS.getCode());
			return result;
		} catch (Exception e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "服务器异常!");
			return result;
		}
	}

	// 签到
	@ResponseBody
	@RequestMapping(value = "/signin_do.action")
	public Map<String, Object> reMark(HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			modelMap = this.signInService.doSignIn(
					this.getSessionUser(session), "A");
			modelMap.put("resultCode", ResultCode.SUCCESS.getCode());
			return modelMap;
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			modelMap.put("resultCode", ResultCode.ERROR.getCode());
			modelMap.put("msg", e.getMessage());
			return modelMap;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("resultCode", ResultCode.ERROR.getCode());
			modelMap.put("msg", "服务器异常");
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/user_signins.action")
	public Map<String, Object> queryUserSigin4Api(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, String> param = this.builderParams(request, true);
			String maxYear = StringUtils.dateFormater3.format(new Date());
			param.put("maxYear", maxYear);
			List<DaySignInfo> list = signInService.queryUserSigin4Api(
					this.getSessionUser(session), param);
			result.put("list", list);
			result.put("resultCode", ResultCode.SUCCESS.getCode());
			return result;
		} catch (BusinessException e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", e.getMessage());
			return result;
		} catch (Exception e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "服务器异常!");
			return result;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/login")
	public Map<String, Object> login(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, String> parms = builderParams(request, true);
			// 所有群组
			UserVo4Api userVo = userService.api_login(parms);
			result.put("user", userVo);
			result.put("resultCode", ResultCode.SUCCESS.getCode());
			SessionUser sessionUser = new SessionUser();
			sessionUser.setUserId(userVo.getUserId());
			sessionUser.setUserName(userVo.getUserName());
			sessionUser.setUserIcon(userVo.getUserIcon());
			session.setAttribute("user", sessionUser);

			String infor = URLEncoder.encode(parms.get("userName").toString()
					+ "," + parms.get("password"), "utf-8");

			// 清除之前的Cookie 信息
			Cookie cookie = new Cookie("cookieInfo", null);
			cookie.setPath("/");
			cookie.setMaxAge(0);

			// 建用户信息保存到Cookie中
			Cookie cookieInfo = new Cookie("cookieInfo", infor);
			cookieInfo.setPath("/");
			// 设置最大生命周期为1年。
			cookieInfo.setMaxAge(31536000);
			Cookie sessionId = new Cookie("JSESSIONID", session.getId());
			response.addCookie(sessionId);
			response.addCookie(cookieInfo);
			return result;
		} catch (BusinessException e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", e.getMessage());
			return result;
		} catch (Exception e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "服务器异常!");
			return result;
		}
	}

	/**
	 * 最新吐槽
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/blast_list_latest")
	public Map<String, Object> blastListLatest(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = blastService.apiQueryAllBlast(this.builderParams(request,
					false));
			result.put("resultCode", ResultCode.SUCCESS.getCode());
			return result;
		} catch (BusinessException e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", e.getMessage());
			return result;
		} catch (Exception e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "服务器异常!");
			return result;
		}
	}

	/**
	 * 我的吐槽
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/blast_list_mine.action")
	public Map<String, Object> blastListMine(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 所有群组
			result = blastService.apiQueryMyBlast(
					this.builderParams(request, false),
					this.getSessionUserId(session));
			result.put("resultCode", ResultCode.SUCCESS.getCode());
			return result;
		} catch (BusinessException e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", e.getMessage());
			return result;
		} catch (Exception e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "服务器异常!");
			return result;
		}
	}

	/**
	 * 新增吐槽
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/blast_post.action")
	public Map<String, Object> blastPost(HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> parms = builderParams(request, false);
			uploadImage(session, request, parms, false);
			String blastId = parms.get("blastId");
			if (StringUtils.isEmpty(blastId)) {
				parms.put("sourceFrom", SourceFromEnums.Android.getValue());
				this.blastService.addBlast(parms, this.getSessionUser(session),
						request);
			} else {
				this.blastCommentService.saveBlastComment(parms,
						this.getSessionUserId(session));
			}
			modelMap.put("resultCode", ResultCode.SUCCESS.getCode());
			return modelMap;
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			modelMap.put("resultCode", ResultCode.ERROR.getCode());
			modelMap.put("msg", e.getMessage());
			return modelMap;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("resultCode", ResultCode.ERROR.getCode());
			modelMap.put("msg", "系统异常");
			return modelMap;
		}
	}

	/**
	 * 吐槽评论
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/blast_commens")
	public Map<String, Object> blastComments(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 所有群组
			result = blastCommentService.queryBlastCommentByPag4Api(this
					.builderParams(request, false));
			result.put("resultCode", ResultCode.SUCCESS.getCode());
			return result;
		} catch (BusinessException e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", e.getMessage());
			return result;
		} catch (Exception e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "服务器异常!");
			return result;
		}
	}

	/**
	 * 新增吐槽评论
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save_blast_comment.action")
	public Map<String, Object> saveBlastComment(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			blastCommentService.saveBlastComment(
					this.builderParams(request, false),
					this.getSessionUserId(session));
			result.put("resultCode", ResultCode.SUCCESS.getCode());
			return result;
		} catch (BusinessException e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", e.getMessage());
			return result;
		} catch (Exception e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "服务器异常!");
			return result;
		}
	}

	/**
	 * 推荐的窝窝
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/commend_groups")
	public Map<String, Object> commendGroups(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = groupService.findCommendGroups4Api(this.builderParams(
					request, false));
			result.put("resultCode", ResultCode.SUCCESS.getCode());
			return result;
		} catch (Exception e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "服务器异常!");
			return result;
		}
	}

	/**
	 * 加入的窝窝
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/joined_groups.action")
	public Map<String, Object> joinedGroups(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = groupService.findJoinedGroups4Api(
					this.builderParams(request, false),
					this.getSessionUserId(session));
			result.put("resultCode", ResultCode.SUCCESS.getCode());
			return result;
		} catch (Exception e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "服务器异常!");
			return result;
		}
	}

	/**
	 * 创建的窝窝
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/created_groups.action")
	public Map<String, Object> createdGroups(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = groupService.findCreatedGroups4Api(
					this.builderParams(request, false),
					this.getSessionUserId(session));
			result.put("resultCode", ResultCode.SUCCESS.getCode());
			return result;
		} catch (Exception e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "服务器异常!");
			return result;
		}
	}

	/**
	 * 群组文章
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/group_topics")
	public Map<String, Object> groupTopics(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = topicService.findTopics4Api(this.builderParams(request,
					false));
			result.put("resultCode", ResultCode.SUCCESS.getCode());
			return result;
		} catch (Exception e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "服务器异常!");
			return result;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/topic_detail")
	public Map<String, Object> showTopic(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = topicService.showTopic4Api(
					this.builderParams(request, false),
					this.getSessionUserId(session));
			result.put("resultCode", ResultCode.SUCCESS.getCode());
			return result;
		} catch (BusinessException e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", e.getMessage());
			return result;
		} catch (Exception e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "服务器异常!");
			return result;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/topic_post.action")
	public Map<String, Object> saveTopic(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, String> parms = this.builderParams(request, false);
			if (StringUtils.isEmpty(parms.get("topicId"))) {
				uploadImage(session, request, parms, true);
				topicService.saveTopic4Api(parms, this.getSessionUser(session),
						request);
			} else {
				topicCommentService.addComment(parms,
						this.getSessionUser(session));
			}
			result.put("resultCode", ResultCode.SUCCESS.getCode());
			return result;
		} catch (BusinessException e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", e.getMessage());
			return result;
		} catch (Exception e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "服务器异常!");
			return result;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/topic_comments")
	public Map<String, Object> topicComments(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = topicCommentService.queryCommentByTopicId4Api(this
					.builderParams(request, false));
			result.put("resultCode", ResultCode.SUCCESS.getCode());
			return result;
		} catch (Exception e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "服务器异常!");
			return result;
		}
	}

	/**
	 * 所有文章
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/latestTopics")
	public Map<String, Object> latestTopics(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, false);
			map.put("orderBy", "create_time desc");
			result = topicService.findTopics4Api(map);
			result.put("resultCode", ResultCode.SUCCESS.getCode());
			return result;
		} catch (Exception e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "服务器异常!");
			return result;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/blogs_latest")
	public Map<String, Object> latestBlogs(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, false);
			map.put("orderBy", "create_time desc");
			result = blogService.queryLatestBlog4Api(map);
			result.put("resultCode", ResultCode.SUCCESS.getCode());
			return result;
		} catch (Exception e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "服务器异常!");
			return result;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/blogs_mine.action")
	public Map<String, Object> mineBlogs(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, false);
			result = blogService.queryBlogByUserId4Api(map,
					this.getSessionUserId(session));
			result.put("resultCode", ResultCode.SUCCESS.getCode());
			return result;
		} catch (Exception e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "服务器异常!");
			return result;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/blog_detail")
	public Map<String, Object> blogDetail(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, false);
			result = blogService.blogDetail4Api(map,
					this.getSessionUserId(session));
			result.put("resultCode", ResultCode.SUCCESS.getCode());
			return result;
		} catch (Exception e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "服务器异常!");
			return result;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/blog_comments")
	public Map<String, Object> blogComments(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, false);
			result = blogCommentService.queryBlogCommentByBlogId4Api(map);
			result.put("resultCode", ResultCode.SUCCESS.getCode());
			return result;
		} catch (Exception e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "服务器异常!");
			return result;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/blog_post.action")
	public Map<String, Object> blogPost(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, false);
			SessionUser sessionUser = this.getSessionUser(session);
			if (StringUtils.isEmpty(map.get("blogId"))) {
				uploadImage(session, request, map, true);
				blogService.saveBlog(map, sessionUser, request);
			} else {
				blogCommentService.addBlogComment(map, sessionUser);
			}
			result.put("resultCode", ResultCode.SUCCESS.getCode());
			return result;
		} catch (BusinessException e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", e.getMessage());
			return result;
		} catch (Exception e) {
			log.error(e.getMessage());
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "服务器异常!");
			return result;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/dolike.action")
	public Map<String, Object> dolike(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			likeService.doLike(this.builderParams(request, false),
					this.getSessionUser(session));
			modelMap.put("resultCode", ResultCode.SUCCESS.getCode());
			return modelMap;
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			modelMap.put("resultCode", ResultCode.ERROR.getCode());
			modelMap.put("msg", e.getMessage());
			return modelMap;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("resultCode", ResultCode.ERROR.getCode());
			modelMap.put("msg", "系统异常");
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/doCollection.action")
	public Map<String, Object> doCollection(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> map = builderParams(request, true);
			this.collectionService.addCollection(map,
					this.getSessionUser(session));
			modelMap.put("resultCode", ResultCode.SUCCESS.getCode());
			return modelMap;
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			modelMap.put("resultCode", ResultCode.ERROR.getCode());
			modelMap.put("msg", e.getMessage());
			return modelMap;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("resultCode", ResultCode.ERROR.getCode());
			modelMap.put("msg", "系统异常");
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getUserInfo")
	public Map<String, Object> getUserInfo(HttpSession session,
			HttpServletRequest request, String userId) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			modelMap = this.userService.queryUserInfo4Api(userId);
			modelMap.put("resultCode", ResultCode.SUCCESS.getCode());
			return modelMap;
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			modelMap.put("resultCode", ResultCode.ERROR.getCode());
			modelMap.put("msg", e.getMessage());
			return modelMap;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("resultCode", ResultCode.ERROR.getCode());
			modelMap.put("msg", "系统异常");
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/loadMySignIn.action", method = RequestMethod.POST)
	public Map<String, Object> loadMySignIn(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, String> param = this.builderParams(request, true);
			String maxYear = StringUtils.dateFormater3.format(new Date());
			param.put("maxYear", maxYear);
			List<Calendar4Signin> list = signInService.queryUserSigin(
					this.getSessionUser(session), param);
			resultMap.put("maxYear", maxYear);
			resultMap.put("list", list);
			resultMap.put("year", param.get("year"));
			resultMap.put("result", ResultCode.SUCCESS.getCode());
			return resultMap;
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			resultMap.put("result", ResultCode.ERROR.getCode());
			resultMap.put("msg", e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			resultMap.put("result", ResultCode.ERROR.getCode());
			resultMap.put("msg", "系统异常");
		}
		return resultMap;
	}

	/**
	 * 图片上传
	 * 
	 * @param session
	 * @param request
	 * @param parms
	 * @param saveFullPath
	 *            TODO
	 * @throws Exception
	 */
	private void uploadImage(HttpSession session, HttpServletRequest request,
			Map<String, String> parms, boolean saveFullPath) throws Exception {
		String realPath = session.getServletContext().getRealPath("/");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<MultipartFile> multipartFiles = multipartRequest.getFiles("file");
		String port = request.getServerPort() == 80 ? "" : ":"
				+ request.getServerPort();
		String hostPath = "http://" + request.getServerName() + port
				+ request.getContextPath();
		if (multipartFiles != null) {
			for (MultipartFile multipartFile : multipartFiles) {
				long size = multipartFile.getSize();
				if (size > 0) {
					String fileName = multipartFile.getOriginalFilename();
					String suffix = fileName.substring(fileName
							.lastIndexOf(".") + 1);
					String current = String.valueOf(System.currentTimeMillis());
					fileName = current + "." + suffix;
					SimpleDateFormat formater = new SimpleDateFormat("yyyyMM");
					String saveDir = formater.format(new Date());
					String savePath = hostPath + "/upload/" + saveDir + "/"
							+ fileName;

					if (!saveFullPath) {
						savePath = saveDir + "/" + fileName;
					}

					String fileDir = realPath + "upload" + "/" + saveDir;
					File dir = new File(fileDir);
					if (!dir.exists()) {
						dir.mkdirs();
					}
					String filePath = fileDir + "/" + fileName;
					File file = new File(filePath);
					multipartFile.transferTo(file);
					if (size > MAXSIZE) {
						BufferedImage src = ImageIO.read(file);
						BufferedImage dst = new ScaleFilter2(MAXWIDTH).filter(
								src, null);
						ImageIO.write(dst, "JPEG", file);
					}

					if (null == parms.get("imgUrl")) {
						parms.put("imgUrl", savePath);
					} else {
						parms.put("imgUrl", parms.get("imgUrl") + ","
								+ savePath);
					}
				}
			}
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getAppVersion")
	public Map<String, Object> fetchVersion(HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Locale locale1 = new Locale("zh", "CN");
			ResourceBundle rb = ResourceBundle.getBundle("config.appConfig",
					locale1);
			String version = rb.getString("version");
			String versionCode = rb.getString("versionCode");
			String info = new String(rb.getString("info")
					.getBytes("ISO-8859-1"), "UTF-8");
			String size = rb.getString("size");
			modelMap.put("version", version);
			modelMap.put("versionCode", versionCode);
			modelMap.put("info", info);
			modelMap.put("size", size);
			modelMap.put("resultCode", ResultCode.SUCCESS.getCode());
			return modelMap;
		} catch (Exception e) {
			modelMap.put("resultCode", ResultCode.ERROR.getCode());
			modelMap.put("msg", "获取版本失败");
			return modelMap;
		}
	}
}
