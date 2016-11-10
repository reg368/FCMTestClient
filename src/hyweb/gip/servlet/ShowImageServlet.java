package hyweb.gip.servlet;

import hyweb.gip.dao.service.ImgCenterService;
import hyweb.util.FileProvider;
import hyweb.util.SpringLifeCycle;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 提供網頁編輯器使用
 * @author A0074
 *
 */
@WebServlet(name="ShowImage", urlPatterns={"/image/*"}, loadOnStartup=2)
public class ShowImageServlet extends HttpServlet {
	private static final long serialVersionUID = -4925996889319948701L;
	private String listenPath = "/image/";
	private ImgCenterService imgCenterService = SpringLifeCycle.getBean("ImgCenterServiceImpl");
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int beginIndex = request.getContextPath().length() + this.listenPath.length();
		String[] paths = request.getRequestURI().substring(beginIndex).split("/");
		String imgName = imgCenterService.getFileName(paths[1]);
		FileProvider.showImageInBrowser(response, imgName);
	}
}
