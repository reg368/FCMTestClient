package hyweb.core.kit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

/**
 * <pre>有關產生Properties的功能，禁止用LOGGER</pre>
 * 尚未試過包在jar裡面,環境變數中有存啟動點
 * @author A0074
 * @version 1.0.110929
 * @since xBox 1.0
 */
public final class PropertiesKit {
	public PropertiesKit(){
		super();
	}
	
	/**
	 * 支援中文，取得位於classpath目錄下的檔案。
	 * @param relativePath 參數檔的檔名
	 * @return  
	 * @throws URISyntaxException 
	 * @throws Exception 當檔案不存在，或載入失敗
	 * @see Properties
	 */
	public static Properties loadByClassPath(String relativePath) throws InvalidPropertiesFormatException, FileNotFoundException, IOException, URISyntaxException{
		return PropertiesKit.loadByClassPath(relativePath, System.getProperty("sun.jnu.encoding"));
	}
	
	/**
	 * 支援中文，取得位於classpath目錄下的檔案。
	 * @param relativePath 參數檔的檔名
	 * @param encoding
	 * @return 
	 * @throws URISyntaxException 
	 * @throws Exception 當檔案不存在，或載入失敗
	 */
	public static Properties loadByClassPath(String relativePath, String encoding) throws InvalidPropertiesFormatException, FileNotFoundException, IOException, URISyntaxException{
		return PropertiesKit.loadByFile(new File(Thread.currentThread().getContextClassLoader().getResource(relativePath).toURI()), encoding);
	}

	/**
	 * 支援中文，取得位於c目錄底下的檔案。
	 * @param relativePath 參數檔的檔名
	 * @param encoding 編碼
	 * ＠param c 設定檔於該class的路徑下
	 * @return 
	 * @throws Exception 當檔案不存在，或載入失敗
	 */
	public static Properties loadInCurrentClassDir(String relativePath, String  encoding, Class<?> c) throws InvalidPropertiesFormatException, IOException{
		return PropertiesKit.loadByJarPath(c, relativePath, encoding);
	}

	/**
	 * 支援取出jar內的設定檔
	 * @param c
	 * @param relativePath
	 * @param encoding
	 * @return
	 * @throws IOException 
	 * @throws InvalidPropertiesFormatException 
	 * @throws Exception
	 */
	
	public static Properties loadByJarPath(Class<?> c, String relativePath, String encoding) throws InvalidPropertiesFormatException, IOException{
		InputStream is = c.getResourceAsStream(relativePath);
		if(relativePath.toLowerCase().endsWith("xml")){
			return PropertiesKit.loadXmlByStream(is);
		}else{
			return PropertiesKit.loadIniByReader(new InputStreamReader(is, encoding));
		}
	}
	
	/**
	 * 支援中文，使用絕對路徑。
	 * @param file
	 * @param encoding
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws InvalidPropertiesFormatException 
	 * @throws Exception
	 */
	public static Properties loadByFile(File file, String encoding) throws InvalidPropertiesFormatException, FileNotFoundException, IOException{
		if(file.exists()){
			if(file.getName().toLowerCase().endsWith("xml")){
				return PropertiesKit.loadXmlByStream(new FileInputStream(file));
			}else{
				return PropertiesKit.loadIniByReader(StreamKit.getReader(file, encoding));
			}
		}else{
			throw new IOException("file not exist");
		}
	}

	/**
	 * 
	 * @param r
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	protected static Properties loadIniByReader(Reader r) throws IOException{
		Properties ret = new Properties();
		ret.load(r);
		return ret;
	}

	protected static Properties loadXmlByStream(InputStream is) throws InvalidPropertiesFormatException, IOException{
		Properties ret = new Properties();
		ret.loadFromXML(is);
		return ret;
	}
}
