package com.ulewo.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

/**
 * UEditor文件上传辅助类
 * 
 */
public class RemoteUploader {

	private static final int width = 750;

	private static final int height = 250;

	// 输出文件地址
	private String url = "";

	// 上传文件名
	private String fileName = "";

	// 状态
	private String state = "";

	// 文件类型
	private String type = "";

	// 原始文件名
	private String originalName = "";

	// 文件大小
	private String size = "";

	private HttpServletRequest request = null;

	private String title = "";

	// 保存路径
	private String savePath = "upload";

	// 文件允许格式
	private String[] allowFiles = { ".gif", ".png", ".jpg", ".jpeg", ".bmp" };

	// 文件大小限制，单位KB
	private int maxSize = 10000;

	private HashMap<String, String> errorInfo = new HashMap<String, String>();

	public RemoteUploader(HttpServletRequest request) {

		this.request = request;
		HashMap<String, String> tmp = this.errorInfo;
		tmp.put("SUCCESS", "SUCCESS"); // 默认成功
		tmp.put("NOFILE", "未包含文件上传域");
		tmp.put("TYPE", "不允许的文件格式");
		tmp.put("SIZE", "文件大小超出限制");
		tmp.put("ENTYPE", "请求类型ENTYPE错误");
		tmp.put("REQUEST", "上传请求异常");
		tmp.put("IO", "IO异常");
		tmp.put("DIR", "目录创建失败");
		tmp.put("UNKNOWN", "未知错误");

	}

	public void upload() throws Exception {

		url = request.getParameter("upfile");
		String[] arr = url.split("ue_separate_ue");
		String[] outSrc = new String[arr.length];
		for (int i = 0; i < arr.length; i++) {

			//格式验证
			String type = getFileType(arr[i]);
			/*if (type.equals("")) {
				state = "图片类型不正确！";
				continue;
			}*/
			String saveName = Long.toString(new Date().getTime()) + type;
			//大小验证
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection conn = (HttpURLConnection) new URL(arr[i]).openConnection();
			if (conn.getContentType().indexOf("image") == -1) {
				state = "请求地址头不正确";
				continue;
			}
			if (conn.getResponseCode() != 200) {
				state = "请求地址不存在！";
				continue;
			}
			String filePath = this.getFolder(this.savePath) + "/" + saveName;
			File savetoFile = new File(getPhysicalPath(filePath));
			outSrc[i] = filePath;
			InputStream is = null;
			OutputStream os = null;
			try {
				is = conn.getInputStream();
				os = new FileOutputStream(savetoFile);
				int b;
				while ((b = is.read()) != -1) {
					os.write(b);
				}
				os.flush();
				// 这里处理 inputStream
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				os.close();
				is.close();
			}
		}
		String port = request.getServerPort() == 80 ? "" : ":" + request.getServerPort();
		String realPath = "http://" + request.getServerName() + port + request.getContextPath();
		for (int i = 0; i < outSrc.length; i++) {
			originalName += realPath + "/" + outSrc[i] + "ue_separate_ue";
		}
		originalName = originalName.substring(0, originalName.lastIndexOf("ue_separate_ue"));
	}

	/**
	 * 文件类型判断
	 * 
	 * @param fileName
	 * @return
	 */
	private boolean checkFileType(String fileName) {

		Iterator<String> type = Arrays.asList(this.allowFiles).iterator();
		while (type.hasNext()) {
			String ext = type.next();
			if (fileName.toLowerCase().endsWith(ext)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @return string
	 */
	private String getFileExt(String fileName) {

		return fileName.substring(fileName.lastIndexOf("."));
	}

	/**
	 * 依据原始文件名生成新文件名
	 * 
	 * @return
	 */
	private String getName(String fileName) {

		Random random = new Random();
		return this.fileName = "" + random.nextInt(10000) + System.currentTimeMillis() + this.getFileExt(fileName);
	}

	/**
	 * 根据字符串创建本地目录 并按照日期建立子目录返回
	 * 
	 * @param path
	 * @return
	 */
	private String getFolder(String path) {

		SimpleDateFormat formater = new SimpleDateFormat("yyyyMM");
		path += "/" + formater.format(new Date());
		File dir = new File(this.getPhysicalPath(path));
		if (!dir.exists()) {
			try {
				dir.mkdirs();
			} catch (Exception e) {
				this.state = this.errorInfo.get("DIR");
				return "";
			}
		}
		return path;
	}

	public String getFileType(String fileName) {

		String[] fileType = { ".gif", ".png", ".jpg", ".jpeg", ".bmp" };
		Iterator<String> type = Arrays.asList(fileType).iterator();
		while (type.hasNext()) {
			String t = type.next();
			if (fileName.endsWith(t)) {
				return t;
			}
		}
		return "";
	}

	/**
	 * 根据传入的虚拟路径获取物理路径
	 * 
	 * @param path
	 * @return
	 */
	private String getPhysicalPath(String path) {

		String realPath = this.request.getSession().getServletContext().getRealPath("/");
		return realPath + path;
	}

	public void setSavePath(String savePath) {

		this.savePath = savePath;
	}

	public void setAllowFiles(String[] allowFiles) {

		this.allowFiles = allowFiles;
	}

	public void setMaxSize(int size) {

		this.maxSize = size;
	}

	public String getSize() {

		return this.size;
	}

	public String getUrl() {

		return this.url;
	}

	public String getFileName() {

		return this.fileName;
	}

	public String getState() {

		return this.state;
	}

	public String getTitle() {

		return this.title;
	}

	public String getType() {

		return this.type;
	}

	public String getOriginalName() {

		return this.originalName;
	}
}
