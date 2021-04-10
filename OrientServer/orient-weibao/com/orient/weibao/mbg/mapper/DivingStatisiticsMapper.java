package com.orient.weibao.mbg.mapper;

import com.orient.weibao.mbg.model.DivingStatisitics;
import com.orient.weibao.mbg.model.DivingStatisiticsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DivingStatisiticsMapper {
    long countByExample(DivingStatisiticsExample example);

    int deleteByExample(DivingStatisiticsExample example);

    int deleteByPrimaryKey(String id);

    int insert(DivingStatisitics record);

    int insertSelective(DivingStatisitics record);

    List<DivingStatisitics> selectByExample(DivingStatisiticsExample example);

    DivingStatisitics selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") DivingStatisitics record, @Param("example") DivingStatisiticsExample example);

    int updateByExample(@Param("record") DivingStatisitics record, @Param("example") DivingStatisiticsExample example);

    int updateByPrimaryKeySelective(DivingStatisitics record);

    int updateByPrimaryKey(DivingStatisitics record);
}