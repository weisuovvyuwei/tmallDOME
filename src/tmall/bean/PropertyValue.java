package tmall.bean;

public class PropertyValue {			//��Ʒ����ֵ��
	private String value;			//����ֵ
	private Product product;		//������Ʒ
	private Property property;		//������������
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