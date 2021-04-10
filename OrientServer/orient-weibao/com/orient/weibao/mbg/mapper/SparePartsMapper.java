package com.orient.weibao.mbg.mapper;

import com.orient.weibao.mbg.model.SpareParts;
import com.orient.weibao.mbg.model.SparePartsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SparePartsMapper {
    long countByExample(SparePartsExample example);

    int deleteByExample(SparePartsExample example);

    int deleteByPrimaryKey(String id);

    int insert(SpareParts record);

    int insertSelective(SpareParts record);

    List<SpareParts> selectByExample(SparePartsExample example);

    SpareParts selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SpareParts record, @Param("example") SparePartsExample example);

    int updateByExample(@Param("record") SpareParts record, @Param("example") SparePartsExample example);

    int updateByPrimaryKeySelective(SpareParts record);

    int updateByPrimaryKey(SpareParts record);
}