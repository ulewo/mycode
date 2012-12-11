package com.lhl.blog.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class DrowImage {
	// 等比例缩放
	public static BufferedImage resize(BufferedImage source, int targetW,
			int targetH) {

		// targetW，targetH分别表示目标长和宽
		int type = source.getType();
		BufferedImage target = null;

		double sx = (double) targetW / source.getWidth();
		double sy = (double) targetH / source.getHeight();
		// 这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放
		// 则将下面的if else语句注释即可
		if (source.getWidth() > source.getHeight()) { // 如果图片的宽度大于高度，那么按照宽度来截取
			targetH = targetW * source.getHeight() / source.getWidth();
			sy = sx;
		} else {
			targetW = targetH * source.getWidth() / source.getHeight();
			sx = sy;
		}

		if (type == BufferedImage.TYPE_CUSTOM) { // handmade
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW,
					targetH);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else
			target = new BufferedImage(targetW, targetH, type);
		Graphics2D g = target.createGraphics();
		// smoother than exlax:
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		return target;
	}

	// 根据宽度来缩放
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
	 * 
	 * @param isometric
	 *            是否等比例
	 * @throws Exception
	 */

	public static void saveImageAsJpg(String inFilePath, String outFilePath,
			int width, int hight, boolean isometric) {

		try {
			File file = new File(inFilePath);
			InputStream in = new FileInputStream(file);
			File saveFile = new File(outFilePath);
			BufferedImage srcImage = ImageIO.read(in);
			String imgType = "JPEG";
			if (inFilePath.toLowerCase().endsWith(".png")) {
				imgType = "PNG";
			}
			// System.out.println(ex);

			// 原图的大小
			int sw = srcImage.getWidth();
			int sh = srcImage.getHeight();
			if (isometric) {
				if (sw > width || sh > hight || file.length() > 1024 * 500) {
					srcImage = resize(srcImage, width, hight);
				}
			} else {
				if (sw > width || file.length() > 1024 * 500) {
					srcImage = resize2(srcImage, width);
				}
			}

			ImageIO.write(srcImage, imgType, saveFile);
			in.close();
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	public static void main(String args[]) {

		saveImageAsJpg("C:/Users/35sz/Desktop/临时文件/62faf073jw1dxxwcdr6xaj.jpg",
				"C:/Users/35sz/Desktop/临时文件/62faf073jw1dxxwcdr6xajx.jpg", 300,
				0, true);
	}
}

/*
 * 思路： 不切割的情况： 比如上传的图片 要求宽不能超过600 那么缩略图就应该按照600来等比例缩放 如果要求高度不能超过600
 * 那么缩略图按照高度来等比例缩放。
 */