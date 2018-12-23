package org.java.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.java.pojo.EasybuyBigcontent;
import org.java.pojo.EasybuyBigcontentExample;

public interface EasybuyBigcontentMapper {
    int countByExample(EasybuyBigcontentExample example);

    int deleteByExample(EasybuyBigcontentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EasybuyBigcontent record);

    int insertSelective(EasybuyBigcontent record);

    List<EasybuyBigcontent> selectByExample(EasybuyBigcontentExample example);

    EasybuyBigcontent selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EasybuyBigcontent record, @Param("example") EasybuyBigcontentExample example);

    int updateByExample(@Param("record") EasybuyBigcontent record, @Param("example") EasybuyBigcontentExample example);

    int updateByPrimaryKeySelective(EasybuyBigcontent record);

    int updateByPrimaryKey(EasybuyBigcontent record);
}