package com.orient.dsrestful.business;

import com.orient.dsrestful.domain.CommonSchema;
import com.orient.dsrestful.domain.SchemaContentBean;
import com.orient.dsrestful.domain.SchemaContentResponse;
import com.orient.dsrestful.domain.lock.LockResponse;
import com.orient.dsrestful.domain.lock.LockStatus;
import com.orient.dsrestful.domain.schemaBaseInfo.FrontSchema;
import com.orient.dsrestful.domain.schemaBaseInfo.SchemaResponse;
import com.orient.dsrestful.domain.schemaXml.XmlContent;
import com.orient.dsrestful.enums.*;
import com.orient.dsrestful.event.DeleteSchemaEvent;
import com.orient.dsrestful.event.SaveSchemaEvent;
import com.orient.dsrestful.event.UpdateSchemaEvent;
import com.orient.dsrestful.eventparam.DeleteSchemaParam;
import com.orient.dsrestful.eventparam.SaveSchemaParam;
import com.orient.dsrestful.eventparam.UpdateSchemaParam;
import com.orient.dsrestful.service.BaseSchemaService;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.metamodel.metadomain.Column;
import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.metadomain.Table;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.metamodel.operationinterface.IMetaModel;
import com.orient.metamodel.operationinterface.ISchema;
import com.orient.sysman.util.RestoreCommandUtil;
import com.orient.sysmodel.service.file.FileService;
import com.orient.utils.CommonTools;
import com.orient.web.base.BaseBusiness;
import com.orient.web.base.dsbean.CommonDataBean;
import com.orient.web.base.dsbean.CommonResponse;
import com.orient.web.springmvcsupport.exception.DSException;
import com.orient.utils.exception.OrientBaseAjaxException;
import com.orient.webservice.tools.ShareModelInitializer;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.Reader;
import java.io.StringReader;
import java.util.*;

/**
 * Created by GNY on 2018/3/26
 */
@Component
public class SchemaOperationBusiness extends BaseBusiness {

    @Autowired
    BaseSchemaService baseSchemaService;

    @Autowired
    MetaDAOFactory metaDaoFactory;

    @Autowired
    FileService fileService;

    @Autowired
    ShareModelInitializer shareModelInitializer;

    @Autowired
    HibernateTransactionManager hibernateTransactionManager;

    public SchemaContentResponse obtainSchemaXml(CommonSchema commonSchema) {
        SchemaContentResponse retVal = new SchemaContentResponse();
        SchemaContentBean result = new SchemaContentBean();
        try {
            result.setSchemaName(commonSchema.getSchemaName());
            result.setVersion(commonSchema.getVersion());
            result.setSchemaXml(baseSchemaService.obtainSchemaXml(commonSchema.getSchemaName(), commonSchema.getVersion()));
            retVal.setResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            retVal.setSuccess(false);
            retVal.setMsg("???????????????");
            retVal.setResult(result);
            return retVal;
        }
        return retVal;
    }

    public SchemaResponse obtainSchemaInfoList() {
        SchemaResponse retVal = new SchemaResponse();
        List<FrontSchema> result = new ArrayList<>();
        try {
            IMetaModel metaModel = metaEngine.getMeta(false);
            Map<String, Schema> schemaMap = metaModel.getSchemas();
            schemaMap.forEach((schemaName, schema) -> {
                FrontSchema frontSchema = new FrontSchema();
                frontSchema.setLockType(schema.getIsLock() + "");
                frontSchema.setSchemaType(schema.getType());
                frontSchema.setSchemaId(schema.getId());
                frontSchema.setSchemaName(schema.getName());
                frontSchema.setVersion(schema.getVersion());
                result.add(frontSchema);
            });
            retVal.setResult(result);
        } catch (Exception e) {
            retVal.setSuccess(false);
            retVal.setMsg("???????????????");
            return retVal;
        }
        return retVal;
    }

