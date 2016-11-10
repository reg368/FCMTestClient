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
 *  add writer(File dest, BufferedImage bImg)
 * 20090609
 * 	add writer(OutputStream os, BufferedImage bImg)
 */
package hyweb.file.img.type;

import hyweb.file.img.ImageFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

/**
 * @author A0074
 * @version 1.0.090807
 * @since xBox 1.0
 */
class PNG implements ImageFile {
	public static String MIME_TYPE = "image/png";
	public PNG(){
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
	public void writer(OutputStream os, BufferedImage bImg) throws IOException{
		ImageIO.write(bImg, "png", os);
	}

	@Override
	public void writer(File dest, BufferedImage bImg) throws IOException {
		ImageIO.write(bImg, "png", dest);
	}
}
