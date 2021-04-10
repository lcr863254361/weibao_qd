package com.orient.modeldata.analyze.bean;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-11-23 16:23
 */
public class ColMapDesc {
    private String fileColName;
    private String timeFormat;
    private String defalutValue;

    private String standardFileColName;

    private String dbColName;
    private String dbColType;

    public String getFileColName() {
        return fileColName;
    }

    public void setFileColName(String fileColName) {
        this.fileColName = fileColName;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public String getDefalutValue() {
        return defalutValue;
    }

    public void setDefalutValue(String defalutValue) {
        this.defalutValue = defalutValue;
    }

    public String getStandardFileColName() {
        return standardFileColName;
    }

    public void setStandardFileColName(String standardFileColName) {
        this.standardFileColName = standardFileColName;
    }

    public String getDbColName() {
        return dbColName;
    }

    public void setDbColName(String dbColName) {
        this.dbColName = dbColName;
    }

    public String getDbColType() {
        return dbColType;
    }

    public void setDbColType(String dbColType) {
        this.dbColType = dbColType;
    }
}
