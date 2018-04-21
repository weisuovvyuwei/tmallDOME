package tmall.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.User;
import tmall.util.Page;

public class UserServlet extends BaseBackServlet{

	@Override
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO 自动生成的方法存根
		List<User> us = userDAO.list(page.getStart(), page.getCount());
		int total = userDAO.getTotal();
		page.setTotal(total);
		
		request.setAttribute("us", us);
		request.setAttribute("page", page);
		return "admin/listUser.jsp";
	}
	
}
