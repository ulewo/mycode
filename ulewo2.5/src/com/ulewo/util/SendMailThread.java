package com.ulewo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-8-29 下午4:42:13
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public class SendMailThread implements Runnable {
	private String content;
	private List<String> emailAddress = new ArrayList<String>();
	public SendMailThread(String content, List<String> emailAddress) {
		this.content = content;
		this.emailAddress = emailAddress;
	}
	@Override
	public void run() {
		try {
			sendMail("有乐窝一周精选", content, emailAddress);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Address[] getAddress(List<String> emilAddress) throws Exception {

		Address[] address = new Address[emilAddress.size()];
		for (int i = 0; i < address.length; i++) {
			address[i] = new InternetAddress(emilAddress.get(i));
		}
		return address;
	}

	public void sendMail(String title, String content,
 List<String> emilAddress)
			throws Exception {
		String host = "localhost"; // 本机smtp服务器
		String from = "ulewo@ulewo.com"; // 邮件发送人的邮件地址
		String[] to = {"308106363@qq.com", "523888550@qq.com",
				"583972479@qq.com", "408194044@qq.com"}; // 邮件接收人的邮件地址
		final String username = "ulewo"; // 发件人的邮件帐户
		final String password = "ulewo123456"; // 发件人的邮件密码

		// 创建Properties 对象
		Properties props = System.getProperties();

		// 添加smtp服务器属性
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");

		// 创建邮件会话
		Session session = Session.getDefaultInstance(props,
				new Authenticator() {
					@Override
					public PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}

				});

		try {
			// 定义邮件信息
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO,
					getAddress(emilAddress));
			message.setSubject(title);
			message.setContent(content, "text/html;charset=gbk;");

			// 发送消息
			session.getTransport("smtp").send(message);
			System.out.println("SendMail Process Over!");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