    /**
     * @param commonSchema
     * @return -1:?????? 0:?????? 1:schema?????????
     */
    public CommonResponse deleteSchema(CommonSchema commonSchema) {
        CommonResponse retVal = new CommonResponse();
        CommonDataBean result = new CommonDataBean();
        IMetaModel metaModel = metaEngine.getMeta(false);
        ISchema schemaMap = metaModel.getISchema(commonSchema.getSchemaName(), commonSchema.getVersion());
        if (schemaMap != null) {
            String schemaId = schemaMap.getId();
            Schema schema = metaDaoFactory.getSchemaDAO().findById(schemaId);
            DeleteSchemaParam eventParam = new DeleteSchemaParam(schema);
            try {
                //????????????????????????????????????????????????
                OrientContextLoaderListener.Appwac.publishEvent(new DeleteSchemaEvent(this, eventParam));
                result.setStatus(SchemaDeleteResponseEnum.TYPE_DELETE_SUCCESS.toString());
                retVal.setResult(result);
            } catch (Exception e) {
                e.printStackTrace();
                throw new DSException(SchemaDeleteResponseEnum.TYPE_EXCEPTION.getType(), "??????????????????" + e.getMessage());
            }
        } else {
            throw new DSException(SchemaDeleteResponseEnum.TYPE_SCHEMA_NOT_EXIST.toString(), "schema?????????");
        }
        return retVal;
    }

    /**
     * ????????????????????????????????????????????????????????????????????????
     *
     * @param commonSchema
     * @return
     */
    public LockResponse isExistData(CommonSchema commonSchema) {
        LockResponse retVal = new LockResponse();
        LockStatus result = new LockStatus();
        try {
            IMetaModel metaModel = metaEngine.getMeta(false);
            ISchema schemaMap = metaModel.getISchema(commonSchema.getSchemaName(), commonSchema.getVersion());
            if (schemaMap != null) {
                String schemaId = schemaMap.getId();
                Schema schema = metaDaoFactory.getSchemaDAO().findById(schemaId);
                result.setUsername(schema.getUsername());
                result.setIp(schema.getIp());
                if (schema.getIsLock() == SchemaLockStatusEnum.TYPE_LOCKED.value()) {
                    result.setStatus(SchemaDataExistEnum.TYPE_LOCKED.toString());
                    retVal.setResult(result);
                    retVal.setSuccess(false);
                    retVal.setMsg("schema????????????");
                    return retVal;
                }
                //???????????????????????????????????????????????????
                List<Table> tableList = metaDaoFactory.getTableDAO().findBySchemaidAndIsValidAndMapTable(schema, new Long(1));
                if (!tableList.isEmpty()) {
                    for (Table table : tableList) {
                        String tableName = table.getTableName();
                        List<Map> a = metaDaoFactory.getTableDAO().getSqlResultByTableName(tableName);
                        if (!a.isEmpty()) {
                            if (metaDaoFactory.getTableDAO().getCountByTableName(tableName) != 0) {
                                result.setStatus(SchemaDataExistEnum.TYPE_HAS_DATA.toString());
                                retVal.setResult(result);
                                return retVal;
                            }
                        } else {
                            result.setStatus(SchemaDataExistEnum.TYPE_TABLE_NOT_EXIST.toString());
                            retVal.setResult(result);
                            retVal.setSuccess(false);
                            retVal.setMsg("????????????");
                            return retVal;
                        }
                    }
                }
            } else {
                result.setStatus(SchemaDataExistEnum.TYPE_SCHEMA_NOT_EXIST.toString());
                retVal.setResult(result);
                retVal.setSuccess(false);
                retVal.setMsg("schema?????????");
                return retVal;
            }
            result.setStatus(SchemaDataExistEnum.TYPE_NO_DATA.toString());
            retVal.setResult(result);
            return retVal;
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(SchemaDataExistEnum.TYPE_EXCEPTION.toString());
            retVal.setResult(result);
            retVal.setSuccess(false);
            retVal.setMsg("???????????????");
            return retVal;
        }
    }

