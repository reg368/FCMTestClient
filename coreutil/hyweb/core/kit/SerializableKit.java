package hyweb.core.kit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author A0074
 * @version 1.0.110429
 * @since xBox 1.0
 */
public final class SerializableKit {
	public static String DEF_CODE_TYPE = "UTF-8";

	private SerializableKit(){
		super();
	}

	/**
	 * 將base64字串反序列化為物件
	 * @param in
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws Exception
	 */
	public static Object deSerializableWithBase64(String in) throws IOException, ClassNotFoundException{
		ObjectInputStream is = null;
		try{
			is = new ObjectInputStream(new ByteArrayInputStream(SecurityKit.decryptByBase64(in, false)));
			return is.readObject();
		}finally{
			if(is != null){
				is.close();
			}
		}
	}

	/**
	 * 序列化物件為base64字串
	 * @param obj
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	public static String enSerializableWithBase64(Object obj) throws IOException{
		java.io.ObjectOutputStream os = null;
		try{
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			os = new ObjectOutputStream(bos);
			os.writeObject(obj);
			return SecurityKit.encryptByBase64(bos.toByteArray(), false);
		}finally{
			if(os != null){
				os.close();
			}
		}
	}
}

