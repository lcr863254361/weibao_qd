package com.orient.collab.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2016-07-20 上午10:21
 */
public class GanttResourceAssign {


    private String id;
    private String planId;
    private String resourceId;
    private String units;

    @JsonProperty("Id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("TaskId")
    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    @JsonProperty("ResourceId")
    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    @JsonProperty("Units")
    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }
}
