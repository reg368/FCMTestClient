/*
 * 所有程式碼皆於 JDK 6 環境，以Eclipse開發
 * 
 * [History]
 * 20081220
 *  add setJpegQuality(float quality)
 * 	add reSize(int maxWidth,int maxHeigh,boolean isFixed)
 * 	add subImage(int startW,int startH,int width,int height)
 *  add saveToDisk(File destFile)
 * 20090107
 *  add addWaterMark(ImageConvert waterMark,float alpha,int startX,int startY)
 * 20090609
 * 	add saveTo(String destUrl)
 *  add saveTo(OutputStream os,String imageType,boolean needColseStream)
 *  fix saveToDisk(File destFile) 改為呼叫上式
 * 20090627
 *  fix reSize(int maxWidth,int maxHeigh,boolean isFixed) 提供更動態縮放選擇
 * 20090711
 * 	add addWaterMark(Phase waterMark,float alpha,int startX,int startY)
 * 20090719
 *  add rotate(int angle)
 */
package hyweb.file.img;

import hyweb.core.kit.StreamKit;
import hyweb.file.img.type.ImageFacrory;
import hyweb.structure.Phase;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
/**
 * @author A0074
 * @version 1.0.090807.α
 * @since xbox 1.0
 * @see BufferedImage,Graphics2D
 */
public class ImageConvert extends ImageMetaData{
	public final static int POS_LEFT_TOP = 1;//左上
	public final static int POS_CENTER_TOP = 2;//中上
	public final static int POS_RIGHT_TOP = 3;//右上
	public final static int POS_LEFT_CENTER = 4;//左中
	public final static int POS_CENTER_CENTER = 5;//中中
	public final static int POS_RIGHT_CENTER = 6;//右中
	public final static int POS_LEFT_BOTTOM = 7;//左下
	public final static int POS_CENTER_BOTTOM = 8;//中下
	public final static int POS_RIGHT_BOTTOM = 9;//右下
	
	protected float _jpegQuality;
	protected int _dpiX;
	protected int _dpiY;
	private boolean _hasChangQuality = false;
	private boolean _hasChangDPI = false;
	public ImageConvert(String src) throws Exception,IOException{
		this(new File(src));
	}
	
	public ImageConvert(File src) throws Exception,IOException{
		super(src);
		this._jpegQuality = 0.8f;
		this._hasChangQuality = true;
	}

	public ImageConvert(ImageMetaData imd){
		super(imd.getBufferedImage());
	}

	public ImageConvert(BufferedImage brImg){
		super(brImg);
	}

	/**
	 * 設定輸出為JPEG時，預設的壓縮比
	 */
	public ImageConvert setJpegQuality(float quality){
		if(quality >= 1.0f){
			this._jpegQuality = 1.0f;
		}else{
			this._jpegQuality = quality;
		}
		this._hasChangQuality = true;
		return this;
	}

	public ImageConvert setJpegDPI(int dpiX, int dpiY){
		this._dpiX = dpiX;
		this._dpiY = dpiY;
		this._hasChangDPI = true;
		return this;
	}

	/**
	 * 轉換圖片大小
	 * 當isFixed為false時會將maxWidth,maxHeigh當為動態欄位，看以哪個為準可以得到最佳顯示。
	 * 當maxWidth、maxHeigh其一為0，則無視isFixed，依據非0的一方等比例縮放
	 * <pre>
	 * 	1280 * 800
	 * 	reSize(400,600,false) = 960 * 600
	 * 	reSize(1000,600,false) = 1000 * 625
	 * 	reSize(800,600,true) = 800 * 600
	 * 	reSize(800,0,true) = 800 * 500
	 * 	reSize(0,600,true) = 960 * 600
	 * </pre>
	 * @param maxWidth
	 * @param maxHeigh
	 * @param isFixed 前兩參數是否為固定數字。
	 * @return 當maxWidth、maxHeigh皆為0，不調整大小
	 */
	public ImageConvert reSize(int maxWidth, int maxHeigh, boolean isFixed){
		return this.reSize(maxWidth, maxHeigh, isFixed, true, false);
	}

