package hyweb.gip.ctr;

import hyweb.core.kit.StreamKit;
import hyweb.gip.dao.service.ImgCenterService;
import hyweb.gip.dao.service.InfoUserService;
import hyweb.gip.model.session.impl.HttpSessionHandle;
import hyweb.gip.pojo.mybatis.table.ImgCenter;
import hyweb.gip.pojo.mybatis.table.InfoUser;
import hyweb.util.Cfg;
import hyweb.util.SpringLifeCycle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
@WebServlet("/app/admin/AddData/insert/uploadShelvesImgAction")
public class ShelvesUploadImg extends HttpServlet {

	private static final long serialVersionUID = 6116932358133704547L;
	
	private ImgCenterService imgCenterService =
				(ImgCenterService)SpringLifeCycle.getBean("ImgCenterServiceImpl");
	
	private InfoUserService infoUserService = 
			(InfoUserService)SpringLifeCycle.getBean("InfoUserServiceImpl");

	public ShelvesUploadImg() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		go(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		go(request,response);
	}

	protected String str(String src) throws UnsupportedEncodingException{
		if(src == null){
			return null;
		}else{
			return new String(src.getBytes("ISO-8859-1"), "UTF-8");
		}
	}

	protected void go(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		Part part = request.getPart("selectfile");
		String filename = getFilename(part);  // 檔名由Header取出
		Date date = new Date();
		String newfilename = System.currentTimeMillis() + "." + filename.split("\\.")[filename.split("\\.").length-1];
		InfoUser infoUser = new HttpSessionHandle(request).getLoginUser();
		ImgCenter imgCenter = new ImgCenter();
		imgCenter.setImgtitle(request.getParameter("imgtitle"));
		imgCenter.setImgcontent(request.getParameter("imgcontent"));
		imgCenter.setImgstatus(request.getParameter("imgstatus"));
		imgCenter.setImgfilename(filename);
		imgCenter.setImgfileuri("/admin/app/upload/"+newfilename);
		imgCenter.setImguploadtime(date);
		imgCenter.setImguploaduser(infoUser.getUserid());
		imgCenter.setImguploaddept(infoUserService.selectByPrimaryKey(infoUser.getUserid()).getDeptid());
		this.imgCenterService.insert(imgCenter);
		//part.write(newfilename);  // saving the uploaded file.
		this.save(part, newfilename);
		

		String show = this.str(request.getParameter("show"));
		String divName = this.str(request.getParameter("divname"));
		String s = "";
		if("Y".equals(show)){
			s = s + "$(window.opener.document.getElementById('"+divName+"')).html('<div class=\"uploadlist\" id=\"uploadlist"+"_"+divName+"\">" +
					"<input name=\"diimgseq"+"_"+divName+"\" type=\"hidden\" value=\""+imgCenter.getImgseq()+"\" />"+
					"<div class=\"img\"><img src=\""+imgCenter.getImgfileuri()+"\" /></div>" +
					"<div class=\"body\">圖片標題<input name=\"diimgtitle"+"_"+divName+"\" type=\"text\" size=\"5\" value=\""+imgCenter.getImgtitle()+"\" /><p>排序" +
					"<input name=\"diimgsort"+"_"+divName+"\" type=\"text\" size=\"5\" value=\"0\" /></p>" +
					"<p>顯示<select name=\"diimgpublic"+"_"+divName+"\"><option value=\"Y\">公開</option><option value=\"N\">不公開</option></select></p>" +
					"<p><input name=\"diimgnews"+"_"+divName+"\" type=\"checkbox\" value=\""+imgCenter.getImgseq()+"Y\"/>圖表新聞</p></div><div class=\"setmain\">" +
					"<input name=\"diimgmain"+"_"+divName+"\" type=\"radio\" value=\""+imgCenter.getImgseq()+"Y\" checked=\"checked\"/>設為資料主圖" +
					"<img src=\"/admin/app/images/btn_remove.gif\" onclick=\"remove"+"_"+divName+"(this);\"></div></div>" +
					"'+$(window.opener.document.getElementById('"+divName+"')).html());";
		}else if("N".equals(show)){
			s = s + "$(window.opener.document.getElementById('"+divName+"')).html('<div class=\"uploadlist\" id=\"uploadlist"+"_"+divName+"\">" +
					"<input name=\"diimgseq"+"_"+divName+"\" type=\"hidden\" value=\""+imgCenter.getImgseq()+"\" />"+ 
					"<div class=\"img\"><img src=\""+imgCenter.getImgfileuri()+"\" /></div>" +
					"<div class=\"setmain"+"_"+divName+"\">" +
					"<img src=\"/admin/app/images/btn_remove.gif\" onclick=\"remove"+"_"+divName+"(this);\"></div></div>" +
					"'+$(window.opener.document.getElementById('"+divName+"')).html());";
		}
	        
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\"><html><head>");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
		out.println("<script src=\"../../../js/jquery-1.9.1.min.js\" type=\"text/javascript\"></script>");
		out.println("<script>");
		out.println(s);
		out.println("window.opener.checkImg"+"_"+divName+"();");
		out.println("window.top.close();"+"</script>");
		out.println("</head><body></body></html>");
		out.flush();
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
		File dir = new File(Cfg.get("IMAGE_DIR"));
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
