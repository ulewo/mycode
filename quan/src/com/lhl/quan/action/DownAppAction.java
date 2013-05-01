package com.lhl.quan.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletResponse;

import com.lhl.common.action.BaseAction;

public class DownAppAction extends BaseAction {
	public void downloadApp() {
		InputStream in = null;
		BufferedInputStream bf = null;
		OutputStream toClient = null;
		try {
			// path是指欲下载的文件的路径。
			String realpath = getSession().getServletContext().getRealPath("/");
			ResourceBundle rb = ResourceBundle.getBundle("config.config");
			String app_name = rb.getString("app_name");
			File file = new File(realpath + "app" + "/" + app_name);
			in = new FileInputStream(file);
			bf = new BufferedInputStream(in);
			byte[] buffer = new byte[bf.available()];
			bf.read(buffer);
			// 清空response
			HttpServletResponse response = getResponse();
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(app_name.getBytes("utf-8"), "ISO-8859-1"));
			response.addHeader("Content-Length", "" + file.length());
			toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (toClient != null) {
				try {
					toClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bf != null) {
				try {
					bf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
