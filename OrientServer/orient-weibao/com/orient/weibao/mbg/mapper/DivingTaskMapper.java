package com.orient.weibao.mbg.mapper;

import com.orient.weibao.mbg.model.DivingTask;
import com.orient.weibao.mbg.model.DivingTaskExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DivingTaskMapper {
    long countByExample(DivingTaskExample example);

    int deleteByExample(DivingTaskExample example);

    int deleteByPrimaryKey(String id);

    int insert(DivingTask record);

    int insertSelective(DivingTask record);

    List<DivingTask> selectByExample(DivingTaskExample example);

    DivingTask selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") DivingTask record, @Param("example") DivingTaskExample example);

    int updateByExample(@Param("record") DivingTask record, @Param("example") DivingTaskExample example);

    int updateByPrimaryKeySelective(DivingTask record);

    int updateByPrimaryKey(DivingTask record);
}