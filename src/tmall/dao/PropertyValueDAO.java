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
	public int getTotal(){		//获取总数
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
	public void add(PropertyValue bean){		//增加
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
	public void delete(int id){  		//删除
		String sql = "delete from propertyvalue where id = "+id;
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()) {
			s.execute(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void update(PropertyValue bean){		//修改
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
	public PropertyValue get(int id){		//根据id获取
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
	public PropertyValue get(int ptid,int pid){		//根据ptid,pid获取
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
	public List<PropertyValue> list(int start,int count){		//分页查询产品属性值
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
	public List<PropertyValue> list(){		//查询所有产品属性值
		return list(0, Short.MAX_VALUE);
	}
	public void init(Product p){
//		初始化某个产品对应的属性值，初始化逻辑：
//		1. 根据分类获取所有的属性 
//		2. 遍历每一个属性
//		2.1 根据属性和产品，获取属性值 
//		2.2 如果属性值不存在，就创建一个属性值对象
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
	public List<PropertyValue> list(int pid){		//查询某个产品下所有的属性值
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
