package com.ulewo.util;

public class ErrorReport implements Runnable {

	private String errorInfo;

	public ErrorReport(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	@Override
	public void run() {
		String[] address = new String[] { "308106363@qq.com" };
		try {
			SendMail.sendEmail("又报错啦", this.errorInfo, address);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
