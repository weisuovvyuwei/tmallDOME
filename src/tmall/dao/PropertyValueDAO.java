package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Product;
import tmall.bean.Property;
import tmall.bean.PropertyValue;
import tmall.util.DBUtil;

public class PropertyValueDAO {
	public int getTotal(){		//��ȡ����
		int total = 0;
		String sql = "select count(*) from propertyvalue";
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
	public void add(PropertyValue bean){		//����
		String sql = "insert into propertyvalue values(null,?,?,?)";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)) {
			ps.setInt(1, bean.getProduct().getId());
			ps.setInt(2, bean.getProperty().getId());
			ps.setString(3, bean.getValue());
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			while (rs.next()) {
				bean.setId(rs.getInt(1));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void delete(int id){  		//ɾ��
		String sql = "delete from propertyvalue where id = "+id;
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()) {
			s.execute(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void update(PropertyValue bean){		//�޸�
		String sql = "update propertyvalue set pid = ?,ptid = ?,value = ? where id = ?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)) {
			ps.setInt(1, bean.getProduct().getId());
			ps.setInt(2, bean.getProperty().getId());
			ps.setString(3, bean.getValue());
			ps.setInt(4, bean.getId());
			ps.execute();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public PropertyValue get(int id){		//����id��ȡ
		PropertyValue bean = null;
		String sql = "select * from propertyvalue where id = "+id;
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()) {
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				bean = new PropertyValue();
				bean.setId(rs.getInt(1));
				bean.setProduct(new ProductDAO().get(rs.getInt("pid")));
				bean.setProperty(new PropertyDAO().get(rs.getInt("ptid")));
				bean.setValue(rs.getString(4));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return bean;
	}
	public PropertyValue get(int ptid,int pid){		//����ptid,pid��ȡ
		PropertyValue bean = null;
		String sql = "select * from propertyvalue where ptid = ? and pid = ?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)) {
			ps.setInt(1, ptid);
			ps.setInt(2, pid);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new PropertyValue();
				bean.setId(rs.getInt("id"));
				bean.setProduct(new ProductDAO().get(rs.getInt("pid")));
				bean.setProperty(new PropertyDAO().get(rs.getInt("ptid")));
				bean.setValue(rs.getString("value"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return bean;
	}
	public List<PropertyValue> list(int start,int count){		//��ҳ��ѯ��Ʒ����ֵ
		List<PropertyValue> beans = new ArrayList<PropertyValue>();
		String sql = "select * from propertyvalue order by id desc limit ?,?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)) {
			ps.setInt(1, start);
			ps.setInt(2, count);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				PropertyValue bean = new PropertyValue();
				bean.setId(rs.getInt(1));
				bean.setProduct(new ProductDAO().get(rs.getInt("pid")));
				bean.setProperty(new PropertyDAO().get(rs.getInt("ptid")));
				bean.setValue(rs.getString("value"));
				beans.add(bean);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return beans;
	}
	public List<PropertyValue> list(){		//��ѯ���в�Ʒ����ֵ
		return list(0, Short.MAX_VALUE);
	}
	public void init(Product p){
//		��ʼ��ĳ����Ʒ��Ӧ������ֵ����ʼ���߼���
//		1. ���ݷ����ȡ���е����� 
//		2. ����ÿһ������
//		2.1 �������ԺͲ�Ʒ����ȡ����ֵ 
//		2.2 �������ֵ�����ڣ��ʹ���һ������ֵ����
		List<Property> pts = new PropertyDAO().list(p.getCategory().getId());
		
		for (Property pt : pts) {
			PropertyValue pv = get(pt.getId(),p.getId());
			if (null==pv) {
				pv = new PropertyValue();
				pv.setProduct(p);
				pv.setProperty(pt);;
				this.add(pv);
			}
		}
	}
	public List<PropertyValue> list(int pid){		//��ѯĳ����Ʒ�����е�����ֵ
		List<PropertyValue> beans = new ArrayList<PropertyValue>();
		String sql = "select * from propertyvalue where pid = ?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)) {
			ps.setInt(1, pid);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				PropertyValue bean = new PropertyValue();
				bean.setId(rs.getInt(1));
				bean.setProduct(new ProductDAO().get(rs.getInt("pid")));
				bean.setProperty(new PropertyDAO().get(rs.getInt("ptid")));
				bean.setValue(rs.getString("value"));
				beans.add(bean);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return beans;
	}
}
