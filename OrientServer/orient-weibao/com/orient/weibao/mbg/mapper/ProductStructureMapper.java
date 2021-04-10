package com.orient.weibao.mbg.mapper;

import com.orient.weibao.mbg.model.ProductStructure;
import com.orient.weibao.mbg.model.ProductStructureExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductStructureMapper {
    long countByExample(ProductStructureExample example);

    int deleteByExample(ProductStructureExample example);

    int deleteByPrimaryKey(String id);

    int insert(ProductStructure record);

    int insertSelective(ProductStructure record);

    List<ProductStructure> selectByExample(ProductStructureExample example);

    ProductStructure selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ProductStructure record, @Param("example") ProductStructureExample example);

    int updateByExample(@Param("record") ProductStructure record, @Param("example") ProductStructureExample example);

    int updateByPrimaryKeySelective(ProductStructure record);

    int updateByPrimaryKey(ProductStructure record);
}