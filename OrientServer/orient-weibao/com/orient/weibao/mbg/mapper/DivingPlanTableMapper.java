package com.orient.weibao.mbg.mapper;

import com.orient.weibao.mbg.model.DivingPlanTable;
import com.orient.weibao.mbg.model.DivingPlanTableExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DivingPlanTableMapper {
    long countByExample(DivingPlanTableExample example);

    int deleteByExample(DivingPlanTableExample example);

    int deleteByPrimaryKey(String id);

    int insert(DivingPlanTable record);

    int insertSelective(DivingPlanTable record);

    List<DivingPlanTable> selectByExampleWithBLOBs(DivingPlanTableExample example);

    List<DivingPlanTable> selectByExample(DivingPlanTableExample example);

    DivingPlanTable selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") DivingPlanTable record, @Param("example") DivingPlanTableExample example);

    int updateByExampleWithBLOBs(@Param("record") DivingPlanTable record, @Param("example") DivingPlanTableExample example);

    int updateByExample(@Param("record") DivingPlanTable record, @Param("example") DivingPlanTableExample example);

    int updateByPrimaryKeySelective(DivingPlanTable record);

    int updateByPrimaryKeyWithBLOBs(DivingPlanTable record);

    int updateByPrimaryKey(DivingPlanTable record);
}