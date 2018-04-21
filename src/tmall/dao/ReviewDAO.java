package tmall.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Review;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

public class ReviewDAO {
	public int getTotal(){		//��ȡ��������
		int total = 0;
		String sql = "select count(*) from review";		
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()) {
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				total = rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return total;
	}
	public int getTotal(int pid){		//��ȡĳ����Ʒ����������
		int total = 0;
		String sql = "select count(*) from review where pid = "+pid;		
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()) {
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				total = rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return total;
	}
	public void add(Review bean){		//����
		String sql = "insert into review values(null,?,?,?,?)";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)) {
			ps.setString(1, bean.getContent());
			ps.setInt(2, bean.getUser().getId());
			ps.setInt(3, bean.getProduct().getId());
			ps.setTimestamp(4, DateUtil.d2t(bean.getCreateDate()));
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			while(rs.next()){
				bean.setId(rs.getInt(1));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void delete(int id){			//ɾ��
		String sql = "delete from review where id = "+id;
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()) {
			s.execute(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void update(Review bean){		//�޸� 
		String sql = "update review set content = ?,uid = ?,pid = ?,createDate = ? where id = ?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)) {
			ps.setString(1, bean.getContent());
			ps.setInt(2, bean.getUser().getId());
			ps.setInt(3, bean.getProduct().getId());
			ps.setTimestamp(4, DateUtil.d2t(bean.getCreateDate()));
			ps.execute();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public Review get(int id){			//����id��ȡ����
		Review bean = null;
		String sql = "select * from review where id = "+id;
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()) {
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				bean = new Review();
				bean.setId(rs.getInt(1));
				bean.setContent(rs.getString(2));
				bean.setUser(new UserDAO().get(rs.getInt(3)));
				bean.setProduct(new ProductDAO().get(rs.getInt(4)));
				bean.setCreateDate(DateUtil.t2d(rs.getTimestamp(5)));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return bean;
	}

	public List<Review> list(int pid,int start,int count){		//��ҳ��ѯĳ��Ʒ������
		List<Review> beans = new ArrayList<Review>();
		String sql = "select * from review where pid = ? order by id desc limit ?,?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)) {
			ps.setInt(1, pid);
			ps.setInt(2, start);
			ps.setInt(3, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Review bean = new Review();
				bean.setId(rs.getInt(1));
				bean.setContent(rs.getString(2));
				bean.setUser(new UserDAO().get(rs.getInt(3)));
				bean.setProduct(new ProductDAO().get(rs.getInt(4)));
				bean.setCreateDate(DateUtil.t2d(rs.getTimestamp(5)));
				beans.add(bean);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return beans;
	}
	public List<Review> list(int pid){		//��ѯĳ��Ʒ����������
		return list(pid, 0, Short.MAX_VALUE);
	}
	public int getCount(int pid){		//��ѯĳ��Ʒ����������
		int count = 0;
		String  sql = "select * from review where pid = "+pid;
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()) {
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return count;
	}
	public boolean isExist(String content,int pid,int uid){			//�ж��û��Ƿ��ύ�˶���ظ�����
		String sql = "select * from review where content = ? and pid = ? and uid = ?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)) {
			ps.setString(1, content);
			ps.setInt(2, pid);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
}