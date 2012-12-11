package com.lhl.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Test
{

	/**
	 * description: 函数的目的/功能
	 * @param args
	 * @author luohl
	*/
	private static final int SMALL_WIDTH = 70;

	public static void main(String[] args)
	{

		InputStream tempIn = null;
		try
		{
			File file = new File("C:/Users/luohl/Desktop/临时文件/图片/4.jpg");
			tempIn = new FileInputStream(file);
			BufferedImage img = ImageIO.read(tempIn);
			//裁剪图片
			BufferedImage subimg = img.getSubimage(0, 399, 70, 70);
			//放大缩小图片
			BufferedImage okimg = new BufferedImage(SMALL_WIDTH, SMALL_WIDTH, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = okimg.createGraphics();
			g.drawImage(subimg, 0, 0, SMALL_WIDTH, SMALL_WIDTH, null);

			//将图片转为字节数组
			File outFile = new File("C:/Users/luohl/Desktop/临时文件/图片/4x.jpg");
			ImageIO.write(okimg, "jpg", outFile);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (tempIn != null)
			{
				try
				{
					tempIn.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}

		}
	}
}
