package com.orient.weibao.mbg.mapper;

import com.orient.weibao.mbg.model.CheckTempInst;
import com.orient.weibao.mbg.model.CheckTempInstExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CheckTempInstMapper {
    long countByExample(CheckTempInstExample example);

    int deleteByExample(CheckTempInstExample example);

    int deleteByPrimaryKey(String id);

    int insert(CheckTempInst record);

    int insertSelective(CheckTempInst record);

    List<CheckTempInst> selectByExample(CheckTempInstExample example);

    CheckTempInst selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CheckTempInst record, @Param("example") CheckTempInstExample example);

    int updateByExample(@Param("record") CheckTempInst record, @Param("example") CheckTempInstExample example);

    int updateByPrimaryKeySelective(CheckTempInst record);

    int updateByPrimaryKey(CheckTempInst record);
}