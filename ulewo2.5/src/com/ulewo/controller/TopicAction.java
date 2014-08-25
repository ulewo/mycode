package com.ulewo.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.enums.AllowPostEnums;
import com.ulewo.enums.FileSizeEnums;
import com.ulewo.enums.ResultCode;
import com.ulewo.enums.TopicCommentTypeEnums;
import com.ulewo.exception.BusinessException;
import com.ulewo.model.AttachmentDownload;
import com.ulewo.model.Group;
import com.ulewo.model.SessionUser;
import com.ulewo.model.Topic;
import com.ulewo.model.TopicCategory;
import com.ulewo.model.TopicComment;
import com.ulewo.model.TopicSurvey;
import com.ulewo.service.AttachmentDownloadService;
import com.ulewo.service.AttachmentService;
import com.ulewo.service.GroupMemberService;
import com.ulewo.service.GroupService;
import com.ulewo.service.Log;
import com.ulewo.service.TopicCategoryService;
import com.ulewo.service.TopicCommentService;
import com.ulewo.service.TopicService;
import com.ulewo.service.TopicSurveyService;
import com.ulewo.service.UserService;
import com.ulewo.util.Constant;
import com.ulewo.util.ErrorReport;
import com.ulewo.util.StringUtils;
import com.ulewo.util.UlewoPaginationResult;

@Controller
@RequestMapping("/group")
public class TopicAction extends BaseGroupAction {
	@Autowired
	GroupService groupService;

	@Autowired
	TopicService topicService;

	@Autowired
	TopicSurveyService topicSurveyService;

	@Autowired
	TopicCategoryService topicCategoryService;

	@Autowired
	UserService userService;

	@Autowired
	GroupMemberService groupMemberService;

	@Autowired
	TopicCommentService topicCmmentService;

	@Autowired
	AttachmentService attachedService;

	@Autowired
	AttachmentDownloadService attachedDownloadService;

	@Log
	Logger log;

