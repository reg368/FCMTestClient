/*
 * 所有程式碼皆於 JDK 6 環境，以Eclipse開發
 *
 * [History]
 * 20090512
 *  add getNewImageInstance(File src)
 *  add getNewImageInstance(String imgType)
 * 20090514
 *  add getNewLossyImageInstance(File src)
 */
package hyweb.file.img.type;

import hyweb.file.img.ImageFile;
import hyweb.file.img.LossyImageFile;

import java.io.File;
import java.io.IOException;
/**
 * @author A0074
 * @version 3.0.090807
 * @since xbox 1.0
 */
public class ImageFacrory {
	public final static String IMAGE_JPG = "JPG";
    public final static String IMAGE_BMP = "BMP";
    public final static String IMAGE_PNG = "PNG";
    public final static String IMAGE_TIF = "TIF";

	public ImageFacrory(){
		super();
	}

	/**
	 * 依照檔名判斷檔案類型
	 * @param src
	 * @return
	 * @throws Exception
	 */
	public static String getImageTypeByFileName(File src) throws Exception{
		String fileName = src.getName();
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		if("jpg_jpeg_jpe_jfif".indexOf(fileType) != -1){
			return ImageFacrory.IMAGE_JPG;
		}else if("bmp_bmpw".indexOf(fileType) != -1){
			return ImageFacrory.IMAGE_BMP;
		}else if("png".indexOf(fileType) != -1){
			return ImageFacrory.IMAGE_PNG;
		}else if("tif_tiff".indexOf(fileType) != -1){
			return ImageFacrory.IMAGE_TIF;
		}else{
			throw new Exception("not suppose this type : " + fileType);
		}
	}


	/**
	 * 
	 * @param src
	 * @return
	 * @throws NotDefineException
	 */
	public static ImageFile getNewImageInstance(File src) throws Exception{
		String imgType = ImageFacrory.getImageTypeByFileName(src);
		try{
			Class<?> c = Class.forName("hyweb.file.img.type." + imgType);
			return (ImageFile)c.newInstance();
		}catch(Exception ex){
			throw new Exception("not suppose this type : " + imgType);
		}
	}

	/**
	 * 
	 * @param imgType
	 * @return
	 * @throws NotDefineException
	 */
	public static ImageFile getNewImageInstance(String imgType) throws IOException{
		if(ImageFacrory.IMAGE_JPG.equalsIgnoreCase(imgType)){
			return new JPG();
		}else if(ImageFacrory.IMAGE_BMP.equalsIgnoreCase(imgType)){
			return new BMP();
		}else if(ImageFacrory.IMAGE_PNG.equalsIgnoreCase(imgType)){
			return new PNG();
		}else if(ImageFacrory.IMAGE_TIF.equalsIgnoreCase(imgType)){
			return new TIF();
		}else{
			throw new IOException("not suppose this type : " + imgType);
		}
	}

	/**
	 * 
	 * @param src
	 * @return
	 * @throws NotDefineException
	 */
	public static LossyImageFile getNewLossyImageInstance(File src) throws Exception{
		String imgType = ImageFacrory.getImageTypeByFileName(src);
		if(ImageFacrory.IMAGE_JPG.equals(imgType)){
			return (LossyImageFile)getNewImageInstance(ImageFacrory.IMAGE_JPG);
		}else{
			throw new Exception("not suppose this type : " + imgType);
		}
	}

	/**
	 * 
	 * @param imageType
	 * @return
	 * @throws Exception
	 */
	public static LossyImageFile getNewLossyImageInstance(String imageType) throws Exception{
		if(ImageFacrory.IMAGE_JPG.equals(imageType)){
			return (LossyImageFile)getNewImageInstance(ImageFacrory.IMAGE_JPG);
		}else{
			throw new Exception("not suppose " + imageType + " type");
		}
	}
}