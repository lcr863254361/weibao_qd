package com.orient.modeldata.tbomHandle.handle;

import com.orient.modeldata.bean.TBomNode;
import com.orient.web.model.BaseNode;

import java.util.List;

public interface TbomHandle {

    public void handleTreeNodes(List<BaseNode> nodes, TBomNode fatherNode);
}
