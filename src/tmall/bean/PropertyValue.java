package tmall.bean;

public class PropertyValue {			//产品属性值类
	private String value;			//属性值
	private Product product;		//所属产品
	private Property property;		//所属属性名称
	private int id;					//id
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Property getProperty() {
		return property;
	}
	public void setProperty(Property property) {
		this.property = property;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
