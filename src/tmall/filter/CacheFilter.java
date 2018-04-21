package tmall.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class CacheFilter implements Filter{

	@Override
	public void destroy() {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		// TODO 自动生成的方法存根
		HttpServletResponse response = (HttpServletResponse)arg1;
        response.setDateHeader("Expires",-1);
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Pragma","no-cache");
        arg2.doFilter(arg0, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO 自动生成的方法存根
		
	}
	
}
