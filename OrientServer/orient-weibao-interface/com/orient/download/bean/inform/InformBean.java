package com.orient.download.bean.inform;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-05-30 14:27
 */
public class InformBean {
    private String id="";
    //部门
    private String department="";
    //通知内容
    private String informContent="";
    //发布日期
    private String date="";
    //通知人
    private String publisher="";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getInformContent() {
        return informContent;
    }

    public void setInformContent(String informContent) {
        this.informContent = informContent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
