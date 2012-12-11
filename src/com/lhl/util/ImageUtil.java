package com.lhl.util;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.AreaAveragingScaleFilter;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.MemoryCacheImageInputStream;

import com.sun.imageio.plugins.bmp.BMPImageReader;
import com.sun.imageio.plugins.gif.GIFImageReader;
import com.sun.imageio.plugins.jpeg.JPEGImageReader;
import com.sun.imageio.plugins.png.PNGImageReader;

/**
 * 
 * 图片操作类
 * @author prestlhh
 * @version 1.00 2009/10/23
 *
 */
public class ImageUtil {

	private static double support = (double) 3.0;

	private static final double PI = (double) 3.14159265358978;
	
	/**
	 * 切割图片
	 * @param srcImage 源图片路径
	 * @param destImage 生成图片路径
	 * @param left 切割起始点left
	 * @param top 切割起始点top
	 * @param width 生成图片的宽
	 * @param height 生成图片的高
	 * @return
	 * @throws IOException 
	 */
	public static void cutImage(String srcImage, String destImage, int left, int top, int width, int height) throws IOException{	
		BufferedImage imgsrc = ImageIO.read(new File(srcImage));
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int wid = imgsrc.getWidth();
		int hei = imgsrc.getHeight();
		for(int i = 0; i < wid; i++){
			for(int j = 0; j < hei; j++){
				if(i >= left && i < left + width){
					if(j >= top && j < top + height){
						image.setRGB(i - left, j - top, imgsrc.getRGB(i, j));
					}
				}
			}
		}
		ImageIO.write(image, "JPEG", new File(destImage));
	}

	/**
	 * 生成简略图
	 * @param srcImage 原图片路径
	 * @param destImage 生成图片路径
	 * @param maxWidth 生成图片的最大宽度
	 * @param maxHeight 生成图片的最大高度
	 * @return
	 * @throws IOException 
	 */
	public static void narrowImage(String srcImage, String destImage,int maxWidth, int maxHeight) throws Exception {
		String type = getImageType(srcImage);
		if("png".equals(type)){
			scalePNGImage(srcImage, destImage,maxWidth, maxHeight);
		} else if("gif".equals(type)){
			scaleGIFImage(srcImage, destImage,maxWidth, maxHeight);
		} else {
			scaleImage(srcImage, destImage,maxWidth, maxHeight);
		}
	}
	
	/**
	 * jpg、bmp图片的缩放
	 * @param srcImage
	 * @param destImage
	 * @param maxWidth
	 * @param maxHeight
	 * @throws Exception
	 */
	public static void scaleImage(String srcImage, String destImage,int maxWidth, int maxHeight) throws Exception {
		// 来源文件
		File fromFile = new File(srcImage);
		// 保存文件
		File saveFile = new File(destImage);
		// 读取来源文件到缓冲区
		BufferedImage srcBufImage = ImageIO.read(fromFile);
		int imageWideth = srcBufImage.getWidth();
		int imageHeight = srcBufImage.getHeight();
		int destWidth = 0;
		int destHeight = 0;
		// 设置生成图片的宽和高
		if (imageWideth > 0 && imageHeight > 0) {
			if (imageWideth / imageHeight >= maxWidth / maxHeight) {
				if (imageWideth > maxWidth) {
					destWidth = maxWidth;
					destHeight = (imageHeight * maxWidth) / imageWideth;
				} else {
					destWidth = imageWideth;
					destHeight = imageHeight;
				}
			} else {
				if (imageHeight > maxHeight) {
					destHeight = maxHeight;
					destWidth = (imageWideth * maxHeight) / imageHeight;
				} else {
					destWidth = imageWideth;
					destHeight = imageHeight;
				}
			}
		}
		// 
		BufferedImage destBufImage = imageZoomOut(srcBufImage, destWidth, destHeight);
		ImageIO.write(destBufImage, "JPEG", saveFile);
	}
	
