package com.orient.collab.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.orient.businessmodel.annotation.BindModel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import static com.orient.collab.config.CollabConstants.COLLAB_SCHEMA_NAME;
import static com.orient.collab.config.CollabConstants.PLAN_DEPENDENCY;

/**
 *  gannt plan's dependency
 *
 * @author Seraph
 *         2016-07-18 下午5:16
 */
@BindModel(modelName=PLAN_DEPENDENCY, schemaName=COLLAB_SCHEMA_NAME)
public class GanttPlanDependency implements Serializable {

    public static final String START_PLAN_ID = "startPlanId";
    public static final String FINISH_PLAN_ID = "finishPlanId";
    public static final String BELONGED_PROJECT_ID = "blngProjectId";

    private String id;
    private String type;
    private String finishPlanId;
    private String startPlanId;
    private String baseLineId;
    private String prjId;
    private String prjVersion;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFinishPlanId() {
        return finishPlanId;
    }

    public void setFinishPlanId(String finishPlanId) {
        this.finishPlanId = finishPlanId;
    }

    public String getStartPlanId() {
        return startPlanId;
    }

    public void setStartPlanId(String startPlanId) {
        this.startPlanId = startPlanId;
    }

    @JsonIgnore
    public String getBaseLineId() {
        return baseLineId;
    }

    public void setBaseLineId(String baseLineId) {
        this.baseLineId = baseLineId;
    }

    @JsonProperty("Id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonIgnore
    public String getPrjId() {
        return prjId;
    }

    public void setPrjId(String prjId) {
        this.prjId = prjId;
    }


    public String getPrjVersion() {
        return prjVersion;
    }

    public void setPrjVersion(String prjVersion) {
        this.prjVersion = prjVersion;
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

}
