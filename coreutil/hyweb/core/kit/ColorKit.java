/*
 * Copyright (c) Monyem Li. All rights reserved.
 * Code licensed under the MIT License.(http://en.wikipedia.org/wiki/MIT_License)
 * You can find the last version at http://polatouche.googlecode.com
 * 
 * The project depend on JDK5+
 *
 * [History]
 * 20090709
 *   add getRandomColor()
 * 	  add getRGBValue(int rgb)
 * 20091110
 *   add RGBtoHSB(int rgb)
 *   add HSBtoRGB(float[] hsb)
 * 	  add RGBtoHSL(int rgb)
 *   add RGBtoYUV(int rgb)
 *   add YUVtoRGB(float[] yuv)
 *  20120323
 *   add getInstance(int colorspace)
 */
package hyweb.core.kit;

import java.awt.Color;
import java.awt.color.ColorSpace;
import java.awt.image.WritableRaster;

/**
 * <pre>
 * 有關各種色彩的基本操作，效能是程式撰寫的唯一考量，禁止繼承，禁止用LOGGER
 * </pre>
 * 
 * @author Monyem
 * @version 1.0.091111
 * @since xBox 1.0
 */
public final class ColorKit {
	public final static int CS_CMYK = ColorSpace.CS_LINEAR_RGB + 1;
	public final static int YCCK = ColorSpace.CS_LINEAR_RGB + 2;
	final private static ColorSpace[] INSTANCES = new ColorSpace[2];
	final private static int BASE_INDEX = ColorKit.CS_CMYK;

	private ColorKit(){
		super();
	}

	public static ColorSpace getInstance(int colorspace){
		if(colorspace >= ColorKit.BASE_INDEX){
			int idx = colorspace - ColorKit.BASE_INDEX;
			if (ColorKit.INSTANCES[idx] == null){
				String[] className = {"xbox.core.graphics.color.ColorSpaceCMYK","xbox.core.graphics.color.ColorSpaceYCCK"};
				try{
					ColorKit.INSTANCES[idx] = (ColorSpace) ClassKit.createNewInstance(className[idx], null, null);
				}catch(Exception ex){
					throw new RuntimeException(ex);
				}
				
			}
			return ColorKit.INSTANCES[idx];
		}else{
			return ColorSpace.getInstance(colorspace);
		}
	}

	/**
	 * 隨機取得一種顏色
	 * 
	 * @return
	 */
	public Color getRandomColor() {
		return new Color((int) ((Math.random() * 1000) % 255), (int) ((Math.random() * 1000) % 255), (int) ((Math.random() * 1000) % 255));
	}

	/**
	 * 
	 * @param rgb
	 * @return
	 */
	public static int[] getRGBValue(int rgb){
		return new int[]{((rgb & 0x00ff0000) >> 16),((rgb & 0x0000ff00) >> 8),(rgb & 0x000000ff)};
	}

	/**
	 * http://zh.wikipedia.org/wiki/HSV%E8%89%B2%E5%BD%A9%E5%B1%9E%E6%80%A7%E6%A8%A1%E5%BC%8F
	 * 1978年提出的色彩空間，由色相(Hue)、飽和度(Saturation)、明度(Brightness)組成，又稱HSV(Value)，可更貼近的讓人們對顏色的描述，GIMP調色盤採用此空間。
	 * java 已經實做，不另外再實作。
	 * @param rgb
	 * @return float[]{0~359,0~100,0~100}
	 * @see java.awt.Color
	 */
	public static int[] RGBtoHSB(int rgb){
		float[] tmp = new float[3];
		tmp = Color.RGBtoHSB(((rgb & 0x00ff0000) >> 16),((rgb & 0x0000ff00) >> 8),(rgb & 0x000000ff), tmp);
		int[] hsb = new int[3];
		hsb[0] = NumberKit.toInt(tmp[0] * 360f, NumberKit.CARRY_ROUNDING);
		hsb[1] = NumberKit.toInt(tmp[1] * 100f, NumberKit.CARRY_ROUNDING);
		hsb[2] = NumberKit.toInt(tmp[2] * 100f, NumberKit.CARRY_ROUNDING);
		return hsb;
	}

