<%@ page language="java" contentType="image/jpeg" pageEncoding="UTF-8"%>
<%@ page import="java.awt.*,java.awt.image.*,java.util.*,javax.imageio.*"%>
<%!
	//给定范围获得随机颜色
	Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
%>
<%
	//设置页面不缓存
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);

	// 在内存中创建图象
	int width = 80, height = 30;
	BufferedImage image = new BufferedImage(width, height,
			BufferedImage.TYPE_INT_RGB);

	// 获取图形上下文
	Graphics g = image.getGraphics();

	//生成随机类
	Random random = new Random();

	// 设定背景色
	g.setColor(new Color(188,223,193));
	g.fillRect(0, 0, width, height);

	//画边框
	//g.setColor(new Color());
	//g.drawRect(0,0,width-1,height-1);
    //产生背景颜色
	// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
	//g.setColor(getRandColor(160, 200));
	
	//for (int i = 0; i < 150; i++) {
	//	int x = random.nextInt(width);
	//	int y = random.nextInt(height);
	//	int xl = random.nextInt(18);
	//	int yl = random.nextInt(18);
	//	g.drawLine(x, y, x + xl, y + yl);
	//}

	//char c[] = new char[62];
	//for (int i = 97, j = 0; i < 123; i++, j++) {
	//	c[j] = (char) i;
	//}
	//for (int o = 65, p = 26; o < 91; o++, p++) {
	//	c[p] = (char) o;
	//}
	//for (int m = 48, n = 52; m < 58; m++, n++) {
	//	c[n] = (char) m;
	//}
	
	String s = "234578ABCDEFGHKMNRWXYZ";
	// 取随机产生的认证码(4位数字)
	String sRand = "";
	for (int i = 0; i < 4; i++) {
		int x = random.nextInt(s.length());
		String rand = String.valueOf(s.charAt(x));
		sRand += rand;
		int temp = random.nextInt(2);
		if(temp == 0){
			g.setFont(new Font("Times New Roman", Font.BOLD, 18));
		}else{
			g.setFont(new Font("Times New Roman", Font.BOLD, 18));
		}
		// 将认证码显示到图象中
		g.setColor(new Color(20 + random.nextInt(110), 20 + random
				.nextInt(110), 20 + random.nextInt(110)));
		//调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
		g.drawString(rand, 15* i + 8,20);
	}
	Graphics2D g2 = image.createGraphics();
	int count = 4;
	int[] xPoints = new int[count];
	int[] yPoints = new int[count];
	float lineWidth = 2.0f;
	BasicStroke   bs = new   BasicStroke(lineWidth);  
 	g2.setStroke(bs);
 	g2.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110))); 
	for(int i = 0; i < count; i++){
		xPoints[i] = width*i/4 + random.nextInt(width/4);
		yPoints[i] = random.nextInt(height);
	}
	g2.drawPolyline(xPoints, yPoints, count);

	// 将认证码存入SESSION
	session.setAttribute("checkCode", sRand);

	// 图象生效
	g.dispose();

	// 输出图象到页面
	ImageIO.write(image, "JPEG", response.getOutputStream());
	 
	// 清空缓存的内容
	out.clear();
	
	// 返回一个新的BodyContent(代表一个HTML页面的BODY部分内容）;保存JspWriter实例的对象out;更新PageContext的out属性的内容
	out = pageContext.pushBody();

%>