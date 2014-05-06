package com.ulewo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.enums.NoticeStatus;
import com.ulewo.enums.ResultCode;
import com.ulewo.exception.BusinessException;
import com.ulewo.model.Notice;
import com.ulewo.service.Log;
import com.ulewo.service.NoticeService;
import com.ulewo.util.Constant;
import com.ulewo.util.UlewoPaginationResult;

@Controller
@RequestMapping("/manage")
public class NoticeManageAction extends BaseAction {

    @Autowired
    private NoticeService noticeService;

    @Log
    Logger log;

    @ResponseBody
    @RequestMapping(value = "/noticeCount.action", method = RequestMethod.GET)
    public Map<String, Object> noticeCount(HttpSession session,
	    HttpServletRequest request, HttpServletResponse response) {

	Map<String, Object> modelMap = new HashMap<String, Object>();

	try {
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("userId", String.valueOf(this.getSessionUserId(session)));
	    map.put("status", NoticeStatus.STATUS0.getStauts());
	    int count = noticeService.queryNoticeCountByUserId(map);
	    modelMap.put("count", count);
	    modelMap.put("result", ResultCode.SUCCESS.getCode());
	    return modelMap;
	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	    modelMap.put("result", ResultCode.ERROR.getCode());
	    return modelMap;
	}
    }

    @ResponseBody
    @RequestMapping(value = "/notice.action", method = RequestMethod.GET)
    public Map<String, Object> notice(HttpSession session,
	    HttpServletRequest request, HttpServletResponse response) {

	Map<String, Object> result = new HashMap<String, Object>();
	try {
	    UlewoPaginationResult<Notice> noticeResult = this.noticeService
		    .queryNoticeByUserId(this.builderParams(request, true),
			    this.getSessionUserId(session));
	    result.put("rows", noticeResult.getList());
	    result.put("total", noticeResult.getPage().getCountTotal());
	    return result;
	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	    result.put("rows", null);
	    result.put("total", 0);
	    return result;
	}
    }

    @ResponseBody
    @RequestMapping(value = "/SignNoticeRead.action")
    public Map<String, Object> SignNoticeRead(HttpSession session,
	    HttpServletRequest request, HttpServletResponse response) {
	Map<String, Object> result = new HashMap<String, Object>();
	try {

	    this.noticeService.signNoticeRead(this.builderParams(request, true),
		    this.getSessionUserId(session));
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

    @ResponseBody
    @RequestMapping(value = "/deleteNotice.action")
    public Map<String, Object> deleteNotice(HttpSession session,
	    HttpServletRequest request, HttpServletResponse response) {
	Map<String, Object> result = new HashMap<String, Object>();
	try {

	    this.noticeService.deleteNotice(this.builderParams(request, true),
		    this.getSessionUserId(session));
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

    @RequestMapping(value = "/readNotice.action", method = RequestMethod.GET)
    public ModelAndView noticeDetail(HttpSession session,
	    HttpServletRequest request, HttpServletResponse response) {

	ModelAndView mv = new ModelAndView();
	try {
	    Map<String, String> map = this.builderParams(request, true);
	    Notice notice = noticeService.readNotice(map,
		    this.getSessionUserId(session));
	    String url = getRealPath(request) + notice.getUrl();
	    mv.setViewName("redirect:" + url);
	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	    mv.setViewName("redirect:" + Constant.ERRORPAGE);
	    return mv;
	}
	return mv;
    }

    private String getRealPath(HttpServletRequest request) {
	String port = request.getServerPort() == 80 ? "" : ":"
		+ request.getServerPort();
	String realPath = "http://" + request.getServerName() + port
		+ request.getContextPath() + "/";
	return realPath;
    }

}