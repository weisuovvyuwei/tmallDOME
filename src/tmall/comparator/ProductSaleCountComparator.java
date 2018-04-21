package tmall.comparator;

import java.util.Comparator;

import tmall.bean.Product;

public class ProductSaleCountComparator implements Comparator<Product>{		//销量比较器

	@Override
	public int compare(Product p1, Product p2) {
		// TODO 自动生成的方法存根
		return p2.getSaleCount()-p1.getSaleCount();
	}
	
}
