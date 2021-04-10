package com.orient.component.runenv.model;


import java.io.Serializable;

public class ComponentFlowData extends AbstractComponentFlowData implements Serializable {

    public ComponentFlowData(String id, String projectCode, String key, String value) {
        super(id, projectCode, key, value);
    }

    public ComponentFlowData() {
        super();
    }

}
