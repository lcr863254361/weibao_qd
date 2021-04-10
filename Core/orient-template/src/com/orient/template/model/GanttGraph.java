package com.orient.template.model;

import java.io.Serializable;

/**
 * used as root node in a gantt graph, to create relations to project tree,
 * and make it easy to preview a gantt graph
 *
 * @author Seraph
 *         2016-09-28 下午2:13
 */
public class GanttGraph implements Serializable {

    transient private String projectId;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    private static final long serialVersionUID =  1L;
}
