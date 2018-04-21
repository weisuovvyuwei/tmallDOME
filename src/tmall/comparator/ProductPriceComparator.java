package tmall.comparator;

import java.util.Comparator;

import tmall.bean.Product;

public class ProductPriceComparator implements Comparator<Product>{	//价格比较器

	@Override
	public int compare(Product p1, Product p2) {
		// TODO 自动生成的方法存根
		return (int) (p1.getPromotePrice()-p2.getPromotePrice());
	}
	
}
