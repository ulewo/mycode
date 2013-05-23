package com.ulewo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.entity.Member;
import com.ulewo.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberAction {
	@Autowired
	private MemberService memberService;

	// 页面跳转，也就是servlet中的foward(); 方式
	@RequestMapping("/registerMember.do")
	public ModelAndView getMember(HttpSession session,
			HttpServletRequest request) {

		List<Member> memberList = memberService.queryList();
		ModelAndView mv = new ModelAndView();
		mv.addObject("memberList", memberList);
		mv.setViewName("home");
		return mv;
	}
	/*
	 * @RequestMapping("/queryMessage.do") public ModelAndView
	 * getMessage(HttpSession session, HttpServletRequest request) {
	 * 
	 * List<Message> list = messageService.queryList(); ModelAndView mv = new
	 * ModelAndView(); mv.addObject("list", list); mv.setViewName("home");
	 * return mv; }
	 * 
	 * @RequestMapping("/queryRedirect.do") public ModelAndView
	 * redirect(HttpSession session, HttpServletRequest request) {
	 * 
	 * String name = "系类"; ModelAndView mv = new ModelAndView();
	 * mv.addObject("name", name); mv.setViewName("redirect:/home.jsp"); return
	 * mv; }
	 * 
	 * @ResponseBody
	 * 
	 * @RequestMapping("/queryJson.do") public Map<String, Object>
	 * getJson(HttpSession session, HttpServletRequest request) {
	 * 
	 * List<Person> list = new ArrayList<Person>(); list.add(new Person("张三",
	 * 20)); Map<String, Object> modelMap = new HashMap<String, Object>();
	 * String name = "阿士大夫阿士大夫"; modelMap.put("name", name);
	 * modelMap.put("list", list); return modelMap; }
	 */

}

/*
 * class Person1 { private String username;
 * 
 * private int age;
 * 
 * Person1(String username, int age) {
 * 
 * this.username = username; this.age = age; }
 * 
 * public String getUserName() {
 * 
 * return username; }
 * 
 * public void setUserName(String username) {
 * 
 * this.username = username; }
 * 
 * public int getAge() {
 * 
 * return age; }
 * 
 * public void setAge(int age) {
 * 
 * this.age = age; } }
 */