	/**
	 * 
	 * @param maxWidth
	 * @param maxHeigh
	 * @param isFixed
	 * @param antiAliasing
	 * @return
	 */
	public ImageConvert reSize(int maxWidth, int maxHeigh, boolean isFixed, boolean quality, boolean antiAliasing){
		int width,height;
		if(maxWidth == 0 && maxHeigh != 0){
			width = (this._srcBufImage.getWidth() * maxHeigh) / this._srcBufImage.getHeight();
			height = maxHeigh;
		}else if(maxWidth != 0 && maxHeigh == 0){
			width = maxWidth;
			height = (this._srcBufImage.getHeight() * maxWidth) / this._srcBufImage.getWidth();
		}else if(maxWidth != 0 && maxHeigh != 0){
			if(isFixed){
				width = maxWidth;
				height = maxHeigh;
			}else{
				if(maxWidth != 0 && maxHeigh != 0){
					double maxProportion = maxWidth/(maxHeigh + 0.0d);
					double nowProportion = this._srcBufImage.getWidth()/(this._srcBufImage.getHeight() + 0.0);
					if(maxProportion > nowProportion){//寬為主
						width = maxWidth;
						height = this._srcBufImage.getHeight() * maxWidth / this._srcBufImage.getWidth();
					}else if(maxProportion < nowProportion){//高為主
						width = this._srcBufImage.getWidth() * maxHeigh / this._srcBufImage.getHeight();
						height = maxHeigh;
					}else{//固定縮放
						width = maxWidth;
						height = maxHeigh;
					}
				}else if(maxWidth == 0 && maxHeigh == 0){
					width = 0;
					height = 0;
				}else if(maxHeigh == 0){//高為主
					width = maxWidth;
					height = this._srcBufImage.getHeight() * maxWidth / this._srcBufImage.getWidth();
				}else{//寬為主
					width = this._srcBufImage.getWidth() * maxHeigh / this._srcBufImage.getHeight();
					height = maxHeigh;
				}
			}
		}else{
			width = this._srcBufImage.getWidth();
			height = this._srcBufImage.getHeight();
		}
		if(quality && antiAliasing){
			this._srcBufImage = new ScaleImage().imageZoomOut(this._srcBufImage, width, height);
		}else{
			BufferedImage tmpbi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = (Graphics2D)tmpbi.createGraphics();
			g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, quality ? RenderingHints.VALUE_RENDER_QUALITY : RenderingHints.VALUE_RENDER_SPEED));
			g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_INTERPOLATION, quality ? RenderingHints.VALUE_INTERPOLATION_BICUBIC :RenderingHints.VALUE_INTERPOLATION_BICUBIC));
			g2d.drawImage(this._srcBufImage,0,0,width,height,null);
			this._srcBufImage = tmpbi;
			g2d.dispose();			
		}
		return this;
	}

	/**
	 * 擷取圖片中的某一塊
	 * @param startW 起始位置的X座標
	 * @param startH 起始位置的y座標
	 * @param width 寬度
	 * @param height 高度
	 */
	public ImageConvert subImage(int startW,int startH,int width,int height){
		int imageW = this._srcBufImage.getWidth();
		int imageH = this._srcBufImage.getHeight();
		if(startW >= imageW){
			startW = 0;
		}
		if(startH >= imageH){
			startH = 0;
		}
		if(startW + width > imageW){
			width = imageW - startW;
		}
		if(startH + height > imageH){
			height = imageH - startH;
		}
		this._srcBufImage = this._srcBufImage.getSubimage(startW,startH,width,height);
		return this;
	}

	/**
	 * 加入圖片型浮水印
	 * @param waterMark 要加入的浮水印
	 * @param alpha 透明度
	 * @param startX
	 * @param startY
	 */
	public ImageConvert addWaterMark(ImageConvert waterMark,float alpha,int startX,int startY){
		Graphics2D g = (Graphics2D)this._srcBufImage.getGraphics();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g.drawImage(waterMark._srcBufImage,startX,startY,null);
		g.dispose();
		return this;
	}

	/**
	 * 加入文字型浮水印
	 * @param waterMark 要加入的浮水印
	 * @param alpha 透明度
	 * @param startX
	 * @param startY
	 * @return
	 */
	public ImageConvert addWaterMark(Phase waterMark, float alpha, int startX, int startY){
		Graphics2D g = this._srcBufImage.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(waterMark.getFont() != null){
			g.setFont(waterMark.getFont());
		}

		if(waterMark.getColor() != null){
			g.setColor(waterMark.getColor());
		}
		new TextLayout(waterMark.getContext(), g.getFont(), g.getFontRenderContext()).draw(g, startX, startY);
		g.dispose();
		return this;
	}

	/**
	 * 存為檔案
	 * @param destUrl 輸出檔名
	 * @return
	 */
	public boolean saveTo(String destUrl){
		return this.saveToDisk(new File(destUrl));
	}

	/**
	 * 存為檔案
	 * @param destFile 輸出檔名
	 */
	public boolean saveToDisk(File destFile){
		try{
			this.saveTo(StreamKit.getFileOutputStream(destFile, false), ImageFacrory.getImageTypeByFileName(destFile), true);
		}catch(Exception ex){
			return false;
		}
		return destFile.exists() && destFile.length() > 0;
	}

	/**
	 * 將資料填入帶入的stream中
	 * @param os
	 * @param imageType
	 * @param needColseStream 函式結束後是否關閉stream
	 * @throws Exception
	 */
	public void saveTo(OutputStream os,String imageType,boolean needColseStream)throws Exception{
		try{
			if(this._hasChangQuality){
				try{
					if(this._hasChangDPI){
						ImageFacrory.getNewLossyImageInstance(imageType).setDpi(this._dpiX, this._dpiY).writer(os, this._srcBufImage,this._jpegQuality);
					}else{
						ImageFacrory.getNewLossyImageInstance(imageType).writer(os, this._srcBufImage,this._jpegQuality);
					}
				}catch(Exception e){
					ImageFacrory.getNewImageInstance(imageType).writer(os, this._srcBufImage);
				}
			}else{
				ImageFacrory.getNewImageInstance(imageType).writer(os, this._srcBufImage);
			}
		}finally{
			if(needColseStream && os != null){
				StreamKit.close(null, os);
			}
		}
	}

	/**
	 * 
	 * @param angle
	 * @return
	 */
	public ImageConvert rotate(int angle){
		Graphics2D g = (Graphics2D)this._srcBufImage.getGraphics();
		g.rotate(Math.toRadians(angle));
		g.drawImage(this._srcBufImage,0,0,null);
		g.dispose();
		return this;
	}
	/*
	public static void main(String[] arg) throws IOException, Exception{
		String msk = "c:\\w.png";
		String pic = "c:\\img.jpg";
		String text = "「我幸福嗎？」六十歲以前，廖偉志從來沒有懷疑過這個問題的答案。身為微風廣場董事長，他向來是朋友羨慕的對象，許多事業成功者往往家庭不圓滿，他卻兩者兼得，自認人生精采萬分。然而，六十歲之後的某一天，對於「幸福嗎？」他卻有了疑惑，隨著回顧過往、審視人生現況，在不斷地反覆自我詰問中，廖偉志為自己找到問題的答案，對幸福，有了全新的解釋。以下，是向來服膺英雄主義、霸氣凌人的廖偉志，對於人生、對於幸福的深刻體悟：";
		String save = "c:\\save.jpg";
		ImageConvert ic = new ImageConvert(new File(pic));
		ic.addWaterMark(new ImageConvert(new File(msk)), 0.8f, 0, 0);
		ic.addWaterMark(new Phase(text), 1f, 20, 20);
		ic.saveToDisk(new File(save));
	}
	*/
}