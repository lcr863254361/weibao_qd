package com.orient.flow.config;

/**
 * flow type
 *
 * @author Seraph
 *         2016-07-04 下午3:43
 */
public enum FlowType {

    Audit(FlowType.FLOW_AUDIT), Collab(FlowType.FLOW_COLLAB);

    FlowType(String name){
        this.name = name;
    }
    @Override
    public String toString(){
        return this.name;
    }

    private final String name;
    private static final String FLOW_AUDIT = "audit";
    private static final String FLOW_COLLAB = "collab";
}
