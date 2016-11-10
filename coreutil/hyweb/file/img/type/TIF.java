/*
 * 所有程式碼皆於 JDK 6 環境，以Eclipse開發
 *
 * [History]
 * 20090514
 *  add reader(File src)
 *  add writer(File dest,BufferedImage bImg)
 * 20090609
 *  add writer(OutputStream os,BufferedImage bImg)
 */
package hyweb.file.img.type;

import hyweb.file.img.ImageFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

/**
 * 需額外安裝jai
 * @author Momyem
 * @version 3.0.090807
 * @since xbox 1.0
 * @since JAI 1.1
 */

class TIF implements ImageFile {
	public static String MIME_TYPE = "image/tiff";

	@Override
	public BufferedImage reader(File src) throws IOException {
		ImageInputStream iis = ImageIO.createImageInputStream(src);
		Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
		ImageReader reader = readers.next();
		reader.setInput(iis, true);
		return reader.read(0);
	}

	@Override
	public BufferedImage reader(InputStream is) throws IOException {
		return ImageIO.read(is);
	}

	@Override
	public void writer(File dest, BufferedImage bImg) throws IOException {
		ImageOutputStream ios = ImageIO.createImageOutputStream(dest);
		ImageWriter writer = ImageIO.getImageWritersByFormatName("tif").next();
		writer.setOutput(ios);
		writer.write(bImg);
	}

	@Override
	public void writer(OutputStream os,BufferedImage bImg) throws IOException{
		ImageOutputStream ios = ImageIO.createImageOutputStream(os);
		ImageWriter writer = ImageIO.getImageWritersByFormatName("tif").next();
		writer.setOutput(ios);
		writer.write(bImg);
	}
}