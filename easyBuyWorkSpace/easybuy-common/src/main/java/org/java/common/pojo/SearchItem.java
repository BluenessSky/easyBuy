package org.java.common.pojo;

import java.io.Serializable;
import java.util.List;

public class SearchItem implements Serializable{
	/**总的页数*/
	private Integer pageCount;
	/**总的信息条数*/
	private long count;
	private List<SearchProduct> productList;
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public List<SearchProduct> getProductList() {
		return productList;
	}
	public void setProductList(List<SearchProduct> productList) {
		this.productList = productList;
	}
	
}
