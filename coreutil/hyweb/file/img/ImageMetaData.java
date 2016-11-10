/*
 * 所有程式碼皆於 JDK 6 環境，以Eclipse開發
 * 
 * [History]
 * 20090107
 *  add getBufferedImage()
 *  
 */
package hyweb.file.img;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.Properties;

import hyweb.file.img.type.ImageFacrory;

/**
 * @author A0074
 * @version 1.0.090807.α
 * @since xbox 1.0
 * @see BufferedImage,Graphics2D
 */
public class ImageMetaData {
	private File _file; 
	protected BufferedImage _srcBufImage;
	protected Properties _exif;
	protected Properties _xmp;
	public ImageMetaData(String src)throws Exception{
		this(new File(src));
	}
	
	public ImageMetaData(File src)throws Exception{
		this(ImageFacrory.getNewImageInstance(src).reader(src));
		this._file = src;
	}

	protected ImageMetaData(BufferedImage srcImg){
		super();
		this._srcBufImage = srcImg;
		this._exif = null;
		this._xmp = null;
		this._file = null;
	}

	/**
	 * 取得目前操作的 BufferedImage 物件
	 */
	public BufferedImage getBufferedImage(){
		return this._srcBufImage;
	}

	/**
	 * 取得 Clone BufferedImage
	 * @return
	 */
	public BufferedImage getCloneBufferedImage() {
		ColorModel cm = this._srcBufImage.getColorModel();
		WritableRaster raster = this._srcBufImage.copyData(null);
		return new BufferedImage(cm, raster, cm.isAlphaPremultiplied(), null);
	}

	/**
	 * 
	 * @return null if file is null
	 */
	public Properties getEXIF(){
		if(this._file == null){
			if(this._exif == null){
				this.initEXIF();
			}
			return this._exif;
		}else{
			return null;
		}
	}

	/**
	 * 
	 * @return null if file is null
	 */
	public Properties getXMP(){
		if(this._file == null){
			if(this._xmp == null){
				this.initXMP();
			}
			return this._xmp;
		}else{
			return null;
		}
	}

	protected void initEXIF(){
		
	}

	protected void initXMP(){
		
	}
}