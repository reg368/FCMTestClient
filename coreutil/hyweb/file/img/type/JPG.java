/*
 * Copyright (c) Monyem Li. All rights reserved.
 * Code licensed under the MIT License.(http://en.wikipedia.org/wiki/MIT_License)
 * You can find the last version at http://polatouche.googlecode.com
 * 
 * The project depend on JDK5+
 * 
 * [History]
 * 20090511
 *  add reader(File src)
 *  add writer(File dest,BufferedImage bImg)
 * 20090514
 *  add writer(File dest,BufferedImage bImg,float quality)
 * 20090609
 *  add writer(OutputStream os,BufferedImage bImg)
 *  add writer(OutputStream os,BufferedImage bImg,float quality)
 */
package hyweb.file.img.type;

import hyweb.core.kit.ColorKit;
import hyweb.file.img.LossyImageFile;

import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * @author A0074
 * @version 1.0.090807
 * @since xBox 1.0
 */
class JPG implements LossyImageFile {
	public static String MIME_TYPE = "image/jpg";
	private int _dpiX;
	private int _dpiY;

	public JPG(){
		this(72, 72);
	}

	public JPG(int dpiX, int dpiY){
		super();
		this._dpiX = dpiX;
		this._dpiY = dpiY;
	}

	public JPG setDpi(int dpiX, int dpiY){
		this._dpiX = dpiX;
		this._dpiY = dpiY;
		return this;
	}

	/**
	 * 支援JDK支援色彩空間(RGB、灰階、YCbCr)、CMYK、YCbCrK
	 */
	public BufferedImage reader(File src) throws IOException {
		return this.reader(new FileInputStream(src));
	}

	public BufferedImage reader(InputStream is) throws IOException {
		ImageInputStream iis = null;
		BufferedImage bi = null;
		boolean isDefaultSuppose = true;
		try{
			iis = ImageIO.createImageInputStream(is);
			Iterator<ImageReader> it = ImageIO.getImageReaders(iis);
			if(it.hasNext()){
				ImageReader reader = it.next();
				ImageReadParam param = reader.getDefaultReadParam();
				reader.setInput(iis, true, true);
				try{
					bi = reader.read(0, param);
				}catch(IIOException iio){
					isDefaultSuppose = false;
				}
				if(!isDefaultSuppose){
					IIOMetadata metadata = reader.getImageMetadata(0);
					IIOMetadataNode iioNode = (IIOMetadataNode) metadata.getAsTree(metadata.getNativeMetadataFormatName());
					iioNode = (IIOMetadataNode)iioNode.getElementsByTagName("app14Adobe").item(0);
					Raster raster = reader.readRaster(0, reader.getDefaultReadParam());
					String transform =iioNode.getAttribute("transform");
					int w = raster.getWidth(), h = raster.getHeight();
					byte[] rgb = new byte[w * h * 3];
					if("2".equals(transform)){// YCbCrK or YCCK
						rgb = ColorKit.YCbCrK2RGB(rgb,
								raster.getSamples(0, 0, w, h, 0, (float[]) null), 
								raster.getSamples(0, 0, w, h, 1, (float[]) null), 
								raster.getSamples(0, 0, w, h, 2, (float[]) null), 
								raster.getSamples(0, 0, w, h, 3, (float[]) null)
								);
						raster = Raster.createInterleavedRaster(new DataBufferByte(rgb, rgb.length), w, h, w * 3, 3, new int[]{0, 1, 2}, null);
						ColorModel cm = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), false, true, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
						bi = new BufferedImage(cm, (WritableRaster)raster, true, null);
					}else{//CMYK
						ColorModel cm = new ComponentColorModel(ColorKit.getInstance(ColorKit.CS_CMYK), new int[] {8,8,8,8} , false, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
						bi = new BufferedImage(cm, (WritableRaster)raster, false, null);
					}
				}
				reader.dispose();
			}
		}finally{
			if(iis != null){iis.close();}
		}
		return bi;
	}

	public void writer(File dest, BufferedImage bImg) throws IOException {
		ImageIO.write(bImg, "jpg", dest);
	}

	public void writer(OutputStream os, BufferedImage bImg) throws IOException{
		ImageIO.write(bImg, "jpg", os);
	}

	public void writer(File dest, BufferedImage bImg,float quality) throws IOException{
		OutputStream os = null;
		try{
			os = new FileOutputStream(dest);
			this.writer(os, bImg, quality);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(os != null){
				os.close();
			}
		}
	}

	public void writer(OutputStream os,BufferedImage bImg,float quality) throws IOException{
		try{
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
			JPEGEncodeParam param = JPEGCodec.getDefaultJPEGEncodeParam(bImg);
			param.setDensityUnit(JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
			param.setQuality(quality,true);
			param.setXDensity(this._dpiX);
			param.setYDensity(this._dpiY);
			encoder.encode(bImg,param);
			os.flush();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(os != null){
				os.close();
			}
		}
	}
}
