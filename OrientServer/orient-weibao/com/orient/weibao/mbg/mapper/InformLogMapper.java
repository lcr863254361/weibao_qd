package com.orient.weibao.mbg.mapper;

import com.orient.weibao.mbg.model.InformLog;
import com.orient.weibao.mbg.model.InformLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InformLogMapper {
    long countByExample(InformLogExample example);

    int deleteByExample(InformLogExample example);

    int deleteByPrimaryKey(String id);

    int insert(InformLog record);

    int insertSelective(InformLog record);

    List<InformLog> selectByExample(InformLogExample example);

    InformLog selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") InformLog record, @Param("example") InformLogExample example);

    int updateByExample(@Param("record") InformLog record, @Param("example") InformLogExample example);

    int updateByPrimaryKeySelective(InformLog record);

    int updateByPrimaryKey(InformLog record);
}