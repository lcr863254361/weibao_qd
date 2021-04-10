package com.orient.collabdev.model;

import java.io.Serializable;

/**
 * @Description
 * @Author GNY
 * @Date 2018/11/5 14:50
 * @Version 1.0
 **/
public class WheelIteration implements Serializable {

    private Integer wheelNumber;
    private Long startVersion;
    private Long endVersion;

    public Integer getWheelNumber() {
        return wheelNumber;
    }

    public void setWheelNumber(Integer wheelNumber) {
        this.wheelNumber = wheelNumber;
    }

    public Long getStartVersion() {
        return startVersion;
    }

    public void setStartVersion(Long startVersion) {
        this.startVersion = startVersion;
    }

    public Long getEndVersion() {
        return endVersion;
    }

    public void setEndVersion(Long endVersion) {
        this.endVersion = endVersion;
    }

}
