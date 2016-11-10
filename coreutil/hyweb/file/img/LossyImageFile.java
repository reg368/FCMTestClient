/*
 * Copyright (c) Monyem Li. All rights reserved.
 * Code licensed under the MIT License.(http://en.wikipedia.org/wiki/MIT_License)
 * You can find the last version at http://polatouche.googlecode.com
 * 
 * The project depend on JDK5+
 *
 * [History]
 * 20090511
 *  add writer(File dest, BufferedImage bImg, float quality)
 * 20090609
 *  add writer(OutputStream os, BufferedImage bImg,float quality)
 */
package hyweb.file.img;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
/**
 * @author Momyem
 * @version 1.0.090807.α
 * @since xBox 1.0
 */
public interface LossyImageFile extends ImageFile{
	public void writer(File dest, BufferedImage bImg, float quality) throws Exception;
	public void writer(OutputStream os, BufferedImage bImg,float quality) throws Exception;
	public LossyImageFile setDpi(int dpiX, int dpiY);
}