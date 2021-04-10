package com.orient.ods.atfx.model;

import java.util.List;

/**
 * Created by mengbin on 16/3/10.
 * Purpose:
 * Detail:
 */
public class AoLocalColumnNode extends ODSNode {

    public AoLocalColumnNode() {
        this.nodetype = BASETYPE.AoLocalColumn;
    }

    @Override
    public void setChild(List<ODSNode> child) {
        super.setChild(child);
    }


}