    //0:??????, 1:schema????????????(schema??????), -1:??????
    public CommonResponse saveSchema(XmlContent xmlContent) {
        CommonResponse retVal = new CommonResponse();
        CommonDataBean result = new CommonDataBean();
        try {
            //???xml??????schema??????
            Schema schema = metaEngine.transformToSchema(xmlContent.getXmlContent());
            if (metaEngine.getMeta(false).getSchema(schema.getName(), schema.getVersion()) != null) {
                throw new DSException(SchemaSaveResponseEnum.TYPE_SCHEMA_DUPLICATION_NAME.getType(), "???????????????????????????????????????schema??????????????????????????????????????????");
            } else {
                SaveSchemaParam param = new SaveSchemaParam();
                param.setSchema(schema);
                Map<String, Column> columnMap = new HashMap<>();
                Map<String, List<Column>> restrictionRefColumnsMap = new HashMap<>();
                Map<Integer, String> createViewSqlMap = new HashMap<>();
                param.setColumnMap(columnMap);
                param.setRestrictionRefColumnsMap(restrictionRefColumnsMap);
                param.setCreateViewSqlMap(createViewSqlMap);
                OrientContextLoaderListener.Appwac.publishEvent(new SaveSchemaEvent(this, param));
                result.setStatus(SchemaSaveResponseEnum.TYPE_SAVE_SUCCESS.getType());
                retVal.setResult(result);
            }
        } catch (Exception e) {
            throw new DSException(SchemaSaveResponseEnum.TYPE_EXCEPTION.getType(), "??????????????????" + e.getMessage());
        }
        return retVal;
    }

