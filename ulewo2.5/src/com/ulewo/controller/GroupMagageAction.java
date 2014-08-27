package com.ulewo.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
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
import com.ulewo.enums.MemberType;
import com.ulewo.enums.ResultCode;
import com.ulewo.enums.TopicEssence;
import com.ulewo.enums.TopicGrade;
import com.ulewo.exception.BusinessException;
import com.ulewo.model.Group;
import com.ulewo.model.GroupMember;
import com.ulewo.model.SessionUser;
import com.ulewo.model.Topic;
import com.ulewo.model.TopicCategory;
import com.ulewo.model.TopicComment;
import com.ulewo.service.GroupMemberService;
import com.ulewo.service.GroupService;
import com.ulewo.service.Log;
import com.ulewo.service.TopicCategoryService;
import com.ulewo.service.TopicCommentService;
import com.ulewo.service.TopicService;
import com.ulewo.util.Constant;
import com.ulewo.util.ErrorReport;
import com.ulewo.util.StringUtils;
import com.ulewo.util.UlewoPaginationResult4Json;

@Controller
@RequestMapping("/groupmanage")
public class GroupMagageAction extends BaseGroupAction {
	@Autowired
	GroupService groupService;

	@Autowired
	TopicCategoryService topicCategoryService;

	@Autowired
	TopicService topicService;

	@Autowired
	TopicCommentService topicCmmentService;

	@Autowired
	GroupMemberService groupMemberService;

	@Log
	Logger log;

	private static final int SMALL_WIDTH = 60;

