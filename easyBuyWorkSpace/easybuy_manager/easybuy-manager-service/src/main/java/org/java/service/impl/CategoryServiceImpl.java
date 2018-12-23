package org.java.service.impl;

import java.util.List;

import org.java.common.pojo.EasyBuyResult;
import org.java.common.vo.CategoryVO;
import org.java.mapper.EasybuyProductCategoryMapper;
import org.java.pojo.EasybuyProductCategory;
import org.java.pojo.EasybuyProductCategoryExample;
import org.java.pojo.EasybuyProductCategoryExample.Criteria;
import org.java.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	private EasybuyProductCategoryMapper easybuyProductCategoryMapper;
	@Override
	public EasyBuyResult getcategory1id(Integer id) {
		try{
		EasybuyProductCategoryExample example=new EasybuyProductCategoryExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andParentidEqualTo(id);
		List<EasybuyProductCategory> list = easybuyProductCategoryMapper.selectByExample(example);
		return EasyBuyResult.ok(list);
		}catch(Exception e){
			return EasyBuyResult.build(500,"根据分类id查询失败");
		}
	}
	@Override
	public PageInfo<CategoryVO> getCategoryPage(Integer currentPage, Integer pageSize) {
		if(currentPage==null){
			currentPage=1;
		}
		if(pageSize==null){
			pageSize=7;
		}
		PageHelper.startPage(currentPage, pageSize);
		EasybuyProductCategoryExample example=new EasybuyProductCategoryExample();
		//List<CategoryVO> category = easybuyProductCategoryMapper.selectByExample(example);
		List<CategoryVO> category = easybuyProductCategoryMapper.selectCategoryList();
		PageInfo<CategoryVO> pageInfo=new PageInfo<>(category);
		return pageInfo;
	}

}
