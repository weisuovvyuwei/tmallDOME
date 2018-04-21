package tmall.dao;

import java.awt.RadialGradientPaint;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Category;
import tmall.util.DBUtil;

public class CategoryDAO {
	public int getTotal(){		//��ѯ����
		int total = 0;	
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()){
			String sql = "select count(*) from category";
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
	public void add(Category bean){		//����
		String sql = "insert into category values(null,?)";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setString(1, bean.getName());
			ps.execute();
			ResultSet rs=ps.getGeneratedKeys();
			while(rs.next()){
				int id = rs.getInt(1);
				bean.setId(id);
			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	public void delete(int id){		//ɾ��
		String sql = "delete from Category where id = "+id;
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)){
			ps.execute();
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	public void update(Category bean){	//�޸�
		String sql = "update Category set name = ? where id = ?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)){
			ps.setString(1, bean.getName());
			ps.setInt(2, bean.getId());
			ps.execute();
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	public Category get(int id){	//����id��ȡ
		Category bean=null;
		String sql = "select * from category where id="+id;
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)){
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				bean = new Category();
				bean.setId(rs.getInt(1));
				bean.setName(rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return bean;
	}
	public List<Category> list(int start,int count){	//��ҳ��ѯ
		List<Category> beans = new ArrayList<Category>();
		String sql = "select * from Category order by id limit ?,?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)){
			ps.setInt(1, start);
			ps.setInt(2, count);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				Category bean = new Category();
				bean.setId(rs.getInt(1));
				bean.setName(rs.getString(2));
				beans.add(bean);
			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return beans;
	}
	public List<Category> list(){		//��ѯȫ��
		return list(0, Short.MAX_VALUE);
	}
}