	/**
	 * 后台管理首页
	 * 
	 * @param gid
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/{gid}", method = RequestMethod.GET)
	public ModelAndView manage(@PathVariable String gid, HttpSession session) {

		ModelAndView mv = new ModelAndView();

		try {
			mv.addObject("gid", gid);
			mv.setViewName("/groupmanage/main");
			return mv;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
	}

	@RequestMapping(value = "/dispatcher/{url}", method = RequestMethod.GET)
	public ModelAndView dispatcher(HttpSession session, HttpServletRequest request, @PathVariable String url) {
		ModelAndView mv = new ModelAndView();
		try {
			String gid = request.getParameter("gid");
			if (!StringUtils.isEmpty(url)) {
				url = url + "_real";
			} else {
				url = Constant.ERRORPAGE;
			}
			mv.addObject("gid", gid);
			mv.setViewName("groupmanage/" + url);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
		return mv;
	}

	/**
	 * 创建群组
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/creteGroup.action", method = RequestMethod.POST)
	public Map<String, Object> creteGroup(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Group group = this.groupService
					.createGroup(this.builderParams(request, true), this.getSessionUser(session));
			modelMap.put("result", ResultCode.SUCCESS.getCode());
			modelMap.put("group", group);
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
	 * 后台管理首页
	 * 
	 * @param gid
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/editGroup.action")
	public ModelAndView editGroup(HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		try {
			Map<String, String> map = this.builderParams(request, true);
			Group group = this.groupService.findGroupBaseInfo(map);
			mv.addObject("gid", group.getGid());
			mv.addObject("group", group);
			mv.setViewName("/groupmanage/group_baseinf_real");
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
	@RequestMapping(value = "/saveGroupIcon.action")
	public Map<String, Object> saveUserIcon(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		String tempimg = request.getParameter("groupIcon");
		String x1 = request.getParameter("x1");
		String y1 = request.getParameter("y1");
		String width = request.getParameter("width");
		String height = request.getParameter("height");
		String gid = request.getParameter("gid");
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
		Integer userId = sessionUser.getUserId();
		String groupIcon = "";
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
			// ScaleFilter filter = new ScaleFilter(SMALL_WIDTH,SMALL_HEIGHT);
			// 放大缩小图片
			ScaleFilter filter = new ScaleFilter(SMALL_WIDTH);
			BufferedImage okimg = filter.filter(subimg, null);
			// 将图片转为字节数组
			String okSrcPath = session.getServletContext().getRealPath("/") + "upload/group/";
			File imagePathFile = new File(okSrcPath);
			if (!imagePathFile.exists()) {
				imagePathFile.mkdirs();
			}
			File okfile = new File(okSrcPath + gid + "." + imgType);
			ImageIO.write(okimg, imgType, okfile);
			groupIcon = "group/" + gid + "." + imgType;
			Map<String, String> map = new HashMap<String, String>();
			map.put("gid", gid);
			map.put("groupIcon", groupIcon);
			this.groupService.updateGroupIcon(map, userId);
			modelMap.put("result", ResultCode.SUCCESS.getCode());
			return modelMap;
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			modelMap.put("result", ResultCode.ERROR.getCode());
			modelMap.put("message", e.getMessage());
			return modelMap;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
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

	/**
	 * 修改群组
	 * 
	 * @param gid
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveGroup.action")
	public Map<String, Object> saveGroup(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			this.groupService.updateGroup(map, this.getSessionUserId(session));
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
	 * 修改公告
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/editGroupNotice")
	public ModelAndView editGroupNotice(HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		try {
			Map<String, String> map = this.builderParams(request, true);
			Group group = this.groupService.findGroupBaseInfo(map);
			mv.addObject("gid", group.getGid());
			mv.addObject("group", group);
			mv.setViewName("/groupmanage/group_notice_real");
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

	@RequestMapping(value = "/editGroupIcon")
	public ModelAndView editGroupIcon(HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		try {
			Map<String, String> map = this.builderParams(request, true);
			Group group = this.groupService.findGroupBaseInfo(map);
			mv.addObject("gid", group.getGid());
			mv.addObject("group", group);
			mv.setViewName("/groupmanage/group_icon_real");
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
	@RequestMapping(value = "/saveGroupNotice.action")
	public Map<String, Object> saveGroupNotice(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			this.groupService.updateGroupNotice(map, this.getSessionUserId(session));
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
	 * 修改群组头像
	 * 
	 * @param session
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/eidtIcon", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> eidtIcon(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		String gid = request.getParameter("gid");
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
			File tempfile = new File(srcpath);
			tempIn = new FileInputStream(tempfile);
			BufferedImage img = ImageIO.read(tempIn);
			// 裁剪图片
			BufferedImage subimg = img.getSubimage(x1_int, y1_int, width_int, height_int);
			// 放大缩小图片
			ScaleFilter filter = new ScaleFilter(SMALL_WIDTH);
			BufferedImage okimg = filter.filter(subimg, null);
			String okSrcPath = session.getServletContext().getRealPath("/") + "upload/group/";
			File imagePathFile = new File(okSrcPath);
			if (!imagePathFile.exists()) {
				imagePathFile.mkdirs();
			}
			File okfile = new File(okSrcPath + gid + "." + imgType);
			ImageIO.write(okimg, imgType, okfile);
			userIcon = "group/" + gid + "." + imgType;
			Group group = new Group();
			group.setGid(Integer.parseInt(gid));
			group.setGroupIcon(userIcon);
			// groupService.updateGroup(group);
		} catch (Exception e) {
			String errorMethod = "GroupMagageAction-->saveUserIcon()<br>";
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

	/**
	 * 分类列表
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/category")
	@ResponseBody
	public UlewoPaginationResult4Json<TopicCategory> category(HttpSession session, HttpServletRequest request) {
		try {
			Map<String, String> map = this.builderParams(request, true);
			UlewoPaginationResult4Json<TopicCategory> result = this.topicCategoryService.queryCategory4Json(map);
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 保存分类
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveCategory.action")
	@ResponseBody
	public Map<String, Object> saveCategory(HttpSession session, HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {

			Map<String, String> map = this.builderParams(request, true);
			this.topicCategoryService.saveCategroy(map, this.getSessionUserId(session));
			modelMap.put("result", ResultCode.SUCCESS.getCode());
			return modelMap;
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			modelMap.put("msg", e.getMessage());
			return modelMap;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("msg", "系统异常!");
			return modelMap;
		}
	}

	/**
	 * 删除分类
	 * 
	 * @param gid
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteCategory", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView deleteCategory(HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		try {
			SessionUser sessionUser = (SessionUser) session.getAttribute("user");
			String userId = sessionUser.getUserId().toString();
			/*
			 * Group mygroup = groupService.queryGorup(gid); if
			 * (!checkPerm(mygroup, userId)) { mv.setViewName("redirect:" +
			 * Constant.ERRORPAGE); } if (!checkPerm(gid, userId)) {
			 * mv.setViewName("redirect:" + gid + "/group_item"); return mv; }
			 * String id = request.getParameter("id"); if
			 * (StringUtils.isEmpty(id) || !StringUtils.isNumber(id)) {
			 * mv.setViewName("redirect:" + Constant.ERRORPAGE); return mv; }
			 * int id_int = 0; if (StringUtils.isNotEmpty(id)) { id_int =
			 * Integer.parseInt(id); } if
			 * (articleItemService.deleteCategroy(id_int, null)) {
			 * mv.setViewName("redirect:group_item"); return mv; } else {
			 * mv.setViewName("redirect:" + Constant.ERRORPAGE); return mv; }
			 */
			return null;

		} catch (Exception e) {
			String errorMethod = "GroupMagageAction-->deleteItem()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
	}

	/**
	 * 文章列表
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/topics")
	@ResponseBody
	public UlewoPaginationResult4Json<Topic> topics(HttpSession session, HttpServletRequest request) {

		try {
			Map<String, String> map = this.builderParams(request, true);
			UlewoPaginationResult4Json<Topic> result = this.topicService.findTopics4Json(map);
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 跳转到编辑页面
	 * 
	 * @param gid
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/editTopic", method = RequestMethod.GET)
	public ModelAndView editTopic(HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		try {
			Map<String, String> map = this.builderParams(request, true);
			Topic topic = this.topicService.showTopic(map);
			List<TopicCategory> categorys = this.topicCategoryService.queryCategoryAndTopicCount(map);
			mv.addObject("topic", topic);
			mv.addObject("categorys", categorys);
			mv.setViewName("groupmanage/edit_topic");
			return mv;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return mv;
		}
	}

	/**
	 * 保存文章
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveTopic.action")
	public Map<String, Object> saveTopic(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, false);
			this.topicService.updateTopic(map, this.getSessionUser(session), request);
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
	 * 删除文章
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteTopic")
	public Map<String, Object> deleteTopic(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			this.topicService.deleteTopic(map, this.getSessionUser(session));
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
	 * 评论列表
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/topicComment.action")
	@ResponseBody
	public UlewoPaginationResult4Json<TopicComment> articleReply(HttpSession session, HttpServletRequest request) {

		try {
			Map<String, String> map = this.builderParams(request, true);
			UlewoPaginationResult4Json<TopicComment> result = this.topicCmmentService.queryComment4JsonByTopicId(map);
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new UlewoPaginationResult4Json<TopicComment>();
		}
	}

	/**
	 * 删除评论
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteComment.action")
	@ResponseBody
	public Map<String, Object> deleteComment(HttpSession session, HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			this.topicCmmentService.deleteComment(map, this.getSessionUser(session));
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
	 * 成员列表
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/member")
	@ResponseBody
	public UlewoPaginationResult4Json<GroupMember> member4Json(HttpSession session, HttpServletRequest request) {
		try {
			Map<String, String> map = this.builderParams(request, true);
			map.put("memberType", MemberType.ISMEMBER.getValue());
			UlewoPaginationResult4Json<GroupMember> result = this.groupMemberService.findMembers4Json(map);
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new UlewoPaginationResult4Json<GroupMember>();
		}
	}

	/**
	 * 删除成员
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteMember.action")
	@ResponseBody
	public Map<String, Object> deleteMember(HttpSession session, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			this.groupMemberService.deleteMember(map, this.getSessionUserId(session));
			result.put("result", ResultCode.SUCCESS.getCode());
			return result;
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			result.put("result", ResultCode.ERROR.getCode());
			result.put("msg", e.getMessage());
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.put("result", ResultCode.ERROR.getCode());
			result.put("msg", "系统异常!");
			return result;
		}
	}

	/**
	 * 成员
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/memberApply")
	@ResponseBody
	public UlewoPaginationResult4Json<GroupMember> memberApply(HttpSession session, HttpServletRequest request) {
		try {
			Map<String, String> map = this.builderParams(request, true);
			map.put("memberType", MemberType.NOTMEMBER.getValue());
			UlewoPaginationResult4Json<GroupMember> result = this.groupMemberService.findMembers4Json(map);
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new UlewoPaginationResult4Json<GroupMember>();
		}
	}

	/**
	 * 审核成员
	 * 
	 * @param gid
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/applyMmeber.action")
	@ResponseBody
	public Map<String, Object> applyMmeber(HttpSession session, HttpServletRequest request) {

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, false);
			this.groupMemberService.accetpMember(map, this.getSessionUserId(session));
			result.put("result", ResultCode.SUCCESS.getCode());
			return result;
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			result.put("result", ResultCode.ERROR.getCode());
			result.put("msg", e.getMessage());
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.put("result", ResultCode.ERROR.getCode());
			result.put("msg", "系统异常!");
			return result;
		}
	}

	@RequestMapping(value = "/topTopic.action")
	@ResponseBody
	public Map<String, Object> topTopic(HttpSession session, HttpServletRequest request) {

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			map.put("grade", String.valueOf(TopicGrade.TOP.getValue()));
			this.topicService.topTopic(map, this.getSessionUser(session));
			result.put("result", ResultCode.SUCCESS.getCode());
			return result;
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			result.put("result", ResultCode.ERROR.getCode());
			result.put("msg", e.getMessage());
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.put("result", ResultCode.ERROR.getCode());
			result.put("msg", "系统异常!");
			return result;
		}
	}

	@RequestMapping(value = "/topTopicCancel.action")
	@ResponseBody
	public Map<String, Object> topTopicNo(HttpSession session, HttpServletRequest request) {

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			map.put("grade", String.valueOf(TopicGrade.NORMAL.getValue()));
			this.topicService.topTopic(map, this.getSessionUser(session));
			result.put("result", ResultCode.SUCCESS.getCode());
			return result;
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			result.put("result", ResultCode.ERROR.getCode());
			result.put("msg", e.getMessage());
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.put("result", ResultCode.ERROR.getCode());
			result.put("msg", "系统异常!");
			return result;
		}
	}

	@RequestMapping(value = "/essenceTopic.action")
	@ResponseBody
	public Map<String, Object> essenceTopic(HttpSession session, HttpServletRequest request) {

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			map.put("essence", TopicEssence.Essence.getValue());
			this.topicService.essenceTopic(map, this.getSessionUser(session));
			result.put("result", ResultCode.SUCCESS.getCode());
			return result;
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			result.put("result", ResultCode.ERROR.getCode());
			result.put("msg", e.getMessage());
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.put("result", ResultCode.ERROR.getCode());
			result.put("msg", "系统异常!");
			return result;
		}
	}

	@RequestMapping(value = "/essenceTopicCancel.action")
	@ResponseBody
	public Map<String, Object> essenceTopicCancel(HttpSession session, HttpServletRequest request) {

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			map.put("essence", TopicEssence.NoEssence.getValue());
			this.topicService.essenceTopic(map, this.getSessionUser(session));
			result.put("result", ResultCode.SUCCESS.getCode());
			return result;
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			result.put("result", ResultCode.ERROR.getCode());
			result.put("msg", e.getMessage());
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.put("result", ResultCode.ERROR.getCode());
			result.put("msg", "系统异常!");
			return result;
		}
	}
}
