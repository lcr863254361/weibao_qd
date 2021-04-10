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
            retVal.setMsg("服务端异常");
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
            retVal.setMsg("服务端异常");
            return retVal;
        }
        return retVal;
    }

    /**
     * @param commonSchema
     * @return -1:异常 0:成功 1:schema不存在
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
                //发送事件，把代码拆分到不同的类中
                OrientContextLoaderListener.Appwac.publishEvent(new DeleteSchemaEvent(this, eventParam));
                result.setStatus(SchemaDeleteResponseEnum.TYPE_DELETE_SUCCESS.toString());
                retVal.setResult(result);
            } catch (Exception e) {
                e.printStackTrace();
                throw new DSException(SchemaDeleteResponseEnum.TYPE_EXCEPTION.getType(), "服务端异常：" + e.getMessage());
            }
        } else {
            throw new DSException(SchemaDeleteResponseEnum.TYPE_SCHEMA_NOT_EXIST.toString(), "schema不存在");
        }
        return retVal;
    }

    /**
     * 删除数据模型时校验该数据模型是否已经存在数据记录
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
                    retVal.setMsg("schema已被上锁");
                    return retVal;
                }
                //引用的数据类不统计其是否包含的数据
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
                            retVal.setMsg("表不存在");
                            return retVal;
                        }
                    }
                }
            } else {
                result.setStatus(SchemaDataExistEnum.TYPE_SCHEMA_NOT_EXIST.toString());
                retVal.setResult(result);
                retVal.setSuccess(false);
                retVal.setMsg("schema不存在");
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
            retVal.setMsg("服务端异常");
            return retVal;
        }
    }

    //0:成功, 1:schema已经存在(schema重名), -1:异常
    public CommonResponse saveSchema(XmlContent xmlContent) {
        CommonResponse retVal = new CommonResponse();
        CommonDataBean result = new CommonDataBean();
        try {
            //把xml转为schema对象
            Schema schema = metaEngine.transformToSchema(xmlContent.getXmlContent());
            if (metaEngine.getMeta(false).getSchema(schema.getName(), schema.getVersion()) != null) {
                throw new DSException(SchemaSaveResponseEnum.TYPE_SCHEMA_DUPLICATION_NAME.getType(), "已经存在名称和版本号相同的schema，请修改名称或版本号后再保存");
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
            throw new DSException(SchemaSaveResponseEnum.TYPE_EXCEPTION.getType(), "服务端异常：" + e.getMessage());
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
            switch (info.ErrorId) {  //0:成功, 1:schema已经存在, -1:异常
                case 0:
                    break;
                case 1:
                    retVal.setSuccess(false);
                    retVal.setMsg("schema已经存在");
                    break;
                case -1:
                    retVal.setSuccess(false);
                    retVal.setMsg("服务端异常");
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
                retVal.setMsg("没有找到对应的Schema");
                return retVal;
            }
            //记录所有字段的信息，方便查询表达式和过滤表达式字段信息的替换, <column.getIdentity(), column>
            Map<String, Column> columnMap = new HashMap<>();
            //创建物化视图的SQL语句，key为生成顺序和视图名的组合，value为sql语句
            Map<Integer, String> createViewSqlMap = new HashMap<>();
            //String: Restriction 的Identity
            Map<String, List<Column>> restrictionRefColumnsMap = new HashMap<>();
            //记录所有需要修改字段在修改前的类型和长度的信息 key表示修改字段在CWM_TAB_COLUMNS表的ID值
            //value记录字段在CWM_TAB_COLUMNS表的type和MaxLength值，中间分隔符为"=="
            Map<String, String> alterInfoMap = new HashMap<>();
            //记录所有已经修改了的实际表字段的信息 integer表示修改序号，由0,1,2...组成
            //String记录的是修改字段在CWM_TAB_COLUMNS表的ID值
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
            //改为手动提交事务
            hibernateTransactionManager.commit(status);
            result.setStatus(SchemaUpdateEnum.TYPE_SUCCESS.getType());
            retVal.setResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            //执行表的行闪回
            if (updateSchemaParam.isNeedFlashback()) {
                //手动回滚事务
                hibernateTransactionManager.rollback(status);

                //把新增表和删除表还原
                List<Map<String, Object>> tableMapList = metaDaoFactory.getJdbcTemplate().queryForList("SELECT * FROM USER_TABLES");
                Set<String> addTableList = updateSchemaParam.getAddTableList();
                Set<String> deleteTableList = updateSchemaParam.getDeleteTableList();
                tableMapList.forEach(tableMap -> {
                    addTableList.forEach(addTableName -> {
                        if (CommonTools.Obj2String(tableMap.get("TABLE_NAME")).equals(addTableName)) {
                            metaDaoFactory.getJdbcTemplate().execute("DROP TABLE " + addTableName);       //删除业务表
                            metaDaoFactory.getJdbcTemplate().execute("DROP sequence SEQ_" + addTableName);  //删除sequence
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

                //把删除的表闪回
                deleteTableList.forEach(deleteTableName -> metaDaoFactory.getJdbcTemplate().execute("FLASHBACK TABLE " + deleteTableName + " TO BEFORE DROP"));
                //关闭session
                List<Map<String, Object>> list = metaDaoFactory.getJdbcTemplate().queryForList("SELECT SESSION_ID FROM V$LOCKED_OBJECT");
                if (list.size() > 0) {
                    String sessionId = CommonTools.Obj2String(list.get(0).get("SESSION_ID"));
                    List<Map<String, Object>> list1 = metaDaoFactory.getJdbcTemplate().queryForList("SELECT SID,SERIAL# FROM V$SESSION WHERE SID = '" + sessionId + "'");
                    String serial = CommonTools.Obj2String(list1.get(0).get("SERIAL#"));
                    Thread t = new Thread(() -> metaDaoFactory.getJdbcTemplate().execute("ALTER SYSTEM DISCONNECT SESSION '" + sessionId + "," + serial + "'" + " IMMEDIATE "));
                    t.start();
                }
                //执行闪回的bat
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
            retVal.setMsg("服务端异常");
            return retVal;
        }
        return retVal;
    }

}
