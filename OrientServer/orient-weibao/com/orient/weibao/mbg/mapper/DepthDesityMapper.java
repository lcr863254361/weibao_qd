package com.orient.weibao.mbg.mapper;

import com.orient.weibao.mbg.model.DepthDesity;
import com.orient.weibao.mbg.model.DepthDesityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DepthDesityMapper {
    long countByExample(DepthDesityExample example);

    int deleteByExample(DepthDesityExample example);

    int deleteByPrimaryKey(String id);

    int insert(DepthDesity record);

    int insertSelective(DepthDesity record);

    List<DepthDesity> selectByExample(DepthDesityExample example);

    DepthDesity selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") DepthDesity record, @Param("example") DepthDesityExample example);

    int updateByExample(@Param("record") DepthDesity record, @Param("example") DepthDesityExample example);

    int updateByPrimaryKeySelective(DepthDesity record);

    int updateByPrimaryKey(DepthDesity record);
}