    public CommonResponse updateSchema(XmlContent xmlContent) {
        CommonResponse retVal = new CommonResponse();
        CommonDataBean result = new CommonDataBean();
        UpdateSchemaParam updateSchemaParam = new UpdateSchemaParam();
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = hibernateTransactionManager.getTransaction(def);
        try {
          /*  ErrorInfo info = metaEngine.updateSchema(xmlContent);
            result.setStatus(info.ErrorId + "");
            retVal.setResult(result);
            switch (info.ErrorId) {  //0:??????, 1:schema????????????, -1:??????
                case 0:
                    break;
                case 1:
                    retVal.setSuccess(false);
                    retVal.setMsg("schema????????????");
                    break;
                case -1:
                    retVal.setSuccess(false);
                    retVal.setMsg("???????????????");
                    break;
            }*/
            Mapping map = new Mapping();
            map.loadMapping(Schema.class.getResource("/") + "map.xml");
            Reader b = new StringReader(xmlContent.getXmlContent());
            Unmarshaller unmarshaller = new Unmarshaller(map);
            unmarshaller.setValidation(false);
            Schema newSchema = (Schema) unmarshaller.unmarshal(b);
            String name = newSchema.getName();
            String version = newSchema.getVersion();
            Schema oldSchema = metaEngine.getMeta(true).getSchema(name, version);
            if (oldSchema == null) {
                retVal.setSuccess(false);
                retVal.setMsg("?????????????????????Schema");
                return retVal;
            }
            //??????????????????????????????????????????????????????????????????????????????????????????, <column.getIdentity(), column>
            Map<String, Column> columnMap = new HashMap<>();
            //?????????????????????SQL?????????key???????????????????????????????????????value???sql??????
            Map<Integer, String> createViewSqlMap = new HashMap<>();
            //String: Restriction ???Identity
            Map<String, List<Column>> restrictionRefColumnsMap = new HashMap<>();
            //????????????????????????????????????????????????????????????????????? key?????????????????????CWM_TAB_COLUMNS??????ID???
            //value???????????????CWM_TAB_COLUMNS??????type???MaxLength????????????????????????"=="
            Map<String, String> alterInfoMap = new HashMap<>();
            //?????????????????????????????????????????????????????? integer????????????????????????0,1,2...??????
            //String???????????????????????????CWM_TAB_COLUMNS??????ID???
            Map<Integer, String> alterColumnMap = new HashMap<>();
            Set<String> updateTableList = new HashSet<>();
            Set<String> deleteTableList = new HashSet<>();
            Set<String> addTableList = new HashSet<>();
            updateSchemaParam.setNewSchema(newSchema);
            updateSchemaParam.setOldSchema(oldSchema);
            updateSchemaParam.setColumnMap(columnMap);
            updateSchemaParam.setRestrictionRefColumnsMap(restrictionRefColumnsMap);
            updateSchemaParam.setAlterColumnMap(alterColumnMap);
            updateSchemaParam.setAlterInfo(alterInfoMap);
            updateSchemaParam.setCreateViewSqlMap(createViewSqlMap);
            updateSchemaParam.setUpdateTableList(updateTableList);
            updateSchemaParam.setDeleteTableList(deleteTableList);
            updateSchemaParam.setAddTableList(addTableList);
            OrientContextLoaderListener.Appwac.publishEvent(new UpdateSchemaEvent(this, updateSchemaParam));
            //????????????????????????
            hibernateTransactionManager.commit(status);
            result.setStatus(SchemaUpdateEnum.TYPE_SUCCESS.getType());
            retVal.setResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            //?????????????????????
            if (updateSchemaParam.isNeedFlashback()) {
                //??????????????????
                hibernateTransactionManager.rollback(status);

                //??????????????????????????????
                List<Map<String, Object>> tableMapList = metaDaoFactory.getJdbcTemplate().queryForList("SELECT * FROM USER_TABLES");
                Set<String> addTableList = updateSchemaParam.getAddTableList();
                Set<String> deleteTableList = updateSchemaParam.getDeleteTableList();
                tableMapList.forEach(tableMap -> {
                    addTableList.forEach(addTableName -> {
                        if (CommonTools.Obj2String(tableMap.get("TABLE_NAME")).equals(addTableName)) {
                            metaDaoFactory.getJdbcTemplate().execute("DROP TABLE " + addTableName);       //???????????????
                            metaDaoFactory.getJdbcTemplate().execute("DROP sequence SEQ_" + addTableName);  //??????sequence
                        }
                    });
                });

                deleteTableList.forEach(deleteTableName -> {
                    tableMapList.forEach(tableMap -> {
                        if (CommonTools.Obj2String(tableMap.get("TABLE_NAME")).equals(deleteTableName)) {
                            deleteTableList.remove(deleteTableName);
                        }
                    });
                });

                //?????????????????????
                deleteTableList.forEach(deleteTableName -> metaDaoFactory.getJdbcTemplate().execute("FLASHBACK TABLE " + deleteTableName + " TO BEFORE DROP"));
                //??????session
                List<Map<String, Object>> list = metaDaoFactory.getJdbcTemplate().queryForList("SELECT SESSION_ID FROM V$LOCKED_OBJECT");
                if (list.size() > 0) {
                    String sessionId = CommonTools.Obj2String(list.get(0).get("SESSION_ID"));
                    List<Map<String, Object>> list1 = metaDaoFactory.getJdbcTemplate().queryForList("SELECT SID,SERIAL# FROM V$SESSION WHERE SID = '" + sessionId + "'");
                    String serial = CommonTools.Obj2String(list1.get(0).get("SERIAL#"));
                    Thread t = new Thread(() -> metaDaoFactory.getJdbcTemplate().execute("ALTER SYSTEM DISCONNECT SESSION '" + sessionId + "," + serial + "'" + " IMMEDIATE "));
                    t.start();
                }
                //???????????????bat
                RestoreCommandUtil rc = new RestoreCommandUtil(updateSchemaParam.getFlashbackBatPath());
                rc.execRestore();
            } else {
                if (e instanceof OrientBaseAjaxException) {
                    OrientBaseAjaxException ex = (OrientBaseAjaxException) e;
                    System.out.print(ex.getErrorMsg());
                    result.setStatus(SchemaUpdateEnum.TYPE_COLUMN_HAS_VALUE.getType());
                    retVal.setResult(result);
                    retVal.setSuccess(false);
                    retVal.setMsg(ex.getErrorMsg());
                    return retVal;
                }
            }
            result.setStatus(SchemaUpdateEnum.TYPE_EXCEPTION.getType());
            retVal.setResult(result);
            retVal.setSuccess(false);
            retVal.setMsg("???????????????");
            return retVal;
        }
        return retVal;
    }

}