	/**
	 * 主题列表，所有主题，分类主题
	 * 
	 * @param gid
	 * @param cateId
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/{gid}/cateId/{cateId}", method = RequestMethod.GET)
	public ModelAndView groupArticleInItem(@PathVariable String gid, @PathVariable String cateId, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		try {
			Map<String, String> map = this.builderParams(request, true);
			map.put("gid", gid);
			map.put("categoryId", cateId);
			Group group = this.checkGroup(map, session);
			// 查询文章
			UlewoPaginationResult<Topic> topicResult = topicService.findTopics(map);
			// 查询分类
			List<TopicCategory> categoryList = topicCategoryService.queryCategoryAndTopicCount(map);
			SessionUser sessionUser = this.getSessionUser(session);
			TopicCategory category = topicCategoryService.getCategroy(map);
			if (sessionUser != null && sessionUser.getUserId().intValue() == group.getGroupUserId().intValue()) {
				category.setAllowPost(AllowPostEnums.ALLOW.getValue());
			}
			mv.addObject("group", group);
			mv.addObject("category", category);
			mv.addObject("gid", gid);
			mv.addObject("topicResult", topicResult);
			mv.addObject("categoryList", categoryList);
			mv.addObject("cateId", cateId);
			if (StringUtils.isEmpty(cateId)) {
				mv.setViewName("group/group");
			} else {
				mv.setViewName("group/group_articles");
			}

		} catch (Exception e) {
			String errorMethod = "GroupAction-->groupIndex()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
		return mv;
	}

	/**
	 * 详情
	 * 
	 * @param gid
	 * @param topicId
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/{gid}/topic/{topicId}", method = RequestMethod.GET)
	public ModelAndView showTopic(@PathVariable String gid, @PathVariable String topicId, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		try {
			Map<String, String> map = this.builderParams(request, true);
			map.put("gid", gid);
			map.put("topicId", topicId);
			Group group = this.checkGroup(map, session);
			Topic topic = this.topicService.showTopic(map);
			// 查询分类
			List<TopicCategory> categoryList = topicCategoryService.queryCategoryAndTopicCount(map);
			mv.addObject("group", group);
			mv.addObject("topic", topic);
			mv.addObject("categoryList", categoryList);
			mv.setViewName("group/show_detail");
		} catch (Exception e) {
			log.error(e.getMessage());
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/loadSurvey", method = RequestMethod.GET)
	public Map<String, Object> loadSurvey(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			TopicSurvey survey = this.topicSurveyService.findTopicSurveyById(map, this.getSessionUser(session));
			result.put("survey", survey);
			result.put("result", ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.put("result", ResultCode.SUCCESS.getCode());
			result.put("msg", "加载调查失败");
			return result;
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/saveSurvey.action", method = RequestMethod.POST)
	public Map<String, Object> saveSurvey(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String[] ids = request.getParameterValues("surveyId");
			Map<String, String> map = this.builderParams(request, true);
			topicSurveyService.SurveyDo(map, ids, this.getSessionUser(session));
			TopicSurvey survey = this.topicSurveyService.findTopicSurveyById(map, this.getSessionUser(session));
			result.put("survey", survey);
			result.put("result", ResultCode.SUCCESS.getCode());
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			result.put("result", ResultCode.ERROR.getCode());
			result.put("msg", e.getMessage());
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.put("result", ResultCode.ERROR.getCode());
			result.put("msg", "系统异常");
			return result;
		}
		return result;
	}

	/**
	 * 附件上传
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/fileupload", method = RequestMethod.POST)
	public ModelAndView fileupload(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		try {
			Object user = session.getAttribute("user");
			if (null == user) {
				mv.addObject("result", "fail");
				mv.addObject("message", "你登陆已超时，请重新登陆");
				mv.setViewName("group/fileupload");
				return mv;
			}
			String realPath = session.getServletContext().getRealPath("/");
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartRequest.getFile("file");
			long size = multipartFile.getSize();
			if (size > FileSizeEnums.SIZE1024_1024.getSize()) {
				mv.addObject("result", "fail");
				mv.addObject("message", "文件超过1M");
				mv.setViewName("group/fileupload");
				return mv;
			}
			String fileName = multipartFile.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			if (!"rar".equalsIgnoreCase(suffix) && !"zip".equalsIgnoreCase(suffix)) {
				mv.addObject("result", "fail");
				mv.addObject("message", "文件类型只能是.rar 压缩文件");
				mv.setViewName("group/fileupload");
				return mv;
			}
			SimpleDateFormat formater = new SimpleDateFormat("yyyyMM");
			String saveDir = formater.format(new Date());
			String realName = String.valueOf(System.currentTimeMillis()) + "." + suffix;
			String savePath = saveDir + "/" + realName;
			String fileDir = realPath + "upload" + "/" + saveDir;
			File dir = new File(fileDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			String filePath = fileDir + "/" + realName;
			File file = new File(filePath);
			multipartFile.transferTo(file);
			mv.addObject("result", "success");
			mv.addObject("savePath", savePath);
			mv.setViewName("group/fileupload");
			return mv;
		} catch (Exception e) {
			String errorMethod = "GroupAction-->fileupload()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			mv.addObject("result", "fail");
			mv.setViewName("group/fileupload");
			return mv;
		}
	}

	/**
	 * 删除附件
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteFile.action", method = RequestMethod.POST)
	public Map<String, Object> deleteFile(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String fileName = request.getParameter("fileName");
			String userId = request.getParameter("userId");
			if (!StringUtils.isNumber(userId)) {
				modelMap.put("result", ResultCode.ERROR.getCode());
				modelMap.put("msg", "参数异常");
				return modelMap;
			}
			if (this.getSessionUserId(session).intValue() != Integer.parseInt(userId)) {
				modelMap.put("result", ResultCode.ERROR.getCode());
				modelMap.put("msg", "参数异常");
				return modelMap;
			}
			if (StringUtils.isEmpty(fileName)) {
				modelMap.put("result", ResultCode.ERROR.getCode());
				modelMap.put("msg", "参数异常");
				return modelMap;
			}
			String realPath = session.getServletContext().getRealPath("/") + "upload/";
			File file = new File(realPath + fileName);
			if (file.exists()) {
				file.delete();
			}
			modelMap.put("result", ResultCode.SUCCESS.getCode());
			return modelMap;
		} catch (Exception e) {
			String errorMethod = "GroupAction-->deleteFile()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	/**
	 * 检测下载
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkDownLoad.action", method = RequestMethod.GET)
	public Map<String, Object> downloadFile(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			attachedDownloadService.checkDownLoad(map, this.getSessionUser(session));
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
	 * 下载附件
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/downloadFile.action", method = RequestMethod.GET)
	public ModelAndView downloadFileDo(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		try {

			this.attachedDownloadService.downloadFile(this.builderParams(request, true), this.getSessionUser(session),
					response, session, request);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
		return null;
	}

	/**
	 * 查询附件下载人
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/fetchAttachedUsers.do", method = RequestMethod.GET)
	public Map<String, Object> fetchAttachedUsers(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			List<AttachmentDownload> list = attachedDownloadService.queryAttachedUserByAttachedId(map);
			modelMap.put("result", ResultCode.SUCCESS.getCode());
			modelMap.put("list", list);
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
	 * 新增文章
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addTopic.action", method = RequestMethod.POST)
	public Map<String, Object> addTopic(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> param = this.builderParams(request, false);
			String[] surveyTitles = request.getParameterValues("surveyTitle");
			String realPath = session.getServletContext().getRealPath("/");
			Topic topic = this.topicService.addTopic(param, this.getSessionUser(session), surveyTitles, request);
			modelMap.put("result", ResultCode.SUCCESS.getCode());
			modelMap.put("topic", topic);
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
	 * 加载评论
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/loadComment", method = RequestMethod.GET)
	@ResponseBody
	public UlewoPaginationResult<TopicComment> loadComment(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Map<String, String> map = this.builderParams(request, true);
			UlewoPaginationResult<TopicComment> result = this.topicCmmentService.queryCommentByTopicId(map);
			return result;
		} catch (Exception e) {
			log.error(e.getMessage());
			return new UlewoPaginationResult<TopicComment>(new ArrayList<TopicComment>(), ResultCode.ERROR,
					new StringBuilder(e.getMessage()));
		}
	}

	/**
	 * 新增评论
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addComment.action", method = RequestMethod.POST)
	public Map<String, Object> addComment(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, false);

			TopicComment comment = topicCmmentService.addComment(map, this.getSessionUser(session));
			modelMap.put("comment", comment);
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
	 * 新增二级回复
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addSubComment.action", method = RequestMethod.POST)
	public Map<String, Object> addSubReComment(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, false);
			map.put("commentType", TopicCommentTypeEnums.SUBCOMMENT.getValue());
			TopicComment comment = topicCmmentService.addComment(map, this.getSessionUser(session));
			modelMap.put("comment", comment);
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
}
