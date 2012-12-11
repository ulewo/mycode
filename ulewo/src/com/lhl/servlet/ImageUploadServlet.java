package com.lhl.servlet;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.lhl.util.DrowImage;
import com.lhl.util.StringUtil;

public class ImageUploadServlet extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int width = 600;

	private static final int height = 250;

	public static final String ATTACH_TEMP_DIR = System.getProperty("user.home") + "/temp/";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{

		RequestDispatcher dispatcher = request.getRequestDispatcher("/upload.jsp");
		String result = "success";
		String imgUrl = "";
		InputStream in = null;
		OutputStream out = null;
		try
		{

			/***图片上传**/

			//最大文件大小
			long maxSize = 1024 * 1024;
			response.setContentType("text/html; charset=UTF-8");

			if (!ServletFileUpload.isMultipartContent(request))
			{
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
			while (itr.hasNext())
			{
				FileItem item = (FileItem) itr.next();
				if (!item.isFormField())
				{
					//检查文件大小
					if (item.getSize() > maxSize)
					{
						result = "filetoobig";
						break;
					}
					in = item.getInputStream();
					BufferedImage srcImage = ImageIO.read(item.getInputStream());
					String name = item.getName();
					String imgType = "jpg";
					if (name != null && !"".equals(name))
					{
						int idx = name.lastIndexOf(".");
						if (idx >= 0)
						{
							imgType = name.substring(idx + 1);
						}
					}
					createTemp();
					String ip = StringUtil.getIpAddress(request);
					imgUrl = StringUtil.encodeByMD5(ip) + "." + imgType;
					String savePath = ATTACH_TEMP_DIR + imgUrl;
					File file = new File(savePath);
					file = new File(savePath);
					out = new FileOutputStream(file);
					int len = 0;
					byte[] b = new byte[1024 * 5];
					while ((len = in.read(b)) != -1)
					{
						out.write(b, 0, len);
					}
					out.flush();
					if (srcImage.getWidth() > width || file.length() > 1024 * 1024)
					{ // 如果图片太大，就重新画
						DrowImage.saveImageAsJpg(ATTACH_TEMP_DIR + imgUrl, ATTACH_TEMP_DIR + imgUrl, width, 0, false);
					}
				}
			}
		}
		catch (Exception e)
		{
			result = "error";
			request.setAttribute("result", result);
			e.printStackTrace();
			try
			{
				dispatcher.forward(request, response);
			}
			catch (ServletException e1)
			{
				e1.printStackTrace();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
		finally
		{
			if (null != in)
			{
				in.close();
			}
			if (null != out)
			{
				out.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("imgUrl", imgUrl);
		request.setAttribute("imgPath", ATTACH_TEMP_DIR + imgUrl);
		dispatcher.forward(request, response);
	}

	private void createTemp()
	{

		File dir = new File(ATTACH_TEMP_DIR);
		if (!dir.exists())
		{
			dir.mkdirs();
		}
	}

	private static BufferedImage resize(BufferedImage source, int targetW, int targetH)
	{

		// targetW，targetH分别表示目标长和宽
		int type = source.getType();
		BufferedImage target = null;

		double sx = (double) targetW / source.getWidth();
		double sy = (double) targetH / source.getHeight();
		// 这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放
		// 则将下面的if else语句注释即可
		if (source.getWidth() > source.getHeight())
		{ //如果图片的宽度大于高度，那么按照高度来截取
			targetH = targetW * source.getHeight() / source.getWidth();
			sy = sx;
		}
		else
		{
			targetW = targetH * source.getWidth() / source.getHeight();
			sx = sy;
		}

		if (type == BufferedImage.TYPE_CUSTOM)
		{
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		}
		else
		{
			target = new BufferedImage(targetW, targetH, type);
		}
		Graphics2D g = target.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		return target;
	}
}
