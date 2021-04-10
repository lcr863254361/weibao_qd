package com.orient.metamodel.metadomain;

import com.orient.metamodel.dijkstra.DijkstraSchema;
import com.orient.metamodel.operationinterface.IMetaModel;
import com.orient.metamodel.operationinterface.ISchema;
import com.orient.metamodel.pedigree.SchemaGraph;
import com.orient.utils.UtilFactory;

import java.util.*;

/**
 * 管理整个系统的元数据模型
 *
 * @author mengbin@cssrc.com.cn
 * @date Mar 16, 2012
 */
public class MetaModel extends BaseMetaBean implements IMetaModel {

    Map<String, Schema> schemas = new HashMap<>();    //key == name--version

    /**
     * @Fields schemaGraphMap :schema谱系Map
     * key：schema的ID
     * value：schema的谱系图
     */
    Map<String, SchemaGraph> schemaGraphMap = new HashMap<>();

    Map<String, DijkstraSchema> dijkstraGraphMap = new HashMap<>();

    Map<String, List<Schema>> schemaNameToSchemasMap = new HashMap<>();

    /**
     * 初始化MetaModel
     *
     * @return boolean
     */
    public boolean initMetaModel() {
        List<Schema> schemaList = (List<Schema>) getMetaDAOFactory().getSchemaDAO().findAll();

        for (Schema loopSchema : schemaList) {

            /**去除系统自带的Schema*/
            if (loopSchema.getId().equals("****")) {
                continue;
            }
            loopSchema = repairSchema(loopSchema);
            String name = loopSchema.getName();
            String version = loopSchema.getVersion();
            String key = name + "--" + version;

            List<Schema> schemasUnderName = schemaNameToSchemasMap.get(name);
            if (schemasUnderName == null) {
                schemasUnderName = UtilFactory.newArrayList();
                schemaNameToSchemasMap.put(name, schemasUnderName);
            }
            schemasUnderName.add(loopSchema);
            schemas.put(key, loopSchema);
            SchemaGraph graph = new SchemaGraph(loopSchema);
            schemaGraphMap.put(loopSchema.getId(), graph);
            DijkstraSchema dijkstraSchema = new DijkstraSchema(loopSchema);
            dijkstraGraphMap.put(loopSchema.getId(), dijkstraSchema);
        }

        for (List<Schema> schemas : schemaNameToSchemasMap.values()) {
            Collections.sort(schemas, new Comparator<Schema>() {
                @Override
                public int compare(Schema o1, Schema o2) {
                    double diff = Double.valueOf(o1.getVersion()) - Double.valueOf(o2.getVersion());
                    if (diff > 0) {
                        return 1;
                    } else if (diff < 0) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
        }

        return true;
    }

    public Map<String, Schema> getSchemas() {
        return schemas;
    }

    public Schema getLastVersionOfName(String name) {
        List<Schema> schemas = this.schemaNameToSchemasMap.get(name);
        return schemas == null ? null : schemas.get(schemas.size() - 1);
    }

    public Schema getSchema(String name, String version) {
        String key = name + "--" + version;
        return this.schemas.get(key);
    }

    /**
     * 调整Schema中和DS不符的逻辑结构
     *
     * @param oldSchema --调整之前的Schema
     * @return Schema                    --调整之后的Schema
     */
    private Schema repairSchema(Schema oldSchema) {
        //(1)在Schema 下面移除子数据类
        Set<Table> tables = oldSchema.getTables();
        Set<Table> childTables = new HashSet<>();
        for (Table table : tables) {
            Table parentTable = table.getParentTable();
            //去除已经删除的模型
            if (parentTable != null || table.getIsValid().equals(0l)) {
                childTables.add(table);
            }
        }
        tables.removeAll(childTables);
        oldSchema.setTables(tables);
        return oldSchema;
    }

    /**
     * @param name
     * @param version
     */
    @Override
    public ISchema getISchema(String name, String version) {
        String key = name + "--" + version;
        return this.schemas.get(key);
    }

    @Override
    public List<ISchema> getCommonSchema() {
        List<ISchema> retList = new ArrayList<>();
        for (Iterator iter = (Iterator) schemas.keySet().iterator(); iter.hasNext(); ) {
            String key = (String) iter.next();
            Schema schema = schemas.get(key);
            if (schema.getType().equalsIgnoreCase(String.valueOf(ISchema.TYPE_COMMON))) {
                retList.add(schema);
            }
        }
        return retList;
    }

    @Override
    public List<ISchema> getShareSchema() {
        List<ISchema> retList = new ArrayList<>();
        for (Iterator iter = (Iterator) schemas.keySet().iterator(); iter.hasNext(); ) {
            String key = (String) iter.next();
            Schema schema = schemas.get(key);
            if (schema.getType().equalsIgnoreCase(String.valueOf(ISchema.TYPE_SHARE))) {
                retList.add(schema);
            }
        }
        return retList;
    }

    @Override
    public ISchema getISchemaById(String Id) {
        for (Iterator iter = this.getSchemas().keySet().iterator(); iter.hasNext(); ) {
            String key = (String) iter.next();
            Schema schema = this.getSchemas().get(key);
            if (schema.getId().compareToIgnoreCase(Id) == 0) {
                return schema;
            }
        }
        return null;
    }

    @Override
    public List<ISchema> getISchemaByIsLockAndIsDelete(Integer isLock, Integer isDelete) {
        List<ISchema> retList = new ArrayList<>();
        for (Iterator iterator = this.getSchemas().keySet().iterator(); iterator.hasNext(); ) {
            String key = (String) iterator.next();
            Schema schema = this.getSchemas().get(key);
            if (isLock.equals(schema.getIsLock()) && isDelete.equals(schema.getIsdelete())) {
                retList.add(schema);
            }
        }
        return retList;
    }

    /**
     * 刷新schema对应的缓存结构
     *
     * @param schemaId schema的id
     */
    public void refreshSchema(String schemaId) {
        Schema schema = this.getMetaDAOFactory().getSchemaDAO().findById(schemaId);
        if (schema == null) {
            return;
        }
        Schema loopSchema = repairSchema(schema);
        String name = loopSchema.getName();
        String version = loopSchema.getVersion();
        String key = name + "--" + version;
        schemas.put(key, loopSchema);
        SchemaGraph graph = new SchemaGraph(loopSchema);
        schemaGraphMap.put(loopSchema.getId(), graph);
        DijkstraSchema dijkstraSchema = new DijkstraSchema(loopSchema);
        dijkstraGraphMap.put(loopSchema.getId(), dijkstraSchema);
    }

    public Map<String, SchemaGraph> getSchemaGraphMap() {
        return schemaGraphMap;
    }

    public Map<String, DijkstraSchema> getDijkstraGraphMap() {
        return dijkstraGraphMap;
    }

    @Override
    public List<ISchema> getISchemaByIsDelete(Integer isDelete) {
        List<ISchema> retList = new ArrayList<>();
        for (Iterator iterator = this.getSchemas().keySet().iterator(); iterator.hasNext(); ) {
            String key = (String) iterator.next();
            Schema schema = this.getSchemas().get(key);
            if (null != schema.getIsdelete() && schema.getIsdelete().equals(isDelete)) {
                retList.add(schema);
            }
        }
        return retList;
    }

    /**
     * 删除内存中的schema
     *
     * @param schemaId
     */
    public void deleteSchema(String schemaId) {
        //todo 如何可以的话，这里最好通过遍历schemas的value来找到schema，并删除
        Schema schema = getMetaDAOFactory().getSchemaDAO().findById(schemaId);
        if (schema == null) {
            return;
        }
        Schema loopSchema = repairSchema(schema);
        String name = loopSchema.getName();
        String version = loopSchema.getVersion();
        String key = name + "--" + version;
        schemas.remove(key);
    }

}

