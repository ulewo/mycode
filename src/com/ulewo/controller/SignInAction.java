package com.ulewo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.enums.ResultCode;
import com.ulewo.exception.BusinessException;
import com.ulewo.model.Calendar4Signin;
import com.ulewo.model.SignIn;
import com.ulewo.service.Log;
import com.ulewo.service.SignInService;
import com.ulewo.util.StringUtils;
import com.ulewo.util.UlewoPaginationResult;

/**
 * 
 * 签到
 * 
 * @author username
 * @date 2014-3-24 上午11:13:19
 * @version 2.5
 * @copyright ulewo.com
 */
@Controller
public class SignInAction extends BaseAction {
	@Autowired
	SignInService signInService;

	@Log
	Logger log;

	// 获取签到情况
	@ResponseBody
	@RequestMapping(value = "/signInInfo", method = RequestMethod.GET)
	public Map<String, Object> signInInfo(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			modelMap = this.signInService.signInInfo(this
					.getSessionUser(session));
			modelMap.put("result", ResultCode.SUCCESS.getCode());
			return modelMap;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("result", ResultCode.ERROR.getCode());
			return modelMap;
		}
	}

	// 签到
	@ResponseBody
	@RequestMapping(value = "/doSignIn.action", method = RequestMethod.GET)
	public Map<String, Object> reMark(HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			this.signInService.doSignIn(this.getSessionUser(session));
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

	// 页面跳转
	@RequestMapping(value = "/mySignIn.action", method = RequestMethod.GET)
	public ModelAndView mysignIn() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("mysignin");
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/loadMySignIn.action", method = RequestMethod.GET)
	public Map<String, Object> loadMySignIn(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, String> param = this.builderParams(request, true);
			String maxYear = StringUtils.dateFormater3.get().format(new Date());
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

	// 页面跳转
	@RequestMapping(value = "/allSignIn.action", method = RequestMethod.GET)
	public ModelAndView allsgin() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("allsignin");
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/loadAllSignIn.action", method = RequestMethod.GET)
	public Map<String, Object> todayReMark(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			UlewoPaginationResult<SignIn> pagResult = signInService
					.queryCurDaySigin(this.builderParams(request, true));
			resultMap.put("data", pagResult);
			resultMap.put("result", ResultCode.SUCCESS.getCode());
			return resultMap;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			resultMap.put("result", ResultCode.ERROR.getCode());
			resultMap.put("msg", "系统异常");
		}
		return resultMap;
	}
}
