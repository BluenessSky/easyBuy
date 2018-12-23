package org.java.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.java.pojo.EasybuyProduct;
import org.java.pojo.EasybuyProductExample;

public interface EasybuyProductMapper {
    int countByExample(EasybuyProductExample example);

    int deleteByExample(EasybuyProductExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EasybuyProduct record);

    int insertSelective(EasybuyProduct record);

    List<EasybuyProduct> selectByExample(EasybuyProductExample example);

    EasybuyProduct selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EasybuyProduct record, @Param("example") EasybuyProductExample example);

    int updateByExample(@Param("record") EasybuyProduct record, @Param("example") EasybuyProductExample example);

    int updateByPrimaryKeySelective(EasybuyProduct record);

    int updateByPrimaryKey(EasybuyProduct record);
}