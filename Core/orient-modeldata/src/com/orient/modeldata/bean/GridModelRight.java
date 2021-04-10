package com.orient.modeldata.bean;

import java.io.Serializable;

/**
 * 表格模型权限描述
 *
 * @author enjoy
 * @creare 2016-04-01 15:03
 */
public class GridModelRight implements Serializable {

    //新增权限
    private Boolean addAble = false;

    //修改权限
    private Boolean editAble = false;

    //删除权限
    private Boolean deleteAble = false;

    //详细权限
    private Boolean detailAble = true;

    //查询权限
    private Boolean queryAble = true;

    //导入权限
    private Boolean importAble = false;

    //导出权限
    private Boolean exportAble = false;

    //启动流程权限
    private Boolean startFLowAble = false;

    //查看流程权限
    private Boolean detailFLowAble = true;

    //关闭流程权限
    private Boolean endFLowAble = false;

    //附件
    private Boolean attachAble = false;

    public Boolean getDetailAble() {
        return detailAble;
    }

    public void setDetailAble(Boolean detailAble) {
        this.detailAble = detailAble;
    }

    public Boolean getAddAble() {
        return addAble;
    }

    public void setAddAble(Boolean addAble) {
        this.addAble = addAble;
    }

    public Boolean getEditAble() {
        return editAble;
    }

    public void setEditAble(Boolean editAble) {
        this.editAble = editAble;
    }

    public Boolean getDeleteAble() {
        return deleteAble;
    }

    public void setDeleteAble(Boolean deleteAble) {
        this.deleteAble = deleteAble;
    }

    public Boolean getQueryAble() {
        return queryAble;
    }

    public void setQueryAble(Boolean queryAble) {
        this.queryAble = queryAble;
    }

    public Boolean getImportAble() {
        return importAble;
    }

    public void setImportAble(Boolean importAble) {
        this.importAble = importAble;
    }

    public Boolean getExportAble() {
        return exportAble;
    }

    public void setExportAble(Boolean exportAble) {
        this.exportAble = exportAble;
    }

    public Boolean getStartFLowAble() {
        return startFLowAble;
    }

    public void setStartFLowAble(Boolean startFLowAble) {
        this.startFLowAble = startFLowAble;
    }

    public Boolean getDetailFLowAble() {
        return detailFLowAble;
    }

    public void setDetailFLowAble(Boolean detailFLowAble) {
        this.detailFLowAble = detailFLowAble;
    }

    public Boolean getEndFLowAble() {
        return endFLowAble;
    }

    public void setEndFLowAble(Boolean endFLowAble) {
        this.endFLowAble = endFLowAble;
    }

    public Boolean getAttachAble() {
        return attachAble;
    }

    public void setAttachAble(Boolean attachAble) {
        this.attachAble = attachAble;
    }
}
