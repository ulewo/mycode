package com.ulewo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ulewo.enums.ResultCode;
import com.ulewo.exception.BusinessException;
import com.ulewo.model.CommonDynamic;
import com.ulewo.service.CommonDynamicService;
import com.ulewo.service.Log;
import com.ulewo.util.UlewoPaginationResult;

@Controller
@RequestMapping("/user")
public class CommonDynamicAction extends BaseUserAction {

	@Autowired
	private CommonDynamicService commonDynamicService;

	@Log
	private Logger log;

	/**
	 * 所有动态
	 * 
	 * @param userId
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/dynamic", method = RequestMethod.GET)
	public Map<String, Object> dynamic(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> param = this.builderParams(request, false);
			UlewoPaginationResult<CommonDynamic> result = commonDynamicService.selectDynamic(param,
					this.getSessionUserId(session));
			modelMap.put("result", result);
			modelMap.put("resultCode", ResultCode.SUCCESS.getCode());
			return modelMap;
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			modelMap.put("resultCode", ResultCode.ERROR.getCode());
			modelMap.put("msg", e.getMessage());
			return modelMap;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("msg", "系统异常");
			return modelMap;
		}
	}
}
