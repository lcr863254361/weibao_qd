package com.orient.web.modelDesc.model;

import com.orient.sysmodel.domain.form.ModelBtnInstanceEntity;
import com.orient.web.modelDesc.column.ColumnDesc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 模型表格描述
 *
 * @author enjoy
 * @creare 2016-04-01 8:50
 */
public class OrientModelDesc implements Serializable {

    private String modelId;

    private String text;

    private String dbName;

    private String showColumn;

    private List<ColumnDesc> columns = new ArrayList<>();

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public List<ColumnDesc> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnDesc> columns) {
        this.columns = columns;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    //不同操作模式下字段ID集合

    //列表
    private List<String> listColumnDesc = new ArrayList<>();

    //新增
    private List<String> createColumnDesc = new ArrayList<>();

    //修改
    private List<String> modifyColumnDesc = new ArrayList<>();

    //详细
    private List<String> detailColumnDesc = new ArrayList<>();

    //查询
    private List<String> queryColumnDesc = new ArrayList<>();

    //导出
    private List<String> exportColumnDesc = new ArrayList<>();

    public List<String> getListColumnDesc() {
        return listColumnDesc;
    }

    public void setListColumnDesc(List<String> listColumnDesc) {
        this.listColumnDesc = listColumnDesc;
    }

    public List<String> getCreateColumnDesc() {
        return createColumnDesc;
    }

    public void setCreateColumnDesc(List<String> createColumnDesc) {
        this.createColumnDesc = createColumnDesc;
    }

    public List<String> getModifyColumnDesc() {
        return modifyColumnDesc;
    }

    public void setModifyColumnDesc(List<String> modifyColumnDesc) {
        this.modifyColumnDesc = modifyColumnDesc;
    }

    public List<String> getDetailColumnDesc() {
        return detailColumnDesc;
    }

    public void setDetailColumnDesc(List<String> detailColumnDesc) {
        this.detailColumnDesc = detailColumnDesc;
    }

    public List<String> getQueryColumnDesc() {
        return queryColumnDesc;
    }

    public void setQueryColumnDesc(List<String> queryColumnDesc) {
        this.queryColumnDesc = queryColumnDesc;
    }

    public List<String> getExportColumnDesc() {
        return exportColumnDesc;
    }

    public void setExportColumnDesc(List<String> exportColumnDesc) {
        this.exportColumnDesc = exportColumnDesc;
    }

    //按钮集合
    private List<ModelBtnInstanceEntity> btns = new ArrayList<>();

    public List<ModelBtnInstanceEntity> getBtns() {
        return btns;
    }

    public void setBtns(List<ModelBtnInstanceEntity> btns) {
        this.btns = btns;
    }

    public String getShowColumn() {
        return showColumn;
    }

    public void setShowColumn(String showColumn) {
        this.showColumn = showColumn;
    }
}
