package tmall.comparator;
 
import java.util.Comparator;

import tmall.bean.Product;

public class ProductDateComparator implements Comparator<Product>{	//��Ʒ�Ƚ���

	@Override
	public int compare(Product p1, Product p2) {
		// TODO �Զ����ɵķ������
		return  p1.getCreateDate().compareTo(p2.getCreateDate());
	}
	
}