package com.ulewo.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jhlabs.image.AbstractBufferedImageOp;

public class ScaleFilter2 extends AbstractBufferedImageOp {

	private int width;

	/**
	 * Construct a ScaleFilter.
	 * @param width the width to scale to
	 * @param height the height to scale to
	 */
	public ScaleFilter2(int width) {
		this.width = width;
	}

	public BufferedImage filter(BufferedImage src, BufferedImage dst) {

		int sorceW = src.getWidth();
		int sorceH = src.getHeight();
		int height = 0; //目标文件的高度
		if (src.getWidth() > width) // 目标文件宽度大于指定宽度
		{
			height = width * sorceH / sorceW;
		} else
		// 目标文件宽度小于指定宽度  那么缩略图大小就跟原图一样大
		{
			width = sorceW;
			height = sorceH;
		}

		if (dst == null) {
			ColorModel dstCM = src.getColorModel();
			dst = new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster(width, height),
					dstCM.isAlphaPremultiplied(), null);
		}

		Image scaleImage = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		Graphics2D g = dst.createGraphics();
		g.drawImage(scaleImage, 0, 0, width, height, null);
		g.dispose();

		return dst;
	}

	public String toString() {

		return "Distort/Scale";
	}

	public static void main(String[] args) {

		try {
			BufferedImage src = ImageIO.read(new File("C:/Documents and Settings/luo.hl/桌面/图片/123x.jpg"));
			BufferedImage dst = new ScaleFilter2(100).filter(src, null);
			ImageIO.write(dst, "JPEG", new File("C:/Documents and Settings/luo.hl/桌面/图片/123x.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}