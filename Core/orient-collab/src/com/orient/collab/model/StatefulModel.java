package com.orient.collab.model;

import java.io.Serializable;

/**
 * represent this model can has a statue which can lead to different behaviour
 *
 * @author Seraph
 *         2016-07-21 下午2:25
 */
public class StatefulModel implements Serializable {

    public static final String STATUS = "status";
    public static final String NAME = "name";
    public static final String PLANED_START_DATE = "plannedStartDate";
    public static final String PLANED_END_DATE = "plannedEndDate";
    public static final String ACTUAL_START_DATE = "actualStartDate";
    public static final String ACTUAL_END_DATE = "actualEndDate";

    private String plannedStartDate;
    private String plannedEndDate;
    private String actualStartDate;
    private String actualEndDate;

    private String id;
    private String name;

    private String status;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlannedStartDate() {
        return plannedStartDate;
    }

    public void setPlannedStartDate(String plannedStartDate) {
        this.plannedStartDate = plannedStartDate;
    }

    public String getPlannedEndDate() {
        return plannedEndDate;
    }

    public void setPlannedEndDate(String plannedEndDate) {
        this.plannedEndDate = plannedEndDate;
    }

    public String getActualStartDate() {
        return actualStartDate;
    }

    public void setActualStartDate(String actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    public String getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(String actualEndDate) {
        this.actualEndDate = actualEndDate;
    }
}
