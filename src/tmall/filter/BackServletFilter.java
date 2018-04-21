package tmall.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class BackServletFilter implements Filter{

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
		
		String contextPath = request.getServletContext().getContextPath();
		String url = request.getRequestURI();

		url = StringUtils.remove(url, contextPath);
		if (url.startsWith("/admin_")) {
			String servletPath = StringUtils.substringBetween(url, "_", "_")+"Servlet";
			String method = StringUtils.substringAfterLast(url, "_");
			request.setAttribute("method", method);
			arg0.getRequestDispatcher("/" + servletPath).forward(request, response);
			return;
		}
		arg2.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO 自动生成的方法存根
		
	}
	
}
