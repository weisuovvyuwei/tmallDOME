package tmall.filter;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import tmall.bean.User;

public class ForeAuthFilter implements Filter{

	@Override
	public void destroy() {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		// TODO 自动生成的方法存根
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		String contextPath=request.getServletContext().getContextPath();
		
		String[] noNeedAuthPage = new String[]{
				"home",
                "checkLogin",
                "register",
                "loginAjax",
                "login",
                "product",
                "category",
                "search"};
		String url = request.getRequestURI();

		url = StringUtils.remove(url, contextPath);
		if (url.startsWith("/fore")&&!url.startsWith("/foreServlet")) {
			String method = StringUtils.substringAfterLast(url, "/fore");
			if (!Arrays.asList(noNeedAuthPage).contains(method)) {
				User user = (User) request.getSession().getAttribute("user");
				if (null==user) {
					response.sendRedirect("login.jsp");
					return;
				}
			}
		}
		arg2.doFilter(arg0, arg1);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO 自动生成的方法存根
		
	}

}
