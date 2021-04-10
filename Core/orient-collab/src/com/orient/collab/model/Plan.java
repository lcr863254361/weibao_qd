package com.orient.collab.model;

import com.orient.businessmodel.annotation.BindModel;

import java.io.*;

import static com.orient.collab.config.CollabConstants.COLLAB_SCHEMA_NAME;
import static com.orient.collab.config.CollabConstants.PLAN;

/**
 * plan
 *
 * @author Seraph
 *         2016-07-01 下午4:49
 */
@BindModel(modelName=PLAN, schemaName=COLLAB_SCHEMA_NAME)
public class Plan extends StatefulModel {

    public static final String PRINCIPAL = "principal";
    public static final String PAR_PROJECT_ID = "parProjectId";
    public static final String PAR_PLAN_ID = "parPlanId";

    private String principal;
    private String executor;
    private String type;
    private String displayOrder;
    private String progress;
    private String parDirId;
    private String parProjectId;
    private String parPlanId;

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
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

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getParDirId() {
        return parDirId;
    }

    public void setParDirId(String parDirId) {
        this.parDirId = parDirId;
    }

    public String getParProjectId() {
        return parProjectId;
    }

    public void setParProjectId(String parProjectId) {
        this.parProjectId = parProjectId;
    }

    public String getParPlanId() {
        return parPlanId;
    }

    public void setParPlanId(String parPlanId) {
        this.parPlanId = parPlanId;
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
