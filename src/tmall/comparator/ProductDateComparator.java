package tmall.comparator;
 
import java.util.Comparator;

import tmall.bean.Product;

public class ProductDateComparator implements Comparator<Product>{	//新品比较器

	@Override
	public int compare(Product p1, Product p2) {
		// TODO 自动生成的方法存根
		return  p1.getCreateDate().compareTo(p2.getCreateDate());
	}
	
}