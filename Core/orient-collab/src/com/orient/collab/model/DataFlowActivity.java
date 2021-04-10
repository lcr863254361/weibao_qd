package com.orient.collab.model;

import com.orient.sysmodel.domain.collab.CollabJobCarvePos;

/**
 * Created by mengbin on 16/8/22.
 * Purpose:
 * Detail:
 */
public class DataFlowActivity {

    private String id;
    private String dispalyName;
    private String xPos;
    private String yPos;
    private String width;
    private String height;

    private String modelId;

    private String dataId;

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getDispalyName() {
        return dispalyName;
    }

    public void setDispalyName(String dispalyName) {
        this.dispalyName = dispalyName;
    }

    public String getxPos() {
        return xPos;
    }

    public void setxPos(String xPos) {
        this.xPos = xPos;
    }

    public String getyPos() {
        return yPos;
    }

    public void setyPos(String yPos) {
        this.yPos = yPos;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }


    public void setPos(CollabJobCarvePos pos) {
        this.xPos = String.valueOf(pos.getXpos());
        this.yPos = String.valueOf(pos.getYpos());
        this.width = String.valueOf(pos.getWidth());
        this.height = String.valueOf(pos.getHeigth());
        this.modelId = String.valueOf(pos.getModelid());
        this.dataId = String.valueOf(pos.getDataid());
    }

    public CollabJobCarvePos conStructJobCarvePos() {

        CollabJobCarvePos pos = new CollabJobCarvePos();
        pos.setModelid(Long.valueOf(modelId));
        pos.setDataid(Long.valueOf(dataId));
        pos.setXpos(Float.valueOf(xPos));
        pos.setYpos(Float.valueOf(yPos));
        pos.setWidth(Float.valueOf(width));
        pos.setHeigth(Float.valueOf(height));
        pos.setId(null == id ? null : Long.valueOf(id));
        return pos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
