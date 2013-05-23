package com.ulewo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.entity.Message;
import com.ulewo.service.MessageService;

@Controller
@RequestMapping("/message")
public class MessageBoardAction {
	@Autowired
	private MessageService messageService;

	// 页面跳转，也就是servlet中的foward(); 方式
	@RequestMapping("/queryMessage.do")
	public ModelAndView getMessage(HttpSession session, HttpServletRequest request) {

		List<Message> list = messageService.queryList();
		ModelAndView mv = new ModelAndView();
		mv.addObject("list", list);
		mv.setViewName("home");
		return mv;
	}

	@RequestMapping("/queryRedirect.do")
	public ModelAndView redirect(HttpSession session, HttpServletRequest request) {

		String name = "系类";
		ModelAndView mv = new ModelAndView();
		mv.addObject("name", name);
		mv.setViewName("redirect:/home.jsp");
		return mv;
	}

	@ResponseBody
	@RequestMapping("/queryJson.do")
	public Map<String, Object> getJson(HttpSession session, HttpServletRequest request) {

		List<Person> list = new ArrayList<Person>();
		list.add(new Person("张三", 20));
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String name = "阿士大夫阿士大夫";
		modelMap.put("name", name);
		modelMap.put("list", list);
		return modelMap;
	}

}

class Person {
	private String name;

	private int age;

	Person(String name, int age) {

		this.name = name;
		this.age = age;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public int getAge() {

		return age;
	}

	public void setAge(int age) {

		this.age = age;
	}

}
