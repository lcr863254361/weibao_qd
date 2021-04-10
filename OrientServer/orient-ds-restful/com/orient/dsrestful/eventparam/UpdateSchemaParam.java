package com.orient.dsrestful.eventparam;

import com.orient.metamodel.metadomain.Column;
import com.orient.metamodel.metadomain.Schema;
import com.orient.web.base.OrientEventBus.OrientEventParams;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by GNY on 2018/4/2
 */
public class UpdateSchemaParam extends OrientEventParams {

    private String flashbackBatPath;
    private Schema oldSchema;
    private Schema newSchema;
    private boolean needFlashback;

    //记录所有字段的信息，方便查询表达式和过滤表达式字段信息的替换, <column.getIdentity(), column>
    private Map<String, Column> columnMap;

    //创建物化视图的SQL语句，key为生成顺序和视图名的组合，value为sql语句
    private Map<Integer, String> createViewSqlMap;

    //String: Restriction的identity
    private Map<String, List<Column>> restrictionRefColumnsMap;

    //记录所有需要修改字段在修改前的类型和长度的信息 key表示修改字段在CWM_TAB_COLUMNS表的ID值
    //value记录字段在CWM_TAB_COLUMNS表的type和MaxLength值，中间分隔符为"=="
    private Map<String, String> alterInfo;

    //记录所有已经修改了的实际表字段的信息 integer表示修改序号，由0,1,2...组成
    //String记录的是修改字段在CWM_TAB_COLUMNS表的ID值
    private Map<Integer, String> alterColumnMap;

    //记录哪几张表更新
    private Set<String> updateTableList;

    private Set<String> deleteTableList;

    private Set<String> addTableList;

    public String getFlashbackBatPath() {
        return flashbackBatPath;
    }

    public void setFlashbackBatPath(String flashbackBatPath) {
        this.flashbackBatPath = flashbackBatPath;
    }

    public Schema getOldSchema() {
        return oldSchema;
    }

    public void setOldSchema(Schema oldSchema) {
        this.oldSchema = oldSchema;
    }

    public Schema getNewSchema() {
        return newSchema;
    }

    public void setNewSchema(Schema newSchema) {
        this.newSchema = newSchema;
    }

    public boolean isNeedFlashback() {
        return needFlashback;
    }

    public void setNeedFlashback(boolean needFlashback) {
        this.needFlashback = needFlashback;
    }

    public Map<String, Column> getColumnMap() {
        return columnMap;
    }

    public void setColumnMap(Map<String, Column> columnMap) {
        this.columnMap = columnMap;
    }

    public Map<Integer, String> getCreateViewSqlMap() {
        return createViewSqlMap;
    }

    public void setCreateViewSqlMap(Map<Integer, String> createViewSqlMap) {
        this.createViewSqlMap = createViewSqlMap;
    }

    public Map<String, List<Column>> getRestrictionRefColumnsMap() {
        return restrictionRefColumnsMap;
    }

    public void setRestrictionRefColumnsMap(Map<String, List<Column>> restrictionRefColumnsMap) {
        this.restrictionRefColumnsMap = restrictionRefColumnsMap;
    }

    public Map<String, String> getAlterInfo() {
        return alterInfo;
    }

    public void setAlterInfo(Map<String, String> alterInfo) {
        this.alterInfo = alterInfo;
    }

    public Map<Integer, String> getAlterColumnMap() {
        return alterColumnMap;
    }

    public void setAlterColumnMap(Map<Integer, String> alterColumnMap) {
        this.alterColumnMap = alterColumnMap;
    }

    public Set<String> getUpdateTableList() {
        return updateTableList;
    }

    public void setUpdateTableList(Set<String> updateTableList) {
        this.updateTableList = updateTableList;
    }

    public Set<String> getDeleteTableList() {
        return deleteTableList;
    }

    public void setDeleteTableList(Set<String> deleteTableList) {
        this.deleteTableList = deleteTableList;
    }

    public Set<String> getAddTableList() {
        return addTableList;
    }

    public void setAddTableList(Set<String> addTableList) {
        this.addTableList = addTableList;
    }

}
