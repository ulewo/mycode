package com.ulewo.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadBase.InvalidContentTypeException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import sun.misc.BASE64Decoder;

/**
 * UEditor文件上传辅助类
 * 
 */
public class Uploader {

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

	private static final int MAXWIDTH = 1024;

	// 文件允许格式
	private String[] allowFiles = { ".gif", ".png", ".jpg", ".jpeg", ".bmp" };

	// 文件大小限制，单位KB
	private int maxSize = 10000;

	private HashMap<String, String> errorInfo = new HashMap<String, String>();

	public Uploader(HttpServletRequest request) {

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

		boolean isMultipart = ServletFileUpload
				.isMultipartContent(this.request);
		if (!isMultipart) {
			this.state = this.errorInfo.get("NOFILE");
			return;
		}
		DiskFileItemFactory dff = new DiskFileItemFactory();
		String savePath = this.getFolder(this.savePath);
		dff.setRepository(new File(savePath));
		InputStream input = null;
		BufferedInputStream in = null;
		FileOutputStream out = null;
		BufferedOutputStream output = null;
		// InputStream tempin = null;
		try {
			ServletFileUpload sfu = new ServletFileUpload(dff);
			sfu.setSizeMax(this.maxSize * 1024);
			sfu.setHeaderEncoding("utf-8");
			FileItemIterator fii = sfu.getItemIterator(this.request);
			while (fii.hasNext()) {
				FileItemStream fis = fii.next();
				if (!fis.isFormField()) {
					this.originalName = fis.getName().substring(
							fis.getName().lastIndexOf(
									System.getProperty("file.separator")) + 1);
					if (!this.checkFileType(this.originalName)) {
						this.state = this.errorInfo.get("TYPE");
						continue;
					}
					this.fileName = this.getName(this.originalName);
					this.type = this.getFileExt(this.fileName);
					this.url = savePath + "/" + this.fileName;
					input = fis.openStream();
					in = new BufferedInputStream(input);

					String realurl = this.getPhysicalPath(this.url);
					File file = new File(realurl);
					out = new FileOutputStream(file);
					output = new BufferedOutputStream(out);
					Streams.copy(in, output, true);
					if (file.length() > 1024 * 1024 * 3) {// 图片大于3M不上传
						return;
					}
					if (file.length() > 1024 * 1024 * 2) { // 如果图片太大，就重新画
						BufferedImage src = ImageIO.read(file);
						BufferedImage dst = new ScaleFilter2(MAXWIDTH).filter(
								src, null);
						ImageIO.write(dst, "JPEG", file);
					}
					this.state = this.errorInfo.get("SUCCESS");
					break;
				} else {
					String fname = fis.getFieldName();
					// 只处理title，其余表单请自行处理
					if (!fname.equals("pictitle")) {
						continue;
					}
					this.title = "图片";
				}
			}
		} catch (SizeLimitExceededException e) {
			this.state = this.errorInfo.get("SIZE");
		} catch (InvalidContentTypeException e) {
			this.state = this.errorInfo.get("ENTYPE");
		} catch (FileUploadException e) {
			this.state = this.errorInfo.get("REQUEST");
		} catch (Exception e) {
			this.state = this.errorInfo.get("UNKNOWN");
		} finally {
			if (input != null) {
				input.close();
			}
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
			if (output != null) {
				output.close();
			}
		}
	}

	/**
	 * 接受并保存以base64格式上传的文件
	 * 
	 * @param fieldName
	 */
	public void uploadBase64(String fieldName) {

		String savePath = this.getFolder(this.savePath);
		String base64Data = this.request.getParameter(fieldName);
		this.fileName = this.getName("test.png");
		this.url = savePath + "/" + this.fileName;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			File outFile = new File(this.getPhysicalPath(this.url));
			OutputStream ro = new FileOutputStream(outFile);
			byte[] b = decoder.decodeBuffer(base64Data);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			ro.write(b);
			ro.flush();
			ro.close();
			this.state = this.errorInfo.get("SUCCESS");
		} catch (Exception e) {
			this.state = this.errorInfo.get("IO");
		}
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
		return this.fileName = "" + random.nextInt(10000)
				+ System.currentTimeMillis() + this.getFileExt(fileName);
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

	/**
	 * 根据传入的虚拟路径获取物理路径
	 * 
	 * @param path
	 * @return
	 */
	private String getPhysicalPath(String path) {

		String realPath = this.request.getSession().getServletContext()
				.getRealPath("/");
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
