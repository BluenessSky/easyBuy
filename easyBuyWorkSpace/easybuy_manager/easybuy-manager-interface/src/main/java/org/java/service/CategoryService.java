package org.java.service;

import java.util.List;

import org.java.common.pojo.EasyBuyResult;
import org.java.common.vo.CategoryVO;
import org.java.pojo.EasybuyProductCategory;

import com.github.pagehelper.PageInfo;

public interface CategoryService {
	EasyBuyResult getcategory1id(Integer id);
	/**
	 * 分页查询分类信息
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	PageInfo<CategoryVO> getCategoryPage(Integer currentPage,Integer pageSize);
}
