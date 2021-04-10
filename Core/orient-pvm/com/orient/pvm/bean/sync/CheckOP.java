package com.orient.pvm.bean.sync;

/**
 * Created by mengbin on 16/8/1.
 * Purpose:
 * Detail:
 */
public class CheckOP {


    public static String TYPE_INPUT = "INPUT";
    public static String TYPE_CHECK = "CHECK";

    private String label ,OPTYPE,value ,OPTIME, OPUSERID,fileId;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getOPTYPE() {
        return OPTYPE;
    }

    public void setOPTYPE(String OPTYPE) {
        this.OPTYPE = OPTYPE;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOPTIME() {
        return OPTIME;
    }

    public void setOPTIME(String OPTIME) {
        this.OPTIME = OPTIME;
    }

    public String getOPUSERID() {
        return OPUSERID;
    }

    public void setOPUSERID(String OPUSERID) {
        this.OPUSERID = OPUSERID;
    }
}
