package hyweb.gip.ctr;

import hyweb.gip.dao.service.InfoUserService;
import hyweb.util.CsvReader;
import hyweb.util.SpringLifeCycle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/file/AddData")
public class FileAddData {

	private InfoUserService infoUserService = 
			(InfoUserService)SpringLifeCycle.getBean("InfoUserServiceImpl");

	/**
	 * 上傳CSV
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value="upload", method={RequestMethod.GET, RequestMethod.POST})
	public String upload(Object file) throws IOException{
//		CsvReader csvValue  = new CsvReader(f, "UTF-8");
		
		System.out.println("=======>" + file.getClass());
		System.out.println("=======>" + file.toString());

		return "addImg";
	}

}
