package com.orient.collab.model;

/**
 * the assign user bean
 *
 * @author Seraph
 *         2016-07-11 下午3:34
 */
public class AssignUserBean {

    private String id;
    private String userName;
    private String allName;
    private String department;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAllName() {
        return allName;
    }

    public void setAllName(String allName) {
        this.allName = allName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

}
