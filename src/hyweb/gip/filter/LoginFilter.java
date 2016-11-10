package hyweb.gip.filter;

import hyweb.gip.model.session.impl.HttpSessionHandle;
import hyweb.gip.pojo.mybatis.table.InfoUser;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns={"/app/admin/*","/app/page/*","/app/cms/*",
						"/app/AddData/*","/app/order/*","/app/logistic/*"})
public class LoginFilter extends HttpServlet implements Filter {

	private static final long serialVersionUID = 7607326117456638096L;

    public LoginFilter() {
        super();
    }


	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		res.setContentType("text/html;charset=UTF-8");
		InfoUser infoUser = new HttpSessionHandle(req).getLoginUser();

		if(infoUser == null){
			res.getWriter().println("<script>");
			res.getWriter().println("alert('登入失效，請重新登入!');");
			res.getWriter().println("window.top.location.href='/admin/';");
			res.getWriter().println("</script>");	
		}
		else
			chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
