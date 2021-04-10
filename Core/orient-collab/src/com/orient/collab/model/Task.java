package com.orient.collab.model;

import com.orient.businessmodel.annotation.BindModel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import static com.orient.collab.config.CollabConstants.COLLAB_SCHEMA_NAME;
import static com.orient.collab.config.CollabConstants.TASK;

/**
 * the model tree's task
 *
 * @author Seraph
 *         2016-07-01 下午4:59
 */
@BindModel(modelName=TASK, schemaName=COLLAB_SCHEMA_NAME)
public class Task extends StatefulModel {
    public static final String PRINCIPAL = "principal";
    public static final String PAR_PLAN_ID = "parPlanId";
    public static final String PAR_TASK_ID = "parTaskId";

    private String principal;
    private String type;
    private String displayOrder;
    private String parPlanId;
    private String parTaskId;

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(String displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getParPlanId() {
        return parPlanId;
    }

    public void setParPlanId(String parPlanId) {
        this.parPlanId = parPlanId;
    }

    public String getParTaskId() {
        return parTaskId;
    }

    public void setParTaskId(String parTaskId) {
        this.parTaskId = parTaskId;
    }

    /**
     * @param ois
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
    }

    /**
     * @param objectOutputStreams
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream objectOutputStreams) throws IOException {
        objectOutputStreams.defaultWriteObject();
    }

    private static final long serialVersionUID =  1L;
}
