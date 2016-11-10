/*
 * 所有程式碼皆於 JDK 6 環境，以Eclipse開發
 * 
 * [History]
 * 20090628
 * 	add getFont()
 *  add getColor()
 *  add getContext()
 *  add creatFont(File fontFile,int fontSize)
 */
package hyweb.structure;
import hyweb.core.kit.StreamKit;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;

/**
 * @author A0074
 * @version 1.0.090807.β
 * @since xbox 1.0
 */
public class Phase implements Serializable{
	static final long serialVersionUID = -6086012801048199622L;
	protected Font _font;
	protected String _msg;
	protected Color _fontColor;
	public Phase(){
		super();
	}

	public Phase(String msg){
		this(msg, null);
	}

	public Phase(String msg,Font font){
		this(msg, font, Color.BLACK);
	}

	public Phase(String msg,Font font,Color fontColor){
		super();
		this._msg = msg;
		this._font = font;
		this._fontColor = fontColor;
	}

	public Font getFont(){
		return this._font;
	}

	public Color getColor(){
		return this._fontColor;
	}

	public String getContext(){
		return this._msg;
	}

	public static Font creatFont(File fontFile,int fontSize)throws Exception{
		InputStream is = null;
		try{
			is = new FileInputStream(fontFile);
			return Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, fontSize);
		}finally{
			StreamKit.close(is, null);
		}
	}
}