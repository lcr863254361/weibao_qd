package com.orient.metamodel.metaengine.business;

import com.orient.metamodel.metadomain.Schema;

/**
 * SchemaTranslator interface, which translate schema xml defined by DS to DB
 *
 * @author Seraph
 *         2016-10-11 下午1:55
 */
public interface SchemaTranslator {

    /**
     * 由XML生成的Schema保存/更新到数据库
     *
     * @param update    是否是更新操作
     * @param newSchema 新的schema
     * @param oldSchema 旧的schema
     * @throws Exception
     */
    default void translateSchemaXmlToDB(boolean update, Schema newSchema, Schema oldSchema) throws Exception {
        preProcess();
        if (update) {
            updateDB(newSchema, oldSchema);
        } else {
            saveAsDB(newSchema);
        }
        postProcess();
    }

    /**
     * 把schema处理成转为xml所需要的schema
     *
     * @param schema
     * @return
     */
    boolean schema2XMLSchema(Schema schema);

    /**
     * 保存一份schema
     *
     * @param newSchema
     * @throws Exception
     */
    void saveAsDB(Schema newSchema) throws Exception;

    /**
     * 更新一个schema
     *
     * @param newSchema
     * @param oldSchema
     * @throws Exception
     */
    void updateDB(Schema newSchema, Schema oldSchema) throws Exception;

    /**
     * 前处理
     */
    void preProcess();

    /**
     * 后处理
     */
    void postProcess();

}
