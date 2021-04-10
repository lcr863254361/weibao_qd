package com.orient.collab.model;

/**
 * ${DESCRIPTION}
 *
 * @author MengBin
 * @create 2016-10-24 8:58
 */
public class PlanViewModel extends Plan{


    public String getBelongedProject() {
        return belongedProject;
    }

    public void setBelongedProject(String belongedProject) {
        this.belongedProject = belongedProject;
    }

    private String belongedProject;
}
