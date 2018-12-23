package org.java.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.java.pojo.EasybuyUser;
import org.java.pojo.EasybuyUserExample;

public interface EasybuyUserMapper {
    int countByExample(EasybuyUserExample example);

    int deleteByExample(EasybuyUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EasybuyUser record);

    int insertSelective(EasybuyUser record);

    List<EasybuyUser> selectByExample(EasybuyUserExample example);

    EasybuyUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EasybuyUser record, @Param("example") EasybuyUserExample example);

    int updateByExample(@Param("record") EasybuyUser record, @Param("example") EasybuyUserExample example);

    int updateByPrimaryKeySelective(EasybuyUser record);

    int updateByPrimaryKey(EasybuyUser record);
}