package com.orient.collabdev.business.structure.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-20 4:10 PM
 */
public class HisStructure implements Serializable {

    private String id;

    private Integer version;

    private List<HisStructure> children = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public List<HisStructure> getChildren() {
        return children;
    }

    public void setChildren(List<HisStructure> children) {
        this.children = children;
    }
}
