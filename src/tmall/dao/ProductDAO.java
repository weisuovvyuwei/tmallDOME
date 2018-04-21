package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

public class ProductDAO {
	public int getTotal(int cid){		//获取某分类下所有产品总数
		int total = 0;
		String sql = "select count(*) from product where cid = "+cid;
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
	public void add(Product bean){		//增加
		String sql = "insert into product values(null,?,?,?,?,?,?,?)";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)) {
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getSubTitle());
			ps.setFloat(3, bean.getOrignalPrice());
			ps.setFloat(4, bean.getPromotePrice());
			ps.setInt(5, bean.getStock());
			ps.setInt(6, bean.getCategory().getId());
			ps.setTimestamp(7, DateUtil.d2t(bean.getCreateDate()));
			ps.execute();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void delete(int id){			//删除
		String sql = "delete from product where id = "+id;
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()) {
			s.execute(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void update(Product bean){	//修改
		String sql = "update product set name = ?,subTitle = ?,orginalPrice = ?,"
					+"promotePrice = ?,stock = ?,cid = ?,createDate = ? where id = ?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)) {
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getSubTitle());
			ps.setFloat(3, bean.getOrignalPrice());
			ps.setFloat(4, bean.getPromotePrice());
			ps.setInt(5, bean.getStock());
			ps.setInt(6, bean.getCategory().getId());
			ps.setTimestamp(7, DateUtil.d2t(bean.getCreateDate()));
			ps.execute();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public Product get(int id){			//根据id获取一个产品对象
		Product bean = null;
		String sql = "select * from product where id ="+id;
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()) {
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				bean = new Product();
				bean.setId(rs.getInt(1));
				bean.setName(rs.getString(2));
				bean.setSubTitle(rs.getString(3));
				bean.setOrignalPrice(rs.getFloat(4));
				bean.setPromotePrice(rs.getFloat(5));
				bean.setStock(rs.getInt(6));
				bean.setCategory(new CategoryDAO().get(rs.getInt(7)));
				bean.setCreateDate(DateUtil.t2d(rs.getTimestamp(8)));
				setFirstProductImage(bean);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return bean;
	}
	public List<Product> list(int cid,int start,int count){		//分页查询某分类下的产品
		List<Product> beans = new ArrayList<Product>();
		String sql = "select * from product where cid = ? order by id desc limit ?,?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)) {
			ps.setInt(1, cid);
			ps.setInt(2, start);
			ps.setInt(3, count);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Product bean = new Product();
				bean.setId(rs.getInt("id"));
				bean.setName(rs.getString("name"));
				bean.setSubTitle(rs.getString("subTitle"));
				bean.setOrignalPrice(rs.getFloat("orignalPrice"));
				bean.setPromotePrice(rs.getFloat("promotePrice"));
				bean.setStock(rs.getInt("stock"));
				bean.setCategory(new CategoryDAO().get(rs.getInt("cid")));
				bean.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
				setFirstProductImage(bean);
				beans.add(bean);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return beans;
	}
	public List<Product> list(int cid){		//查询某分类下的所有产品
		return list(cid, 0, Short.MAX_VALUE);
	}
	public void fill(Category c){		//为分类填充产品集合
		List<Product> ps = this.list(c.getId());
		c.setProducts(ps);
	}
	public void fill(List<Category> cs){		//为分类填充产品集合
		for(Category c : cs){
			fill(c);
		}
	}	
	public void fillByRow(List<Category> cs) {
        int productNumberEachRow = 8;
        for (Category c : cs) {
            List<Product> products =  c.getProducts();
            List<List<Product>> productsByRow =  new ArrayList<>();
            for (int i = 0; i < products.size(); i+=productNumberEachRow) {
                int size = i+productNumberEachRow;
                size= size>products.size()?products.size():size;
                List<Product> productsOfEachRow =products.subList(i, size);
                productsByRow.add(productsOfEachRow);
            }
            c.setProductsByRow(productsByRow);
        }
    }
	public void setFirstProductImage(Product p){		//把第一个图片设置为主图片	
		List<ProductImage> pis = new ProductImageDAO().list(p, ProductImageDAO.type_single);
		if (!pis.isEmpty()) {
			p.setFirstProductImage(pis.get(0));
		}
	}
	public void setSaleAndReviewNumber(Product p){		//为产品设置销售和评价数量
		int saleCount = new OrderItemDAO().getSaleCount(p.getId());
        p.setSaleCount(saleCount);         
 
        int reviewCount = new ReviewDAO().getCount(p.getId());
        p.setReviewCount(reviewCount);
	}
	public void setSaleAndReviewNumber(List<Product> ps){		//为产品设置销售和评价数量
		for (Product p : ps) {
			setSaleAndReviewNumber(p);
		}
	}
	public List<Product> search(String keyword,int start,int count){
		List<Product> beans = new ArrayList<Product>();
		if (null==keyword||0==keyword.trim().length()) {
			return beans;
		}
		String sql = "select * from product where name like ? limit ?,?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)) {
			ps.setString(1, "%"+keyword.trim()+"%");
			ps.setInt(2, start);
			ps.setInt(3, count);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Product bean = new Product();
				bean.setId(rs.getInt("id"));
				bean.setName(rs.getString("name"));
				bean.setSubTitle(rs.getString("subTitle"));
				bean.setOrignalPrice(rs.getFloat("orignalPrice"));
				bean.setPromotePrice(rs.getFloat("promotePrice"));
				bean.setStock(rs.getInt("stock"));
				bean.setCategory(new CategoryDAO().get(rs.getInt("cid")));
				bean.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
				setFirstProductImage(bean);
				beans.add(bean);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return beans;
	}
}
