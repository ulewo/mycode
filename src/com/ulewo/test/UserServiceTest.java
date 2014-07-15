package com.ulewo.test;

import com.ulewo.service.UserService;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-2-11 下午2:23:29
 * @version 0.1.0 
 * @copyright yougou.com 
 */

public class UserServiceTest {

	public void testPass() {

	}

	public static void main(String[] args) {

		UserService userService = (UserService) SpringWiredBean.getInstance().getBeanById("userService");
		System.out.print(userService);
	}
}
