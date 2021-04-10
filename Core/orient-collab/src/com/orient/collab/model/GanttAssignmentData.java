package com.orient.collab.model;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2016-07-20 上午10:30
 */
public class GanttAssignmentData {

    private List<GanttResource> resources;
    private List<GanttResourceAssign> assignments;

    public List<GanttResource> getResources() {
        return resources;
    }

    public void setResources(List<GanttResource> resources) {
        this.resources = resources;
    }

    public List<GanttResourceAssign> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<GanttResourceAssign> assignments) {
        this.assignments = assignments;
    }
}