	/**
	 * java 已經實做，不另外再實作。
	 * @param hsb float[]{0~359,0~100,0~100}
	 * @return
	 */
	public static int HSBtoRGB(final float[] hsb){
		return Color.HSBtoRGB((hsb[0] / 360f), (hsb[1] / 100f), (hsb[2] / 100f));
	}

	/**
	 * http://zh.wikipedia.org/zh-tw/HSL%E5%92%8CHSV%E8%89%B2%E5%BD%A9%E7%A9%BA%E9%97%B4
	 * 由色相(Hue)、飽和度(Saturation)、亮度(Lightness)組成，是CCS3採用的色彩空間，windows調色盤採用此色彩空間。
	 * @param rgb
	 * @return float[]{0~359,0~100,0~100}
	 */
	public static int[] RGBtoHSL(int rgb) {
		int[] hsl = new int[3];
		float r = ((rgb >> 16) & 0xff) / 255f;
		float g = ((rgb >> 8) & 0xff) / 255f;
		float b = (rgb & 0xff) / 255f;
		float max = r > g ? (r > b ? r : b) : (g > b ? g : b);
		float min = r < g ? (r < b ? r : b) : (g < b ? g : b);
		float delta = max - min;
		if (r == max) {
			hsl[0] = NumberKit.toInt(((g - b) / delta * 60 + 720) % 360, NumberKit.CARRY_ROUNDING);
		} else if (g == max) {
			hsl[0] = NumberKit.toInt((b - r) / delta * 60 + 120, NumberKit.CARRY_ROUNDING);
		} else if (b == max) {
			hsl[0] = NumberKit.toInt((r - g) / delta * 60 + 240, NumberKit.CARRY_ROUNDING); 
		}
		float l = (max + min) / 2;
		if (max == 0) {
			hsl[1] = 0;
		} else {
			if(l <= 0f){
				hsl[1] = 0;
			}else if(l <= 0.5f){
				hsl[1] = NumberKit.toInt(delta /(2 * l) * 100, NumberKit.CARRY_ROUNDING);
			}else{
				hsl[1] = NumberKit.toInt(delta /(2 * (1 - l)) * 100, NumberKit.CARRY_ROUNDING);
			}
		}
		hsl[2] = NumberKit.toInt( l * 100 , NumberKit.CARRY_ROUNDING);
		return hsl;
	}

	/**
	 * http://zh.wikipedia.org/wiki/YUV
	 * 明亮度(Luminance)、色度(Chrominance)、濃度(Chroma)，YUV包含多種變形，大體上每個像素都少於24位元。
	 * @param rgb
	 * @return
	 */
	public static float[] RGBtoYUV(int rgb){
		float[] yuv = new float[3];
		float r = ((rgb >> 16) & 0xff) / 255f;
		float g = ((rgb >> 8) & 0xff) / 255f;
		float b = (rgb & 0xff) / 255f;
		yuv[0] = (0.299f * r) + (0.587f * g) + (0.114f * b);
		yuv[1] = (-0.169f * r) - (0.331f * g) + (0.5f * b);
		yuv[2] = (0.5f * r) - (0.419f * g) - (0.081f * b);
		return yuv;
	}

	/**
	 * 
	 * @param yuv
	 * @return
	 */
	public static int YUVtoRGB(final float[] yuv){
		int[] rgb = new int[3]; 
		rgb[0] = NumberKit.toInt(yuv[0] + (1.140f * yuv[2]), NumberKit.CARRY_ROUNDING);
		rgb[1] = NumberKit.toInt(yuv[0] - (0.394f * yuv[1]) - (0.581f * yuv[2]), NumberKit.CARRY_ROUNDING);
		rgb[2] = NumberKit.toInt(yuv[0] + (2.028f * yuv[1]), NumberKit.CARRY_ROUNDING);
		return (rgb[0] << 16) + (rgb[1] << 8) + rgb[2]; 
	}

