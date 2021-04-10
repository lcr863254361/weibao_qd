package com.orient.download.bean.checkHeadRowCellBean;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2018-10-20 11:19
 */
public class HeadBean {
    private String name;
    private String colIndex;
    private String dbId;
    private boolean isCheck = false;//是否为检查类，为打钩或者填值

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        //特殊标志位
        if (name.indexOf("#") == 0 && name.length() != 0) {
//            name = name.substring(1);
            this.isCheck = true;
        }
        this.name = name;
    }

    public String getColIndex() {
        return colIndex;
    }

    public void setColIndex(String colIndex) {
        this.colIndex = colIndex;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
