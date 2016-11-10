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
 *  add writer(OutputStream os, BufferedImage bImg)
 */
package hyweb.file.img;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
/**
 * 
 * @author Momyem
 * @version 1.0.090807
 * @since xBox 1.0
 */
public interface ImageFile {
	/**
	 * 
	 * @param src
	 * @return
	 * @throws IOException
	 */
	public BufferedImage reader(File src) throws IOException;

	/**
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public BufferedImage reader(InputStream is) throws IOException;

	/**
	 * 
	 * @param dest
	 * @param bImg
	 * @throws IOException
	 */
	public void writer(File dest, BufferedImage bImg) throws IOException;

	/**
	 * 
	 * @param os
	 * @param bImg
	 * @throws IOException
	 */
	public void writer(OutputStream os, BufferedImage bImg) throws IOException;
}
