package tmall.comparator;

import java.util.Comparator;

import tmall.bean.Product;

public class ProductSaleCountComparator implements Comparator<Product>{		//�����Ƚ���

	@Override
	public int compare(Product p1, Product p2) {
		// TODO �Զ����ɵķ������
		return p2.getSaleCount()-p1.getSaleCount();
	}
	
}
