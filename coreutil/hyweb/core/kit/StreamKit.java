package hyweb.core.kit;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PushbackInputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
/**
 * 各種有關串流、頻道的基本操作，效能是程式撰寫的唯一考量，將忠實的丟出各種Exception
 * 全部method都沒有鎖synchronized，請呼叫時自行注意
 * @author A0074
 * @version 1.0
 * @since xBox 1.0
 */
public final class StreamKit {
	static final int CHANNEL_SIZE = 1024 << 6;
	static final int BUFFER_SIZE = 1024 << 3;
	static final int CHAR_BUFFER_SIZE = 1024 << 2;
	private StreamKit(){
		super();
	}

	/**
	 * 使用directMap 進行channel 複製
	 * @param in 來源頻道
	 * @param out 目的頻到
	 * @throws IOException
	 */
	public static long copy(ReadableByteChannel in, WritableByteChannel out) throws IOException{
		return StreamKit.nioCopyWithMoreCPU(in, out);
	}
	/**
	 * CPU量較大的NIO,使用directMap 進行channel 複製
	 * @param in 來源頻道
	 * @param out 目的頻到
	 * @return 傳輸量
	 * @throws IOException
	 */
	public static long nioCopyWithMoreCPU(ReadableByteChannel in, WritableByteChannel out) throws IOException{
		ByteBuffer buffer = ByteBuffer.allocateDirect(StreamKit.CHANNEL_SIZE);
		long size = 0;
		buffer.flip();
		while(in.read(buffer) != -1){
			buffer.flip();
			size += out.write(buffer);
			buffer.compact();
		}
		buffer.flip();
		while(buffer.hasRemaining()){
			size += out.write(buffer);
		}
		return size;
	}

	/**
	 * IO量較大的NIO,使用directMap 進行channel 複製
	 * @param in 來源頻道
	 * @param out 目的頻到
	 * @return 傳輸量
	 * @throws IOException
	 */
	public static long nioCopyWithMoreIO(ReadableByteChannel in, WritableByteChannel out) throws IOException{
		ByteBuffer buffer = ByteBuffer.allocateDirect(StreamKit.CHANNEL_SIZE);
		long size = 0;
		while(in.read(buffer)!=-1){
			buffer.flip();
			while(buffer.hasRemaining()){
				size += out.write(buffer);
			}
			buffer.clear();
		}
		return size;
	}

	/**
	 * RandomAccessFile間的複製
	 * @param in 來源
	 * @param out 目的
	 * @param useNio 是否使用 MappedByteBuffer. 進行多檔複製時建議選 true
	 * @throws IOException
	 */
	public static void copy(RandomAccessFile in, RandomAccessFile out, boolean useNio) throws IOException{
		out.setLength(0);
		if(useNio){
			FileChannel fci = null, fco = null;
			try{
				fci = in.getChannel();
				fco = out.getChannel();
				long size = fci.size();
				MappedByteBuffer mbbi = fci.map(FileChannel.MapMode.READ_ONLY, 0, size);  
				MappedByteBuffer mbbo = fco.map(FileChannel.MapMode.READ_WRITE, 0, size);
				for (int i = 0; i < size; i++) {  
				    byte b = mbbi.get(i);  
				    mbbo.put(i, b);  
				}
			}finally{
				StreamKit.close(fci, fco);
			}
		}else{
			byte[] buffer = new byte[StreamKit.BUFFER_SIZE];
			int n = 0;
			while (-1 != (n = in.read(buffer))) {
				out.write(buffer, 0, n);
			}  
		}  
	}

	/**
	 * 串流複製
	 * @param in 來源串流
	 * @param out 目的串流
	 * @see InputStream、OutputStream
	 */
	public static void copy(InputStream in, OutputStream out) throws IOException{
		byte[] buffer = new byte[StreamKit.BUFFER_SIZE];
		int n = 0;
		while (-1 != (n = in.read(buffer))) {
			out.write(buffer, 0, n);
		}
	}

	/**
	 * 檔案複製到StringBuffer中,也可採用StringWriter，達到將stream複製到String的效果。
	 * @param in 來源
	 * @param out 輸出
	 * @param lineSeparator 自訂的換行符號
	 * @see BufferedReader、StringBuffer
	 */
	public static void copy(BufferedReader in, StringBuffer out, String lineSeparator) throws IOException{
		String line = null;	
		while(null != (line = in.readLine())){
			out.append(line).append(lineSeparator);
		}
	}

	/**
	 * 檔案複製到StringBuffer中,也可採用StringWriter，達到將stream複製到String的效果。
	 * @param in 來源
	 * @param out 輸出
	 * @param lineSeparator 自訂的換行符號
	 * @see BufferedReader、StringBuffer
	 */
	public static void copy(BufferedReader in, StringBuilder out, String lineSeparator) throws IOException{
		String line = null;	
		while(null != (line = in.readLine())){
			out.append(line).append(lineSeparator);
		}
	}

