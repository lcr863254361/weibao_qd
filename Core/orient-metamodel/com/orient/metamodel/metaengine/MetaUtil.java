package com.orient.metamodel.metaengine;

import com.orient.edm.init.IContextLoadRun;
import com.orient.metamodel.metadomain.MetaModel;
import com.orient.metamodel.metadomain.Schema;
import org.springframework.web.context.WebApplicationContext;

/**
 * 对元数据模型的操作接口,供其他模块调用
 *
 * @author mengbin
 * @Date Feb 7, 2012		8:59:47 AM
 */
public interface MetaUtil extends IContextLoadRun {

    /**
     * 初始化元数据模型，即实例化元数据模型
     *
     * @return boolean
     */
    boolean initMeta();

    /**
     * 获取元数据模型
     *
     * @param bReset 是否需要重新初始化
     * @return MetaModel
     */
    MetaModel getMeta(boolean bReset);

    /**
     * 获取元数据模型的XML字符串
     *
     * @param name    Schema名称
     * @param version Schema版本
     */
    String getSchemaXML(String name, String version);

    /**
     * 通过xml内容，保存schema至Model
     *
     * @param schema
     * @return int 0 :成功 ;1：schema已经存在; -1:异常
     */
    int saveSchema(Schema schema) throws Exception;

    /**
     * 把xml转为schema对象
     *
     * @param xmlContent
     * @return Schema
     */
    Schema transformToSchema(String xmlContent) throws Exception;

    /**
     * 通过xml内容，更新schema
     *
     * @param xmlContent
     * @return ErrorInfo
     */
    ErrorInfo updateSchema(String xmlContent) throws Exception;

    /**
     * 删除数据模型
     *
     * @param name    Schema名称
     * @param version Schema版本
     * @return int 0 :成功 ;1：schema不存在; -1:异常
     */
    int deleteSchema(String name, String version);

    /**
     * 刷新缓存
     *
     * @param schemaId
     */
    void refreshMetaData(String schemaId);

    /**
     * 程序启动时加载改模块
     *
     * @param contextLoad spring的上下文
     * @return boolean
     */
    @Override
    boolean modelLoadRun(WebApplicationContext contextLoad);

}

