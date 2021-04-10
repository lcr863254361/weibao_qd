package com.orient.sysmodel.domain.pvm;

/**
 * Created by Administrator on 2016/11/8.
 */
public class CwmSysCheckModelColumn extends CwmSysCheckmodelsetEntity {
    private String columnName;
    private String checkTypeName;
    private String isRequiredName;
    private String isBindPhotoName;

    public String getIsBindPhotoName() {
        return isBindPhotoName;
    }

    public void setIsBindPhotoName(String isBindPhotoName) {
        this.isBindPhotoName = isBindPhotoName;
    }

    public String getIsRequiredName() {
        return isRequiredName;
    }

    public void setIsRequiredName(String isRequiredName) {
        this.isRequiredName = isRequiredName;
    }

    public void setColumnName(String columnName) {this.columnName = columnName;}

    public String getColumnName() {return this.columnName;}

    public void setCheckTypeName(String checkTypeName) {this.checkTypeName = checkTypeName;}

    public String getCheckTypeName() {return this.checkTypeName;}
}
