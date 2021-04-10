package com.orient.download.bean.checkHeadRowCellBean;
//表尾结束内容
public class EndContentBean {
    private String id;
    private String name;
    private String cellType;
    private String content="";
    //只针对type类型是对勾或者是否无类型，true表示检查项被操作过，false代表未操作过
    private boolean operation;
    //2021.2.2新增故障单元格描述
//    private String troubleCellDesp="";
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCellType() {
        return cellType;
    }

    public void setCellType(String cellType) {
        this.cellType = cellType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isOperation() {
        return operation;
    }

    public void setOperation(boolean operation) {
        this.operation = operation;
    }

//    public void setTroubleCellDesp(String troubleCellDesp) {
//        this.troubleCellDesp = troubleCellDesp;
//    }
//
//    public String getTroubleCellDesp() {
//        return troubleCellDesp;
//    }
}
