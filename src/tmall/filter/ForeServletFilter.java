package tmall.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import tmall.bean.Category;
import tmall.bean.OrderItem;
import tmall.bean.User;
import tmall.dao.CategoryDAO;
import tmall.dao.OrderItemDAO;

public class ForeServletFilter implements Filter{

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
		request.getServletContext().setAttribute("contextPath", contextPath);
		
		User user = (User) request.getSession().getAttribute("user");
		int cartTotalItemNumber = 0;
		if (null != user) {
			List<OrderItem> ois = new OrderItemDAO().listByUser(user.getId());
			for(OrderItem oi : ois){
				cartTotalItemNumber += oi.getNumber();
			}
		}
		request.setAttribute("cartTotalItemNumber", cartTotalItemNumber);
		
		List<Category> cs = (List<Category>) request.getAttribute("cs");
		if (null != cs) {
			cs = new CategoryDAO().list();
			request.setAttribute("cs", cs);
		}
		
		String url = request.getRequestURI();
		url = StringUtils.remove(url, contextPath);
		if (url.startsWith("/fore")&&!url.startsWith("/foreServlet")) {
			String method = StringUtils.substringAfter(url, "/fore");
			request.setAttribute("method", method);
			arg0.getRequestDispatcher("/foreServlet").forward(arg0, arg1);
			return;
		}
		arg2.doFilter(arg0, arg1);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO 自动生成的方法存根
		
	}
	
}
