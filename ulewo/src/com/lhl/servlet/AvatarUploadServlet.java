package com.lhl.servlet;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.lhl.entity.User;
import com.lhl.service.UserService;
import com.lhl.util.Constant;
import com.lhl.util.DrowImage;
import com.lhl.util.StringUtil;
import com.lhl.util.UpYun;

public class AvatarUploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int width = 600;

	private static final int height = 600;

	private static final int SMALL_WIDTH = 70;

	public static final String ATTACH_TEMP_DIR = System
			.getProperty("user.home") + "/temp/";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String type = request.getParameter("type");
		if ("upload".equals(type)) {
			uploadAvatar(request, response);
		} else if ("cut".equals(type)) {
			cutImg(request, response);
		}

	}

	private void uploadAvatar(HttpServletRequest request,
			HttpServletResponse response) {

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/uploadavatar.jsp");
		String result = "success";
		String imgUrl = "";
		InputStream in = null;
		OutputStream out = null;
		try {

			/*** 图片上传 **/

			// 最大文件大小
			long maxSize = 1024 * 1024;
			response.setContentType("text/html; charset=UTF-8");

			if (!ServletFileUpload.isMultipartContent(request)) {
				result = "noFile";
				request.setAttribute("result", result);
				dispatcher.forward(request, response);
				return;
			}
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");
			List items = upload.parseRequest(request);
			Iterator itr = items.iterator();
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				if (!item.isFormField()) {
					// 检查文件大小
					if (item.getSize() > maxSize) {
						result = "filetoobig";
						break;
					}
					in = item.getInputStream();
					// BufferedImage srcImage =
					// ImageIO.read(item.getInputStream());
					String imgType = "jpg";
					createTemp();
					String ip = StringUtil.getIpAddress(request);
					imgUrl = StringUtil.encodeByMD5(ip) + "." + imgType;
					String savePath = ATTACH_TEMP_DIR + imgUrl;
					File file = new File(savePath);
					out = new FileOutputStream(file);
					int len = 0;
					byte[] b = new byte[1024 * 5];
					while ((len = in.read(b)) != -1) {
						out.write(b, 0, len);
					}
					out.flush();
					BufferedImage srcImage = ImageIO.read(file);
					if (srcImage.getWidth() > width
							|| srcImage.getHeight() > height
							|| file.length() > 1024 * 1024) { // 如果图片太大，就重新画
						DrowImage.saveImageAsJpg(ATTACH_TEMP_DIR + imgUrl,
								ATTACH_TEMP_DIR + imgUrl, width, height, true);
					}

				}
			}
		} catch (Exception e) {
			result = "error";
			request.setAttribute("result", result);
			e.printStackTrace();
			try {
				dispatcher.forward(request, response);
			} catch (ServletException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("imgUrl", imgUrl);
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void cutImg(HttpServletRequest request, HttpServletResponse response) {

		String ip = StringUtil.getIpAddress(request);
		String imgStr = StringUtil.encodeByMD5(ip) + ".jpg";
		String result = "success";
		String savepath = "";
		int x1 = 0;
		int y1 = 0;
		int width = 0;
		int height = 0;

		String x1str = request.getParameter("x1");
		if (x1str != null) {
			x1 = Integer.parseInt(x1str);
		}
		String y1str = request.getParameter("y1");
		if (y1str != null) {
			y1 = Integer.parseInt(y1str);
		}
		String widthstr = request.getParameter("width");
		if (widthstr != null) {
			width = Integer.parseInt(widthstr);
		}
		String heightstr = request.getParameter("height");
		if (heightstr != null) {
			height = Integer.parseInt(heightstr);
		}
		InputStream tempIn = null;
		ByteArrayOutputStream out = null;
		OutputStream imgOut = null;
		String imgType = "jpg";
		if (imgStr != null && !"".equals(imgStr)) {
			int idx = imgStr.lastIndexOf(".");
			if (idx >= 0) {
				imgType = imgStr.substring(idx + 1);
			}
		}
		try {
			createTemp();
			String tempFilePath = ATTACH_TEMP_DIR + imgStr;
			File file = new File(tempFilePath);
			tempIn = new FileInputStream(file);
			BufferedImage img = ImageIO.read(tempIn);
			// 裁剪图片
			BufferedImage subimg = img.getSubimage(x1, y1, width, height);
			// 放大缩小图片
			BufferedImage okimg = new BufferedImage(SMALL_WIDTH, SMALL_WIDTH,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = okimg.createGraphics();
			g.drawImage(subimg, 0, 0, SMALL_WIDTH, SMALL_WIDTH, null);

			// 将图片转为字节数组
			out = new ByteArrayOutputStream();
			ImageIO.write(okimg, imgType, out);
			byte[] data = out.toByteArray();

			String fileDir = "/upload/avatar";
			String newName = ((User) request.getSession().getAttribute("user"))
					.getUid() + ".jpg";
			savepath = "upload/avatar/" + newName;
			// 保存到本地服务器
			boolean saveResult = save2local(request, data, fileDir, newName);
			// 将数据保存到数据库
			if (saveResult) {
				String uid = "";
				Object obj = request.getSession().getAttribute("user");
				if (obj != null) {
					User user = (User) obj;
					uid = user.getUid();
					UserService.getInstance().updateUserAvatar(uid, savepath);
					user.setAvatar(savepath);
				}
			}
		} catch (Exception e) {
			result = "error";
			e.printStackTrace();
		} finally {
			try {
				if (null != tempIn) {
					tempIn.close();
					tempIn = null;
				}
				if (null != out) {
					out.close();
					out = null;
				}
				if (imgOut != null) {
					imgOut.close();
				}
			} catch (Exception e) {
				result = "error";
			}
			new File(ATTACH_TEMP_DIR + imgStr).delete();
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", result);
		jsonObject.put("imgUrl", savepath);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter outPrint = null;
		try {
			outPrint = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		outPrint.println(String.valueOf(jsonObject));
	}

	// 保存到upyun
	private boolean save2UpYun(String fileName, byte[] data, String fileDir,
			String imgStr) {

		UpYun upYun = UpYun.getInstance(Constant.UPYUN_HOST,
				Constant.UPYUN_HOST, Constant.UPYUN_PWD);
		String filepath = fileDir + "/" + imgStr;
		try {
			upYun.writeFile(filepath, data, true);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// 保存到本地服务器
	private boolean save2local(HttpServletRequest request, byte[] data,
			String fileDir, String imgStr) {

		String uploadPath = request.getSession().getServletContext()
				.getRealPath("/");
		String dirpath = uploadPath + fileDir;
		File bigDir = new File(dirpath);
		if (!bigDir.exists()) {
			bigDir.mkdirs();
		}
		File file = new File(dirpath + "/" + imgStr);
		OutputStream out = null;
		try {
			out = new FileOutputStream(file);
			out.write(data);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	private void createTemp() {

		File dir = new File(ATTACH_TEMP_DIR);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	private static BufferedImage resize(BufferedImage source, int targetW,
			int targetH) {

		// targetW，targetH分别表示目标长和宽
		int type = source.getType();
		BufferedImage target = null;

		double sx = (double) targetW / source.getWidth();
		double sy = (double) targetH / source.getHeight();
		// 这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放
		// 则将下面的if else语句注释即可
		if (source.getWidth() > source.getHeight()) { // 如果图片的宽度大于高度，那么按照高度来截取
			targetH = targetW * source.getHeight() / source.getWidth();
			sy = sx;
		} else {
			targetW = targetH * source.getWidth() / source.getHeight();
			sx = sy;
		}

		if (type == BufferedImage.TYPE_CUSTOM) {
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW,
					targetH);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else {
			target = new BufferedImage(targetW, targetH, type);
		}
		Graphics2D g = target.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		return target;
	}
}
