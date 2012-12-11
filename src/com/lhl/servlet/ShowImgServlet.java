package com.lhl.servlet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

public class ShowImgServlet extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int width = 250;

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

		String img = request.getParameter("img");
		String type = "jpg";
		if (img != null)
		{
			type = img.substring(img.lastIndexOf(".") + 1);
		}
		OutputStream out = null;
		File file = new File(ATTACH_TEMP_DIR + img);
		InputStream in = null;

		ByteArrayOutputStream byteArrayOut = null;
		try
		{
			in = new FileInputStream(file);
			BufferedImage srcImage = ImageIO.read(in);
			byteArrayOut = new ByteArrayOutputStream();
			ImageIO.write(srcImage, type, byteArrayOut);
			byte[] data = byteArrayOut.toByteArray();
			out = response.getOutputStream();
			response.setContentType(type);
			response.setContentLength(data.length);
			out.write(data, 0, data.length); // write
			out.flush();
		}
		catch (Exception e)
		{

		}
		finally
		{
			if (byteArrayOut != null)
			{
				byteArrayOut.close();
			}
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}
	}
}
