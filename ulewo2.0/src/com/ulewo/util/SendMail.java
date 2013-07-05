package com.ulewo.util;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {

	/**
	 * 
	 * @param title  邮件标题
	 * @param content  邮件内容
	 * @param emilAddress  收件人地址
	 * @throws Exception
	 */
	public static void sendEmail(String title, String content, String[] emilAddress) throws Exception {

		Properties props = new Properties();
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.transport.protocol", "smtp");
		Session session = Session.getInstance(props);
		session.setDebug(true);
		Message msg = new MimeMessage(session);
		// 发送的邮箱地址
		msg.setFrom(new InternetAddress("ulewo@qq.com"));
		// 设置标题
		msg.setSubject(title);
		// 设置内容
		msg.setContent(content, "text/html;charset=gbk;");
		Transport transport = session.getTransport();
		// 设置服务器以及账号和密码
		transport.connect("smtp.qq.com", 25, "ulewo@qq.com", "*************");
		// 发送到的邮箱地址
		transport.sendMessage(msg, getAddress(emilAddress));
		transport.close();
	}

	private static Address[] getAddress(String[] emilAddress) throws Exception {

		Address[] address = new Address[emilAddress.length];
		for (int i = 0; i < address.length; i++) {
			address[i] = new InternetAddress(emilAddress[i]);
		}
		return address;
	}

	public static void main(String[] args) {

		String[] emilAddress = { "308106363@qq.com" };
		try {
			sendEmail("ssss", "hello", emilAddress);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
