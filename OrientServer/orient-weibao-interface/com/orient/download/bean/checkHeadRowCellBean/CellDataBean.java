package com.orient.download.bean.checkHeadRowCellBean;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2018-10-20 11:20
 */
public class CellDataBean {
    private String content="";
    private String id;
    private String type;
    private String colIndex;
    //2021.2.2新增故障单元格描述
//    private String troubleCellDesp="";

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

    public String getColIndex() {
        return colIndex;
    }

    public void setColIndex(String colIndex) {
        this.colIndex = colIndex;
    }

//    public void setTroubleCellDesp(String troubleCellDesp) {
//        this.troubleCellDesp = troubleCellDesp;
//    }
//
//    public String getTroubleCellDesp() {
//        return troubleCellDesp;
//    }
}
