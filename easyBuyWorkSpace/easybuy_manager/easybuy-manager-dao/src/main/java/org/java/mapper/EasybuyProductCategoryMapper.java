package org.java.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.java.common.vo.CategoryVO;
import org.java.pojo.EasybuyProductCategory;
import org.java.pojo.EasybuyProductCategoryExample;

public interface EasybuyProductCategoryMapper {
    int countByExample(EasybuyProductCategoryExample example);

    int deleteByExample(EasybuyProductCategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EasybuyProductCategory record);

    int insertSelective(EasybuyProductCategory record);

    List<EasybuyProductCategory> selectByExample(EasybuyProductCategoryExample example);

    EasybuyProductCategory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EasybuyProductCategory record, @Param("example") EasybuyProductCategoryExample example);

    int updateByExample(@Param("record") EasybuyProductCategory record, @Param("example") EasybuyProductCategoryExample example);

    int updateByPrimaryKeySelective(EasybuyProductCategory record);

    int updateByPrimaryKey(EasybuyProductCategory record);
    
    List<CategoryVO> selectCategoryList();
}