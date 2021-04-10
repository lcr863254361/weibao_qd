package com.orient.download.bean.uploadCheckInstBean;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2018-10-20 11:20
 */
public class CellDataBean {
    //填写、打钩
    private String content = "";
    private String id;
    private String type;
    //上传的数据
    private String value="";
    private String colIndex;
    //只针对type类型是对勾或者是否无类型，true表示检查项被操作过，false代表未操作过
    private boolean operation;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColIndex() {
        return colIndex;
    }

    public void setColIndex(String colIndex) {
        this.colIndex = colIndex;
    }

    public boolean isOperation() {
        return operation;
    }

    public void setOperation(boolean operation) {
        this.operation = operation;
    }
}
