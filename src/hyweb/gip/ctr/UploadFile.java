package hyweb.gip.ctr;

import hyweb.core.kit.StreamKit;
import hyweb.gip.dao.service.FileCenterService;
import hyweb.gip.dao.service.InfoUserService;
import hyweb.gip.model.session.impl.HttpSessionHandle;
import hyweb.gip.pojo.mybatis.table.FileCenter;
import hyweb.gip.pojo.mybatis.table.InfoUser;
import hyweb.util.Cfg;
import hyweb.util.SpringLifeCycle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class UploadImg
 */
@MultipartConfig(location = "")
@WebServlet("/app/admin/AddData/insert/uploadFileAction")
public class UploadFile extends HttpServlet {

	private static final long serialVersionUID = 2977602527099992198L;
	
	private FileCenterService fileCenterService =
				(FileCenterService)SpringLifeCycle.getBean("FileCenterServiceImpl");
	private InfoUserService infoUserService = 
			(InfoUserService)SpringLifeCycle.getBean("InfoUserServiceImpl");

	public UploadFile() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		go(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		go(request,response);
	}

	protected void go(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
			response.setContentType("text/html;charset=UTF-8");
			
			String filetitle = request.getParameter("filetitle");
			String filecontent = request.getParameter("filecontent");
			String filestatus = request.getParameter("filestatus");
			
			PrintWriter out = response.getWriter();
			Part part = request.getPart("selectfile");
			String filename = getFilename(part);  // 檔名由Header取出
	      // _sFilename = new String(_sFilename.getBytes("ISO8859_1"),"UTF-8");  // 在不同的code page啟動AP時有時需要轉碼
			Date date = new Date();
			String newfilename = System.currentTimeMillis() + "." + filename.split("\\.")[filename.split("\\.").length-1];
			InfoUser infoUser = new HttpSessionHandle(request).getLoginUser();
			FileCenter fileCenter = new FileCenter();
			fileCenter.setFiletitle(filetitle);
			fileCenter.setFilecontent(filecontent);
			fileCenter.setFilestatus(filestatus);
			fileCenter.setFilefilename(filename);
			fileCenter.setFilefileuri("/admin/app/upload/"+newfilename);
			fileCenter.setFileuploadtime(date);
			fileCenter.setFileuploaduser(infoUser.getUserid());
			fileCenter.setFileuploaddept(infoUserService.selectByPrimaryKey(infoUser.getUserid()).getDeptid());
			
			this.fileCenterService.insert(fileCenter);
			
	        //part.write(newfilename);  // saving the uploaded file.
	        this.save(part, newfilename);
	        
	        
	        String s ="$(window.opener.document.getElementById('showfile')).html('<div class=\"uploadfile\" id=\"uploadfile\">" +
	        		"<input name=\"dffileseq\" type=\"hidden\" value=\""+fileCenter.getFileseq()+"\" />"+
	        		"<span class=\"title\">"+fileCenter.getFilefilename()+"</span>" +
	        		"<span>排序<input name=\"dffilesort\" type=\"text\" size=\"5\" value=\"0\" /></span>" +
	        		"<span>顯示<select name=\"dffilepublic\"><option value=\"Y\">公開</option><option value=\"N\">不公開</option></select></span>" +
	        		"<img src=\"/admin/app/images/btn_remove.gif\" onclick=\"removeFile(this);\"/></div>" +
	        		"'+$(window.opener.document.getElementById('showfile')).html());";
	       
	        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\"><html><head>");
	        out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
	        out.println("<script src=\"../../../js/jquery-1.9.1.min.js\" type=\"text/javascript\"></script>");
	        out.println("<script>");
	        out.println(s);
	        out.println("window.top.close();"+"</script>");
	        out.println("</head><body></body></html>");
	        out.close();
	    
	}
	private static String getFilename(Part part) {
    for (String cd : part.getHeader("content-disposition").split(";")) {
      if (cd.trim().startsWith("filename")) {
        String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
        return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
      }
    }
    return null;
  }
	
	private void save(Part part, String newfilename) throws IOException{
		File dir = new File(Cfg.get("FILE_DIR"));
		InputStream is = null;
		OutputStream os = null;
		try{
			is = part.getInputStream();
			os = new FileOutputStream(new File(dir, newfilename));
			StreamKit.copy(is, os);
		}finally{
			StreamKit.close(is, os);
		}
		
	}
}
