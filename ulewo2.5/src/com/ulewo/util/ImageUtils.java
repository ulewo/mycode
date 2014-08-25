package com.ulewo.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

public class ImageUtils {
	public static String geImageSmall(String topicImage,
			HttpServletRequest request, boolean isFullPath) {
		StringBuilder topicImageSmall = new StringBuilder();
		if (!StringUtils.isEmpty(topicImage)) {
			String port = request.getServerPort() == 80 ? "" : ":"
					+ request.getServerPort();
			String hostPath = "http://" + request.getServerName() + port
					+ request.getContextPath();
			String realPath = request.getSession().getServletContext()
					.getRealPath("");
			String[] topoicImages = topicImage.split("\\|");
			int smallCount = 3;// 缩略图只需要生成三张即可
			if (topoicImages.length <= 3) {
				smallCount = topoicImages.length;
			}
			for (int i = 0; i < smallCount; i++) {
				String img = topoicImages[i];
				if (StringUtils.isEmpty(img)) {
					continue;
				}
				String sourcePath = "";
				String smallSavePath = "";
				String smallPath = "";
				if (isFullPath) {
					String imagePath = img.replaceAll(hostPath, "");
					sourcePath = realPath + imagePath;
					smallPath = sourcePath + ".small.jpg";
					smallSavePath = hostPath + imagePath + ".small.jpg";
				} else {
					sourcePath = realPath + "/upload/" + img;
					smallPath = sourcePath + ".small.jpg";
					smallSavePath = img + ".small.jpg";
				}
				BufferedImage src = null;
				try {
					// 生成的缩略图 最宽100，最高100
					int cutWidth = 100;
					src = ImageIO.read(new File(sourcePath));
					int width = src.getWidth();
					int height = src.getHeight();
					if (width > height) {
						cutWidth = 100;
					} else {
						cutWidth = width * 100 / height;
					}
					BufferedImage dst = new ScaleFilter2(cutWidth).filter(src,
							null);
					ImageIO.write(dst, "JPEG", new File(smallPath));
				} catch (IOException e) {
					e.printStackTrace();
					break;
				}
				topicImageSmall.append(smallSavePath).append("|");
			}
			return topicImageSmall.toString().substring(0,
					topicImageSmall.length() - 1);
		}
		return topicImageSmall.toString();
	}
}
