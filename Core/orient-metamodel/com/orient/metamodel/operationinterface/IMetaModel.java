package com.orient.metamodel.operationinterface;

import com.orient.metamodel.dijkstra.DijkstraSchema;
import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.pedigree.SchemaGraph;

import java.util.List;
import java.util.Map;

/**
 * 对整个MetaModel的操作
 *
 * @author mengbin@cssrc.com.cn
 * @date Mar 16, 2012
 */
    public interface IMetaModel {

    ISchema getISchema(String name, String version);

    ISchema getISchemaById(String Id);

    List<ISchema> getISchemaByIsLockAndIsDelete(Integer isLock, Integer isDelete);

    List<ISchema> getISchemaByIsDelete(Integer isDelete);

    List<ISchema> getCommonSchema();

    List<ISchema> getShareSchema();

    Map<String, Schema> getSchemas();

    /**
     * 获取当前库中所有Schema的谱系Map
     *
     * @return Map<String,SchemaGraph> key:Schema的ID value:SchemaGraph 谱系图
     */
    Map<String, SchemaGraph> getSchemaGraphMap();

    Map<String, DijkstraSchema> getDijkstraGraphMap();

}

