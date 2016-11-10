package hyweb.file.img.type;

import hyweb.file.img.ImageFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public class BMP implements ImageFile{
	public static String MIME_TYPE = "image/bmp";
	public BMP(){
		super();
	}

	@Override
	public BufferedImage reader(File src) throws IOException {
		return ImageIO.read(src);
	}

	@Override
	public BufferedImage reader(InputStream is) throws IOException {
		return ImageIO.read(is);
	}

	@Override
	public void writer(File dest,BufferedImage bImg) throws IOException {
		ImageIO.write(bImg, "bmp", dest);
	}

	@Override
	public void writer(OutputStream os,BufferedImage bImg) throws IOException{
		ImageIO.write(bImg, "bmp", os);
	}
}
