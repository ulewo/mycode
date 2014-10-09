package com.ulewo.controller;

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
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.enums.PageSize;
import com.ulewo.enums.ResultCode;
import com.ulewo.enums.SourceFromEnums;
import com.ulewo.exception.BusinessException;
import com.ulewo.model.Blast;
import com.ulewo.model.BlastComment;
import com.ulewo.model.Group;
import com.ulewo.model.UserFriend;
import com.ulewo.service.BlastCommentService;
import com.ulewo.service.BlastService;
import com.ulewo.service.GroupService;
import com.ulewo.service.Log;
import com.ulewo.service.UserFriendService;
import com.ulewo.util.Constant;
import com.ulewo.util.SimplePage;
import com.ulewo.util.UlewoPaginationResult;
import com.ulewo.vo.UserVo;

@Controller
@RequestMapping("/user")
public class BlastAction extends BaseUserAction {
	@Log
	private static Logger log;
	@Autowired
	private BlastCommentService blastCommentService;

	@Autowired
	private BlastService blastService;
	@Autowired
	private UserFriendService userFriendService;

	@Autowired
	private GroupService groupService;

	/**
	 * 保存吐槽
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveBlast.action")
	public Map<String, Object> saveBlast(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> parms = builderParams(request, false);
			Blast blast = this.blastService.addBlast(parms, this.getSessionUser(session), request);
			modelMap.put("result", ResultCode.SUCCESS.getCode());
			modelMap.put("blast", blast);
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
	 * 加载吐槽
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/loadBlast", method = RequestMethod.GET)
	public UlewoPaginationResult<Blast> loadMoreTalk(HttpSession session, HttpServletRequest request) {
		try {
			UlewoPaginationResult<Blast> result = blastService.queryAllBlast(this.builderParams(request, true));
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 吐槽详情
	 * 
	 * @param userId
	 * @param blastId
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/{userId}/blast/{blastId}", method = RequestMethod.GET)
	public ModelAndView talkDetal(@PathVariable String userId, @PathVariable String blastId, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		try {
			UserVo userVo = checkUserInfo(userId, session);
			if (null == userVo) {
				mv.setViewName("redirect:" + Constant.ERRORPAGE);
				return mv;
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("userId", userId);
			map.put("blastId", blastId);
			int userId_int = Integer.parseInt(userId);
			Blast blast = this.blastService.selectBlast(map);
			List<UserFriend> fansList = userFriendService.queryFans4List(userId_int);
			List<UserFriend> focusList = userFriendService.queryFocus4List(userId_int);
			SimplePage page = new SimplePage(0, PageSize.SIZE5.getSize());
			List<Group> createdGroups = groupService.findCreatedGroups(userId_int, page);
			List<Group> joinedGroups = groupService.findJoinedGroups(userId_int, page);
			mv.addObject("focusList", focusList);
			mv.addObject("fansList", fansList);
			mv.addObject("createdGroups", createdGroups);
			mv.addObject("joinedGroups", joinedGroups);
			mv.addObject("blast", blast);
			mv.addObject("userVo", userVo);
			mv.setViewName("/user/blast_detal");

		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
		return mv;
	}

	/**
	 * 吐槽评论
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/loadBlastComment", method = RequestMethod.POST)
	public Map<String, Object> loadBlastComment(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			UlewoPaginationResult<BlastComment> paginResult = blastCommentService.queryBlastCommentByPag(this
					.builderParams(request, false));
			modelMap.put("result", ResultCode.SUCCESS.getCode());
			modelMap.put("data", paginResult);
			return modelMap;
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			modelMap.put("result", ResultCode.ERROR.getCode());
			return modelMap;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("result", ResultCode.ERROR.getCode());
			return modelMap;
		}
	}

	/**
	 * 评论吐槽
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveBlastCommetn.action", method = RequestMethod.POST)
	public Map<String, Object> saveBlastCommetn(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String,String> parms = this.builderParams(request, false);
			parms.put("sourceFrom",SourceFromEnums.PC.getValue());
			BlastComment comment = blastCommentService.saveBlastComment(parms,this.getSessionUserId(session));
			modelMap.put("result", ResultCode.SUCCESS.getCode());
			modelMap.put("comment", comment);
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
