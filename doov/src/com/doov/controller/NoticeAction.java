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
import com.doov.util.Constant;
import com.doov.util.Pagination;
import com.doov.util.Tools;

@Controller
@RequestMapping("/member")
public class NoticeAction {
	@Autowired
	private NoticeService noticeService;

	@RequestMapping("/querNotices.do")
	public ModelAndView getMember(HttpSession session, HttpServletRequest request) {

		String title = request.getParameter("title");
		String pageStr = request.getParameter("page");
		int page = 1;
		if (Tools.isNumber(pageStr)) {
			page = Integer.parseInt(pageStr);
		}
		int countNumber = noticeService.getCount(title);
		Pagination.setPageSize(Constant.pageSize20);
		int pageSize = Pagination.getPageSize();
		int pageTotal = Pagination.getPageTotal(countNumber);
		if (page > pageTotal) {
			page = pageTotal;
		}
		if (page < 1) {
			page = 1;
		}
		int noStart = (page - 1) * pageSize;
		List<Notice> noticeList = noticeService.selectAll(title, noStart, Constant.pageSize20);
		ModelAndView mv = new ModelAndView();
		mv.addObject("list", noticeList);
		mv.addObject("page", page);
		mv.addObject("pageTotal", pageTotal);
		return mv;
	}
}
