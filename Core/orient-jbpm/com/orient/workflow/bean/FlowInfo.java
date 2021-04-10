package com.orient.workflow.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/8 0008.
 * 流程描述
 */
public class FlowInfo implements Serializable {

    private String id;

    private Long dbId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FlowInfo(String id, Long dbId) {
        this.id = id;
        this.dbId = dbId;
    }
}
