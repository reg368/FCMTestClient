package hyweb.core.kit;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Vector;

/**
 * 各種有關檔案（File、URL）的基本操作
 * FileChannel並沒有比較快，所以複製檔案採用RandomAccessFile傳輸
 * @author A0074
 * @version 1.0.120718
 * @since xBox 1.0
 */
public final class FileKit {
	public static final String LINE_SEPARATOR_WINDOWS = "\r\n";
	public static final String LINE_SEPARATOR_UNIX = "\n";
	public static final String LINE_SEPARATOR_MAC = "\r";
	public static final String LINE_SEPARATOR_HTML = "<br />";
	public static final int ENCODING_UNKNOW = -1;
	public static final int ENCODING_ANSI = 0;
	public static final int ENCODING_UTF_8 = 1;
	public static final int ENCODING_UTF_8_BOM = 2;
	public static final int ENCODING_UTF_16_LITTLE_ENDIAN = 3;//UCS-2
	public static final int ENCODING_UTF_16_BIG_ENDIAN = 4;//UCS-2
	public static final int ENCODING_UTF_32_LITTLE_ENDIAN = 5;//UCS-4
	public static final int ENCODING_UTF_32_BIG_ENDIAN = 6;//UCS-4
	public static final String ZIP_SEPARATOR = System.getProperty("file.separator");

	static final long LARGE_FILE_SIZE = 1024 * 1024 * 10;
	private FileKit(){
		super();
	}

	/**
	 * 以二進制複製檔案或完整資料夾
	 * @param srcFile 來源檔案
	 * @param destFile 目的檔案
	 * @return true 檔案完整複製
	 * @see File
	 */
	public static boolean copy(File srcFile, File destFile){
		if(srcFile.getPath().equals(destFile.getPath())){
			throw new RuntimeException("Source and destination are the same");
		}
		RandomAccessFile in = null;  
		RandomAccessFile out = null;  
		try{
			if(srcFile.isDirectory()){
				return FileKit.copyDirectory(srcFile,destFile);
			}else{
				in = new RandomAccessFile(srcFile, "r");
				out = new RandomAccessFile(destFile, "rw");
				StreamKit.copy(in, out, srcFile.length() > FileKit.LARGE_FILE_SIZE);
				return srcFile.length() == destFile.length();
			}
		}catch(Exception ex){
			ex.printStackTrace(System.out);
			return false;
		}finally{
			StreamKit.close(in, out);
		}
	}

	/**
	 * 以Reader、Writer複製檔案，內含synchronized
	 * @param srcFile 來源檔案
	 * @param destFile 目的檔案
	 * @param srcEnCoding
	 * @param destEnCoding
	 * @see File
	 */
	public static boolean copy(File srcTextFile, File destTextFile, String srcEnCoding, String destEnCoding){
		BufferedReader br = null;
    	BufferedWriter bw = null;
    	try{
    		br = new BufferedReader(StreamKit.getReader(srcTextFile, srcEnCoding),StreamKit.BUFFER_SIZE);
    		bw = new BufferedWriter(new OutputStreamWriter(StreamKit.getFileOutputStream(destTextFile,false),destEnCoding),StreamKit.BUFFER_SIZE);
    		StreamKit.copy(br, bw);
    	}catch(Exception ex){
    		return false;
    	}finally{
    		StreamKit.close(br, bw);
    	}
    	return destTextFile.length() != 0;
	}

	/**
	 * 以二進制複製資料夾
	 * @param srcFile 來源資料夾
	 * @param destFile 目的資料夾
	 * @return true 資料夾完整複製
	 * @see File
	 */
	private static boolean copyDirectory(File srcFile, File destFile){
		if(srcFile.isDirectory() && !srcFile.toString().equals(destFile.toString())){
			InputStream is = null;
			OutputStream os = null;
			try{
				List<File> dirList = new Vector<File>(100);
				dirList.add(srcFile);
				int totalDir = 0;
				int srcBaseUrlLength = srcFile.getPath().length();
				String destBaseUrl = destFile.getPath();
				while(true){
					if(dirList.size() < totalDir){
						break;
					}else{
						for(File file : dirList.get(totalDir ++).listFiles()){
							if(file.isDirectory()){
								dirList.add(file);
							}else{
								is = new FileInputStream(file);
								os = StreamKit.getFileOutputStream(new File(destBaseUrl + file.toString().substring(srcBaseUrlLength,file.toString().length())),false);
								StreamKit.copy(is,os);
								StreamKit.close(is,os);
							}
						}
					}
				}
				return true;
			}catch(Exception ex){
				return false;
			}finally{
				StreamKit.close(is,os);
			}
		}else{
			return false;
		}
	}

	/**
	 * 取出檔案的內容
	 * @param file 對象
	 * @param encoding 編碼
	 */
	public static String toString(File txtFile,String encoding) throws Exception{
    	return toString(txtFile,encoding,System.getProperty("line.separator"));
    }

