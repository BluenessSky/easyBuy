package org.java.service;

import java.util.List;

import org.java.common.pojo.EasyBuyResult;
import org.java.pojo.EasybuyProduct;
import org.java.pojo.EasybuyProductCategory;

import com.github.pagehelper.PageInfo;

/***
 * 商品服务接口
 * @author Administrator
 *
 */
public interface ProductService {
	/**
	 * 根据商品编号查询商品对象
	 * @param id  商品编号
	 * @return    商品对象
	 */
	EasybuyProduct getById(Integer id);
	/**
	 * 分页查询
	 * @param currentPage 当前要查询的页码
	 * @param pageSize	每页显示的信息条数
	 * @return          一页的信息
	 */
	PageInfo<EasybuyProduct> getProducts(Integer currentPage,Integer pageSize);
	/**
	 * 添加或修改商品
	 * @param product
	 * @return
	 */
	EasyBuyResult updateOrSave(EasybuyProduct product);
	/**
	 * 
	 * 删除商品
	 * @param product
	 * @return
	 */
	EasyBuyResult deleteProduct(Integer id);
}
