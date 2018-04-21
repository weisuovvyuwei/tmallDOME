package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Order;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

public class OrderDAO {
    public static final String waitPay = "waitPay";				//������
    public static final String waitDelivery = "waitDelivery";	//������
    public static final String waitConfirm = "waitConfirm";		//���ջ�
    public static final String waitReview = "waitReview";		//������
    public static final String finish = "finish";				//���
    public static final String delete = "delete";				//�h��
	
    public int getTotal(){		//ͳ�����ж�������
    	int total = 0;
    	String sql = "select conut(*) from order_";
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
    public void add(Order bean){		//����
    	String sql = "insert into order_ values(null,?,?,?,?,?,?,?,?,?,?,?,?)";
    	try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)) {
			ps.setString(1, bean.getOrderCode());
			ps.setString(2, bean.getAddress());
			ps.setString(3, bean.getPost());
			ps.setString(4, bean.getReceiver());
			ps.setString(5, bean.getMobile());
			ps.setString(6,	bean.getUserMessage());
			
			ps.setTimestamp(7, DateUtil.d2t(bean.getCreateDate()));
			ps.setTimestamp(8, DateUtil.d2t(bean.getPayDate()));
			ps.setTimestamp(9, DateUtil.d2t(bean.getDeliveryDate()));
			ps.setTimestamp(10, DateUtil.d2t(bean.getConfirmDate()));
			ps.setInt(11, bean.getUser().getId());
			ps.setString(12, bean.getStatus());
			
			ps.execute();
			
			ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                bean.setId(id);
            }
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    public void delete(int id){		//ɾ��
    	String sql = "dalete from order_ where id = "+id;
    	try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()) {
			s.execute(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    public void update(Order bean){			//�޸�
    	String sql = "update order_ set orderCode= ?, address=?, post=?,receiver=?,mobile=? "
    				+",userMessage = ? , createDate =? , payDate =?, deliveryDate = ? , confirmDate =?"
    				+", uid=?, status=? where id = ?";
    	try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)) {
			ps.setString(1, bean.getOrderCode());
			ps.setString(2, bean.getAddress());
			ps.setString(3, bean.getPost());
			ps.setString(4, bean.getReceiver());
			ps.setString(5, bean.getMobile());
			ps.setString(6,	bean.getUserMessage());
			
			ps.setTimestamp(7, DateUtil.d2t(bean.getCreateDate()));
			ps.setTimestamp(8, DateUtil.d2t(bean.getPayDate()));
			ps.setTimestamp(9, DateUtil.d2t(bean.getDeliveryDate()));
			ps.setTimestamp(10, DateUtil.d2t(bean.getConfirmDate()));
			ps.setInt(11, bean.getUser().getId());
			ps.setString(12, bean.getStatus());
			ps.setInt(13, bean.getId());
			
			ps.execute();
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    public Order get(int id){			//����id��ѯ����
    	Order bean = null;
    	String sql = "select * from order_ where id = "+id;
    	try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()) {
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				bean = new Order();
				bean.setId(rs.getInt("id"));
				bean.setOrderCode(rs.getString("orderCode"));
				bean.setAddress(rs.getString("address"));
				bean.setPost(rs.getString("post"));
				bean.setReceiver(rs.getString("receiver"));
				bean.setMobile(rs.getString("mobile"));
				bean.setUserMessage(rs.getString("userMessage"));
				bean.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
				bean.setPayDate(DateUtil.t2d(rs.getTimestamp("payDate")));
				bean.setDeliveryDate(DateUtil.t2d(rs.getTimestamp("deliveryDate")));
				bean.setConfirmDate(DateUtil.t2d(rs.getTimestamp("confirmDate")));
				bean.setUser(new UserDAO().get(rs.getInt("uid")));
				bean.setStatus(rs.getString("status"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
    	return bean;
    }
    public List<Order> list(int start,int count){	  //��ҳ��ѯ����
    	List<Order> beans = new ArrayList<Order>();
    	String sql = "select * from order_ order by id desc limit ?,?";
    	try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)) {
			ps.setInt(1, start);
			ps.setInt(2, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Order bean = new Order();
				bean.setId(rs.getInt("id"));
				bean.setOrderCode(rs.getString("orderCode"));
				bean.setAddress(rs.getString("address"));
				bean.setPost(rs.getString("post"));
				bean.setReceiver(rs.getString("receiver"));
				bean.setMobile(rs.getString("mobile"));
				bean.setUserMessage(rs.getString("userMessage"));
				bean.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
				bean.setPayDate(DateUtil.t2d(rs.getTimestamp("payDate")));
				bean.setDeliveryDate(DateUtil.t2d(rs.getTimestamp("deliveryDate")));
				bean.setConfirmDate(DateUtil.t2d(rs.getTimestamp("confirmDate")));
				bean.setUser(new UserDAO().get(rs.getInt("uid")));
				bean.setStatus(rs.getString("status"));
				beans.add(bean);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
    	return beans;
    }
    public List<Order> list(){			 //��ѯ���ж���
    	return list(0, Short.MAX_VALUE);
    }
    public List<Order> list(int uid,String excludedStatus,int start,int count){		//��ѯָ���û��Ķ���(ȥ��ĳ�ֶ���״̬��ͨ����"delete")
    	List<Order> beans = new ArrayList<Order>();
    	String sql = "select * from Order_ where uid = ? and status != ? order by id desc limit ?,? ";
    	try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)) {
			ps.setInt(1, uid);
			ps.setString(2, excludedStatus);
			ps.setInt(3, start);
			ps.setInt(4, count);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Order bean = new Order();
				bean.setId(rs.getInt("id"));
				bean.setOrderCode(rs.getString("orderCode"));
				bean.setAddress(rs.getString("address"));
				bean.setPost(rs.getString("post"));
				bean.setReceiver(rs.getString("receiver"));
				bean.setMobile(rs.getString("mobile"));
				bean.setUserMessage(rs.getString("userMessage"));
				bean.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
				bean.setPayDate(DateUtil.t2d(rs.getTimestamp("payDate")));
				bean.setDeliveryDate(DateUtil.t2d(rs.getTimestamp("deliveryDate")));
				bean.setConfirmDate(DateUtil.t2d(rs.getTimestamp("confirmDate")));
				bean.setUser(new UserDAO().get(rs.getInt("uid")));
				bean.setStatus(rs.getString("status"));
				
				beans.add(bean);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
    	return beans;
    }
    public List<Order> list(int uid,String excludedStatus){			//��ѯָ���û��Ķ���(ȥ��ĳ�ֶ���״̬��ͨ����"delete")
    	return list(uid, excludedStatus, 0, Short.MAX_VALUE);
    }
}
