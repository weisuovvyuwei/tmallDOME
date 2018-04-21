package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.util.DBUtil;

public class ProductImageDAO {
	public static final String type_single = "type_single";		//����ͼƬ
	public static final String type_detail = "type_detail";		//����ͼƬ
	
	public int getTotal(){		//��ȡ��ƷͼƬ����
		int total = 0;
		String sql = "select count(*) from Pronductimage";
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()){
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				total = rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return total;
	}
	public void add(ProductImage bean){		//����
		String sql = "insert into productimage values(null,?,?)";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)){
			ps.setInt(1, bean.getProduct().getId());
			ps.setString(2, bean.getType());
			ps.execute();
			
			ResultSet rs = ps.getGeneratedKeys();
			while(rs.next()){
				bean.setId(rs.getInt(1));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void update(ProductImage bean){		//�޸�
//		String sql = "update productimage set cid = ?,type = ? where id = ?";
//		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)) {
//			ps.setInt(1, bean.getProduct().getId());
//			ps.setString(2, bean.getType());
//			ps.setInt(3, bean.getId());
//			ps.execute();
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
	}
	public void delete(int id){		//ɾ��
		String sql = "delete from productimage where id = "+id;
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()) {
			s.execute(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public ProductImage get(int id){		//����id��ѯ
		ProductImage bean = null;
		String sql = "select * from productimage where id = "+id;
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()){
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				bean = new ProductImage();
				bean.setId(rs.getInt(1));
				bean.setProduct(new ProductDAO().get(rs.getInt(2)));
				bean.setType(rs.getString(3));
			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return bean;
	}
	public List<ProductImage> list(Product p,String type,int start,int count){		//��ҳ��ѯָ����Ʒ�£�ĳ�����͵�ProductImage
		List<ProductImage> beans = new ArrayList<ProductImage>();
		String sql = "select * from productimage where pid = ? and type = ? order by id desc limit ?,?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)) {
			ps.setInt(1, p.getId());
			ps.setString(2, type);
			ps.setInt(3, start);
			ps.setInt(4, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ProductImage bean = new ProductImage();
				bean.setId(rs.getInt(1));
				bean.setProduct(p);
				bean.setType(type);
				beans.add(bean);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return beans;
	}
	public List<ProductImage> list(Product p,String type){		//��ѯָ����Ʒ�£�ĳ�����͵�ProductImage
		return list(p, type, 0, Short.MAX_VALUE);
	}
}
