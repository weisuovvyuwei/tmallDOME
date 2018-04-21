package tmall.bean;

import java.util.List;

public class Category {		//产品分类
	private int id;			//id
	private String name;	//分类名称
	List<Product> products;	//分类下产品
	List<List<Product>> productsByRow;	//首页分类下商品
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString(){
		return "Category [name"+name+"]";
	}
	public List<Product> getProducts(){
		return products;
	}
	public void setProducts(List<Product> products){
		this.products=products;
	}
	public List<List<Product>> getProductsByRow() {
		return productsByRow;
	}
	public void setProductsByRow(List<List<Product>> productsByRow) {
		this.productsByRow = productsByRow;
	}
}
