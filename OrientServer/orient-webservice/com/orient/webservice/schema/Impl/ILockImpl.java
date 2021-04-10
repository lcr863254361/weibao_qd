package com.orient.webservice.schema.Impl;

import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.operationinterface.IMetaModel;
import com.orient.metamodel.operationinterface.ISchema;
import com.orient.utils.Commons;
import org.springframework.dao.DataAccessException;


public class ILockImpl extends SchemaBean {
    /**
     * 上锁.
     *
     * @param schemaName 数据模型名称
     * @param version    数据模型版本号
     * @param lock       上锁标记，1为上锁
     * @param username   用户名
     * @param ip         ip
     * @return 锁定成功或失败信息
     * @throws Exception 异常信息
     */
    private String lock(Schema schema, String schemaName, String version, Integer lock, String username, String ip) throws Exception {
        schema.setIsLock(lock);
        schema.setUsername(username);
        schema.setIp(ip);
        schema.setLockModifiedTime(Commons.getSysDate());
        metadaofactory.getSchemaDAO().attachDirty(schema);
        IMetaModel metaModel = metaEngine.getMeta(false);
        ISchema schemaMap = metaModel.getISchema(schemaName, version);
        Schema newSchema = null;
        if (schemaMap != null) {
            newSchema = (Schema) schemaMap;
        } else {
            return 5 + username;
        }
        Integer newLock = newSchema.getIsLock();
        if (newLock.equals(new Long(1))) {
            return 1 + username;//锁定成功
        }
        return 0 + username;
    }

    public String setLock(String schemaName, String version, Integer lock, String username, String ip) {
        //lock 0表示解锁，1表示上锁，4表示解除模型的打开状态并解锁，5表示解除模型的打开状态。
        try {
            StringBuffer sql = new StringBuffer();
            IMetaModel metaModel = metaEngine.getMeta(false);
            Schema schema = (Schema) metaModel.getISchema(schemaName, version);
            if (schema == null) {
                return 5 + username;
            }
            Integer oldLock = schema.getIsLock();
            String oldusername = schema.getUsername();
            String oldIp = schema.getIp();
            if (1 == lock) {//上锁操作
                if (oldLock == 1) {
                    if (!username.equals(oldusername) || !oldIp.equals(ip)) {

                        return 2 + oldusername;// 已有用户将该业务库锁定
                    } else {
                        return 1 + username;
                    }
                } else {
                    return lock(schema, schemaName, version, lock, username, ip);
                }
            } else if (lock == 0) {//解锁操作
                if (ip.equals(oldIp) && username.equals(oldusername)) {
                    schema.setIsLock(lock);
                    schema.setLockModifiedTime(Commons.getSysDate());
                    metadaofactory.getSchemaDAO().attachDirty(schema);
                    return 3 + username;//解锁成功
                } else {
                    return 4 + username;//被其他用户锁住，无法解锁
                }
            }
            return 0 + username;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return "false";
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }
}
