package com.lhl.upload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.lhl.util.DrowImage;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * 文件上传类
 * 
 * @author Administrator
 * 
 */
public class AvatarUploadAction extends ActionSupport {

	private Logger logger = Logger.getLogger(AvatarUploadAction.class);

	private static final long serialVersionUID = 1L;

	private static final int BUFFER_SIZE = 20 * 1024;

	// 用File数组来封装多个上传文件域对象
	private File upload;

	// 用String数组来封装多个上传文件名
	private String uploadFileName;

	private static boolean copy(File src, File dst) {

		boolean result = false;
		InputStream in = null;
		OutputStream out = null;
		FileInputStream fin = null;
		FileOutputStream fout = null;
		try {
			fin = new FileInputStream(src);
			fout = new FileOutputStream(dst);
			in = new BufferedInputStream(fin, BUFFER_SIZE);
			out = new BufferedOutputStream(fout, BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			out.flush();
			result = true;
		}
		catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		finally {
			if (null != fin) {
				try {
					fin.close();
					fin = null;
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != fout) {
				try {
					fout.close();
					fout = null;
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != in) {
				try {
					in.close();
					in = null;
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != out) {
				try {
					out.close();
					out = null;
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public String deleteFile() throws Exception {

		HttpServletRequest request = ServletActionContext.getRequest();
		String filePath = request.getParameter("filePath");
		String fileName = request.getParameter("fileName");
		if (filePath != null && fileName != null) {
			File file = new File(ServletActionContext.getServletContext().getRealPath("/") + "/" + filePath);
			if (file.exists() && file.isFile()) {
				file.delete();
				ServletActionContext.getRequest().setAttribute("fileName", fileName);
				return SUCCESS;
			}
		}
		return ERROR;
	}

	public String avatarUpload() {

		try {
			File srcFiles = upload;
			String srcName = uploadFileName.toLowerCase();
			// 判断文件类型是否符合(.jpg,.gif,.png,.bmp)
			if (!srcName.endsWith(".jpg") && !srcName.endsWith(".gif") && !srcName.endsWith(".png")
					&& !srcName.endsWith(".bmp")) {
				return ERROR;
			}
			SimpleDateFormat yearMonthFormat = new SimpleDateFormat("yyyyMM");
			String yearMonth = yearMonthFormat.format(new Date());
			String imagePath = ServletActionContext.getServletContext().getRealPath("/") + "/upload/" + yearMonth;
			File imagePathFile = new File(imagePath);
			if (!imagePathFile.exists()) {
				imagePathFile.mkdirs();
			}
			// 根据服务器的文件保存地址和原文件名创建目录文件全路径
			String destPath = imagePathFile + "/" + uploadFileName;
			File destFile = new File(destPath);
			if (!copy(srcFiles, destFile)) {
				return ERROR;
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String current = format.format(new Date());
			String destImage = ServletActionContext.getServletContext().getRealPath("/") + "upload/" + yearMonth + "/"
					+ current + ".jpg";
			DrowImage.saveImageAsJpg(destPath, destImage, 60, 60, true);
			destFile.delete();
			uploadFileName = yearMonth + "/" + current + ".jpg";
			return SUCCESS;
		}
		catch (Exception e) {
			logger.error(e);
			return ERROR;
		}
	}

	public String getRealyPath(String path) {

		return ServletActionContext.getServletContext().getRealPath(path);
	}

	public void setUpload(File upload) {

		this.upload = upload;
	}

	public void setUploadFileName(String uploadFileName) {

		this.uploadFileName = uploadFileName;
	}

	public String getUploadFileName() {

		return uploadFileName;
	}
}