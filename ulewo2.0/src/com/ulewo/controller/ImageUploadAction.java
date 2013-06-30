package com.ulewo.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.util.DrowImage;
import com.ulewo.util.ErrorReport;

@Controller
public class ImageUploadAction {

	private final static int MAX_FILE = 1024 * 1024;

	private static final int MAXWIDTH = 600;

	@RequestMapping(value = "/imageUpload", method = RequestMethod.POST)
	public ModelAndView fileupload(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		try {
			Object user = session.getAttribute("user");
			if (null == user) {
				mv.addObject("result", "fail");
				mv.addObject("message", "你登陆已超时，请重新登陆");
				mv.setViewName("common/imgupload");
				return mv;
			}
			String realPath = session.getServletContext().getRealPath("/");
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartRequest.getFile("file");
			long size = multipartFile.getSize();
			if (size > MAX_FILE) {
				mv.addObject("result", "fail");
				mv.addObject("message", "文件超过1M");
				mv.setViewName("common/imgupload");
				return mv;
			}
			String fileName = multipartFile.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			if (!"JPG".equalsIgnoreCase(suffix)
					&& !"PNG".equalsIgnoreCase(suffix)
					&& !"gif".equalsIgnoreCase(suffix)
					&& !"BMP".equalsIgnoreCase(suffix)) {
				mv.addObject("result", "fail");
				mv.addObject("message", "文件类型只能是图片");
				mv.setViewName("common/imgupload");
				return mv;
			}
			String current = String.valueOf(System.currentTimeMillis());
			fileName = current + "." + suffix;
			SimpleDateFormat formater = new SimpleDateFormat("yyyyMM");
			String saveDir = formater.format(new Date());
			String savePath = saveDir + "/" + fileName;
			String fileDir = realPath + "upload" + "/" + saveDir;
			File dir = new File(fileDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			String filePath = fileDir + "/" + fileName;
			File file = new File(filePath);
			multipartFile.transferTo(file);

			File fromFile = new File(filePath);
			BufferedImage srcImage = ImageIO.read(fromFile);
			int width = srcImage.getWidth();
			int height = srcImage.getHeight();
			int w = MAXWIDTH;
			int h = MAXWIDTH;
			if (fromFile.length() > 200 * 1024 || width > MAXWIDTH) {
				if (fromFile.length() > 200 * 1024 && width < MAXWIDTH) {// 图片太大，长宽不大的情况
					w = width;
					h = height;
				}
				String drowPath = fileDir + "/" + current + "x.jpg";
				DrowImage.saveImageAsJpg(filePath, drowPath, w, h, false);
				fromFile.delete();
				savePath = saveDir + "/" + current + "x.jpg";
			}
			mv.addObject("result", "success");
			mv.addObject("savePath", savePath);
			mv.setViewName("common/imgupload");
			return mv;
		} catch (Exception e) {
			String errorMethod = "ImageUploadAction-->fileupload()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			mv.addObject("result", "fail");
			mv.setViewName("common/imgupload");
			return mv;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/FileUpload", method = RequestMethod.POST)
	public Map<String, Object> logout(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> modelMap = new HashMap<String, Object>();

		modelMap.put("result", "success");
		return modelMap;
	}
}
