/**
 * @ClassName DsWebserviceImpl.java
 * @author Administrator
 * @date 2012-5-14
 */
package com.orient.webservice.schema.Impl;

import com.orient.webservice.schema.ISchema;

import java.util.List;
import java.util.Map;

/**
 * @author wubing
 */
public class ISchemaImpl implements ISchema {

    private ILockImpl lockBean;
    private IScriptImpl genSeqScriptBean;
    private SchemaImpl schemaBean;
    private SchemaInfoImpl schemaInfoBean;
    private ShareSchemaImpl shareSchemaBean;

    public ILockImpl getLockBean() {
        return lockBean;
    }

    public void setLockBean(ILockImpl lockBean) {
        this.lockBean = lockBean;
    }

    public IScriptImpl getGenSeqScriptBean() {
        return genSeqScriptBean;
    }

    public void setGenSeqScriptBean(IScriptImpl genSeqScriptBean) {
        this.genSeqScriptBean = genSeqScriptBean;
    }

    public SchemaImpl getSchemaBean() {
        return schemaBean;
    }

    public void setSchemaBean(SchemaImpl schemaBean) {
        this.schemaBean = schemaBean;
    }

    public SchemaInfoImpl getSchemaInfoBean() {
        return schemaInfoBean;
    }

    public void setSchemaInfoBean(SchemaInfoImpl schemaInfoBean) {
        this.schemaInfoBean = schemaInfoBean;
    }

    public ShareSchemaImpl getShareSchemaBean() {
        return shareSchemaBean;
    }

    public void setShareSchemaBean(ShareSchemaImpl shareSchemaBean) {
        this.shareSchemaBean = shareSchemaBean;
    }

    @Override
    public Map<String, String> getShareAndDefault() {
        return shareSchemaBean.getShareAndDefault();
    }

    @Override
    public String getTableDetail(String id) {
        return shareSchemaBean.getTableDetail(id);
    }

    @Override
    public String canDelete(String id, int type) {
        return shareSchemaBean.canDelete(id, type);
    }

    @Override
    public Map<String, Object> query(Long id) {
        return genSeqScriptBean.query(id);
    }

    @Override
    public List<Map<String, Object>> queryList() {
        return genSeqScriptBean.queryList();
    }

    @Override
    public String insert(Map<String, Object> scriptMap) {
        return genSeqScriptBean.insert(scriptMap);
    }

    @Override
    public String update(Map<String, Object> scriptMap, Long id) {
        return genSeqScriptBean.update(scriptMap, id);
    }

    @Override
    public String delete(String id) {
        return genSeqScriptBean.delete(id);
    }

    @Override
    public String getSchema() {
        return schemaInfoBean.getSchema();
    }

    @Override
    public int deleteSchema(String name, String version) {
        return schemaInfoBean.deleteSchema(name, version);
    }

    @Override
    public String isExistData(String name, String version) {
        return schemaInfoBean.isExistData(name, version);
    }

    @Override
    public String getArithmetic() {
        return schemaInfoBean.getArithmetic();
    }

    @Override
    public String deleteFile(String schemaId) {
        return schemaInfoBean.deleteFile(schemaId);
    }

    @Override
    public String unlockSchema(List<String> list) {
        return schemaInfoBean.unlockSchema(list);
    }

    @Override
    public String updateSeqValue(String id, int value) {
        return schemaInfoBean.updateSeqValue(id, value);
    }

    @Override
    public String updateSeqValue(String id, int value, int interval) {
        return schemaInfoBean.updateSeqValue(id, value, interval);
    }

    @Override
    public String setLock(String schemaName, String version, Integer lockTag, String username, String ip) {
        return lockBean.setLock(schemaName, version, lockTag, username, ip);
    }

    @Override
    public String getSchema(String name, String version) {
        return schemaBean.getSchema(name, version);
    }

    @Override
    public List<String> getSchemaList() {
        return schemaBean.getSchemaList();
    }

    @Override
    public int setSchema(String xmlContent) {
        return schemaBean.setSchema(xmlContent);
    }

    @Override
    public int updateSchema(String xmlContent) {
        return schemaBean.updateSchema(xmlContent);
    }
}