	/**
	 * 複製文字檔，可帶入不同編碼進行轉碼
	 * @param in 來源
	 * @param out 目的
	 * @throws IOException
	 * @see InputStream、BufferedWriter
	 */
	public static void copy(Reader in, Writer out) throws IOException{
		char[] buffer = new char[CHAR_BUFFER_SIZE];
		int bytesRead = -1;
		while((bytesRead = in.read(buffer)) != -1){
			out.write(buffer, 0, bytesRead);
		}
		out.flush();
	}

	/**
	 * 關閉channel
	 * @param c1 Channe In
	 * @param c2 Channel out
	 * @return
	 */
	public static boolean close(Channel c1, Channel c2){
		boolean success = true;
		if(c1 != null){
			try{
				c1.close();
			}catch(IOException ex){
				success =  false;
			}
		}
		if(c2 != null){
			try{
				c2.close();
			}catch(IOException ex){
				success =  false;
			}
		}
		return success;
	}

	/**
	 * 關閉 RandomAccessFile
	 * @param raf1 RandomAccessFile In
	 * @param raf2 RandomAccessFile Out
	 * @return
	 */
	public static boolean close(RandomAccessFile raf1, RandomAccessFile raf2){
		boolean success = true;
		if(raf1 != null){
			try{
				raf1.close();
			}catch(IOException ex){
				success =  false;
			}
		}
		if(raf2 != null){
			try{
				raf2.close();
			}catch(IOException ex){
				success =  false;
			}
		}
		return success;
	}
	

	/**
	 * 關閉串流 
	 * @param is 要關閉的InputStream
	 * @param os 要關閉的OutputStream
	 * @return false if close throws exception
	 */
	public static boolean close(InputStream is, OutputStream os){
		boolean success = true;
		if(is != null){
			try{
				is.close();
			}catch(IOException ex){
				success = false;
			}
		}
		if(os != null){
			try{
				os.close();
			}catch(IOException ex){
				success = false;
			}
		}
		return success;
	}

	/**
	 * 關閉串流 
	 * @param reader 要關閉的Reader
	 * @param writer 要關閉的Writer
	 * @return false if close throws exception
	 */
	public static boolean close(Reader reader, Writer writer){
		boolean success = true;
		if(reader != null){
			try{
				reader.close();
			}catch(IOException ex){
				success = false;
			}
		}
		if(writer != null){
			try{
				writer.close();
			}catch(IOException ex){
				success = false;
			}
		}
		return success;
	}

	/**
	 * 取得支援utf-8 with bom 的InputStreamReader 
	 * @param file 文字檔
	 * @param encoding 檔案編碼
	 */
	public static Reader getReader(File file, String encoding) throws IOException{
		if(! EnvKit.isSuppose(6) && "UTF-8".equals(encoding.toUpperCase())){
			PushbackInputStream srcStream = new PushbackInputStream(new FileInputStream(file), 4);
			byte[] bom = new byte[3];
			int totalReadByte = srcStream.read(bom);
			if((totalReadByte == 3) && ((byte)0xEF == bom[0]) && ((byte)0xBB == bom[1]) && ((byte)0xBF == bom[2])){//EF BB BF
			}else{
				srcStream.unread(bom);
			}
			return new InputStreamReader(srcStream, encoding);			
		}
		return new InputStreamReader(new FileInputStream(file), encoding);
	}

	/**
	 *  快速取得 OutputStreamWriter
	 * @param file 來源檔案
	 * @param append 是否繼續寫在原檔案後面
	 * @param encoding 編碼方式
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public static Writer getWriter(File file, boolean append, String encoding) throws UnsupportedEncodingException, IOException{
		return new OutputStreamWriter(StreamKit.getFileOutputStream(file, append), encoding);
	}

	/**
	 * 快速取得 BufferedWriter
	 * @param file 來源檔案
	 * @param append 是否繼續寫在原檔案後面
	 * @param encoding 編碼方式
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public static BufferedWriter getBufferedWriter(File file, boolean append, String encoding) throws UnsupportedEncodingException, IOException{
		return new BufferedWriter(StreamKit.getWriter(file, append, encoding));
	}

	/**
	 * 自動建立不存在目錄, 並取得FileOutputStream
	 * @param file 要建立OutputStream的檔案;
	 * @param append 是否要加在檔案後面
	 * @see FileOutputStream
	 */
	public static OutputStream getFileOutputStream(File file, boolean append) throws IOException{
		File parent = file.getParentFile();
		if(! parent.exists()){
			parent.mkdirs();
		}
		return new FileOutputStream(file,append);
	}
}
