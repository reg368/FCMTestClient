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


@MultipartConfig(location = "")
@WebServlet("/HtmlEditImgUpload")
public class HtmlEditImgUpload extends HttpServlet {
	
	private static final long serialVersionUID = -8711769979201791026L;

    public HtmlEditImgUpload() {
        super();
    }
    private ImgCenterService imgCenterService =
			(ImgCenterService)SpringLifeCycle.getBean("ImgCenterServiceImpl");

	private InfoUserService infoUserService = 
			(InfoUserService)SpringLifeCycle.getBean("InfoUserServiceImpl");
	
	
	
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
		imgCenter.setImgtitle(this.str(request.getParameter("imgtitle")));
		imgCenter.setImgcontent(this.str(request.getParameter("imgcontent")));
		imgCenter.setImgstatus(this.str(request.getParameter("imgstatus")));
		imgCenter.setImgfilename(filename);
		imgCenter.setImgfileuri("/admin/app/upload/"+newfilename);
		imgCenter.setImguploadtime(date);
		imgCenter.setImguploaduser(infoUser.getUserid());
		imgCenter.setImguploaddept(infoUserService.selectByPrimaryKey(infoUser.getUserid()).getDeptid());
		this.imgCenterService.insert(imgCenter);
		//part.write(newfilename);  // saving the uploaded file.
		this.save(part, newfilename);
		
		imgCenter = imgCenterService.selectByPrimaryKey(imgCenter.getImgseq());
		
		out.println("<script>parent.elRTEathImg(null,'" + imgCenter.getImgid() + "');</script>");
	
		
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
