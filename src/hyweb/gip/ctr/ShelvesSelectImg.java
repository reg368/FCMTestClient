package hyweb.gip.ctr;

import hyweb.gip.dao.service.ImgCenterService;
import hyweb.gip.pojo.mybatis.table.ImgCenter;
import hyweb.util.SpringLifeCycle;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SelectImg
 */
@WebServlet("/app/admin/AddData/list/shelvesSelectImg")
public class ShelvesSelectImg extends HttpServlet {

	private static final long serialVersionUID = -4587693366553782017L;
	
	private ImgCenterService imgCenterService =
			(ImgCenterService)SpringLifeCycle.getBean("ImgCenterServiceImpl");
	
	public ShelvesSelectImg() {
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
		PrintWriter out = response.getWriter();
		String showData = request.getParameter("show");
		String divName = request.getParameter("divname");
		String[] selectImg =request.getParameterValues("Checkboxpic");
		ImgCenter imgCenter =null;
		String buff = "";
		for(int i=0;i<selectImg.length;i++){
			imgCenter = imgCenterService.selectByPrimaryKey(Integer.parseInt(selectImg[i]));
			if("Y".equals(showData)){
				buff = buff +
						"<div class=\"uploadlist\" id=\"uploadlist"+"_"+divName+"\">" +
						"<input name=\"diimgseq"+"_"+divName+"\" type=\"hidden\" value=\""+imgCenter.getImgseq()+"\" />"+
		        		"<div class=\"img\"><img src=\""+imgCenter.getImgfileuri()+"\" /></div>" +
		        		"<div class=\"body\">圖片標題<input name=\"diimgtitle"+"_"+divName+"\" type=\"text\" size=\"5\" value=\""+imgCenter.getImgtitle()+"\" /><p>排序" +
		        		"<input name=\"diimgsort"+"_"+divName+"\" type=\"text\" size=\"5\" value=\"0\" /></p>" +
		        		"<p>顯示<select name=\"diimgpublic"+"_"+divName+"\"><option value=\"Y\">公開</option><option value=\"N\">不公開</option></select></p>" +
		        		"<p><input name=\"diimgnews"+"_"+divName+"\" type=\"checkbox\" value=\""+imgCenter.getImgseq()+"Y\"/>圖表新聞</p></div><div class=\"setmain\">" +
		        		"<input name=\"diimgmain"+"_"+divName+"\" type=\"radio\" value=\""+imgCenter.getImgseq()+"Y\" checked=\"checked\"/>設為資料主圖" +
		        		"<img src=\"/admin/app/images/btn_remove.gif\" onclick=\"remove"+"_"+divName+"(this);\"></div></div>";
			}else if("N".equals(showData)){
				buff = buff +
						"<div class=\"uploadlist\" id=\"uploadlist"+"_"+divName+"\">" +
						"<input name=\"diimgseq"+"_"+divName+"\" type=\"hidden\" value=\""+imgCenter.getImgseq()+"\" />"+
		        		"<div class=\"img\"><img src=\""+imgCenter.getImgfileuri()+"\" /></div>" +
		        		"<div class=\"setmain\">" +
		        		"<img src=\"/admin/app/images/btn_remove.gif\" onclick=\"remove"+"_"+divName+"(this);\"></div></div>";
			}
		}
		
		
		String show = "$(window.opener.document.getElementById('"+divName+"')).html('" +
					  buff+
					  "'+$(window.opener.document.getElementById('"+divName+"')).html());";
       
        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\"><html><head>");
        out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
        out.println("<script src=\"../../../js/jquery-1.9.1.min.js\" type=\"text/javascript\"></script>");
        out.println("<script>");
        out.println(show);
        out.println("window.opener.checkImg"+"_"+divName+"();");
        out.println("window.top.close();"+"</script>");
        out.println("</head><body></body></html>");
        out.close();
	}
}
