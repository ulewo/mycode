/* 
 * Copyright (C), 2004-2010, 三五互联科技股份有限公司
 * File Name: com.lhl.filter.ContextLoaderListenerOverWrite.java
 * Encoding UTF-8 
 * Version: 1.0 
 * Date: 2012-5-31
 * History:
 * 1. Date: 2012-5-31
 *    Author: 35sz
 *    Modification: 新建
 * 2. ...
 */
package com.lhl.filter;

import java.util.List;

import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.lhl.cache.CacheManager;
import com.lhl.cache.GroupAdminManager;
import com.lhl.entity.Member;
import com.lhl.quan.service.MemberService;

/** 
 * @Title:
 * @Description: TODO(服务器启动时加载缓存)
 * @author 35sz
 * @date 2012-5-31
 * @version V1.0
*/
public class ContextLoaderListenerOverWrite extends ContextLoaderListener {
	private MemberService memberService;

	public void contextInitialized(ServletContextEvent event) {

		super.contextInitialized(event);
		ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(event
				.getServletContext());

		memberService = (MemberService) applicationContext.getBean("memberService");
		try {
			CacheManager manager = GroupAdminManager.getInstants();
			List<Member> list = memberService.queryAllAdmins();
			for (Member member : list) {
				manager.add(member.getGid(), member);
			}
		}
		catch (Exception e) {

		}
	}
}
