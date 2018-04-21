package tmall.servlet;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Order;
import tmall.dao.OrderDAO;
import tmall.util.Page;

public class OrderServlet extends BaseBackServlet{

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
	public String delivery(HttpServletRequest request, HttpServletResponse response, Page page){
		int id = Integer.parseInt(request.getParameter("id"));
		System.out.println("id="+id);
		Order o = orderDAO.get(id);
		System.out.println("o::"+o);
		o.setDeliveryDate(new Date());
		o.setStatus(OrderDAO.waitConfirm);
		orderDAO.update(o);
		return "@admin_order_list";
	}
	@Override
	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO 自动生成的方法存根
		List<Order> os = orderDAO.list(page.getStart(), page.getCount());
		orderItemDAO.fill(os);
		int total = orderDAO.getTotal();
		page.setTotal(total);
		
		request.setAttribute("os", os);
		request.setAttribute("page", page);
		return "admin/listOrder.jsp";
	}
	
}
