package com.orient.background.custom.statistic.bean;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author ZhangSheng
 * @create 2018-09-04 10:12 AM
 */
public class CollabTaskStatisticBean implements Serializable{

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

    public CollabTaskStatisticBean(String status, Integer count) {
        this.status = status;
        this.count = count;
    }
}
