<%@page import="java.io.ObjectOutputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,
				 hyweb.util.SpringLifeCycle,
				 java.io.OutputStream,
				 com.sun.image.codec.jpeg.*,
				 java.awt.*,
				 java.awt.image.BufferedImage,
				 java.awt.geom.*" %>
<%           
	response.setContentType("image/jpeg");  //設定回應的型態為 image/jpeg 
	int width = 86;    //圖形的寬度        
	int height = 25;     //圖形的高度        
	Random random = new Random();            
	BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);     //建立圖像       
	Graphics g = image.getGraphics();        
	Graphics2D g2d = (Graphics2D)g;
	Font f = new Font(Font.SANS_SERIF, Font.PLAIN, 18);  //設定字型的屬性        
	g.setColor(new Color(0xFF,0xEB,0xF5));     //圖像背景為淺粉紅
	g.fillRect(0, 0, width, height);     //將背景繪上       
	g.setFont(f);     //設定字型        
	g.setColor(Color.BLACK);    //再將背景設定為黑色        

	String code = "";  //驗證碼       
	for(int i=0;i<5;i++){    //產生四個隨機的英文或是數字                   
	/*switch(random.nextInt(2)){           
	    case 0:  //英文字母                    
			int r = random.nextInt(26) + 65;                    
			code = code + String.valueOf((char)r);                    
			break;                
		case 1:  //數字                    
*/					
			int n = random.nextInt(10) + 48;                    
			code = code + String.valueOf((char)n);
			
//	}            
		g.setColor(new Color(0x00,0x88,0x00));   //設定字型顏色
		Graphics2D g2dAT = (Graphics2D)g;            
		AffineTransform tran = new AffineTransform();            
		tran.scale(1.1, 1.3);   //縮放            
		tran.rotate(random.nextInt(10)*3.14/180, 28, 14); //旋轉           
		g2dAT.setTransform(tran);            
		g.drawString(code, 18, 14);   //將驗證碼繪上        
	}

//以下的迴圈會產生干擾線        
	for(int i=0;i<10;i++){  
	//產生兩組作標            
		int x1 = random.nextInt(width-1);              
		int y1 = random.nextInt(height-1);            
		int x2 = random.nextInt(24) + 1;            
		int y2 = random.nextInt(12) + 1;            
		//建立干擾線的輪廓 bsk            
		BasicStroke bsk = new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);            
		Line2D line = new Line2D.Double(x1, y1, x1+x2, y1+y2);            
		g2d.setStroke(bsk);            
		g2d.draw(line);        
	}        


	g.dispose();
	session.setAttribute("code", code);  //附加到 session 屬性中, 以便後續驗證所需        
	ServletOutputStream  os = response.getOutputStream(); //這裡要用 ServletOutputStream, 不要用 OutputStream      
	JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);        
	encoder.encode(image);
	os.flush();
	os.close();
	out.clear();
	out = pageContext.popBody();
	
%>