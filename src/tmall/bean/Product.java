package tmall.bean;

import java.util.Date;
import java.util.List;

public class Product {			//产品类
	private String name;		//产品名字
	private String subTitle;	//产品副标题
	private float orignalPrice;	//产品价格
	private float promotePrice;	//促销价格
	private int stock;			//产品库存
    private Date createDate;	//创建日期
    private Category category;	//所属分类
    private int id;				//id
    private ProductImage firstProductImage;	//产品展示图片
    private List<ProductImage> productImages; //总产品图片
    private List<ProductImage> productSingleImages;	//产品显示图片
    private List<ProductImage> productDetailImages;	//产品详情图片
    private int reviewCount;	//评价数量
    private int saleCount;	//产品销量
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public float getOrignalPrice() {
		return orignalPrice;
	}
	public void setOrignalPrice(float orignalPrice) {
		this.orignalPrice = orignalPrice;
	}
	public float getPromotePrice() {
		return promotePrice;
	}
	public void setPromotePrice(float promotePrice) {
		this.promotePrice = promotePrice;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ProductImage getFirstProductImage() {
		return firstProductImage;
	}
	public void setFirstProductImage(ProductImage firstProductImage) {
		this.firstProductImage = firstProductImage;
	}
	public List<ProductImage> getProductImages() {
		return productImages;
	}
	public void setProductImages(List<ProductImage> productImages) {
		this.productImages = productImages;
	}
	public List<ProductImage> getProductSingleImages() {
		return productSingleImages;
	}
	public void setProductSingleImages(List<ProductImage> productSingleImages) {
		this.productSingleImages = productSingleImages;
	}
	public List<ProductImage> getProductDetailImages() {
		return productDetailImages;
	}
	public void setProductDetailImages(List<ProductImage> productDetailImages) {
		this.productDetailImages = productDetailImages;
	}
	public int getReviewCount() {
		return reviewCount;
	}
	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}
	public int getSaleCount() {
		return saleCount;
	}
	public void setSaleCount(int saleCount) {
		this.saleCount = saleCount;
	}
    
}
