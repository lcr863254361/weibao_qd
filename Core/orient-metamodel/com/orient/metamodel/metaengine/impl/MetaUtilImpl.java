package com.orient.metamodel.metaengine.impl;

import com.orient.metamodel.metadomain.MetaModel;
import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.metaengine.ErrorInfo;
import com.orient.metamodel.metaengine.MetaUtil;
import com.orient.metamodel.metaengine.business.OracleSchemaTranslator;
import com.orient.metamodel.metaengine.business.SchemaTranslator;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.springframework.web.context.WebApplicationContext;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;

/**
 * @author mengbin
 * @Date Feb 10, 2012		9:52:03 AM
 */
public class MetaUtilImpl implements MetaUtil {

    private MetaModel metaModel = null;

    /**
     * 初始化元数据模型，即实例化元数据模型
     *
     * @param reset 是否需要重新初始化
     * @return
     */
    @Override
    public MetaModel getMeta(boolean reset) {
        if (metaModel == null || reset) {
            if (!initMeta()) {
                return null;
            } else {
                return metaModel;
            }
        } else {
            return metaModel;
        }
    }

    /**
     * 获取元数据模型
     *
     * @return
     */
    @Override
    public boolean initMeta() {
        metaModel = new MetaModel();
        return metaModel.initMetaModel();
    }

    /**
     * 获取元数据模型的XML字符串
     *
     * @param name    Schema名称
     * @param version Schema版本
     * @return String
     */
    @Override
    public String getSchemaXML(String name, String version) {
        MetaModel model = getMeta(false);
        Schema schema = model.getSchema(name, version);
        if (schema == null) {
            return "";
        }
        OracleSchemaTranslator schemaIO = (OracleSchemaTranslator) schema.getBean("schemaio");
        schemaIO.schema2XMLSchema(schema);
        try {
            Mapping map = new Mapping();
            map.loadMapping(Schema.class.getResource("/") + "map.xml");
            Writer write = new StringWriter();
            Marshaller marshaller = new Marshaller(write);
            marshaller.setEncoding("GBK");
            marshaller.setMapping(map);
            marshaller.marshal(schema);
            return write.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 通过xml内容，转化为schema对象
     *
     * @param xmlContent xml内容
     * @return Schema
     */
    public Schema transformToSchema(String xmlContent) throws Exception {
        Mapping map = new Mapping();
        try {
            map.loadMapping(Schema.class.getResource("/") + "map.xml");
            Reader b = new StringReader(xmlContent);
            Unmarshaller unmarshaller = new Unmarshaller(map);
            unmarshaller.setValidation(false);
            return (Schema) unmarshaller.unmarshal(b);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 保存schema至Model
     *
     * @param schema
     * @return int 0 :成功 ;1：schema已经存在; -1:异常
     */
    public int saveSchema(Schema schema) throws Exception {
        String name = schema.getName();
        String version = schema.getVersion();
        if (getMeta(false).getSchema(name, version) != null) {
            return 1;
        }
        SchemaTranslator schemaio = (SchemaTranslator) schema.getBean("schemaio");
        schemaio.translateSchemaXmlToDB(false, schema, null);
        return 0;
    }

    public void refreshMetaData(String schemaId) {
        //刷新缓存
        metaModel.refreshSchema(schemaId);
    }

    /**
     * 通过xml内容更新数据库和内存数据
     *
     * @param xmlContent xml的内容
     * @return 错误信息
     */
    public ErrorInfo updateSchema(String xmlContent) throws Exception {
        Mapping map = new Mapping();
        ErrorInfo info = new ErrorInfo();
        map.loadMapping(Schema.class.getResource("/") + "map.xml");
        Reader b = new StringReader(xmlContent);
        Unmarshaller unmarshaller = new Unmarshaller(map);
        unmarshaller.setValidation(false);
        Schema schema = (Schema) unmarshaller.unmarshal(b);
        String name = schema.getName();
        String version = schema.getVersion();
        Schema dbSchema = getMeta(true).getSchema(name, version);
        if (dbSchema == null) {
            info.ErrorId = 1;
            info.Warning = "没有找到对应的Schema";
            return info;
        }
        SchemaTranslator schemaio = (SchemaTranslator) schema.getBean("schemaio");
        schemaio.translateSchemaXmlToDB(true, schema, dbSchema);
        //刷新缓存
        metaModel.refreshSchema(schema.getId());
        return info;
    }

    /**
     * 该方法没有用到
     *
     * @param name    Schema名称
     * @param version Schema版本
     * @return
     */
    @Override
    @Deprecated
    public int deleteSchema(String name, String version) {
        MetaModel model = getMeta(false);
        Schema schema = model.getSchema(name, version);
        if (schema == null) {
            return 1;
        }
        OracleSchemaTranslator schemaio = (OracleSchemaTranslator) schema.getBean("schemaio");
        return 0;
    }

    /**
     * 程序启动后就加载
     *
     * @param contextLoad context环境
     * @return true/false
     */
    @Override
    public boolean modelLoadRun(WebApplicationContext contextLoad) {
        this.getMeta(true);
        return true;
    }

}

