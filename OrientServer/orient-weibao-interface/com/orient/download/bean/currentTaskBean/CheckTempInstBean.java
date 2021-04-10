package com.orient.download.bean.currentTaskBean;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-02-20 15:31
 */
public class CheckTempInstBean {
    private String id ;
    private String checkName = "";
    private String state = "";
    //2019.08.19 新增检查表格类型
    private String checkTableType = "";
    //20201116 新增是否重复上传检查表的属性
    private String isRepeatUpload = "";

    public String getIsRepeatUpload() {
        return isRepeatUpload;
    }

    public void setIsRepeatUpload(String isRepeatUpload) {
        this.isRepeatUpload = isRepeatUpload;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCheckTableType() {
        return checkTableType;
    }

    public void setCheckTableType(String checkTableType) {
        this.checkTableType = checkTableType;
    }
}
