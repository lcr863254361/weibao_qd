package com.orient.collab.model;

import com.orient.businessmodel.annotation.BindModel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import static com.orient.collab.config.CollabConstants.COLLAB_SCHEMA_NAME;
import static com.orient.collab.config.CollabConstants.PROJECT;

/**
 * wbs project
 *
 * @author Seraph
 *         2016-07-01 下午4:41
 */
@BindModel(modelName=PROJECT, schemaName=COLLAB_SCHEMA_NAME)
public class Project extends StatefulModel {

    private String principal;
    private String displayOrder;
    private String parDirId;

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(String displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getParDirId() {
        return parDirId;
    }

    public void setParDirId(String parDirId) {
        this.parDirId = parDirId;
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
