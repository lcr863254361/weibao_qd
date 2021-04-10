package com.orient.download.bean.currentTaskBean;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-02-20 16:15
 */
public class PostBean {

    private String id="";
    //岗位名称
    private String postName="";
    //填表人ID
    private String fillPerson="";
    //签署人ID
    private String signPerson="";


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }


    public String getFillPerson() {
        return fillPerson;
    }

    public void setFillPerson(String fillPerson) {
        this.fillPerson = fillPerson;
    }

    public String getSignPerson() {
        return signPerson;
    }

    public void setSignPerson(String signPerson) {
        this.signPerson = signPerson;
    }
}
