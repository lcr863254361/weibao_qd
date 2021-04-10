package com.orient.collab.model;

import com.orient.businessmodel.annotation.BindModel;

import static com.orient.collab.config.CollabConstants.COLLAB_SCHEMA_NAME;
import static com.orient.collab.config.CollabConstants.DIRECTORY;

/**
 * directory
 *
 * @author Seraph
 *         2016-07-01 下午3:43
 */
@BindModel(modelName=DIRECTORY, schemaName=COLLAB_SCHEMA_NAME)
public class Directory {

    private String id;
    private String name;
    private String displayOrder;
    private String parDirId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
