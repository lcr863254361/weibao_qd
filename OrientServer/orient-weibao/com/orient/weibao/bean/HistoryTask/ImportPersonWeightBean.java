package com.orient.weibao.bean.HistoryTask;

public class ImportPersonWeightBean {
    private String personWeightKey;
    private String userId;
    private String userName;
    private String department="";
    private String weight="";
    private String joinShipDate;
    private String totalDays;
    private String liveboat="";
    private String technicalPost="";
    private String hangduanPost="";
    private String politicsStatus="";

    public String getPersonWeightKey() {
        return personWeightKey;
    }

    public void setPersonWeightKey(String personWeightKey) {
        this.personWeightKey = personWeightKey;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getJoinShipDate() {
        return joinShipDate;
    }

    public void setJoinShipDate(String joinShipDate) {
        this.joinShipDate = joinShipDate;
    }

    public String getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(String totalDays) {
        this.totalDays = totalDays;
    }

    public String getLiveboat() {
        return liveboat;
    }

    public void setLiveboat(String liveboat) {
        this.liveboat = liveboat;
    }

    public String getTechnicalPost() {
        return technicalPost;
    }

    public void setTechnicalPost(String technicalPost) {
        this.technicalPost = technicalPost;
    }

    public String getHangduanPost() {
        return hangduanPost;
    }

    public void setHangduanPost(String hangduanPost) {
        this.hangduanPost = hangduanPost;
    }

    public String getPoliticsStatus() {
        return politicsStatus;
    }

    public void setPoliticsStatus(String politicsStatus) {
        this.politicsStatus = politicsStatus;
    }
}
