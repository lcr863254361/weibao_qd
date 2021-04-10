package com.orient.ods.atfx.model;

import java.util.List;

/**
 * Created by mengbin on 16/3/8.
 * Purpose:
 * Detail:
 */
public class AoMeasurementQuantityNode extends  ODSNode  {

    public AoMeasurementQuantityNode() {
        this.nodetype = BASETYPE.AoMeasurementQuantity;
    }

    @Override
    public List<ODSNode> constructChildNodes(boolean cascade) throws Exception {
        return super.constructChildNodes(cascade);
    }
}
