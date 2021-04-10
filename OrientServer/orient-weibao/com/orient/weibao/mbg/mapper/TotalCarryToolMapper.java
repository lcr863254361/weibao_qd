package com.orient.weibao.mbg.mapper;

import com.orient.weibao.mbg.model.TotalCarryTool;
import com.orient.weibao.mbg.model.TotalCarryToolExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TotalCarryToolMapper {
    long countByExample(TotalCarryToolExample example);

    int deleteByExample(TotalCarryToolExample example);

    int deleteByPrimaryKey(String id);

    int insert(TotalCarryTool record);

    int insertSelective(TotalCarryTool record);

    List<TotalCarryTool> selectByExample(TotalCarryToolExample example);

    TotalCarryTool selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TotalCarryTool record, @Param("example") TotalCarryToolExample example);

    int updateByExample(@Param("record") TotalCarryTool record, @Param("example") TotalCarryToolExample example);

    int updateByPrimaryKeySelective(TotalCarryTool record);

    int updateByPrimaryKey(TotalCarryTool record);
}