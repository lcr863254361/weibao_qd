package com.orient.dsrestful.service;

import com.orient.dsrestful.domain.lock.LockResponse;
import com.orient.dsrestful.domain.lock.LockStatus;
import com.orient.dsrestful.domain.schemaBaseInfo.FrontSchema;
import com.orient.dsrestful.enums.LockRequestEnum;
import com.orient.dsrestful.enums.LockResponseEnum;
import com.orient.dsrestful.enums.SchemaLockStatusEnum;
import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.metamodel.metaengine.impl.MetaUtilImpl;
import com.orient.metamodel.operationinterface.IMetaModel;
import com.orient.metamodel.operationinterface.ISchema;
import com.orient.utils.Commons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by GNY on 2018/3/24
 */
@Service
public class BaseSchemaService {

    @Autowired
    MetaUtilImpl metaEngine;

    @Autowired
    MetaDAOFactory metaDaoFactory;

    /**
     * 获取schema基本信息列表
     *
     * @param getAll true表述获取所有数据模型，false表示获取上锁的数据模型
     * @return schema列表
     */
    public List<FrontSchema> getSchemaList(boolean getAll) {
        List<FrontSchema> retVal = new ArrayList<>();
        IMetaModel metaModel = metaEngine.getMeta(false);
        Map<String, Schema> schemaMap = metaModel.getSchemas();
        if (getAll) {
            schemaMap.forEach((schemaName, schema) -> {
                FrontSchema frontSchema = new FrontSchema();
                frontSchema.setLockType(schema.getIsLock() + "");
                frontSchema.setSchemaType(schema.getType());
                frontSchema.setSchemaId(schema.getId());
                frontSchema.setSchemaName(schema.getName());
                frontSchema.setVersion(schema.getVersion());
                frontSchema.setSchemaType(schema.getType());
                retVal.add(frontSchema);
            });
        } else {
            schemaMap.forEach((schemaName, schema) -> {
                Integer isLock = schema.getIsLock();
                if (isLock == SchemaLockStatusEnum.TYPE_LOCKED.value()) {
                    FrontSchema frontSchema = new FrontSchema();
                    frontSchema.setLockType(isLock + "");
                    frontSchema.setSchemaType(schema.getType());
                    frontSchema.setSchemaId(schema.getId());
                    frontSchema.setSchemaName(schema.getName());
                    frontSchema.setVersion(schema.getVersion());
                    frontSchema.setSchemaType(schema.getType());
                    retVal.add(frontSchema);
                }
            });
        }
        return retVal;
    }

    /**
     * 给一个schema上锁
     *
     * @param schema
     * @param username
     * @param ip
     * @param schemaName
     * @param version
     * @param retVal
     * @param result
     * @return
     */
    public LockResponse lockSchema(Schema schema, String username, String ip, String schemaName, String version, LockResponse retVal, LockStatus result) {
        try {
            schema.setIsLock(SchemaLockStatusEnum.TYPE_LOCKED.value());
            schema.setUsername(username);
            schema.setIp(ip);
            schema.setLockModifiedTime(Commons.getSysDate());
            metaDaoFactory.getSchemaDAO().attachDirty(schema);
            IMetaModel metaModel = metaEngine.getMeta(false);
            ISchema iSchema = metaModel.getISchema(schemaName, version);
            Schema newSchema;
            if (iSchema != null) {
                newSchema = (Schema) iSchema;
            } else {
                result.setStatus(LockResponseEnum.TYPE_SCHEMA_NOT_EXIST.value()); //schema不存在
                retVal.setResult(result);
                retVal.setSuccess(false);
                return retVal;
            }
            Integer newLock = newSchema.getIsLock();
            if (newLock == SchemaLockStatusEnum.TYPE_LOCKED.value()) {
                result.setStatus(LockResponseEnum.TYPE_LOCK_SUCCESS.value()); //上锁成功
                retVal.setResult(result);
                return retVal;
            }
        } catch (Exception e) {
            result.setStatus(LockResponseEnum.TYPE_EXCEPTION.value());       //上锁异常
            retVal.setResult(result);
            retVal.setSuccess(false);
            return retVal;
        }
        return null;
    }

    /**
     * 给一个schema解锁
     *
     * @param schema
     * @param retVal
     * @param result
     * @return
     */
    public LockResponse unLockSchema(Schema schema, LockResponse retVal, LockStatus result) {
        try {
            schema.setIsLock(SchemaLockStatusEnum.TYPE_UNLOCKED.value()); //设置为解锁状态值
            schema.setLockModifiedTime(Commons.getSysDate());
            metaDaoFactory.getSchemaDAO().attachDirty(schema);
            result.setStatus(LockResponseEnum.TYPE_UNLOCK_SUCCESS.value());
            retVal.setResult(result);  //解锁成功
            return retVal;
        } catch (Exception e) {
            result.setStatus(LockResponseEnum.TYPE_EXCEPTION.value());       //解锁异常
            retVal.setResult(result);
            retVal.setSuccess(false);
            return retVal;
        }
    }

    /**
     * 获取一个schema的xml
     *
     * @param schemaName
     * @param version
     * @return
     */
    public String obtainSchemaXml(String schemaName, String version) {
        return metaEngine.getSchemaXML(schemaName, version);
    }

    /**
     * 强制解锁
     *
     * @param schemaIdList
     * @return
     */
    public LockResponse forceUnlock(List<String> schemaIdList) {
        LockResponse retVal = new LockResponse();
        LockStatus result = new LockStatus();
        try {
            schemaIdList.forEach(schemaId -> {
                Schema schema = (Schema) metaEngine.getMeta(false).getISchemaById(schemaId);
                schema.setIsLock(LockRequestEnum.TYPE_TO_UNLOCK.value());
                metaDaoFactory.getSchemaDAO().attachDirty(schema);
            });
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(LockResponseEnum.TYPE_EXCEPTION.value());
            retVal.setResult(result);
            return retVal;
        }
        result.setStatus(LockResponseEnum.TYPE_LOCK_SUCCESS.value());
        retVal.setResult(result);
        return retVal;
    }

}
