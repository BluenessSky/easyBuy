package org.java.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.java.pojo.EasybuyUserAddress;
import org.java.pojo.EasybuyUserAddressExample;

public interface EasybuyUserAddressMapper {
    int countByExample(EasybuyUserAddressExample example);

    int deleteByExample(EasybuyUserAddressExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EasybuyUserAddress record);

    int insertSelective(EasybuyUserAddress record);

    List<EasybuyUserAddress> selectByExample(EasybuyUserAddressExample example);

    EasybuyUserAddress selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EasybuyUserAddress record, @Param("example") EasybuyUserAddressExample example);

    int updateByExample(@Param("record") EasybuyUserAddress record, @Param("example") EasybuyUserAddressExample example);

    int updateByPrimaryKeySelective(EasybuyUserAddress record);

    int updateByPrimaryKey(EasybuyUserAddress record);
}