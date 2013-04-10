package com.doov.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.doov.entity.Notice;
import com.doov.service.NoticeService;

@Controller
@RequestMapping("/member")
public class NoticeAction {
	@Autowired
	private NoticeService noticeService;

	@RequestMapping("/querNotices.do")
	public ModelAndView getMember(HttpSession session, HttpServletRequest request) {

		String title = request.getParameter("title");
		List<Notice> noticeList = noticeService.selectAll(title, 0, 10);
		ModelAndView mv = new ModelAndView();
		mv.addObject("list", noticeList);
		return mv;
	}
}
