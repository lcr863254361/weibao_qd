package com.orient.collabdev.model;

import com.orient.sysmodel.domain.collabdev.CollabNodeDevStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-04 2:42 PM
 */
public class PedigreeDTO implements Serializable {

    private List<PedigreeNode> nodes = new ArrayList<>();

    private List<PedigreeNodeRelation> relations = new ArrayList<>();

    private List<CollabNodeDevStatus> statuses = new ArrayList<>();

    public List<PedigreeNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<PedigreeNode> nodes) {
        this.nodes = nodes;
    }

    public List<PedigreeNodeRelation> getRelations() {
        return relations;
    }

    public void setRelations(List<PedigreeNodeRelation> relations) {
        this.relations = relations;
    }

    public List<CollabNodeDevStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<CollabNodeDevStatus> statuses) {
        this.statuses = statuses;
    }
}
