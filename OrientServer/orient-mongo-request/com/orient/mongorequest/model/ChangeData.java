package com.orient.mongorequest.model;

/**
 * ${DESCRIPTION}
 *
 * @author GNY
 * @create 2018-06-07 14:55
 */
public class ChangeData {

    private String column;
    private String changeValue;
    private String objId;

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getChangeValue() {
        return changeValue;
    }

    public void setChangeValue(String changeValue) {
        this.changeValue = changeValue;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

}
