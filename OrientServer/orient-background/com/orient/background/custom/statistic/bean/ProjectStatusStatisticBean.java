package com.orient.background.custom.statistic.bean;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2017-04-10 5:38 PM
 */
public class ProjectStatusStatisticBean implements Serializable{

    private String status;

    private Integer count;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public ProjectStatusStatisticBean(String status, Integer count) {
        this.status = status;
        this.count = count;
    }
}
