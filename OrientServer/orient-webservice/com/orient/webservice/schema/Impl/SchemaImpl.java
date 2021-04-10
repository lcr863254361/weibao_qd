package com.orient.webservice.schema.Impl;

import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.metaengine.ErrorInfo;
import com.orient.metamodel.operationinterface.IMetaModel;
import com.orient.utils.CommonTools;
import com.orient.webservice.tools.ShareModelInitializer;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SchemaImpl extends SchemaBean {

    private ShareModelInitializer initializer;

    @Transactional(propagation = Propagation.REQUIRED)
    public String getSchema(String name, String version) {
        String xmlContent = metaEngine.getSchemaXML(name, version);
        return xmlContent;
    }

    public List<String> getSchemaList() {
        List<String> schemaList = new ArrayList<>();
        IMetaModel metaModel = metaEngine.getMeta(false);
        Map<String, Schema> schemamap = metaModel.getSchemas();
        StringBuffer str = new StringBuffer();
        for (Map.Entry<String, Schema> entry : schemamap.entrySet()) {
            Schema schema = entry.getValue();
            String name = schema.getName();
            String version = schema.getVersion();
            String type = schema.getType();
            schemaList.add(getSchema(name, version));
        }
        return schemaList;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int setSchema(String xmlContent) {
        Schema schema;
        int info = 0;
        try {
            schema = metaEngine.transformToSchema(xmlContent);
            info = metaEngine.saveSchema(schema);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        if (0 == info) {
            //模型数据初始化
            metaEngine.refreshMetaData(schema.getId());
            initializer.initSchema(schema);
        }
        return info;
    }

    /**
     * @Function Name:  initCollabData
     * @Description: 初始化协同项目数据
     * @Date Created:  2014-3-29 下午02:00:20
     * @Author: Pan Duan Duan
     * @Last Modified:     ,  Date Modified:
     */
    private void initCollabData(Schema schema) {
        //TODO 待重构
        String schemaId = schema.getId();
        try {
            StringBuffer querySql = new StringBuffer();
            //获取相关模型ID
            String[] modelIds = new String[3];
            String[] modelNames = new String[]{"PRO_DIR", "PROJECT", "PRO_PLAN"};
            int index = 0;
            for (String modelName : modelNames) {
                querySql = new StringBuffer();
                querySql.append("SELECT ID FROM CWM_TABLES WHERE S_NAME=? AND SCHEMA_ID = ?");
                List<Map<String, Object>> queryList = metadaofactory.getJdbcTemplate().queryForList(querySql.toString(), new Object[]{modelName, schemaId});
                if (queryList.size() > 0) {
                    modelIds[index] = CommonTools.Obj2String(queryList.get(0).get("ID"));
                }
                index++;
            }
            //获取相关字段信息
            String[] columnIds = new String[3];
            String[] columnNames = new String[]{"DirName", "PROJECT_NAME", "PLAN_NAME"};
            index = 0;
            for (String columnName : columnNames) {
                querySql = new StringBuffer();
                querySql.append("SELECT ID FROM CWM_TAB_COLUMNS WHERE S_NAME=? AND TABLE_ID = ?");
                List<Map<String, Object>> queryList = metadaofactory.getJdbcTemplate().queryForList(querySql.toString(), new Object[]{columnName, modelIds[index]});
                if (queryList.size() > 0) {
                    columnIds[index] = CommonTools.Obj2String(queryList.get(0).get("ID"));
                }
                index++;
            }
            //插入bom相关数据
            String rootBomId = getNextId("SEQ_CWM_TBOM");
            StringBuffer insertSql = new StringBuffer();
            insertSql.append("insert into CWM_TBOM (ID, PID, TABLE_ID, VIEW_ID, TYPE, NAME, DESCRIPTION, DETAIL_TEXT, BIG_IMAGE, NOR_IMAGE, SMA_IMAGE, IS_ROOT, ORDER_SIGN, IS_VALID, XML_ID, SCHEMA_ID, COLUMN_ID, COLUMN_NAME, EXP, ORIGIN_EXP, URL) ");
            insertSql.append("values (?, null, null, null, null, '我的项目', null, null, null, null, null, 1, 0, 1, '000', ?, null, null, null, null, null)");
            metadaofactory.getJdbcTemplate().update(insertSql.toString(), new Object[]{rootBomId, schemaId});

            String nodeBomId = getNextId("SEQ_CWM_TBOM");
            insertSql = new StringBuffer();
            insertSql.append("insert into CWM_TBOM (ID, PID, TABLE_ID, VIEW_ID, TYPE, NAME, DESCRIPTION, DETAIL_TEXT, BIG_IMAGE, NOR_IMAGE, SMA_IMAGE, IS_ROOT, ORDER_SIGN, IS_VALID, XML_ID, SCHEMA_ID, COLUMN_ID, COLUMN_NAME, EXP, ORIGIN_EXP, URL) ");
            insertSql.append("values (?, ?, ?, null, 0, '我的项目', null, null, null, null, null, 0, 0, 1, '000000', null, null, null, null, null, null)");
            metadaofactory.getJdbcTemplate().update(insertSql.toString(), new Object[]{nodeBomId, rootBomId, modelIds[0]});

            String bomDirId = getNextId("SEQ_TBOM");
            insertSql = new StringBuffer();
            insertSql.append("insert into CWM_TBOM_DIR (ID, NAME, SCHEMA_ID, IS_LOCK, USERNAME, MODIFIED_TIME, LOCK_MODIFIED_TIME, IS_DELETE, TYPE, ORDER_SIGN) ");
            insertSql.append("values (?, '我的项目', ?, 0, 'SYSTEM', null, null, 1, 2, 1)");
            metadaofactory.getJdbcTemplate().update(insertSql.toString(), new Object[]{bomDirId, schemaId});
            //插入bom动态节点数据
            insertSql = new StringBuffer();
            insertSql.append("insert into CWM_DYNAMIC_TBOM (COLUMN_ID, DATA_SOURCE, ORDER_SIGN, TBOM_ID, TABLE_ID, VIEW_ID, ID, URL, DISPLAY, PID, EXP, ORIGIN_EXP) ");
            insertSql.append("values (?, ?, '1', ?, ?, null, '-1', null, 'Tab页', null, null, null)");
            metadaofactory.getJdbcTemplate().update(insertSql.toString(), new Object[]{columnIds[0], "t" + modelIds[1], nodeBomId, modelIds[0]});

            insertSql = new StringBuffer();
            insertSql.append("insert into CWM_DYNAMIC_TBOM (COLUMN_ID, DATA_SOURCE, ORDER_SIGN, TBOM_ID, TABLE_ID, VIEW_ID, ID, URL, DISPLAY, PID, EXP, ORIGIN_EXP) ");
            insertSql.append("values (?, ?, '1', ?, ?, null, '-2', null, 'Tab页', '-1', null, null)");
            metadaofactory.getJdbcTemplate().update(insertSql.toString(), new Object[]{columnIds[1], "t" + modelIds[2], nodeBomId, modelIds[1]});

            insertSql = new StringBuffer();
            insertSql.append("insert into CWM_DYNAMIC_TBOM (COLUMN_ID, DATA_SOURCE, ORDER_SIGN, TBOM_ID, TABLE_ID, VIEW_ID, ID, URL, DISPLAY, PID, EXP, ORIGIN_EXP) ");
            insertSql.append("values (?, ?, '1', ?, ?, null, '-3', null, 'Tab页', '-2', null, null)");
            metadaofactory.getJdbcTemplate().update(insertSql.toString(), new Object[]{columnIds[2], "t" + modelIds[2], nodeBomId, modelIds[2]});
            //插入项目文件夹根节点
            insertSql = new StringBuffer();
            insertSql.append("insert into PRO_DIR_" + schemaId + " (ID,PRO_DIR_" + schemaId + "_ID, DIRNAME_" + modelIds[0] + ") ");
            insertSql.append("values ('-1','-2','我的项目文件夹')");
            metadaofactory.getJdbcTemplate().update(insertSql.toString(), new Object[]{});

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Function Name:  getNextId
     * @Description: @param seqName
     * @Description: @return 获取下一个seq的值
     * @Date Created:  2014-3-29 下午02:19:29
     * @Author: Pan Duan Duan
     * @Last Modified:     ,  Date Modified:
     */
    private String getNextId(String seqName) {

        StringBuffer querySql = new StringBuffer();
        querySql.append("SELECT ").append(seqName).append(".NEXTVAL").append(" FROM DUAL");
        return metadaofactory.getJdbcTemplate().queryForObject(querySql.toString(), String.class);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int updateSchema(String xmlContent) {
        //metaEngine.initMeta();
        ErrorInfo info = null;
        try {
            info = metaEngine.updateSchema(xmlContent);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return info.ErrorId;
    }

    public void setInitializer(ShareModelInitializer initializer) {
        this.initializer = initializer;
    }

}
