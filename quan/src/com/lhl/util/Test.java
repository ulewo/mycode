package com.lhl.util;

public class Test
{

	/**
	 * description: 函数的目的/功能
	 * @param args
	 * @author luohl
	*/
	public static void main(String[] args)
	{

		/*
				try
				{
					ApplicationContext context = new ClassPathXmlApplicationContext("classpath:config/applicationContext.xml");
					TestService testService = (TestService) context.getBean("testService");
					testService.getUsers();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}*/

		String str = "12@35.com";
		System.out.println(str.substring(str.indexOf("@") + 1, str.length()));
	}
}
