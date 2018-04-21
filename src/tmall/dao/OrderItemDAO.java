package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Order;
import tmall.bean.OrderItem;
import tmall.util.DBUtil;

public class OrderItemDAO {
	public int getTotal(){				//��ȡ����������
		int total = 0;
		String sql = "select count(*) from orederitem";
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()) {
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()){
				total = rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return total;
	}
	public void add(OrderItem bean){			//����
		String sql = "insert into orderitem values(null,?,?,?,?)";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)) {
			ps.setInt(1, bean.getProduct().getId());
			 //�������ڴ�����ʱ����û�ж�����Ϣ��
			if (null==bean.getOrder()) {
				ps.setInt(2, -1);
			}else {
				ps.setInt(2, bean.getOrder().getId());
			}
			ps.setInt(3, bean.getUser().getId());
			ps.setInt(4, bean.getNumber());
			ps.execute();
			
			ResultSet rs = ps.getGeneratedKeys();
			while (rs.next()) {
				bean.setId(rs.getInt(1));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void delete(int id){				//ɾ��
		String sql = "delete from orderitem where id = "+id;
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()) {
			s.execute(sql);
 		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void update(OrderItem bean){		//�޸�
		String sql = "update orderitem set pid = ?,oid = ?,uid = ?,number = ?"
					+" where id = ?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)) {
			ps.setInt(1, bean.getProduct().getId());
			if (null==bean.getOrder()) {
				ps.setInt(2, -1);
			}else {
				ps.setInt(2, bean.getOrder().getId());
			}
			ps.setInt(3, bean.getUser().getId());
			ps.setInt(4, bean.getNumber());
			ps.setInt(5, bean.getId());
			ps.execute();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public OrderItem get(int id){			//����id��ȡ
		OrderItem bean = null;
		String sql = "select * from orderitem where id = "+id;
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()) {
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				bean = new OrderItem();
				bean.setId(rs.getInt(1));
				bean.setProduct(new ProductDAO().get(rs.getInt(2)));
				bean.setOrder(new OrderDAO().get(rs.getInt(3)));
				bean.setUser(new UserDAO().get(rs.getInt(4)));
				bean.setNumber(rs.getInt(5));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return bean;
	}
	public List<OrderItem> listByUser(int uid,int start,int count){			//��ѯĳ���û���δ���ɶ����Ķ�����(�ȹ��ﳵ�еĶ�����)
		List<OrderItem> beans = new ArrayList<OrderItem>();
		String sql = "select * from orderitem where uid = ? and oid=-1 order by id desc limit ?,?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)) {
			ps.setInt(1, uid);
			ps.setInt(2, start);
			ps.setInt(3, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				OrderItem bean = new OrderItem();
				bean.setId(rs.getInt(1));
				bean.setProduct(new ProductDAO().get(rs.getInt(2)));
				if (-1!=rs.getInt(3)) {
					bean.setOrder(new OrderDAO().get(rs.getInt(3)));
				}
				bean.setUser(new UserDAO().get(rs.getInt(4)));
				bean.setNumber(rs.getInt(5));
				
				beans.add(bean);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return beans;
	}
	public List<OrderItem> listByUser(int uid){
		return listByUser(uid, 0, Short.MAX_VALUE);
	}
	public List<OrderItem> listByOrder(int oid,int start,int count){	//��ѯĳ�ֶ��������еĶ�����
		List<OrderItem> beans = new ArrayList<OrderItem>();
		String sql = "select * from orderitem where oid = ? order by id desc limit ?,?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)) {
			ps.setInt(1, oid);
			ps.setInt(2, start);
			ps.setInt(3, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				OrderItem bean = new OrderItem();
				bean.setId(rs.getInt(1));
				bean.setProduct(new ProductDAO().get(rs.getInt(2)));
				if (-1!=rs.getInt(3)) {
					bean.setOrder(new OrderDAO().get(rs.getInt(3)));
				}
				bean.setUser(new UserDAO().get(rs.getInt(4)));
				bean.setNumber(rs.getInt(5));
				
				beans.add(bean);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return beans;
	}
	public List<OrderItem> listByOreder(int oid){
		return listByOrder(oid, 0, Short.MAX_VALUE);
	}

	public void fill(Order o){		//Ϊ�������ö������
		List<OrderItem> ois = listByOreder(o.getId());
		float total = 0;
		for(OrderItem oi : ois){
			total += oi.getNumber()*oi.getProduct().getPromotePrice();
		}
		o.setTotal(total);
		o.setOrderItems(ois);
	}
	public void fill(List<Order> os){	//Ϊ����������ö������
		for(Order o : os){
			List<OrderItem> ois = listByOreder(o.getId());
			float total = 0;
			int totalNumber = 0;
			for(OrderItem oi : ois){
				total += oi.getNumber()*oi.getProduct().getPromotePrice();
				totalNumber += oi.getNumber(); 
			}
			o.setTotal(total);
			o.setTotalNumber(totalNumber);
			o.setOrderItems(ois);
		}
	}
	public List<OrderItem> listByProduct(int pid,int start,int count){
		List<OrderItem> beans = new ArrayList<OrderItem>();
		String sql = "select * from orderitem where pid = ? order by id desc limit ?,?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)) {
			ps.setInt(1, pid);
			ps.setInt(2, start);
			ps.setInt(3, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				OrderItem bean = new OrderItem();
				bean.setId(rs.getInt(1));
				bean.setProduct(new ProductDAO().get(rs.getInt(2)));
				if (-1!=rs.getInt(3)) {
					bean.setOrder(new OrderDAO().get(rs.getInt(3)));
				}
				bean.setUser(new UserDAO().get(rs.getInt(4)));
				bean.setNumber(rs.getInt(5));
				
				beans.add(bean);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return beans;
	}
	public List<OrderItem> listByProduct(int pid){
		return listByProduct(pid, 0, Short.MAX_VALUE);
	}
	public int getSaleCount(int pid){		//��ȡĳһ�ֲ�Ʒ������
		int total = 0;
		String sql = "select sum(number) from orderitem where pid = "+pid;
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()){
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()){
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return total;
	}
}
