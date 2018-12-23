package org.java.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.java.pojo.EasybuyOrder;
import org.java.pojo.EasybuyOrderExample;

public interface EasybuyOrderMapper {
    int countByExample(EasybuyOrderExample example);

    int deleteByExample(EasybuyOrderExample example);

    int deleteByPrimaryKey(String id);

    int insert(EasybuyOrder record);

    int insertSelective(EasybuyOrder record);

    List<EasybuyOrder> selectByExample(EasybuyOrderExample example);

    EasybuyOrder selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") EasybuyOrder record, @Param("example") EasybuyOrderExample example);

    int updateByExample(@Param("record") EasybuyOrder record, @Param("example") EasybuyOrderExample example);

    int updateByPrimaryKeySelective(EasybuyOrder record);

    int updateByPrimaryKey(EasybuyOrder record);
}