	/**
	 * 
	 * @param rgb
	 * @param c
	 * @param m
	 * @param y
	 * @param k
	 * @return
	 */
	public static byte[] CMYK2RGB(byte[] rgb, int[] c, int[] m, int[] y, int[] k){
		for (int i = 0, imax = c.length, base = 0; i < imax; i++, base += 3) {
			int c1 = 255 - c[i];
			int m1 = 255 - m[i];
			int y1 = 255 - y[i];
			int k1 = 255 - k[i];
			float k2 = k[i] / 255f;
			rgb[base] = (byte) (255 - Math.min(255f, c1 * k2 + k1));
			rgb[base + 1] = (byte) (255 - Math.min(255f, m1 * k2 + k1));
			rgb[base + 2] = (byte) (255 - Math.min(255f, y1 * k2 + k1));
		}
		return rgb;
	}

	/**
	 * 
	 * @param rgb
	 * @param yAry
	 * @param cbAry
	 * @param crAry
	 * @param kAry
	 * @return
	 */
	public static byte[]  YCbCrK2RGB(byte[] rgb, float[] yAry, float[] cbAry, float[] crAry, float[] kAry){
		for (int i = 0, imax = yAry.length, base = 0; i < imax; i++, base += 3) {
			float k = 220 - kAry[i], y = 255 - yAry[i], cb = 255 - cbAry[i], cr = 255 - crAry[i];
			double val = y + 1.402 * (cr - 128) - k;
			val = (val - 128) * .65f + 128;
			rgb[base] = val < 0.0 ? (byte) 0 : val > 255.0 ? (byte) 0xff : (byte) (val + 0.5);
			val = y - 0.34414 * (cb - 128) - 0.71414 * (cr - 128) - k;
			val = (val - 128) * .65f + 128;
			rgb[base + 1] = val < 0.0 ? (byte) 0 : val > 255.0 ? (byte) 0xff : (byte) (val + 0.5);
			val = y + 1.772 * (cb - 128) - k;
			val = (val - 128) * .65f + 128;
			rgb[base + 2] = val < 0.0 ? (byte) 0 : val > 255.0 ? (byte) 0xff : (byte) (val + 0.5);
		}
		return rgb;
	}

	public static void YCCKtoCMYK(WritableRaster rast, boolean invertedColors){
	    int w = rast.getWidth(), h = rast.getHeight();
	    double c,m,y,k;
	    double Y,Cb,Cr,K;
	 
	    //turn YCCK in Raster to CYMK using formula
	    int[] pixels = null;
	    for(int row = 0; row < h; row++) {
	        pixels = rast.getPixels(0,row,w,1,pixels);
	 
	        for(int i = 0; i < pixels.length; i+=4) {
	            Y  = pixels[i];
	            Cb = pixels[i+1];
	            Cr = pixels[i+2];
	            K  = pixels[i+3];
	 
	            c =255 - (Y + 1.402*Cr - 179.456);
	            m =255 - (Y - 0.34414*Cb - 0.71414*Cr + 135.45984);
	            y =255 - (Y + 1.7718d*Cb - 226.816);
	            k = K;
	 
	            //clamp
	            c = Math.min(255,Math.max(0,c));
	            m = Math.min(255,Math.max(0,m));
	            y = Math.min(255,Math.max(0,y));
	 
	            if(invertedColors) {
	                pixels[i]   = (byte) (255-c);
	                pixels[i+1] = (byte) (255-m);
	                pixels[i+2] = (byte) (255-y);
	                pixels[i+3] = (byte) (255-k);
	            }else {
	                pixels[i]   = (byte) c;
	                pixels[i+1] = (byte) m;
	                pixels[i+2] = (byte) y;
	                pixels[i+3] = (byte) k;
	            }
	        }
	        rast.setPixels(0,row,w,1,pixels);
	    }
	}

}