	/**
	 * 取出檔案的內容
	 * @param file 對象
	 * @param encoding 編碼
	 * @param lineSeparator 換行符號
	 */
	public static String toString(File txtFile,String encoding,String lineSeparator) throws IOException{
		StringBuffer re = new StringBuffer(2048);
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(StreamKit.getReader(txtFile, encoding),StreamKit.BUFFER_SIZE);
			StreamKit.copy(reader,re,lineSeparator);
		}finally{
			if(reader != null){reader.close();}
		}
		return re.toString();
	}

	/**
	 * 取得該目錄下所有的檔案，包含所有子目錄
	 * @param directory 啟始目錄
	 * @param fileList 檔案列表
	 */
	public static void getFileList(File directory,List<File> fileList){
		if(directory != null && directory.exists() && directory.isDirectory()){
			File[] files = directory.listFiles();
			if(files != null){
				for(File file : files){
					if(file.isDirectory()){
						FileKit.getFileList(file,fileList);
					}else{
						fileList.add(file);
					}
				}
			}
		}
	}


	/**
	 * deep delete
	 * @param file
	 */
	public static void delete(File file){
		String[] files = file.list();
		for(int i = 0; i < files.length; i++) {
			File subfile = new File(file, files[i]);
			if(subfile.isDirectory()){
				FileKit.delete(subfile);
			}
			subfile.delete();
		}
		file.delete();
	}

	/**
	 * 以相對路徑取得資源
	 * @param c 以哪個class 當相對路徑
	 * @param relativePath 在jar內會以jar的/為基礎;非jar會以磁碟路徑為基礎路徑
	 * @return
	 * @throws MalformedURLException 
	 */
	public static URL getUrlByInitPath(final Class<?> c, String relativePath) throws MalformedURLException{
		URL url = c.getProtectionDomain().getCodeSource().getLocation();
		URL ret = null;
		if("file".equals(url.getProtocol())){
			String extension = url.toExternalForm();
			if(extension.endsWith(".jar") || extension.endsWith(".zip")){
				ret = new URL("jar:".concat(extension).concat("!/").concat(relativePath));
			}else if(new File(url.getFile()).isDirectory()){
				ret = new URL(url, relativePath);
			}
		}
		if(ret == null){
			ClassLoader loader = c.getClassLoader();
			ret = loader == null ? ClassLoader.getSystemResource(relativePath) : loader.getResource(relativePath);
		}
		return ret;
	}

	/**
	 * 檔案存到硬碟
	 * @param file
	 * @param text
	 * @param append
	 * @param encoding
	 */
	public static void saveToDisk(File file, String text, boolean append, String encoding) throws UnsupportedEncodingException, IOException{
		BufferedWriter bw = null;
		try{
			bw = StreamKit.getBufferedWriter(file, append, encoding);
			bw.write(text);
			bw.flush();
		}finally{
			StreamKit.close(null, bw);
		}
	}

	/**
	 * 取得捷徑的真實路徑
	 * @param src
	 * @return
	 * @author altCognito@stackoverflow.com
	 * @throws IOException 
	 */
	public static File getShortcutFile(String src) throws IOException{
		final int shell_offset = 0X4c;
		final byte has_shell_mask = (byte)0X01;
		final int file_location_info_flag_offset_offset = 0X08;
		final int basename_offset_offset = 0X10;
		final int networkVolumeTable_offset_offset = 0X14;
		final int finalname_offset_offset = 0X18;

		if(src.endsWith("lnk")){
			String real_file = null;
			InputStream is = new FileInputStream(new File(src));
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			byte[] buff = new byte[256];
			int n;
			while((n= is.read(buff)) != -1) {
				bout.write(buff, 0, n);
			}
			StreamKit.close(is, null);
			byte[] link = bout.toByteArray();

			int shell_len = 0;
			if ((link[0X14] & has_shell_mask) > 0) {
				shell_len = bytes2short(link, shell_offset) + 2;
			}
			int file_start = 0x4c + shell_len;
			int file_location_info_flag = link[file_start + file_location_info_flag_offset_offset];
			int finalname_offset = link[file_start + finalname_offset_offset] + file_start;
			String finalname = getNullDelimitedString(link, finalname_offset);
			if((file_location_info_flag & 2) == 0){
				int basename_offset = link[file_start + basename_offset_offset] + file_start;
				String basename = getNullDelimitedString(link, basename_offset);
				real_file = basename + finalname;
			}else{
				int networkVolumeTable_offset = link[file_start + networkVolumeTable_offset_offset] + file_start;
				int shareName_offset_offset = 0X08;
				int shareName_offset = link[networkVolumeTable_offset + shareName_offset_offset] + networkVolumeTable_offset;
				String shareName = getNullDelimitedString(link, shareName_offset);
				real_file = shareName + "\\" + finalname;
	        }
			return new File(real_file);
		}else{
			return new File(src);
		}
	}

	/**
	 * 
	 * @param src
	 */
	public static String getExtension(File src){
		return FileKit.getExtension(src.getName());
	}
	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getExtension(String fileName){
		int idx = fileName.lastIndexOf(".");
		if(idx <= 0){
			return "";
		}else if(++idx == fileName.length()){
			return "";
		}else{
			return fileName.substring(idx);
		}
	}

	private static int bytes2short(byte[] bytes, int off) {
		return ((bytes[off + 1] & 0xff) << 8) | (bytes[off] & 0xff);
	}

	private static String getNullDelimitedString(byte[] bytes, int off) {
		int len = 0; 
		while(bytes[off + len] != 0) {
			len++;
		}
		return new String(bytes, off, len);
	}
}
