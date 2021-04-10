package com.orient.weibao.mbg.mapper;

import com.orient.weibao.mbg.model.CarryTool;
import com.orient.weibao.mbg.model.CarryToolExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CarryToolMapper {
    long countByExample(CarryToolExample example);

    int deleteByExample(CarryToolExample example);

    int deleteByPrimaryKey(String id);

    int insert(CarryTool record);

    int insertSelective(CarryTool record);

    List<CarryTool> selectByExample(CarryToolExample example);

    CarryTool selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CarryTool record, @Param("example") CarryToolExample example);

    int updateByExample(@Param("record") CarryTool record, @Param("example") CarryToolExample example);

    int updateByPrimaryKeySelective(CarryTool record);

    int updateByPrimaryKey(CarryTool record);
}