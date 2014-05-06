package com.ulewo.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class DrowImage {
	private static BufferedImage resize(BufferedImage source, int targetW, int targetH) {

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

		if (type == BufferedImage.TYPE_CUSTOM) { // handmade
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else
			target = new BufferedImage(targetW, targetH, type);
		Graphics2D g = target.createGraphics();
		// smoother than exlax:
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		return target;
	}

	public static BufferedImage resize2(BufferedImage source, int targetW) {

		// targetW，targetH分别表示目标长和宽
		int targetH = source.getHeight();
		int type = source.getType();
		BufferedImage target = null;

		double sx = 0;
		double sy = 0;
		if (source.getWidth() > targetW) // 目标文件宽度大于指定宽度
		{
			sx = (double) targetW / source.getWidth();
			targetH = targetW * source.getHeight() / source.getWidth();
			sy = sx;
		} else
		// 目标文件宽度小于指定宽度
		{
			targetW = source.getWidth();
			targetH = source.getHeight();
			sx = sy = 1;
		}

		if (type == BufferedImage.TYPE_CUSTOM) { // handmade
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else {
			target = new BufferedImage(targetW, targetH, type);
		}
		Graphics2D g = target.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		return target;
	}

	/**
	 * 实现图像的等比缩放和缩放后的截取
	 * 
	 * @param inFilePath
	 *            要截取文件的路径
	 * @param outFilePath
	 *            截取后输出的路径
	 * @param width
	 *            要截取宽度
	 * @param hight
	 *            要截取的高度
	 * @param proportion
	 * @throws Exception
	 */

	public static void saveImageAsJpg(String inFilePath, String outFilePath, int width, int hight, boolean isReCreate) {

		try {
			boolean isCut = false;
			File file = new File(inFilePath);
			InputStream in = new FileInputStream(file);
			File saveFile = new File(outFilePath);
			BufferedImage srcImage = ImageIO.read(in);
			String imgType = "JPEG";
			if (inFilePath.toLowerCase().endsWith(".png")) {
				imgType = "PNG";
			}
			// System.out.println(ex);
			if (width > 0 || hight > 0) {
				// 原图的大小
				int sw = srcImage.getWidth();
				int sh = srcImage.getHeight();
				width = sw;
				srcImage = resize2(srcImage, width);
			}
			// 需要截取的图片

			// 判断是否需要截取
			if (isCut) {
				// 缩放后的图像的宽和高
				int w = srcImage.getWidth();
				int h = srcImage.getHeight();
				// 如果缩放后的图像和要求的图像宽度一样，就对缩放的图像的高度进行截取
				if (w == width) {
					// 计算X轴坐标
					int x = 0;
					int y = 0;
					saveSubImage(srcImage, new Rectangle(x, y, width, hight), saveFile);
				}
				// 否则如果是缩放后的图像的高度和要求的图像高度一样，就对缩放后的图像的宽度进行截取
				else if (h == hight) {
					// 计算X轴坐标
					int x = 0;
					int y = 0;
					saveSubImage(srcImage, new Rectangle(x, y, width, hight), saveFile);
				}
			} else {
				if (isReCreate) {
					// 创建一个空白的图片
					BufferedImage blockImage = new BufferedImage(60, 60, BufferedImage.TYPE_INT_RGB);
					Graphics2D g = blockImage.createGraphics();
					g.setColor(new Color(250, 250, 250));
					g.fillRect(0, 0, 60, 60);
					int left = 0;
					int top = 0;
					if (srcImage.getWidth() > srcImage.getHeight()) { // 如果宽度大于高度，那么上下空一点，图片在中间
						top = (srcImage.getWidth() - srcImage.getHeight()) / 2;
					} else { // 如果宽度小于高度，那么左右空一点，图片在中间
						left = (srcImage.getHeight() - srcImage.getWidth()) / 2;
					}
					// 将图片贴到空白图片上
					g.drawImage(srcImage, left, top, null);
					ImageIO.write(blockImage, imgType, saveFile);
				} else {
					ImageIO.write(srcImage, imgType, saveFile);
				}
			}
			in.close();
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	/**
	 * 实现缩放后的截图
	 * 
	 * @param image
	 *            缩放后的图像
	 * @param subImageBounds
	 *            要截取的子图的范围
	 * @param subImageFile
	 *            要保存的文件
	 * @throws IOException
	 */
	private static void saveSubImage(BufferedImage image, Rectangle subImageBounds, File subImageFile)
			throws IOException {

		if (subImageBounds.x < 0 || subImageBounds.y < 0 || subImageBounds.width - subImageBounds.x > image.getWidth()
				|| subImageBounds.height - subImageBounds.y > image.getHeight()) {
			return;
		}
		BufferedImage subImage = image.getSubimage(subImageBounds.x, subImageBounds.y, subImageBounds.width,
				subImageBounds.height);
		String fileName = subImageFile.getName();
		String formatName = fileName.substring(fileName.lastIndexOf('.') + 1);
		ImageIO.write(subImage, formatName, subImageFile);
	}

	public static void main(String[] args) {

		try {
			createSmallImage("D:\\wmsworkspace\\ulewo2.5\\ulewo2.5\\WebRoot\\upload\\201404\\31501396408124208.jpg",
					"D:\\wmsworkspace\\ulewo2.5\\ulewo2.5\\WebRoot\\upload\\201404\\31501396408124208.jpg.small.jpg",
					120);
		} catch (Exception e) {
			e.printStackTrace();// 2.29m
		}
	}

	public static void createSmallImage(String inFilePath, String outFilePath, int width) {
		InputStream in = null;
		try {
			File file = new File(inFilePath);
			in = new FileInputStream(file);
			File saveFile = new File(outFilePath);
			BufferedImage srcImage = ImageIO.read(in);
			String imgType = "JPEG";
			if (inFilePath.toLowerCase().endsWith(".png")) {
				imgType = "PNG";
			}
			srcImage = resize2(srcImage, width);
			ImageIO.write(srcImage, imgType, saveFile);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != in) {
					in.close();
					in = null;
				}
			} catch (Exception e) {

			}
		}

	}
}

/*
 * 思路： 不切割的情况： 比如上传的图片 要求宽不能超过600 那么缩略图就应该按照600来等比例缩放 如果要求高度不能超过600
 * 那么缩略图按照高度来等比例缩放。
 */