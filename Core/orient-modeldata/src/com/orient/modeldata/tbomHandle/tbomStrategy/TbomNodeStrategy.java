package com.orient.modeldata.tbomHandle.tbomStrategy;

import com.orient.web.model.BaseNode;
import com.orient.modeldata.eventParam.GetTbomNodesEventParam;

import java.util.List;

/**
 * Created by enjoy on 2016/5/22 0022.
 * 生成Tbom节点策略
 */
public interface TbomNodeStrategy {

    //获取子节点信息
    List<BaseNode> getSonTbomNodes(GetTbomNodesEventParam getTbomNodesEventParam);
}

