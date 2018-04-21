package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.User;
import tmall.util.DBUtil;

public class UserDAO {
	public int getTotal(){		//��ѯ����
		int total = 0;
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()){
			String sql = "select count(*) from user";
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
	public void add(User bean){		//���� 
		String sql = "insert into user values(null,?,?)";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)){
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getPassword());
			ps.execute();
			ResultSet rs=ps.getGeneratedKeys();
			while(rs.next()){
				bean.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	public void delete(int id){ 		//ɾ��
		String sql = "dalete from user where id = "+id;
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()){
			s.executeQuery(sql);
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	public void update(User bean){		//�޸�
		String sql = "update user set name = ?,password = ? where id = ?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)){
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getPassword());
			ps.setInt(3, bean.getId());
			ps.execute();
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	public User get(int id){	//����id��ѯ
		User bean = null;
		String sql = "select * from user where id = "+id;
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()){
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				bean = new User();
				bean.setId(rs.getInt(1));
				bean.setName(rs.getString(2));
				bean.setPassword(rs.getString(3));
			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return bean;
	}
	public List<User> list(int start,int count){	//��ҳ��ѯ
		List<User> beans = new ArrayList<User>();
		String sql = "select * from user order by id desc limit ?,?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)){
			ps.setInt(1, start);
			ps.setInt(2, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				User bean = new User();
				bean.setId(rs.getInt(1));
				bean.setName(rs.getString(2));
				bean.setPassword(rs.getString(3));
				beans.add(bean);
			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return beans;
	}
	public List<User> list(){		//��ѯȫ��
		return list(0, Short.MAX_VALUE);
	}
	public User get(String name){	//�����û�����ѯ�û�    �����û�ע�����жϴ��û����Ƿ����
		User bean = null;
		String sql = "select * from user where name = "+"'"+name+"'";
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()){
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				bean = new User();
				bean.setId(rs.getInt(1));
				bean.setName(rs.getString(2));
				bean.setPassword(rs.getString(3));
			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return bean;
	}
	public boolean isExist(String name){	//�ж��û����Ƿ����
		User bean = get(name);
		if (null == bean) {
			return false;
		}else{
			return true;
		}
	}
	public User get(String name,String password){ //�����˺ź������ȡ�����ж��˺������Ƿ���ȷ
		User bean = null;
		String sql = "select * from user where name = ? and password = ?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)){
			ps.setString(1, name);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				 bean = new User();
				 bean.setId(rs.getInt(1));
				 bean.setName(name);
				 bean.setPassword(password);
			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return bean;
	}
}
