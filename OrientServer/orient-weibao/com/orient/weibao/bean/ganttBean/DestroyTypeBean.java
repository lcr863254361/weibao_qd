package com.orient.weibao.bean.ganttBean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.orient.collab.model.GanttPlan;
import com.orient.utils.UtilFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class DestroyTypeBean {

//    @JsonProperty("Id")
    private String id;  //必须
    private String name;//必须
    private String modelName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
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
