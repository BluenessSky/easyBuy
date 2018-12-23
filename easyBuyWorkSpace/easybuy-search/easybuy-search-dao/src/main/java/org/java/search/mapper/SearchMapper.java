package org.java.search.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.java.common.pojo.SearchProduct;
import org.java.pojo.EasybuyUserAddress;
import org.java.pojo.EasybuyUserAddressExample;

public interface SearchMapper {
	/**
	 * 查询所有商品
	 * @return
	 */
    List<SearchProduct> selectProduct();
    /**
     * 根据id获取对象
     * @param id
     * @return
     */
    SearchProduct selectProductbyId(Integer id);
}