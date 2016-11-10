package hyweb.util;

import hyweb.core.kit.ServletKit;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileProvider {
	public static final File IMAGE_BASE_DIR;
	public static final File FILE_BASE_DIR;
	private static final Integer BROWSER_IMAGE_TIME_OUT_SEC;
	static{
		IMAGE_BASE_DIR = new File(Cfg.get("IMAGE_DIR"));
		FILE_BASE_DIR = new File(Cfg.get("FILE_DIR"));
		BROWSER_IMAGE_TIME_OUT_SEC = Integer.parseInt(Cfg.get("BROWSER_IMAGE_TIME_OUT_SEC"));
	}
	/**
	 * 塞入資源圖片,會呼叫
	 * @param response
	 * @param fileName 檔名
	 */
	public static void showImageInBrowser(HttpServletResponse response, String fileName) throws IOException{
		File file = new File(IMAGE_BASE_DIR, fileName);
		response.reset();
		if(file.exists()){
			System.out.println("fileName => "+fileName);
			
			ServletKit.setDownloadMineType(response, fileName);
			ServletKit.setResponseTimeout(response, BROWSER_IMAGE_TIME_OUT_SEC);
			ServletKit.setDownloadCopy(response, file);
		}else{
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
	
	/**
	 * 塞入資源圖片,會呼叫
	 * @param response
	 * @param fileName 檔名
	 */
	public static void showImageInBrowser(HttpServletResponse response, HttpServletRequest request, String fileName) throws IOException{
		File file = new File(IMAGE_BASE_DIR, fileName);
		response.reset();
		ServletKit.setDownloadMineType(response, fileName);
		if(file.exists()){
			boolean download = ServletKit.setResponseTimeout(request, response,  BROWSER_IMAGE_TIME_OUT_SEC, file.lastModified());
			if (download) {
				ServletKit.setDownloadCopy(response, file);
			}
		}else{
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	/**
	 * 下載附件
	 * @param request
	 * @param response
	 * @param fileName
	 * @param downloadName
	 */
	public static void downloadFile(HttpServletRequest request,HttpServletResponse response, String fileName, String downloadName) throws IOException{
		File file = new File(FILE_BASE_DIR, fileName);
		response.reset();
		if(file.exists()){
			ServletKit.setDownloadMineType(response, fileName);
			ServletKit.setResponseTimeout(response, BROWSER_IMAGE_TIME_OUT_SEC);
			ServletKit.setDownloadName(request, response, downloadName);
			ServletKit.setDownloadCopy(response, file);
		}else{
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
}
