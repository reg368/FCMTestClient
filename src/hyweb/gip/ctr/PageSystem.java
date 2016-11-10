package hyweb.gip.ctr;

import hyweb.util.FileProvider;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/")
public class PageSystem {
	/**
	 * 圖片載入
	 * @param response
	 * @param request
	 */
	@ResponseBody
	@RequestMapping(value="upload/{imageName}", method=RequestMethod.GET)
	public void getImageByFileName(HttpServletResponse response,HttpServletRequest request) throws IOException{
		String fileName = request.getRequestURI().split("upload/",2)[1];
		FileProvider.showImageInBrowser(response, fileName);
	}

	/**
	 * 下載檔案
	 * @param response
	 * @param request
	 * @param downloadName
	 */
	@ResponseBody
	@RequestMapping(value="upload/{downloadName}/{fileName}", method=RequestMethod.GET)
	public void getFileByFileName(HttpServletResponse response,HttpServletRequest request
			,@PathVariable(value = "downloadName") String downloadName
			) throws IOException{
		int idx = request.getRequestURI().lastIndexOf("/");
		String fileName = request.getRequestURI().substring(idx + 1);
		FileProvider.downloadFile(request, response, fileName, downloadName);
	}
	
	/**
	 * 訊息導頁
	 * @param response
	 * @param request
	 * @param nextUrl
	 * @param msg
	 */
	@ResponseBody
	@RequestMapping(value="page/PageRedirectMsg/alert", method=RequestMethod.GET)
	public void pageRedirectMsg(
			HttpServletResponse response,
			HttpServletRequest request,
			@RequestParam(value="nextUrl", required=false) String nextUrl,
			@RequestParam(value="msg", required=false) String msg
			) throws IOException{
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html"); 
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<body onload=\"download()\">");
		out.println("<script type=\"text/javascript\">");
		out.println(" function download(){ alert('"+msg+"'); window.location = '"+nextUrl+"'; } "); 
		out.println("</script><noscript></noscript>");
		out.println("</body>");
		out.println("</html>");
		
	}
}
