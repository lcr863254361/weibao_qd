package com.orient.collab.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2016-07-20 上午10:21
 */
public class GanttResource {

    private String id;
    private String name;

    @JsonProperty("Id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
