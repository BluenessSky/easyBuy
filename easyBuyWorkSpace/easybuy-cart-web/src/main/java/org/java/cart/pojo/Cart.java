package org.java.cart.pojo;

import java.io.Serializable;
import java.util.List;

import org.java.pojo.EasybuyProduct;

public class Cart implements Serializable{
	private List<EasybuyProduct> items;			//购物车中的商品列表
	private Float sum;				//总的商品价格
	
	public List<EasybuyProduct> getItems() {
		return items;
	}
	public void setItems(List<EasybuyProduct> items) {
		this.items = items;
	}
	public Float getSum() {
		Float priceSum = 0F;
		//计算总价格后返回
		for (EasybuyProduct product : items) {
			priceSum += product.getPrice() * product.getStock();
		}
		return priceSum;
	}
	public void setSum(Float sum) {
		this.sum = sum;
	}
	
}
