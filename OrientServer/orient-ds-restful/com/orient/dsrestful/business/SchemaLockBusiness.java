package com.orient.dsrestful.business;

import com.orient.dsrestful.domain.lock.LockResponse;
import com.orient.dsrestful.domain.lock.LockStatus;
import com.orient.dsrestful.domain.lock.SchemaLockBean;
import com.orient.dsrestful.enums.LockRequestEnum;
import com.orient.dsrestful.enums.LockResponseEnum;
import com.orient.dsrestful.enums.SchemaLockStatusEnum;
import com.orient.dsrestful.service.BaseSchemaService;
import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.metamodel.operationinterface.IMetaModel;
import com.orient.web.base.BaseBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author GNY
 * @create 2018-03-24 11:08
 */
@Component
public class SchemaLockBusiness extends BaseBusiness {

    @Autowired
    BaseSchemaService baseSchemaService;

    @Autowired
    MetaDAOFactory metaDaoFactory;

    public LockResponse setLock(SchemaLockBean schemaLockBean) {
        LockResponse retVal = new LockResponse();
        LockStatus result = new LockStatus(schemaLockBean.getUsername(), schemaLockBean.getIp());
        try {
            IMetaModel metaModel = metaEngine.getMeta(false);
            Schema schema = (Schema) metaModel.getISchema(schemaLockBean.getSchemaName(), schemaLockBean.getVersion());
            if (schema == null) {
                retVal.setSuccess(false);
                result.setStatus(LockResponseEnum.TYPE_SCHEMA_NOT_EXIST.value());
                retVal.setResult(result);
                return retVal;
            }
            //获取数据库中该schema的锁住状态
            Integer dbIsLocked = schema.getIsLock();
            String dbUsername = schema.getUsername();
            String dbIp = schema.getIp();
            switch (LockRequestEnum.valueOf(schemaLockBean.getLockTag())) {
                case TYPE_TO_LOCK: //客户端请求上锁
                    if (dbIsLocked == SchemaLockStatusEnum.TYPE_LOCKED.value()) {  //已经上锁
                        if (schemaLockBean.getUsername().equals(dbUsername) && schemaLockBean.getIp().equals(dbIp)) {
                            result.setStatus(LockResponseEnum.TYPE_LOCK_SUCCESS.value());
                            retVal.setResult(result); //如果是自己上的锁返回1
                        } else {
                            result.setStatus(LockResponseEnum.TYPE_HAS_LOCKED.value()); //如果是别人上的锁返回2
                            retVal.setResult(result);
                            retVal.setMsg("当前schema已被其他用户上锁，您无法上锁");
                            retVal.setSuccess(false);
                        }
                    } else {
                        return baseSchemaService.lockSchema(schema, schemaLockBean.getSchemaName(), schemaLockBean.getVersion(), schemaLockBean.getUsername(), schemaLockBean.getIp(), retVal, result);
                    }
                    break;
                case TYPE_TO_UNLOCK: //客户端请求解锁
                    if (dbIsLocked == SchemaLockStatusEnum.TYPE_UNLOCKED.value()) {  //已经解锁
                        result.setStatus(LockResponseEnum.TYPE_HAS_UNLOCKED.value());
                        retVal.setResult(result);
                        retVal.setMsg("当前schema已被其他用户解锁，不需要解锁");
                        retVal.setSuccess(false);
                    } else {
                        if (schemaLockBean.getUsername().equals(dbUsername) && schemaLockBean.getIp().equals(dbIp)) {  //被自己上的锁，可以解锁
                            return baseSchemaService.unLockSchema(schema, retVal, result);
                        } else { //被别人上的锁，无法解锁
                            result.setStatus(LockResponseEnum.TYPE_CAN_NOT_UNLOCK.value());
                            retVal.setResult(result);
                            retVal.setMsg("当前schema已经被其他用户上锁，您无法解锁");
                            retVal.setSuccess(false);  //当前schema非该用户上锁，无法解锁，但是依旧可以强制解锁
                        }
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(LockResponseEnum.TYPE_EXCEPTION.value());
            retVal.setResult(result);
            retVal.setSuccess(false);
            return retVal;
        }
        return retVal;
    }

    /**
     * 强制解锁,不用校验用户名和密码
     *
     * @param schemaIdList
     * @return
     */
    public LockResponse forceUnlock(List<String> schemaIdList) {
        return baseSchemaService.forceUnlock(schemaIdList);
    }

}