	/**
	 * 缩放gif图片(现在只能取第一帧，1.6后调用readGifFile和writeGifFile可以实现动画缩放)
	 * @param srcImage
	 * @param destImage
	 * @param maxWidth
	 * @param maxHeight
	 * @throws Exception
	 */
	public static void scaleGIFImage(String srcImage, String destImage,int maxWidth, int maxHeight) throws Exception {   
    	BufferedImage sourceImage = ImageIO.read(new File(srcImage));   
        AreaAveragingScaleFilter areaAveragingScaleFilter = new AreaAveragingScaleFilter(maxWidth,maxHeight);   
        FilteredImageSource filteredImageSource = new FilteredImageSource(sourceImage.getSource(),areaAveragingScaleFilter);   
        BufferedImage bufferedImage = new BufferedImage(maxWidth,maxHeight,BufferedImage.TYPE_3BYTE_BGR);   
        Graphics graphics = bufferedImage.createGraphics();   
        graphics.drawImage(new Canvas().createImage(filteredImageSource),0,0,null);   
        ImageIO.write(bufferedImage,"JPEG",new File(destImage));   
    }
	
	/**
	 * 缩放png图片
	 * @param srcImage
	 * @param destImage
	 * @param maxWidth
	 * @param maxHeight
	 * @throws Exception
	 */
	public static void scalePNGImage(String srcImage, String destImage,int maxWidth, int maxHeight) throws Exception {
		 BufferedImage sourceImage = ImageIO.read(new File(srcImage));   
	     AffineTransform transform = AffineTransform.getScaleInstance((float)maxWidth/(float)sourceImage.getWidth(), (float)maxHeight/(float)sourceImage.getHeight());// 返回表示缩放变换的变换   
	     AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);   
	     BufferedImage dsetBufImage = op.filter(sourceImage, null);   
	     ImageIO.write(dsetBufImage, "PNG", new File(destImage));    
	}
	
	/**
	 * 判别图片格式
	 * @param image
	 * @return
	 * @throws IOException
	 */
	public static String getImageType(String image) throws IOException {
		FileInputStream fis = new FileInputStream(image);
		int leng = fis.available();
		BufferedInputStream buff = new BufferedInputStream(fis);
		byte[] mapObj = new byte[leng];
		buff.read(mapObj, 0, leng);
		String type = "";
		ByteArrayInputStream bais = null;
		MemoryCacheImageInputStream mcis = null;
		try {
			bais = new ByteArrayInputStream(mapObj);
			mcis = new MemoryCacheImageInputStream(bais);
			Iterator itr = ImageIO.getImageReaders(mcis);
			while (itr.hasNext()) {
				ImageReader reader = (ImageReader) itr.next();
				if (reader instanceof GIFImageReader) {
					type = "gif";
				} else if (reader instanceof JPEGImageReader) {
					type = "jpeg";
				} else if (reader instanceof PNGImageReader) {
					type = "png";
				} else if (reader instanceof BMPImageReader) {
					type = "bmp";
				}
			}
		} finally {
			if (bais != null) {
				try {
					bais.close();
				} catch (IOException ioe) {

				}
			}
			if (mcis != null) {
				try {
					mcis.close();
				} catch (IOException ioe) {

				}
			}
		}
		return type;
	}
	
	/**
	 * 生成简略图
	 * @param srcImage 原图片路径
	 * @param destImage 生成图片路径
	 * @param maxWidth 生成图片的最大宽度
	 * @param maxHeight 生成图片的最大高度
	 * @return
	 * @throws IOException 
	 */
	public static void createImage(BufferedImage srcBufImage, String destImage,int maxWidth, int maxHeight) throws Exception {
		// 保存文件
		File saveFile = new File(destImage);
		// 读取来源文件到缓冲区
		int imageWideth = srcBufImage.getWidth();
		int imageHeight = srcBufImage.getHeight();
		int destWidth = 0;
		int destHeight = 0;
		// 设置生成图片的宽和高
		if (imageWideth > 0 && imageHeight > 0) {
			if (imageWideth / imageHeight >= maxWidth / maxHeight) {
				if (imageWideth > maxWidth) {
					destWidth = maxWidth;
					destHeight = (imageHeight * maxWidth) / imageWideth;
				} else {
					destWidth = imageWideth;
					destHeight = imageHeight;
				}
			} else {
				if (imageHeight > maxHeight) {
					destHeight = maxHeight;
					destWidth = (imageWideth * maxHeight) / imageHeight;
				} else {
					destWidth = imageWideth;
					destHeight = imageHeight;
				}
			}
		}
		// 
		BufferedImage destBufImage = imageZoomOut(srcBufImage, destWidth, destHeight);
		ImageIO.write(destBufImage, "JPEG", saveFile);
	}

	/**
	 * 
	 * @param srcBufferImage
	 * @param destWidth
	 * @param destHeight
	 * @return
	 */
	public static BufferedImage imageZoomOut(BufferedImage srcBufferImage, int destWidth, int destHeight) {
		try{
			int width = srcBufferImage.getWidth();
			int height = srcBufferImage.getHeight();
			int scaleWidth = destWidth;
			// 如果是做放大操作，直接返回原图
			if (!DetermineResultSize(destWidth, destHeight, width, height)) {
				return srcBufferImage;
			}
			
			int nHalfDots = (int) ((double) width * support / (double) scaleWidth);
			int nDots = nHalfDots * 2 + 1;
			double[] contrib = new double[nDots];
			double[] normContrib = new double[nDots];
			double[] tmpContrib = new double[nDots];
			
			int center = nHalfDots;
			contrib[center] = 1.0;
			double weight = 0.0;
			for (int i = 1; i <= center; i++) {
				contrib[center + i] = lanczos(i, width, scaleWidth, support);
				weight += contrib[center + i];
			}
			for (int i = center - 1; i >= 0; i--) {
				contrib[i] = contrib[center * 2 - i];
			}
			weight = weight * 2 + 1.0;
			for (int i = 0; i <= center; i++) {
				normContrib[i] = contrib[i] / weight;
			}
			for (int i = center + 1; i < nDots; i++) {
				normContrib[i] = normContrib[center * 2 - i];
			}
			
			BufferedImage outImage = horizontalFiltering(srcBufferImage, destWidth, nHalfDots, nDots, contrib, normContrib, tmpContrib);
			BufferedImage finalOutImage = verticalFiltering(outImage, destHeight, nHalfDots, nDots, contrib, normContrib, tmpContrib);
			return finalOutImage;
		}catch(Exception e){
			return srcBufferImage;
		}
	}

	/**
	 * 决定图像尺寸
	 * @param destWidth
	 * @param destHeight
	 * @return 
	 */
	private static boolean DetermineResultSize(int destWidth, int destHeight, int width, int height) {
		double scaleH = (double) destWidth / (double) width;
		double scaleV = (double) destHeight / (double) height;
		// 需要判断一下scaleH，scaleV，不做放大操作
		if (scaleH >= 1.0 && scaleV >= 1.0) {
			return false;
		}
		return true;
	} 

	/**
	 * 
	 * @param num
	 * @param inWidth
	 * @param outWidth
	 * @param Support
	 * @return
	 */
	private static double lanczos(int num, int inWidth, int outWidth, double Support) {
		double x = (double) num * (double) outWidth / (double) inWidth;
		return Math.sin(x * PI) / (x * PI) * Math.sin(x * PI / Support) / (x * PI / Support);
	}

	/**
	 * 
	 * @param rgbValue
	 * @return
	 */
	private static int getRedValue(int rgbValue) {
		return (rgbValue & 0x00ff0000) >> 16;
	}

	/**
	 * 
	 * @param rgbValue
	 * @return
	 */
	private static int getGreenValue(int rgbValue) {
		return (rgbValue & 0x0000ff00) >> 8;
	}

	/**
	 * 
	 * @param rgbValue
	 * @return
	 */
	private static int getBlueValue(int rgbValue) {
		return rgbValue & 0x000000ff;
	}

	/**
	 * 
	 * @param redValue
	 * @param greenValue
	 * @param blueValue
	 * @return
	 */
	private static int comRGB(int redValue, int greenValue, int blueValue) {
		return (redValue << 16) + (greenValue << 8) + blueValue;
	}

	/**
	 * 行水平滤波
	 * @param bufImg
	 * @param startX
	 * @param stopX
	 * @param start
	 * @param stop
	 * @param y
	 * @param pContrib
	 * @return
	 */
	private static int horizontalFilter(BufferedImage bufImg, int startX, int stopX,
			int start, int stop, int y, double[] pContrib) {
		double valueRed = 0.0;
		double valueGreen = 0.0;
		double valueBlue = 0.0;
		int valueRGB = 0;
		for (int i = startX, j = start; i <= stopX; i++, j++) {
			valueRGB = bufImg.getRGB(i, y);
			valueRed += getRedValue(valueRGB) * pContrib[j];
			valueGreen += getGreenValue(valueRGB) * pContrib[j];
			valueBlue += getBlueValue(valueRGB) * pContrib[j];
		}
		valueRGB = comRGB(clip((int) valueRed), clip((int) valueGreen), clip((int) valueBlue));
		return valueRGB;
	} 

	/**
	 * 图片水平滤波
	 * @param bufImage
	 * @param iOutW
	 * @return
	 */
	private static BufferedImage horizontalFiltering(BufferedImage bufImage, int iOutW, int nHalfDots, int nDots, double[] contrib, double[] normContrib, double[] tmpContrib) {
		int dwInW = bufImage.getWidth();
		int dwInH = bufImage.getHeight();
		int value = 0;
		BufferedImage pbOut = new BufferedImage(iOutW, dwInH, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < iOutW; x++) {
			int startX;
			int start;
			int X = (int) (((double) x) * ((double) dwInW) / ((double) iOutW) + 0.5);
			int y = 0;
			startX = X - nHalfDots;
			if (startX < 0) {
				startX = 0;
				start = nHalfDots - X;
			} else {
				start = 0;
			}
			int stop;
			int stopX = X + nHalfDots;
			if (stopX > (dwInW - 1)) {
				stopX = dwInW - 1;
				stop = nHalfDots + (dwInW - 1 - X);
			} else {
				stop = nHalfDots * 2;
			}
			if (start > 0 || stop < nDots - 1) {
				double weight = 0;
				for (int i = start; i <= stop; i++) {
					weight += contrib[i];
				}
				for (int i = start; i <= stop; i++) {
					tmpContrib[i] = contrib[i] / weight;
				}
				for (y = 0; y < dwInH; y++) {
					value = horizontalFilter(bufImage, startX, stopX, start,
							stop, y, tmpContrib);
					pbOut.setRGB(x, y, value);
				}
			} else {
				for (y = 0; y < dwInH; y++) {
					value = horizontalFilter(bufImage, startX, stopX, start,
							stop, y, normContrib);
					pbOut.setRGB(x, y, value);
				}
			}
		}
		return pbOut;
	} 

	/**
	 * 
	 * @param pbInImage
	 * @param startY
	 * @param stopY
	 * @param start
	 * @param stop
	 * @param x
	 * @param pContrib
	 * @return
	 */
	private static int verticalFilter(BufferedImage pbInImage, int startY, int stopY,
			int start, int stop, int x, double[] pContrib) {
		double valueRed = 0.0;
		double valueGreen = 0.0;
		double valueBlue = 0.0;
		int valueRGB = 0;
		for (int i = startY, j = start; i <= stopY; i++, j++) {
			valueRGB = pbInImage.getRGB(x, i);
			valueRed += getRedValue(valueRGB) * pContrib[j];
			valueGreen += getGreenValue(valueRGB) * pContrib[j];
			valueBlue += getBlueValue(valueRGB) * pContrib[j];
		}
		valueRGB = comRGB(clip((int) valueRed), clip((int) valueGreen), clip((int) valueBlue));
		return valueRGB;
	} 

	/**
	 * 
	 * @param pbImage
	 * @param iOutH
	 * @return
	 */
	private static BufferedImage verticalFiltering(BufferedImage bufImage, int iOutH, int nHalfDots, int nDots, double[] contrib, double[] normContrib, double[] tmpContrib) {
		int iW = bufImage.getWidth();
		int iH = bufImage.getHeight();
		int value = 0;
		BufferedImage pbOut = new BufferedImage(iW, iOutH, BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < iOutH; y++) {
			int startY;
			int start;
			int Y = (int) (((double) y) * ((double) iH) / ((double) iOutH) + 0.5);
			startY = Y - nHalfDots;
			if (startY < 0) {
				startY = 0;
				start = nHalfDots - Y;
			} else {
				start = 0;
			}
			int stop;
			int stopY = Y + nHalfDots;
			if (stopY > (int) (iH - 1)) {
				stopY = iH - 1;
				stop = nHalfDots + (iH - 1 - Y);
			} else {
				stop = nHalfDots * 2;
			}
			if (start > 0 || stop < nDots - 1) {
				double weight = 0;
				for (int i = start; i <= stop; i++) {
					weight += contrib[i];
				}
				for (int i = start; i <= stop; i++) {
					tmpContrib[i] = contrib[i] / weight;
				}
				for (int x = 0; x < iW; x++) {
					value = verticalFilter(bufImage, startY, stopY, start, stop,
							x, tmpContrib);
					pbOut.setRGB(x, y, value);
				}
			} else {
				for (int x = 0; x < iW; x++) {
					value = verticalFilter(bufImage, startY, stopY, start, stop,
							x, normContrib);
					pbOut.setRGB(x, y, value);
				}
			}
		}
		return pbOut;
	}
	
	/**
	 * 
	 * @param x
	 * @return
	 */
	private static int clip(int x) {
		if (x < 0) {
			return 0;
		}
		if (x > 255) {
			return 255;
		}
		return x;
	}

    /**  
     * 图片水印  
     * @param pressImg 水印图片  
     * @param targetImg 目标图片  
     * @param x 修正值 默认在中间  
     * @param y 修正值 默认在中间  
     * @param alpha 透明度  
     */  
    public final static void pressImage(String pressImg, String targetImg, int x, int y, float alpha) throws Exception{   
        File img = new File(targetImg);   
        Image src = ImageIO.read(img);   
        int wideth = src.getWidth(null);   
        int height = src.getHeight(null);   
        BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);   
        Graphics2D g = image.createGraphics();   
        g.drawImage(src, 0, 0, wideth, height, null);   
        //水印文件   
        Image src_biao = ImageIO.read(new File(pressImg));   
        int wideth_biao = src_biao.getWidth(null);   
        int height_biao = src_biao.getHeight(null);   
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));   
        g.drawImage(src_biao, (wideth - wideth_biao) / 2, (height - height_biao) / 2, wideth_biao, height_biao, null);   
        //水印文件结束   
        g.dispose();   
        ImageIO.write((BufferedImage) image, "JPEG", img);   
    }   
  
    /**  
     * 文字水印  
     * @param pressText 水印文字  
     * @param targetImg 目标图片  
     * @param fontName 字体名称  
     * @param fontStyle 字体样式  
     * @param color 字体颜色  
     * @param fontSize 字体大小  
     * @param x 修正值  
     * @param y 修正值  
     * @param alpha 透明度  
     */  
    public static void pressText(String pressText, String targetImg, String fontName, int fontStyle, Color color, int fontSize, int x, int y, float alpha) throws Exception{   
        File img = new File(targetImg);   
        Image src = ImageIO.read(img);   
        int width = src.getWidth(null);   
        int height = src.getHeight(null);   
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);   
        Graphics2D g = image.createGraphics();   
        g.drawImage(src, 0, 0, width, height, null);   
        g.setColor(color);   
        g.setFont(new Font(fontName, fontStyle, fontSize));   
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));   
        g.drawString(pressText, (width - (getLength(pressText) * fontSize)) / 2 + x, (height - fontSize) / 2 + y);   
        g.dispose();   
        ImageIO.write((BufferedImage) image, "JPEG", img);   
    }   
  
    /**  
     * 放大图片  
     * @param inImage 输入图片路径  
     * @param outImage 输出图片路径  
     * @param height 高度  
     * @param width 宽度  
     * @param flag 比例不对时是否需要补白  
     */  
    public static void zoomImage(String inImage, String outImage, int width, int height, boolean flag) throws IOException {   
        double ratio = 0.0; //缩放比例    
        File file = new File(inImage);   
        BufferedImage bufImage = ImageIO.read(file);   
        Image tmpImage = bufImage.getScaledInstance(width, height, bufImage.SCALE_SMOOTH);   
        //计算比例
        int iWidth = bufImage.getWidth();
        int iHeight = bufImage.getHeight();
        if (iWidth > width || iHeight > height) {   
            if (iWidth > iHeight) {   
            	ratio = (new Integer(width)).doubleValue() / iWidth;
            } else {   
            	ratio = (new Integer(height)).doubleValue() / iHeight;
            }   
            AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);   
            tmpImage = op.filter(bufImage, null);   
        }   
        if (flag) {   
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);   
            Graphics2D g = image.createGraphics();   
            g.setColor(Color.white);   
            g.fillRect(0, 0, width, height);   
            if (width == tmpImage.getWidth(null)){   
                g.drawImage(tmpImage, 0, (height - tmpImage.getHeight(null)) / 2, tmpImage.getWidth(null), tmpImage.getHeight(null), Color.white, null);   
            } else { 
                g.drawImage(tmpImage, (width - tmpImage.getWidth(null)) / 2, 0, tmpImage.getWidth(null), tmpImage.getHeight(null), Color.white, null);   
            }
            g.dispose();   
            tmpImage = image;   
        }   
        ImageIO.write((BufferedImage) tmpImage, "JPEG", new File(outImage));   
    }   
  
    public static int getLength(String text) {   
        int length = 0;   
        for (int i = 0; i < text.length(); i++) {   
            if (new String(text.charAt(i) + "").getBytes().length > 1) {   
                length += 2;   
            } else {   
                length += 1;   
            }   
        }   
        return length / 2;   
    }   
    
    /**  
     * 读取GIF文件，并进行缩放，存放于BufferedImage数组中  
     *   
     * @param inputFileName  
     * @param zoomRatio  
     * @return  
     * @throws IOException  
     */  
    public BufferedImage[] readGifFile(String inputFileName,double zoomRatio) throws IOException   
    {   
        Iterator imageReaders = ImageIO.getImageReadersBySuffix("GIF");   
        if(!imageReaders.hasNext())   
        {   
            throw new IOException("no ImageReaders for GIF");   
        }   
        ImageReader imageReader = (ImageReader)imageReaders.next();   
        File file = new File(inputFileName);   
        if(!file.exists())   
        {   
            throw new IOException("no file: " + file.getName());   
        }   
        imageReader.setInput(ImageIO.createImageInputStream(file));   
        List < BufferedImage > images = new ArrayList < BufferedImage >();   
        for(int i = 0;true;++i)   
        {   
            try  
            {   
                Image image = imageReader.read(i);   
                int width = new Double(image.getWidth(null) * zoomRatio).intValue();   
                int height = new Double(image.getHeight(null) * zoomRatio).intValue();   
                if(width > 0 && height > 0)   
                {   
                    AreaAveragingScaleFilter areaAveragingScaleFilter = new AreaAveragingScaleFilter(width,height);   
                    FilteredImageSource filteredImageSource = new FilteredImageSource(image.getSource(),areaAveragingScaleFilter);   
                    BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);   
                    Graphics graphics = bufferedImage.createGraphics();   
                    graphics.drawImage(new Canvas().createImage(filteredImageSource),0,0,null);   
                    images.add(bufferedImage);   
                }   
            }   
            catch(IndexOutOfBoundsException e)   
            {   
                break;   
            }   
        }   
        return images.toArray(new BufferedImage[images.size()]);   
    }   
    /**  
     * 根据BufferedImage数组的数据，写入到GIF文件中去(JDK1.6后才支持) 
     *   
     * @param images  
     * @param outputFileName  
     * @throws IOException  
     */  
    public void writeGifFile(BufferedImage[] images,String outputFileName) throws IOException   
    {   
        Iterator imageWriters = ImageIO.getImageWritersBySuffix("GIF");   
        if(!imageWriters.hasNext())   
        {   
            throw new IOException("no ImageWriters for GIF");   
        }   
        ImageWriter imageWriter = (ImageWriter)imageWriters.next();   
        File file = new File(outputFileName);   
        file.delete();   
        imageWriter.setOutput(ImageIO.createImageOutputStream(file));   
        if(imageWriter.canWriteSequence())   
        {   
            imageWriter.prepareWriteSequence(null);   
            for(int i = 0;i < images.length;++i)   
            {   
                imageWriter.writeToSequence(new IIOImage(images[i],null,null),null);   
            }   
            imageWriter.endWriteSequence();   
        }   
        else  
        {   
            for(int i = 0;i < images.length;++i)   
            {   
                imageWriter.write(images[i]);   
            }   
        }   
    }  
    
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			//cutImage("c:/1.png", "c:/17.png", 0, 0, 627, 540);
			//narrowImage("F:/3.jpg", "F:/5.jpg", 320, 240);
			//pressImage("F:/1.jpg", "F:/mm.jpg", 0, 0, 1.0f);   
	        //pressText("我是文字水印", "F:/mm.jpg", "黑体", 36, Color.white, 80, 0, 0, 0.3f);   
	        //zoomImage("F:/2.jpg", 320, 240, true); 
			//narrowImage("F:/图片/IMG_0152.jpg", "F:/图片/me.jpg",800, 600);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
