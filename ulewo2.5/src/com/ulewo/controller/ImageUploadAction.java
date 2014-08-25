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
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.enums.ResultCode;
import com.ulewo.util.ErrorReport;
import com.ulewo.util.ScaleFilter2;

@Controller
public class ImageUploadAction {

	private final static int MAX_FILE = 1024 * 1024 * 2;

	private final static int MAX_FILE_MAX = 1024 * 1024 * 3;

	private static final int MAXWIDTH = 1024;

	@RequestMapping(value = "/imageUpload.action", method = RequestMethod.POST)
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
			if (size > MAX_FILE_MAX) {
				mv.addObject("result", "fail");
				mv.addObject("message", "文件最大不能超过3M");
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
			if (size > MAX_FILE) {
				BufferedImage src = ImageIO.read(file);
				BufferedImage dst = new ScaleFilter2(src.getWidth()).filter(
						src, null);
				ImageIO.write(dst, "JPEG", file);
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
	@RequestMapping(value = "/imageUpload4Ajax.action", method = RequestMethod.POST)
	public Map<String,Object> imageUpload4Ajax(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String,Object> result = new HashMap<String,Object>();
		try {
			Object user = session.getAttribute("user");
			if (null == user) {
				result.put("result",ResultCode.ERROR.getCode());
				result.put("msg", "你登陆已超时，请重新登陆");
				return result;
			}
			String realPath = session.getServletContext().getRealPath("/");
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartRequest.getFile("file");
			long size = multipartFile.getSize();
			if (size > MAX_FILE_MAX) {
				result.put("result",ResultCode.ERROR.getCode());
				result.put("msg", "文件最大不能超过3M");
				return result;
			}
			String fileName = multipartFile.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			if (!"JPG".equalsIgnoreCase(suffix)
					&& !"PNG".equalsIgnoreCase(suffix)
					&& !"gif".equalsIgnoreCase(suffix)
					&& !"BMP".equalsIgnoreCase(suffix)) {
				result.put("result",ResultCode.ERROR.getCode());
				result.put("msg", "文件类型只能是图片");
				return result;
			}
			String current = String.valueOf(System.currentTimeMillis());
			fileName = current + "." + suffix;
			SimpleDateFormat formater = new SimpleDateFormat("yyyyMM");
			String saveDir = formater.format(new Date());
			String savePath = saveDir + "/" + fileName;
			String port = request.getServerPort() == 80 ? "" : ":"
					+ request.getServerPort();
			String hostPath = "http://" + request.getServerName() + port
					+ request.getContextPath();
			savePath = hostPath+"/upload/"+savePath;
			String fileDir = realPath + "upload" + "/" + saveDir;
			File dir = new File(fileDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			String filePath = fileDir + "/" + fileName;
			File file = new File(filePath);
			multipartFile.transferTo(file);
			if (size > MAX_FILE) {
				BufferedImage src = ImageIO.read(file);
				BufferedImage dst = new ScaleFilter2(src.getWidth()).filter(
						src, null);
				ImageIO.write(dst, "JPEG", file);
			}
			result.put("result",ResultCode.SUCCESS.getCode());
			result.put("savePath", savePath);
			return result;
		} catch (Exception e) {
			result.put("result",ResultCode.ERROR.getCode());
			result.put("msg", "系统异常");
			return result;
		}
	}
	
	@RequestMapping(value = "/iconUpload", method = RequestMethod.POST)
	public ModelAndView iconUpload(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		try {
			Object user = session.getAttribute("user");
			if (null == user) {
				mv.addObject("result", "fail");
				mv.addObject("message", "你登陆已超时，请重新登陆");
				mv.setViewName("common/iconUpload");
				return mv;
			}
			String realPath = session.getServletContext().getRealPath("/");
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartRequest.getFile("file");
			long size = multipartFile.getSize();
			if (size > MAX_FILE) {
				mv.addObject("result", "fail");
				mv.addObject("message", "文件超过1M");
				mv.setViewName("common/iconUpload");
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
				mv.setViewName("common/iconUpload");
				return mv;
			}
			String current = String.valueOf(System.currentTimeMillis());
			fileName = current + "." + suffix;
			String savePath = "tempfile" + "/" + fileName;
			String fileDir = realPath + "upload" + "/tempfile";
			File dir = new File(fileDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			String filePath = fileDir + "/" + fileName;
			File file = new File(filePath);
			multipartFile.transferTo(file);
			BufferedImage srcImage = ImageIO.read(file);
			int width = srcImage.getWidth();
			if (width > MAXWIDTH) {
				file.delete();
				mv.addObject("result", "fail");
				mv.addObject("message", "文件宽度不能超过1024");
				mv.setViewName("common/iconUpload");
				return mv;
			}
			mv.addObject("result", "success");
			mv.addObject("savePath", savePath);
			mv.setViewName("common/iconUpload");
			return mv;
		} catch (Exception e) {
			String errorMethod = "ImageUploadAction-->iconUpload()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			mv.addObject("result", "fail");
			mv.setViewName("common/iconUpload");
			return mv;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/FileUpload", method = RequestMethod.POST)
	public Map<String, Object> FileUpload(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> modelMap = new HashMap<String, Object>();

		modelMap.put("result", "success");
		return modelMap;
	}
}
