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
	public int getTotal(){		//查询总数
		int total = 0;
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()){
			String sql = "select count(*) from user";
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()){
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return total;
	}
	public void add(User bean){		//增加 
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	public void delete(int id){ 		//删除
		String sql = "dalete from user where id = "+id;
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()){
			s.executeQuery(sql);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	public void update(User bean){		//修改
		String sql = "update user set name = ?,password = ? where id = ?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)){
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getPassword());
			ps.setInt(3, bean.getId());
			ps.execute();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	public User get(int id){	//根据id查询
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return bean;
	}
	public List<User> list(int start,int count){	//分页查询
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return beans;
	}
	public List<User> list(){		//查询全部
		return list(0, Short.MAX_VALUE);
	}
	public User get(String name){	//根据用户名查询用户    可在用户注册是判断此用户名是否存在
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return bean;
	}
	public boolean isExist(String name){	//判断用户名是否存在
		User bean = get(name);
		if (null == bean) {
			return false;
		}else{
			return true;
		}
	}
	public User get(String name,String password){ //根据账号和密码获取对象，判断账号密码是否正确
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return bean;
	}
}
